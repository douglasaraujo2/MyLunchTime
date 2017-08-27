package app.dougaraujo.com.mylunchtime;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
        favorites = favoriteDAO.getAll();
        LatLng position = null;
        Favorite fav;
        for (int i = 0; i < favorites.size(); i++) {
            fav = favorites.get(i);
            position = new LatLng(Double.parseDouble(fav.getLatitude()), Double.parseDouble(fav.getLongitude()));
            map.addMarker(new MarkerOptions()
                    .position(position)
                    .title(fav.getNome())
            );
            //LatLng minhaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());
            //mMap.addMarker(new MarkerOptions().position(minhaLocalizacao).title("Estou aqui"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaLocalizacao, 17));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
        }
    }
}
