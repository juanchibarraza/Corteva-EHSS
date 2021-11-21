package com.example.corteva.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
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
import com.example.corteva.ControladorSODA;
import com.example.corteva.ControladorSectores;
import com.example.corteva.ControladorSites;
import com.example.corteva.DatePickerFragment;
import com.example.corteva.R;
import com.example.corteva.SaveSharedPreference;
import com.example.corteva.Syncronizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SODAFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    EditText etFecha, etActosSeguros, etActosInseguros;
    Spinner spinnerSite, spinnerArea, spinnerSectorEmpleado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_soda, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

        Bundle bundle = this.getArguments();
        final String usuario = bundle.getString("usuario");
        spinnerArea = getActivity().findViewById(R.id.spinnerAreaDepto);
        spinnerSite = getActivity().findViewById(R.id.spinnerSiteSODA);
        etFecha = getActivity().findViewById(R.id.etFechaSODA);
        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etFecha.setText(today);
        etActosSeguros = getActivity().findViewById(R.id.etActosSeguros);
        etActosInseguros = getActivity().findViewById(R.id.etActosInseguros);
        spinnerSectorEmpleado = getActivity().findViewById(R.id.spinnerSectorEmpleado);

        //traer los sites desde la web por syncro
        ArrayList<String> listaSites=new ControladorSites(getContext()).getSites();
        ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSites);
        spinnerSite.setAdapter(adapter1);

        if(SaveSharedPreference.getLoggedSite(getContext())!=""){
            spinnerSite.setSelection(adapter1.getPosition(SaveSharedPreference.getLoggedSite(getContext())));
        }

        spinnerSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String spinnerSiteSeleccion= spinnerSite.getSelectedItem().toString();
                ArrayList<String> listaSectores=new ControladorSectores(getContext()).getSectoresBySite(spinnerSiteSeleccion);
                ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSectores);
                spinnerArea.setAdapter(adapter1);
                spinnerSectorEmpleado.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Mostrar/Ocultar grupos
        TextView textView_ReaccionPersonas = getActivity().findViewById(R.id.ReaccionPersona);
        textView_ReaccionPersonas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_ReaccionPersona);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });

        TextView textView_ProteccionPersonal = getActivity().findViewById(R.id.ProteccionPersonal);
        textView_ProteccionPersonal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_ProteccionPersonal);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });

        TextView textView_PosicionesDeLasPersonas = getActivity().findViewById(R.id.PosicionesDeLasPersonas);
        textView_PosicionesDeLasPersonas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_PosicionesDeLasPersonas);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });

        TextView textView_HerramientasYEquipo = getActivity().findViewById(R.id.HerramientasYEquipo);
        textView_HerramientasYEquipo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_HerramientasYEquipo);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });

        TextView textView_ProcedimietosPermisos = getActivity().findViewById(R.id.ProcedimietosPermisos);
        textView_ProcedimietosPermisos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_ProcedimietosPermisos);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });

        TextView textView_OrdenYLimpieza = getActivity().findViewById(R.id.OrdenYLimpieza);
        textView_OrdenYLimpieza.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_OrdenYLimpieza);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });

        TextView textView_RiesgosDelSite = getActivity().findViewById(R.id.RiesgosDelSite);
        textView_RiesgosDelSite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_RiesgosDelSite);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });

        //CALENDARIO
        etFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se muestre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(SODAFragment.this, 0);
                        datePicker.show(getFragmentManager(), "date picker");
                        break;
                }
                return false;
            }
        });

        Button botonGuardar = getActivity().findViewById(R.id.GuardarSODA);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String spinnerSiteSeleccion=spinnerSite.getSelectedItem().toString();
                    String spinnerAreaSeleccion = spinnerArea.getSelectedItem().toString();
                    String SectorEmpleado = spinnerSectorEmpleado.getSelectedItem().toString();
                    String txtEtActosSeguros=etActosSeguros.getText().toString();
                    String txtEtFecha=etFecha.getText().toString();
                    String txtEtActosInseguros=etActosInseguros.getText().toString();

                    //Reacciones de personas

                    RadioGroup RGAgregarProteccion = (RadioGroup)getActivity().findViewById(R.id.RGAgregarProteccion);
                    int idAgregarProteccion=RGAgregarProteccion.getCheckedRadioButtonId();
                    RadioButton radioButtonAgregarProteccion = (RadioButton)getActivity().findViewById(idAgregarProteccion);
                    String AgregarProteccion=radioButtonAgregarProteccion.getText().toString();

                    RadioGroup RGCambioPosicion = (RadioGroup)getActivity().findViewById(R.id.RGCambioPosicion);
                    int idCambioPosicion=RGCambioPosicion.getCheckedRadioButtonId();
                    RadioButton radioButtonCambioPosicion = (RadioButton)getActivity().findViewById(idCambioPosicion);
                    String CambioPosicion=radioButtonCambioPosicion.getText().toString();

                    RadioGroup RGReacomodaTrabajo = (RadioGroup)getActivity().findViewById(R.id.RGReacomodaTrabajo);
                    int idReacomodaTrabajo=RGReacomodaTrabajo.getCheckedRadioButtonId();
                    RadioButton radioButtonReacomodaTrabajo = (RadioButton)getActivity().findViewById(idReacomodaTrabajo);
                    String ReacomodaTrabajo=radioButtonReacomodaTrabajo.getText().toString();

                    RadioGroup RGDejarTrabajar = (RadioGroup)getActivity().findViewById(R.id.RGDejarTrabajar);
                    int idDejarTrabajar=RGDejarTrabajar.getCheckedRadioButtonId();
                    RadioButton radioButtonDejarTrabajar = (RadioButton)getActivity().findViewById(idDejarTrabajar);
                    String DejarTrabajar=radioButtonDejarTrabajar.getText().toString();

                    RadioGroup RGColocanTierras = (RadioGroup)getActivity().findViewById(R.id.RGColocanTierras);
                    int idColocanTierras=RGColocanTierras.getCheckedRadioButtonId();
                    RadioButton radioButtonColocanTierras = (RadioButton)getActivity().findViewById(idColocanTierras);
                    String ColocanTierras=radioButtonColocanTierras.getText().toString();

                    RadioGroup RGColocanBloqueos = (RadioGroup)getActivity().findViewById(R.id.RGColocanBloqueos);
                    int idColocanBloqueos=RGColocanBloqueos.getCheckedRadioButtonId();
                    RadioButton radioButtonColocanBloqueos = (RadioButton)getActivity().findViewById(idColocanBloqueos);
                    String ColocanBloqueos=radioButtonColocanBloqueos.getText().toString();

                    //Equipo Proteccion Personal

                    RadioGroup RGCabeza = (RadioGroup)getActivity().findViewById(R.id.RGCabeza);
                    int idCabeza=RGCabeza.getCheckedRadioButtonId();
                    RadioButton radioButtonCabeza = (RadioButton)getActivity().findViewById(idCabeza);
                    String Cabeza=radioButtonCabeza.getText().toString();

                    RadioGroup RGOjosYCara = (RadioGroup)getActivity().findViewById(R.id.RGOjosYCara);
                    int idOjosYCara=RGOjosYCara.getCheckedRadioButtonId();
                    RadioButton radioButtonOjosYCara = (RadioButton)getActivity().findViewById(idOjosYCara);
                    String OjosYCara=radioButtonOjosYCara.getText().toString();

                    RadioGroup RGOidos = (RadioGroup)getActivity().findViewById(R.id.RGOidos);
                    int idOidos=RGOidos.getCheckedRadioButtonId();
                    RadioButton radioButtonOidos = (RadioButton)getActivity().findViewById(idOidos);
                    String Oidos=radioButtonOidos.getText().toString();

                    RadioGroup RGAparatoRespiratorio = (RadioGroup)getActivity().findViewById(R.id.RGAparatoRespiratorio);
                    int idAparatoRespiratorio=RGAparatoRespiratorio.getCheckedRadioButtonId();
                    RadioButton radioButtonAparatoRespiratorio = (RadioButton)getActivity().findViewById(idAparatoRespiratorio);
                    String AparatoRespiratorio=radioButtonAparatoRespiratorio.getText().toString();

                    RadioGroup RGBrazosyManos = (RadioGroup)getActivity().findViewById(R.id.RGBrazosyManos);
                    int idBrazosyManos=RGBrazosyManos.getCheckedRadioButtonId();
                    RadioButton radioButtonBrazosyManos = (RadioButton)getActivity().findViewById(idBrazosyManos);
                    String BrazosyManos=radioButtonBrazosyManos.getText().toString();

                    RadioGroup RGTronco = (RadioGroup)getActivity().findViewById(R.id.RGTronco);
                    int idTronco=RGTronco.getCheckedRadioButtonId();
                    RadioButton radioButtonTronco = (RadioButton)getActivity().findViewById(idTronco);
                    String Tronco=radioButtonTronco.getText().toString();

                    RadioGroup RGPiernasyPies = (RadioGroup)getActivity().findViewById(R.id.RGPiernasyPies);
                    int idPiernasyPies=RGPiernasyPies.getCheckedRadioButtonId();
                    RadioButton radioButtonPiernasyPies = (RadioButton)getActivity().findViewById(idPiernasyPies);
                    String PiernasyPies=radioButtonPiernasyPies.getText().toString();

                    //Posiciones de las personas (Causas de lesiones)

                    RadioGroup RGGolpearContraObjetos = (RadioGroup)getActivity().findViewById(R.id.RGGolpearContraObjetos);
                    int idGolpearContraObjetos=RGGolpearContraObjetos.getCheckedRadioButtonId();
                    RadioButton radioButtonGolpearContraObjetos = (RadioButton)getActivity().findViewById(idGolpearContraObjetos);
                    String GolpearContraObjetos=radioButtonGolpearContraObjetos.getText().toString();

                    RadioGroup RGSerGolpeadoContraObjetos = (RadioGroup)getActivity().findViewById(R.id.RGSerGolpeadoContraObjetos);
                    int idSerGolpeadoContraObjetos=RGSerGolpeadoContraObjetos.getCheckedRadioButtonId();
                    RadioButton radioButtonSerGolpeadoContraObjetos = (RadioButton)getActivity().findViewById(idSerGolpeadoContraObjetos);
                    String SerGolpeadoContraObjetos=radioButtonSerGolpeadoContraObjetos.getText().toString();

                    RadioGroup RGAtrapado = (RadioGroup)getActivity().findViewById(R.id.RGAtrapado);
                    int idAtrapado=RGAtrapado.getCheckedRadioButtonId();
                    RadioButton radioButtonAtrapado = (RadioButton)getActivity().findViewById(idAtrapado);
                    String Atrapado=radioButtonAtrapado.getText().toString();

                    RadioGroup RGCaidas = (RadioGroup)getActivity().findViewById(R.id.RGCaidas);
                    int idCaidas=RGCaidas.getCheckedRadioButtonId();
                    RadioButton radioButtonCaidas = (RadioButton)getActivity().findViewById(idCaidas);
                    String Caidas=radioButtonCaidas.getText().toString();

                    RadioGroup RGContactoTemperaturas = (RadioGroup)getActivity().findViewById(R.id.RGContactoTemperaturas);
                    int idContactoTemperaturas=RGContactoTemperaturas.getCheckedRadioButtonId();
                    RadioButton radioButtonContactoTemperaturas = (RadioButton)getActivity().findViewById(idContactoTemperaturas);
                    String ContactoTemperaturas=radioButtonContactoTemperaturas.getText().toString();

                    RadioGroup RGContactoElectricidad = (RadioGroup)getActivity().findViewById(R.id.RGContactoElectricidad);
                    int idContactoElectricidad=RGContactoElectricidad.getCheckedRadioButtonId();
                    RadioButton radioButtonContactoElectricidad = (RadioButton)getActivity().findViewById(idContactoElectricidad);
                    String ContactoElectricidad=radioButtonContactoElectricidad.getText().toString();

                    RadioGroup RGInhalacion = (RadioGroup)getActivity().findViewById(R.id.RGInhalacion);
                    int idInhalacion=RGInhalacion.getCheckedRadioButtonId();
                    RadioButton radioButtonInhalacion = (RadioButton)getActivity().findViewById(idInhalacion);
                    String Inhalacion=radioButtonInhalacion.getText().toString();

                    RadioGroup RGAbsorcion = (RadioGroup)getActivity().findViewById(R.id.RGAbsorcion);
                    int idAbsorcion=RGAbsorcion.getCheckedRadioButtonId();
                    RadioButton radioButtonAbsorcion = (RadioButton)getActivity().findViewById(idAbsorcion);
                    String Absorcion=radioButtonAbsorcion.getText().toString();

                    RadioGroup RGIngestion = (RadioGroup)getActivity().findViewById(R.id.RGIngestion);
                    int idIngestion=RGIngestion.getCheckedRadioButtonId();
                    RadioButton radioButtonIngestion = (RadioButton)getActivity().findViewById(idIngestion);
                    String Ingestion=radioButtonIngestion.getText().toString();

                    RadioGroup RGSobreesfuerzo = (RadioGroup)getActivity().findViewById(R.id.RGSobreesfuerzo);
                    int idSobreesfuerzo=RGSobreesfuerzo.getCheckedRadioButtonId();
                    RadioButton radioButtonSobreesfuerzo = (RadioButton)getActivity().findViewById(idSobreesfuerzo);
                    String Sobreesfuerzo=radioButtonSobreesfuerzo.getText().toString();

                    RadioGroup RGMovimientosRepetitivos = (RadioGroup)getActivity().findViewById(R.id.RGMovimientosRepetitivos);
                    int idMovimientosRepetitivos=RGMovimientosRepetitivos.getCheckedRadioButtonId();
                    RadioButton radioButtonMovimientosRepetitivos = (RadioButton)getActivity().findViewById(idMovimientosRepetitivos);
                    String MovimientosRepetitivos=radioButtonMovimientosRepetitivos.getText().toString();

                    RadioGroup RGPosicionesIncomodas = (RadioGroup)getActivity().findViewById(R.id.RGPosicionesIncomodas);
                    int idPosicionesIncomodas=RGPosicionesIncomodas.getCheckedRadioButtonId();
                    RadioButton radioButtonPosicionesIncomodas = (RadioButton)getActivity().findViewById(idPosicionesIncomodas);
                    String PosicionesIncomodas=radioButtonPosicionesIncomodas.getText().toString();

                    //Herramientas y equipo

                    RadioGroup RGHerramientasInadecuadas = (RadioGroup)getActivity().findViewById(R.id.RGHerramientasInadecuadas);
                    int idHerramientasInadecuadas=RGHerramientasInadecuadas.getCheckedRadioButtonId();
                    RadioButton radioButtonHerramientasInadecuadas = (RadioButton)getActivity().findViewById(idHerramientasInadecuadas);
                    String HerramientasInadecuadas=radioButtonHerramientasInadecuadas.getText().toString();

                    RadioGroup RGEmpleoIncorrecto = (RadioGroup)getActivity().findViewById(R.id.RGEmpleoIncorrecto);
                    int idEmpleoIncorrecto=RGEmpleoIncorrecto.getCheckedRadioButtonId();
                    RadioButton radioButtonEmpleoIncorrecto = (RadioButton)getActivity().findViewById(idEmpleoIncorrecto);
                    String EmpleoIncorrecto=radioButtonEmpleoIncorrecto.getText().toString();

                    RadioGroup RGEmpleoInseguro = (RadioGroup)getActivity().findViewById(R.id.RGEmpleoInseguro);
                    int idEmpleoInseguro=RGEmpleoInseguro.getCheckedRadioButtonId();
                    RadioButton radioButtonEmpleoInseguro = (RadioButton)getActivity().findViewById(idEmpleoInseguro);
                    String EmpleoInseguro=radioButtonEmpleoInseguro.getText().toString();

                    //Procedimientos / Permisos

                    RadioGroup RGProcedimientosInadecuados = (RadioGroup)getActivity().findViewById(R.id.RGProcedimientosInadecuados);
                    int idProcedimientosInadecuados=RGProcedimientosInadecuados.getCheckedRadioButtonId();
                    RadioButton radioButtonProcedimientosInadecuados = (RadioButton)getActivity().findViewById(idProcedimientosInadecuados);
                    String ProcedimientosInadecuados=radioButtonProcedimientosInadecuados.getText().toString();

                    RadioGroup RGProcedimientosDesconocidos = (RadioGroup)getActivity().findViewById(R.id.RGProcedimientosDesconocidos);
                    int idProcedimientosDesconocidos=RGProcedimientosDesconocidos.getCheckedRadioButtonId();
                    RadioButton radioButtonProcedimientosDesconocidos = (RadioButton)getActivity().findViewById(idProcedimientosDesconocidos);
                    String ProcedimientosDesconocidos=radioButtonProcedimientosDesconocidos.getText().toString();

                    RadioGroup RGProcedimientoNoCumplen = (RadioGroup)getActivity().findViewById(R.id.RGProcedimientoNoCumplen);
                    int idProcedimientoNoCumplen=RGProcedimientoNoCumplen.getCheckedRadioButtonId();
                    RadioButton radioButtonProcedimientoNoCumplen = (RadioButton)getActivity().findViewById(idProcedimientoNoCumplen);
                    String ProcedimientoNoCumplen=radioButtonProcedimientoNoCumplen.getText().toString();

                    //Orden y Limpieza

                    RadioGroup RGLocalSucio = (RadioGroup)getActivity().findViewById(R.id.RGLocalSucio);
                    int idLocalSucio=RGLocalSucio.getCheckedRadioButtonId();
                    RadioButton radioButtonLocalSucio = (RadioButton)getActivity().findViewById(idLocalSucio);
                    String LocalSucio=radioButtonLocalSucio.getText().toString();

                    RadioGroup RGLocalDesorganizado = (RadioGroup)getActivity().findViewById(R.id.RGLocalDesorganizado);
                    int idLocalDesorganizado=RGLocalDesorganizado.getCheckedRadioButtonId();
                    RadioButton radioButtonLocalDesorganizado = (RadioButton)getActivity().findViewById(idLocalDesorganizado);
                    String LocalDesorganizado=radioButtonLocalDesorganizado.getText().toString();

                    RadioGroup RGLocalFugas = (RadioGroup)getActivity().findViewById(R.id.RGLocalFugas);
                    int idLocalFugas=RGLocalFugas.getCheckedRadioButtonId();
                    RadioButton radioButtonLocalFugas = (RadioButton)getActivity().findViewById(idLocalFugas);
                    String LocalFugas=radioButtonLocalFugas.getText().toString();

                    RadioGroup RGDispocisionInadecuada = (RadioGroup)getActivity().findViewById(R.id.RGDispocisionInadecuada);
                    int idDispocisionInadecuada=RGDispocisionInadecuada.getCheckedRadioButtonId();
                    RadioButton radioButtonDispocisionInadecuada = (RadioButton)getActivity().findViewById(idDispocisionInadecuada);
                    String DispocisionInadecuada=radioButtonDispocisionInadecuada.getText().toString();

                    //Riesgos del Site
                    RadioGroup RGCaminaSinCelular = (RadioGroup)getActivity().findViewById(R.id.RGCaminaSinCelular);
                    int idCaminaSinCelular=RGCaminaSinCelular.getCheckedRadioButtonId();
                    RadioButton radioButtonCaminaSinCelular = (RadioButton)getActivity().findViewById(idCaminaSinCelular);
                    String CaminaSinCelular=radioButtonCaminaSinCelular.getText().toString();

                    RadioGroup RGSendasPeatonales = (RadioGroup)getActivity().findViewById(R.id.RGSendasPeatonales);
                    int idSendasPeatonales=RGSendasPeatonales.getCheckedRadioButtonId();
                    RadioButton radioButtonSendasPeatonales = (RadioButton)getActivity().findViewById(idSendasPeatonales);
                    String SendasPeatonales=radioButtonSendasPeatonales.getText().toString();

                    RadioGroup RGPasamanos = (RadioGroup)getActivity().findViewById(R.id.RGPasamanos);
                    int idPasamanos=RGPasamanos.getCheckedRadioButtonId();
                    RadioButton radioButtonPasamanos = (RadioButton)getActivity().findViewById(idPasamanos);
                    String Pasamanos=radioButtonPasamanos.getText().toString();

                    RadioGroup RGVehiculo = (RadioGroup)getActivity().findViewById(R.id.RGCaminaSinCelular);
                    int idVehiculo=RGVehiculo.getCheckedRadioButtonId();
                    RadioButton radioButtonVehiculo = (RadioButton)getActivity().findViewById(idVehiculo);
                    String Vehiculo=radioButtonVehiculo.getText().toString();

                    RadioGroup RGMontacargas1 = (RadioGroup)getActivity().findViewById(R.id.RGCaminaSinCelular);
                    int idMontacargas1=RGMontacargas1.getCheckedRadioButtonId();
                    RadioButton radioButtonMontacargas1 = (RadioButton)getActivity().findViewById(idMontacargas1);
                    String Montacargas1=radioButtonMontacargas1.getText().toString();

                    RadioGroup RGMontacargas2 = (RadioGroup)getActivity().findViewById(R.id.RGCaminaSinCelular);
                    int idMontacargas2=RGMontacargas2.getCheckedRadioButtonId();
                    RadioButton radioButtonMontacargas2 = (RadioButton)getActivity().findViewById(idMontacargas2);
                    String Montacargas2=radioButtonMontacargas2.getText().toString();

                    RadioGroup RGMontacargas3 = (RadioGroup)getActivity().findViewById(R.id.RGCaminaSinCelular);
                    int idMontacargas3=RGMontacargas3.getCheckedRadioButtonId();
                    RadioButton radioButtonMontacargas3 = (RadioButton)getActivity().findViewById(idMontacargas3);
                    String Montacargas3=radioButtonMontacargas3.getText().toString();

                    boolean resultado = new ControladorSODA(getContext()).Insert(spinnerSiteSeleccion, spinnerAreaSeleccion, AgregarProteccion,
                            CambioPosicion, ReacomodaTrabajo, DejarTrabajar, ColocanTierras, ColocanBloqueos, Cabeza, OjosYCara,
                            Oidos, AparatoRespiratorio, BrazosyManos, Tronco, PiernasyPies, GolpearContraObjetos, SerGolpeadoContraObjetos,
                            Atrapado, Caidas, ContactoTemperaturas, ContactoElectricidad, Inhalacion, Absorcion, Ingestion, Sobreesfuerzo, MovimientosRepetitivos,
                            PosicionesIncomodas, HerramientasInadecuadas, EmpleoIncorrecto, EmpleoInseguro, ProcedimientosInadecuados, ProcedimientosDesconocidos,
                            ProcedimientoNoCumplen, LocalSucio, LocalDesorganizado,LocalFugas,DispocisionInadecuada, CaminaSinCelular, SendasPeatonales, Pasamanos,
                            txtEtActosSeguros, txtEtActosInseguros, Vehiculo, Montacargas1, Montacargas2, Montacargas3, txtEtFecha, SectorEmpleado, usuario);

                    if(resultado){
                        Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                        //new Syncronizer(getActivity(),"setSODA").execute();
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
        String mes = Integer.toString(month +1);
        if( mes.length()<2){
            mes = "0"+mes;
        }
        String dia = Integer.toString(dayOfMonth);
        if(dia.length()<2){
            dia = "0"+dia;
        }
        etFecha.setText(dia + "-" + mes + "-" +  Integer.toString(year));
    }

    public interface OnFragmentInteractionListener {

    }
}