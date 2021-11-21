package com.example.corteva.fragments;

import android.app.DatePickerDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.example.corteva.ControladorPruebasACampo;
import com.example.corteva.ControladorResponsables;
import com.example.corteva.ControladorSectores;
import com.example.corteva.ControladorSites;
import com.example.corteva.DatePickerFragment;
import com.example.corteva.R;
import com.example.corteva.SaveSharedPreference;
import com.example.corteva.Syncronizer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PruebasACampoFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    Spinner spinnerSite, spinnerObservadorUno, spinnerSector,
            spinnerResponsableAlcance, spinnerResponsableCategoria, spinnerResponsableAtributos, spinnerResponsableRiesgosYPrecauciones,
            spinnerResponsableEPP, spinnerResponsableHerramientas, spinnerResponsablePersonalMinimo, spinnerResponsableDesviaciones,
            spinnerResponsablePasoAPaso, spinnerResponsableDocumentosRelacionados, spinnerResponsableControlRegistro,
            spinnerResponsableValidacion, spinnerResponsableAprobado, spinnerResponsableMOC, spinnerResponsablePruebaMOC,
            spinnerResponsableUltimaRevision, spinnerResponsableCopiaControlada, spinnerResponsableTest;

    EditText etObservador2, etEjecutante, etFecha, etNombreProcedimiento,
            etObservacionesAlcance, etPropuestaMejoraAlcance, etAccionAlcance,
            etObservacionesCategoria, etPropuestaMejoraCategoria, etAccionCategoria,
            etObservacionesAtributos, etPropuestaMejoraAtributos, etAccionAtributos,
            etObservacionesRiesgosYPrecauciones, etPropuestaMejoraRiesgosYPrecauciones, etAccionRiesgosYPrecauciones,
            etObservacionesEPP, etPropuestaMejoraEPP, etAccionEPP,
            etObservacionesHerramientas, etPropuestaMejoraHerramientas, etAccionHerramientas,
            etObservacionesPersonalMinimo, etPropuestaMejoraPersonalMinimo, etAccionPersonalMinimo,
            etObservacionesDesviaciones, etPropuestaMejoraDesviaciones, etAccionDesviaciones,
            etObservacionesPasoAPaso, etPropuestaMejoraPasoAPaso, etAccionPasoAPaso,
            etObservacionesDocumentosRelacionados, etPropuestaMejoraDocumentosRelacionados, etAccionDocumentosRelacionados,
            etObservacionesControlRegistro, etPropuestaMejoraControlRegistro, etAccionControlRegistro,
            etObservacionesValidacion, etPropuestaMejoraValidacion, etAccionValidacion,
            etObservacionesAprobado, etPropuestaMejoraAprobado, etAccionAprobado,
            etObservacionesMOC, etPropuestaMejoraMOC, etAccionMOC,
            etObservacionesPruebaMOC, etPropuestaMejoraPruebaMOC, etAccionPruebaMOC,
            etObservacionesUltimaRevision, etPropuestaMejoraUltimaRevision, etAccionUltimaRevision,
            etObservacionesCopiaControlada, etPropuestaMejoraCopiaControlada, etAccionCopiaControlada,
            etObservacionesTest, etPropuestaMejoraTest, etAccionTest;

    TextView etPorcentaje, etValorReferenciaAlcance,etValorReferenciaCategoria,etValorReferenciaAtributos, etValorReferenciaRiesgosYPrecauciones,etValorReferenciaEPP,
            etValorReferenciaHerramientas,etValorReferenciaPersonalMinimo,etValorReferenciaDesviaciones,etValorReferenciaPasoAPaso,
            etValorReferenciaDocumentosRelacionados,etValorReferenciaControlRegistro,etValorReferenciaValidacion,etValorReferenciaAprobado,
            etValorReferenciaMOC,etValorReferenciaPruebaMOC,etValorReferenciaUltimaRevision,etValorReferenciaCopiaControlada,etValorReferenciaTest;

    RadioGroup rg_Alcance, rg_Categoria, rg_Atributos, rg_RiesgosYPrecauciones, rg_EPP, rg_Herramientas, rg_PersonalMinimo, rg_Desviaciones,
            rg_PasoAPaso, rg_DocumentosRelacionados, rg_ControlRegistro, rg_Validacion, rg_Aprobado, rg_MOC, rg_PruebaMOC, rg_UltimaRevision,
            rg_CopiaControlada, rg_Test;

    Integer valor_Alcance, valor_Categoria, valor_Atributos, valor_RiesgosYPrecauciones, valor_EPP, valor_Herramientas, valor_PersonalMinimo, valor_Desviaciones,
            valor_PasoAPaso, valor_DocumentosRelacionados, valor_ControlRegistro, valor_Validacion, valor_Aprobado, valor_MOC, valor_PruebaMOC, valor_UltimaRevision,
            valor_CopiaControlada, valor_Test;

    Integer target = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pruebas_acampo, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        //Inicializo la variables
        valor_Alcance =  valor_Categoria =  valor_Atributos =  valor_RiesgosYPrecauciones =  valor_EPP =  valor_Herramientas =
                valor_PersonalMinimo =  valor_Desviaciones = valor_PasoAPaso =  valor_DocumentosRelacionados =  valor_ControlRegistro =
                        valor_Validacion =  valor_Aprobado =  valor_MOC =  valor_PruebaMOC =  valor_UltimaRevision =
                                valor_CopiaControlada =  valor_Test = 0;
        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");
        spinnerSite=getActivity().findViewById(R.id.spinnerSitePruebasACampo);
        spinnerObservadorUno=getActivity().findViewById(R.id.spinnerObservador1);
        etObservador2=getActivity().findViewById(R.id.etObservador2);
        etEjecutante=getActivity().findViewById(R.id.etEjecutante);
        spinnerSector=getActivity().findViewById(R.id.spinnerSectorPruebaACampo);
        spinnerResponsableAlcance=getActivity().findViewById(R.id.spinnerResponsableAlcance);
        spinnerResponsableCategoria=getActivity().findViewById(R.id.spinnerResponsableCategoria);
        spinnerResponsableAtributos=getActivity().findViewById(R.id.spinnerResponsableAtributos);
        spinnerResponsableRiesgosYPrecauciones=getActivity().findViewById(R.id.spinnerResponsableRiesgosYPrecauciones);
        spinnerResponsableEPP=getActivity().findViewById(R.id.spinnerResponsableEPP);
        spinnerResponsableHerramientas=getActivity().findViewById(R.id.spinnerResponsableHerramientas);
        spinnerResponsablePersonalMinimo=getActivity().findViewById(R.id.spinnerResponsablePersonalMinimo);
        spinnerResponsableDesviaciones=getActivity().findViewById(R.id.spinnerResponsableDesviaciones);
        spinnerResponsablePasoAPaso=getActivity().findViewById(R.id.spinnerResponsablePasoAPaso);
        spinnerResponsableDocumentosRelacionados=getActivity().findViewById(R.id.spinnerResponsableDocumentosRelacionados);
        spinnerResponsableControlRegistro=getActivity().findViewById(R.id.spinnerResponsableControlRegistro);
        spinnerResponsableValidacion=getActivity().findViewById(R.id.spinnerResponsableValidacion);
        spinnerResponsableAprobado=getActivity().findViewById(R.id.spinnerResponsableAprobado);
        spinnerResponsableMOC=getActivity().findViewById(R.id.spinnerResponsableMOC);
        spinnerResponsablePruebaMOC=getActivity().findViewById(R.id.spinnerResponsablePruebaMOC);
        spinnerResponsableUltimaRevision=getActivity().findViewById(R.id.spinnerResponsableUltimaRevision);
        spinnerResponsableCopiaControlada=getActivity().findViewById(R.id.spinnerResponsableCopiaControlada);
        spinnerResponsableTest=getActivity().findViewById(R.id.spinnerResponsableTest);

        etFecha=getActivity().findViewById(R.id.etFechaPruebaACampo);
        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etFecha.setText(today);
        etNombreProcedimiento=getActivity().findViewById(R.id.etNombreProcedimiento);
        etPorcentaje = getActivity().findViewById(R.id.textViewPorcentaje);

        etValorReferenciaAlcance=getActivity().findViewById(R.id.etValorReferenciaAlcance);
        etObservacionesAlcance=getActivity().findViewById(R.id.etObservacionesAlcance);
        etPropuestaMejoraAlcance=getActivity().findViewById(R.id.etPropuestaDeMejoraAlcance);
        etAccionAlcance=getActivity().findViewById(R.id.etAccionAlcance);

        etValorReferenciaCategoria=getActivity().findViewById(R.id.etValorReferenciaCategoria);
        etObservacionesCategoria=getActivity().findViewById(R.id.etObservacionesCategoria);
        etPropuestaMejoraCategoria=getActivity().findViewById(R.id.etPropuestaDeMejoraCategoria);
        etAccionCategoria=getActivity().findViewById(R.id.etAccionCategoria);

        etValorReferenciaAtributos=getActivity().findViewById(R.id.etValorReferenciaAtributos);
        etObservacionesAtributos=getActivity().findViewById(R.id.etObservacionesAtributos);
        etPropuestaMejoraAtributos=getActivity().findViewById(R.id.etPropuestaDeMejoraAtributos);
        etAccionAtributos=getActivity().findViewById(R.id.etAccionAtributos);

        etValorReferenciaRiesgosYPrecauciones=getActivity().findViewById(R.id.etValorReferenciaRiesgosYPrecauciones);
        etObservacionesRiesgosYPrecauciones=getActivity().findViewById(R.id.etObservacionesRiesgosYPrecauciones);
        etPropuestaMejoraRiesgosYPrecauciones=getActivity().findViewById(R.id.etPropuestaDeMejoraRiesgosYPrecauciones);
        etAccionRiesgosYPrecauciones=getActivity().findViewById(R.id.etAccionRiesgosYPrecauciones);

        etValorReferenciaEPP=getActivity().findViewById(R.id.etValorReferenciaEPP);
        etObservacionesEPP=getActivity().findViewById(R.id.etObservacionesEPP);
        etPropuestaMejoraEPP=getActivity().findViewById(R.id.etPropuestaDeMejoraEPP);
        etAccionEPP=getActivity().findViewById(R.id.etAccionEPP);

        etValorReferenciaHerramientas=getActivity().findViewById(R.id.etValorReferenciaHerramientas);
        etObservacionesHerramientas=getActivity().findViewById(R.id.etObservacionesHerramientas);
        etPropuestaMejoraHerramientas=getActivity().findViewById(R.id.etPropuestaDeMejoraHerramientas);
        etAccionHerramientas=getActivity().findViewById(R.id.etAccionHerramientas);

        etValorReferenciaPersonalMinimo=getActivity().findViewById(R.id.etValorReferenciaPersonalMinimo);
        etObservacionesPersonalMinimo=getActivity().findViewById(R.id.etObservacionesPersonalMinimo);
        etPropuestaMejoraPersonalMinimo=getActivity().findViewById(R.id.etPropuestaDeMejoraPersonalMinimo);
        etAccionPersonalMinimo=getActivity().findViewById(R.id.etAccionPersonalMinimo);

        etValorReferenciaDesviaciones=getActivity().findViewById(R.id.etValorReferenciaDesviaciones);
        etObservacionesDesviaciones=getActivity().findViewById(R.id.etObservacionesDesviaciones);
        etPropuestaMejoraDesviaciones=getActivity().findViewById(R.id.etPropuestaDeMejoraDesviaciones);
        etAccionDesviaciones=getActivity().findViewById(R.id.etAccionDesviaciones);

        etValorReferenciaPasoAPaso=getActivity().findViewById(R.id.etValorReferenciaPasoAPaso);
        etObservacionesPasoAPaso=getActivity().findViewById(R.id.etObservacionesPasoAPaso);
        etPropuestaMejoraPasoAPaso=getActivity().findViewById(R.id.etPropuestaDeMejoraPasoAPaso);
        etAccionPasoAPaso=getActivity().findViewById(R.id.etAccionPasoAPaso);

        etValorReferenciaDocumentosRelacionados=getActivity().findViewById(R.id.etValorReferenciaDocumentosRelacionados);
        etObservacionesDocumentosRelacionados=getActivity().findViewById(R.id.etObservacionesDocumentosRelacionados);
        etPropuestaMejoraDocumentosRelacionados=getActivity().findViewById(R.id.etPropuestaDeMejoraDocumentosRelacionados);
        etAccionDocumentosRelacionados=getActivity().findViewById(R.id.etAccionDocumentosRelacionados);

        etValorReferenciaControlRegistro=getActivity().findViewById(R.id.etValorReferenciaControlRegistro);
        etObservacionesControlRegistro=getActivity().findViewById(R.id.etObservacionesControlRegistro);
        etPropuestaMejoraControlRegistro=getActivity().findViewById(R.id.etPropuestaDeMejoraControlRegistro);
        etAccionControlRegistro=getActivity().findViewById(R.id.etAccionControlRegistro);

        etValorReferenciaValidacion=getActivity().findViewById(R.id.etValorReferenciaValidacion);
        etObservacionesValidacion=getActivity().findViewById(R.id.etObservacionesValidacion);
        etPropuestaMejoraValidacion=getActivity().findViewById(R.id.etPropuestaDeMejoraValidacion);
        etAccionValidacion=getActivity().findViewById(R.id.etAccionValidacion);

        etValorReferenciaAprobado=getActivity().findViewById(R.id.etValorReferenciaAprobado);
        etObservacionesAprobado=getActivity().findViewById(R.id.etObservacionesAprobado);
        etPropuestaMejoraAprobado=getActivity().findViewById(R.id.etPropuestaDeMejoraAprobado);
        etAccionAprobado=getActivity().findViewById(R.id.etAccionAprobado);

        etValorReferenciaMOC=getActivity().findViewById(R.id.etValorReferenciaMOC);
        etObservacionesMOC=getActivity().findViewById(R.id.etObservacionesMOC);
        etPropuestaMejoraMOC=getActivity().findViewById(R.id.etPropuestaDeMejoraMOC);
        etAccionMOC=getActivity().findViewById(R.id.etAccionMOC);

        etValorReferenciaPruebaMOC=getActivity().findViewById(R.id.etValorReferenciaPruebaMOC);
        etObservacionesPruebaMOC=getActivity().findViewById(R.id.etObservacionesPruebaMOC);
        etPropuestaMejoraPruebaMOC=getActivity().findViewById(R.id.etPropuestaDeMejoraPruebaMOC);
        etAccionPruebaMOC=getActivity().findViewById(R.id.etAccionPruebaMOC);

        etValorReferenciaUltimaRevision=getActivity().findViewById(R.id.etValorReferenciaUltimaRevision);
        etObservacionesUltimaRevision=getActivity().findViewById(R.id.etObservacionesUltimaRevision);
        etPropuestaMejoraUltimaRevision=getActivity().findViewById(R.id.etPropuestaDeMejoraUltimaRevision);
        etAccionUltimaRevision=getActivity().findViewById(R.id.etAccionUltimaRevision);

        etValorReferenciaCopiaControlada=getActivity().findViewById(R.id.etValorReferenciaCopiaControlada);
        etObservacionesCopiaControlada=getActivity().findViewById(R.id.etObservacionesCopiaControlada);
        etPropuestaMejoraCopiaControlada=getActivity().findViewById(R.id.etPropuestaDeMejoraCopiaControlada);
        etAccionCopiaControlada=getActivity().findViewById(R.id.etAccionCopiaControlada);

        etValorReferenciaTest=getActivity().findViewById(R.id.etValorReferenciaTest);
        etObservacionesTest=getActivity().findViewById(R.id.etObservacionesTest);
        etPropuestaMejoraTest=getActivity().findViewById(R.id.etPropuestaDeMejoraTest);
        etAccionTest=getActivity().findViewById(R.id.etAccionTest);

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
                ArrayList<String> listaSectores=new ControladorSectores(getContext()).getSectoresBySite(spinnerSiteSeleccion);
                ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSectores);
                spinnerSector.setAdapter(adapter1);

                ArrayList<String> observador1=new ControladorResponsables(getContext()).getResponsablesBySite(spinnerSiteSeleccion);

                ArrayAdapter<String> adapterEmpleados=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerObservadorUno.setAdapter(adapterEmpleados);

                ArrayAdapter<String> adapterresponsableAlcance=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableAlcance.setAdapter(adapterresponsableAlcance);

                ArrayAdapter<String> adapterresponsableCategoria=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableCategoria.setAdapter(adapterresponsableCategoria);

                ArrayAdapter<String> adapterresponsableAtributos=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableAtributos.setAdapter(adapterresponsableAtributos);

                ArrayAdapter<String> adapterresponsableRiesgosYPrecauciones=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableRiesgosYPrecauciones.setAdapter(adapterresponsableRiesgosYPrecauciones);

                ArrayAdapter<String> adapterresponsableEPP=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableEPP.setAdapter(adapterresponsableEPP);

                ArrayAdapter<String> adapterresponsableHerramientas=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableHerramientas.setAdapter(adapterresponsableHerramientas);

                ArrayAdapter<String> adapterresponsablePersonalMinimo=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsablePersonalMinimo.setAdapter(adapterresponsablePersonalMinimo);

                ArrayAdapter<String> adapterresponsableDesviaciones=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableDesviaciones.setAdapter(adapterresponsableDesviaciones);

                ArrayAdapter<String> adapterresponsablePasoAPaso=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsablePasoAPaso.setAdapter(adapterresponsablePasoAPaso);

                ArrayAdapter<String> adapterresponsableDocumentosRelacionados=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableDocumentosRelacionados.setAdapter(adapterresponsableDocumentosRelacionados);

                ArrayAdapter<String> adapterresponsableControlRegistro=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableControlRegistro.setAdapter(adapterresponsableControlRegistro);

                ArrayAdapter<String> adapterresponsableValidacion=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableValidacion.setAdapter(adapterresponsableValidacion);

                ArrayAdapter<String> adapterresponsableAprobado=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableAprobado.setAdapter(adapterresponsableAprobado);

                ArrayAdapter<String> adapterresponsableMOC=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableMOC.setAdapter(adapterresponsableMOC);

                ArrayAdapter<String> adapterresponsablePruebaMOC=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsablePruebaMOC.setAdapter(adapterresponsablePruebaMOC);

                ArrayAdapter<String> adapterresponsableUltimaRevision=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableUltimaRevision.setAdapter(adapterresponsableUltimaRevision);

                ArrayAdapter<String> adapterresponsableCopiaControlada=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableCopiaControlada.setAdapter(adapterresponsableCopiaControlada);

                ArrayAdapter<String> adapterresponsableTest=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,observador1);
                spinnerResponsableTest.setAdapter(adapterresponsableTest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //CALENDARIO
        etFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(PruebasACampoFragment.this, 0);
                        datePicker.show(getFragmentManager(), "date picker");
                        break;
                }
                return false;
            }
        });

        //Hacer visibles los menu
        //Alcance
        TextView textView_Alcance = getActivity().findViewById(R.id.textView_Alcance);
        textView_Alcance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Alcance);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_Alcance = (RadioGroup)getActivity().findViewById(R.id.Alcance);
        rg_Alcance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_Alcance = Integer.parseInt(etValorReferenciaAlcance.getText().toString());
                }else{
                    valor_Alcance = 0;
                }
                CalcularTarget();
            }
        });

        //Categoria
        TextView textView_Categoria = getActivity().findViewById(R.id.textView_Categoria);
        textView_Categoria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Categoria);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_Categoria = (RadioGroup)getActivity().findViewById(R.id.Categoria);
        rg_Categoria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_Categoria = Integer.parseInt(etValorReferenciaCategoria.getText().toString());
                }else{
                    valor_Categoria = 0;
                }
                CalcularTarget();
            }
        });

        //Atributos
        TextView textView_Atributos = getActivity().findViewById(R.id.textView_Atributos);
        textView_Atributos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Atributos);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_Atributos = (RadioGroup)getActivity().findViewById(R.id.Atributos);
        rg_Atributos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_Atributos = Integer.parseInt(etValorReferenciaAtributos.getText().toString());
                }else{
                    valor_Atributos = 0;
                }
                CalcularTarget();
            }
        });

        //Riesgos y Precauciones
        TextView textView_RiesgosYPrecauciones = getActivity().findViewById(R.id.textView_RiesgosYPrecauciones);
        textView_RiesgosYPrecauciones.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_RiesgosYPrecauciones);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_RiesgosYPrecauciones = (RadioGroup)getActivity().findViewById(R.id.RiesgosYPrecauciones);
        rg_RiesgosYPrecauciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_RiesgosYPrecauciones = Integer.parseInt(etValorReferenciaRiesgosYPrecauciones.getText().toString());
                }else{
                    valor_RiesgosYPrecauciones = 0;
                }
                CalcularTarget();
            }
        });

        //EPP
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
        rg_EPP = (RadioGroup)getActivity().findViewById(R.id.EPP);
        rg_EPP.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_EPP = Integer.parseInt(etValorReferenciaEPP.getText().toString());
                }else{
                    valor_EPP = 0;
                }
                CalcularTarget();
            }
        });

        //Herramientas
        TextView textView_Herramientas = getActivity().findViewById(R.id.textView_HerraminetasPruebaACampo);
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
        rg_Herramientas = (RadioGroup)getActivity().findViewById(R.id.Herramientas);
        rg_Herramientas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_Herramientas = Integer.parseInt(etValorReferenciaHerramientas.getText().toString());
                }else{
                    valor_Herramientas  = 0;
                }
                CalcularTarget();
            }
        });

        //Personal Minimo
        TextView textView_PersonalMinimo = getActivity().findViewById(R.id.textView_PersonalMinimo);
        textView_PersonalMinimo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_PersonalMinimo);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_PersonalMinimo = (RadioGroup)getActivity().findViewById(R.id.PersonalMinimo);
        rg_PersonalMinimo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_PersonalMinimo = Integer.parseInt(etValorReferenciaPersonalMinimo.getText().toString());
                }else{
                    valor_PersonalMinimo = 0;
                }
                CalcularTarget();
            }
        });

        //Desviaciones
        TextView textView_Desviaciones = getActivity().findViewById(R.id.textView_Desviaciones);
        textView_Desviaciones.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Desviaciones);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_Desviaciones = (RadioGroup)getActivity().findViewById(R.id.Desviaciones);
        rg_Desviaciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_Desviaciones = Integer.parseInt(etValorReferenciaDesviaciones.getText().toString());
                }else{
                    valor_Desviaciones = 0;
                }
                CalcularTarget();
            }
        });

        //Paso a paso
        TextView textView_PasoAPaso = getActivity().findViewById(R.id.textView_PasoAPaso);
        textView_PasoAPaso.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_PasoAPaso);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_PasoAPaso = (RadioGroup)getActivity().findViewById(R.id.PasoAPaso);
        rg_PasoAPaso.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_PasoAPaso = Integer.parseInt(etValorReferenciaPasoAPaso.getText().toString());
                }else{
                    valor_PasoAPaso = 0;
                }
                CalcularTarget();
            }
        });

        //Documentos Relacionados
        TextView textView_DocumentosRelacionados = getActivity().findViewById(R.id.textView_DocumentosRelacionados);
        textView_DocumentosRelacionados.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_DocumentosRelacionados);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_DocumentosRelacionados = (RadioGroup)getActivity().findViewById(R.id.DocumentosRelacionados);
        rg_DocumentosRelacionados.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_DocumentosRelacionados = Integer.parseInt(etValorReferenciaDocumentosRelacionados.getText().toString());
                }else{
                    valor_DocumentosRelacionados = 0;
                }
                CalcularTarget();
            }
        });

        //Control Registro
        TextView textView_ControlRegistro = getActivity().findViewById(R.id.textView_ControlRegistro);
        textView_ControlRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_ControlRegistro);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_ControlRegistro = (RadioGroup)getActivity().findViewById(R.id.ControlRegistro);
        rg_ControlRegistro.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_ControlRegistro = Integer.parseInt(etValorReferenciaControlRegistro.getText().toString());
                }else{
                    valor_ControlRegistro = 0;
                }
                CalcularTarget();
            }
        });

        //Validacion
        TextView textView_Validacion = getActivity().findViewById(R.id.textView_Validacion);
        textView_Validacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Validacion);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_Validacion = (RadioGroup)getActivity().findViewById(R.id.Validacion);
        rg_Validacion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_Validacion = Integer.parseInt(etValorReferenciaValidacion.getText().toString());
                }else{
                    valor_Validacion = 0;
                }
                CalcularTarget();
            }
        });

        //Aprobado
        TextView textView_Aprobado = getActivity().findViewById(R.id.textView_Aprobado);
        textView_Aprobado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Aprobado);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_Aprobado = (RadioGroup)getActivity().findViewById(R.id.Aprobado);
        rg_Aprobado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_Aprobado = Integer.parseInt(etValorReferenciaAprobado.getText().toString());
                }else{
                    valor_Aprobado = 0;
                }
                CalcularTarget();
            }
        });

        //MOC
        TextView textView_MOC = getActivity().findViewById(R.id.textView_MOC);
        textView_MOC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_MOC);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_MOC = (RadioGroup)getActivity().findViewById(R.id.MOC);
        rg_MOC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_MOC = Integer.parseInt(etValorReferenciaMOC.getText().toString());
                }else{
                    valor_MOC = 0;
                }
                CalcularTarget();
            }
        });

        //Prueba MOC
        TextView textView_PruebaMOC = getActivity().findViewById(R.id.textView_PruebaMOC);
        textView_PruebaMOC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_PruebaMOC);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_PruebaMOC = (RadioGroup)getActivity().findViewById(R.id.PruebaMOC);
        rg_PruebaMOC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_PruebaMOC = Integer.parseInt(etValorReferenciaPruebaMOC.getText().toString());
                }else{
                    valor_PruebaMOC = 0;
                }
                CalcularTarget();
            }
        });

        //Ultima Revision
        TextView textView_UltimaRevision = getActivity().findViewById(R.id.textView_UltimaRevision);
        textView_UltimaRevision.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_UltimaRevision);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_UltimaRevision = (RadioGroup)getActivity().findViewById(R.id.UltimaRevision);
        rg_UltimaRevision.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_UltimaRevision = Integer.parseInt(etValorReferenciaUltimaRevision.getText().toString());
                }else{
                    valor_UltimaRevision = 0;
                }
                CalcularTarget();
            }
        });

        //Copia Controlada
        TextView textView_CopiaControlada = getActivity().findViewById(R.id.textView_CopiaControlada);
        textView_CopiaControlada.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_CopiaControlada);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_CopiaControlada = (RadioGroup)getActivity().findViewById(R.id.CopiaControlada);
        rg_CopiaControlada.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_CopiaControlada = Integer.parseInt(etValorReferenciaCopiaControlada.getText().toString());
                }else{
                    valor_CopiaControlada = 0;
                }
                CalcularTarget();
            }
        });

        //Test
        TextView textView_Test = getActivity().findViewById(R.id.textView_Test);
        textView_Test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Test);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        rg_Test = (RadioGroup)getActivity().findViewById(R.id.Test);
        rg_Test.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)getActivity().findViewById(checkedId);
                if(rb.getText().toString().contains("Si")){
                    valor_Test = Integer.parseInt(etValorReferenciaTest.getText().toString());
                }else{
                    valor_Test = 0;
                }
                CalcularTarget();
            }
        });

        Button botonGuardar=getActivity().findViewById(R.id.botonGuardarPruebaACampo);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    String spinnerSiteSeleccion=spinnerSite.getSelectedItem().toString();
                    String spinnerObservadorUnoSeleccion=spinnerObservadorUno.getSelectedItem().toString();
                    String spinnerObservadorDosSeleccion=etObservador2.getText().toString();
                    String spinnerSectorSeleccion=spinnerSector.getSelectedItem().toString();
                    String spinnerEjecutanteSeleccion=etEjecutante.getText().toString();

                    String txtNombreProcedimiento=etNombreProcedimiento.getText().toString().trim();
                    String txtFecha = etFecha.getText().toString().trim();

                    //Alcance
                    int idAlcance =rg_Alcance.getCheckedRadioButtonId();
                    RadioButton radioButtonAlcance = (RadioButton)getActivity().findViewById(idAlcance);
                    String Alcance=radioButtonAlcance.getText().toString();

                    String txtValorDeReferenciaAlcance=etValorReferenciaAlcance.getText().toString();
                    String txtObservacionesAlcance=etObservacionesAlcance.getText().toString();
                    String txtPropuestaDeMejoraAlcance=etPropuestaMejoraAlcance.getText().toString();
                    String txtAccionAlcance=etAccionAlcance.getText().toString();
                    String spinnerResponsableAlcanceSeleccion=spinnerResponsableAlcance.getSelectedItem().toString();

                    //Categoria
                    int idCategoria =rg_Categoria.getCheckedRadioButtonId();
                    RadioButton radioButtonCategoria = (RadioButton)getActivity().findViewById(idCategoria);
                    String Categoria=radioButtonCategoria.getText().toString();

                    String txtValorDeReferenciaCategoria=etValorReferenciaCategoria.getText().toString();
                    String txtObservacionesCategoria=etObservacionesCategoria.getText().toString();
                    String txtPropuestaDeMejoraCategoria=etPropuestaMejoraCategoria.getText().toString();
                    String txtAccionCategoria=etAccionCategoria.getText().toString();
                    String spinnerResponsableCategoriaSeleccion=spinnerResponsableCategoria.getSelectedItem().toString();

                    //Atributos
                    int idAtributos =rg_Atributos.getCheckedRadioButtonId();
                    RadioButton radioButtonAtributos = (RadioButton)getActivity().findViewById(idAtributos);
                    String Atributos=radioButtonAtributos.getText().toString();

                    String txtValorDeReferenciaAtributos=etValorReferenciaAtributos.getText().toString();
                    String txtObservacionesAtributos=etObservacionesAtributos.getText().toString();
                    String txtPropuestaDeMejoraAtributos=etPropuestaMejoraAtributos.getText().toString();
                    String txtAccionAtributos=etAccionAtributos.getText().toString();
                    String spinnerResponsableAtributosSeleccion=spinnerResponsableAtributos.getSelectedItem().toString();

                    //Riesgos y Precauciones
                    int idRiesgosYPrecauciones =rg_RiesgosYPrecauciones.getCheckedRadioButtonId();
                    RadioButton radioButtonRiesgosYPrecauciones = (RadioButton)getActivity().findViewById(idRiesgosYPrecauciones);
                    String RiesgosYPrecauciones=radioButtonRiesgosYPrecauciones.getText().toString();

                    String txtValorDeReferenciaRiesgosYPrecauciones=etValorReferenciaRiesgosYPrecauciones.getText().toString();
                    String txtObservacionesRiesgosYPrecauciones=etObservacionesRiesgosYPrecauciones.getText().toString();
                    String txtPropuestaDeMejoraRiesgosYPrecauciones=etPropuestaMejoraRiesgosYPrecauciones.getText().toString();
                    String txtAccionRiesgosYPrecauciones=etAccionRiesgosYPrecauciones.getText().toString();
                    String spinnerResponsableRiesgosYPrecaucionesSeleccion=spinnerResponsableRiesgosYPrecauciones.getSelectedItem().toString();

                    //EPP
                    int idEPP =rg_EPP.getCheckedRadioButtonId();
                    RadioButton radioButtonEPP = (RadioButton)getActivity().findViewById(idEPP);
                    String EPP=radioButtonEPP.getText().toString();

                    String txtValorDeReferenciaEPP=etValorReferenciaEPP.getText().toString();
                    String txtObservacionesEPP=etObservacionesEPP.getText().toString();
                    String txtPropuestaDeMejoraEPP=etPropuestaMejoraEPP.getText().toString();
                    String txtAccionEPP=etAccionEPP.getText().toString();
                    String spinnerResponsableEPPSeleccion=spinnerResponsableEPP.getSelectedItem().toString();

                    //Herramientas
                    int idHerramientas =rg_Herramientas.getCheckedRadioButtonId();
                    RadioButton radioButtonHerramientas = (RadioButton)getActivity().findViewById(idHerramientas);
                    String Herramientas=radioButtonHerramientas.getText().toString();

                    String txtValorDeReferenciaHerramientas=etValorReferenciaHerramientas.getText().toString();
                    String txtObservacionesHerramientas=etObservacionesHerramientas.getText().toString();
                    String txtPropuestaDeMejoraHerramientas=etPropuestaMejoraHerramientas.getText().toString();
                    String txtAccionHerramientas=etAccionHerramientas.getText().toString();
                    String spinnerResponsableHerramientasSeleccion=spinnerResponsableHerramientas.getSelectedItem().toString();

                    //Personal Minimo
                    int idPersonalMinimo =rg_PersonalMinimo.getCheckedRadioButtonId();
                    RadioButton radioButtonPersonalMinimo = (RadioButton)getActivity().findViewById(idPersonalMinimo);
                    String PersonalMinimo=radioButtonPersonalMinimo.getText().toString();

                    String txtValorDeReferenciaPersonalMinimo=etValorReferenciaPersonalMinimo.getText().toString();
                    String txtObservacionesPersonalMinimo=etObservacionesPersonalMinimo.getText().toString();
                    String txtPropuestaDeMejoraPersonalMinimo=etPropuestaMejoraPersonalMinimo.getText().toString();
                    String txtAccionPersonalMinimo=etAccionPersonalMinimo.getText().toString();
                    String spinnerResponsablePersonalMinimoSeleccion=spinnerResponsablePersonalMinimo.getSelectedItem().toString();

                    //Desviaciones
                    int idDesviaciones =rg_Desviaciones.getCheckedRadioButtonId();
                    RadioButton radioButtonDesviaciones = (RadioButton)getActivity().findViewById(idDesviaciones);
                    String Desviaciones=radioButtonDesviaciones.getText().toString();

                    String txtValorDeReferenciaDesviaciones=etValorReferenciaDesviaciones.getText().toString();
                    String txtObservacionesDesviaciones=etObservacionesDesviaciones.getText().toString();
                    String txtPropuestaDeMejoraDesviaciones=etPropuestaMejoraDesviaciones.getText().toString();
                    String txtAccionDesviaciones=etAccionDesviaciones.getText().toString();
                    String spinnerResponsableDesviacionesSeleccion=spinnerResponsableDesviaciones.getSelectedItem().toString();

                    //Paso a Paso
                    int idPasoAPaso =rg_PasoAPaso.getCheckedRadioButtonId();
                    RadioButton radioButtonPasoAPaso = (RadioButton)getActivity().findViewById(idPasoAPaso);
                    String PasoAPaso=radioButtonPasoAPaso.getText().toString();

                    String txtValorDeReferenciaPasoAPaso=etValorReferenciaPasoAPaso.getText().toString();
                    String txtObservacionesPasoAPaso=etObservacionesPasoAPaso.getText().toString();
                    String txtPropuestaDeMejoraPasoAPaso=etPropuestaMejoraPasoAPaso.getText().toString();
                    String txtAccionPasoAPaso=etAccionPasoAPaso.getText().toString();
                    String spinnerResponsablePasoAPasoSeleccion=spinnerResponsablePasoAPaso.getSelectedItem().toString();

                    //Documentos Relacionados
                    int idDocumentosRelacionados =rg_DocumentosRelacionados.getCheckedRadioButtonId();
                    RadioButton radioButtonDocumentosRelacionados = (RadioButton)getActivity().findViewById(idDocumentosRelacionados);
                    String DocumentosRelacionados=radioButtonDocumentosRelacionados.getText().toString();

                    String txtValorDeReferenciaDocumentosRelacionados=etValorReferenciaDocumentosRelacionados.getText().toString();
                    String txtObservacionesDocumentosRelacionados=etObservacionesDocumentosRelacionados.getText().toString();
                    String txtPropuestaDeMejoraDocumentosRelacionados=etPropuestaMejoraDocumentosRelacionados.getText().toString();
                    String txtAccionDocumentosRelacionados=etAccionDocumentosRelacionados.getText().toString();
                    String spinnerResponsableDocumentosRelacionadosSeleccion=spinnerResponsableDocumentosRelacionados.getSelectedItem().toString();

                    //Control Registro
                    int idControlRegistro =rg_ControlRegistro.getCheckedRadioButtonId();
                    RadioButton radioButtonControlRegistro = (RadioButton)getActivity().findViewById(idControlRegistro);
                    String ControlRegistro=radioButtonControlRegistro.getText().toString();

                    String txtValorDeReferenciaControlRegistro=etValorReferenciaControlRegistro.getText().toString();
                    String txtObservacionesControlRegistro=etObservacionesControlRegistro.getText().toString();
                    String txtPropuestaDeMejoraControlRegistro=etPropuestaMejoraControlRegistro.getText().toString();
                    String txtAccionControlRegistro=etAccionControlRegistro.getText().toString();
                    String spinnerResponsableControlRegistroSeleccion=spinnerResponsableControlRegistro.getSelectedItem().toString();

                    //Validacion
                    int idValidacion =rg_Validacion.getCheckedRadioButtonId();
                    RadioButton radioButtonValidacion = (RadioButton)getActivity().findViewById(idValidacion);
                    String Validacion=radioButtonValidacion.getText().toString();

                    String txtValorDeReferenciaValidacion=etValorReferenciaValidacion.getText().toString();
                    String txtObservacionesValidacion=etObservacionesValidacion.getText().toString();
                    String txtPropuestaDeMejoraValidacion=etPropuestaMejoraValidacion.getText().toString();
                    String txtAccionValidacion=etAccionValidacion.getText().toString();
                    String spinnerResponsableValidacionSeleccion=spinnerResponsableValidacion.getSelectedItem().toString();

                    //Aprobado
                    int idAprobado =rg_Aprobado.getCheckedRadioButtonId();
                    RadioButton radioButtonAprobado = (RadioButton)getActivity().findViewById(idAprobado);
                    String Aprobado=radioButtonAprobado.getText().toString();

                    String txtValorDeReferenciaAprobado=etValorReferenciaAprobado.getText().toString();
                    String txtObservacionesAprobado=etObservacionesAprobado.getText().toString();
                    String txtPropuestaDeMejoraAprobado=etPropuestaMejoraAprobado.getText().toString();
                    String txtAccionAprobado=etAccionAprobado.getText().toString();
                    String spinnerResponsableAprobadoSeleccion=spinnerResponsableAprobado.getSelectedItem().toString();

                    //MOC
                    int idMOC =rg_MOC.getCheckedRadioButtonId();
                    RadioButton radioButtonMOC = (RadioButton)getActivity().findViewById(idMOC);
                    String MOC=radioButtonMOC.getText().toString();

                    String txtValorDeReferenciaMOC=etValorReferenciaMOC.getText().toString();
                    String txtObservacionesMOC=etObservacionesMOC.getText().toString();
                    String txtPropuestaDeMejoraMOC=etPropuestaMejoraMOC.getText().toString();
                    String txtAccionMOC=etAccionMOC.getText().toString();
                    String spinnerResponsableMOCSeleccion=spinnerResponsableMOC.getSelectedItem().toString();

                    //Prueba MOC
                    int idPruebaMOC = rg_PruebaMOC.getCheckedRadioButtonId();
                    RadioButton radioButtonPruebaMOC = (RadioButton)getActivity().findViewById(idPruebaMOC);
                    String PruebaMOC=radioButtonPruebaMOC.getText().toString();

                    String txtValorDeReferenciaPruebaMOC=etValorReferenciaPruebaMOC.getText().toString();
                    String txtObservacionesPruebaMOC=etObservacionesPruebaMOC.getText().toString();
                    String txtPropuestaDeMejoraPruebaMOC=etPropuestaMejoraPruebaMOC.getText().toString();
                    String txtAccionPruebaMOC=etAccionPruebaMOC.getText().toString();
                    String spinnerResponsablePruebaMOCSeleccion=spinnerResponsablePruebaMOC.getSelectedItem().toString();

                    //Ultima Revision
                    int idUltimaRevision =rg_UltimaRevision.getCheckedRadioButtonId();
                    RadioButton radioButtonUltimaRevision = (RadioButton)getActivity().findViewById(idUltimaRevision);
                    String UltimaRevision=radioButtonUltimaRevision.getText().toString();

                    String txtValorDeReferenciaUltimaRevision=etValorReferenciaUltimaRevision.getText().toString();
                    String txtObservacionesUltimaRevision=etObservacionesUltimaRevision.getText().toString();
                    String txtPropuestaDeMejoraUltimaRevision=etPropuestaMejoraUltimaRevision.getText().toString();
                    String txtAccionUltimaRevision=etAccionUltimaRevision.getText().toString();
                    String spinnerResponsableUltimaRevisionSeleccion=spinnerResponsableUltimaRevision.getSelectedItem().toString();

                    //Copia Controlada
                    int idCopiaControlada =rg_CopiaControlada.getCheckedRadioButtonId();
                    RadioButton radioButtonCopiaControlada = (RadioButton)getActivity().findViewById(idCopiaControlada);
                    String CopiaControlada=radioButtonCopiaControlada.getText().toString();

                    String txtValorDeReferenciaCopiaControlada=etValorReferenciaCopiaControlada.getText().toString();
                    String txtObservacionesCopiaControlada=etObservacionesCopiaControlada.getText().toString();
                    String txtPropuestaDeMejoraCopiaControlada=etPropuestaMejoraCopiaControlada.getText().toString();
                    String txtAccionCopiaControlada=etAccionCopiaControlada.getText().toString();
                    String spinnerResponsableCopiaControladaSeleccion=spinnerResponsableCopiaControlada.getSelectedItem().toString();

                    //Test
                    int idTest =rg_Test.getCheckedRadioButtonId();
                    RadioButton radioButtonTest = (RadioButton)getActivity().findViewById(idTest);
                    String Test=radioButtonTest.getText().toString();

                    String txtValorDeReferenciaTest=etValorReferenciaTest.getText().toString();
                    String txtObservacionesTest=etObservacionesTest.getText().toString();
                    String txtPropuestaDeMejoraTest=etPropuestaMejoraTest.getText().toString();
                    String txtAccionTest=etAccionTest.getText().toString();
                    String spinnerResponsableTestSeleccion=spinnerResponsableTest.getSelectedItem().toString();

                    //validar que se completen todos los campos, sino no se puede guardar
                    if(txtFecha.length() > 0 && txtNombreProcedimiento.length() > 0){
                        boolean resultado = new ControladorPruebasACampo(getContext()).Insert(spinnerSiteSeleccion, txtFecha, spinnerObservadorUnoSeleccion, spinnerObservadorDosSeleccion,
                            txtNombreProcedimiento, spinnerEjecutanteSeleccion, spinnerSectorSeleccion, Alcance, txtValorDeReferenciaAlcance,
                            txtObservacionesAlcance, txtPropuestaDeMejoraAlcance, txtAccionAlcance, spinnerResponsableAlcanceSeleccion,
                            Categoria, txtValorDeReferenciaCategoria, txtObservacionesCategoria, txtPropuestaDeMejoraCategoria, txtAccionCategoria,
                            spinnerResponsableCategoriaSeleccion, Atributos, txtValorDeReferenciaAtributos, txtObservacionesAtributos,
                            txtPropuestaDeMejoraAtributos, txtAccionAtributos, spinnerResponsableAtributosSeleccion, RiesgosYPrecauciones, txtValorDeReferenciaRiesgosYPrecauciones,
                            txtObservacionesRiesgosYPrecauciones, txtPropuestaDeMejoraRiesgosYPrecauciones, txtAccionRiesgosYPrecauciones, spinnerResponsableRiesgosYPrecaucionesSeleccion,
                            EPP, txtValorDeReferenciaEPP, txtObservacionesEPP, txtPropuestaDeMejoraEPP, txtAccionEPP, spinnerResponsableEPPSeleccion,
                            Herramientas, txtValorDeReferenciaHerramientas, txtObservacionesHerramientas, txtPropuestaDeMejoraHerramientas, txtAccionHerramientas,
                            spinnerResponsableHerramientasSeleccion, PersonalMinimo, txtValorDeReferenciaPersonalMinimo, txtObservacionesPersonalMinimo, txtPropuestaDeMejoraPersonalMinimo,
                            txtAccionPersonalMinimo, spinnerResponsablePersonalMinimoSeleccion, Desviaciones, txtValorDeReferenciaDesviaciones, txtObservacionesDesviaciones,
                            txtPropuestaDeMejoraDesviaciones, txtAccionDesviaciones, spinnerResponsableDesviacionesSeleccion, PasoAPaso, txtValorDeReferenciaPasoAPaso,
                            txtObservacionesPasoAPaso, txtPropuestaDeMejoraPasoAPaso, txtAccionPasoAPaso, spinnerResponsablePasoAPasoSeleccion, DocumentosRelacionados,
                            txtValorDeReferenciaDocumentosRelacionados, txtObservacionesDocumentosRelacionados, txtPropuestaDeMejoraDocumentosRelacionados, txtAccionDocumentosRelacionados, spinnerResponsableDocumentosRelacionadosSeleccion,
                            ControlRegistro, txtValorDeReferenciaControlRegistro, txtObservacionesControlRegistro, txtPropuestaDeMejoraControlRegistro, txtAccionControlRegistro,
                            spinnerResponsableControlRegistroSeleccion, Validacion, txtValorDeReferenciaValidacion, txtObservacionesValidacion, txtPropuestaDeMejoraValidacion,
                            txtAccionValidacion, spinnerResponsableValidacionSeleccion, Aprobado, txtValorDeReferenciaAprobado, txtObservacionesAprobado, txtPropuestaDeMejoraAprobado,
                            txtAccionAprobado, spinnerResponsableAprobadoSeleccion, MOC, txtValorDeReferenciaMOC, txtObservacionesMOC, txtPropuestaDeMejoraMOC,
                            txtAccionMOC, spinnerResponsableMOCSeleccion, PruebaMOC, txtValorDeReferenciaPruebaMOC, txtObservacionesPruebaMOC, txtPropuestaDeMejoraPruebaMOC,
                            txtAccionPruebaMOC, spinnerResponsablePruebaMOCSeleccion, UltimaRevision, txtValorDeReferenciaUltimaRevision, txtObservacionesUltimaRevision,
                            txtPropuestaDeMejoraUltimaRevision, txtAccionUltimaRevision, spinnerResponsableUltimaRevisionSeleccion, CopiaControlada, txtValorDeReferenciaCopiaControlada,
                            txtObservacionesCopiaControlada, txtPropuestaDeMejoraCopiaControlada, txtAccionCopiaControlada, spinnerResponsableCopiaControladaSeleccion,
                            Test, txtValorDeReferenciaTest, txtObservacionesTest, txtPropuestaDeMejoraTest, txtAccionTest, spinnerResponsableTestSeleccion,
                                target, usuario);

                        if(resultado){
                            Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                            new Syncronizer(getActivity(),"setPruebas").execute();
                        }else{
                            Toast.makeText(getActivity(),"No se guardó correctamente.",Toast.LENGTH_SHORT).show();
                        }
                        getActivity().onBackPressed();

                    }else{
                        Toast.makeText(getActivity(),"Complete todos los campos obligatorios (*).",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),"Error al guardar los datos. " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String mes = Integer.toString(month +1);
        if( mes.length()<2){
            mes = "0"+mes;
        }
        String dia = Integer.toString(dayOfMonth);
        if(dia.length()<2){
            dia = "0"+dia;
        }
        etFecha.setText( Integer.toString(year) + "-" + mes  + "-" + dia );
    }

    private void CalcularTarget(){
        target = valor_Alcance + valor_Categoria + valor_Atributos+ valor_RiesgosYPrecauciones +  valor_EPP +  valor_Herramientas +  valor_PersonalMinimo +  valor_Desviaciones +
                valor_PasoAPaso+ valor_DocumentosRelacionados +  valor_ControlRegistro +  valor_Validacion +  valor_Aprobado +  valor_MOC +  valor_PruebaMOC +  valor_UltimaRevision +
                valor_CopiaControlada + valor_Test;
        etPorcentaje.setText("Cumpliento de la prueba: "+ target.toString() +"%");
    }

    public interface OnFragmentInteractionListener {
    }
}