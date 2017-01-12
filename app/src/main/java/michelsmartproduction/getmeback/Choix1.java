package michelsmartproduction.getmeback;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

public class Choix1 extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.choix, container, false);

        final RadioButton pied = (RadioButton) view.findViewById(R.id.pied);
        final RadioButton velo = (RadioButton) view.findViewById(R.id.velo);
        final RadioButton voiture = (RadioButton) view.findViewById(R.id.voiture);
        Button lancer = (Button) view.findViewById(R.id.lancerMaps);
        Button retour = (Button) view.findViewById(R.id.choixRetour);
        TinyDB tinydb = new TinyDB(getActivity());
        final Double lat = tinydb.getDouble("lastLat", 0);
        final Double lon = tinydb.getDouble("lastLong", 0);

        lancer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mode = "w";
                if (pied.isChecked()) {
                    mode = "w";
                } else if (velo.isChecked()) {
                    mode = "b";
                } else if (voiture.isChecked()) {
                    mode = "d";
                }
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + String.valueOf(lat) + "," +
                        String.valueOf(lon) + "&mode=" + mode);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        retour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_top, R.anim.slide_bot);
                Menu menu = new Menu();
                fragmentTransaction.replace(R.id.activity_main, menu);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}