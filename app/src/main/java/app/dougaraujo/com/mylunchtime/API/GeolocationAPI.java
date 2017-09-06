package app.dougaraujo.com.mylunchtime.API;

import java.util.Map;

import app.dougaraujo.com.mylunchtime.model.ResultsModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Dux-Douglas2 on 01/09/2017.
 */

public interface GeolocationAPI {
    @GET("maps/api/geocode/json")
    Call<ResultsModel> getResults(@QueryMap Map<String, String> options);


}
