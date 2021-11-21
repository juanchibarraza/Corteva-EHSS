package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ControladorSites extends AdminSQLiteOpenHelper {
    public ControladorSites(Context context) {
        super(context);
    }

    public boolean InsertOrUpdate(Integer idSite, String Site, Integer activo){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("idSite", idSite);
        values.put("Site", Site);
        values.put("Activo", activo);
        //Intento hacer un UPDATE si falla hago INSERT
        String where = "idSite = ?";
        String[] whereArgs = { Integer.toString(idSite) };
        boolean success = BaseDeDatos.update("sites", values, where, whereArgs) > 0;
        if (!success){
            success = BaseDeDatos.insert("sites", null, values) > 0;
        }
        BaseDeDatos.close();
        return success;
    }

    public ArrayList<String> getSites(){
        ArrayList<String> listaSite=new ArrayList<String>();
        SQLiteDatabase baseDeDatos=this.getReadableDatabase();
        String consulta="SELECT Site FROM sites WHERE Activo=1 ORDER BY Site ASC";
        Cursor cursor=baseDeDatos.rawQuery(consulta, null);
        if(cursor.moveToFirst()){
            listaSite.add("Seleccione Site");
            do{
                listaSite.add(cursor.getString(cursor.getColumnIndex("Site")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        baseDeDatos.close();
        return listaSite;
    }
}
