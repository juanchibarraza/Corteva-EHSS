package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ControladorUAC extends AdminSQLiteOpenHelper {
    public ControladorUAC (Context context){
        super(context);
    }

    //se pueden generar UAC desde el celular o pueden venir nuevos desde la nube

    //Este metodo es para guardar desde el celular
    public boolean Insert(String tipo, String lugar, String accion, String responsable, String vencimiento,String spinnerRiesgoSeleccion,
                          String spinnerSectorSeleccion, String estado, String condicionInsegura, String usuario, String imagen, String site,
                          String fecha, String Lider_recorrida, String Participantes, String Categoria){

        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        if(tipo=="Reporte de condiciones inseguras UAC"){
            tipo = "UAC";
        }else if(tipo == "Auditoria de primera parte"){
            tipo = "Auditoria";
        }else {
            tipo = "EHSS";
        }
        values.put("tipo", tipo);
        values.put("site", site);
        values.put("fecha", fecha);
        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("fechaHoraCreacion", fh);
        values.put("sector",spinnerSectorSeleccion);
        values.put("lugar", lugar);
        values.put("accion", accion);
        values.put("responsable", responsable);
        values.put("vencimiento", vencimiento);
        values.put("riesgo",spinnerRiesgoSeleccion);
        values.put("estado",estado );
        values.put("condicionInsegura", condicionInsegura);
        values.put("usuarioCreacion",usuario);
        values.put("imagenUAC",imagen);
        values.put("Lider_recorrida",Lider_recorrida);
        values.put("Participantes",Participantes);
        values.put("Categoria",Categoria);
        values.put("syncro", 0);

        boolean resultado=BaseDeDatos.insert("uac",null,values)>0;
        BaseDeDatos.close();
        return resultado;
    }

    //Este metodo son para las UAC que vienen desde la nube
    public boolean InsertOrUpdate(String tipo, String lugar, String accion, String responsable, String vencimiento,String sector,String riesgo,
                                  String estado, String condicionInsegura, String usuarioCreacion, String fechaHoraCreacion, String site, String numero,
                                  String Lider_recorrida, String Participantes, String Categoria){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("tipo", tipo);
        values.put("lugar", lugar);
        values.put("accion", accion);
        values.put("responsable", responsable);
        values.put("vencimiento", vencimiento);
        values.put("sector",sector);
        values.put("estado",estado );
        values.put("riesgo",riesgo);
        values.put("condicionInsegura", condicionInsegura);
        values.put("usuarioCreacion",usuarioCreacion);
        values.put("fechaHoraCreacion",fechaHoraCreacion);
        values.put("site", site);
        values.put("numero", numero);
        values.put("Lider_recorrida",Lider_recorrida);
        values.put("Participantes",Participantes);
        values.put("Categoria",Categoria);
        String where = "fechaHoraCreacion = ? AND sector=? AND usuarioCreacion=?";
        String[] whereArgs = { fechaHoraCreacion, sector, usuarioCreacion };
        boolean success = BaseDeDatos.update("uac", values, where, whereArgs) > 0;
        if (!success) {
            success = BaseDeDatos.insert("uac", null, values) > 0;
        }
        BaseDeDatos.close();
        return success;
    }

    //se actualizar la tabla UAC con los datos de cierre segun el ID pasado como parametro
    public boolean Cerrar(String id, String comentario, String usuario, String imagen){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("comentarioCierre",comentario);
        values.put("estado", "Cerrado");
        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("fechaHoraCierre", fh);
        values.put("usuarioCierre", usuario);
        values.put("imagenUACCIERRE",imagen);
        values.put("syncro", 0);

        String where = "id = ?";
        String[] whereArgs = { id };
        boolean success = BaseDeDatos.update("uac", values, where, whereArgs) > 0;
        BaseDeDatos.close();
        return success;
    }


    public ArrayList<HashMap<String, String>> getUACPendientes(String Site, String sector, String responsable) {
        ArrayList<HashMap<String, String>> rows = new ArrayList<>();

        String consulta = "SELECT id, numero, responsable, sector, lugar, condicionInsegura, accion FROM uac WHERE (estado='Pendiente' OR estado='Vencido') AND site='"+ Site +"'";
        if (!sector.isEmpty() && !sector.equals("Todos") && !sector.equals("Seleccione Sector")){
            consulta +=" AND sector ='" + sector + "'";
        }
        if (!responsable.isEmpty() && !responsable.equals("Todos")){
            consulta +=" AND responsable ='" + responsable + "'";
        }
        consulta += " ORDER BY numero ASC, sector ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> row = new HashMap<>();
                // adding each child node to HashMap key => value
                row.put("id", cursor.getString(cursor.getColumnIndex("id")));
                if(cursor.getString(cursor.getColumnIndex("numero")) != null){
                    row.put("numeroysector", "N°: " + cursor.getString(cursor.getColumnIndex("numero")) + " - "+ cursor.getString(cursor.getColumnIndex("sector")));
                }else{
                    row.put("numeroysector", cursor.getString(cursor.getColumnIndex("sector")));
                }
                row.put("responsable", "Responsable: " + cursor.getString(cursor.getColumnIndex("responsable")));
                row.put("lugar", "Lugar: " + cursor.getString(cursor.getColumnIndex("lugar")) +
                        "\nCondición insegura: " + cursor.getString(cursor.getColumnIndex("condicionInsegura")) +
                        "\nAcción: " + cursor.getString(cursor.getColumnIndex("condicionInsegura")));
                rows.add(row);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return rows;
    }

    public HashMap<String, String> getOneUAC(String id) {
        HashMap<String, String> row = new HashMap<>();

        String consulta = "SELECT id, numero, responsable, sector, lugar, condicionInsegura, accion FROM uac WHERE id="+id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);
        if (cursor.moveToFirst()) {
            do {

                // adding each child node to HashMap key => value
                row.put("id", cursor.getString(cursor.getColumnIndex("id")));
                if(cursor.getString(cursor.getColumnIndex("numero")) != null){
                    row.put("numeroysector", "N°: " + cursor.getString(cursor.getColumnIndex("numero")) + " - "+ cursor.getString(cursor.getColumnIndex("sector")));
                }else{
                    row.put("numeroysector", cursor.getString(cursor.getColumnIndex("sector")));
                }
                row.put("responsable", "Responsable: " + cursor.getString(cursor.getColumnIndex("responsable")));
                row.put("lugar", "Lugar: " + cursor.getString(cursor.getColumnIndex("lugar")) +
                        "\nCondición insegura: " + cursor.getString(cursor.getColumnIndex("condicionInsegura")) +
                        "\nAcción: " + cursor.getString(cursor.getColumnIndex("condicionInsegura")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return row;
    }

    public JSONArray getUACSinSincronizar() {
        JSONArray rows = new JSONArray();
        String sql = "SELECT * FROM uac WHERE syncro='0'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                try{
                    JSONObject row = new JSONObject();
                    row.put("id", cursor.getInt(cursor.getColumnIndex("id")));
                    row.put("estado", cursor.getString(cursor.getColumnIndex("estado")));
                    row.put("usuarioCreacion", cursor.getString(cursor.getColumnIndex("usuarioCreacion")));
                    row.put("lugar", cursor.getString(cursor.getColumnIndex("lugar")));
                    row.put("accion", cursor.getString(cursor.getColumnIndex("accion")));
                    row.put("responsable", cursor.getString(cursor.getColumnIndex("responsable")));
                    row.put("vencimiento", cursor.getString(cursor.getColumnIndex("vencimiento")));
                    row.put("riesgo", cursor.getString(cursor.getColumnIndex("riesgo")));
                    row.put("sector", cursor.getString(cursor.getColumnIndex("sector")));
                    row.put("condicionInsegura", cursor.getString(cursor.getColumnIndex("condicionInsegura")));
                    row.put("fechaHoraCreacion", cursor.getString(cursor.getColumnIndex("fechaHoraCreacion")));
                    row.put("comentarioCierre", cursor.getString(cursor.getColumnIndex("comentarioCierre")));
                    row.put("fechaHoraCierre", cursor.getString(cursor.getColumnIndex("fechaHoraCierre")));
                    row.put("usuarioCierre", cursor.getString(cursor.getColumnIndex("usuarioCierre")));
                    row.put("imagenUAC", cursor.getString(cursor.getColumnIndex("imagenUAC")));
                    row.put("imagenUACCIERRE", cursor.getString(cursor.getColumnIndex("imagenUACCIERRE")));
                    row.put("site", cursor.getString(cursor.getColumnIndex("site")));
                    row.put("numero", cursor.getString(cursor.getColumnIndex("numero")));
                    row.put("fecha", cursor.getString(cursor.getColumnIndex("fecha")));
                    row.put("tipo", cursor.getString(cursor.getColumnIndex("tipo")));
                    row.put("Lider_recorrida", cursor.getString(cursor.getColumnIndex("Lider_recorrida")));
                    row.put("Participantes", cursor.getString(cursor.getColumnIndex("Participantes")));
                    row.put("Categoria", cursor.getString(cursor.getColumnIndex("Categoria")));
                    rows.put(row);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rows;
    }

    //public Boolean setUACSincronizados(JSONArray array) {
    public Boolean setUACSincronizados(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;
        //for (int i = 0; i < array.length(); i++) {
            try{
                //JSONObject c = array.getJSONObject(i);
                //Integer id = c.getInt("id");
                ContentValues values = new ContentValues();
                values.put("syncro", 1);
                String where = "id = ?";
                String[] whereArgs = {id };
                success = db.update("uac", values, where, whereArgs) > 0;
            } catch (Exception e) {
                success = false;
            }
        //}
        db.close();
        return success;
    }
}