package com.example.corteva.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.corteva.ControladorResponsables;
import com.example.corteva.ControladorSectores;
import com.example.corteva.ControladorSites;
import com.example.corteva.ControladorTopTen;
import com.example.corteva.DatePickerFragment;
import com.example.corteva.FilePath;
import com.example.corteva.R;
import com.example.corteva.SaveSharedPreference;
import com.example.corteva.Syncronizer;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TopTenFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    EditText etObservadorDos, etPTSFecha, etPTSConfecciono, etPTSObservaciones;
    EditText etMasterTarjetasFecha, etMasterTarjetasConfecciono, etMasterTarjetasObservaciones;
    EditText etEspacioConfinadoFecha, etEspacioConfinadoConfecciono, etEspacioConfinadoObservaciones;
    EditText etTrabajoEnAlturaFecha, etTrabajoEnAlturaConfecciono, etTrabajoEnAlturaObservaciones;
    EditText etTrabajoEnCalienteFecha, etTrabajoEnCalienteConfecciono, etTrabajoEnCalienteObservaciones;
    EditText etAperturaLineasFecha, etAperturaLineasConfecciono, etAperturaLineasObservaciones;
    EditText etHidrolavadoraFecha, etHidrolavadoraConfecciono, etHidrolavadoraObservaciones;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEPTS=1111;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEMASTERTARJETASROJAS=2222;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEACCESOESPACIOCONFINADO=3333;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODETRABAJOENALTURA=4444;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODETRABAJOENCALIENTE=5555;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEAPERTURALINEAEQUIPOS=6666;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEHIDROLAVADORA=7777;
    private static final int CHOOSE_IMAGEACTIVITY_REQUEST_CODEPTS=11111;
    private static final int CHOOSE_IMAGEACTIVITY_REQUEST_CODEMASTERTARJETASROJAS=22221;
    private static final int CHOOSE_IMAGEACTIVITY_REQUEST_CODEACCESOESPACIOCONFINADO=33331;
    private static final int CHOOSE_IMAGEACTIVITY_REQUEST_CODETRABAJOENALTURA=44441;
    private static final int CHOOSE_IMAGEACTIVITY_REQUEST_CODETRABAJOENCALIENTE=55551;
    private static final int CHOOSE_IMAGEACTIVITY_REQUEST_CODEAPERTURALINEAEQUIPOS=6667;
    private static final int CHOOSE_IMAGEACTIVITY_REQUEST_CODEHIDROLAVADORA=7778;
    ImageSwitcher fotoImageSwitcherPTS, fotoImageSwitcherTarjetasRojas, fotoImageSwitcherEspacioConfinado, fotoImageSwitcherTrabajoEnAltura,
            fotoImageSwitcherTrabajoEnCaliente, fotoImageSwitcherAperturaLinea,fotoImageSwitcherHidrolavadora;
    Spinner spinnerSector, spinnerObservadorUno, spinnerSite;
    private ArrayList<Uri> imagenPTSUris, imagenTarjetasRojasUris, imagenEspacioConfinadoUris, imagenTrabajoEnAlturaUris, imagenAperturaLineaUris,
            imagenTrabajoEnCalienteUris, imagenHidrolavadoraUris;
    LinearLayout imageSwitcherContainerPTS, imageSwitcherContainerTarjetasRojas, imageSwitcherContainerEspacioConfinado, imageSwitcherContainerTrabajoEnAltura,
            imageSwitcherContainerAperturaLinea, imageSwitcherContainerTrabajoEnCaliente, imageSwitcherContainerHidrolavadora;
    Uri imageUri;
    int positionPTS = 0, positionTarjetasRojas = 0, positionEspacioConfinado = 0, positionTrabajoEnAltura = 0, positionTrabajoEnCaliente = 0, positionAperturaLinea = 0, positionHidrolavadora = 0;
    int CALENDAR_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_top_ten, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");

        etPTSFecha=getActivity().findViewById(R.id.PTSFechaEmision);
        etPTSConfecciono=getActivity().findViewById(R.id.PTSConfecciono);
        etPTSObservaciones=getActivity().findViewById(R.id.PTSObservaciones);
        etMasterTarjetasFecha=getActivity().findViewById(R.id.TarjetasRojasFechaEmision);
        etMasterTarjetasConfecciono=getActivity().findViewById(R.id.TarjetasRojasConfecciono);
        etMasterTarjetasObservaciones=getActivity().findViewById(R.id.TarjetasRojasObservaciones);
        etEspacioConfinadoFecha=getActivity().findViewById(R.id.etEspacioConfinadoFecha);
        etEspacioConfinadoConfecciono=getActivity().findViewById(R.id.etEspacioConfinadoConfecciono);
        etEspacioConfinadoObservaciones=getActivity().findViewById(R.id.etObservacionesEspacioConfinado);
        etTrabajoEnAlturaFecha=getActivity().findViewById(R.id.TrabajoEnAlturaFecha);
        etTrabajoEnAlturaConfecciono=getActivity().findViewById(R.id.TrabajoEnAlturaConfecciono);
        etTrabajoEnAlturaObservaciones=getActivity().findViewById(R.id.TrabajoEnAlturaObservaciones);
        etTrabajoEnCalienteFecha=getActivity().findViewById(R.id.TrabajoEnCalienteFecha);
        etTrabajoEnCalienteConfecciono=getActivity().findViewById(R.id.TrabajoEnCalienteConfecciono);
        etTrabajoEnCalienteObservaciones=getActivity().findViewById(R.id.TrabajoEnCalienteObservaciones);
        etAperturaLineasFecha=getActivity().findViewById(R.id.AperturaLineasFecha);
        etAperturaLineasConfecciono=getActivity().findViewById(R.id.AperturaLineasConfecciono);
        etAperturaLineasObservaciones=getActivity().findViewById(R.id.AperturaLineasObservaciones);
        etHidrolavadoraFecha=getActivity().findViewById(R.id.HidrolavadoraFecha);
        etHidrolavadoraConfecciono=getActivity().findViewById(R.id.HidrolavadoraConfecciono);
        etHidrolavadoraObservaciones=getActivity().findViewById(R.id.HidrolavadoraObservaciones);

        imageSwitcherContainerPTS = getActivity().findViewById(R.id.imageSwitcherPTS);
        imageSwitcherContainerTarjetasRojas = getActivity().findViewById(R.id.imageSwitcherMasterTarjetasRojas);
        imageSwitcherContainerEspacioConfinado = getActivity().findViewById(R.id.imageSwitcherEspacioConfinado);
        imageSwitcherContainerTrabajoEnAltura = getActivity().findViewById(R.id.imageSwitcherTrabajoEnAltura);
        imageSwitcherContainerTrabajoEnCaliente = getActivity().findViewById(R.id.imageSwitcherTrabajoEnCaliente);
        imageSwitcherContainerAperturaLinea = getActivity().findViewById(R.id.imageSwitcherAperturaLineas);
        imageSwitcherContainerHidrolavadora = getActivity().findViewById(R.id.imageSwitcherHidrolavadora);
        imagenPTSUris = new ArrayList<Uri>();
        imagenTarjetasRojasUris = new ArrayList<Uri>();
        imagenEspacioConfinadoUris = new ArrayList<Uri>();
        imagenTrabajoEnAlturaUris = new ArrayList<Uri>();
        imagenAperturaLineaUris = new ArrayList<Uri>();
        imagenTrabajoEnCalienteUris = new ArrayList<Uri>();
        imagenHidrolavadoraUris = new ArrayList<Uri>();

        spinnerSector=getActivity().findViewById(R.id.spinnerSector);
        spinnerObservadorUno=getActivity().findViewById(R.id.spinnerObservador1);
        etObservadorDos=getActivity().findViewById(R.id.txtObservador2);
        spinnerSite=getActivity().findViewById(R.id.spinnerSiteTopTen);

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

                ArrayList<String> listaEmpleados=new ControladorResponsables(getContext()).getResponsablesBySite(spinnerSiteSeleccion);
                ArrayAdapter<String> adapterEmpleados=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaEmpleados);
                spinnerObservadorUno.setAdapter(adapterEmpleados);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //CALENDARIOS
        etPTSFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 1;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(TopTenFragment.this, 0);
                        datePicker.show(getFragmentManager(), "Fecha PTS");
                        break;
                }
                return false;
            }
        });
        etMasterTarjetasFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 2;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(TopTenFragment.this, 0);
                        datePicker.show(getFragmentManager(), "Fecha Master Tarjetas");
                        break;
                }
                return false;
            }
        });
        etEspacioConfinadoFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 3;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(TopTenFragment.this, 0);
                        datePicker.show(getFragmentManager(), "Fecha espacio confinado");
                        break;
                }
                return false;
            }
        });
        etTrabajoEnAlturaFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 4;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(TopTenFragment.this, 0);
                        datePicker.show(getFragmentManager(), "Fecha Trabajo en altura");
                        break;
                }
                return false;
            }
        });
        etTrabajoEnCalienteFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 5;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(TopTenFragment.this, 0);
                        datePicker.show(getFragmentManager(), "Fecha Trabajo en caliente");
                        break;
                }
                return false;
            }
        });
        etAperturaLineasFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 6;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(TopTenFragment.this, 0);
                        datePicker.show(getFragmentManager(), "Fecha Apertura de Lineas");
                        break;
                }
                return false;
            }
        });
        etHidrolavadoraFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 7;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(TopTenFragment.this, 0);
                        datePicker.show(getFragmentManager(), "date picker");
                        break;
                }
                return false;
            }
        });
        //Mostrar/Ocultar grupos
        TextView textView_CorePermit = getActivity().findViewById(R.id.textView_CorePermit);
        textView_CorePermit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_CorePermit);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_Master = getActivity().findViewById(R.id.textView_Master);
        textView_Master.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Master);
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
        TextView textView_Caliente = getActivity().findViewById(R.id.textView_Caliente);
        textView_Caliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Caliente);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_Apertura = getActivity().findViewById(R.id.textView_Apertura);
        textView_Apertura.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Apertura);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        TextView textView_Hidrolavadora = getActivity().findViewById(R.id.textView_Hidrolavadora);
        textView_Hidrolavadora.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout LL = getActivity().findViewById(R.id.LinearLayout_Hidrolavadora);
                if(LL.getVisibility()== View.GONE){
                    LL.setVisibility(View.VISIBLE);
                }else{
                    LL.setVisibility(View.GONE);
                }
            }
        });
        //Fotos PTS
        final Button botonElegirFotoPTS=getActivity().findViewById(R.id.buttonElegirFotoTOPTENPTS);
        botonElegirFotoPTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerPTS.getVisibility() == v.GONE){
                    imageSwitcherContainerPTS.setVisibility(v.VISIBLE);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione imagenes"), CHOOSE_IMAGEACTIVITY_REQUEST_CODEPTS);
            }
        });
        final Button botonFotoPTS=getActivity().findViewById(R.id.botonFotoTOPTENPTS);
        botonFotoPTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerPTS.getVisibility() == v.GONE){
                    imageSwitcherContainerPTS.setVisibility(v.VISIBLE);
                }
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "ehss_"+timeStamp + ".jpg";
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + imageFileName);
                imageUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEPTS);
            }
        });
        fotoImageSwitcherPTS = getActivity().findViewById(R.id.fotoImageSwitcherPTS);
        fotoImageSwitcherPTS.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView viewImage = new ImageView(getContext());
                return viewImage;
            }
        });
        Button anterior = getActivity().findViewById(R.id.botonAnteriorImageSwitcherPTS);
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionPTS>0){
                    positionPTS--;
                    fotoImageSwitcherPTS.setImageURI(imagenPTSUris.get(positionPTS));
                }else {
                    Toast.makeText(getActivity(),"No hay imágen anterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button siguiente = getActivity().findViewById(R.id.botonSiguienteImageSwitcherPTS);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionPTS<imagenPTSUris.size() - 1){
                    positionPTS++;
                    fotoImageSwitcherPTS.setImageURI(imagenPTSUris.get(positionPTS));
                }else{
                    Toast.makeText(getActivity(),"No hay imágen posterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button botonBorrar = getActivity().findViewById(R.id.eliminarFotoPTS);
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagenPTSUris.size()>0){
                    String imageTag = imagenPTSUris.get(positionPTS).getPath();
                    File fileTag = new File(imageTag);
                    if (fileTag.exists()) {
                        fileTag.delete();
                    }
                    //eliminar de imagenPTSUris
                    imagenPTSUris.remove(positionPTS);
                    if(positionPTS>0){
                        positionPTS--;
                        fotoImageSwitcherPTS.setImageURI(imagenPTSUris.get(positionPTS));
                    }else{
                        if(imagenPTSUris.size()>0) {
                            positionPTS++;
                            fotoImageSwitcherPTS.setImageURI(imagenPTSUris.get(positionPTS));
                        }else{
                            positionPTS = 0;
                            fotoImageSwitcherPTS.setImageURI(null);
                        }
                    }
                    Toast.makeText(getActivity(), "Imagen borrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Fotos Master Tarjetas Rojas
        final Button botonElegirFotoMasterTarjetasRojas=getActivity().findViewById(R.id.buttonElegirFotoMasterTarjetasRojas);
        botonElegirFotoMasterTarjetasRojas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerTarjetasRojas.getVisibility() == v.GONE){
                    imageSwitcherContainerTarjetasRojas.setVisibility(v.VISIBLE);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione imagenes"), CHOOSE_IMAGEACTIVITY_REQUEST_CODEMASTERTARJETASROJAS);
            }
        });
        final Button botonFotoTarjetasRojas=getActivity().findViewById(R.id.botonFotoMasterTarjetasRojas);
        botonFotoTarjetasRojas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerTarjetasRojas.getVisibility() == v.GONE){
                    imageSwitcherContainerTarjetasRojas.setVisibility(v.VISIBLE);
                }
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "ehss_"+timeStamp + ".jpg";
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + imageFileName);
                imageUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEMASTERTARJETASROJAS);
            }
        });
        fotoImageSwitcherTarjetasRojas = getActivity().findViewById(R.id.fotoImageSwitcherMasterTarjetasRojas);
        fotoImageSwitcherTarjetasRojas.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView viewImage = new ImageView(getContext());
                return viewImage;
            }
        });
        Button anterior2 = getActivity().findViewById(R.id.botonAnteriorImageSwitcherMasterTarjetasRojas);
        anterior2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionTarjetasRojas>0){
                    positionTarjetasRojas--;
                    fotoImageSwitcherTarjetasRojas.setImageURI(imagenTarjetasRojasUris.get(positionTarjetasRojas));
                }else {
                    Toast.makeText(getActivity(),"No hay imágen anterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button siguiente2 = getActivity().findViewById(R.id.botonSiguienteImageSwitcherMasterTarjetasRojas);
        siguiente2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionTarjetasRojas<imagenTarjetasRojasUris.size() - 1){
                    positionTarjetasRojas++;
                    fotoImageSwitcherTarjetasRojas.setImageURI(imagenTarjetasRojasUris.get(positionTarjetasRojas));
                }else{
                    Toast.makeText(getActivity(),"No hay imágen posterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button botonBorrar2 = getActivity().findViewById(R.id.eliminarFotoMasterTarjetasRojas);
        botonBorrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagenTarjetasRojasUris.size()>0){
                    String imageTag = imagenTarjetasRojasUris.get(positionTarjetasRojas).getPath();
                    File fileTag = new File(imageTag);
                    if (fileTag.exists()) {
                        fileTag.delete();
                    }
                    //eliminar de imagenTarjetasRojasUris
                    imagenTarjetasRojasUris.remove(positionTarjetasRojas);
                    if(positionTarjetasRojas>0){
                        positionTarjetasRojas--;
                        fotoImageSwitcherTarjetasRojas.setImageURI(imagenTarjetasRojasUris.get(positionTarjetasRojas));
                    }else{
                        if(imagenTarjetasRojasUris.size()>0) {
                            positionTarjetasRojas++;
                            fotoImageSwitcherTarjetasRojas.setImageURI(imagenTarjetasRojasUris.get(positionTarjetasRojas));
                        }else{
                            positionTarjetasRojas = 0;
                            fotoImageSwitcherTarjetasRojas.setImageURI(null);
                        }
                    }
                    Toast.makeText(getActivity(), "Imagen borrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Fotos Espacio Confinado
        final Button botonElegirFotoEspacioConfinado=getActivity().findViewById(R.id.buttonElegirFotoTOPTENEspacioConfinado);
        botonElegirFotoEspacioConfinado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerEspacioConfinado.getVisibility() == v.GONE){
                    imageSwitcherContainerEspacioConfinado.setVisibility(v.VISIBLE);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione imagenes"), CHOOSE_IMAGEACTIVITY_REQUEST_CODEACCESOESPACIOCONFINADO);
            }
        });
        final Button botonFotoEspacioConfinado=getActivity().findViewById(R.id.botonFotoTOPTENEspacioConfinado);
        botonFotoEspacioConfinado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerEspacioConfinado.getVisibility() == v.GONE){
                    imageSwitcherContainerEspacioConfinado.setVisibility(v.VISIBLE);
                }
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "ehss_"+timeStamp + ".jpg";
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + imageFileName);
                imageUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEACCESOESPACIOCONFINADO);
            }
        });
        fotoImageSwitcherEspacioConfinado = getActivity().findViewById(R.id.fotoImageSwitcherEspacioConfinado);
        fotoImageSwitcherEspacioConfinado.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView viewImage = new ImageView(getContext());
                return viewImage;
            }
        });
        Button anterior3 = getActivity().findViewById(R.id.botonAnteriorImageSwitcherEspacioConfinado);
        anterior3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionEspacioConfinado>0){
                    positionEspacioConfinado--;
                    fotoImageSwitcherEspacioConfinado.setImageURI(imagenEspacioConfinadoUris.get(positionEspacioConfinado));
                }else {
                    Toast.makeText(getActivity(),"No hay imágen anterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button siguiente3 = getActivity().findViewById(R.id.botonSiguienteImageSwitcherEspacioConfinado);
        siguiente3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionEspacioConfinado<imagenEspacioConfinadoUris.size() - 1){
                    positionEspacioConfinado++;
                    fotoImageSwitcherEspacioConfinado.setImageURI(imagenEspacioConfinadoUris.get(positionEspacioConfinado));
                }else{
                    Toast.makeText(getActivity(),"No hay imágen posterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button botonBorrar3 = getActivity().findViewById(R.id.eliminarFotoEspacioConfinado);
        botonBorrar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagenEspacioConfinadoUris.size()>0){
                    String imageTag = imagenEspacioConfinadoUris.get(positionEspacioConfinado).getPath();
                    File fileTag = new File(imageTag);
                    if (fileTag.exists()) {
                        fileTag.delete();
                    }
                    //eliminar de imagenEspaciosConfinadoUris
                    imagenEspacioConfinadoUris.remove(positionEspacioConfinado);
                    if(positionEspacioConfinado>0){
                        positionEspacioConfinado--;
                        fotoImageSwitcherEspacioConfinado.setImageURI(imagenEspacioConfinadoUris.get(positionEspacioConfinado));
                    }else{
                        if(imagenEspacioConfinadoUris.size()>0) {
                            positionEspacioConfinado++;
                            fotoImageSwitcherEspacioConfinado.setImageURI(imagenEspacioConfinadoUris.get(positionEspacioConfinado));
                        }else{
                            positionEspacioConfinado = 0;
                            fotoImageSwitcherEspacioConfinado.setImageURI(null);
                        }
                    }
                    Toast.makeText(getActivity(), "Imagen borrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Fotos Trabajo En Altura
        final Button botonElegirFotoTrabajoEnAltura=getActivity().findViewById(R.id.buttonElegirFotoTOPTENTrabajoEnAltura);
        botonElegirFotoTrabajoEnAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerTrabajoEnAltura.getVisibility() == v.GONE){
                    imageSwitcherContainerTrabajoEnAltura.setVisibility(v.VISIBLE);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione imagenes"), CHOOSE_IMAGEACTIVITY_REQUEST_CODETRABAJOENALTURA);
            }
        });
        final Button botonFotoTrabajoEnAltura=getActivity().findViewById(R.id.botonFotoTOPTENTrabajoEnAltura);
        botonFotoTrabajoEnAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerTrabajoEnAltura.getVisibility() == v.GONE){
                    imageSwitcherContainerTrabajoEnAltura.setVisibility(v.VISIBLE);
                }
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "ehss_"+timeStamp + ".jpg";
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + imageFileName);
                imageUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODETRABAJOENALTURA);
            }
        });
        fotoImageSwitcherTrabajoEnAltura = getActivity().findViewById(R.id.fotoImageSwitcherTrabajoEnAltura);
        fotoImageSwitcherTrabajoEnAltura.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView viewImage = new ImageView(getContext());
                return viewImage;
            }
        });
        Button anterior4 = getActivity().findViewById(R.id.botonAnteriorImageSwitcherTrabajoEnAltura);
        anterior4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionTrabajoEnAltura>0){
                    positionTrabajoEnAltura--;
                    fotoImageSwitcherTrabajoEnAltura.setImageURI(imagenTrabajoEnAlturaUris.get(positionTrabajoEnAltura));
                }else {
                    Toast.makeText(getActivity(),"No hay imágen anterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button siguiente4 = getActivity().findViewById(R.id.botonSiguienteImageSwitcherTrabajoEnAltura);
        siguiente4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionTrabajoEnAltura<imagenTrabajoEnAlturaUris.size() - 1){
                    positionTrabajoEnAltura++;
                    fotoImageSwitcherTrabajoEnAltura.setImageURI(imagenTrabajoEnAlturaUris.get(positionTrabajoEnAltura));
                }else{
                    Toast.makeText(getActivity(),"No hay imágen posterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button botonBorrar4 = getActivity().findViewById(R.id.eliminarFotoTrabajoEnAltura);
        botonBorrar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagenTrabajoEnAlturaUris.size()>0){
                    String imageTag = imagenTrabajoEnAlturaUris.get(positionTrabajoEnAltura).getPath();
                    File fileTag = new File(imageTag);
                    if (fileTag.exists()) {
                        fileTag.delete();
                    }
                    //eliminar de imagenEspaciosConfinadoUris
                    imagenTrabajoEnAlturaUris.remove(positionTrabajoEnAltura);
                    if(positionTrabajoEnAltura>0){
                        positionTrabajoEnAltura--;
                        fotoImageSwitcherTrabajoEnAltura.setImageURI(imagenTrabajoEnAlturaUris.get(positionTrabajoEnAltura));
                    }else{
                        if(imagenTrabajoEnAlturaUris.size()>0) {
                            positionTrabajoEnAltura++;
                            fotoImageSwitcherTrabajoEnAltura.setImageURI(imagenTrabajoEnAlturaUris.get(positionTrabajoEnAltura));
                        }else{
                            positionTrabajoEnAltura = 0;
                            fotoImageSwitcherTrabajoEnAltura.setImageURI(null);
                        }
                    }
                    Toast.makeText(getActivity(), "Imagen borrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Fotos Trabajo En Caliente
        final Button botonElegirFotoTrabajoEnCaliente=getActivity().findViewById(R.id.buttonElegirFotoTOPTENTrabajoEnCaliente);
        botonElegirFotoTrabajoEnCaliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerTrabajoEnCaliente.getVisibility() == v.GONE){
                    imageSwitcherContainerTrabajoEnCaliente.setVisibility(v.VISIBLE);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione imagenes"), CHOOSE_IMAGEACTIVITY_REQUEST_CODETRABAJOENCALIENTE);
            }
        });
        final Button botonFotoTrabajoEnCaliente=getActivity().findViewById(R.id.botonFotoTOPTENTrabajoEnCaliente);
        botonFotoTrabajoEnCaliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerTrabajoEnCaliente.getVisibility() == v.GONE){
                    imageSwitcherContainerTrabajoEnCaliente.setVisibility(v.VISIBLE);
                }
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "ehss_"+timeStamp + ".jpg";
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + imageFileName);
                imageUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODETRABAJOENCALIENTE);
            }
        });
        fotoImageSwitcherTrabajoEnCaliente = getActivity().findViewById(R.id.fotoImageSwitcherTrabajoEnCaliente);
        fotoImageSwitcherTrabajoEnCaliente.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView viewImage = new ImageView(getContext());
                return viewImage;
            }
        });
        Button anterior5 = getActivity().findViewById(R.id.botonAnteriorImageSwitcherTrabajoEnCaliente);
        anterior5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionTrabajoEnCaliente>0){
                    positionTrabajoEnCaliente--;
                    fotoImageSwitcherTrabajoEnCaliente.setImageURI(imagenTrabajoEnCalienteUris.get(positionTrabajoEnCaliente));
                }else {
                    Toast.makeText(getActivity(),"No hay imágen anterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button siguiente5 = getActivity().findViewById(R.id.botonSiguienteImageSwitcherTrabajoEnCaliente);
        siguiente5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionTrabajoEnCaliente<imagenTrabajoEnCalienteUris.size() - 1){
                    positionTrabajoEnCaliente++;
                    fotoImageSwitcherTrabajoEnCaliente.setImageURI(imagenTrabajoEnCalienteUris.get(positionTrabajoEnCaliente));
                }else{
                    Toast.makeText(getActivity(),"No hay imágen posterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button botonBorrar5 = getActivity().findViewById(R.id.eliminarFotoTrabajoEnCaliente);
        botonBorrar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagenTrabajoEnCalienteUris.size()>0){
                    String imageTag = imagenTrabajoEnCalienteUris.get(positionTrabajoEnCaliente).getPath();
                    File fileTag = new File(imageTag);
                    if (fileTag.exists()) {
                        fileTag.delete();
                    }
                    //eliminar de imagenEspaciosConfinadoUris
                    imagenTrabajoEnCalienteUris.remove(positionTrabajoEnCaliente);
                    if(positionTrabajoEnCaliente>0){
                        positionTrabajoEnCaliente--;
                        fotoImageSwitcherTrabajoEnCaliente.setImageURI(imagenTrabajoEnCalienteUris.get(positionTrabajoEnCaliente));
                    }else{
                        if(imagenTrabajoEnCalienteUris.size()>0) {
                            positionTrabajoEnCaliente++;
                            fotoImageSwitcherTrabajoEnCaliente.setImageURI(imagenTrabajoEnCalienteUris.get(positionTrabajoEnCaliente));
                        }else{
                            positionTrabajoEnCaliente = 0;
                            fotoImageSwitcherTrabajoEnCaliente.setImageURI(null);
                        }
                    }
                    Toast.makeText(getActivity(), "Imagen borrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Fotos Apertura Lineas
        final Button botonElegirFotoAperturaLineas=getActivity().findViewById(R.id.buttonElegirFotoTOPTENAperturaLineas);
        botonElegirFotoAperturaLineas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerAperturaLinea.getVisibility() == v.GONE){
                    imageSwitcherContainerAperturaLinea.setVisibility(v.VISIBLE);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione imagenes"), CHOOSE_IMAGEACTIVITY_REQUEST_CODEAPERTURALINEAEQUIPOS);
            }
        });
        final Button botonFotoAperturaLineas=getActivity().findViewById(R.id.botonFotoTOPTENAperturaLineas);
        botonFotoAperturaLineas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerAperturaLinea.getVisibility() == v.GONE){
                    imageSwitcherContainerAperturaLinea.setVisibility(v.VISIBLE);
                }
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "ehss_"+timeStamp + ".jpg";
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + imageFileName);
                imageUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEAPERTURALINEAEQUIPOS);
            }
        });
        fotoImageSwitcherAperturaLinea = getActivity().findViewById(R.id.fotoImageSwitcherAperturaLineas);
        fotoImageSwitcherAperturaLinea.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView viewImage = new ImageView(getContext());
                return viewImage;
            }
        });
        Button anterior6 = getActivity().findViewById(R.id.botonAnteriorImageSwitcherAperturaLineas);
        anterior6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionAperturaLinea>0){
                    positionAperturaLinea--;
                    fotoImageSwitcherAperturaLinea.setImageURI(imagenAperturaLineaUris.get(positionAperturaLinea));
                }else {
                    Toast.makeText(getActivity(),"No hay imágen anterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button siguiente6 = getActivity().findViewById(R.id.botonSiguienteImageSwitcherAperturaLineas);
        siguiente6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionAperturaLinea<imagenAperturaLineaUris.size() - 1){
                    positionAperturaLinea++;
                    fotoImageSwitcherAperturaLinea.setImageURI(imagenAperturaLineaUris.get(positionAperturaLinea));
                }else{
                    Toast.makeText(getActivity(),"No hay imágen posterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button botonBorrar6 = getActivity().findViewById(R.id.eliminarFotoAperturaLineas);
        botonBorrar6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagenAperturaLineaUris.size()>0){
                    String imageTag = imagenAperturaLineaUris.get(positionAperturaLinea).getPath();
                    File fileTag = new File(imageTag);
                    if (fileTag.exists()) {
                        fileTag.delete();
                    }
                    //eliminar de imagenEspaciosConfinadoUris
                    imagenAperturaLineaUris.remove(positionAperturaLinea);
                    if(positionAperturaLinea>0){
                        positionAperturaLinea--;
                        fotoImageSwitcherAperturaLinea.setImageURI(imagenAperturaLineaUris.get(positionAperturaLinea));
                    }else{
                        if(imagenAperturaLineaUris.size()>0) {
                            positionAperturaLinea++;
                            fotoImageSwitcherAperturaLinea.setImageURI(imagenAperturaLineaUris.get(positionAperturaLinea));
                        }else{
                            positionAperturaLinea = 0;
                            fotoImageSwitcherAperturaLinea.setImageURI(null);
                        }
                    }
                    Toast.makeText(getActivity(), "Imagen borrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Fotos Hidrolavadora
        final Button botonElegirFotoHidrolavadora=getActivity().findViewById(R.id.buttonElegirFotoTOPTENHidrolavadora);
        botonElegirFotoHidrolavadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerHidrolavadora.getVisibility() == v.GONE){
                    imageSwitcherContainerHidrolavadora.setVisibility(v.VISIBLE);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione imagenes"), CHOOSE_IMAGEACTIVITY_REQUEST_CODEHIDROLAVADORA);
            }
        });
        final Button botonFotoHidrolavadora=getActivity().findViewById(R.id.botonFotoTOPTENHidrolavadora);
        botonFotoHidrolavadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainerHidrolavadora.getVisibility() == v.GONE){
                    imageSwitcherContainerHidrolavadora.setVisibility(v.VISIBLE);
                }
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "ehss_"+timeStamp + ".jpg";
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + imageFileName);
                imageUri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEHIDROLAVADORA);
            }
        });
        fotoImageSwitcherHidrolavadora = getActivity().findViewById(R.id.fotoImageSwitcherHidrolavadora);
        fotoImageSwitcherHidrolavadora.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView viewImage = new ImageView(getContext());
                return viewImage;
            }
        });
        Button anterior7 = getActivity().findViewById(R.id.botonAnteriorImageSwitcherHidrolavadora);
        anterior7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionHidrolavadora>0){
                    positionHidrolavadora--;
                    fotoImageSwitcherHidrolavadora.setImageURI(imagenHidrolavadoraUris.get(positionHidrolavadora));
                }else {
                    Toast.makeText(getActivity(),"No hay imágen anterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button siguiente7 = getActivity().findViewById(R.id.botonSiguienteImageSwitcherHidrolavadora);
        siguiente7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionHidrolavadora<imagenHidrolavadoraUris.size() - 1){
                    positionHidrolavadora++;
                    fotoImageSwitcherHidrolavadora.setImageURI(imagenHidrolavadoraUris.get(positionHidrolavadora));
                }else{
                    Toast.makeText(getActivity(),"No hay imágen posterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button botonBorrar7 = getActivity().findViewById(R.id.eliminarFotoHidrolavadora);
        botonBorrar7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagenHidrolavadoraUris.size()>0){
                    String imageTag = imagenHidrolavadoraUris.get(positionHidrolavadora).getPath();
                    File fileTag = new File(imageTag);
                    if (fileTag.exists()) {
                        fileTag.delete();
                    }
                    //eliminar de imagenEspaciosConfinadoUris
                    imagenHidrolavadoraUris.remove(positionHidrolavadora);
                    if(positionHidrolavadora>0){
                        positionHidrolavadora--;
                        fotoImageSwitcherHidrolavadora.setImageURI(imagenHidrolavadoraUris.get(positionHidrolavadora));
                    }else{
                        if(imagenHidrolavadoraUris.size()>0) {
                            positionHidrolavadora++;
                            fotoImageSwitcherHidrolavadora.setImageURI(imagenHidrolavadoraUris.get(positionHidrolavadora));
                        }else{
                            positionHidrolavadora = 0;
                            fotoImageSwitcherHidrolavadora.setImageURI(null);
                        }
                    }
                    Toast.makeText(getActivity(), "Imagen borrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button botonGuardar=getActivity().findViewById(R.id.botonGuardarTopTen);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    String spinnerSectorSeleccion=spinnerSector.getSelectedItem().toString();
                    String spinnerObservadorUnoSeleccion=spinnerObservadorUno.getSelectedItem().toString();
                    String spinnerObservadorDosSeleccion=etObservadorDos.getText().toString();
                    String spinnerSiteSeleccion=spinnerSite.getSelectedItem().toString();

                    //PTS
                    String txtPTSFechaEmision=etPTSFecha.getText().toString();
                    String txtPTSConfecciono=etPTSConfecciono.getText().toString();
                    String txtPTSObservaciones=etPTSObservaciones.getText().toString();

                    RadioGroup rg_PTSValidezPermiso = (RadioGroup)getActivity().findViewById(R.id.core1);
                    int idPTSValidezPermiso=rg_PTSValidezPermiso.getCheckedRadioButtonId();
                    RadioButton radioButtonPTSValidezPermiso = (RadioButton)getActivity().findViewById(idPTSValidezPermiso);
                    String core1=radioButtonPTSValidezPermiso.getText().toString();

                    RadioGroup rg_PTSFirmasResponsables = (RadioGroup)getActivity().findViewById(R.id.core2);
                    int idPTSFirmasResponsables=rg_PTSFirmasResponsables.getCheckedRadioButtonId();
                    RadioButton radioButtonPTSFirmasResponsables = (RadioButton)getActivity().findViewById(idPTSFirmasResponsables);
                    String core2=radioButtonPTSFirmasResponsables.getText().toString();

                    RadioGroup rg_PTSSectorTrabajo = (RadioGroup)getActivity().findViewById(R.id.core3);
                    int idPTSSectorTrabajo=rg_PTSSectorTrabajo.getCheckedRadioButtonId();
                    RadioButton radioButtonPTSSectorTrabajo = (RadioButton)getActivity().findViewById(idPTSSectorTrabajo);
                    String core3=radioButtonPTSSectorTrabajo.getText().toString();

                    RadioGroup rg_PTSListadoPersonal = (RadioGroup)getActivity().findViewById(R.id.core4);
                    int idPTSListadoPersonal=rg_PTSListadoPersonal.getCheckedRadioButtonId();
                    RadioButton radioButtonPTSListadoPersonal = (RadioButton)getActivity().findViewById(idPTSListadoPersonal);
                    String core4=radioButtonPTSListadoPersonal.getText().toString();

                    RadioGroup rg_PTSDescripcionTrabajo = (RadioGroup)getActivity().findViewById(R.id.core5);
                    int idPTSDescripcionTrabajo=rg_PTSDescripcionTrabajo.getCheckedRadioButtonId();
                    RadioButton radioButtonPTSDescripcionTrabajo = (RadioButton)getActivity().findViewById(idPTSDescripcionTrabajo);
                    String core5=radioButtonPTSDescripcionTrabajo.getText().toString();

                    RadioGroup rg_PTSInspeccionSector = (RadioGroup)getActivity().findViewById(R.id.core6);
                    int idPTSInspeccionSector=rg_PTSInspeccionSector.getCheckedRadioButtonId();
                    RadioButton radioButtonPTSInspeccionSector = (RadioButton)getActivity().findViewById(idPTSInspeccionSector);
                    String core6=radioButtonPTSInspeccionSector.getText().toString();

                    RadioGroup rg_PTSRiesgosFisicos = (RadioGroup)getActivity().findViewById(R.id.core7);
                    int idPTSRiesgosFisicos=rg_PTSRiesgosFisicos.getCheckedRadioButtonId();
                    RadioButton radioButtonPTSRiesgosFisicos = (RadioButton)getActivity().findViewById(idPTSRiesgosFisicos);
                    String core7=radioButtonPTSRiesgosFisicos.getText().toString();

                    RadioGroup rg_PTSRiesgosQuimicos = (RadioGroup)getActivity().findViewById(R.id.core8);
                    int idPTSRiesgosQuimicos=rg_PTSRiesgosQuimicos.getCheckedRadioButtonId();
                    RadioButton radioButtonPTSRiesgosQuimicos = (RadioButton)getActivity().findViewById(idPTSRiesgosQuimicos);
                    String core8=radioButtonPTSRiesgosQuimicos.getText().toString();

                    RadioGroup rg_PTSRiesgosAmbientales = (RadioGroup)getActivity().findViewById(R.id.core9);
                    int idPTSRiesgosAmbientales =rg_PTSRiesgosAmbientales .getCheckedRadioButtonId();
                    RadioButton radioButtonPTSRiesgosAmbientales  = (RadioButton)getActivity().findViewById(idPTSRiesgosAmbientales);
                    String core9 =radioButtonPTSRiesgosAmbientales .getText().toString();

                    RadioGroup rg_PTSEPPAdicional = (RadioGroup)getActivity().findViewById(R.id.core10);
                    int idPTSEPPAdicional =rg_PTSEPPAdicional .getCheckedRadioButtonId();
                    RadioButton radioButtonPTSEPPAdicional  = (RadioButton)getActivity().findViewById(idPTSEPPAdicional);
                    String core10 =radioButtonPTSEPPAdicional .getText().toString();

                    //MasterTarjetasRojas

                    String txtTarjetasRojasFechaEmison=etMasterTarjetasFecha.getText().toString();
                    String txtTarjetasRojasConfecciono=etMasterTarjetasConfecciono.getText().toString();
                    String txtTarjetasRojasObservaciones=etMasterTarjetasObservaciones.getText().toString();

                    RadioGroup rg_TarjetasRojasCantTarj = (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasCantTarj);
                    int idTarjetasRojasCantTarj=rg_TarjetasRojasCantTarj .getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasCantTarj  = (RadioButton)getActivity().findViewById(idTarjetasRojasCantTarj);
                    String TarjetasRojasCantTarj=radioButtonTarjetasRojasCantTarj.getText().toString();

                    RadioGroup rg_TarjetasRojasEquipoFuera = (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasEquipoFuera);
                    int idTarjetasRojasEquipoFuera=rg_TarjetasRojasEquipoFuera.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasEquipoFuera= (RadioButton)getActivity().findViewById(idTarjetasRojasEquipoFuera);
                    String TarjetasRojasEquipoFuera=radioButtonTarjetasRojasEquipoFuera.getText().toString();

                    RadioGroup rg_TarjetasRojasRazon= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasRazon);
                    int idTarjetasRojasRazon=rg_TarjetasRojasRazon.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasRazon= (RadioButton)getActivity().findViewById(idTarjetasRojasRazon);
                    String TarjetasRojasRazon=radioButtonTarjetasRojasRazon.getText().toString();

                    RadioGroup rg_TarjetasRojasTarea = (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasTarea);
                    int idTarjetasRojasTarea=rg_TarjetasRojasTarea.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasTarea= (RadioButton)getActivity().findViewById(idTarjetasRojasTarea);
                    String TarjetasRojasTarea=radioButtonTarjetasRojasTarea.getText().toString();

                    RadioGroup rg_TarjetasRojasDpto= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasDpto);
                    int idTarjetasRojasDpto=rg_TarjetasRojasDpto.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasDpto= (RadioButton)getActivity().findViewById(idTarjetasRojasDpto);
                    String TarjetasRojasDpto=radioButtonTarjetasRojasDpto.getText().toString();

                    RadioGroup rg_TarjetasRojasAcepto= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasAcepto);
                    int idTarjetasRojasAcepto=rg_TarjetasRojasAcepto.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasAcepto= (RadioButton)getActivity().findViewById(idTarjetasRojasAcepto);
                    String TarjetasRojasAcepto=radioButtonTarjetasRojasAcepto.getText().toString();

                    RadioGroup rg_TarjetasRojasApellido= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasApellido);
                    int idTarjetasRojasApellido=rg_TarjetasRojasApellido.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasApellido= (RadioButton)getActivity().findViewById(idTarjetasRojasApellido);
                    String TarjetasRojasApellido=radioButtonTarjetasRojasApellido.getText().toString();

                    RadioGroup rg_TarjetasRojasCancelacion= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasCancelacion);
                    int idTarjetasRojasCancelacion=rg_TarjetasRojasCancelacion.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasCancelacion= (RadioButton)getActivity().findViewById(idTarjetasRojasCancelacion);
                    String TarjetasRojasCancelacion=radioButtonTarjetasRojasCancelacion.getText().toString();

                    RadioGroup rg_TarjetasRojasRepDeLaInst= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasRepDeLaInst);
                    int idTarjetasRojasRepDeLaInst=rg_TarjetasRojasRepDeLaInst.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasRepDeLaInst= (RadioButton)getActivity().findViewById(idTarjetasRojasRepDeLaInst);
                    String TarjetasRojasRepDeLaInst=radioButtonTarjetasRojasRepDeLaInst.getText().toString();

                    RadioGroup rg_TarjetasRojasAislacionFisica= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasAislacionFisica);
                    int idTarjetasRojasAislacionFisica=rg_TarjetasRojasAislacionFisica.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasAislacionFisica= (RadioButton)getActivity().findViewById(idTarjetasRojasAislacionFisica);
                    String TarjetasRojasAislacionFisica=radioButtonTarjetasRojasAislacionFisica.getText().toString();

                    RadioGroup rg_TarjetasRojasRepTrabajo= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasRepTrabajo);
                    int idTarjetasRojasRepTrabajo=rg_TarjetasRojasRepTrabajo.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasRepTrabajo= (RadioButton)getActivity().findViewById(idTarjetasRojasRepTrabajo);
                    String TarjetasRojasRepTrabajo=radioButtonTarjetasRojasRepTrabajo.getText().toString();

                    RadioGroup rg_TarjetasRojasTarjetasRetiradas= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasTarjetasRetiradas);
                    int idTarjetasRojasTarjetasRetiradas=rg_TarjetasRojasTarjetasRetiradas.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasTarjetasRetiradas= (RadioButton)getActivity().findViewById(idTarjetasRojasTarjetasRetiradas);
                    String TarjetasRojasTarjetasRetiradas=radioButtonTarjetasRojasTarjetasRetiradas.getText().toString();

                    RadioGroup rg_TarjetasRojasNroTarjeta= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasNroTarjeta);
                    int idTarjetasRojasNroTarjeta=rg_TarjetasRojasNroTarjeta.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasNroTarjeta= (RadioButton)getActivity().findViewById(idTarjetasRojasNroTarjeta);
                    String TarjetasRojasNroTarjeta=radioButtonTarjetasRojasNroTarjeta.getText().toString();

                    RadioGroup rg_TarjetasRojasUbicacion= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasUbicacion);
                    int idTarjetasRojasUbicacion=rg_TarjetasRojasUbicacion.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasUbicacion= (RadioButton)getActivity().findViewById(idTarjetasRojasUbicacion);
                    String TarjetasRojasUbicacion=radioButtonTarjetasRojasUbicacion.getText().toString();

                    RadioGroup rg_TarjetasRojasColocadoPor= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasColocadoPor);
                    int idTarjetasRojasColocadoPor=rg_TarjetasRojasColocadoPor.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasColocadoPor= (RadioButton)getActivity().findViewById(idTarjetasRojasColocadoPor);
                    String TarjetasRojasColocadoPor=radioButtonTarjetasRojasColocadoPor.getText().toString();

                    RadioGroup rg_TarjetasRojasFechaRetiro= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasFechaRetiro);
                    int idTarjetasRojasFechaRetiro=rg_TarjetasRojasFechaRetiro.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasFechaRetiro= (RadioButton)getActivity().findViewById(idTarjetasRojasFechaRetiro);
                    String TarjetasRojasFechaRetiro=radioButtonTarjetasRojasFechaRetiro.getText().toString();

                    RadioGroup rg_TarjetasRojasRetirada= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasRetirada);
                    int idTarjetasRojasRetirada=rg_TarjetasRojasRetirada.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasRetirada= (RadioButton)getActivity().findViewById(idTarjetasRojasRetirada);
                    String TarjetasRojasRetirada=radioButtonTarjetasRojasRetirada.getText().toString();

                    RadioGroup rg_TarjetasRojasConciliado= (RadioGroup)getActivity().findViewById(R.id.TarjetasRojasConciliado);
                    int idTarjetasRojasConciliado=rg_TarjetasRojasConciliado.getCheckedRadioButtonId();
                    RadioButton radioButtonTarjetasRojasConciliado= (RadioButton)getActivity().findViewById(idTarjetasRojasConciliado);
                    String TarjetasRojasConciliado=radioButtonTarjetasRojasConciliado.getText().toString();

                    //Espacio Confinado

                    String txtEspacioConfinadoFechaEmision=etEspacioConfinadoFecha.getText().toString();
                    String txtEspacioConfinadoConfecciono=etEspacioConfinadoConfecciono.getText().toString();
                    String txtEspacioConfinadoObservaciones=etEspacioConfinadoObservaciones.getText().toString();

                    RadioGroup rg_EspacioConfinadoIdentificacion = (RadioGroup)getActivity().findViewById(R.id.IdentificacionEspacioConfinado);
                    int idEspacioConfinadoIdentificacion=rg_EspacioConfinadoIdentificacion.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoIdentificacion = (RadioButton)getActivity().findViewById(idEspacioConfinadoIdentificacion);
                    String EspacioConfinadoIdentificacion=radioButtonEspacioConfinadoIdentificacion.getText().toString();

                    RadioGroup rg_EspacioConfinadoPermisoAEC = (RadioGroup)getActivity().findViewById(R.id.PTSPermisoAEC);
                    int idEspacioConfinadoPermisoAEC=rg_EspacioConfinadoPermisoAEC.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoPermisoAEC = (RadioButton)getActivity().findViewById(idEspacioConfinadoPermisoAEC);
                    String EspacioConfinadoPermisoAEC=radioButtonEspacioConfinadoPermisoAEC.getText().toString();

                    RadioGroup rg_EspacioConfinadoVigenciaDelPermiso = (RadioGroup)getActivity().findViewById(R.id.VigenciaPermisoTrabajo);
                    int idEspacioConfinadoVigenciaDelPermiso=rg_EspacioConfinadoVigenciaDelPermiso.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoVigenciaDelPermiso = (RadioButton)getActivity().findViewById(idEspacioConfinadoVigenciaDelPermiso);
                    String EspacioConfinadoVigenciaDelPermiso=radioButtonEspacioConfinadoVigenciaDelPermiso.getText().toString();

                    RadioGroup rg_EspacioConfinadoIngreso = (RadioGroup)getActivity().findViewById(R.id.IngresoEspacioConfinado);
                    int idEspacioConfinadoIngreso=rg_EspacioConfinadoIngreso.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoIngreso = (RadioButton)getActivity().findViewById(idEspacioConfinadoIngreso);
                    String EspacioConfinadoIngreso=radioButtonEspacioConfinadoIngreso.getText().toString();

                    RadioGroup rg_EspacioConfinadoVigiaExterno = (RadioGroup)getActivity().findViewById(R.id.VigiasExternos);
                    int idEspacioConfinadoVigiaExterno=rg_EspacioConfinadoVigiaExterno.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoVigiaExterno = (RadioButton)getActivity().findViewById(idEspacioConfinadoVigiaExterno);
                    String EspacioConfinadoVigiaExterno=radioButtonEspacioConfinadoVigiaExterno.getText().toString();

                    RadioGroup rg_EspacioConfinadoVigia = (RadioGroup)getActivity().findViewById(R.id.ComprendeVigia);
                    int idEspacioConfinadoVigia=rg_EspacioConfinadoVigia.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoVigia = (RadioButton)getActivity().findViewById(idEspacioConfinadoVigia);
                    String EspacioConfinadoVigia=radioButtonEspacioConfinadoVigia.getText().toString();

                    RadioGroup rg_EspacioConfinadoPersonalInvolucrado= (RadioGroup)getActivity().findViewById(R.id.PersonalInvolucrado);
                    int idEspacioConfinadoPersonalInvolucrado=rg_EspacioConfinadoPersonalInvolucrado.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoPersonalInvolucrado= (RadioButton)getActivity().findViewById(idEspacioConfinadoPersonalInvolucrado);
                    String EspacioConfinadoPersonalInvolucrado=radioButtonEspacioConfinadoPersonalInvolucrado.getText().toString();

                    RadioGroup rg_EspacioConfinadoCheckListAdicionales= (RadioGroup)getActivity().findViewById(R.id.CheckListAdicionalesEspacioConfinado);
                    int idEspacioConfinadoCheckListAdicionales=rg_EspacioConfinadoCheckListAdicionales.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoCheckListAdicionales= (RadioButton)getActivity().findViewById(idEspacioConfinadoCheckListAdicionales);
                    String EspacioConfinadoCheckListAdicionales=radioButtonEspacioConfinadoCheckListAdicionales.getText().toString();

                    RadioGroup rg_EspacioConfinadoAislacion= (RadioGroup)getActivity().findViewById(R.id.AislacionEspacioConfinado);
                    int idEspacioConfinadoAislacion=rg_EspacioConfinadoAislacion.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoAislacion= (RadioButton)getActivity().findViewById(idEspacioConfinadoAislacion);
                    String EspacioConfinadoAislacion=radioButtonEspacioConfinadoAislacion.getText().toString();

                    RadioGroup rg_EspacioConfinadoEquiposElectricos= (RadioGroup)getActivity().findViewById(R.id.EquiposElectricosEspacioConfinado);
                    int idEspacioConfinadoEquiposElectricos=rg_EspacioConfinadoEquiposElectricos.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoEquiposElectricos= (RadioButton)getActivity().findViewById(idEspacioConfinadoEquiposElectricos);
                    String EspacioConfinadoEquiposElectricos=radioButtonEspacioConfinadoEquiposElectricos.getText().toString();

                    RadioGroup rg_EspacioConfinadoLimpiezaVentilacion= (RadioGroup)getActivity().findViewById(R.id.LimpiezaVentilacionEspacioConfinado);
                    int idEspacioConfinadoLimpiezaVentilacion=rg_EspacioConfinadoLimpiezaVentilacion.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoLimpiezaVentilacion= (RadioButton)getActivity().findViewById(idEspacioConfinadoLimpiezaVentilacion);
                    String EspacioConfinadoLimpiezaVentilacion=radioButtonEspacioConfinadoLimpiezaVentilacion.getText().toString();

                    RadioGroup rg_EspacioConfinadoHerramientasEquipos= (RadioGroup)getActivity().findViewById(R.id.HerramientasEquiposEspaciosConfinados);
                    int idEspacioConfinadoHerramientasEquipos=rg_EspacioConfinadoHerramientasEquipos.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoHerramientasEquipos= (RadioButton)getActivity().findViewById(idEspacioConfinadoHerramientasEquipos);
                    String EspacioConfinadoHerramientasEquipos=radioButtonEspacioConfinadoHerramientasEquipos.getText().toString();

                    RadioGroup rg_EspacioConfinadoMonitoreo= (RadioGroup)getActivity().findViewById(R.id.MonitoreoEspacioConfinado);
                    int idEspacioConfinadoMonitoreo=rg_EspacioConfinadoMonitoreo.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoMonitoreo= (RadioButton)getActivity().findViewById(idEspacioConfinadoMonitoreo);
                    String EspacioConfinadoMonitoreo=radioButtonEspacioConfinadoMonitoreo.getText().toString();

                    RadioGroup rg_EspacioConfinadoEntrada= (RadioGroup)getActivity().findViewById(R.id.EntradaEspacioConfinado);
                    int idEspacioConfinadoEntrada=rg_EspacioConfinadoEntrada.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoEntrada= (RadioButton)getActivity().findViewById(idEspacioConfinadoEntrada);
                    String EspacioConfinadoEntrada=radioButtonEspacioConfinadoEntrada.getText().toString();

                    RadioGroup rg_EspacioConfinadoRescate= (RadioGroup)getActivity().findViewById(R.id.RescateEspacioConfinado);
                    int idEspacioConfinadoRescate=rg_EspacioConfinadoRescate.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoRescate= (RadioButton)getActivity().findViewById(idEspacioConfinadoRescate);
                    String EspacioConfinadoRescate=radioButtonEspacioConfinadoRescate.getText().toString();

                    RadioGroup rg_EspacioConfinadoFirmaResponsable= (RadioGroup)getActivity().findViewById(R.id.FirmaResponsableEspacioConfinado);
                    int idEspacioConfinadoFirmaResponsable=rg_EspacioConfinadoFirmaResponsable.getCheckedRadioButtonId();
                    RadioButton radioButtonEspacioConfinadoFirmaResponsable= (RadioButton)getActivity().findViewById(idEspacioConfinadoFirmaResponsable);
                    String EspacioConfinadoFirmaResponsable=radioButtonEspacioConfinadoFirmaResponsable.getText().toString();

                    //Trabajo en Altura

                    String txtTrabajoEnAlturaFechaEmision=etTrabajoEnAlturaFecha.getText().toString();
                    String txtTrabajoEnAlturaConfecciono=etTrabajoEnAlturaConfecciono.getText().toString();
                    String txtTrabajoEnAlturaObservaciones=etTrabajoEnAlturaObservaciones.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaIdentificacionSector = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaIdentificacionSector);
                    int idPTrabajoEnAlturaIdentificacionSector=rg_TrabajoEnAlturaIdentificacionSector.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaIdentificacionSector = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaIdentificacionSector);
                    String TrabajoEnAlturaIdentificacionSector=radioButtonTrabajoEnAlturaIdentificacionSector.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaPTSCaidas = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaPTSCaidas);
                    int idPTrabajoEnAlturaPTSCaidas=rg_TrabajoEnAlturaPTSCaidas.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaPTSCaidas = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaPTSCaidas);
                    String TrabajoEnAlturaPTSCaidas=radioButtonTrabajoEnAlturaPTSCaidas.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaVigenciaPT = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaVigenciaPT);
                    int idPTrabajoEnAlturaVigenciaPT=rg_TrabajoEnAlturaVigenciaPT.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaVigenciaPT = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaVigenciaPT);
                    String TrabajoEnAlturaVigenciaPT=radioButtonTrabajoEnAlturaVigenciaPT.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaEPPCaidas = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaEPPCaidas);
                    int idPTrabajoEnAlturaEPPCaidas=rg_TrabajoEnAlturaEPPCaidas.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaEPPCaidas = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaEPPCaidas);
                    String TrabajoEnAlturaEPPCaidas=radioButtonTrabajoEnAlturaEPPCaidas.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaHabilitacionEPP = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaHabilitacionEPP);
                    int idPTrabajoEnAlturaHabilitacionEPP=rg_TrabajoEnAlturaHabilitacionEPP.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaHabilitacionEPP = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaHabilitacionEPP);
                    String TrabajoEnAlturaHabilitacionEPP=radioButtonTrabajoEnAlturaHabilitacionEPP.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaAndamioHabilitado = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaAndamioHabilitado);
                    int idPTrabajoEnAlturaAndamioHabilitado=rg_TrabajoEnAlturaAndamioHabilitado.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaAndamioHabilitado = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaAndamioHabilitado);
                    String TrabajoEnAlturaAndamioHabilitado=radioButtonTrabajoEnAlturaAndamioHabilitado.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaBarandasCondiciones = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaBarandasCondiciones);
                    int idPTrabajoEnAlturaBarandasCondiciones=rg_TrabajoEnAlturaBarandasCondiciones.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaBarandasCondiciones = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaBarandasCondiciones);
                    String TrabajoEnAlturaBarandasCondiciones=radioButtonTrabajoEnAlturaBarandasCondiciones.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaCondicionesEscaleras = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaCondicionesEscaleras);
                    int idPTrabajoEnAlturaCondicionesEscaleras=rg_TrabajoEnAlturaCondicionesEscaleras.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaCondicionesEscaleras = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaCondicionesEscaleras);
                    String TrabajoEnAlturaCondicionesEscaleras=radioButtonTrabajoEnAlturaCondicionesEscaleras.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaEscaleraSujeta = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaEscaleraSujeta);
                    int idPTrabajoEnAlturaEscaleraSujeta=rg_TrabajoEnAlturaEscaleraSujeta.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaEscaleraSujeta = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaEscaleraSujeta);
                    String TrabajoEnAlturaEscaleraSujeta=radioButtonTrabajoEnAlturaEscaleraSujeta.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaSenalizacionSector = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaSeñalizacionSector);
                    int idPTrabajoEnAlturaSenalizacionSector=rg_TrabajoEnAlturaSenalizacionSector.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaSenalizacionSector = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaSenalizacionSector);
                    String TrabajoEnAlturaSenalizacionSector=radioButtonTrabajoEnAlturaSenalizacionSector.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaOperarioAtado = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaOperarioAtado);
                    int idPTrabajoEnAlturaOperarioAtado=rg_TrabajoEnAlturaOperarioAtado.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaOperarioAtado = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaOperarioAtado);
                    String TrabajoEnAlturaOperarioAtado=radioButtonTrabajoEnAlturaOperarioAtado.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaHerramientasYEquipos = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaHerramientasEquipos);
                    int idPTrabajoEnAlturaHerramientasYEquipos=rg_TrabajoEnAlturaHerramientasYEquipos.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaHerramientasYEquipos = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaHerramientasYEquipos);
                    String TrabajoEnAlturaHerramientasYEquipos=radioButtonTrabajoEnAlturaHerramientasYEquipos.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaDesenergizacionParaTrabajar = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaDesenergizacionParaTrabajar);
                    int idPTrabajoEnAlturaDesenergizacionParaTrabajar=rg_TrabajoEnAlturaDesenergizacionParaTrabajar.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaDesenergizacionParaTrabajar = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaDesenergizacionParaTrabajar);
                    String TrabajoEnAlturaDesenergizacionParaTrabajar=radioButtonTrabajoEnAlturaDesenergizacionParaTrabajar.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaColocacionGuardaCritica = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaColocacionGuardaCritica);
                    int idPTrabajoEnAlturaColocacionGuardaCritica=rg_TrabajoEnAlturaColocacionGuardaCritica.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaColocacionGuardaCritica = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaColocacionGuardaCritica);
                    String TrabajoEnAlturaColocacionGuardaCritica=radioButtonTrabajoEnAlturaColocacionGuardaCritica.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaGuardaCritica = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaGuardaCritica);
                    int idPTrabajoEnAlturaGuardaCritica=rg_TrabajoEnAlturaGuardaCritica.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaGuardaCritica = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaGuardaCritica);
                    String TrabajoEnAlturaGuardaCritica=radioButtonTrabajoEnAlturaGuardaCritica.getText().toString();

                    RadioGroup rg_TrabajoEnAlturaFirmaResponsable = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnAlturaFirmaResponsable);
                    int idPTrabajoEnAlturaFirmaResponsable=rg_TrabajoEnAlturaFirmaResponsable.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnAlturaFirmaResponsable = (RadioButton)getActivity().findViewById(idPTrabajoEnAlturaFirmaResponsable);
                    String TrabajoEnAlturaFirmaResponsable=radioButtonTrabajoEnAlturaFirmaResponsable.getText().toString();

                    //Trabajo en Caliente

                    String txtTrabajoEnCalienteFechaEmision=etTrabajoEnCalienteFecha.getText().toString();
                    String txtTrabajoEnCalienteConfecciono=etTrabajoEnCalienteConfecciono.getText().toString();
                    String txtTrabajoEnCalienteObservaciones=etTrabajoEnCalienteObservaciones.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteIdentificacionSector = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteIdentificacionSector);
                    int idPTrabajoEnCalienteIdentificacionSector=rg_TrabajoEnCalienteIdentificacionSector.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteIdentificacionSector = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteIdentificacionSector);
                    String TrabajoEnCalienteIdentificacionSector=radioButtonTrabajoEnCalienteIdentificacionSector.getText().toString();

                    RadioGroup rg_TrabajoEnCalientePTSTrabajoEnCaliente = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalientePTSTrabajoEnCaliente);
                    int idPTrabajoEnCalientePTSTrabajoEnCaliente=rg_TrabajoEnCalientePTSTrabajoEnCaliente.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalientePTSTrabajoEnCaliente = (RadioButton)getActivity().findViewById(idPTrabajoEnCalientePTSTrabajoEnCaliente);
                    String TrabajoEnCalientePTSTrabajoEnCaliente=radioButtonTrabajoEnCalientePTSTrabajoEnCaliente.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteVigenciaPT = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteVigenciaPT);
                    int idPTrabajoEnCalienteVigenciaPT=rg_TrabajoEnCalienteVigenciaPT.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteVigenciaPT = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteVigenciaPT);
                    String TrabajoEnCalienteVigenciaPT=radioButtonTrabajoEnCalienteVigenciaPT.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteEPPTrabajoEnCaliente = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteEPPTrabajoEnCaliente );
                    int idPTrabajoEnCalienteEPPTrabajoEnCaliente =rg_TrabajoEnCalienteEPPTrabajoEnCaliente.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteEPPTrabajoEnCaliente  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteEPPTrabajoEnCaliente) ;
                    String TrabajoEnCalienteEPPTrabajoEnCaliente =radioButtonTrabajoEnCalienteEPPTrabajoEnCaliente.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteHabilitacionHerramientas = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteHabilitacionHerramientas );
                    int idPTrabajoEnCalienteHabilitacionHerramientas =rg_TrabajoEnCalienteHabilitacionHerramientas.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteHabilitacionHerramientas  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteHabilitacionHerramientas) ;
                    String TrabajoEnCalienteHabilitacionHerramientas =radioButtonTrabajoEnCalienteHabilitacionHerramientas.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteSenalizacionSector = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteSeñalizacionSector );
                    int idPTrabajoEnCalienteSenalizacionSector =rg_TrabajoEnCalienteSenalizacionSector.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteSenalizacionSector  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteSenalizacionSector) ;
                    String TrabajoEnCalienteSenalizacionSector =radioButtonTrabajoEnCalienteSenalizacionSector.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteInflamablesFueraPerimetro = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteInflamablesFueraPerimetro );
                    int idPTrabajoEnCalienteInflamablesFueraPerimetro =rg_TrabajoEnCalienteInflamablesFueraPerimetro.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteInflamablesFueraPerimetro  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteInflamablesFueraPerimetro) ;
                    String TrabajoEnCalienteInflamablesFueraPerimetro =radioButtonTrabajoEnCalienteInflamablesFueraPerimetro.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteCanerias = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteCañerias );
                    int idPTrabajoEnCalienteCanerias =rg_TrabajoEnCalienteCanerias.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteCanerias  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteCanerias) ;
                    String TrabajoEnCalienteCanerias =radioButtonTrabajoEnCalienteCanerias.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteObservadorFuego = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteObservadorFuego );
                    int idPTrabajoEnCalienteObservadorFuego =rg_TrabajoEnCalienteObservadorFuego.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteObservadorFuego  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteObservadorFuego) ;
                    String TrabajoEnCalienteObservadorFuego =radioButtonTrabajoEnCalienteObservadorFuego.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteEquipoExtinsion = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteEquipoExtinsion );
                    int idPTrabajoEnCalienteEquipoExtinsion =rg_TrabajoEnCalienteEquipoExtinsion.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteEquipoExtinsion  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteEquipoExtinsion) ;
                    String TrabajoEnCalienteEquipoExtinsion =radioButtonTrabajoEnCalienteEquipoExtinsion.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteMonitoreo = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteMonitoreo );
                    int idPTrabajoEnCalienteMonitoreo=rg_TrabajoEnCalienteMonitoreo.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteMonitoreo  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteMonitoreo) ;
                    String TrabajoEnCalienteMonitoreo =radioButtonTrabajoEnCalienteMonitoreo.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteEstadoEquipo = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteEstadoEquipo );
                    int idPTrabajoEnCalienteEstadoEquipo=rg_TrabajoEnCalienteEstadoEquipo.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteEstadoEquipo  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteEstadoEquipo) ;
                    String TrabajoEnCalienteEstadoEquipo =radioButtonTrabajoEnCalienteEstadoEquipo.getText().toString();

                    RadioGroup rg_TrabajoEnCalientePurgado = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalientePurgado );
                    int idPTrabajoEnCalientePurgado=rg_TrabajoEnCalientePurgado.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalientePurgado  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalientePurgado) ;
                    String TrabajoEnCalientePurgado =radioButtonTrabajoEnCalientePurgado.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteBloqueosDobles = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteBloqueosDobles );
                    int idPTrabajoEnCalienteBloqueosDobles=rg_TrabajoEnCalienteBloqueosDobles.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteBloqueosDobles  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteBloqueosDobles) ;
                    String TrabajoEnCalienteBloqueosDobles =radioButtonTrabajoEnCalienteBloqueosDobles.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteTrabajoAltaEnergia = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteTrabajoAltaEnergia );
                    int idPTrabajoEnCalienteTrabajoAltaEnergia=rg_TrabajoEnCalienteTrabajoAltaEnergia.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteTrabajoAltaEnergia  = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteTrabajoAltaEnergia) ;
                    String TrabajoEnCalienteTrabajoAltaEnergia =radioButtonTrabajoEnCalienteTrabajoAltaEnergia.getText().toString();

                    RadioGroup rg_TrabajoEnCalienteFirmaResponsable = (RadioGroup)getActivity().findViewById(R.id.TrabajoEnCalienteFirmaResponsable );
                    int idPTrabajoEnCalienteFirmaResponsable=rg_TrabajoEnCalienteFirmaResponsable.getCheckedRadioButtonId();
                    RadioButton radioButtonTrabajoEnCalienteFirmaResponsable = (RadioButton)getActivity().findViewById(idPTrabajoEnCalienteFirmaResponsable) ;
                    String TrabajoEnCalienteFirmaResponsable =radioButtonTrabajoEnCalienteFirmaResponsable.getText().toString();

                    //Apertura Lineas

                    String txtAperturaLineasFechaEmision=etAperturaLineasFecha.getText().toString();
                    String txtAperturaLineasConfecciono=etAperturaLineasConfecciono.getText().toString();
                    String txtAperturaLineasObservaciones=etAperturaLineasObservaciones.getText().toString();

                    RadioGroup rg_AperturaLineasSectorYUbicacion = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasSecorYUbicacion);
                    int idAperturaLineasSectorYUbicacion=rg_AperturaLineasSectorYUbicacion.getCheckedRadioButtonId();
                    RadioButton radioButtonAperturaLineasSectorYUbicacion = (RadioButton)getActivity().findViewById(idAperturaLineasSectorYUbicacion) ;
                    String AperturaLineasSectorYUbicacion =radioButtonAperturaLineasSectorYUbicacion.getText().toString();

                    RadioGroup rg_AperturaLineasEstadoLinea = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasEstadoLinea);
                    int idAperturaLineasEstadoLinea=rg_AperturaLineasEstadoLinea.getCheckedRadioButtonId();
                    RadioButton radioButtonAperturaLineasEstadoLinea = (RadioButton)getActivity().findViewById(idAperturaLineasEstadoLinea) ;
                    String AperturaLineasEstadoLinea =radioButtonAperturaLineasEstadoLinea.getText().toString();

                    RadioGroup rg_AperturaLineasSenalizacion = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasSeñalizacion);
                    int idAperturaLineasSenalizacion=rg_AperturaLineasSenalizacion.getCheckedRadioButtonId();
                    RadioButton radioButtonAperturasSenalizacion = (RadioButton)getActivity().findViewById(idAperturaLineasSenalizacion) ;
                    String AperturaLineasSenalizacion =radioButtonAperturasSenalizacion.getText().toString();

                    RadioGroup rg_AperturaLineasAislacionLineaEquipo = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasAislacionLineaEquipo );
                    int idAperturaLineasAislacionLineaEquipo =rg_AperturaLineasAislacionLineaEquipo .getCheckedRadioButtonId();
                    RadioButton radioButtonAperturasAislacionLineaEquipo  = (RadioButton)getActivity().findViewById(idAperturaLineasAislacionLineaEquipo ) ;
                    String AperturaLineasAislacionLineaEquipo  =radioButtonAperturasAislacionLineaEquipo .getText().toString();

                    RadioGroup rg_AperturaLineasIdentificacionApertura = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasIdentificacionApertura );
                    int idAperturaLineasIdentificacionApertura =rg_AperturaLineasIdentificacionApertura .getCheckedRadioButtonId();
                    RadioButton radioButtonAperturasLineasIdentificacionApertura  = (RadioButton)getActivity().findViewById(idAperturaLineasIdentificacionApertura) ;
                    String AperturaLineasIdentificacionApertura  =radioButtonAperturasLineasIdentificacionApertura.getText().toString();

                    RadioGroup rg_AperturaLineasVerificacionPuntosDificiles = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasVerificacionPuntosDificiles);
                    int idAperturaLineasVerificacionPuntosDificiles =rg_AperturaLineasVerificacionPuntosDificiles .getCheckedRadioButtonId();
                    RadioButton radioButtonAperturasLineasVerificacionPuntosDificiles  = (RadioButton)getActivity().findViewById(idAperturaLineasVerificacionPuntosDificiles) ;
                    String AperturaLineasVerificacionPuntosDificiles  =radioButtonAperturasLineasVerificacionPuntosDificiles.getText().toString();

                    RadioGroup rg_AperturaLineasAperturaLineasSoldadas = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasAperturaLineaSoldada);
                    int idAperturaLineasAperturaLineasSoldadas  =rg_AperturaLineasAperturaLineasSoldadas .getCheckedRadioButtonId();
                    RadioButton radioButtonAperturaLineasAperturaLineasSoldadas   = (RadioButton)getActivity().findViewById(idAperturaLineasAperturaLineasSoldadas ) ;
                    String AperturaLineasAperturaLineasSoldadas   =radioButtonAperturaLineasAperturaLineasSoldadas .getText().toString();

                    RadioGroup rg_AperturaLineasDespresurazacion = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasDespresurizacion);
                    int idAperturaLineasDespresurazacion  =rg_AperturaLineasDespresurazacion.getCheckedRadioButtonId();
                    RadioButton radioButtonAperturaLineasDespresurazacion   = (RadioButton)getActivity().findViewById(idAperturaLineasDespresurazacion) ;
                    String AperturaLineasDespresurazacion   =radioButtonAperturaLineasDespresurazacion.getText().toString();

                    RadioGroup rg_AperturaLineasLimpiezaLineaEquipo = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasLimpiezaLineaEquipo);
                    int idAperturaLineasLimpiezaLineaEquipo  =rg_AperturaLineasLimpiezaLineaEquipo.getCheckedRadioButtonId();
                    RadioButton radioButtonAperturaLineasLimpiezaLineaEquipo   = (RadioButton)getActivity().findViewById(idAperturaLineasLimpiezaLineaEquipo) ;
                    String AperturaLineasLimpiezaLineaEquipo   =radioButtonAperturaLineasLimpiezaLineaEquipo.getText().toString();

                    RadioGroup rg_AperturaLineasLineaEquipo = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasLineaEquipo);
                    int idAperturaLineasLineaEquipo  =rg_AperturaLineasLineaEquipo.getCheckedRadioButtonId();
                    RadioButton radioButtonAperturaLineasLineaEquipo   = (RadioButton)getActivity().findViewById(idAperturaLineasLineaEquipo) ;
                    String AperturaLineasLineaEquipo   =radioButtonAperturaLineasLineaEquipo.getText().toString();

                    RadioGroup rg_AperturaLineasOtrosPeligros = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasOtrosPeligros);
                    int idAperturaLineasOtrosPeligros  =rg_AperturaLineasOtrosPeligros.getCheckedRadioButtonId();
                    RadioButton radioButtonAperturaLineasOtrosPeligros   = (RadioButton)getActivity().findViewById(idAperturaLineasOtrosPeligros) ;
                    String AperturaLineasOtrosPeligros   =radioButtonAperturaLineasOtrosPeligros.getText().toString();

                    RadioGroup rg_AperturaLineasFirmasResponsables = (RadioGroup)getActivity().findViewById(R.id.AperturaLineasFirmasResponsables);
                    int idAperturaLineasFirmasResponsables =rg_AperturaLineasFirmasResponsables.getCheckedRadioButtonId();
                    RadioButton radioButtonAperturaLineasFirmasResponsables  = (RadioButton)getActivity().findViewById(idAperturaLineasFirmasResponsables) ;
                    String AperturaLineasFirmasResponsables =radioButtonAperturaLineasFirmasResponsables.getText().toString();

                    //Hidrolavadora

                    String txtHidrolavadoraFechaEmision=etHidrolavadoraFecha.getText().toString();
                    String txtHidrolavadoraConfecciono=etHidrolavadoraConfecciono.getText().toString();
                    String txtHidrolavadoraObservaciones=etHidrolavadoraObservaciones.getText().toString();

                    RadioGroup rg_HidrolavadoraIdentificacionSector = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraIdentificacionSector);
                    int idHidrolavadoraIdentificacionSector =rg_HidrolavadoraIdentificacionSector.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraIdentificacionSector= (RadioButton)getActivity().findViewById(idHidrolavadoraIdentificacionSector) ;
                    String HidrolavadoraIdentificacionSector =radioButtonHidrolavadoraIdentificacionSector.getText().toString();

                    RadioGroup rg_HidrolavadoraPTSAnalisis = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraPTSAnalisis);
                    int idHidrolavadoraPTSAnalisis =rg_HidrolavadoraPTSAnalisis.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraPTSAnalisis= (RadioButton)getActivity().findViewById(idHidrolavadoraPTSAnalisis) ;
                    String HidrolavadoraPTSAnalisis =radioButtonHidrolavadoraPTSAnalisis.getText().toString();

                    RadioGroup rg_HidrolavadoraVigenciaPT = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraVigenciaPT);
                    int idHidrolavadoraVigenciaPT =rg_HidrolavadoraVigenciaPT.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraVigenciaPT= (RadioButton)getActivity().findViewById(idHidrolavadoraVigenciaPT) ;
                    String HidrolavadoraVigenciaPT =radioButtonHidrolavadoraVigenciaPT.getText().toString();

                    RadioGroup rg_HidrolavadoraOperariosHabilitados = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraOperariosHabilitados);
                    int idHidrolavadoraOperariosHabilitados=rg_HidrolavadoraOperariosHabilitados.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraOperariosHabilitados= (RadioButton)getActivity().findViewById(idHidrolavadoraOperariosHabilitados) ;
                    String HidrolavadoraOperariosHabilitados =radioButtonHidrolavadoraOperariosHabilitados.getText().toString();

                    RadioGroup rg_HidrolavadoraEPPAdecuados = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraEPPAdecuados);
                    int idHidrolavadoraEPPAdecuados=rg_HidrolavadoraEPPAdecuados.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraEPPAdecuados= (RadioButton)getActivity().findViewById(idHidrolavadoraEPPAdecuados) ;
                    String HidrolavadoraEPPAdecuados =radioButtonHidrolavadoraEPPAdecuados.getText().toString();

                    RadioGroup rg_HidrolavadoraHidro = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraHidro);
                    int idHidrolavadoraHidro=rg_HidrolavadoraHidro.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraHidro= (RadioButton)getActivity().findViewById(idHidrolavadoraHidro) ;
                    String HidrolavadoraHidro =radioButtonHidrolavadoraHidro.getText().toString();

                    RadioGroup rg_HidrolavadoraValladoPerimetral = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraValladoPerimetral);
                    int idHidrolavadoraValladoPerimetral=rg_HidrolavadoraValladoPerimetral.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraValladoPerimetral= (RadioButton)getActivity().findViewById(idHidrolavadoraValladoPerimetral) ;
                    String HidrolavadoraValladoPerimetral =radioButtonHidrolavadoraValladoPerimetral.getText().toString();

                    RadioGroup rg_HidrolavadoraColocacionMangueras = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraColocacionMangueras);
                    int idHidrolavadoraColocacionMangueras=rg_HidrolavadoraColocacionMangueras.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraColocacionMangueras= (RadioButton)getActivity().findViewById(idHidrolavadoraColocacionMangueras) ;
                    String HidrolavadoraColocacionMangueras =radioButtonHidrolavadoraColocacionMangueras.getText().toString();

                    RadioGroup rg_HidrolavadoraVigiaPresente = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraVigiaPresente);
                    int idHidrolavadoraVigiaPresente=rg_HidrolavadoraVigiaPresente.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraVigiaPresente= (RadioButton)getActivity().findViewById(idHidrolavadoraVigiaPresente) ;
                    String HidrolavadoraVigiaPresente =radioButtonHidrolavadoraVigiaPresente.getText().toString();

                    RadioGroup rg_HidrolavadoraVigia = (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraVigia);
                    int idHidrolavadoraVigia=rg_HidrolavadoraVigia.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraVigia= (RadioButton)getActivity().findViewById(idHidrolavadoraVigia) ;
                    String HidrolavadoraVigia =radioButtonHidrolavadoraVigia.getText().toString();

                    RadioGroup rg_HidrolavadoraCheckListAdicionales= (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraCheckListAdicionales);
                    int idHidrolavadoraCheckListAdicionales=rg_HidrolavadoraCheckListAdicionales.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraCheckListAdicionales= (RadioButton)getActivity().findViewById(idHidrolavadoraCheckListAdicionales) ;
                    String HidrolavadoraCheckListAdicionales =radioButtonHidrolavadoraCheckListAdicionales.getText().toString();

                    RadioGroup rg_HidrolavadoraHerramientasEquipos= (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraHerramientasEquipos);
                    int idHidrolavadoraHerramientasEquipos=rg_HidrolavadoraHerramientasEquipos.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraHerramientasEquipos= (RadioButton)getActivity().findViewById(idHidrolavadoraHerramientasEquipos) ;
                    String HidrolavadoraHerramientasEquipos =radioButtonHidrolavadoraHerramientasEquipos.getText().toString();

                    RadioGroup rg_HidrolavadoraDesenergizacion= (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraDesenergizacion);
                    int idHidrolavadoraDesenergizacion=rg_HidrolavadoraDesenergizacion.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraDesenergizacion= (RadioButton)getActivity().findViewById(idHidrolavadoraDesenergizacion) ;
                    String HidrolavadoraDesenergizacion =radioButtonHidrolavadoraDesenergizacion.getText().toString();

                    RadioGroup rg_HidrolavadoraTemperaturaMaxima= (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraTemperaturaMaxima);
                    int idHidrolavadoraTemperaturaMaxima=rg_HidrolavadoraTemperaturaMaxima.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraTemperaturaMaxima= (RadioButton)getActivity().findViewById(idHidrolavadoraTemperaturaMaxima) ;
                    String HidrolavadoraTemperaturaMaxima =radioButtonHidrolavadoraTemperaturaMaxima.getText().toString();

                    RadioGroup rg_HidrolavadoraProteccionEquiposElectricos= (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraProteccionEquiposElectricos);
                    int idHidrolavadoraProteccionEquiposElectricos=rg_HidrolavadoraProteccionEquiposElectricos.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraProteccionEquiposElectricos= (RadioButton)getActivity().findViewById(idHidrolavadoraProteccionEquiposElectricos) ;
                    String HidrolavadoraProteccionEquiposElectricos =radioButtonHidrolavadoraProteccionEquiposElectricos.getText().toString();

                    RadioGroup rg_HidrolavadoraFirmaResponsable= (RadioGroup)getActivity().findViewById(R.id.HidrolavadoraFirmaResponsable);
                    int idHidrolavadoraFirmaResponsable=rg_HidrolavadoraFirmaResponsable.getCheckedRadioButtonId();
                    RadioButton radioButtonHidrolavadoraFirmaResponsable= (RadioButton)getActivity().findViewById(idHidrolavadoraFirmaResponsable) ;
                    String HidrolavadoraFirmaResponsable =radioButtonHidrolavadoraFirmaResponsable.getText().toString();

                    //iterar por imagesUris y concatenar el filePath
                    String imagenPTS = "", imagenTarjetasRojas = "", imagenEspacioConfinado = "", imagenTrabajoEnAltura = "", imagenAperturaLinea = "", imagenTrabajoEnCaliente = "", imagenHidrolavadora = "";
                    for(Uri currentUri : imagenPTSUris) {
                        imagenPTS += FilePath.getPath(getContext(), currentUri) +":" ;
                    }
                    for(Uri currentUri : imagenTarjetasRojasUris) {
                        imagenTarjetasRojas += FilePath.getPath(getContext(), currentUri) +":" ;
                    }
                    for(Uri currentUri : imagenEspacioConfinadoUris) {
                        imagenEspacioConfinado += FilePath.getPath(getContext(), currentUri) +":" ;
                    }
                    for(Uri currentUri : imagenTrabajoEnAlturaUris) {
                        imagenTrabajoEnAltura += FilePath.getPath(getContext(), currentUri) +":" ;
                    }
                    for(Uri currentUri : imagenAperturaLineaUris) {
                        imagenAperturaLinea += FilePath.getPath(getContext(), currentUri) +":" ;
                    }
                    for(Uri currentUri : imagenTrabajoEnCalienteUris) {
                        imagenTrabajoEnCaliente += FilePath.getPath(getContext(), currentUri) +":" ;
                    }
                    for(Uri currentUri : imagenHidrolavadoraUris) {
                        imagenHidrolavadora += FilePath.getPath(getContext(), currentUri) +":" ;
                    }

                    boolean resultado=new ControladorTopTen(getContext()).Insert(txtPTSFechaEmision, txtPTSConfecciono,
                            txtPTSObservaciones, core1, core2, core3, core4, core5, core6, core7, core8,
                            core9, core10,/*PTSValidezPermiso,PTSFirmasResponsables,PTSSectorTrabajo,PTSListadoPersonal,
                            PTSDescripcionTrabajo,PTSInspeccionSector,PTSRiesgosFisicos,PTSRiesgosQuimicos,PTSRiesgosAmbientales,
                            PTSEPPAdicional,PTSEntrenamiento,PTSCierre,*/ txtTarjetasRojasFechaEmison, txtTarjetasRojasConfecciono, txtTarjetasRojasObservaciones,
                            TarjetasRojasCantTarj, TarjetasRojasEquipoFuera, TarjetasRojasRazon,
                            TarjetasRojasTarea, TarjetasRojasDpto, TarjetasRojasAcepto,
                            TarjetasRojasApellido, TarjetasRojasCancelacion, TarjetasRojasRepDeLaInst,
                            TarjetasRojasAislacionFisica, TarjetasRojasRepTrabajo, TarjetasRojasTarjetasRetiradas,
                            TarjetasRojasNroTarjeta, TarjetasRojasUbicacion, TarjetasRojasColocadoPor,
                            TarjetasRojasFechaRetiro, TarjetasRojasRetirada, TarjetasRojasConciliado, txtEspacioConfinadoFechaEmision,
                            txtEspacioConfinadoConfecciono, txtEspacioConfinadoObservaciones, EspacioConfinadoIdentificacion, EspacioConfinadoPermisoAEC,
                            EspacioConfinadoVigenciaDelPermiso, EspacioConfinadoIngreso, EspacioConfinadoVigiaExterno, EspacioConfinadoVigia,
                            EspacioConfinadoPersonalInvolucrado, EspacioConfinadoCheckListAdicionales, EspacioConfinadoAislacion,
                            EspacioConfinadoEquiposElectricos, EspacioConfinadoLimpiezaVentilacion, EspacioConfinadoHerramientasEquipos,
                            EspacioConfinadoMonitoreo, EspacioConfinadoEntrada, EspacioConfinadoRescate, EspacioConfinadoFirmaResponsable,
                            txtTrabajoEnAlturaFechaEmision, txtTrabajoEnAlturaConfecciono, txtTrabajoEnAlturaObservaciones,
                            TrabajoEnAlturaIdentificacionSector, TrabajoEnAlturaPTSCaidas, TrabajoEnAlturaVigenciaPT, TrabajoEnAlturaEPPCaidas,
                            TrabajoEnAlturaHabilitacionEPP, TrabajoEnAlturaAndamioHabilitado, TrabajoEnAlturaBarandasCondiciones,
                            TrabajoEnAlturaCondicionesEscaleras, TrabajoEnAlturaEscaleraSujeta, TrabajoEnAlturaSenalizacionSector,
                            TrabajoEnAlturaOperarioAtado, TrabajoEnAlturaHerramientasYEquipos, TrabajoEnAlturaDesenergizacionParaTrabajar,
                            TrabajoEnAlturaColocacionGuardaCritica, TrabajoEnAlturaGuardaCritica, TrabajoEnAlturaFirmaResponsable,
                            txtTrabajoEnCalienteFechaEmision, txtTrabajoEnCalienteConfecciono, txtTrabajoEnCalienteObservaciones,
                            TrabajoEnCalienteIdentificacionSector, TrabajoEnCalientePTSTrabajoEnCaliente, TrabajoEnCalienteVigenciaPT,
                            TrabajoEnCalienteEPPTrabajoEnCaliente, TrabajoEnCalienteHabilitacionHerramientas, TrabajoEnCalienteSenalizacionSector,
                            TrabajoEnCalienteInflamablesFueraPerimetro, TrabajoEnCalienteCanerias, TrabajoEnCalienteObservadorFuego,
                            TrabajoEnCalienteEquipoExtinsion, TrabajoEnCalienteMonitoreo, TrabajoEnCalienteEstadoEquipo,
                            TrabajoEnCalientePurgado, TrabajoEnCalienteBloqueosDobles, TrabajoEnCalienteTrabajoAltaEnergia, TrabajoEnCalienteFirmaResponsable,
                            txtAperturaLineasFechaEmision, txtAperturaLineasConfecciono, txtAperturaLineasObservaciones,
                            AperturaLineasSectorYUbicacion, AperturaLineasEstadoLinea, AperturaLineasSenalizacion,
                            AperturaLineasAislacionLineaEquipo, AperturaLineasIdentificacionApertura, AperturaLineasVerificacionPuntosDificiles,
                            AperturaLineasAperturaLineasSoldadas, AperturaLineasDespresurazacion, AperturaLineasLimpiezaLineaEquipo,
                            AperturaLineasLineaEquipo, AperturaLineasOtrosPeligros, AperturaLineasFirmasResponsables,
                            txtHidrolavadoraFechaEmision, txtHidrolavadoraConfecciono, txtHidrolavadoraObservaciones,
                            HidrolavadoraIdentificacionSector,HidrolavadoraPTSAnalisis, HidrolavadoraVigenciaPT,
                            HidrolavadoraOperariosHabilitados, HidrolavadoraEPPAdecuados, HidrolavadoraHidro,
                            HidrolavadoraValladoPerimetral,HidrolavadoraColocacionMangueras,HidrolavadoraVigiaPresente,
                            HidrolavadoraVigia,HidrolavadoraCheckListAdicionales,HidrolavadoraHerramientasEquipos,HidrolavadoraDesenergizacion,
                            HidrolavadoraTemperaturaMaxima,HidrolavadoraProteccionEquiposElectricos,HidrolavadoraFirmaResponsable,
                            spinnerSectorSeleccion, spinnerObservadorUnoSeleccion, spinnerObservadorDosSeleccion, imagenPTS, imagenTarjetasRojas, imagenEspacioConfinado,
                            imagenTrabajoEnAltura, imagenAperturaLinea, imagenTrabajoEnCaliente, imagenHidrolavadora, spinnerSiteSeleccion, usuario);

                    if(resultado){
                        Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                        new Syncronizer(getActivity(),"setTopTen").execute();
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
        if(CALENDAR_CODE==1){
            etPTSFecha.setText(dia + "-" + mes + "-" +  Integer.toString(year));
        }else if(CALENDAR_CODE==2){
            etMasterTarjetasFecha.setText(dia + "-" + mes + "-" +  Integer.toString(year));
        }else if(CALENDAR_CODE==3){
            etEspacioConfinadoFecha.setText(dia + "-" + mes + "-" +  Integer.toString(year));
        }else if(CALENDAR_CODE==4){
            etTrabajoEnAlturaFecha.setText(dia + "-" + mes + "-" +  Integer.toString(year));
        }else if(CALENDAR_CODE==5){
            etTrabajoEnCalienteFecha.setText(dia + "-" + mes + "-" +  Integer.toString(year));
        }else if(CALENDAR_CODE==6){
            etAperturaLineasFecha.setText(dia + "-" + mes + "-" +  Integer.toString(year));
        }else if(CALENDAR_CODE==7){
            etHidrolavadoraFecha.setText(dia + "-" + mes + "-" +  Integer.toString(year));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Imagen PTS
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEPTS) {
            if (resultCode == Activity.RESULT_OK) {
                //si se toma foto desde la camara
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    imagenPTSUris.add(imageUri);
                    fotoImageSwitcherPTS.setImageURI(imagenPTSUris.get(imagenPTSUris.size()-1));
                    positionPTS = imagenPTSUris.size()-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CHOOSE_IMAGEACTIVITY_REQUEST_CODEPTS && resultCode == Activity.RESULT_OK && data != null) {
            //si se elige foto desde el celu
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imagenPTSUris.add(imageUri);
                }
                fotoImageSwitcherPTS.setImageURI(imagenPTSUris.get(imagenPTSUris.size()-1));
                positionPTS = imagenPTSUris.size()-1;
            } else {
                Uri imageUri = data.getData();
                imagenPTSUris.add(imageUri);
                //muestro ultima imagen
                fotoImageSwitcherPTS.setImageURI(imagenPTSUris.get(imagenPTSUris.size()-1));
                positionPTS = imagenPTSUris.size()-1;
            }
        }

        //Imagen MasterTarjetasRojas
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEMASTERTARJETASROJAS) {
            if (resultCode == Activity.RESULT_OK) {
                //si se toma foto desde la camara
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    imagenTarjetasRojasUris.add(imageUri);
                    fotoImageSwitcherTarjetasRojas.setImageURI(imagenTarjetasRojasUris.get(imagenTarjetasRojasUris.size()-1));
                    positionTarjetasRojas = imagenTarjetasRojasUris.size()-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CHOOSE_IMAGEACTIVITY_REQUEST_CODEMASTERTARJETASROJAS && resultCode == Activity.RESULT_OK && data != null) {
            //si se elige foto desde el celu
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imagenTarjetasRojasUris.add(imageUri);
                }
                fotoImageSwitcherTarjetasRojas.setImageURI(imagenTarjetasRojasUris.get(imagenTarjetasRojasUris.size()-1));
                positionTarjetasRojas = imagenTarjetasRojasUris.size()-1;
            } else {
                Uri imageUri = data.getData();
                imagenTarjetasRojasUris.add(imageUri);
                //muestro ultima imagen
                fotoImageSwitcherTarjetasRojas.setImageURI(imagenTarjetasRojasUris.get(imagenTarjetasRojasUris.size()-1));
                positionTarjetasRojas = imagenTarjetasRojasUris.size()-1;
            }
        }

        //Imagen EspacioConfinado
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEACCESOESPACIOCONFINADO) {
            if (resultCode == Activity.RESULT_OK) {
                //si se toma foto desde la camara
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    imagenEspacioConfinadoUris.add(imageUri);
                    fotoImageSwitcherEspacioConfinado.setImageURI(imagenEspacioConfinadoUris.get(imagenEspacioConfinadoUris.size()-1));
                    positionEspacioConfinado = imagenEspacioConfinadoUris.size()-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CHOOSE_IMAGEACTIVITY_REQUEST_CODEACCESOESPACIOCONFINADO && resultCode == Activity.RESULT_OK && data != null) {
            //si se elige foto desde el celu
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imagenEspacioConfinadoUris.add(imageUri);
                }
                fotoImageSwitcherEspacioConfinado.setImageURI(imagenEspacioConfinadoUris.get(imagenEspacioConfinadoUris.size()-1));
                positionEspacioConfinado = imagenEspacioConfinadoUris.size()-1;
            } else {
                Uri imageUri = data.getData();
                imagenEspacioConfinadoUris.add(imageUri);
                //muestro ultima imagen
                fotoImageSwitcherEspacioConfinado.setImageURI(imagenEspacioConfinadoUris.get(imagenEspacioConfinadoUris.size()-1));
                positionEspacioConfinado = imagenEspacioConfinadoUris.size()-1;
            }
        }

        //Imagen Trabajo Altura
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODETRABAJOENALTURA) {
            if (resultCode == Activity.RESULT_OK) {
                //si se toma foto desde la camara
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    imagenTrabajoEnAlturaUris.add(imageUri);
                    fotoImageSwitcherTrabajoEnAltura.setImageURI(imagenTrabajoEnAlturaUris.get(imagenTrabajoEnAlturaUris.size()-1));
                    positionTrabajoEnAltura = imagenTrabajoEnAlturaUris.size()-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CHOOSE_IMAGEACTIVITY_REQUEST_CODETRABAJOENALTURA && resultCode == Activity.RESULT_OK && data != null) {
            //si se elige foto desde el celu
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imagenTrabajoEnAlturaUris.add(imageUri);
                }
                fotoImageSwitcherTrabajoEnAltura.setImageURI(imagenTrabajoEnAlturaUris.get(imagenTrabajoEnAlturaUris.size()-1));
                positionTrabajoEnAltura = imagenTrabajoEnAlturaUris.size()-1;
            } else {
                Uri imageUri = data.getData();
                imagenTrabajoEnAlturaUris.add(imageUri);
                //muestro ultima imagen
                fotoImageSwitcherTrabajoEnAltura.setImageURI(imagenTrabajoEnAlturaUris.get(imagenTrabajoEnAlturaUris.size()-1));
                positionTrabajoEnAltura = imagenTrabajoEnAlturaUris.size()-1;
            }
        }

        //Imagen Trabajo en Caliente
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODETRABAJOENCALIENTE) {
            if (resultCode == Activity.RESULT_OK) {
                //si se toma foto desde la camara
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    imagenTrabajoEnCalienteUris.add(imageUri);
                    fotoImageSwitcherTrabajoEnCaliente.setImageURI(imagenTrabajoEnCalienteUris.get(imagenTrabajoEnCalienteUris.size()-1));
                    positionTrabajoEnCaliente = imagenTrabajoEnCalienteUris.size()-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CHOOSE_IMAGEACTIVITY_REQUEST_CODETRABAJOENCALIENTE && resultCode == Activity.RESULT_OK && data != null) {
            //si se elige foto desde el celu
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imagenTrabajoEnCalienteUris.add(imageUri);
                }
                fotoImageSwitcherTrabajoEnCaliente.setImageURI(imagenTrabajoEnCalienteUris.get(imagenTrabajoEnCalienteUris.size()-1));
                positionTrabajoEnCaliente = imagenTrabajoEnCalienteUris.size()-1;
            } else {
                Uri imageUri = data.getData();
                imagenTrabajoEnCalienteUris.add(imageUri);
                //muestro ultima imagen
                fotoImageSwitcherTrabajoEnCaliente.setImageURI(imagenTrabajoEnCalienteUris.get(imagenTrabajoEnCalienteUris.size()-1));
                positionTrabajoEnCaliente = imagenTrabajoEnCalienteUris.size()-1;
            }
        }

        //Imagen Apertura Linea Equipo
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEAPERTURALINEAEQUIPOS) {
            if (resultCode == Activity.RESULT_OK) {
                //si se toma foto desde la camara
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    imagenAperturaLineaUris.add(imageUri);
                    fotoImageSwitcherAperturaLinea.setImageURI(imagenAperturaLineaUris.get(imagenAperturaLineaUris.size()-1));
                    positionAperturaLinea = imagenAperturaLineaUris.size()-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CHOOSE_IMAGEACTIVITY_REQUEST_CODEAPERTURALINEAEQUIPOS && resultCode == Activity.RESULT_OK && data != null) {
            //si se elige foto desde el celu
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imagenAperturaLineaUris.add(imageUri);
                }
                fotoImageSwitcherAperturaLinea.setImageURI(imagenAperturaLineaUris.get(imagenAperturaLineaUris.size()-1));
                positionAperturaLinea = imagenAperturaLineaUris.size()-1;
            } else {
                Uri imageUri = data.getData();
                imagenAperturaLineaUris.add(imageUri);
                //muestro ultima imagen
                fotoImageSwitcherAperturaLinea.setImageURI(imagenAperturaLineaUris.get(imagenAperturaLineaUris.size()-1));
                positionAperturaLinea = imagenAperturaLineaUris.size()-1;
            }
        }

        //Hidrolavadora
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODEHIDROLAVADORA) {
            if (resultCode == Activity.RESULT_OK) {
                //si se toma foto desde la camara
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    imagenHidrolavadoraUris.add(imageUri);
                    fotoImageSwitcherHidrolavadora.setImageURI(imagenHidrolavadoraUris.get(imagenHidrolavadoraUris.size()-1));
                    positionHidrolavadora = imagenHidrolavadoraUris.size()-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CHOOSE_IMAGEACTIVITY_REQUEST_CODEHIDROLAVADORA && resultCode == Activity.RESULT_OK && data != null) {
            //si se elige foto desde el celu
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imagenHidrolavadoraUris.add(imageUri);
                }
                fotoImageSwitcherHidrolavadora.setImageURI(imagenHidrolavadoraUris.get(imagenHidrolavadoraUris.size()-1));
                positionHidrolavadora = imagenHidrolavadoraUris.size()-1;
            } else {
                Uri imageUri = data.getData();
                imagenHidrolavadoraUris.add(imageUri);
                //muestro ultima imagen
                fotoImageSwitcherHidrolavadora.setImageURI(imagenHidrolavadoraUris.get(imagenHidrolavadoraUris.size()-1));
                positionHidrolavadora = imagenHidrolavadoraUris.size()-1;
            }
        }
    }
    public interface OnFragmentInteractionListener {
    }
}