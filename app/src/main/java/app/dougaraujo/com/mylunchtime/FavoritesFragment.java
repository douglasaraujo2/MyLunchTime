package app.dougaraujo.com.mylunchtime;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;
import app.dougaraujo.com.mylunchtime.adapter.FavoriteAdapter;
import app.dougaraujo.com.mylunchtime.model.Favorite;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    RecyclerView rvFavorites;
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            Toast.makeText(getActivity(), new String(String.valueOf(swipeDir)), Toast.LENGTH_SHORT).show();
        }
    };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
    private FavoriteAdapter favoriteAdapter;
    private List<Favorite> favorites;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_favorites, container, false);
        rvFavorites = (RecyclerView) itemView.findViewById(R.id.rvFavorites);
        FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
        favorites = favoriteDAO.getAll();
        favoriteAdapter = new FavoriteAdapter(favorites);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        itemTouchHelper.attachToRecyclerView(rvFavorites);
        rvFavorites.setLayoutManager(layoutManager);
        rvFavorites.setAdapter(favoriteAdapter);
        rvFavorites.setHasFixedSize(true);

        favoriteAdapter.update(favorites);
        return itemView;

    }


}
