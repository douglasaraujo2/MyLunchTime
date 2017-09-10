package app.dougaraujo.com.mylunchtime;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.ArrayList;
import java.util.List;

import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;
import app.dougaraujo.com.mylunchtime.adapter.FavoriteAdapter;
import app.dougaraujo.com.mylunchtime.model.Favorite;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    RecyclerView rvFavorites;
    TextView tvEmpty;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE};
    ShareButton shareButton;
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
        if (!hasPermissions(getActivity(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }
        //
        favoriteAdapter = new FavoriteAdapter(new ArrayList<Favorite>());
//                , new OnItemClickListener() {
//            @Override
//            public void onItemClick(Favorite fav) {
////                Intent shareIntent = new Intent(Intent.ACTION_SEND);
////                shareIntent.setType("text/plain");
//                String textShare = "Restaurante de Hoje: ";
//                textShare += fav.getNome();
//                textShare += "/n Para quem quiser o endereço: "+fav.getEnd();
//                textShare += "/n Telefone: "+fav.getTelefone();
////                shareIntent.putExtra(Intent.EXTRA_TEXT, textShare);

//                String uri = "http://maps.google.com/maps?saddr=" +fav.getLatitude()+","+fav.getLongitude();
//
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
////                String ShareSub = "Here is my location";
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, textShare);
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
//                startActivity(Intent.createChooser(sharingIntent, getString(R.string.app_name)));
////                startActivity(Intent.createChooser(shareIntent, getString(R.string.app_name)));
//            }
//        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//;        itemTouchHelper.attachToRecyclerView(rvFavorites);
        rvFavorites.setLayoutManager(layoutManager);
        rvFavorites.setAdapter(favoriteAdapter);
        rvFavorites.setHasFixedSize(true);

        loadData();
        return itemView;

    }

    public void loadData() {
        FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences("info", getActivity().MODE_PRIVATE);
        String usuario = pref.getString("username", "");
        favorites = favoriteDAO.getAll(usuario);
        if (favorites.isEmpty()) {
            rvFavorites.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvFavorites.setVisibility(View.VISIBLE);
        }
        favoriteAdapter.update(favorites);
    }


    public void shareContent(View view) {

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();


    }
}

