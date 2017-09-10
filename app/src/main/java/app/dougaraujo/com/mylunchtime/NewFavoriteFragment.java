package app.dougaraujo.com.mylunchtime;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import app.dougaraujo.com.mylunchtime.API.APIUtils;
import app.dougaraujo.com.mylunchtime.API.GeolocationAPI;
import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;
import app.dougaraujo.com.mylunchtime.model.ResultsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFavoriteFragment extends Fragment implements View.OnClickListener {
    protected double latitude, longitude;
    ProgressDialog dialog;
    private EditText etName;
    private EditText etPhone;
    private EditText etAddress;
    private TextInputLayout tilName;
    private TextInputLayout tilAddress;
    private TextInputLayout tilPhone;
    private Button btnNewFavorite;
    private GeolocationAPI geolocationAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_new_favorite, container, false);
        // Inflate the layout for this fragment
        etName = (EditText) itemView.findViewById(R.id.etName);
        etPhone = (EditText) itemView.findViewById(R.id.etPhoneNumber);
        etAddress = (EditText) itemView.findViewById(R.id.etAddress);
        tilName = (TextInputLayout) itemView.findViewById(R.id.tilName);
        tilAddress = (TextInputLayout) itemView.findViewById(R.id.tilAddress);
        tilPhone = (TextInputLayout) itemView.findViewById(R.id.tilPhoneNumber);
        btnNewFavorite = (Button) itemView.findViewById(R.id.btnNewFavorite);
        btnNewFavorite.setOnClickListener(this);

        etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        return itemView;

    }

    public void getData(final String name, final String address, final String phone) {
        geolocationAPI = APIUtils.getLinhaAPIVersionGeo(getString(R.string.txt_maps_url));
        final FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
//        final boolean[] isResponse = {false};
        Map<String, String> data = new HashMap<>();
        data.put("address", address);
        data.put("key", getString(R.string.maps_key_webservice));
        geolocationAPI.getResults(data).enqueue(new Callback<ResultsModel>() {
            @Override
            public void onResponse(Call<ResultsModel> call, Response<ResultsModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        latitude = response.body().getResults().get(0).getGeometry().getLocation().getLat();
                        longitude = response.body().getResults().get(0).getGeometry().getLocation().getLng();
                        String lat = new Double(latitude).toString();
                        String longi = new Double(longitude).toString();
                        SharedPreferences pref = getActivity().getSharedPreferences("info", getActivity().MODE_PRIVATE);
                        String usuario = pref.getString("username", "");
                        favoriteDAO.insertNew(name, address, phone, lat, longi, usuario);
                        Toast.makeText(getActivity(), R.string.txt_added, Toast.LENGTH_SHORT).show();
                        clearFields();
                        dialog.hide();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultsModel> call, Throwable t) {
                dialog.hide();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void clearFields() {
        etName.setText("");
        etPhone.setText("");
        etAddress.setText("");
    }

    public void saveNew(View view) {

        String name = etName.getText().toString();
        String address = etAddress.getText().toString();
        String phone = etPhone.getText().toString();
        if (name.isEmpty()) {
            tilName.setError(getString(R.string.fill_name));
            tilName.setErrorEnabled(true);
            return;
        } else {
            tilName.setError("");
            tilName.setErrorEnabled(false);
        }
        if (address.isEmpty()) {
            tilAddress.setError(getString(R.string.fill_postal));
            tilAddress.setErrorEnabled(true);
            return;
        } else {
            tilAddress.setError("");
            tilAddress.setErrorEnabled(false);
        }
        if (phone.isEmpty()) {
            tilPhone.setError(getString(R.string.fill_phone));
            tilPhone.setErrorEnabled(true);
            return;
        } else {
            tilPhone.setError("");
            tilPhone.setErrorEnabled(false);

        }
        try {
            dialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.txt_wait), true);
            getData(name, address, phone);
        } catch (Error e) {
            Toast.makeText(this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewFavorite:
                saveNew(v);
                break;
        }

    }


    public interface OnFragmentInteractionListener {
    }
}
