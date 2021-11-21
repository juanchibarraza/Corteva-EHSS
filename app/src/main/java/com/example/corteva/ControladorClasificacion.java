package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ControladorClasificacion extends AdminSQLiteOpenHelper {
    public ControladorClasificacion(Context context) {
        super(context);
    }

    public boolean InsertOrUpdate(Integer idClasificacion, String Clasificacion, Integer activo){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("idClasificacion", idClasificacion);
        values.put("Clasificacion", Clasificacion);
        values.put("Activo", activo);
        //Intento hacer un UPDATE si falla hago INSERT
        String where = "idClasificacion = ?";
        String[] whereArgs = { Integer.toString(idClasificacion) };
        boolean success = BaseDeDatos.update("clasificacion", values, where, whereArgs) > 0;
        if (!success){
            success = BaseDeDatos.insert("clasificacion", null, values) > 0;
        }
        BaseDeDatos.close();
        return success;
    }

    public ArrayList<String> getClasificacion(){
        ArrayList<String> listaClasificacion=new ArrayList<String>();
        SQLiteDatabase baseDeDatos=this.getReadableDatabase();
        String consulta="SELECT Clasificacion FROM clasificacion WHERE Activo=1 ORDER BY Clasificacion ASC";
        Cursor cursor=baseDeDatos.rawQuery(consulta, null);
        if(cursor.moveToFirst()){
            do{
                listaClasificacion.add(cursor.getString(cursor.getColumnIndex("Clasificacion")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        baseDeDatos.close();
        return listaClasificacion;
    }
}
