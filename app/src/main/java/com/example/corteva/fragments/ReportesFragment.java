package com.example.corteva.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.corteva.ControladorClasificacion;
import com.example.corteva.ControladorReportes;
import com.example.corteva.ControladorResponsables;
import com.example.corteva.ControladorSectores;
import com.example.corteva.ControladorSites;
import com.example.corteva.DatePickerFragment;
import com.example.corteva.FilePath;
import com.example.corteva.R;
import com.example.corteva.SaveSharedPreference;
import com.example.corteva.Syncronizer;
import com.example.corteva.TimePickerFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReportesFragment extends Fragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    View view;
    Spinner spinnerResponsable, spinnerSector, spinnerResponsableReportesOpciones, spinnerSeguimientoReportesOpciones, spinnerPotencial, spinnerSite, spinnerClasificacion;
    EditText txtFecha, txtHora, txtLugar, txtNombreOperario, txtFuncionOperario, txtDescribirOcurrido, txtLesion, txtParteCuerpoAfectada, txtAccionReportesOpciones, txtEmpresa;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1212;
    private static final int CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE = 12131;
    ImageSwitcher switcherImage;
    private ArrayList<Uri> imageUris;
    int position = 0;
    Uri imageUri;
    LinearLayout imageSwitcherContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_reportes, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");

        imageSwitcherContainer = getActivity().findViewById(R.id.imageSwitcher);
        imageUris = new ArrayList<Uri>();

        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        txtFecha = getActivity().findViewById(R.id.etFechaIncidente);
        txtFecha.setText(today);
        String hora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        txtHora = getActivity().findViewById(R.id.etHoraIncidente);
        txtHora.setText(hora);
        txtLugar=getActivity().findViewById(R.id.etLugar);
        txtNombreOperario=getActivity().findViewById(R.id.etNombreOperario);
        txtFuncionOperario=getActivity().findViewById(R.id.etFuncionOperario);
        txtDescribirOcurrido=getActivity().findViewById(R.id.etDescribirOcurrido);
        txtLesion=getActivity().findViewById(R.id.etLesion);
        txtParteCuerpoAfectada=getActivity().findViewById(R.id.etParteCuerpoAfectada);
        txtEmpresa = getActivity().findViewById(R.id.etEmpresa);

        spinnerResponsable=getActivity().findViewById(R.id.spinnerResponsable);
        spinnerSector=getActivity().findViewById(R.id.spinnerSector);
        spinnerPotencial=getActivity().findViewById(R.id.spinnerPotencial);
        spinnerSite=getActivity().findViewById(R.id.spinnerSiteReportes);
        spinnerClasificacion=getActivity().findViewById(R.id.spinnerClasificacion);
        final Spinner spinnerSectorResponsable=getActivity().findViewById(R.id.spinnerSectorResponsable);

        ArrayList<String> opciones = new ControladorClasificacion(getContext()).getClasificacion();
        ArrayAdapter<String>adapterClasificacion=new ArrayAdapter<>(getActivity(),R.layout.spinner_item_reportes,opciones);
        spinnerClasificacion.setAdapter(adapterClasificacion);

        //Cuando se seleccion 'Heridas y Enfermedades' se muestran los campos 'tipo lesion' y 'parte del cuerpo'
        spinnerClasificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String clasificacion= spinnerClasificacion.getSelectedItem().toString();
                if (clasificacion.contains("Heridas")){
                    txtParteCuerpoAfectada.setVisibility(View.VISIBLE);
                    txtLesion.setVisibility(View.VISIBLE);
                }else{
                    txtParteCuerpoAfectada.setVisibility(View.GONE);
                    txtLesion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String [] potenical=new String[]{"Bajo","Medio","Alto"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes, potenical);
        spinnerPotencial.setAdapter(adapter);

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

                ArrayAdapter<String>adapter2=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaSectores);
                spinnerSectorResponsable.setAdapter(adapter2);
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
                ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaResponsables);
                spinnerResponsable.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Si se tildan las opciones Contratista habitual o Contratista no habitual se debe mostrar el campo empresa
        final CheckBox checkHabitual, checkNoHabitual;
        checkHabitual = getActivity().findViewById(R.id.checkbox4);
        checkNoHabitual = getActivity().findViewById(R.id.checkbox5);

        checkHabitual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked || checkNoHabitual.isChecked()){
                   txtEmpresa.setVisibility(View.VISIBLE);
                }else{
                    txtEmpresa.setVisibility(View.GONE);
                }
            }
        });

        checkNoHabitual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked || checkHabitual.isChecked()){
                    txtEmpresa.setVisibility(View.VISIBLE);
                }else{
                    txtEmpresa.setVisibility(View.GONE);
                }
            }
        });

        //CALENDARIO
        txtFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se muestre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(ReportesFragment.this, 0);
                        datePicker.show(getFragmentManager(), "date picker");
                        break;
                }
                return false;
            }
        });
        txtHora.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se mustre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        DialogFragment time= new TimePickerFragment();
                        time.setTargetFragment(ReportesFragment.this, 0);
                        time.show(getFragmentManager(), "time picker");

                        break;
                }

                return false;
            }
        });

        //Para las acciones se muestran todos los responsables
        final ArrayList<String> listaResponsableOpciones = new ControladorResponsables(getContext()).getResponsables();

        final LinearLayout contenedor=(LinearLayout)view.findViewById(R.id.acciones);

        //BOTON AGREGAR ACCIONES
        Button botonAgregar=view.findViewById(R.id.buttonAgregar);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View child = inflater.inflate(R.layout.fragment_reportes_opciones, null);
                contenedor.addView(child);

                spinnerResponsableReportesOpciones=child.findViewById(R.id.spinnerResponsableReportesOpciones);
                spinnerSeguimientoReportesOpciones=child.findViewById(R.id.spinnerSeguimientoReportesOpciones);
                txtAccionReportesOpciones=child.findViewById(R.id.etAccionReportesOpciones);

                ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaResponsableOpciones);
                spinnerResponsableReportesOpciones.setAdapter(adapter);

                spinnerSeguimientoReportesOpciones.setAdapter(adapter);
            }
        });

        //BOTON ELEGIR IMAGEN DESDE GALERIA
        final Button botonElegirFoto=getActivity().findViewById(R.id.buttonElegirFoto);
        botonElegirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainer.getVisibility() == v.GONE){
                    imageSwitcherContainer.setVisibility(v.VISIBLE);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione imagen"), CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        //BOTON FOTO DESDE CAMARA
        final Button botonFoto=getActivity().findViewById(R.id.buttonFoto);
        botonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageSwitcherContainer.getVisibility() == v.GONE){
                    imageSwitcherContainer.setVisibility(v.VISIBLE);
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
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        switcherImage = getActivity().findViewById(R.id.fotoImageSwitcher);
        switcherImage.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView viewImage = new ImageView(getContext());
                return viewImage;
            }
        });

        Button anterior = getActivity().findViewById(R.id.botonAnteriorImageSwitcher);
        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position>0){
                    position--;
                    switcherImage.setImageURI(imageUris.get(position));
                }else {
                    Toast.makeText(getActivity(),"No hay imágen anterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button siguiente = getActivity().findViewById(R.id.botonSiguienteImageSwitcher);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position<imageUris.size() - 1){
                    position++;
                    switcherImage.setImageURI(imageUris.get(position));
                }else{
                    Toast.makeText(getActivity(),"No hay imágen posterior.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button botonBorrar = getActivity().findViewById(R.id.eliminarFoto);
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUris.size()>0){
                    String imageTag = imageUris.get(position).getPath();
                    File fileTag = new File(imageTag);
                    if (fileTag.exists()) {
                        fileTag.delete();
                    }
                    //eliminar de imageUris
                    imageUris.remove(position);
                    if(position>0){
                        position--;
                        switcherImage.setImageURI(imageUris.get(position));
                    }else{
                        if(imageUris.size()>0) {
                            position++;
                            switcherImage.setImageURI(imageUris.get(position));
                        }else{
                            position = 0;
                            switcherImage.setImageURI(null);
                        }
                    }
                    Toast.makeText(getActivity(), "Imagen borrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTON GUARDAR
        Button boton=getActivity().findViewById(R.id.buttonGuardarReportes);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String getLugar=txtLugar.getText().toString();
                    String getNombreOperario=txtNombreOperario.getText().toString();
                    String getFuncionOperario=txtFuncionOperario.getText().toString();
                    String getDescribirOcurrido=txtDescribirOcurrido.getText().toString();
                    String getLesion=txtLesion.getText().toString();
                    String getParteCuerpoAfectada=txtParteCuerpoAfectada.getText().toString();
                    String getEmpresa = txtEmpresa.getText().toString();
                    String fechahoraincidente = txtFecha.getText().toString() + " " + txtHora.getText().toString();
                    String spinnerResponsableSeleccion = "";
                    if(spinnerResponsable.getSelectedItem()!=null) {
                        spinnerResponsableSeleccion = spinnerResponsable.getSelectedItem().toString();
                    }
                    String spinnerSectorSeleccion= spinnerSector.getSelectedItem().toString();
                    String spinnerPotencialSeleccion=spinnerPotencial.getSelectedItem().toString();
                    String spinnerSiteSeleccion=spinnerSite.getSelectedItem().toString();
                    String spinnerClasificacionSeleccion=spinnerClasificacion.getSelectedItem().toString();

                    CheckBox[] checkBoxes;
                    Integer[] checkBoxesID={R.id.checkbox1, R.id.checkbox2, R.id.checkbox3, R.id.checkbox4, R.id.checkbox5, R.id.checkbox6};

                    checkBoxes = new CheckBox[checkBoxesID.length];
                    String personalInvolucrado="";
                    for(int i=0; i< checkBoxesID.length; i++){
                        checkBoxes[i] = getActivity().findViewById(checkBoxesID[i]);
                        if(checkBoxes[i].isChecked()){
                            personalInvolucrado +=checkBoxes[i].getText().toString()+"/";
                        }
                    }

                    String AccionesReportesOpciones="";
                    for(int z=0; z<contenedor.getChildCount();z++) {
                        if (contenedor.getChildAt(z) instanceof LinearLayout) {
                            LinearLayout linearLayout = (LinearLayout) contenedor.getChildAt(z);
                            int x = 0;
                            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                                if (linearLayout.getChildAt(i) instanceof EditText) {
                                    EditText accionText = (EditText) linearLayout.getChildAt(i);
                                    AccionesReportesOpciones += accionText.getText().toString() + "/*/";
                                } else if (linearLayout.getChildAt(i) instanceof Spinner && x == 0) {
                                    x = 1;
                                    Spinner responsableText = (Spinner) linearLayout.getChildAt(i);
                                    AccionesReportesOpciones += responsableText.getSelectedItem().toString() + "/*/";
                                } else if (linearLayout.getChildAt(i) instanceof Spinner && x == 1) {
                                    x = 0;
                                    Spinner seguimientoText = (Spinner) linearLayout.getChildAt(i);
                                    AccionesReportesOpciones += seguimientoText.getSelectedItem().toString() + "/_/";
                                }
                            }
                        }
                    }
                    //iterar por imagesUris y concatenar el filePath
                    String filePath = "";
                    for(Uri currentUri : imageUris) {
                        filePath += FilePath.getPath(getContext(), currentUri) +":" ;
                    }
                    boolean resultado=new ControladorReportes(getContext()).Insert(getLugar,getNombreOperario,getFuncionOperario,
                            getDescribirOcurrido,getLesion,getParteCuerpoAfectada, personalInvolucrado,
                            spinnerResponsableSeleccion, spinnerSectorSeleccion, filePath, AccionesReportesOpciones, spinnerPotencialSeleccion,
                            spinnerSiteSeleccion, spinnerClasificacionSeleccion, getEmpresa, usuario, fechahoraincidente);
                    if(resultado){
                        Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                        new Syncronizer(getActivity(),"setReportes").execute();
                    }else{
                        Toast.makeText(getActivity(),"No se guardó correctamente.",Toast.LENGTH_SHORT).show();
                    }
                    getActivity().onBackPressed();

                }catch(Exception e){
                    Toast.makeText(getActivity(),"Error al guardar los datos. " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //si se toma foto desde la camara
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    imageUris.add(imageUri);
                    switcherImage.setImageURI(imageUris.get(imageUris.size()-1));
                    position = imageUris.size()-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //si se eligen fotos desde el celu
        if (requestCode == CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(imageUri);
                }
                switcherImage.setImageURI(imageUris.get(imageUris.size()-1));
                position = imageUris.size()-1;
            } else {
                Uri imageUri = data.getData();
                imageUris.add(imageUri);
                //muestro ultima imagen
                switcherImage.setImageURI(imageUris.get(imageUris.size()-1));
                position = imageUris.size()-1;
            }
        }
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
        txtFecha.setText(Integer.toString(dayOfMonth) + "-" + mes + "-" +  dia);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        txtHora.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + ":00");
    }

    public interface OnFragmentInteractionListener {
    }

}
