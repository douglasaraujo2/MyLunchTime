package app.dougaraujo.com.mylunchtime.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public static final String COLUNA_ENDERECO = "endereco";
    public static final String COLUNA_LATIT = "latitude";
    public static final String COLUNA_LOGINT = "longitude";
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
                favorite.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                favorite.setEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO)));
                favorite.setLatitude(cursor.getString(cursor.getColumnIndex(COLUNA_LATIT)));
                favorite.setLongitude(cursor.getString(cursor.getColumnIndex(COLUNA_LOGINT)));
                favorites.add(favorite);
            } while (cursor.moveToNext());

        }
        return favorites;
    }
}
