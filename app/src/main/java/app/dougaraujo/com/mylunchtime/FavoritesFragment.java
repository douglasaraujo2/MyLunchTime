package app.dougaraujo.com.mylunchtime;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;
import app.dougaraujo.com.mylunchtime.adapter.FavoriteAdapter;
import app.dougaraujo.com.mylunchtime.adapter.OnItemClickListener;
import app.dougaraujo.com.mylunchtime.adapter.SwipeHelper;
import app.dougaraujo.com.mylunchtime.model.Favorite;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    RecyclerView rvFavorites;
    TextView tvEmpty;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE};
    private FavoriteAdapter favoriteAdapter;
    private List<Favorite> favorites;

    public FavoritesFragment() {
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
        View itemView = inflater.inflate(R.layout.fragment_favorites, container, false);
        rvFavorites = (RecyclerView) itemView.findViewById(R.id.rvFavorites);
        tvEmpty = (TextView) itemView.findViewById(R.id.tvEmpty);

        favoriteAdapter = new FavoriteAdapter(new ArrayList<Favorite>(), new OnItemClickListener() {
            @Override
            public void onItemClick(Favorite fav) {
                if (!hasPermissions(getActivity(), PERMISSIONS)) {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
                }
                PERMISSION_ALL = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
                if (PERMISSION_ALL == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + fav.getTelefone()));
                    startActivity(intent);
                }

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//;        itemTouchHelper.attachToRecyclerView(rvFavorites);
        rvFavorites.setLayoutManager(layoutManager);
        rvFavorites.setAdapter(favoriteAdapter);
        rvFavorites.setHasFixedSize(true);
        loadData();
        initSwipe();
        return itemView;

    }

    private void loadData() {
        FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
        favorites = favoriteDAO.getAll();
        if (favorites.isEmpty()) {
            rvFavorites.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvFavorites.setVisibility(View.VISIBLE);
        }
        favoriteAdapter.update(favorites);
    }

    private void initSwipe() {
        SwipeHelper swipeHelper = new SwipeHelper(getActivity(), rvFavorites) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        getResources().getString(R.string.txt_delete),
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Favorite favorito = favorites.get(pos);
                                FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
                                favoriteDAO.deleteFavorite(favorito.getId());
                                //favorites.remove(pos);
                                loadData();
                                Toast.makeText(getActivity(), "Registro deletado com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        getResources().getString(R.string.txt_edit),
                        0,
                        Color.parseColor("#025bff"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Favorite favorito = favorites.get(pos);
                                FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
                                favorito = favoriteDAO.selectSingleFavorite(favorito.getId());
                                EditFavoriteFragment editFavoriteFragment = new EditFavoriteFragment();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                Bundle data = new Bundle();
                                //data.putString("dados",new Gson().toJson(favorito));
                                //data.putSerializable("dados", (Serializable) favorito);
                                data.putParcelable("dados", favorito);
                                //editFavoriteFragment.set
                                editFavoriteFragment.setArguments(data);
                                transaction.replace(R.id.content_main, editFavoriteFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();

                            }
                        }
                ));
            }
        };
    }
}

//https://maps.googleapis.com/maps/api/geocode/json?address=Avenida%20Prefeito%20Paulo%20Lauro,264&key=
