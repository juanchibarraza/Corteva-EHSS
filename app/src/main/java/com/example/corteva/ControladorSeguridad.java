package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorSeguridad extends AdminSQLiteOpenHelper {
    public ControladorSeguridad (Context context){
        super(context);
    }

    public boolean Insert(String comentario, String tema, String spinnerSector, String spinnerResponsable, String tipo, String grupo, String imagePath,
                          String filePath, String site, String usuario, String fecha){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("comentario",comentario);
        values.put("tema", tema);
        values.put("sector",spinnerSector);
        values.put("responsable", spinnerResponsable);
        values.put("tipo",tipo);
        values.put("grupo", grupo);
        values.put("imagen",imagePath);
        values.put("filesPath", filePath);
        values.put("site", site);
        values.put("usuario",usuario);
        values.put("fecha",fecha);
        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("fechaHora", fh);
        values.put("syncro", 0);

        boolean resultado=BaseDeDatos.insert("momentosSeguridad",null,values)>0;
        BaseDeDatos.close();
        return resultado;

    }

    public JSONArray getSeguridadPendientes() {
        JSONArray rows = new JSONArray();
        String sql = "SELECT * FROM momentosSeguridad WHERE syncro='0'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                try{
                    JSONObject row = new JSONObject();
                    row.put("id", cursor.getInt(cursor.getColumnIndex("id")));
                    row.put("site", cursor.getString(cursor.getColumnIndex("site")));
                    row.put("comentario", cursor.getString(cursor.getColumnIndex("comentario")));
                    row.put("tema", cursor.getString(cursor.getColumnIndex("tema")));
                    row.put("responsable", cursor.getString(cursor.getColumnIndex("responsable")));
                    row.put("sector", cursor.getString(cursor.getColumnIndex("sector")));
                    row.put("tipo", cursor.getString(cursor.getColumnIndex("tipo")));
                    row.put("grupo", cursor.getString(cursor.getColumnIndex("grupo")));
                    row.put("fechaHora", cursor.getString(cursor.getColumnIndex("fechaHora")));
                    row.put("imagen",cursor.getString(cursor.getColumnIndex("imagen")));
                    row.put("filesPath",cursor.getString(cursor.getColumnIndex("filesPath")));
                    row.put("usuario", cursor.getString(cursor.getColumnIndex("usuario")));
                    row.put("fecha", cursor.getString(cursor.getColumnIndex("fecha")));
                    rows.put(row);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rows;
    }

    //public Boolean setSeguridadPendientes(JSONArray array) {
    public Boolean setSeguridadPendientes(String id) {
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
                success = db.update("momentosSeguridad", values, where, whereArgs) > 0;
            } catch (Exception e) {
                success = false;
            }
        //}
        db.close();
        return success;
    }
}