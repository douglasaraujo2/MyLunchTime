package app.dougaraujo.com.mylunchtime;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import app.dougaraujo.com.mylunchtime.DAO.FavoriteDAO;
import app.dougaraujo.com.mylunchtime.model.Favorite;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {
    //    FrameLayout frameLayout;
    private List<Favorite> favorites;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_maps, container, false);
        // Inflate the layout for this fragment
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.frameMap, supportMapFragment).commit();
//        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
//                .findFragmentById();
//        mapFragment.getMapAsync(this);
        supportMapFragment.getMapAsync(this);
        return itemView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences("info", getActivity().MODE_PRIVATE);
        String usuario = pref.getString("username", "");
        favorites = favoriteDAO.getAll(usuario);
        LatLng position = null;
        Favorite fav;
        List<MarkerOptions> markers = new ArrayList<>();
        for (int i = 0; i < favorites.size(); i++) {
            fav = favorites.get(i);
            position = new LatLng(Double.parseDouble(fav.getLatitude()), Double.parseDouble(fav.getLongitude()));
            map.addMarker(new MarkerOptions()
                    .position(position)
                    .title(fav.getNome()));
            markers.add(i, new MarkerOptions()
                    .position(position)
                    .title(fav.getNome()));
        }
        if (markers.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (MarkerOptions m : markers) {
                builder.include(m.getPosition());
            }
            int padding = 0; // offset from edges of the map in pixels
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdt;
            cameraUpdt = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            map.moveCamera(cameraUpdt);
        }

    }
}
