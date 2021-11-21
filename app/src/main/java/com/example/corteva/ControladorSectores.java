package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ControladorSectores extends AdminSQLiteOpenHelper {
    public ControladorSectores(Context context) {
        super(context);
    }

    public boolean InsertOrUpdate(Integer idSector, String site, String nombre, Integer activo){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("idSector", idSector);
        values.put("site", site);
        values.put("nombre", nombre);
        values.put("Activo", activo);
        //Intento hacer un UPDATE si falla hago INSERT
        String where = "idSector = ?";
        String[] whereArgs = { Integer.toString(idSector) };
        boolean success = BaseDeDatos.update("sectores", values, where, whereArgs) > 0;
        if (!success){
            success = BaseDeDatos.insert("sectores", null, values) > 0;
        }
        BaseDeDatos.close();
        return success;
    }

    public ArrayList<String> getSectoresBySite(String site){
        ArrayList<String> listaSectores=new ArrayList<String>();
        SQLiteDatabase baseDeDatos=this.getReadableDatabase();
        String consulta="SELECT nombre FROM sectores WHERE site='"+site+"' AND Activo=1 ORDER BY nombre ASC";
        Cursor cursor=baseDeDatos.rawQuery(consulta, null);
        if(cursor.moveToFirst()){
            listaSectores.add("Seleccione Sector");
            do{
                listaSectores.add(cursor.getString(cursor.getColumnIndex("nombre")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        baseDeDatos.close();
        return listaSectores;
    }
}