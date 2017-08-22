package app.dougaraujo.com.mylunchtime;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    private EditText etName;
    private EditText etPostalCode;
    private EditText etPhone;
    private TextInputLayout tilName;
    private TextInputLayout tilPostalCode;
    private TextInputLayout tilPhone;
    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        etName = (EditText) container.findViewById(R.id.etName);
        etPostalCode = (EditText) container.findViewById(R.id.etPostalCode);
        etPhone = (EditText) container.findViewById(R.id.etPhoneNumber);
        tilName = (TextInputLayout) container.findViewById(R.id.tilName);
        tilPostalCode = (TextInputLayout) container.findViewById(R.id.tilPostalCode);
        tilPhone = (TextInputLayout) container.findViewById(R.id.tilPhoneNumber);
        return inflater.inflate(R.layout.fragment_favorites, container, false);

    }

    public void clearFields() {
        etName.setText("");
        etPostalCode.setText("");
        etPhone.setText("");
    }

    public void saveNew(View view) {
        FavoriteDAO favoriteDAO = new FavoriteDAO(this.getActivity());
        String name = etName.getText().toString();
        String code = etPostalCode.getText().toString();
        String phone = etPhone.getText().toString();
//        boolean isError = false;
//        if(name == ""){
//            Toast.makeText(this.getActivity(),"Preencha o nome",Toast.LENGTH_SHORT);
//
//            tilName.setError(((String) R.string.error_name));
//            isError = true;
//        }
        favoriteDAO.insertNew(name, code, phone);
        Toast.makeText(this.getActivity(), "Favorito adicionado com sucesso", Toast.LENGTH_SHORT);
        clearFields();

    }
}
