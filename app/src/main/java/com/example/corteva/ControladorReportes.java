package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorReportes extends AdminSQLiteOpenHelper {
    public ControladorReportes (Context context){
        super(context);
    }

    public boolean Insert(String lugar,String nombreOperario, String funcionOperario, String describirOcurrido, String lesion,
                          String parteCuerpoAfectada, String personalInvolucrado, String spinnerResponsableSeleccion,
                          String spinnerSectorSeleccion, String imagenPath, String getAccionesReportesOpciones, String potencial,
                          String site, String clasificacion, String empresa, String usuario, String fechahoraincidente){

        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("lugar",lugar);
        values.put("nombreOperario", nombreOperario);
        values.put("funcionOperario", funcionOperario);
        values.put("describirOcurrido", describirOcurrido);
        values.put("lesion", lesion);
        values.put("parteCuerpoAfectada", parteCuerpoAfectada);
        values.put("personalInvolucrado", personalInvolucrado);
        values.put("responsable", spinnerResponsableSeleccion);
        values.put("sector", spinnerSectorSeleccion);
        values.put("accionesChild", getAccionesReportesOpciones);
        values.put("potencial", potencial);
        values.put("site", site);
        values.put("clasificacion", clasificacion);
        values.put("empresa", empresa);
        values.put("usuario",usuario);
        values.put("fechahoraincidente",fechahoraincidente);
        //String imagenString= image2String(imagen);
        values.put("imagen",imagenPath);

        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("fechaHora", fh);
        values.put("syncro", 0);

        boolean resultado=BaseDeDatos.insert("reportes",null,values)>0;
        BaseDeDatos.close();
        return resultado;

    }

    public JSONArray getReportesPendientes() {
        JSONArray rows = new JSONArray();
        String sql = "SELECT * FROM reportes WHERE syncro='0'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                try{
                    JSONObject row = new JSONObject();

                    row.put("id", cursor.getInt(cursor.getColumnIndex("id")));
                    row.put("lugar", cursor.getString(cursor.getColumnIndex("lugar")));
                    row.put("nombreOperario", cursor.getString(cursor.getColumnIndex("nombreOperario")));
                    row.put("funcionOperario", cursor.getString(cursor.getColumnIndex("funcionOperario")));
                    row.put("describirOcurrido", cursor.getString(cursor.getColumnIndex("describirOcurrido")));
                    row.put("lesion", cursor.getString(cursor.getColumnIndex("lesion")));
                    row.put("parteCuerpoAfectada", cursor.getString(cursor.getColumnIndex("parteCuerpoAfectada")));
                    row.put("personalInvolucrado", cursor.getString(cursor.getColumnIndex("personalInvolucrado")));
                    row.put("responsable", cursor.getString(cursor.getColumnIndex("responsable")));
                    row.put("sector", cursor.getString(cursor.getColumnIndex("sector")));
                    row.put("fechaHora", cursor.getString(cursor.getColumnIndex("fechaHora")));
                    row.put("imagen", cursor.getString(cursor.getColumnIndex("imagen")));
                    row.put("accionesChild", cursor.getString(cursor.getColumnIndex("accionesChild")));
                    row.put("potencial", cursor.getString(cursor.getColumnIndex("potencial")));
                    row.put("site", cursor.getString(cursor.getColumnIndex("site")));
                    row.put("clasificacion", cursor.getString(cursor.getColumnIndex("clasificacion")));
                    row.put("empresa", cursor.getString(cursor.getColumnIndex("empresa")));
                    row.put("usuario", cursor.getString(cursor.getColumnIndex("usuario")));
                    row.put("fechahoraincidente", cursor.getString(cursor.getColumnIndex("fechahoraincidente")));
                    rows.put(row);
                } catch (Exception e) {
                    Log.e("getReportesPendientes", e.getMessage().toString());
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rows;
    }

    //public Boolean setReportesPendientes(JSONArray array) {
    public Boolean setReportesPendientes(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;
        //for (int i = 0; i < array.length(); i++) {
            try{
                //JSONObject c = array.getJSONObject(i);
                //Integer id = c.getInt("id");
                ContentValues values = new ContentValues();
                values.put("syncro", 1);
                String where = "id = ?";
                String[] whereArgs = { id };
                success = db.update("reportes", values, where, whereArgs) > 0;
            } catch (Exception e) {
                success = false;
            }
        //}
        db.close();
        return success;
    }
}