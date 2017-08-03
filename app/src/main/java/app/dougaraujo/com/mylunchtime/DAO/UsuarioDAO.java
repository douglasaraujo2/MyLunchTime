package app.dougaraujo.com.mylunchtime.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.LinkedList;
import java.util.List;

import app.dougaraujo.com.mylunchtime.model.Usuario;

/**
 * Created by Dux-Douglas2 on 15/07/2017.
 */

public class UsuarioDAO {
    public static final String TABELA_USUARIOS = "usuarios";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "usuario";
    public static final String COLUNA_SENHA = "senha";
    private DBOpenHelper banco;

    public UsuarioDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    public List<Usuario> getAll() {
        List<Usuario> usuarios = new LinkedList<>();
        String query = "SELECT * FROM " + TABELA_USUARIOS;
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            do {
                usuario = new Usuario();
                usuario.setId(cursor.getColumnIndex(COLUNA_ID));
                usuario.setPass(cursor.getString(cursor.getColumnIndex(COLUNA_SENHA)));
                usuario.setUsuario(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                usuarios.add(usuario);
            } while (cursor.moveToNext());

        }
        return usuarios;
    }

    public Usuario getBy(String nome, String senha) {
        SQLiteDatabase db = banco.getWritableDatabase();
        String colunas[] = {COLUNA_ID, COLUNA_NOME, COLUNA_SENHA};
        String where = "usuario = '" + nome + "' and senha = '" + senha + "'";
        Cursor cursor = db.query(true, TABELA_USUARIOS, colunas, where, null, null, null, null, null);
        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            usuario = new Usuario();
            usuario.setId(cursor.getColumnIndex(COLUNA_ID));
            usuario.setPass(cursor.getString(cursor.getColumnIndex(COLUNA_SENHA)));
            usuario.setUsuario(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
        }
        return usuario;
    }


    public boolean loginCreated(String nome, String senha) {
        boolean isCreated = false;
        Usuario u = getBy(nome, senha);
        if (u != null) {
            isCreated = true;
        }
        return isCreated;
    }

    public boolean insertNew(String nome, String senha) {
        boolean createSuccessful = false;
        if (!loginCreated(nome, senha)) {
            SQLiteDatabase db = banco.getWritableDatabase();
            String colunas = "usuario,senha";
            ContentValues values = new ContentValues();


            try {
                SQLiteStatement stmt = db.compileStatement("INSERT INTO usuarios(usuario,senha) VALUES(?,?)");
                stmt.bindString(1, nome);
                stmt.bindString(2, senha);
                long rowId = stmt.executeInsert();
                createSuccessful = true;
                db.setTransactionSuccessful();
                db.close();
            } catch (Exception e) {

            }
        } else {
            createSuccessful = true;
        }


        return createSuccessful;
    }
}
