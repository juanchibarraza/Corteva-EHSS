package com.example.corteva.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corteva.ControladorResponsables;
import com.example.corteva.ControladorSectores;
import com.example.corteva.ControladorSites;
import com.example.corteva.ControladorUAC;
import com.example.corteva.R;
import com.example.corteva.SaveSharedPreference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;


public class UACCIERRELISTVIEWFragment extends Fragment {

    private ListView lv1;
    LinearLayout LL;
    Spinner spinnerResponsable, spinnerSectorResponsable, spinnerSector, spinnerSite;
    FloatingActionButton btnShowHide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_uaccierrelistview, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        //Recibo parametros
        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");
        final String email = bundle.getString("email");

        LL = getActivity().findViewById(R.id.LL);

        btnShowHide = getActivity().findViewById(R.id.showHide);
        btnShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Primer sincronizacion
                btnShowHide.hide();
                LL.setVisibility(View.VISIBLE);
            }
        });
        spinnerResponsable=getActivity().findViewById(R.id.spinnerResponsable);
        spinnerSector=getActivity().findViewById(R.id.spinnerSector);
        spinnerSectorResponsable=getActivity().findViewById(R.id.spinnerSectorResponsable);
        spinnerSite=getActivity().findViewById(R.id.spinnerSite);

        //traer los sites desde la web por syncro
        ArrayList<String> listaSites=new ControladorSites(getContext()).getSites();
        ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSites);
        spinnerSite.setAdapter(adapter1);

        if(SaveSharedPreference.getLoggedSite(getContext())!=""){
            spinnerSite.setSelection(adapter1.getPosition(SaveSharedPreference.getLoggedSite(getContext())));
        }

        //Cuando se selecciona el Site se cargan los sectores relacionados
        spinnerSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String spinnerSiteSeleccion= spinnerSite.getSelectedItem().toString();
                ArrayList<String> listaSectores = new ControladorSectores(getContext()).getSectoresBySite(spinnerSiteSeleccion);
                listaSectores.add("Todos");
                ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSectores);
                spinnerSector.setAdapter(adapter1);
                spinnerSectorResponsable.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Cuando se selecciona el sector del responsable se cargan los responsables
        spinnerSectorResponsable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String spinnerSectorSeleccion= spinnerSectorResponsable.getSelectedItem().toString();
                ArrayList<String> listaResponsables=new ControladorResponsables(getContext()).getResponsablesBySector(spinnerSectorSeleccion);
                listaResponsables.add("Todos");
                ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaResponsables);
                spinnerResponsable.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //BOTON BUSCAR
        Button botonBuscar=view.findViewById(R.id.buttonBuscar);
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readRecords();
            }
        });

        lv1=getActivity().findViewById(R.id.lv1);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtID = (TextView) view.findViewById(R.id.ID);
                TextView txtResponsable = (TextView) view.findViewById(R.id.titulo2);
                String Responsable = txtResponsable.getText().toString().replace("Responsable: ","");

                //Si el usuario es el responsable, puede cerrar la Condición Insegura, sino no
                if(Responsable.toUpperCase().equals(usuario.toUpperCase())){
                    Fragment miFragment = new UACCIERREFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("usuario",email);
                    bundle.putString("id",txtID.getText().toString());
                    miFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.inicio, miFragment, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }else{
                    Toast.makeText(getActivity(),"Solo el responsable puede cerrar la Condición Insegura.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void readRecords(){
        String getSite = spinnerSite.getSelectedItem().toString();
        String selectedSite = "";
        if (getSite.contains("Seleccione")) {
            Toast.makeText(getActivity(), "Seleccione Site por favor.", Toast.LENGTH_LONG).show();
            return;
        }else{
            selectedSite = spinnerSite.getSelectedItem().toString();
        }
        String selectedSector = "";
        if (spinnerSector.getSelectedItem() != null){
            selectedSector = spinnerSector.getSelectedItem().toString();
        }
        String selectedResponsable = "";
        if (spinnerResponsable.getSelectedItem()!= null) {
            selectedResponsable = spinnerResponsable.getSelectedItem().toString();
        }
        ArrayList<HashMap<String, String>> filas= new ControladorUAC(getContext()).getUACPendientes(selectedSite, selectedSector,selectedResponsable);
        ListAdapter adapter = new SimpleAdapter(
                getContext(), filas,
                R.layout.list_view_personalizado,
                new String[]{"id", "numeroysector", "responsable", "lugar"},
                new int[]{R.id.ID, R.id.titulo1, R.id.titulo2, R.id.titulo3}){
        };
        LL.setVisibility(View.GONE);
        btnShowHide.show();
        lv1.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
    }
}
