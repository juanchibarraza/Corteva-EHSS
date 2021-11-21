package com.example.corteva.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corteva.ControladorSites;
import com.example.corteva.ControladorStop;
import com.example.corteva.ControladorSectores;
import com.example.corteva.ControladorTareas;
import com.example.corteva.R;
import com.example.corteva.SaveSharedPreference;
import com.example.corteva.Syncronizer;

import java.util.ArrayList;

public class StopFragment extends Fragment {

    EditText observaciones;
    EditText interacciones;
    EditText lugar;
    Spinner spinnerTarea, spinnerP, spinnerSector, spinnerSite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_stop, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");
        spinnerTarea=getActivity().findViewById(R.id.spinnerTarea);
        spinnerP=getActivity().findViewById(R.id.spinnerPersonas);
        spinnerSector=getActivity().findViewById(R.id.spinnerSector);
        spinnerSite=getActivity().findViewById(R.id.spinnerSiteSTOP);

        //traer los sites desde la web por syncro
        ArrayList<String> listaSites=new ControladorSites(getContext()).getSites();
        ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSites);
        spinnerSite.setAdapter(adapter1);

        if(SaveSharedPreference.getLoggedSite(getContext())!=""){
            spinnerSite.setSelection(adapter1.getPosition(SaveSharedPreference.getLoggedSite(getContext())));
        }

        String [] cantPersonas=new String[]{"1","2","3","4","5","Más de 5"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes, cantPersonas);
        spinnerP.setAdapter(adapter);

        //Cuando se selecciona el Site se cargan los sectores relacionados
        spinnerSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String spinnerSiteSeleccion= spinnerSite.getSelectedItem().toString();
                ArrayList<String> listaSectores=new ControladorSectores(getContext()).getSectoresBySite(spinnerSiteSeleccion);
                ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSectores);
                spinnerSector.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> listaTarea=new ControladorTareas(getContext()).getTareas();
        ArrayAdapter<String>adapter2=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaTarea);
        spinnerTarea.setAdapter(adapter2);

        observaciones=getActivity().findViewById(R.id.etObservaciones);
        interacciones=getActivity().findViewById(R.id.etInteracciones);
        lugar=getActivity().findViewById(R.id.etLugar);

        //Mostrar/Ocultar grupos
        TextView textView_EPP = getActivity().findViewById(R.id.textView_EPP);
        textView_EPP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_EPP);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_MontaCargas = getActivity().findViewById(R.id.textView_MontaCargas);
        textView_MontaCargas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_MontaCargas);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_Vehiculo = getActivity().findViewById(R.id.textView_Vehiculo);
        textView_Vehiculo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Vehiculo);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_Posiciones = getActivity().findViewById(R.id.textView_Posiciones);
        textView_Posiciones.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Posiciones);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_Herramientas = getActivity().findViewById(R.id.textView_Herramientas);
        textView_Herramientas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Herramientas);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_Altura = getActivity().findViewById(R.id.textView_Altura);
        textView_Altura.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Altura);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_LOTO = getActivity().findViewById(R.id.textView_LOTO);
        textView_LOTO.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_LOTO);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_Confinados = getActivity().findViewById(R.id.textView_Confinados);
        textView_Confinados.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Confinados);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_Riesgos = getActivity().findViewById(R.id.textView_Riesgos);
        textView_Riesgos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Riesgos);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });

        //GUARDAR
        Button botonGuardar = getActivity().findViewById(R.id.botonGuardarBBP);
        botonGuardar.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                            public void onClick(View view){
                                try{
                                    String txtObservaciones=observaciones.getText().toString();
                                    String txtInteracciones=interacciones.getText().toString();
                                    String txtLugar=lugar.getText().toString();
                                    String spinnerPersonas = spinnerP.getSelectedItem().toString();
                                    String spinnerTareaSeleccion=spinnerTarea.getSelectedItem().toString();
                                    String spinnerSectorSeleccion=spinnerSector.getSelectedItem().toString();
                                    String spinnerSiteSeleccion=spinnerSite.getSelectedItem().toString();

                                    RadioGroup rg_epp1 = (RadioGroup)getActivity().findViewById(R.id.EPP1);
                                    int idEPP1=rg_epp1.getCheckedRadioButtonId();
                                    RadioButton radioButtonEPP1 = (RadioButton)getActivity().findViewById(idEPP1);
                                    String epp1=radioButtonEPP1.getText().toString();

                                    RadioGroup rg_epp2 = (RadioGroup)getActivity().findViewById(R.id.EPP2);
                                    int idEPP2=rg_epp2.getCheckedRadioButtonId();
                                    RadioButton radioButtonEPP2 = (RadioButton)getActivity().findViewById(idEPP2);
                                    String epp2=radioButtonEPP2.getText().toString();

                                    RadioGroup rg_epp3 = (RadioGroup)getActivity().findViewById(R.id.EPP3);
                                    int idEPP3=rg_epp3.getCheckedRadioButtonId();
                                    RadioButton radioButtonEPP3 = (RadioButton)getActivity().findViewById(idEPP3);
                                    String epp3=radioButtonEPP3.getText().toString();

                                    RadioGroup rg_montacargas1 = (RadioGroup)getActivity().findViewById(R.id.montacargas1);
                                    int idMontacargas1=rg_montacargas1.getCheckedRadioButtonId();
                                    RadioButton radioButtonMontacargas1 = (RadioButton)getActivity().findViewById(idMontacargas1);
                                    String montacargas1=radioButtonMontacargas1.getText().toString();

                                    RadioGroup rg_montacargas2 = (RadioGroup)getActivity().findViewById(R.id.montacargas2);
                                    int idMontacargas2=rg_montacargas2.getCheckedRadioButtonId();
                                    RadioButton radioButtonMontacargas2 = (RadioButton)getActivity().findViewById(idMontacargas2);
                                    String montacargas2=radioButtonMontacargas2.getText().toString();

                                    RadioGroup rg_montacargas3 = (RadioGroup)getActivity().findViewById(R.id.montacargas3);
                                    int idMontacargas3=rg_montacargas3.getCheckedRadioButtonId();
                                    RadioButton radioButtonMontacargas3 = (RadioButton)getActivity().findViewById(idMontacargas3);
                                    String montacargas3=radioButtonMontacargas3.getText().toString();

                                    RadioGroup rg_montacargas4 = (RadioGroup)getActivity().findViewById(R.id.montacargas4);
                                    int idMontacargas4=rg_montacargas4.getCheckedRadioButtonId();
                                    RadioButton radioButtonMontacargas4 = (RadioButton)getActivity().findViewById(idMontacargas4);
                                    String montacargas4=radioButtonMontacargas4.getText().toString();

                                    RadioGroup rg_montacargas5 = (RadioGroup)getActivity().findViewById(R.id.montacargas5);
                                    int idMontacargas5=rg_montacargas5.getCheckedRadioButtonId();
                                    RadioButton radioButtonMontacargas5 = (RadioButton)getActivity().findViewById(idMontacargas5);
                                    String montacargas5=radioButtonMontacargas5.getText().toString();

                                    RadioGroup rg_montacargas6 = (RadioGroup)getActivity().findViewById(R.id.montacargas6);
                                    int idMontacargas6=rg_montacargas6.getCheckedRadioButtonId();
                                    RadioButton radioButtonMontacargas6 = (RadioButton)getActivity().findViewById(idMontacargas6);
                                    String montacargas6=radioButtonMontacargas6.getText().toString();

                                    RadioGroup rg_vehiculo1 = (RadioGroup)getActivity().findViewById(R.id.vehiculo1);
                                    int idVehiculo1=rg_vehiculo1.getCheckedRadioButtonId();
                                    RadioButton radioButtonVehiculo1 = (RadioButton)getActivity().findViewById(idVehiculo1);
                                    String vehiculo1=radioButtonVehiculo1.getText().toString();

                                    RadioGroup rg_vehiculo2 = (RadioGroup)getActivity().findViewById(R.id.vehiculo2);
                                    int idVehiculo2=rg_vehiculo2.getCheckedRadioButtonId();
                                    RadioButton radioButtonVehiculo2 = (RadioButton)getActivity().findViewById(idVehiculo2);
                                    String vehiculo2=radioButtonVehiculo2.getText().toString();

                                    RadioGroup rg_vehiculo3 = (RadioGroup)getActivity().findViewById(R.id.vehiculo3);
                                    int idVehiculo3=rg_vehiculo3.getCheckedRadioButtonId();
                                    RadioButton radioButtonVehiculo3 = (RadioButton)getActivity().findViewById(idVehiculo3);
                                    String vehiculo3=radioButtonVehiculo3.getText().toString();

                                    RadioGroup rg_vehiculo4 = (RadioGroup)getActivity().findViewById(R.id.vehiculo4);
                                    int idVehiculo4=rg_vehiculo4.getCheckedRadioButtonId();
                                    RadioButton radioButtonVehiculo4 = (RadioButton)getActivity().findViewById(idVehiculo4);
                                    String vehiculo4=radioButtonVehiculo4.getText().toString();

                                    RadioGroup rg_posiciones1 = (RadioGroup)getActivity().findViewById(R.id.posiciones1);
                                    int idRiesgos2=rg_posiciones1.getCheckedRadioButtonId();
                                    RadioButton radioButtonRiesgos2 = (RadioButton)getActivity().findViewById(idRiesgos2);
                                    String posiciones1=radioButtonRiesgos2.getText().toString();

                                    RadioGroup rg_posiciones2 = (RadioGroup)getActivity().findViewById(R.id.posiciones2);
                                    int idRiesgos3=rg_posiciones2.getCheckedRadioButtonId();
                                    RadioButton radioButtonRiesgos3 = (RadioButton)getActivity().findViewById(idRiesgos3);
                                    String posiciones2=radioButtonRiesgos3.getText().toString();

                                    RadioGroup rg_riesgos1 = (RadioGroup)getActivity().findViewById(R.id.riesgos1);
                                    int idRiesgos1=rg_riesgos1.getCheckedRadioButtonId();
                                    RadioButton radioButtonRiesgos1 = (RadioButton)getActivity().findViewById(idRiesgos1);
                                    String riesgos1=radioButtonRiesgos1.getText().toString();

                                    RadioGroup rg_riesgos4 = (RadioGroup)getActivity().findViewById(R.id.riesgos4);
                                    int idRiesgos4=rg_riesgos4.getCheckedRadioButtonId();
                                    RadioButton radioButtonRiesgos4 = (RadioButton)getActivity().findViewById(idRiesgos4);
                                    String riesgos4=radioButtonRiesgos4.getText().toString();

                                    RadioGroup rg_RiesgoCaida = (RadioGroup)getActivity().findViewById(R.id.RiesgoCaida);
                                    int idRiesgoCaida=rg_RiesgoCaida.getCheckedRadioButtonId();
                                    RadioButton radioButtonRiesgoCaida = (RadioButton)getActivity().findViewById(idRiesgoCaida);
                                    String RiesgoCaida=radioButtonRiesgoCaida.getText().toString();

                                    RadioGroup rg_altura1 = (RadioGroup)getActivity().findViewById(R.id.altura1);
                                    int idAltura1=rg_altura1.getCheckedRadioButtonId();
                                    RadioButton radioButtonAltura1 = (RadioButton)getActivity().findViewById(idAltura1);
                                    String altura1=radioButtonAltura1.getText().toString();

                                    RadioGroup rg_altura2 = (RadioGroup)getActivity().findViewById(R.id.altura2);
                                    int idAltura2=rg_altura2.getCheckedRadioButtonId();
                                    RadioButton radioButtonAltura2 = (RadioButton)getActivity().findViewById(idAltura2);
                                    String altura2=radioButtonAltura2.getText().toString();

                                    /*RadioGroup rg_altura3 = (RadioGroup)getActivity().findViewById(R.id.altura3);
                                    int idAltura3=rg_altura3.getCheckedRadioButtonId();
                                    RadioButton radioButtonAltura3 = (RadioButton)getActivity().findViewById(idAltura3);
                                    String altura3=radioButtonAltura3.getText().toString();*/

                                    RadioGroup rg_altura4 = (RadioGroup)getActivity().findViewById(R.id.altura4);
                                    int idAltura4=rg_altura4.getCheckedRadioButtonId();
                                    RadioButton radioButtonAltura4 = (RadioButton)getActivity().findViewById(idAltura4);
                                    String altura4=radioButtonAltura4.getText().toString();

                                    RadioGroup rg_candados = (RadioGroup)getActivity().findViewById(R.id.candados);
                                    int idCandados=rg_candados.getCheckedRadioButtonId();
                                    RadioButton radioButtonCandados = (RadioButton)getActivity().findViewById(idCandados);
                                    String candados=radioButtonCandados.getText().toString();

                                    RadioGroup rg_todosLosEquipos = (RadioGroup)getActivity().findViewById(R.id.TodosLosEquipos);
                                    int idtodosLosEquipos=rg_todosLosEquipos.getCheckedRadioButtonId();
                                    RadioButton radioButtontodosLosEquipos = (RadioButton)getActivity().findViewById(idtodosLosEquipos);
                                    String todosLosEquipos=radioButtontodosLosEquipos.getText().toString();

                                    /*RadioGroup rg_aislacionEnergia1 = (RadioGroup)getActivity().findViewById(R.id.aislacionEnergia1);
                                    int idAislacionEnergia1=rg_aislacionEnergia1.getCheckedRadioButtonId();
                                    RadioButton radioButtonAislacionEnergia1 = (RadioButton)getActivity().findViewById(idAislacionEnergia1);
                                    String aislacionEnergia1=radioButtonAislacionEnergia1.getText().toString();

                                    RadioGroup rg_aislacionEnergia2 = (RadioGroup)getActivity().findViewById(R.id.aislacionEnergia2);
                                    int idAislacionEnergia2=rg_aislacionEnergia2.getCheckedRadioButtonId();
                                    RadioButton radioButtonAislacionEnergia2 = (RadioButton)getActivity().findViewById(idAislacionEnergia2);
                                    String aislacionEnergia2=radioButtonAislacionEnergia2.getText().toString();*/

                                    RadioGroup rg_aislacionEnergia3 = (RadioGroup)getActivity().findViewById(R.id.aislacionEnergia3);
                                    int idAislacionEnergia3=rg_aislacionEnergia3.getCheckedRadioButtonId();
                                    RadioButton radioButtonAislacionEnergia3 = (RadioButton)getActivity().findViewById(idAislacionEnergia3);
                                    String aislacionEnergia3=radioButtonAislacionEnergia3.getText().toString();

                                    RadioGroup rg_aislacionEnergia4 = (RadioGroup)getActivity().findViewById(R.id.aislacionEnergia4);
                                    int idAislacionEnergia4=rg_aislacionEnergia4.getCheckedRadioButtonId();
                                    RadioButton radioButtonAislacionEnergia4 = (RadioButton)getActivity().findViewById(idAislacionEnergia4);
                                    String aislacionEnergia4=radioButtonAislacionEnergia4.getText().toString();

                                    RadioGroup rg_espacioConfinado1 = (RadioGroup)getActivity().findViewById(R.id.espacioConfinado1);
                                    int idEspacioConfinado1=rg_espacioConfinado1.getCheckedRadioButtonId();
                                    RadioButton radioButtonEspacioConfinado1 = (RadioButton)getActivity().findViewById(idEspacioConfinado1);
                                    String espacioConfinado1=radioButtonEspacioConfinado1.getText().toString();

                                    RadioGroup rg_otros1 = (RadioGroup)getActivity().findViewById(R.id.otros1);
                                    int idOtro1=rg_otros1.getCheckedRadioButtonId();
                                    RadioButton radioButtonOtros1 = (RadioButton)getActivity().findViewById(idOtro1);
                                    String otros1=radioButtonOtros1.getText().toString();

                                    RadioGroup rg_otros2 = (RadioGroup)getActivity().findViewById(R.id.otros2);
                                    int idOtro2=rg_otros2.getCheckedRadioButtonId();
                                    RadioButton radioButtonOtros2 = (RadioButton)getActivity().findViewById(idOtro2);
                                    String otros2=radioButtonOtros2.getText().toString();

                                    RadioGroup rg_otros3 = (RadioGroup)getActivity().findViewById(R.id.otros3);
                                    int idOtro3=rg_otros3.getCheckedRadioButtonId();
                                    RadioButton radioButtonOtros3 = (RadioButton)getActivity().findViewById(idOtro3);
                                    String otros3=radioButtonOtros3.getText().toString();

                                    /*RadioGroup rg_otros4 = (RadioGroup)getActivity().findViewById(R.id.otros4);
                                    int idOtro4=rg_otros4.getCheckedRadioButtonId();
                                    RadioButton radioButtonOtros4 = (RadioButton)getActivity().findViewById(idOtro4);
                                    String otros4=radioButtonOtros4.getText().toString();*/

                                    RadioGroup rg_AreaTrabajoLimpia=(RadioGroup)getActivity().findViewById(R.id.AreaTrabajoLimpia);
                                    int idAreaTrabajoLimpia=rg_AreaTrabajoLimpia.getCheckedRadioButtonId();
                                    RadioButton radioButtonAreaTrabajoLimpia=(RadioButton)getActivity().findViewById(idAreaTrabajoLimpia);
                                    String areaTrabajoLimpia=radioButtonAreaTrabajoLimpia.getText().toString();

                                    CheckBox[] checkBoxes;
                                    Integer[] checkBoxesID={R.id.checkbox6, R.id.checkbox7, R.id.checkbox8, R.id.checkbox9, R.id.checkbox10, R.id.checkbox11, R.id.checkbox12};

                                    checkBoxes = new CheckBox[checkBoxesID.length];
                                    String tipoEmpleado="";
                                    for(int i=0; i< checkBoxesID.length; i++){
                                        checkBoxes[i] = getActivity().findViewById(checkBoxesID[i]);
                                        //checkBoxes[i].setOnCheckedChangeListener(this);
                                        if(checkBoxes[i].isChecked()){
                                            tipoEmpleado +=checkBoxes[i].getText().toString()+"/";
                                        }
                                    }

                                    RadioGroup rg_HerramientasAdecuadas = (RadioGroup)getActivity().findViewById(R.id.HerramientasAdecuadas);
                                    int idHerramientasAdecuadas=rg_HerramientasAdecuadas.getCheckedRadioButtonId();
                                    RadioButton radioButtonHerramientasAdecuadas = (RadioButton)getActivity().findViewById(idHerramientasAdecuadas);
                                    String HerramientasAdecuadas=radioButtonHerramientasAdecuadas.getText().toString();

                                    RadioGroup rg_usoHerramientas = (RadioGroup)getActivity().findViewById(R.id.UsoHerramientas);
                                    int idusoHerramientas=rg_usoHerramientas.getCheckedRadioButtonId();
                                    RadioButton radioButtonusoHerramientas = (RadioButton)getActivity().findViewById(idusoHerramientas);
                                    String usoHerramientas=radioButtonusoHerramientas.getText().toString();

                                    RadioGroup rg_HerramientasEstado = (RadioGroup)getActivity().findViewById(R.id.HerramientasEstado);
                                    int idHerramientasEstado=rg_HerramientasEstado.getCheckedRadioButtonId();
                                    RadioButton radioButtonHerramientasEstado = (RadioButton)getActivity().findViewById(idHerramientasEstado);
                                    String HerramientasEstado=radioButtonHerramientasEstado.getText().toString();


                                    boolean resultado=new ControladorStop(getContext()).Insert(txtObservaciones, txtInteracciones, txtLugar,epp1,epp2,epp3,montacargas1,
                                            montacargas2, montacargas3, montacargas4, montacargas5,montacargas6, vehiculo1, vehiculo2, vehiculo3, vehiculo4,
                                            posiciones1, posiciones2, riesgos1, riesgos4,altura1,altura2,altura4,aislacionEnergia3,aislacionEnergia4,espacioConfinado1, otros1, otros2, otros3,
                                            tipoEmpleado, spinnerPersonas, spinnerTareaSeleccion, spinnerSectorSeleccion, spinnerSiteSeleccion, areaTrabajoLimpia, candados, todosLosEquipos,
                                            RiesgoCaida, HerramientasAdecuadas, usoHerramientas, HerramientasEstado, usuario);

                                    if(resultado){
                                        Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_LONG).show();
                                        new Syncronizer(getActivity(),"setBBP").execute();
                                    }else{
                                        Toast.makeText(getActivity(),"No se guardó correctamente.",Toast.LENGTH_LONG).show();
                                    }
                                    getActivity().onBackPressed();
                                }
                                catch(Exception e){
                                    Toast.makeText(getActivity(),"Error al guardar los datos. " + e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                    }

                }
        );
    }


    public interface OnFragmentInteractionListener {
    }
}

