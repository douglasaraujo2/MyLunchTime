package app.dougaraujo.com.mylunchtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import app.dougaraujo.com.mylunchtime.DAO.UsuarioDAO;
import app.dougaraujo.com.mylunchtime.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilLogin;
    private TextInputLayout tilPass;
    private CheckBox isKeep;
    private EditText etLogin;

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getDefault(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
        tilPass = (TextInputLayout) findViewById(R.id.tilPass);
        etLogin = (EditText) findViewById(R.id.etLogin);
        isKeep = (CheckBox) findViewById(R.id.cbKeep);
        if (isConectado()) {
            navegarViewPrincipal();
        }

    }

    public void navegarViewPrincipal() {
        Intent principal = new Intent(this, MainActivity.class);
        startActivity(principal);
    }

    private boolean isConectado() {
//        SharedPreferences shared = getSharedPreferences("info", MODE_PRIVATE);

        String login = getDefault("login", this);
        return !login.equals("");
    }

    public void logar(View view) {
        String usuario = tilLogin.getEditText().getText().toString();
        String senha = tilPass.getEditText().getText().toString();
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuarioObj = new Usuario();
        usuarioObj = usuarioDAO.getBy(usuario, senha);
        if (usuarioObj != null) {
            if (isKeep.isChecked()) {
//                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("LOGIN", usuario);
//                editor.apply();
                setDefaults("login", usuario, this);
            }
            navegarViewPrincipal();

            //LoginActivity.this.finish();
        } else {
            Toast.makeText(this, "Usuário e/ou senha inválidos", Toast.LENGTH_LONG).show();
        }
    }

    public void register(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivityForResult(i, 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("LOGIN")) {
                etLogin.setText(data.getExtras().getString("LOGIN"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
