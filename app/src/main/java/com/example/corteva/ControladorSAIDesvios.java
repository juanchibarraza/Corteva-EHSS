package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class ControladorSAIDesvios extends AdminSQLiteOpenHelper{

    public ControladorSAIDesvios(Context context) {
        super(context);
    }

    public boolean InsertOrUpdate(Integer idSai, String Desvio, String Severidad, Integer Activo){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("idSai", idSai);
        values.put("Desvio", Desvio);
        values.put("Severidad", Severidad);
        values.put("Activo", Activo);
        //Intento hacer un UPDATE si falla hago INSERT
        String where = "idSai = ?";
        String[] whereArgs = { Integer.toString(idSai) };
        boolean success = BaseDeDatos.update("sai_desvios", values, where, whereArgs) > 0;
        if (!success){
            success = BaseDeDatos.insert("sai_desvios", null, values) > 0;
        }
        BaseDeDatos.close();
        return success;
    }

    public ArrayList<String> getSAIDesvios(){
        ArrayList<String> listaSAIDesvios=new ArrayList<String>();
        SQLiteDatabase baseDeDatos=this.getReadableDatabase();
        String consulta="SELECT Desvio FROM sai_desvios WHERE Activo=1 ORDER BY Desvio ASC";
        Cursor cursor=baseDeDatos.rawQuery(consulta, null);
        if(cursor.moveToFirst()){
            listaSAIDesvios.add("Seleccione desvío");
            do{
                listaSAIDesvios.add(cursor.getString(cursor.getColumnIndex("Desvio")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        baseDeDatos.close();
        return listaSAIDesvios;
    }

    public Double GetSeveridad(String desvio) {

        SQLiteDatabase db = this.getWritableDatabase();
        Double severidad = 0.0;

        //Busco el valor actual de last_syncro
        String sql = "SELECT Severidad FROM sai_desvios WHERE Desvio = '"+desvio+"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            severidad = cursor.getDouble(cursor.getColumnIndex("Severidad"));
        }
        cursor.close();
        db.close();
        return severidad;
    }
}
