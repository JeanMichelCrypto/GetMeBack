package michelsmartproduction.getmeback;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Lucas on 07/01/2017.
 */
public class Menu extends Fragment {

    AlertDialog.Builder dlgAlert ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)

        dlgAlert  = new AlertDialog.Builder(getActivity());
        final Button listButton = (Button) view.findViewById(R.id.listButton);
        final Button posButton = (Button) view.findViewById(R.id.savePos);

        assert listButton != null;
        assert posButton != null;

        listButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ListeAddresses AddressFragment = new ListeAddresses();
                fragmentTransaction.add(R.id.activity_main, AddressFragment);
                fragmentTransaction.commit();
            }
        });

        posButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dlgAlert.setMessage("This is an alert with no consequence");
                dlgAlert.setTitle("App Title");
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });

        return view; }
}
