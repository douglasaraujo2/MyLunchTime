package app.dougaraujo.com.mylunchtime;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import app.dougaraujo.com.mylunchtime.API.APIUtils;
import app.dougaraujo.com.mylunchtime.API.UsuarioAPI;
import app.dougaraujo.com.mylunchtime.DAO.UsuarioDAO;
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
        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.INTERNET}, permissionCheck);

            }
        }
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if (!isConectado()) {
                loadData();
            } else {
                carregar();
            }

        } else {
            Toast.makeText(this, "Não é possível se conectar a internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void askPermission() {

    }

    private void loadData() {
        usuarioAPI = APIUtils.getLinhaAPIVersion();
        usuarioAPI.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    UsuarioDAO usuarioDAO = new UsuarioDAO(SplashScreen.this);
                    String nome = response.body().getUsuario();
                    String senha = response.body().getSenha();

                    boolean isInsert = false;
                    isInsert = usuarioDAO.insertNew(nome, senha);
                    if (isInsert) {
                        carregar();
                    } else {
                        Toast.makeText(SplashScreen.this, "Erro ao inserir usuários", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private boolean isConectado() {
        SharedPreferences shared = getSharedPreferences("login", MODE_PRIVATE);
        String login = shared.getString("login", "");
        return !login.equals("");
    }

    private void carregar() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);
        anim.reset();

        ImageView ivLogo = (ImageView) findViewById(R.id.splash);
        if (ivLogo != null) {
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
        }, SPLASH_SCREEN_DISPLAY_LENGTH);
    }
}
