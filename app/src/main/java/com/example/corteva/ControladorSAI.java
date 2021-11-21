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

public class ControladorSAI extends AdminSQLiteOpenHelper{

    public ControladorSAI(Context context){

        super(context);
    }

    public boolean Insert (String Site, String fecha, String Hora_Inicio, String Hora_Fin, String Observadores,
                                   String Total_SAI, String Total_Empleados, String Total_Contratistas, String usuario){

        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("fechaHora", fh);
        values.put("Site", Site);
        values.put("fecha", fecha);
        values.put("Hora_Inicio", Hora_Inicio);
        values.put("Hora_Fin", Hora_Fin);
        values.put("Observadores", Observadores);
        values.put("Total_SAI", Total_SAI);
        values.put("Total_Empleados", Total_Empleados);
        values.put("Total_Contratistas", Total_Contratistas);
        values.put("usuario", usuario);
        values.put("syncro", 0);

        boolean resultado=BaseDeDatos.insert("ehss_sai",null,values)>0;
        BaseDeDatos.close();
        return resultado;

    }

    public int idSAI(){
        SQLiteDatabase BaseDeDatos = this.getReadableDatabase();
        String query = "SELECT max(id) FROM ehss_sai";
        Cursor cursor = BaseDeDatos.rawQuery(query, null);

        int id = 0;
        if (cursor.moveToFirst())
        {
            do
            {
                id = cursor.getInt(0);
            } while(cursor.moveToNext());
        }
        return id;
    }

    public boolean InsertSectores (String Sector, String Cantidad_Contratista, String Cantidad_Empleado,
                                           String Desvios, String Observaciones, String Total_SAI, String Total_Empleados,
                                           String Total_Contratistas){

        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        Integer idSAI = idSAI();

        values.put("idSAI", idSAI);
        values.put("Sector", Sector);
        values.put("Cantidad_Contratista", Cantidad_Contratista);
        values.put("Cantidad_Empleado", Cantidad_Empleado);
        values.put("Desvios", Desvios);
        values.put("Observaciones", Observaciones);
        values.put("Total_SAI", Total_SAI);
        values.put("Total_Empleados", Total_Empleados);
        values.put("Total_Contratistas", Total_Contratistas);

        boolean resultado=BaseDeDatos.insert("ehss_sai_sectores",null,values)>0;
        BaseDeDatos.close();
        return resultado;

    }

    public JSONArray getSAIPendientes() {
        JSONArray rows = new JSONArray();
        String sql = "SELECT * FROM ehss_sai WHERE syncro='0'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                try{
                    JSONObject row = new JSONObject();
                    row.put("id", cursor.getInt(cursor.getColumnIndex("id")));
                    row.put("fechaHora", cursor.getString(cursor.getColumnIndex("fechaHora")));
                    row.put("Site", cursor.getString(cursor.getColumnIndex("Site")));
                    row.put("fecha", cursor.getString(cursor.getColumnIndex("fecha")));
                    row.put("Hora_Inicio", cursor.getString(cursor.getColumnIndex("Hora_Inicio")));
                    row.put("Hora_Fin", cursor.getString(cursor.getColumnIndex("Hora_Fin")));
                    row.put("Observadores", cursor.getString(cursor.getColumnIndex("Observadores")));
                    row.put("Total_SAI", cursor.getString(cursor.getColumnIndex("Total_SAI")));
                    row.put("Total_Empleados", cursor.getString(cursor.getColumnIndex("Total_Empleados")));
                    row.put("Total_Contratistas", cursor.getString(cursor.getColumnIndex("Total_Contratistas")));
                    row.put("usuario", cursor.getString(cursor.getColumnIndex("usuario")));

                    /*agregar los datos de los sectores */
                    String sql2 = "SELECT * FROM ehss_sai_sectores WHERE idSAI='"+ cursor.getInt(cursor.getColumnIndex("id"))+"'";
                    Cursor cursor2 = db.rawQuery(sql2, null);
                    JSONArray sectores = new JSONArray();
                    if (cursor2.moveToFirst()) {
                        do {
                            try{
                                JSONObject sector = new JSONObject();
                                sector.put("Sector", cursor2.getString(cursor2.getColumnIndex("Sector")));
                                sector.put("Cantidad_Contratista", cursor2.getInt(cursor2.getColumnIndex("Cantidad_Contratista")));
                                sector.put("Cantidad_Empleado", cursor2.getInt(cursor2.getColumnIndex("Cantidad_Empleado")));
                                sector.put("Desvios", cursor2.getString(cursor2.getColumnIndex("Desvios")));
                                sector.put("Observaciones", cursor2.getString(cursor2.getColumnIndex("Observaciones")));
                                sector.put("Total_SAI", cursor2.getString(cursor2.getColumnIndex("Total_SAI")));
                                sector.put("Total_Empleados", cursor2.getString(cursor2.getColumnIndex("Total_Empleados")));
                                sector.put("Total_Contratistas", cursor2.getString(cursor2.getColumnIndex("Total_Contratistas")));
                                sectores.put(sector);
                            } catch (Exception e) {
                                Log.e("getSAIPendientes", e.getMessage().toString());
                            }
                        } while (cursor2.moveToNext());
                    }
                    cursor2.close();
                    row.put("sectores", sectores);
                    rows.put(row);
                } catch (Exception e) {
                    Log.e("getSAIPendientes", e.getMessage().toString());
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rows;
    }

    public Boolean setSAIPendientes(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;
        try{
            ContentValues values = new ContentValues();
            values.put("syncro", 1);
            String where = "id = ?";
            String[] whereArgs = { id };
            success = db.update("ehss_sai", values, where, whereArgs) > 0;
        } catch (Exception e) {
            success = false;
        }
        db.close();
        return success;
    }

}
