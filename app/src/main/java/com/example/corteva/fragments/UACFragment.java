package com.example.corteva.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.corteva.ControladorResponsables;
import com.example.corteva.ControladorSectores;
import com.example.corteva.ControladorSites;
import com.example.corteva.ControladorUAC;
import com.example.corteva.DatePickerFragment;
import com.example.corteva.FilePath;
import com.example.corteva.R;
import com.example.corteva.SaveSharedPreference;
import com.example.corteva.Syncronizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;


public class UACFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    Spinner spinnerTipo, spinnerRiesgo, spinnerSector, spinnerResponsable, spinnerSite;
    EditText txtLugar, txtFecha, txtAccion, txtVencimiento, txtCondicionInsegura, txtLider, txtParticipantes;
    TextView txtCategoria;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE=1313;
    private static final int CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE = 13131;
    int CALENDAR_CODE = 0;
    ImageSwitcher switcherImage;
    private ArrayList<Uri> imageUris;
    int position = 0;
    Uri imageUri;
    LinearLayout imageSwitcherContainer;

    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {
            "Señalización y cartelería",
            "1. Posee cartelería sobre identificación de riesgos.",
            "2. Señalización de prohibido fumar.",
            "3. NFPA símbolos para identificar productos químicos.",
            "4. Cartelería para uso de elementos de protección personal.",
            "5. Números de teléfonos de emergencia disponibles.",
            "6. Plan de emergencias actualizado",
            "Orden y limpieza de pisos",
            "7. Limpios y libres de aceite, grasa y escombros.",
            "8. No hay superficies desiguales o con defectos.",
            "9. Plataformas o salidas con zócalos de protección de 10 cm. de alto.",
            "10. Área de trabajo limpia y ordenada.",
            "11. Herramientas y equipos de trabajo en su lugar cuando no son utilizados.",
            "12. Guarda-pies en buen estado",
            "Orden y limpieza de pasillos",
            "13. Claramente marcados con franjas blancas o amarillas.",
            "14. Libre acceso.",
            "15. Lo suficiente amplios para el manejo de materiales (al menos de 1 metro).",
            "Escaleras",
            "16. De altura y profundidad suficiente.",
            "17. Los escalones no están gastados o dañados.",
            "18. Barandas instaladas cuando son 4  o más escalones.",
            "19. Libre acceso.",
            "20. Iluminación adecuada.",
            "Almacenamiento de productos químicos (APQ)",
            "21. Hojas de seguridad disponibles.",
            "22. Etiquetas en los bidones que contienen productos.",
            "23. Equipo de contención y limpieza accesibles.",
            "24. Contenedores de productos en buenas condiciones, sin señales de corrosión",
            "Almacenamiento de líquidos inflamables",
            "25. Si se toman contenedores a granel, se utilizan envases seguros.",
            "26. Área de almacenamiento lejos de las fuentes de calor y otros combustibles.",
            "27. Combustible etiquetado y almacenado en los contenedores apropiados.",
            "28. El almacenamiento reúne las condiciones requeridas para tal fin.",
            "Gases comprimidos",
            "29. Almacenamiento vertical y seguro, Área alejada de las fuentes de calor.",
            "30. Contenido indicado en todos los cilindros.",
            "31. Cilindros de oxigeno/gases separados por una pared de al menos 1.6 metros de alto o como mínimo a separados 6 metros entre sí (Pared: ½ hora de resistencia contra incendios).",
            "Salidas de emergencia y vías de evacuación",
            "32. Salidas no cerradas o bloqueadas.",
            "33. Las salidas de emergencia se encuentran identificadas.",
            "34. Las puertas que abren hacia superficies cerradas.",
            "35. Luces de emergencia en funcionamiento.",
            "36. Aquellas salidas que no son salidas de emergencia o que no den al exterior, poseen un cartel que dice “Salida” o bien preferentemente que dice “Salida sin acceso al exterior”",
            "Extintores contra incendios",
            "37. Del tipo y tamaño apropiado colocado en una ubicación accesible (dentro de 15 a 25 metros del área de trabajo dependiendo del peligro, y a una altura de 0.9 m a 1.5m sobre el piso).",
            "38. Ubicación claramente marcada.",
            "39. Inspeccionado mensualmente y anotado en una planilla que lleva el responsable del sector.",
            "40. Se le realiza servicio una vez en el año por una empresa externa.",
            "41. Se eliminan las unidades en mal estado y son etiquetadas como “fuera de servicio”.",
            "Estación lavaojos y ducha de emergencia",
            "42. Se encuentra ubicadas en un lugar accesible para el empleado que requiera utilizarla.",
            "43. Fechas de expiración de las unidades portátiles revisadas regularmente.",
            "44. Revisiones permanentes de las unidades y mantenimiento en una base mensual.",
            "Puestos de rescate y botiquín de primeros auxilios",
            "45. Disponible y de fácil acceso. Completo.",
            "46. Puestos de rescate: buen estado en general",
            "47. Puestos de rescate: control periódico",
            "Protecciones de seguridad en maquinaria",
            "48. Diseñadas para piezas específicas del equipo.",
            "49. Guardas para puntas y partes en movimiento (rotación, traslación y alternativos).",
            "50. Previenen que cualquier parte del cuerpo entre en contacto con la maquina en movimiento.",
            "51. Todas las correas, cadenas, que se encuentren por debajo de 2.2 m del piso poseen guardas en ruedas y engranajes.",
            "Equipos con energía mecánica",
            "52. Los controles de las paradas de emergencia están claramente visibles, etiquetados y accesibles.",
            "53. Hay protecciones que previenen encendidos accidentales.",
            "Herramientas de mano portátiles",
            "54. Eléctricamente puestas a tierra o de doble aislamiento.",
            "55. Conexión de 3 entradas, operativa.",
            "56. Cables en buenas condiciones.",
            "57. Guardas e instalaciones de seguridad operativas.",
            "58. Interruptor de presión constante para apagar /encender.",
            "Escaleras portátiles",
            "59. Todas las partes se encuentran en buenas condiciones. ",
            "60. Pie de seguridad en buena condición operable.",
            "61. Escaleras que no sean de metal o aluminio en las áreas eléctricas.",
            "62. Las escaleras que no sean seguras deben retirarse de uso y ser etiquetadas.",
            "63. Las escaleras defectuosas de madera deben ser desechadas y no reparadas.",
            "Equipos para elevar cargas (grúas, aparejos, eslingas)",
            "64. Capacidad de carga identificada.",
            "65. Señales de advertencia ubicadas en donde se requiere su uso.",
            "66. Inspección y mantenimiento periódico.",
            "Compresores de aire",
            "67. Tienen una boquilla apropiada para reducir la presión a 2.11 kgf/cm2 (30 psi).",
            "Manejo de material, descarga y depósitos",
            "68. Calzas para las cubiertas de los camiones disponibles.",
            "69. Líneas amarillas pintadas a lo largo del borde de las rampas y descargas.",
            "70. Espejos en su lugar, en las esquinas ciegas.",
            "71. Señales de advertencia colocadas para límite de alturas.",
            "Montacargas",
            "72. Programa de capacitación para el conductor.",
            "73. Inspección de mantenimiento preventivo realizada.",
            "74. Bocina, sirena, baliza de retroceso y luces operables.",
            "75. Procedimiento de estacionamiento seguro (frenos, retiro de llave, uñas sobre el piso).",
            "76. Uso de cinturones de seguridad.",
            "77. Extintores disponibles en todas las unidades.",
            "78. Capacidad de carga máxima indicada en la unidad.",
            "Eléctrico",
            "79. Tableros de control indicando voltaje / peligro",
            "80. No haya cables expuestos, tableros o cajas de conexiones abiertas.",
            "81. Tableros de control asegurados, accesibles y no obstruidos.",
            "82. Interruptores de control marcados indicando “apagado” para la posición que corresponda.",
            "Cables de extensión",
            "83. Clasificado para uso industrial (TPR).",
            "84. Aislamiento sin quemaduras  y rupturas.",
            "85. Conexiones de puestas a tierra en su lugar.",
            "Misceláneo",
            "86. El personal posee una capacitación en los procedimientos de bloqueo/etiquetado.",
            "87. Equipo de protección personal provisto a los empleados.",
            "Capacitación",
            "88. Espacios confinados.",
            "89. Agentes patógenos de origen sanguíneo.",
            "90. Protección de caídas.",
            "91. EPP",
            "92. Comunicación de peligros.",
            "93. Registro de momentos de seguridad",
            "Arnés de seguridad",
            "94. Etiquetas actualizadas al mes actual",
    };
    final boolean[] disabledItems = new boolean[]{false,true,true,true,true,true,true,false,true,true,true,true,true,true,false,true,true,true,false,true,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,false,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,true,false,true,true,false,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,true,false,true,true,true,true,true,true,true,false,true,true,true,true,false,true,true,true,false,true,true,false,true,true,true,true,true,true,false,true    };
    boolean[] selectedLanguage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_uac, container, false);

        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        //Recibo parametros
        Bundle bundle=this.getArguments();
        final String usuario = bundle.getString("usuario");

        imageSwitcherContainer = getActivity().findViewById(R.id.imageSwitcher);
        imageUris = new ArrayList<Uri>();

        final Spinner spinnerSectorResponsable=getActivity().findViewById(R.id.spinnerSectorResponsable);
        spinnerResponsable=getActivity().findViewById(R.id.spinnerResponsable);
        spinnerRiesgo=getActivity().findViewById(R.id.spinnerRiesgo);
        spinnerSector=getActivity().findViewById(R.id.spinnerSector);
        spinnerSite=getActivity().findViewById(R.id.spinnerSiteUAC);
        spinnerTipo = getActivity().findViewById(R.id.spinnerTipoUAC);
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        txtFecha = getActivity().findViewById(R.id.etFecha);
        txtFecha.setText(today);
        txtLugar=getActivity().findViewById(R.id.etLugar);
        txtAccion=getActivity().findViewById(R.id.etAccion);
        txtVencimiento=getActivity().findViewById(R.id.etVencimiento);
        txtCondicionInsegura=getActivity().findViewById(R.id.etCondicionInsegura);
        txtLider=getActivity().findViewById(R.id.etLider);
        txtParticipantes=getActivity().findViewById(R.id.etParticipantes);
        txtCategoria = getActivity().findViewById(R.id.etCategoria);
        selectedLanguage = new boolean[langArray.length];

        String [] riesgos=new String[]{"Bajo","Medio","Alto"};
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes, riesgos);
        spinnerRiesgo.setAdapter(adapter);

        String [] tipos = new String[]{"Reporte de condiciones inseguras UAC","Auditoria de primera parte","Recorrida EHS&S"};
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_reportes, tipos);
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

        //Cuando se selecciona el TIPO se muestran/ocultan los campos
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout LL = getActivity().findViewById(R.id.layoutAuditorias);
                String spinnerSeleccion= spinnerTipo.getSelectedItem().toString();
                if (spinnerSeleccion == "Auditoria de primera parte") {
                    LL.setVisibility(view.VISIBLE);
                }else{
                    LL.setVisibility(view.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // initialize selected language array
        selectedLanguage = new boolean[langArray.length];

        //BOTON AGREGAR CATEGORIAS
        txtCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Seleccione Categoria");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            if(disabledItems[i]){
                                // when checkbox selected
                                // Add position  in lang list
                                if(!langList.contains(i)){
                                    langList.add(i);
                                }
                                selectedLanguage[i] = true;
                            }else{
                                selectedLanguage[i] = false;
                                ((AlertDialog) dialogInterface).getListView().setItemChecked(i, false);
                            }
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                            selectedLanguage[i] = false;
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        //for (int j = 0; j < selectedLanguage.length; j++) {
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append("/-/\n");
                            }
                        }
                        // set text on textView
                        txtCategoria.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Limpiar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            txtCategoria.setText("");
                        }
                    }
                });

                /* deshabilito las opciones que son titulos */
                AlertDialog alertDialog = builder.create();

                // show dialog
                alertDialog.show();
            }
        });

        //CALENDARIO
        txtFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se muestre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 1;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(UACFragment.this, 555);
                        datePicker.show(getFragmentManager(), "date picker");
                        break;
                }
                return false;
            }
        });
        txtVencimiento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //se agrega el switch para validar que se muestre solo cuando se hace clic (action down)
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        CALENDAR_CODE = 2;
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.setTargetFragment(UACFragment.this, 111);
                        datePicker.show(getFragmentManager(), "date picker");
                        break;
                }
                return false;
            }
        });

        //BOTON ELEGIR IMAGEN DESDE GALERIA
        final Button botonElegirFoto=getActivity().findViewById(R.id.buttonElegirFotoUAC);
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
        final Button botonFoto=getActivity().findViewById(R.id.botonFotoUAC);
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
        Button boton=getActivity().findViewById(R.id.GuardarUAC);
        boton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try{
                    String getVencimiento=txtVencimiento.getText().toString();
                    String fecha = txtFecha.getText().toString();
                    if(getVencimiento.length() > 0) {
                        String estado="Pendiente";
                        String spinnerTipoSeleccion = spinnerTipo.getSelectedItem().toString();
                        String getLugar=txtLugar.getText().toString();
                        String getAccion=txtAccion.getText().toString();
                        String getCondicionInsegura=txtCondicionInsegura.getText().toString();
                        String spinnerRiesgoSeleccion=spinnerRiesgo.getSelectedItem().toString();
                        String spinnerSectorSeleccion=spinnerSector.getSelectedItem().toString();
                        String spinnerResponsableSeleccion=spinnerResponsable.getSelectedItem().toString();
                        String spinnerSiteSeleccion=spinnerSite.getSelectedItem().toString();
                        String getLider = txtLider.getText().toString();
                        String getParticipantes = txtParticipantes.getText().toString();
                        String getCategoria = txtCategoria.getText().toString();
                        //iterar por imagesUris y concatenar el filePath
                        String filePath = "";
                        for(Uri currentUri : imageUris) {
                            filePath += FilePath.getPath(getContext(), currentUri) +":" ;
                        }
                        boolean resultado=new ControladorUAC(getContext()).Insert(spinnerTipoSeleccion, getLugar,getAccion,spinnerResponsableSeleccion,getVencimiento,
                                spinnerRiesgoSeleccion,spinnerSectorSeleccion,estado, getCondicionInsegura, usuario, filePath,
                                spinnerSiteSeleccion, fecha, getLider, getParticipantes, getCategoria);
                        if(resultado){
                            Toast.makeText(getActivity(),"Guardado correctamente.", Toast.LENGTH_SHORT).show();
                            new Syncronizer(getActivity(),"setUAC").execute();
                        }else{
                            Toast.makeText(getActivity(),"No se guardó correctamente.",Toast.LENGTH_SHORT).show();
                        }
                        if(spinnerTipoSeleccion == "Auditoria de primera parte"){
                            /* Limpio el form y lo dejo abierto */
                            txtVencimiento.setText("");
                            txtLugar.setText("");
                            txtAccion.setText("");
                            txtCondicionInsegura.setText("");
                            txtLider.setText("");
                            txtParticipantes.setText("");
                            txtCategoria.setText("");
                            spinnerSectorResponsable.setSelection(0);
                            if( filePath != ""){
                                filePath = "";
                                imageUris = new ArrayList<Uri>();
                                position = 0;
                                switcherImage.setImageURI(null);
                            }
                        }else{
                            getActivity().onBackPressed();
                        }

                    }else{
                        Toast.makeText(getActivity(),"Complete el campo Fecha Vencimiento (*).",Toast.LENGTH_LONG).show();
                    }

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
            txtFecha.setText(Integer.toString(year) + "-" + mes + "-" + dia);
        }else if(CALENDAR_CODE==2) {
            txtVencimiento.setText(Integer.toString(year) + "-" + mes + "-" + dia);
        }
    }

    public interface OnFragmentInteractionListener {
    }
}