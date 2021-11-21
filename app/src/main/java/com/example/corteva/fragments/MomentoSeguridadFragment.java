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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.corteva.ControladorGrupos;
import com.example.corteva.ControladorResponsables;
import com.example.corteva.ControladorSectores;
import com.example.corteva.ControladorSeguridad;
import com.example.corteva.ControladorSites;
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


public class MomentoSeguridadFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    Spinner spinnerResponsable, spinnerSectorResponsable, spinnerSector, spinnerSite, spinnerTipo, spinnerGrupo;
    TextView tema, comentario;
    EditText txtFecha;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE=1234;
    private static final int CHOOSE_FILE_ACTIVITY_REQUEST_CODE = 12345;
    private static final int CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE = 12341;
    ImageSwitcher switcherImage;
    private ArrayList<Uri> imageUris;
    int position = 0;
    Uri imageUri;
    LinearLayout imageSwitcherContainer;
    String filePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_momento_seguridad, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");

        imageSwitcherContainer = getActivity().findViewById(R.id.imageSwitcher);
        imageUris = new ArrayList<Uri>();

        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        txtFecha = getActivity().findViewById(R.id.etFecha);
        txtFecha.setText(today);

        spinnerSite=getActivity().findViewById(R.id.spinnerSiteMomentosSeguridad);
        spinnerSectorResponsable=getActivity().findViewById(R.id.spinnerSectorResponsable);
        spinnerResponsable=getActivity().findViewById(R.id.spinnerResponsable);
        spinnerSector=getActivity().findViewById(R.id.spinnerSector);
        spinnerTipo=getActivity().findViewById(R.id.spinnerTipo);
        spinnerGrupo=getActivity().findViewById(R.id.spinnerGrupo);
        tema=getActivity().findViewById(R.id.etTema);
        comentario=getActivity().findViewById(R.id.etComentario);

        String [] tipos=new String[]{"Charla de 5 min","Momento de Seguridad","Otro"};
        ArrayAdapter<String>adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_item_reportes,tipos);
        spinnerTipo.setAdapter(adapter);

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

        //Cuando se selecciona el sector se cargan los grupos
        spinnerSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String spinnerSectorSeleccion= spinnerSector.getSelectedItem().toString();
                ArrayList<String> listaGrupos = new ControladorGrupos(getContext()).getGruposBySector(spinnerSectorSeleccion);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaGrupos);
                spinnerGrupo.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Cuando se selecciona el sector del responsable se cargan responsables
        spinnerSectorResponsable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String spinnerSectorSeleccion = spinnerSectorResponsable.getSelectedItem().toString();
                ArrayList<String> listaResponsables=new ControladorResponsables(getContext()).getResponsablesBySector(spinnerSectorSeleccion);
                ArrayAdapter<String>adapter1=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes,listaResponsables);
                spinnerResponsable.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                        datePicker.setTargetFragment(MomentoSeguridadFragment.this, 0);
                        datePicker.show(getFragmentManager(), "date picker");
                        break;
                }
                return false;
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
        final Button botonFoto=getActivity().findViewById(R.id.botonFoto);
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

        //BOTON ADJUNTAR ARCHIVOS
        Button botonAdjuntar =  getActivity().findViewById(R.id.botonAdjuntar);
        botonAdjuntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //SE ABRE EL BUSCADOR DE ARCHIVOS
                    Intent intent = new Intent();
                    //intent.setType("application/msword,application/pdf,application/excel,application/mspowerpoint");
                    intent.setType("application/*");
                    //intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Seleccione Archivo"), CHOOSE_FILE_ACTIVITY_REQUEST_CODE);
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Error al adjuntar archivo "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });

        //BOTON DE GUARDAR
        Button boton=getActivity().findViewById(R.id.GuardarMomento);
        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    String fecha = txtFecha.getText().toString();
                    String txtTema = tema.getText().toString();
                    String txtComentario = comentario.getText().toString();
                    String spinnerResponsableSeleccion=spinnerResponsable.getSelectedItem().toString();
                    String spinnerSectorSeleccion=spinnerSector.getSelectedItem().toString();
                    String spinnerSiteSeleccion=spinnerSite.getSelectedItem().toString();
                    String spinnerTipoSeleccion=spinnerTipo.getSelectedItem().toString();
                    String spinnerGrupoSeleccion = "";
                    if (spinnerGrupo.getSelectedItem() != null) {
                        spinnerGrupoSeleccion = spinnerGrupo.getSelectedItem().toString();
                    }
                    //iterar por imagesUris y concatenar el filePath
                    String imagePath = "";
                    for(Uri currentUri : imageUris) {
                        imagePath += FilePath.getPath(getContext(), currentUri) +":" ;
                    }
                    boolean resultado=new ControladorSeguridad(getContext()).Insert(txtComentario,txtTema, spinnerSectorSeleccion,
                            spinnerResponsableSeleccion, spinnerTipoSeleccion, spinnerGrupoSeleccion, imagePath, filePath,
                            spinnerSiteSeleccion, usuario, fecha);
                    if(resultado){
                        Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                        new Syncronizer(getActivity(),"setSeguridad").execute();
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
        if (requestCode == CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null ) {
            //si se elige foto desde el celu
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
        //RESPUESTA DEL ADJUNTAR ARCHIVOS
        if (requestCode == CHOOSE_FILE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
                Uri path= null;
                filePath = "";
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for(int i = 0; i < count; i++){
                        path = data.getClipData().getItemAt(i).getUri();
                        filePath += FilePath.getPath(getContext(), path)+":";
                    }
                }else if(data.getData() != null) {
                    //String imagePath = data.getData().getPath();
                    path  = data.getData();
                    filePath = FilePath.getPath(getContext(), path);
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
        txtFecha.setText(dia + "-" + mes + "-" +  Integer.toString(year));
    }

    public interface OnFragmentInteractionListener {
    }

}
