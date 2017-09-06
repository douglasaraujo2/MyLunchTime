package app.dougaraujo.com.mylunchtime.API;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dux-Douglas2 on 15/07/2017.
 */

class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
//        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
//        }
        return retrofit;
    }
}
