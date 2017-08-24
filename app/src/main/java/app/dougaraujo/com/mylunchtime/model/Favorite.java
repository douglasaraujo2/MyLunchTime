package app.dougaraujo.com.mylunchtime.model;

/**
 * Created by Dux-Douglas2 on 20/08/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    public static final Parcelable.Creator<Favorite>
            CREATOR = new Parcelable.Creator<Favorite>() {

        public Favorite createFromParcel(Parcel in) {
            return null;
        }

        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
    private String nome;
    private String cep;
    private String latitude;
    private String longitude;
    private String telefone;
    private long id;

    public Favorite() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cep);
        dest.writeLong(id);
        dest.writeString(telefone);
        dest.writeString(nome);
    }
}
