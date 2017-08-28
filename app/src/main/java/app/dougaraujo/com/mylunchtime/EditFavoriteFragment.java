package app.dougaraujo.com.mylunchtime;


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

import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;
import app.dougaraujo.com.mylunchtime.model.Favorite;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFavoriteFragment extends Fragment implements View.OnClickListener {
    private Favorite fav;
    private EditText etName;
    private EditText etPostalCode;
    private EditText etPhone;
    private EditText etAddress;
    private TextInputLayout tilName;
    private TextInputLayout tilPostalCode;
    private TextInputLayout tilPhone;
    private Button btnNewFavorite;

    public EditFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_edit_favorite, container, false);
        fav = getArguments().getParcelable("dados");
        etName = (EditText) itemView.findViewById(R.id.etName);
        etPhone = (EditText) itemView.findViewById(R.id.etPhoneNumber);
        tilName = (TextInputLayout) itemView.findViewById(R.id.tilName);
        tilPhone = (TextInputLayout) itemView.findViewById(R.id.tilPhoneNumber);

        etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        btnNewFavorite = (Button) itemView.findViewById(R.id.btnNewFavorite);
        btnNewFavorite.setOnClickListener(this);
        fillFields();
        return itemView;
    }

    public void fillFields() {
        etName.setText(fav.getNome());
        etPhone.setText(fav.getTelefone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewFavorite:
                try {
                    saveUpdate(v);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                break;

        }

    }

    public void clearFields() {
        etName.setText("");
        etPhone.setText("");
    }

    public void saveUpdate(View view) throws Throwable {
        String nome = etName.getText().toString();
        String phone = etPhone.getText().toString();
//        String cep = etPostalCode.getText().toString();
        if (nome.isEmpty()) {
            tilName.setError(getString(R.string.fill_name));
            tilName.setErrorEnabled(true);
            return;
        } else {
            tilName.setError("");
            tilName.setErrorEnabled(false);
        }

        if (phone.isEmpty()) {
            tilPhone.setError(getString(R.string.fill_phone));
            tilPhone.setErrorEnabled(true);
            return;
        } else {
            tilPhone.setError("");
            tilPhone.setErrorEnabled(false);

        }
        FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
        favoriteDAO.updateFavorite(nome, phone, fav.getId());
        clearFields();
        Toast.makeText(getActivity(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
        //getActivity().getFragmentManager().popBackStack();
        getFragmentManager().popBackStackImmediate();
    }


}
