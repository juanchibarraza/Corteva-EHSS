package com.example.corteva.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.corteva.ControladorComentarios;
import com.example.corteva.ControladorSites;
import com.example.corteva.R;
import com.example.corteva.SaveSharedPreference;
import com.example.corteva.Syncronizer;

import java.util.ArrayList;

public class ComentariosFragment extends Fragment {

    Spinner spinnerSite;
    EditText comentario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_comentarios, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

        //Recibo parametros
        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");

        comentario = getActivity().findViewById(R.id.etComentario);
        spinnerSite=getActivity().findViewById(R.id.spinnerSiteComentario);
        //traer los sites desde la web por syncro
        ArrayList<String> listaSites = new ControladorSites(getContext()).getSites();
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSites);
        spinnerSite.setAdapter(adapter1);

        if(SaveSharedPreference.getLoggedSite(getContext())!=""){
            spinnerSite.setSelection(adapter1.getPosition(SaveSharedPreference.getLoggedSite(getContext())));
        }

        //BOTON GUARDAR
        Button boton=getActivity().findViewById(R.id.botonGuardarComentario);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String txtComentario = comentario.getText().toString();
                    String spinnerSiteSeleccion = spinnerSite.getSelectedItem().toString();
                    boolean resultado = new ControladorComentarios(getContext()).Insert(spinnerSiteSeleccion, usuario, txtComentario);
                    if(resultado){
                        Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                        new Syncronizer(getActivity(),"setComentario").execute();
                    }else{
                        Toast.makeText(getActivity(),"No se guardó correctamente.",Toast.LENGTH_SHORT).show();
                    }
                    getActivity().onBackPressed();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),"Error al guardar los datos. " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
    }
}
