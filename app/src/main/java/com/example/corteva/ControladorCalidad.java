package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class ControladorCalidad extends AdminSQLiteOpenHelper {
    public ControladorCalidad(Context context) {
        super(context);
    }

    public boolean Insert(String Lugar, String Fecha_deteccion, String Fecha_ocurrencia, String Procedencia,
                                  String Impacto, String Observaciones, String Evidencia, String Emisores, String Accion_inmediata, String usuario){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Lugar", Lugar);
        String Fecha_carga = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("Fecha_carga", Fecha_carga);
        values.put("Fecha_deteccion", Fecha_deteccion);
        values.put("Fecha_ocurrencia", Fecha_ocurrencia);
        values.put("Procedencia", Procedencia);
        values.put("Impacto", Impacto);
        values.put("Observaciones", Observaciones);
        values.put("Evidencia", Evidencia);
        values.put("Emisores", Emisores);
        values.put("Accion_inmediata", Accion_inmediata);
        values.put("usuario", usuario);
        values.put("syncro", 0);

        boolean resultado=BaseDeDatos.insert("calidad_nc",null,values)>0;
        BaseDeDatos.close();
        return resultado;
    }

    public JSONArray getCalidad() {
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

}