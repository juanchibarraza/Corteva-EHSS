package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ControladorGrupos extends AdminSQLiteOpenHelper {
    public ControladorGrupos(Context context) {
        super(context);
    }

    public boolean InsertOrUpdate(Integer idGrupo, String grupo, String Sector, Integer activo){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("idGrupo", idGrupo);
        values.put("grupo", grupo);
        values.put("Sector", Sector);
        values.put("Activo", activo);
        //Intento hacer un UPDATE si falla hago INSERT
        String where = "idGrupo = ?";
        String[] whereArgs = { Integer.toString(idGrupo) };
        boolean success = BaseDeDatos.update("grupos", values, where, whereArgs) > 0;
        if (!success){
            success = BaseDeDatos.insert("grupos",null,values)>0;
        }
        BaseDeDatos.close();
        return success;
    }

    public ArrayList<String> getGruposBySector(String sectorSeleccionado){
        ArrayList<String> listagrupos=new ArrayList<String>();
        SQLiteDatabase baseDeDatos=this.getReadableDatabase();
        String consulta="SELECT grupo FROM grupos WHERE (',' || Sector || ',') LIKE '%,"+sectorSeleccionado+",%' AND Activo=1 ORDER BY grupo ASC";
        Cursor cursor=baseDeDatos.rawQuery(consulta, null);
        if(cursor.moveToFirst()){
            do{
                listagrupos.add(cursor.getString(cursor.getColumnIndex("grupo")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        baseDeDatos.close();
        return listagrupos;
    }
}

