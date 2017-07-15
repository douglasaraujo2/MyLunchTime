package app.dougaraujo.com.mylunchtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import app.dougaraujo.com.mylunchtime.API.APIUtils;
import app.dougaraujo.com.mylunchtime.API.UsuarioAPI;
import app.dougaraujo.com.mylunchtime.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_SCREEN_DISPLAY_LENGTH = 5000;
    private UsuarioAPI usuarioAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //loadData();
        carregar();
    }

    private void loadData() {
        usuarioAPI = APIUtils.getLinhaAPIVersion();
        usuarioAPI.getUser(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void carregar() {
        Animation anim  = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);
        anim.reset();

        ImageView ivLogo = (ImageView) findViewById(R.id.splash);
        if(ivLogo != null){
            ivLogo.clearAnimation();
            ivLogo.startAnimation(anim);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Após o tempo definido irá executar a próxima tela
                Intent intent = new Intent(SplashScreen.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashScreen.this.finish();

            }
        },SPLASH_SCREEN_DISPLAY_LENGTH);
    }
}
