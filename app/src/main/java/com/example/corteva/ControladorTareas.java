package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ControladorTareas extends AdminSQLiteOpenHelper {
    public ControladorTareas(Context context) {
        super(context);
    }

    public boolean InsertOrUpdate(Integer idTarea, String nombreTarea, Integer activo){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("idTarea", idTarea);
        values.put("nombreTarea", nombreTarea);
        values.put("Activo", activo);
        //Intento hacer un UPDATE si falla hago INSERT
        String where = "idTarea = ?";
        String[] whereArgs = { Integer.toString(idTarea) };
        boolean success = BaseDeDatos.update("tareas", values, where, whereArgs) > 0;
        if (!success){
            success = BaseDeDatos.insert("tareas", null, values) > 0;
        }
        BaseDeDatos.close();
        return success;
    }

    public ArrayList<String> getTareas(){
        ArrayList<String> listaTareas=new ArrayList<String>();
        SQLiteDatabase baseDeDatos=this.getReadableDatabase();
        String consulta="SELECT nombreTarea FROM tareas WHERE Activo=1 ORDER BY nombreTarea ASC";
        Cursor cursor=baseDeDatos.rawQuery(consulta, null);
        if(cursor.moveToFirst()){
            do{
                listaTareas.add(cursor.getString(cursor.getColumnIndex("nombreTarea")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        baseDeDatos.close();
        return listaTareas;
    }
}
