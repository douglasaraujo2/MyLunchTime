package app.dougaraujo.com.mylunchtime.API;

/**
 * Created by Dux-Douglas2 on 15/07/2017.
 */

public class APIUtils {
    public static String BASE_URL = "";

    public static UsuarioAPI getLinhaAPIVersion(String url) {
        BASE_URL = url;
        return RetrofitClient.getClient(url).create(UsuarioAPI.class);
    }

    public static GeolocationAPI getLinhaAPIVersionGeo(String url) {
        BASE_URL = url;
        return RetrofitClient.getClient(url).create(GeolocationAPI.class);
    }
}
