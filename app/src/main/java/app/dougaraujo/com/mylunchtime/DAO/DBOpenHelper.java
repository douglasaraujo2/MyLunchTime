package app.dougaraujo.com.mylunchtime.DAO;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.dougaraujo.com.mylunchtime.R;

/**
 * Created by Dux-Douglas2 on 15/07/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "usuarios.db";
    private static final int VERSAO_BANCO = 1;
    private Context ctx;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSAO_BANCO);
        this.ctx = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            iniciarDb(db, ctx, R.raw.db_criar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; ++i) {
            String migrationFileName = String.format("from_%d_to_%d", i, (i + 1));
            log("Looking for migration file: " + migrationFileName);
            int migrationFileResId = ctx.getResources()
                    .getIdentifier(migrationFileName, "raw", ctx.getPackageName());
            if (migrationFileResId != 0) {
// execute script
                log("Found, executing");
                lerEExecutarSQLScript(db, ctx, migrationFileResId);
            } else {
                log("Not found!");
            }
        }
    }

    private void iniciarDb(SQLiteDatabase db, Context ctx, Integer
            sqlScriptResId) throws IOException {
        Resources res = ctx.getResources();
        InputStream is = res.openRawResource(sqlScriptResId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        executarSQLScript(db, reader);
        reader.close();
        is.close();
    }

    private void executarSQLScript(SQLiteDatabase db, BufferedReader reader) throws
            IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            statement.append(line);
            statement.append(" \n ");
            if (line.endsWith(";")) {
                String toExec = statement.toString();
                log("Executing script: " + toExec);
                db.execSQL(toExec);
                statement = new StringBuilder();
            }
        }


    }

    private void lerEExecutarSQLScript(SQLiteDatabase db, Context ctx, int sqlScriptResId) {
        Resources res = ctx.getResources();
        try {
            InputStream is = res.openRawResource(sqlScriptResId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            executarSQLScript(db, reader);
            reader.close();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException("Não foi possivel ler o arquivo SQLite", e);
        }
    }

    private void log(String msg) {
        Log.d(DBOpenHelper.class.getSimpleName(), msg);
    }
}
