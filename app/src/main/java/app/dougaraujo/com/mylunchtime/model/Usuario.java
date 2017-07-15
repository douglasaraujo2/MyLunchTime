package app.dougaraujo.com.mylunchtime.model;

/**
 * Created by Dux-Douglas2 on 15/07/2017.
 */

public class Usuario {
    private String usuario;
    private String pass;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
