package app.dougaraujo.com.mylunchtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import app.dougaraujo.com.mylunchtime.DAO.UsuarioDAO;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout tilNewUser;
    private TextInputLayout tilNewPass;
    private String name;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tilNewUser = (TextInputLayout) findViewById(R.id.tilNewLogin);
        tilNewPass = (TextInputLayout) findViewById(R.id.tilNewPass);
    }

    public void register(View view) {
        name = tilNewUser.getEditText().getText().toString();
        pass = tilNewPass.getEditText().getText().toString();
        boolean isError = false;
        if (!name.isEmpty()) {

        } else {
            tilNewUser.setError(getText(R.string.fill_name));
            isError = true;
        }
        if (!pass.isEmpty()) {

        } else {
            tilNewPass.setError(getText(R.string.fill_name));
            isError = true;
        }
        if (!isError) {
            UsuarioDAO usuario = new UsuarioDAO(this);
            boolean isInsert = usuario.insertNew(name, pass);
            if (!isInsert) {

                Toast.makeText(this, "Usuário não cadastrado", Toast.LENGTH_SHORT);
            } else {
                RegisterActivity.this.finish();
            }

        }
    }

    @Override
    public void finish() {
        Intent ret = new Intent();
        ret.putExtra("LOGIN", name);
        setResult(RESULT_OK, ret);
        super.finish();
    }
}
