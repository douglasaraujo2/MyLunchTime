package app.dougaraujo.com.mylunchtime.API;

/**
 * Created by Dux-Douglas2 on 15/07/2017.
 */

public class APIUtils {
    public static final String BASE_URL = "http://www.mocky.io/";

    public static UsuarioAPI getLinhaAPIVersion() {
        return RetrofitClient.getClient(BASE_URL).create(UsuarioAPI.class);
    }
}
