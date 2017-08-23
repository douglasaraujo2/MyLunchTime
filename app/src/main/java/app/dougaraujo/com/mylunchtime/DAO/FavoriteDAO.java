package app.dougaraujo.com.mylunchtime.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.LinkedList;
import java.util.List;

import app.dougaraujo.com.mylunchtime.model.Favorite;

/**
 * Created by Dux-Douglas2 on 20/08/2017.
 */

public class FavoriteDAO {

    public static final String TABELA_FAVORITOS = "favoritos";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_CEP = "postalcode";
    public static final String COLUNA_LATIT = "latitude";
    public static final String COLUNA_LOGINT = "longitude";
    public static final String COLUNA_TELE = "telefone";
    private DBOpenHelper banco;

    public FavoriteDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    public List<Favorite> getAll() {
        List<Favorite> favorites = new LinkedList<>();
        String query = "SELECT * FROM " + TABELA_FAVORITOS;
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Favorite favorite = null;
        if (cursor.moveToFirst()) {
            do {
                favorite = new Favorite();
                favorite.setId(cursor.getLong(cursor.getColumnIndex("id")));
                favorite.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                favorite.setCep(cursor.getString(cursor.getColumnIndex(COLUNA_CEP)));
                //favorite.setLatitude(cursor.getString(cursor.getColumnIndex(COLUNA_LATIT)));
                //favorite.setLongitude(cursor.getString(cursor.getColumnIndex(COLUNA_LOGINT)));
                favorite.setTelefone(cursor.getString(cursor.getColumnIndex(COLUNA_TELE)));
                favorites.add(favorite);
            } while (cursor.moveToNext());

        }
        return favorites;
    }

    public void deleteFavorite(long id) {
        SQLiteDatabase db = banco.getWritableDatabase();
        try {
            SQLiteStatement stmt = db.compileStatement("DELETE FROM favoritos WHERE id = ?");
            //stmt.bindString(1, name);
            stmt.bindLong(1, id);
            stmt.execute();
            db.setTransactionSuccessful();
            db.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public void insertNew(String name, String postalCode, String phone) {
        SQLiteDatabase db = banco.getWritableDatabase();
        try {
            SQLiteStatement stmt = db.compileStatement("INSERT INTO favoritos(nome,postalcode,telefone) VALUES(?,?,?)");
            stmt.bindString(1, name);
            stmt.bindString(2, postalCode);
            stmt.bindString(3, phone);
            long rowId = stmt.executeInsert();
            db.setTransactionSuccessful();
            db.close();
        } catch (Exception e) {
            throw e;
        }

    }
}
