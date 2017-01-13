package michelsmartproduction.getmeback;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class Menu extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    AlertDialog.Builder dlgAlert;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;


    private long INTERVAL = 10 * 1000;  // 10 secs
    private long FASTEST_INTERVAL = 2000; // 2 sec
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    public String lat = "";
    public String lon = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu, container, false);

        final TinyDB tinydb = new TinyDB(getActivity());

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }
        createLocationRequest();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        dlgAlert = new AlertDialog.Builder(getActivity());
        final Button listButton = (Button) view.findViewById(R.id.listButton);
        final Button posButton = (Button) view.findViewById(R.id.savePos);
        final Button goButton = (Button) view.findViewById(R.id.getBack);


        assert listButton != null;
        assert posButton != null;


        listButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ListeAddresses AddressFragment = new ListeAddresses();
                fragmentTransaction.setCustomAnimations(R.anim.slide_top, R.anim.slide_bot);
                fragmentTransaction.replace(R.id.activity_main, AddressFragment);
                fragmentTransaction.commit();
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_right);
                Choix1 choix = new Choix1();
                fragmentTransaction.replace(R.id.activity_main, choix);
                fragmentTransaction.commit();
            }
        });

        posButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                }
                startLocationUpdates();
                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (mLastLocation != null) {
                    tinydb.putDouble("lastLat", mLastLocation.getLatitude());
                    tinydb.putDouble("lastLong", mLastLocation.getLongitude());
                    dlgAlert.setTitle("Position récupérée");
                    dlgAlert.setMessage("Votre position a été correctement enregistrée");
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                } else {
                    dlgAlert.setTitle("Erreur");
                    dlgAlert.setMessage("Activez votre GPS ou réessayez dans quelques instants");
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
             }
        });

        return view;
    }

    public void onConnected(@Nullable Bundle bundle) {

    }


    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

}
