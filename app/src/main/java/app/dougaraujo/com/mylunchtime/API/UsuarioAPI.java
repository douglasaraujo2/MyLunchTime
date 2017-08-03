package app.dougaraujo.com.mylunchtime.API;

import app.dougaraujo.com.mylunchtime.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dux-Douglas2 on 15/07/2017.
 */

public interface UsuarioAPI {
    @GET("58b9b1740f0000b614f09d2f")
    Call<User> getUser();
}
