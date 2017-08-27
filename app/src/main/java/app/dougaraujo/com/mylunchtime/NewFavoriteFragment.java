package app.dougaraujo.com.mylunchtime;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFavoriteFragment extends Fragment implements View.OnClickListener {
    protected double latitude, longitude;
    protected LocationManager locationManager;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private EditText etName;
    private EditText etPostalCode;
    private EditText etPhone;
    private EditText etAddress;
    private TextInputLayout tilName;
    private TextInputLayout tilPostalCode;
    private TextInputLayout tilPhone;
    private Button btnNewFavorite;
    public NewFavoriteFragment() {
        // Required empty public constructor


    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!hasPermissions(getActivity(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }
        View itemView = inflater.inflate(R.layout.fragment_new_favorite, container, false);
        // Inflate the layout for this fragment
        etName = (EditText) itemView.findViewById(R.id.etName);
        etPostalCode = (EditText) itemView.findViewById(R.id.etPostalCode);
        etPhone = (EditText) itemView.findViewById(R.id.etPhoneNumber);
        etAddress = (EditText) itemView.findViewById(R.id.etAddress);
        tilName = (TextInputLayout) itemView.findViewById(R.id.tilName);
        tilPostalCode = (TextInputLayout) itemView.findViewById(R.id.tilPostalCode);
        tilPhone = (TextInputLayout) itemView.findViewById(R.id.tilPhoneNumber);
//        btnNewFavorite = (Button) itemView.findViewById(R.id.btnNewFavorite);
//        btnNewFavorite.setOnClickListener(this);
        btnNewFavorite = (Button) itemView.findViewById(R.id.btnNewFavorite);
        btnNewFavorite.setOnClickListener(this);

        etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        return itemView;

    }

    public void clearFields() {
        etName.setText("");
        etPostalCode.setText("");
        etPhone.setText("");
        etAddress.setText("");
    }

    public void saveNew(View view) {
        FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
        String name = etName.getText().toString();
        String code = etPostalCode.getText().toString();
        String phone = etPhone.getText().toString();
        try {
            PERMISSION_ALL = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
            //ContextCompat.checkSelfPermission(getActivity())
            if (PERMISSION_ALL == PackageManager.PERMISSION_GRANTED) {
                Criteria criteria = new Criteria();
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                String provider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(provider);
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            }
            String lat = new Double(latitude).toString();
            String longi = new Double(longitude).toString();
            favoriteDAO.insertNew(name, code, phone, lat, longi);
            Toast.makeText(this.getActivity(), "Favorito adicionado com sucesso", Toast.LENGTH_SHORT).show();
            clearFields();
        } catch (Error e) {
            Toast.makeText(this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewFavorite:
                saveNew(v);
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
