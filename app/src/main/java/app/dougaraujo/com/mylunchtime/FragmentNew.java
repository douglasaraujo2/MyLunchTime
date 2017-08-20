package app.dougaraujo.com.mylunchtime;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;
import app.dougaraujo.com.mylunchtime.adapter.FavoriteAdapter;
import app.dougaraujo.com.mylunchtime.model.Favorite;

/**
 * Created by Dux-Douglas2 on 20/08/2017.
 */

public class FragmentNew extends Fragment implements View.OnClickListener {
    RecyclerView rvFavorites;
    private TextView tvName;
    private TextView tvAdress;
    private FavoriteAdapter favoriteAdapter;

    public FragmentNew() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_new_favorite, container, false);
        rvFavorites = (RecyclerView) itemView.findViewById(R.id.rvFavorites);
        favoriteAdapter = new FavoriteAdapter(new ArrayList<Favorite>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        rvFavorites.setLayoutManager(layoutManager);
        rvFavorites.setHasFixedSize(true);
        rvFavorites.setAdapter(favoriteAdapter);
        loadData();
        return itemView;
    }

    @Override

    public void onClick(View v) {

    }

    private void loadData() {
        FavoriteDAO favoriteDAO = new FavoriteDAO(this.getActivity());
        List<Favorite> favorites = favoriteDAO.getAll();
        favoriteAdapter.update(favorites);

    }
}
