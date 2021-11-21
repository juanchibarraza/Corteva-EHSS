package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ControladorConfig extends AdminSQLiteOpenHelper {

    public ControladorConfig(Context context) {
        super(context);
    }

    public boolean deleteOldData() {

        SQLiteDatabase db = this.getWritableDatabase();
        //ELIMINO TEMPERATURAS VIEJAS Y YA SINCRONIZADAS
        String whereClause = "syncro = ? and fechahora < current_date";
        String[] whereArgs = { "1" };
        db.delete("temperaturas", whereClause, whereArgs);

        //ELIMINO SOLICITUDES VIEJAS Y YA SINCRONIZADAS
        whereClause = "syncro = ? and fecha_recepcion < date('now','-15 days')";
        boolean success = db.delete("solicitudes", whereClause, whereArgs) > 0;
        db.close();
        return success;
    }

    public boolean LimpiarBasedeDatos() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("clasificacion",null,null);
        db.delete("sai_desvios", null, null);
        db.delete("grupos",null,null);
        db.delete("tareas", null, null);
        db.delete("sectores",null,null);
        db.delete("responsables",null,null);
        db.delete("formulariostop",null,null);
        db.delete("momentosSeguridad",null,null);
        db.delete("reportes",null,null);
        db.delete("uac",null,null);
        db.delete("TopTen",null,null);
        db.delete("PruebasACampo",null,null);
        db.delete("ehss_sai", null, null);
        db.delete("ehss_sai_sectores", null, null);
        boolean success = db.delete("usuarios", null, null) > 0;
        db.close();
        return success;
    }

    public String GetSet_last_syncro() {

        SQLiteDatabase db = this.getWritableDatabase();
        String last = null;

        //Busco el valor actual de last_syncro
        String sql = "SELECT last_syncro FROM config WHERE id = '1'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            last = cursor.getString(cursor.getColumnIndex("last_syncro"));
        }
        cursor.close();
        //Actualizo el valor de last_syncro
        ContentValues values = new ContentValues();
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("last_syncro", currentDateTime);
        String where = "id = ?";
        String[] whereArgs = { "1" };
        db.update("config", values, where, whereArgs);

        db.close();
        //Devuelvo el valor viejo
        return last;
    }

    public Boolean Set_miscargas(Integer bbpanual, Integer bbpmes, Integer bbpultimostres, Integer momentosanual, Integer momentosmes,
                                Integer momentosultimostres, Integer pruebasanual, Integer pruebasmes, Integer pruebasultimostres, Integer reportesanual,
                                Integer reportesmes, Integer reportesultimostres, Integer toptenanual, Integer toptenmes, Integer toptenultimostres,
                                Integer uacanual, Integer uacmes, Integer uacultimostres) {
        boolean success = false;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            //Actualizo los valores
            ContentValues values = new ContentValues();
            values.put("bbpanual", bbpanual);
            values.put("bbpmes", bbpmes);
            values.put("bbpultimostres", bbpultimostres);
            values.put("momentosanual", momentosanual);
            values.put("momentosmes", momentosmes);
            values.put("momentosultimostres", momentosultimostres);
            values.put("pruebasanual", pruebasanual);
            values.put("pruebasmes", pruebasmes);
            values.put("pruebasultimostres", pruebasultimostres);
            values.put("reportesanual", reportesanual);
            values.put("reportesmes", reportesmes);
            values.put("reportesultimostres", reportesultimostres);
            values.put("toptenanual", toptenanual);
            values.put("toptenmes", toptenmes);
            values.put("toptenultimostres", toptenultimostres);
            values.put("uacanual", uacanual);
            values.put("uacmes", uacmes);
            values.put("uacultimostres", uacultimostres);
            String where = "id = ?";
            String[] whereArgs = { "1" };
            success = db.update("config", values, where, whereArgs) > 0;
            db.close();
        } catch (Exception e) {
            success = false;
        }
        return success;
    }

    public Boolean Set_miscargasDetalle(String tipo, String fechaHora, String datos, String usuario) {
        boolean success = false;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            //Actualizo los valores
            ContentValues values = new ContentValues();
            values.put("tipo", tipo);
            values.put("fechaHora", fechaHora);
            values.put("datos", datos);
            values.put("usuario", usuario);
            success = db.insertWithOnConflict("miscargasdetalle", null, values, SQLiteDatabase.CONFLICT_IGNORE) > 0;
            db.close();
        } catch (Exception e) {
            success = false;
        }
        return success;
    }

    public ArrayList<BarEntry> Get_MisCargas(String fecha, String tipo){

        ArrayList<BarEntry> datos = new ArrayList<>();
        try{
            String sql = "SELECT * FROM config WHERE id=1";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if(cursor.moveToFirst()){
                do{
                    if(fecha.equals("Éste mes")) {
                        if(tipo.equals("Todos")) {
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("bbpmes"))));
                            datos.add(new BarEntry(1f, cursor.getInt(cursor.getColumnIndex("momentosmes"))));
                            datos.add(new BarEntry(2f, cursor.getInt(cursor.getColumnIndex("pruebasmes"))));
                            datos.add(new BarEntry(3f, cursor.getInt(cursor.getColumnIndex("reportesmes"))));
                            datos.add(new BarEntry(4f, cursor.getInt(cursor.getColumnIndex("toptenmes"))));
                            datos.add(new BarEntry(5f, cursor.getInt(cursor.getColumnIndex("uacmes"))));
                        }else if(tipo.equals("SODA")){
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("bbpmes"))));
                        }else if(tipo.equals("Pausas de Seguridad")){
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("momentosmes"))));
                        }else if(tipo.equals("Pruebas a campo")){
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("pruebasmes"))));
                        }else if(tipo.equals("Reportes de Desvíos")) {
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("reportesmes"))));
                        }else if(tipo.equals("TopTen")) {
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("toptenmes"))));
                        }else{
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("uacmes"))));
                        }
                    }else if(fecha.equals("Últimos 3 meses")){
                        if(tipo.equals("Todos")) {
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("bbpultimostres"))));
                            datos.add(new BarEntry(1f, cursor.getInt(cursor.getColumnIndex("momentosultimostres"))));
                            datos.add(new BarEntry(2f, cursor.getInt(cursor.getColumnIndex("pruebasultimostres"))));
                            datos.add(new BarEntry(3f, cursor.getInt(cursor.getColumnIndex("reportesultimostres"))));
                            datos.add(new BarEntry(4f, cursor.getInt(cursor.getColumnIndex("toptenultimostres"))));
                            datos.add(new BarEntry(5f, cursor.getInt(cursor.getColumnIndex("uacultimostres"))));
                        }else if(tipo.equals("SODA")){
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("bbpultimostres"))));
                        }else if(tipo.equals("Pausas de Seguridad")){
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("Pausasultimostres"))));
                        }else if(tipo.equals("Pruebas a campo")){
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("pruebasultimostres"))));
                        }else if(tipo.equals("Reportes de Desvíos")) {
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("reportesultimostres"))));
                        }else if(tipo.equals("TopTen")) {
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("toptenultimostres"))));
                        }else{
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("uacultimostres"))));
                        }
                    }else{
                        if(tipo.equals("Todos")) {
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("bbpanual"))));
                            datos.add(new BarEntry(1f, cursor.getInt(cursor.getColumnIndex("momentosanual"))));
                            datos.add(new BarEntry(2f, cursor.getInt(cursor.getColumnIndex("pruebasanual"))));
                            datos.add(new BarEntry(3f, cursor.getInt(cursor.getColumnIndex("reportesanual"))));
                            datos.add(new BarEntry(4f, cursor.getInt(cursor.getColumnIndex("toptenanual"))));
                            datos.add(new BarEntry(5f, cursor.getInt(cursor.getColumnIndex("uacanual"))));
                        }else if(tipo.equals("SODA")){
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("bbpanual"))));
                        }else if(tipo.equals("Pausas de Seguridad")){
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("momentosanual"))));
                        }else if(tipo.equals("Pruebas a campo")){
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("pruebasanual"))));
                        }else if(tipo.equals("Reportes de Desvíos")) {
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("reportesanual"))));
                        }else if(tipo.equals("TopTen")) {
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("toptenanual"))));
                        }else{
                            datos.add(new BarEntry(0f, cursor.getInt(cursor.getColumnIndex("uacanual"))));
                        }
                    }
                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){

        }
        return datos;
    }

    public ArrayList<HashMap<String, String>> Get_MisCargasDetalle(String fecha, String tipo, String usuario){

        ArrayList<HashMap<String, String>> rows = new ArrayList<>();
        try{
            String sql = "SELECT * FROM miscargasdetalle WHERE usuario='" + usuario + "'";
            if(!tipo.equals("Todos")) {
                sql += " AND Tipo ='"+ tipo +"'";
            }
            if(fecha.equals("Éste mes")) {
                sql += " AND strftime('%Y',fechaHora) = strftime('%Y',date('now')) AND strftime('%m',fechaHora) = strftime('%m',date('now'))";
            }else if(fecha.equals("Últimos 3 meses")) {
                sql += " AND fechaHora>= date('now','start of month','-2 months')";
            }else{
                sql += " AND strftime('%Y',fechaHora) = strftime('%Y',date('now'))";
            }
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if(cursor.moveToFirst()){
                do{
                    HashMap<String, String> row = new HashMap<>();
                    row.put("tipo", cursor.getString(cursor.getColumnIndex("tipo")));
                    row.put("fechahora", cursor.getString(cursor.getColumnIndex("fechahora")));
                    row.put("datos", cursor.getString(cursor.getColumnIndex("datos")));
                    rows.add(row);
                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){

        }
        return rows;
    }

}