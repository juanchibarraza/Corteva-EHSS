package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ControladorResponsables extends AdminSQLiteOpenHelper {
    public ControladorResponsables(Context context) {
        super(context);
    }

    public boolean InsertOrUpdate(Integer idResponsable, String nombre, String Sector, Integer activo){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("idResponsable", idResponsable);
        values.put("nombre", nombre);
        values.put("Sector", Sector);
        values.put("Activo", activo);
        //Intento hacer un UPDATE si falla hago INSERT
        String where = "idResponsable = ?";
        String[] whereArgs = { Integer.toString(idResponsable) };
        boolean success = BaseDeDatos.update("responsables", values, where, whereArgs) > 0;
        if (!success){
            success = BaseDeDatos.insert("responsables",null,values)>0;
        }
        BaseDeDatos.close();
        return success;
    }

    public ArrayList<String> getResponsablesBySite(String siteSeleccionado){
        ArrayList<String> listaResponsables=new ArrayList<String>();
        HashSet hs = new HashSet();
        SQLiteDatabase baseDeDatos=this.getReadableDatabase();
        String consulta="SELECT nombre FROM sectores WHERE site='" + siteSeleccionado + "' and Activo=1";
        Cursor cursor=baseDeDatos.rawQuery(consulta, null);
        if(cursor.moveToFirst()){
            do{
                String consulta2="SELECT nombre FROM responsables WHERE (',' || Sector || ',') LIKE '%,"+cursor.getString(cursor.getColumnIndex("nombre"))+",%' AND Activo=1 ORDER BY nombre ASC";
                Cursor cursor2=baseDeDatos.rawQuery(consulta2, null);
                if(cursor2.moveToFirst()){
                    listaResponsables.add("Seleccione");
                    do{
                        listaResponsables.add(cursor2.getString(cursor.getColumnIndex("nombre")));
                    }while(cursor2.moveToNext());
                }
                cursor2.close();
            }while(cursor.moveToNext());
        }
        cursor.close();
        baseDeDatos.close();

        hs.addAll(listaResponsables); // demoArrayList= name of arrayList from which u want to remove duplicates
        listaResponsables.clear();
        listaResponsables.addAll(hs);
        Collections.sort(listaResponsables);
        return listaResponsables;
    }

    public ArrayList<String> getResponsablesBySector(String sectorSeleccionado){
        ArrayList<String> listaResponsables=new ArrayList<String>();
        SQLiteDatabase baseDeDatos=this.getReadableDatabase();
        String consulta="SELECT nombre FROM responsables WHERE (',' || Sector || ',') LIKE '%,"+sectorSeleccionado+",%' AND Activo=1 ORDER BY nombre ASC";
        Cursor cursor=baseDeDatos.rawQuery(consulta, null);
        if(cursor.moveToFirst()){
            listaResponsables.add("Seleccione");
            do{
                listaResponsables.add(cursor.getString(cursor.getColumnIndex("nombre")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        baseDeDatos.close();
        return listaResponsables;
    }

    public ArrayList<String> getResponsables(){
        ArrayList<String> listaResponsables=new ArrayList<String>();
        SQLiteDatabase baseDeDatos=this.getReadableDatabase();
        String consulta="SELECT nombre FROM responsables WHERE Activo=1 ORDER BY nombre ASC";
        Cursor cursor=baseDeDatos.rawQuery(consulta, null);
        if(cursor.moveToFirst()){
            listaResponsables.add("Seleccione");
            do{
                listaResponsables.add(cursor.getString(cursor.getColumnIndex("nombre")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        baseDeDatos.close();
        return listaResponsables;
    }
}