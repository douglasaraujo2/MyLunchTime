package app.dougaraujo.com.mylunchtime;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        favoriteDAO.insertNew(name, code, phone);
        Toast.makeText(this.getActivity(), "Favorito adicionado com sucesso", Toast.LENGTH_SHORT);
        clearFields();

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
