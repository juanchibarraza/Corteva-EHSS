package com.example.corteva.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.corteva.ControladorResponsables;
import com.example.corteva.ControladorSAI;
import com.example.corteva.ControladorSAIDesvios;
import com.example.corteva.ControladorSectores;
import com.example.corteva.ControladorSites;
import com.example.corteva.DatePickerFragment;
import com.example.corteva.R;
import com.example.corteva.SaveSharedPreference;
import com.example.corteva.Syncronizer;
import com.example.corteva.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SAIFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    View view;
    int CALENDAR_CODE = 0;
    EditText etFecha, etHora, etHoraFinalizacion, etCantidadContratistasForm, etCantidadEmpleadosForm;
    TextView tvTotales;
    Spinner spinnerSite, spinnerObservador, spinnerObservadorAgregado, spinnerDesvioForm, spinnerSectorForm, spinnerDesvioAgregado;
    ArrayAdapter<String> adapterSector;
    LinearLayout contenedorSectores;
    Map<String, String[]> desviosSectores = new HashMap<String, String[]>();
    Double global_sai, global_empleados, global_contratistas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_sai, container, false);
        return view;
    }

    public void onViewCreated(final View view, Bundle savedInstanceState){

        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");
        etFecha = getActivity().findViewById(R.id.etFechaSAI);
        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etFecha.setText(today);
        etHora = getActivity().findViewById(R.id.etHoraSAI);
        String now = new SimpleDateFormat("HH:mm:ss").format(new Date());
        etHora.setText(now);
        spinnerObservador = getActivity().findViewById(R.id.spinnerObservadorSAI);
        etHoraFinalizacion = getActivity().findViewById(R.id.etHoraFinSAI);
        spinnerSite = getActivity().findViewById(R.id.spinnerSiteSAI);
        tvTotales = getActivity().findViewById(R.id.txtTotales);
        contenedorSectores = getActivity().findViewById(R.id.formSectoresSAI);

        //Cargar Sites
        ArrayList<String> listaSites=new ControladorSites(getContext()).getSites();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),R.layout.spinner_item_reportes,listaSites);
        spinnerSite.setAdapter(adapter);

        if(SaveSharedPreference.getLoggedSite(getContext())!=""){
            spinnerSite.setSelection(adapter.getPosition(SaveSharedPreference.getLoggedSite(getContext())));
        }

        //Cargar sectores relacionados con el Site
        spinnerSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                contenedorSectores.removeAllViews();
                actualizarTotales();

                String spinnerSiteSeleccion = spinnerSite.getSelectedItem().toString();
                ArrayList<String> listaSectores = new ControladorSectores(getContext()).getSectoresBySite(spinnerSiteSeleccion);
                adapterSector = new ArrayAdapter<>(getActivity(),R.layout.spinner_item_reportes,listaSectores);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Cargar observadores
        ArrayList<String> observador=new ControladorResponsables(getContext()).getResponsables();
        final ArrayAdapter<String> adapterObservador=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador);
        spinnerObservador.setAdapter(adapterObservador);

        final LinearLayout contenedorObservador=(LinearLayout)view.findViewById(R.id.observadoresSAI);

        //BOTON AGREGAR OBSERVADOR
        Button botonAgregar=view.findViewById(R.id.agregarObservadorSAI);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View child = inflater.inflate(R.layout.sai_observador, null);
                contenedorObservador.addView(child);

                spinnerObservadorAgregado = child.findViewById(R.id.spinnerObservadorSAIAgregado);
                spinnerObservadorAgregado.setAdapter(adapterObservador);
            }
        });

        //Adapter de desvios
        ArrayList<String> desvios = new ControladorSAIDesvios(getContext()).getSAIDesvios();
        final ArrayAdapter<String> adapterDesvios = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,desvios);

        //Event listener para checkboxes
        final CompoundButton.OnCheckedChangeListener evento = new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                actualizarTotales();
                //if ( isChecked ){ }
            }
        };

        //Event listener para desvios
        final AdapterView.OnItemSelectedListener eventoDesvios = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                actualizarTotales();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        };

        //BOTON AGREGAR SECTOR
        Button botonAgregarForm = view.findViewById(R.id.buttonAgregarFormSector);
        botonAgregarForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IMPORTANTE: ANTES DE AGREGAR UN SECTOR SE DEBE HABER ELEGIDO EL SITE!!
                String getSite = spinnerSite.getSelectedItem().toString();
                if (getSite.contains("Seleccione")) {
                    Toast.makeText(getActivity(), "Seleccione Site por favor.", Toast.LENGTH_LONG).show();
                    return;
                }
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View child = inflater.inflate(R.layout.sai_sectores, null);
                contenedorSectores.addView(child);

                //Mostrar Cantidad de Contratistas y Empleados
                etCantidadContratistasForm = child.findViewById(R.id.etCantContratistaForm);
                etCantidadEmpleadosForm = child.findViewById(R.id.etCantEmpleadosForm);
                //tvTotalObservadosForm = child.findViewById(R.id.tvTotalObservadosMuestraForm);
                spinnerSectorForm = child.findViewById(R.id.spinnerSectorSAIForm);
                spinnerSectorForm.setAdapter(adapterSector);
                spinnerDesvioForm = child.findViewById(R.id.spinnerDesvioSAIForm);
                spinnerDesvioForm.setAdapter(adapterDesvios);
                //event listeners
                spinnerDesvioForm.setOnItemSelectedListener(eventoDesvios);

                CheckBox ChkBx1 = child.findViewById(R.id.chckContratistaForm);
                CheckBox ChkBx2 = child.findViewById(R.id.chckEmpleadoForm);
                ChkBx1.setOnCheckedChangeListener(evento);
                ChkBx2.setOnCheckedChangeListener(evento);

                TextWatcher textWatcherForm = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        actualizarTotales();
                    }
                };

                etCantidadContratistasForm.addTextChangedListener(textWatcherForm);
                etCantidadEmpleadosForm.addTextChangedListener(textWatcherForm);

                //BOTON AGREGAR DESVIO
                final LinearLayout contenedorDesvios = (LinearLayout) child.findViewById(R.id.desvioSAIForm);
                Button botonAgregarDesvioForm = child.findViewById(R.id.agregarDesvioSAIForm);
                botonAgregarDesvioForm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflaterForm = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View childForm = inflaterForm.inflate(R.layout.sai_desvio, null);
                        contenedorDesvios.addView(childForm);

                        spinnerDesvioAgregado = childForm.findViewById(R.id.spinnerDesvioSAIAgregado);
                        spinnerDesvioAgregado.setAdapter(adapterDesvios);
                        spinnerDesvioAgregado.setOnItemSelectedListener(eventoDesvios);
                        CheckBox ChkBx3 = childForm.findViewById(R.id.chckContratistaDesvioAgregado);
                        CheckBox ChkBx4 = childForm.findViewById(R.id.chckEmpleadoDesvioAgregado);
                        ChkBx3.setOnCheckedChangeListener(evento);
                        ChkBx4.setOnCheckedChangeListener(evento);

                        //BOTON ELIMINAR DESVIO
                        Button botondeleteDesvio = childForm.findViewById(R.id.deleteDesvio);
                        botondeleteDesvio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AlertDialog.Builder(getActivity())
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Eliminar Desvio")
                                        .setMessage("Desea eliminar el Desvio?")
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                contenedorDesvios.removeView(childForm);
                                            }

                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            }
                        });
                    }
                });

                //BOTON ELIMINAR SECTOR
                Button botondeleteSector = child.findViewById(R.id.deleteSector);
                botondeleteSector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getActivity())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Eliminar Sector")
                                .setMessage("Desea eliminar el sector?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        contenedorSectores.removeView(child);
                                    }

                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                });
            }
        });

        etFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se muestre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 1;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(SAIFragment.this, 111);
                        datePicker.show(getFragmentManager(), "date picker");
                        break;
                }
                return false;
            }
        });

        etHora.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        DialogFragment time= new TimePickerFragment();
                        time.setTargetFragment(SAIFragment.this, 0);
                        time.show(getFragmentManager(), "time picker");

                        break;
                }

                return false;
            }
        });

        etHoraFinalizacion.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        DialogFragment time= new TimePickerFragment();
                        time.setTargetFragment(SAIFragment.this, 0);
                        time.show(getFragmentManager(), "time picker");

                        break;
                }

                return false;
            }
        });

        Button botonGuardar = getActivity().findViewById(R.id.buttonGuardarSAI);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    String getSite = spinnerSite.getSelectedItem().toString();
                    String getFecha = etFecha.getText().toString();
                    String getHora = etHora.getText().toString();
                    String getHoraFinalizacion = etHoraFinalizacion.getText().toString();

                    String getSpinnerObservador = "";
                    if(spinnerObservador.getSelectedItem()!=null) {
                        getSpinnerObservador = spinnerObservador.getSelectedItem().toString();
                    }

                    String getSpinnerObservadorAgregado="";
                    for(int z=0; z<contenedorObservador.getChildCount();z++) {
                        if (contenedorObservador.getChildAt(z) instanceof LinearLayout) {
                            LinearLayout linearLayout = (LinearLayout) contenedorObservador.getChildAt(z);
                            int x = 0;
                            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                                if(linearLayout.getChildAt(i) instanceof  Spinner && x == 0){
                                    Spinner observadorAgregado = (Spinner) linearLayout.getChildAt(i);
                                    getSpinnerObservadorAgregado += "/-/" + observadorAgregado.getSelectedItem().toString() ;
                                }
                            }
                        }
                    }
                    getSpinnerObservador += getSpinnerObservadorAgregado;

                    /* Itero por todos los sectores y los proceso */
                    String getSpinnerSector, getCantidadContratistas, getCantidadEmpleados, getSpinnerDesvio,
                            getSpinnerDesvioAgregado, chckContratistaEmpleadoForm, getObservaciones;

                    ArrayList<String[]> arregloSectores = new ArrayList<>();
                    for(int z=0; z<contenedorSectores.getChildCount();z++) {
                        //Comienza un nuevo ciclo
                        LinearLayout LayoutSectores = (LinearLayout) contenedorSectores.getChildAt(z);
                        //Limpio las variables
                        getSpinnerSector = getCantidadContratistas = getCantidadEmpleados = getSpinnerDesvio =
                                getSpinnerDesvioAgregado = chckContratistaEmpleadoForm = "";

                        getSpinnerSector = ((Spinner) LayoutSectores.getChildAt(1)).getSelectedItem().toString();
                        getSpinnerDesvio = ((Spinner) LayoutSectores.getChildAt(5)).getSelectedItem().toString();

                        if(getSpinnerSector.contains("Seleccione")){
                            continue;
                        }

                        getCantidadContratistas = ((EditText) LayoutSectores.getChildAt(2)).getText().toString();
                        getCantidadEmpleados = ((EditText) LayoutSectores.getChildAt(3)).getText().toString();

                        //el linearLayout que tiene los checkbox es el 6
                        LinearLayout LayoutCheckboxes = (LinearLayout) LayoutSectores.getChildAt(6);
                        chckContratistaEmpleadoForm = "";
                        if (((CheckBox) LayoutCheckboxes.getChildAt(0)).isChecked()){
                            chckContratistaEmpleadoForm = ((CheckBox) LayoutCheckboxes.getChildAt(0)).getText().toString()+ ",";
                        }
                        if (((CheckBox) LayoutCheckboxes.getChildAt(1)).isChecked()){
                            chckContratistaEmpleadoForm += ((CheckBox) LayoutCheckboxes.getChildAt(1)).getText().toString();
                        }
                        String desvios = getSpinnerDesvio +"_"+ chckContratistaEmpleadoForm.replaceAll(",$", "") + ":";

                        //aqui estan los desvios agregados
                        LinearLayout LayoutDesviosAgregados = (LinearLayout) LayoutSectores.getChildAt(7);
                        for (int x = 0; x < LayoutDesviosAgregados.getChildCount(); x++) {
                            LinearLayout linearLayout = (LinearLayout) LayoutDesviosAgregados.getChildAt(x);

                            Spinner desvioAgregado = (Spinner) linearLayout.getChildAt(1);
                            getSpinnerDesvioAgregado = desvioAgregado.getSelectedItem().toString();
                            /*if(getSpinnerDesvioAgregado.contains("Seleccione")){
                                continue;
                            }*/

                            chckContratistaEmpleadoForm = "";
                            LinearLayout LayoutDesvioSAIAgregado = (LinearLayout) linearLayout.getChildAt(2);
                            if (((CheckBox) LayoutDesvioSAIAgregado.getChildAt(0)).isChecked()){
                                chckContratistaEmpleadoForm = ((CheckBox) LayoutDesvioSAIAgregado.getChildAt(0)).getText().toString() + ",";
                            }
                            if (((CheckBox) LayoutDesvioSAIAgregado.getChildAt(1)).isChecked()){
                                chckContratistaEmpleadoForm += ((CheckBox) LayoutDesvioSAIAgregado.getChildAt(1)).getText().toString();
                            }
                            //concatenar todo con la variable desvios de linea 291
                             desvios += getSpinnerDesvioAgregado +"_"+ chckContratistaEmpleadoForm.replaceAll(",$", "") + ":";
                        }
                        getObservaciones = ((EditText) LayoutSectores.getChildAt(8)).getText().toString();
                        arregloSectores.add(new String[]{getSpinnerSector, getCantidadContratistas, getCantidadEmpleados, desvios.replaceAll(":$", ""), getObservaciones});
                    }

                    boolean resultado = new ControladorSAI(getContext()).Insert(getSite, getFecha, getHora, getHoraFinalizacion,
                            getSpinnerObservador, String.format("%.2f", global_sai), String.format("%.2f", global_empleados),
                            String.format("%.2f", global_contratistas), usuario);

                    if (resultado){
                        boolean resultado2 = false;
                        for(int x=0; x<arregloSectores.size(); x++){
                            String[] valores = desviosSectores.get(arregloSectores.get(x)[0]);
                            resultado2 = new ControladorSAI(getContext()).InsertSectores(arregloSectores.get(x)[0],arregloSectores.get(x)[1],
                                    arregloSectores.get(x)[2], arregloSectores.get(x)[3], arregloSectores.get(x)[4], valores[0].toString(),
                                    valores[1].toString(), valores[2].toString());
                        }

                        if (resultado2){
                            Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                            new Syncronizer(getActivity(),"setSAI").execute();
                        }else{
                            Toast.makeText(getActivity(),"No se guardó correctamente.",Toast.LENGTH_SHORT).show();
                        }
                        getActivity().onBackPressed();
                    }

                }catch (Exception e){

                    Toast.makeText(getActivity(),"Error al guardar los datos. " + e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("SAI", "Error al guardar los datos. " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(CALENDAR_CODE==1){
            String mes = Integer.toString(month +1);
            if( mes.length()<2){
                mes = "0"+mes;
            }
            String dia = Integer.toString(dayOfMonth);
            if(dia.length()<2){
                dia = "0"+dia;
            }
            etFecha.setText(Integer.toString(year) + "-" + mes + "-" + dia);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        etHora.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + ":00");
        etHoraFinalizacion.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + ":00");
    }

    private void actualizarTotales(){

        desviosSectores.clear();
        Integer Global_CantidadContratistas = 0, Global_CantidadEmpleados = 0;
        Double Global_severidad_empleados = 0.0, Global_severidad_contratistas = 0.0;
        String totales = "";

        for(int z=0; z<contenedorSectores.getChildCount();z++) {
            //Comienza un nuevo ciclo
            LinearLayout LayoutSectores = (LinearLayout) contenedorSectores.getChildAt(z);

            String Desvio = ((Spinner) LayoutSectores.getChildAt(5)).getSelectedItem().toString();

            String Sector = ((Spinner) LayoutSectores.getChildAt(1)).getSelectedItem().toString();
            Integer CantidadContratistas = 0;
            try {
                 CantidadContratistas = Integer.parseInt(((EditText) LayoutSectores.getChildAt(2)).getText().toString());
            } catch (Exception e) {

            }
            Integer CantidadEmpleados = 0;
            try {
                CantidadEmpleados = Integer.parseInt(((EditText) LayoutSectores.getChildAt(3)).getText().toString());
            } catch (Exception e) {

            }

            Global_CantidadContratistas += CantidadContratistas;
            Global_CantidadEmpleados += CantidadEmpleados;

            //get severidad del desvio
            Double severidad = 0.0;
            if(!Desvio.contains("Seleccione")){
                severidad = new ControladorSAIDesvios(getContext()).GetSeveridad(Desvio);
            }
            Double severidad_empleados = 0.0;
            Double severidad_contratistas = 0.0;
            Double total_sai = 0.0;
            Double total_empleados = 0.0;
            Double total_contratistas = 0.0;

            LinearLayout LayoutCheckboxes = (LinearLayout) LayoutSectores.getChildAt(6);
            if (((CheckBox) LayoutCheckboxes.getChildAt(0)).isChecked() && CantidadContratistas>0){ //empleado checked
                severidad_contratistas = severidad;
            }
            if (((CheckBox) LayoutCheckboxes.getChildAt(1)).isChecked() && CantidadEmpleados>0){ //contratista checked
                severidad_empleados = severidad;
            }
            //aqui estan los desvios agregados
            LinearLayout LayoutDesviosAgregados = (LinearLayout) LayoutSectores.getChildAt(7);
            for (int x = 0; x < LayoutDesviosAgregados.getChildCount(); x++) {
                LinearLayout linearLayout = (LinearLayout) LayoutDesviosAgregados.getChildAt(x);

                String desvioAgregado = ((Spinner) linearLayout.getChildAt(1)).getSelectedItem().toString();
                if(desvioAgregado.contains("Seleccione")){
                    severidad = 0.0;
                }else{
                    severidad = new ControladorSAIDesvios(getContext()).GetSeveridad(desvioAgregado);
                }

                LinearLayout LayoutDesvioSAIAgregado = (LinearLayout) linearLayout.getChildAt(2);
                if (((CheckBox) LayoutDesvioSAIAgregado.getChildAt(0)).isChecked() && CantidadContratistas>0){
                    severidad_contratistas += severidad;
                }
                if (((CheckBox) LayoutDesvioSAIAgregado.getChildAt(1)).isChecked() && CantidadEmpleados>0){
                    severidad_empleados += severidad;
                }
            }
            if(CantidadEmpleados>0){
                total_empleados = 100 - (severidad_empleados*100)/CantidadEmpleados;
                Global_severidad_empleados += severidad_empleados;
            }else{
                total_empleados = 0.0;
            }
            if(CantidadContratistas>0){
                total_contratistas = 100 - (severidad_contratistas*100)/CantidadContratistas;
                Global_severidad_contratistas += severidad_contratistas;
            }else{
                total_contratistas = 0.0;
            }
            total_sai = 100 - (severidad_contratistas+severidad_empleados)*100/(CantidadEmpleados + CantidadContratistas);
            desviosSectores.put(Sector, new String[]{String.format("%.2f", total_sai), String.format("%.2f", total_empleados), String.format("%.2f", total_contratistas)});
            totales += "\n\nTotal "+Sector +": "+ String.format("%.2f", total_sai) + "\nTotal Empleados: " + String.format("%.2f", total_empleados)
                    + "\nTotal Contratistas: " + String.format("%.2f", total_contratistas);
        }
        
        /*GLOBALES*/
        if(Global_CantidadEmpleados>0) {
            global_empleados = 100 - (Global_severidad_empleados*100)/Global_CantidadEmpleados;
        }else{
            global_empleados = 100.0;
        }
        if(Global_CantidadContratistas>0){
            global_contratistas = 100 - (Global_severidad_contratistas*100)/Global_CantidadContratistas;
        }else{
            global_contratistas = 100.0;
        }
        global_sai = 100 - (Global_severidad_empleados+Global_severidad_contratistas)*100/(Global_CantidadEmpleados + Global_CantidadContratistas);

        tvTotales.setText("Total SAI: "+String.format("%.2f", global_sai)+"\nTotal Empleados: "+String.format("%.2f", global_empleados)
                +"\nTotal Contratistas: "+String.format("%.2f", global_contratistas) + totales);
    }
}