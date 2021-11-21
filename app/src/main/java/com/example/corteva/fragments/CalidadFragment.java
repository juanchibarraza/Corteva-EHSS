package com.example.corteva.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.corteva.ControladorCalidad;
import com.example.corteva.ControladorResponsables;
import com.example.corteva.ControladorSectores;
import com.example.corteva.DatePickerFragment;
import com.example.corteva.R;

import java.util.ArrayList;

public class CalidadFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    int CALENDAR_CODE = 0;
    Spinner spinnerTipo, spinnerLugarOcurrencia, spinnerLaboratorio, spinnerPlanta, spinnerEmisor, spinnerEmisorAgregado;
    EditText etFechaCarga, etFechaDeteccion, etFechaOcurrencia, etProcedencia, etImpacto, etObsevaciones,etEvidencia,etEmisor, etAccionInmediata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_calidad, container, false);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void onViewCreated(View view, Bundle savedInstanceState){

        spinnerTipo = getActivity().findViewById(R.id.spinnerTipoCalidad);
        spinnerLugarOcurrencia = getActivity().findViewById(R.id.spinnerLugarOcurrenciaCalidad);
        spinnerEmisor = getActivity().findViewById(R.id.spinnerEmisoresCalidad);
        etFechaDeteccion = getActivity().findViewById(R.id.etFechaDeteccionCalidad);
        etFechaOcurrencia = getActivity().findViewById(R.id.etFechaOcurrenciaCalidad);
        etProcedencia = getActivity().findViewById(R.id.etProcedenciaCalidad);
        etImpacto = getActivity().findViewById(R.id.etImpactoCalidad);
        etObsevaciones = getActivity().findViewById(R.id.etObservacionesCalidad);
        etEvidencia = getActivity().findViewById(R.id.etEvidenciaCalidad);
        etEmisor = getActivity().findViewById(R.id.etEmisoresCalidad);
        etAccionInmediata = getActivity().findViewById(R.id.etAccionInmediataCalidad);

        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");

        String [] tipo = new String[]{"NC","TNC"};
        ArrayAdapter<String>adapterTipo = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes, tipo);
        spinnerTipo.setAdapter(adapterTipo);

        //CALENDARIO
        etFechaDeteccion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se muestre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 1;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(CalidadFragment.this, 0);
                        datePicker.show(getFragmentManager(), "fecha Deteccion");
                        break;
                }
                return false;
            }
        });

        etFechaOcurrencia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se muestre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 2;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(CalidadFragment.this, 1);
                        datePicker.show(getFragmentManager(), "fecha Ocurrencia");
                        break;
                }
                return false;
            }
        });

        String [] lugarOcurrencia = new String[]{"Seleccione un lugar","Laboratorio","Planta"};
        ArrayAdapter<String>adapterLugarOcurrencia = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes, lugarOcurrencia);
        spinnerLugarOcurrencia.setAdapter(adapterLugarOcurrencia);

        final LinearLayout contenedorLaboratorio=(LinearLayout)view.findViewById(R.id.agregarLaboratorio);
        final LinearLayout contenedorPlanta=(LinearLayout)view.findViewById(R.id.agregarPlanta);
        final Button botonAgregar = getActivity().findViewById(R.id.btnAgregarEmisoresCalidad);
        final LinearLayout contenedorEmisorLaboratorio=(LinearLayout) getActivity().findViewById(R.id.emisoresLaboratorio);
        final LinearLayout contenedorEmisorPlanta=(LinearLayout) getActivity().findViewById(R.id.emisoresPlanta);

        spinnerLugarOcurrencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (spinnerLugarOcurrencia.getSelectedItem().toString()) {
                    case "Laboratorio":

                        contenedorPlanta.removeAllViewsInLayout();
                        contenedorEmisorPlanta.removeAllViewsInLayout();
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View child = inflater.inflate(R.layout.fragment_calidad_laboratorio, null);
                        contenedorLaboratorio.addView(child);
                        spinnerLaboratorio = child.findViewById(R.id.spinnerLugarOcurrenciaLaboratorio);

                        String[] lugarOcurrenciaLaboratorio = new String[]{"Botánico", "Físico","Gestión", "Recepción", "Sistemas", "Sizing"};
                        ArrayAdapter<String> adapterLugarOcurrenciaLaboratorio = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_reportes, lugarOcurrenciaLaboratorio);
                        spinnerLaboratorio.setAdapter(adapterLugarOcurrenciaLaboratorio);

                        ArrayList<String> listaResponsablesLaboratorio=new ControladorResponsables(getContext()).getResponsablesBySector("Control de calidad");
                        final ArrayAdapter<String>adapterEmisoresLaboratorio=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaResponsablesLaboratorio);
                        spinnerEmisor.setAdapter(adapterEmisoresLaboratorio);

                        //BOTON AGREGAR EMISOR
                        botonAgregar.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View child = inflater.inflate(R.layout.fragment_emisor_laboratorio, null);
                                contenedorEmisorLaboratorio.addView(child);
                                spinnerEmisorAgregado = child.findViewById(R.id.spinnerEmisorAgregadoLaboratorio);
                                spinnerEmisorAgregado.setAdapter(adapterEmisoresLaboratorio);

                            }
                        });

                        break;

                    case "Planta":

                        contenedorLaboratorio.removeAllViewsInLayout();
                        contenedorEmisorLaboratorio.removeAllViewsInLayout();
                        LayoutInflater inflaterPlanta = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View childPlanta = inflaterPlanta.inflate(R.layout.fragment_calidad_planta, null);
                        contenedorPlanta.addView(childPlanta);
                        spinnerPlanta = childPlanta.findViewById(R.id.spinnerLugarOcurrenciaPlanta);

                        ArrayList<String> listaSectores=new ControladorSectores(getContext()).getSectoresBySite("Planta Venado Tuerto");
                        ArrayAdapter<String>adapterPlanta=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSectores);
                        spinnerPlanta.setAdapter(adapterPlanta);

                        String lugarPlanta="";
                        for(int z=0; z<contenedorPlanta.getChildCount();z++) {
                            if (contenedorPlanta.getChildAt(z) instanceof LinearLayout) {
                                LinearLayout linearLayout = (LinearLayout) contenedorPlanta.getChildAt(z);
                                int x = 0;
                                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                                    if (linearLayout.getChildAt(i) instanceof Spinner) {
                                        Spinner sectorText = (Spinner) linearLayout.getChildAt(i);
                                        lugarPlanta += sectorText.getSelectedItem().toString() + "/_/";
                                    }
                                }
                            }
                        }

                        ArrayList<String> listaResponsablesPlanta=new ControladorResponsables(getContext()).getResponsablesBySector(lugarPlanta);
                        ArrayAdapter<String>adapterEmisoresPlanta=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaResponsablesPlanta);
                        spinnerEmisor.setAdapter(adapterEmisoresPlanta);

                        //BOTON AGREGAR EMISOR
                        botonAgregar.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View child = inflater.inflate(R.layout.fragment_emisor_planta, null);
                                contenedorEmisorPlanta.addView(child);
                                spinnerEmisorAgregado = child.findViewById(R.id.spinnerEmisorAgregadoPlanta);

                                String lugarPlanta="";
                                for(int z=0; z<contenedorPlanta.getChildCount();z++) {
                                    if (contenedorPlanta.getChildAt(z) instanceof LinearLayout) {
                                        LinearLayout linearLayout = (LinearLayout) contenedorPlanta.getChildAt(z);
                                        int x = 0;
                                        for (int i = 0; i < linearLayout.getChildCount(); i++) {
                                            if (linearLayout.getChildAt(i) instanceof Spinner) {
                                                Spinner sectorText = (Spinner) linearLayout.getChildAt(i);
                                                lugarPlanta += sectorText.getSelectedItem().toString() + "/_/";
                                            }
                                        }
                                    }
                                }

                                ArrayList<String> listaResponsablesPlanta=new ControladorResponsables(getContext()).getResponsablesBySector(lugarPlanta);
                                ArrayAdapter<String>adapterEmisoresPlanta=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaResponsablesPlanta);
                                spinnerEmisorAgregado.setAdapter(adapterEmisoresPlanta);

                            }
                        });

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //BOTON GUARDAR
        Button btnGuardar = getActivity().findViewById(R.id.btnGuardarCalidad);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String getTipo = spinnerTipo.getSelectedItem().toString();
                    String getFechaDeteccion = etFechaDeteccion.getText().toString();
                    String getFechaOcurrencia = etFechaOcurrencia.getText().toString();
                    String getLugarOcurrencia = spinnerLugarOcurrencia.getSelectedItem().toString();
                    String getProcedencia = etProcedencia.getText().toString();
                    String getImpacto = etImpacto.getText().toString();
                    String getEvidencia = etEvidencia.getText().toString();
                    String getObservaciones = etObsevaciones.getText().toString();
                    String getEmisores = spinnerEmisor.getSelectedItem().toString() + "/_/" + spinnerEmisorAgregado.getSelectedItem().toString() + "/_/" + etEmisor.getText().toString();
                    String getAccionInmediata = etAccionInmediata.getText().toString();

                    boolean resultado=new ControladorCalidad(getContext()).Insert(getLugarOcurrencia, getFechaDeteccion, getFechaOcurrencia,
                            getProcedencia, getImpacto, getObservaciones, getEvidencia, getEmisores, getAccionInmediata, usuario);
                    if(resultado){
                        Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"No se guardó correctamente.",Toast.LENGTH_SHORT).show();
                    }
                    getActivity().onBackPressed();

                }catch (Exception e){
                    Toast.makeText(getActivity(),"Error al guardar los datos. " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if(CALENDAR_CODE==1){
            etFechaDeteccion.setText(Integer.toString(year) + "-" +Integer.toString(month +1) + "-" + Integer.toString(dayOfMonth));
        }else if(CALENDAR_CODE==2){
            etFechaOcurrencia.setText(Integer.toString(year) + "-" +Integer.toString(month +1) + "-" + Integer.toString(dayOfMonth));
        }

        /*String mes = Integer.toString(month +1);
        if( mes.length()<2){
            mes = "0"+mes;
        }
        String dia = Integer.toString(dayOfMonth);
        if(dia.length()<2){
            dia = "0"+dia;
        }

        etFechaDeteccion.setText(Integer.toString(dayOfMonth) + "-" + mes + "-" +  dia);
        etFechaOcurrencia.setText(Integer.toString(dayOfMonth) + "-" + mes + "-" +  dia);*/

    }

    public interface OnFragmentInteractionListener {
    }

}