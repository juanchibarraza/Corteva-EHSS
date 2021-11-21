package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorComentarios extends AdminSQLiteOpenHelper {
    public ControladorComentarios(Context context) {
        super(context);
    }

    public boolean Insert(String site, String usuario, String comentario){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("site", site);
        values.put("usuario", usuario);
        values.put("comentario", comentario);
        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("fechaHora", fh);
        values.put("syncro", 0);
        boolean resultado = BaseDeDatos.insert("comentarios",null,values)>0;
        BaseDeDatos.close();
        return resultado;
    }

    public JSONArray getComentariosPendientes() {
        JSONArray rows = new JSONArray();
        String sql = "SELECT * FROM comentarios WHERE syncro='0'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                try{
                    JSONObject row = new JSONObject();
                    row.put("id", cursor.getString(cursor.getColumnIndex("id")));
                    row.put("fechaHora", cursor.getString(cursor.getColumnIndex("fechaHora")));
                    row.put("site",cursor.getString(cursor.getColumnIndex("Site")));
                    row.put("usuario",cursor.getString(cursor.getColumnIndex("Usuario")));
                    row.put("comentario",cursor.getString(cursor.getColumnIndex("Comentario")));
                            rows.put(row);
                } catch (Exception e) {
                    //Log.e("getComentariosPendiente", e.getMessage().toString());
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rows;
    }

    public Boolean setComentariosPendientes(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;
        try{
            ContentValues values = new ContentValues();
            values.put("syncro", 1);
            String where = "id = ?";
            String[] whereArgs = { id };
            success = db.update("comentarios", values, where, whereArgs) > 0;
        } catch (Exception e) {
            success = false;
        }
        db.close();
        return success;
    }
}

