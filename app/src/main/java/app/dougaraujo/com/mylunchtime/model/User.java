package app.dougaraujo.com.mylunchtime.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dux-Douglas2 on 15/07/2017.
 */

public class User {
    @SerializedName("usuario")
    private String usuario;
    @SerializedName("senha")
    private String senha;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
