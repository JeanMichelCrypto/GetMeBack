package michelsmartproduction.getmeback;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListeAddresses extends Fragment {

    private ArrayList<String> liste = new ArrayList<String>();
    TinyDB tinydb;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listeadr, container, false);

        android.support.design.widget.FloatingActionButton retour = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.retour);
        android.support.design.widget.FloatingActionButton ajouter = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.addAdd);

        tinydb = new TinyDB(getActivity());
        liste = tinydb.getListString("listAddr");

        retour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_top, R.anim.slide_bot);
                Menu menuFragment = new Menu();
                fragmentTransaction.replace(R.id.activity_main, menuFragment);
                fragmentTransaction.commit();
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_top, R.anim.slide_bot);
                AjouterAdr ajout = new AjouterAdr();
                fragmentTransaction.replace(R.id.activity_main, ajout);
                fragmentTransaction.commit();
            }
        });

        ListView lv = (ListView) view.findViewById(R.id.listView);
        lv.setAdapter(new MyListAdaper(getActivity(), R.layout.adresses, liste));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.textViewforlist);
                viewHolder.button = (michelsmartproduction.getmeback.CustomButton) convertView.findViewById(R.id.buttonforlist);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_bot);
                    Plus plus = new Plus();
                    Bundle args = new Bundle();
                    args.putInt("id", position);
                    plus.setArguments(args);
                    fragmentTransaction.replace(R.id.activity_main, plus);
                    fragmentTransaction.commit();
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }
    }
    public class ViewHolder {
        TextView title;
        Button button;
    }
}


