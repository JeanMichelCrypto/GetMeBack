package michelsmartproduction.getmeback;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Plus extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plus, container, false);

        TextView adresse = (TextView) view.findViewById(R.id.adresse);
        Button suppr = (Button) view.findViewById(R.id.supprimer);
        Button retour = (Button) view.findViewById(R.id.retour2);

        final TinyDB tinydb = new TinyDB(getActivity());
        final ArrayList<String> liste = tinydb.getListString("listAddr");
        final int id = getArguments().getInt("id");
        String adr = liste.get(id);

        adresse.setText(adr);

        retour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ListeAddresses listeAdr = new ListeAddresses();
                fragmentTransaction.add(R.id.activity_main, listeAdr);
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
                ListeAddresses listeAdr = new ListeAddresses();
                fragmentTransaction.add(R.id.activity_main, listeAdr);
                fragmentTransaction.commit();
            }
        });

        return view;

    }

}
