package com.example.corteva.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.corteva.ControladorUAC;
import com.example.corteva.FilePath;
import com.example.corteva.R;
import com.example.corteva.Syncronizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UACCIERREFragment extends Fragment {

    EditText comentario;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE=1111;
    private static final int CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE = 11111;
    ImageSwitcher switcherImage;
    private ArrayList<Uri> imageUris;
    int position = 0;
    Uri imageUri;
    LinearLayout imageSwitcherContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_uaccieere, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

        //Recibo parametros
        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");
        //id de la UAC que se desea cerrar
        final String id = bundle.getString("id");

        imageSwitcherContainer = getActivity().findViewById(R.id.imageSwitcher);
        imageUris = new ArrayList<Uri>();

        comentario=getActivity().findViewById(R.id.etComentario);

        TextView titulo1 = getActivity().findViewById(R.id.titulo2);
        TextView titulo2 = getActivity().findViewById(R.id.titulo2);
        TextView titulo3 = getActivity().findViewById(R.id.titulo3);

        HashMap<String, String> fila= new ControladorUAC(getContext()).getOneUAC(id);
        titulo1.setText(fila.get("numeroysector"));
        titulo2.setText(fila.get("responsable"));
        titulo3.setText(fila.get("lugar"));

        //BOTON ELEGIR IMAGEN DESDE GALERIA
        final Button botonElegirFoto=getActivity().findViewById(R.id.buttonElegirFotoUACCIERRE);
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
        final Button botonFoto=getActivity().findViewById(R.id.botonFotoUACCIERRE);
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
                        position = 0;
                        switcherImage.setImageURI(null);
                    }
                    Toast.makeText(getActivity(), "Imagen borrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTON GUARDAR
        Button boton=getActivity().findViewById(R.id.botonGuardarUACCIERRE);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String txtComentario=comentario.getText().toString();
                    //iterar por imagesUris y concatenar el filePath
                    String filePath = "";
                    for(Uri currentUri : imageUris) {
                        filePath += FilePath.getPath(getContext(), currentUri) +":" ;
                    }
                    boolean resultado=new ControladorUAC(getContext()).Cerrar(id, txtComentario, usuario, filePath);
                    if(resultado){
                        Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                        new Syncronizer(getActivity(),"setUAC").execute();
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
        if (requestCode == CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
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
    }

    public interface OnFragmentInteractionListener {
    }
}
