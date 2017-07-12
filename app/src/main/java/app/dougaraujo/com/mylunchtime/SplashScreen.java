package app.dougaraujo.com.mylunchtime;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_SCREEN_DISPLAY_LENGTH = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        carregar();
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
