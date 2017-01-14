package michelsmartproduction.getmeback;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AjouterAdr extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ajouter_adresse, container, false);

        final EditText ville = (EditText) view.findViewById(R.id.ville);
        final EditText rue = (EditText) view.findViewById(R.id.rue);
        final EditText num = (EditText) view.findViewById(R.id.num);
        michelsmartproduction.getmeback.CustomButton ajouter = (michelsmartproduction.getmeback.CustomButton) view.findViewById(R.id.ajouter);
        michelsmartproduction.getmeback.CustomButton retour = (michelsmartproduction.getmeback.CustomButton) view.findViewById(R.id.annuler);

        final TinyDB tinydb = new TinyDB(getActivity());
        ArrayList<String> liste = new ArrayList<String>();
        liste = tinydb.getListString("listAddr");

        retour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ListeAddresses listeAdr = new ListeAddresses();
                fragmentTransaction.setCustomAnimations(R.anim.slide_top, R.anim.slide_bot);
                fragmentTransaction.replace(R.id.activity_main, listeAdr);
                fragmentTransaction.commit();
            }
        });
        final ArrayList<String> finalListe = liste;
        ajouter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String adresse;
                adresse = String.valueOf(num.getText()) + " " + String.valueOf(rue.getText()) + " " + String.valueOf(ville.getText());
                finalListe.add(adresse);
                tinydb.putListString("listAddr", finalListe);
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_top, R.anim.slide_bot);
                ListeAddresses listeAdr = new ListeAddresses();
                fragmentTransaction.replace(R.id.activity_main, listeAdr);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
