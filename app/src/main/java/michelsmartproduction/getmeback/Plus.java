package michelsmartproduction.getmeback;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Plus extends Fragment   { //implements OnMapReadyCallback

    private GoogleMap mGoogleMap;
    MapView mMapView;
    //View view;
    String addressTitle;
    double lat = 0;
    double lon = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plus, container, false);

        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        TextView adresse = (TextView) view.findViewById(R.id.adresse);
        Button suppr = (Button) view.findViewById(R.id.supprimer);
        Button retour = (Button) view.findViewById(R.id.retour2);


        final TinyDB tinydb = new TinyDB(getActivity());
        final ArrayList<String> liste = tinydb.getListString("listAddr");
        final int id = getArguments().getInt("id");
        String adr = liste.get(id);
        addressTitle=adr;


        adresse.setText(adr);

        retour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_top, R.anim.slide_bot);
                ListeAddresses listeAdr = new ListeAddresses();
                fragmentTransaction.replace(R.id.activity_main, listeAdr);
                fragmentTransaction.commit();
            }
        });

        suppr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                liste.remove(id);
                tinydb.putListString("listAddr", liste);

                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_top, R.anim.slide_bot);
                ListeAddresses listeAdr = new ListeAddresses();
                fragmentTransaction.replace(R.id.activity_main, listeAdr);
                fragmentTransaction.commit();
            }
        });


        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> address = geoCoder.getFromLocationName(adr, 1);
            lat = address.get(0).getLatitude();
            lon = address.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;


                // For dropping a marker at a point on the Map
                LatLng loc = new LatLng(lat, lon);
                mGoogleMap.addMarker(new MarkerOptions().position(loc).title(addressTitle).snippet(""));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(18).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return view;


    }




   /* public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getActivity());
        mGoogleMap=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions()
        .position(new LatLng(10,10))
        .title(address));
        CameraPosition basis = CameraPosition.builder()
                .target(new LatLng(10 ,10)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(basis));

    }*/
}
