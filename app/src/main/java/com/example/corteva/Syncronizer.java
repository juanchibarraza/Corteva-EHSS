package com.example.corteva;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Syncronizer extends AsyncTask<Void, Void, Void> {

    HttpHandler sh = new HttpHandler();
    //Propiedades de la clase
    Activity activity;
    String tipo;

    public Syncronizer(Activity activity, String tipo) {
        this.activity = activity;
        this.tipo = tipo;
    }

    protected Void doInBackground(Void... params) {

        //validar si hay conexion a internet
        if (isConnected(activity.getApplicationContext())) {
            try {
                //mostrar un Toast que diga sincronizando
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getApplicationContext(), "Iniciando sincronización completa.\nPor favor aguarde.", Toast.LENGTH_LONG).show();
                    }
                });
                if (tipo.equals("setSeguridad")) {
                    uploadMultipart("setSeguridad");
                }else if(tipo.equals("setPruebas")){
                    uploadMultipart("setPruebas");
                }else if(tipo.equals("setReportes")){
                    uploadMultipart("setReportes");
                }else if(tipo.equals("setSODA")){
                    uploadMultipart("setSODA");
                }else if(tipo.equals("setTopTen")){
                    uploadMultipart("setTopTen");
                }else if(tipo.equals("setUAC")) {
                    uploadMultipart("setUAC");
                }else if(tipo.equals("getMisCargas")){
                    if(!SaveSharedPreference.getLoggedEmail(activity).isEmpty()) {
                        GetData("getMisCargas",SaveSharedPreference.getLoggedEmail(activity));
                        GetData("getMisCargasDetalle",SaveSharedPreference.getLoggedEmail(activity));
                    }
                }else if(tipo.equals("setSAI")){
                    uploadMultipart("setSAI");
                }else if(tipo.equals("setComentario")){
                    uploadMultipart("setComentario");
                }else if(tipo.equals("setCalidad")){
                    uploadMultipart("setCalidad");
                } else {
                    //Primero envio los datos de la app
                    uploadMultipart("setSODA");
                    uploadMultipart("setReportes");
                    uploadMultipart("setSeguridad");
                    uploadMultipart("setTopTen");
                    uploadMultipart("setUAC");
                    uploadMultipart("setPruebas");
                    uploadMultipart("setSAI");
                    uploadMultipart("setComentario");
                    //Luego solicito los que estan en la nube
                    int totalDatos = 0;
                    int totalUsers = 0;
                    if (tipo.equals("Completo")) {
                        //Traigo todos los Usuarios para sincronizarlos
                        totalUsers = GetData("getUsers", null);
                        if(!SaveSharedPreference.getLoggedEmail(activity).isEmpty()) {
                            totalDatos += GetData("getMisCargas",SaveSharedPreference.getLoggedEmail(activity));
                            GetData("getMisCargasDetalle",SaveSharedPreference.getLoggedEmail(activity));
                        }
                        totalDatos += GetData("getResponables", null);
                        totalDatos += GetData("getTareas", null);
                        totalDatos += GetData("getSectores", null);
                        totalDatos += GetData("getGrupos", null);
                        totalDatos += GetData("getClasificacion", null);
                        totalDatos += GetData("getSites", null);
                        totalDatos += GetData("getDesviosSAI", null);
                        //traer los UAC para poder cerrarlos
                        totalDatos += GetData("getUACPendientes", null);
                        GetData("getLastVersion", null);
                        final int total1 = totalDatos;
                        final int total2 = totalUsers;
                        //mostrar un Toast con los resultados
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity.getApplicationContext(), "Sincronización Finalizada.\nDatos recibidos:" + total1 + "\nUsuarios actualizados:" + total2, Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {

                        //En lugar de pedir los datos por estado, se piden los que son mas nuevos al campo last_syncro
                        //si la ultima syncro fue mas de 1 hora o es la primera vez que se ejecuta la app -> traigo todos los registros

                        //busco el valor de last_syncro para enviarlo como parametro y ademas, lo actualizo con la fechahoractual
                        String last_syncro = new ControladorConfig(activity.getApplicationContext()).GetSet_last_syncro();
                        //Log.e("last_syncro", last_syncro);

                        //Calculo tiempo transcurrido desde la ultima syncro
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date ultima_syncro = df.parse(last_syncro);
                        Date ahora = df.parse(df.format(new Date()));

                        long millis = Math.abs(ahora.getTime() - ultima_syncro.getTime());
                        int diferencia = (int) (millis / (60 * 1000)); //espera en minutos

                        //Log.e("last_syncro", ultima_syncro.toString() + " ahora: "+ ahora.toString() + " diferencia: "+ Integer.toString(diferencia));

                        if (diferencia >= 60) {
                            //si la ultima syncro fue mas de 60 minutos o es la primera vez que se ejecuta la app -> traigo todos los registros
                            //Traigo todos los Usuarios para sincronizarlos
                            totalUsers = GetData("getUsers", null);
                            //Traigo todas las tablas
                            if(!SaveSharedPreference.getLoggedEmail(activity).isEmpty()) {
                                totalDatos += GetData("getMisCargas",SaveSharedPreference.getLoggedEmail(activity));
                                totalDatos += GetData("getMisCargasDetalle",SaveSharedPreference.getLoggedEmail(activity));
                            }
                            totalDatos += GetData("getResponables", null);
                            totalDatos += GetData("getTareas", null);
                            totalDatos += GetData("getSectores", null);
                            totalDatos += GetData("getGrupos", null);
                            totalDatos += GetData("getUACPendientes", null);
                            totalDatos += GetData("getClasificacion", null);
                            totalDatos += GetData("getSites", null);
                            totalDatos += GetData("getDesviosSAI", null);
                            GetData("getLastVersion", null);
                            final int total1 = totalDatos;
                            final int total2 = totalUsers;
                            //mostrar un Toast con los resultados
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity.getApplicationContext(), "Sincronización Finalizada.\nDatos recibidos:" + total1 + "\nUsuarios actualizados:" + total2, Toast.LENGTH_LONG).show();
                                }
                            });
                        } else if (diferencia > 2) {
                            //sino pido los datos desde la last_syncro
                            //Traigo los Usuarios modificados
                            totalUsers = GetData("getUsersByDatetime", last_syncro);
                            totalDatos = GetData("getUACPendientesByDatetime", last_syncro);
                            if(!SaveSharedPreference.getLoggedEmail(activity).isEmpty()) {
                                totalDatos += GetData("getMisCargas",SaveSharedPreference.getLoggedEmail(activity));
                                totalDatos += GetData("getMisCargasDetalle",SaveSharedPreference.getLoggedEmail(activity));
                            }
                            GetData("getLastVersion", null);
                            final int total1 = totalDatos;
                            final int total2 = totalUsers;
                            //mostrar un Toast con los resultados
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity.getApplicationContext(), "Sincronización Finalizada.\nDatos recibidos:" + total1 + "\nUsuarios actualizados:" + total2, Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                }
            } catch (Exception e) {
                Log.e("SINCRONIZACION ERROR", e.getMessage());
                e.printStackTrace();
            }
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity.getApplicationContext(), "No hay internet para sincronizar", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }

    protected int GetData(String method, String last_syncro) {
        JSONObject row = new JSONObject();
        JSONArray jsonParam = new JSONArray();
        try {
            row.put("last_syncro", last_syncro);
            jsonParam.put(row);
        } catch (Exception e) {

        }
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(method, jsonParam);
        //Log.e("SINCRONIZACION", "Response from url: " + jsonStr);
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray datos = jsonObj.getJSONArray("datos");

                // looping through All Datos
                for (int i = 0; i < datos.length(); i++) {
                    JSONObject c = datos.getJSONObject(i);

                    if (method.equals("getUACPendientesByDatetime") || method.equals("getUACPendientes")) {
                        String numero = c.getString("id");
                        String site = c.getString("Site");
                        String lugar = c.getString("lugar");
                        String accion = c.getString("accion");
                        String estado = c.getString("estado");
                        String responsable = c.getString("responsable");
                        String vencimiento = c.getString("vencimiento");
                        String sector = c.getString("sector");
                        String riesgo = c.getString("riesgo");
                        String condicionInsegura = c.getString("condicionInsegura");
                        String usuarioCreacion = c.getString("usuarioCreacion");
                        String fechaHoraCreacion = c.getString("fechaHoraCreacion");
                        String tipo = c.getString("Tipo");
                        String lider = c.getString("Lider_recorrida");
                        String participantes = c.getString("Participantes");
                        String categoria = c.getString("Categoria");
                        boolean success = new ControladorUAC(activity.getApplicationContext()).InsertOrUpdate(tipo, lugar, accion, responsable, vencimiento, sector, riesgo, estado, condicionInsegura, usuarioCreacion, fechaHoraCreacion, site, numero, lider, participantes, categoria);
                        //Log.e("INSERTAR solicitudes", "RESPUESTA INSERT: " + success);
                    } else if (method.equals("getUsers") || method.equals("getUsersByDatetime")) {
                        Integer idUsuario = c.getInt("idUsuario");
                        String user = c.getString("usuario");
                        String password = c.getString("password");
                        String nombre = c.getString("Nombre");
                        String site = c.getString("Site");
                        Boolean success = new ControladorUsuarios(activity.getApplicationContext()).InsertOrUpdate(idUsuario, user, password, nombre, site);
                        //Log.e("INSERTAR Usuarios", "RESPUESTA INSERT: " + success);
                    } else if (method.equals("getResponables")) {
                        Integer id = c.getInt("id");
                        String nombre = c.getString("Responsable");
                        String sector = c.getString("Sector");
                        Integer activo = c.getInt("Activo");
                        Boolean success = new ControladorResponsables(activity.getApplicationContext()).InsertOrUpdate(id, nombre, sector, activo);
                    } else if (method.equals("getTareas")) {
                        Integer id = c.getInt("id");
                        String nombre = c.getString("Tarea");
                        Integer activo = c.getInt("Activo");
                        Boolean success = new ControladorTareas(activity.getApplicationContext()).InsertOrUpdate(id, nombre, activo);
                    } else if (method.equals("getSectores")) {
                        Integer id = c.getInt("id");
                        String site = c.getString("Site");
                        String nombre = c.getString("Sector");
                        Integer activo = c.getInt("Activo");
                        Boolean success = new ControladorSectores(activity.getApplicationContext()).InsertOrUpdate(id, site, nombre, activo);
                    } else if (method.equals("getGrupos")) {
                        Integer id = c.getInt("id");
                        String grupo = c.getString("Grupo");
                        String sector = c.getString("Sector");
                        Integer activo = c.getInt("Activo");
                        Boolean success = new ControladorGrupos(activity.getApplicationContext()).InsertOrUpdate(id, grupo, sector, activo);
                    } else if (method.equals("getClasificacion")) {
                        Integer id = c.getInt("id");
                        String clasificacion = c.getString("Clasificacion");
                        Integer activo = c.getInt("Activo");
                        Boolean success = new ControladorClasificacion(activity.getApplicationContext()).InsertOrUpdate(id, clasificacion, activo);
                    } else if (method.equals("getDesviosSAI")){
                        Integer id = c.getInt("id");
                        String desvio = c.getString("Desvio");
                        String severidad = c.getString("Severidad");
                        Integer activo = c.getInt("Activo");
                        Boolean success = new ControladorSAIDesvios(activity.getApplicationContext()).InsertOrUpdate(id, desvio, severidad, activo);
                    }
                    else if (method.equals("getSites")) {
                        Integer idSite = c.getInt("idSite");
                        String site = c.getString("Site");
                        Integer activo = c.getInt("Activo");
                        Boolean success = new ControladorSites(activity.getApplicationContext()).InsertOrUpdate(idSite, site, activo);
                    } else if(method.equals("getMisCargas")){
                        Integer bbpanual = c.getInt("bbpanual");
                        Integer bbpmes = c.getInt("bbpmes");
                        Integer bbpultimostres = c.getInt("bbpultimostres");
                        Integer momentosanual = c.getInt("momentosanual");
                        Integer momentosmes = c.getInt("momentosmes");
                        Integer momentosultimostres = c.getInt("momentosultimostres");
                        Integer pruebasanual = c.getInt("pruebasanual");
                        Integer pruebasmes = c.getInt("pruebasmes");
                        Integer pruebasultimostres = c.getInt("pruebasultimostres");
                        Integer reportesanual = c.getInt("reportesanual");
                        Integer reportesmes = c.getInt("reportesmes");
                        Integer reportesultimostres = c.getInt("reportesultimostres");
                        Integer toptenanual = c.getInt("toptenanual");
                        Integer toptenmes = c.getInt("toptenmes");
                        Integer toptenultimostres = c.getInt("toptenultimostres");
                        Integer uacanual = c.getInt("uacanual");
                        Integer uacmes = c.getInt("uacmes");
                        Integer uacultimostres = c.getInt("uacultimostres");
                        boolean success = new ControladorConfig(activity.getApplicationContext()).Set_miscargas(bbpanual, bbpmes, bbpultimostres, momentosanual, momentosmes, momentosultimostres, pruebasanual, pruebasmes, pruebasultimostres, reportesanual, reportesmes, reportesultimostres, toptenanual, toptenmes, toptenultimostres, uacanual, uacmes, uacultimostres);
                    } else if(method.equals("getMisCargasDetalle")){
                        String tipo = c.getString("Tipo");
                        String fechahora = c.getString("fechaHora");
                        String registros = c.getString("Datos");
                        String usuario = c.getString("usuario");
                        Boolean success = new ControladorConfig(activity.getApplicationContext()).Set_miscargasDetalle(tipo, fechahora, registros, usuario);
                    } else if (method.equals("getLastVersion")) {
                        Integer version = c.getInt("version");
                        PackageManager manager = activity.getPackageManager();
                        try {
                            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
                            Integer versionCode = (Integer) info.versionCode;
                            SaveSharedPreference.setNewVersion(activity, version > versionCode);
                        } catch (PackageManager.NameNotFoundException e) {

                        }
                    }
                }
                return datos.length();
            } catch (final JSONException e) {
                Log.e("SINCRONIZACION", "Json parsing error: " + e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }

    public void uploadMultipart(final String method) {
        //Uploading code
        JSONArray jsonParam = new JSONArray();
        /*if (method.equals("setBBP")){
            jsonParam = new ControladorStop(activity.getApplicationContext()).getStopPendientes();
        }*/
        if (method.equals("setSODA")) {
            jsonParam = new ControladorSODA(activity.getApplicationContext()).getSODAPendientes();
        } else if (method.equals("setReportes")) {
            jsonParam = new ControladorReportes(activity.getApplicationContext()).getReportesPendientes();
        } else if (method.equals("setSeguridad")) {
            jsonParam = new ControladorSeguridad(activity.getApplicationContext()).getSeguridadPendientes();
        } else if (method.equals("setTopTen")) {
            jsonParam = new ControladorTopTen(activity.getApplicationContext()).getTopTenPendientes();
        } else if (method.equals("setUAC")) {
            jsonParam = new ControladorUAC(activity.getApplicationContext()).getUACSinSincronizar();
        } else if (method.equals("setPruebas")) {
            jsonParam = new ControladorPruebasACampo(activity.getApplicationContext()).getPruebasPendientes();
        } else if (method.equals("setSAI")){
            jsonParam = new ControladorSAI(activity.getApplicationContext()).getSAIPendientes();
        } else if (method.equals("setComentario")){
            jsonParam = new ControladorComentarios(activity.getApplicationContext()).getComentariosPendientes();
        }
        try {
            for (int i = 0; i < jsonParam.length(); i++) {
                try {
                    JSONObject c = jsonParam.getJSONObject(i);
                    //Creating a multi part request
                    String uploadId = method + "_" + UUID.randomUUID().toString();
                    //uploadReceiver.setUploadID(uploadId);
                    MultipartUploadRequest request = new MultipartUploadRequest(activity.getApplicationContext(), uploadId, "https://www.cortevasemillasconosur.com/ehss_api_rest.php");

                    if (method.equals("setReportes")) {
                        if (c.has("imagen")) {
                            if(!c.getString("imagen").equals(null) && !c.getString("imagen").equals("")) {
                                if (c.getString("imagen").contains(":")) {
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagen").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagen[]");
                                    }
                                } else {
                                    request.addFileToUpload(c.getString("imagen"), "imagen[]");
                                }
                            }
                        }
                    } else if (method.equals("setSeguridad")) {
                        if (c.has("imagen")) {
                            if(!c.getString("imagen").equals(null)  && !c.getString("imagen").equals("")){
                                if(c.getString("imagen").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagen").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagen[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagen"), "imagen[]");
                                }
                            }
                        }
                        if (c.has("filesPath")) {
                            if(c.getString("filesPath").contains(":")){
                                //hay muchos archivos
                                String[] myData = c.getString("filesPath").split(":");
                                for (String s : myData) {
                                    request.addFileToUpload(s, "filesPath[]");
                                }
                            }else if(!c.getString("filesPath").equals(null)){
                                request.addFileToUpload(c.getString("filesPath"), "filesPath[]");
                            }
                        }
                    } else if (method.equals("setTopTen")) {
                        if (c.has("imagenPTS")) {
                            if(!c.getString("imagenPTS").equals(null) && !c.getString("imagenPTS").equals("")){
                                if(c.getString("imagenPTS").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagenPTS").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagenPTS[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagenPTS"), "imagenPTS[]");
                                }
                            }
                        }
                        if (c.has("imagenTarjetasRojas")) {
                            if(!c.getString("imagenTarjetasRojas").equals(null) && !c.getString("imagenTarjetasRojas").equals("")){
                                if(c.getString("imagenTarjetasRojas").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagenTarjetasRojas").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagenTarjetasRojas[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagenTarjetasRojas"), "imagenTarjetasRojas[]");
                                }
                            }

                        }
                        if (c.has("imagenEspacioConfinado")) {
                            if(!c.getString("imagenEspacioConfinado").equals(null) && !c.getString("imagenEspacioConfinado").equals("")){
                                if(c.getString("imagenEspacioConfinado").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagenEspacioConfinado").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagenEspacioConfinado[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagenEspacioConfinado"), "imagenEspacioConfinado[]");
                                }
                            }
                        }
                        if (c.has("imagenTrabajoEnAltura")) {
                            if(!c.getString("imagenTrabajoEnAltura").equals(null) && !c.getString("imagenTrabajoEnAltura").equals("")){
                                if(c.getString("imagenTrabajoEnAltura").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagenTrabajoEnAltura").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagenTrabajoEnAltura[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagenTrabajoEnAltura"), "imagenTrabajoEnAltura[]");
                                }
                            }
                        }
                        if (c.has("imagenAperturaLinea")) {
                            if(!c.getString("imagenAperturaLinea").equals(null) && !c.getString("imagenAperturaLinea").equals("")){
                                if(c.getString("imagenAperturaLinea").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagenAperturaLinea").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagenAperturaLinea[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagenAperturaLinea"), "imagenAperturaLinea[]");
                                }
                            }
                        }
                        if (c.has("imagenTrabajoEnCaliente") && !c.getString("imagenTrabajoEnCaliente").isEmpty()) {
                            if(!c.getString("imagenTrabajoEnCaliente").equals(null) && !c.getString("imagenTrabajoEnCaliente").equals("")){
                                if(c.getString("imagenTrabajoEnCaliente").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagenTrabajoEnCaliente").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagenTrabajoEnCaliente[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagenTrabajoEnCaliente"), "imagenTrabajoEnCaliente[]");
                                }
                            }
                        }
                        if (c.has("imagenHidrolavadora")) {
                            if(!c.getString("imagenHidrolavadora").equals(null) && !c.getString("imagenHidrolavadora").equals("")){
                                if(c.getString("imagenHidrolavadora").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagenHidrolavadora").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagenHidrolavadora[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagenHidrolavadora"), "imagenHidrolavadora[]");
                                }
                            }
                        }
                    } else if (method.equals("setUAC")) {
                        if (c.has("imagenUAC")){
                            if(!c.getString("imagenUAC").equals(null) && !c.getString("imagenUAC").equals("")){
                                if(c.getString("imagenUAC").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagenUAC").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagenUAC[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagenUAC"), "imagenUAC[]");
                                }
                            }
                        }
                        if (c.has("imagenUACCIERRE")) {
                            if(!c.getString("imagenUACCIERRE").equals(null) && !c.getString("imagenUACCIERRE").equals("")){
                                if(c.getString("imagenUACCIERRE").contains(":")){
                                    //hay muchas imagenes
                                    String[] myData = c.getString("imagenUACCIERRE").split(":");
                                    for (String s : myData) {
                                        request.addFileToUpload(s, "imagenUACCIERRE[]");
                                    }
                                }else{
                                    request.addFileToUpload(c.getString("imagenUACCIERRE"), "imagenUACCIERRE[]");
                                }
                            }
                        }
                    }

                    request.addParameter("accion", method)
                            .addParameter("user", "MobileApp")
                            .addParameter("pwd", "12345")
                            .addParameter("datos", c.toString()) //Adding text parameter to the request
                            .setUtf8Charset()
                            //Las notificaciones son obligatorias
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .startUpload(); //La respuesta del envio se recibe en MyReceiver
                } catch (Exception exc) {
                    Toast.makeText(activity.getApplicationContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            //Log.e("SINCRONIZACION SENDING", "Exception: " + e.getMessage());
        }
    }

    protected static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}
