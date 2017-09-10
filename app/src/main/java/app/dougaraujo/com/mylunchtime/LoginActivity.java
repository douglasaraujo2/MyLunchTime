package app.dougaraujo.com.mylunchtime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import app.dougaraujo.com.mylunchtime.DAO.UsuarioDAO;
import app.dougaraujo.com.mylunchtime.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    private TextInputLayout tilLogin;
    private TextInputLayout tilPass;
    private CheckBox isKeep;
    private EditText etLogin;
    private LoginButton loginButton;


    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
        tilPass = (TextInputLayout) findViewById(R.id.tilPass);
        etLogin = (EditText) findViewById(R.id.etLogin);
        isKeep = (CheckBox) findViewById(R.id.cbKeep);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        if (Profile.getCurrentProfile() != null) {
            String nome = Profile.getCurrentProfile().getName();
            SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("username", nome);
            editor.apply();
            navegarViewPrincipal();
            return;
        }


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "",
                        getString(R.string.txt_wait), true);
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {


                                // Application code
                                try {
                                    String email = object.getString("email");
                                    String nome = object.getString("name");
                                    SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("username", nome);

                                    editor.apply();
                                    navegarViewPrincipal();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
                dialog.hide();
            }
            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        if (isConectado()) {
            navegarViewPrincipal();
        }
    }

    public void navegarViewPrincipal() {
        Intent principal = new Intent(this, MainActivity.class);
        startActivity(principal);
    }

    private boolean isConectado() {
        SharedPreferences shared = getSharedPreferences("info", MODE_PRIVATE);
        String login = shared.getString("login", "");
        return !login.equals("");
    }

    public void logar(View view) {
        String usuario = tilLogin.getEditText().getText().toString();
        String senha = tilPass.getEditText().getText().toString();
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuarioObj = new Usuario();
        ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "",
                getString(R.string.txt_wait), true);
        usuarioObj = usuarioDAO.getBy(usuario, senha);
        if (usuarioObj != null) {
            SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            if (isKeep.isChecked()) {
                editor.putString("login", usuario);

            }
            editor.putString("username", usuario);
            editor.apply();
            dialog.hide();
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("LOGIN")) {
                etLogin.setText(data.getExtras().getString("LOGIN"));
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
