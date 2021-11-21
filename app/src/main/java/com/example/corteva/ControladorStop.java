package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorStop extends AdminSQLiteOpenHelper {
    public ControladorStop(Context context){
        super(context);
    }

    public boolean Insert(String observaciones, String interacciones,String lugar, String epp1, String epp2, String epp3,
                          String montacargas1,String montacargas2,String montacargas3,String montacargas4,
                          String montacargas5,String montacargas6, String vehiculo1, String vehiculo2, String vehiculo3,
                          String vehiculo4, String posiciones1, String posiciones2, String riesgos1, String riesgos4,
                          String altura1, String altura2, String altura4,String aislacionEnergia3, String aislacionEnergia4, String espacioConfinado1,
                          String otros1, String otros2, String otros3, String tipoEmpleado, String spinnerPersonas,
                          String spinnerTarea, String spinnerSector, String site, String areaTrabajoLimpia, String candado, String todosLosEquipos,
                          String riesgoCaida, String HerramientasAdecuadas, String usoHerramientas, String HerramientasEstado, String usuario){

        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("HerramientasAdecuadas", HerramientasAdecuadas);
        values.put("usoHerramientas", usoHerramientas);
        values.put("HerramientasEstado", HerramientasEstado);
        values.put("observaciones", observaciones);
        values.put("interacciones", interacciones);
        values.put("lugar", lugar);
        values.put("tipoEmpleado", tipoEmpleado);
        values.put("cantPersonas", spinnerPersonas);
        values.put("EPP1", epp1);
        values.put("EPP2", epp2);
        values.put("EPP3", epp3);
        values.put("Montacargas1", montacargas1);
        values.put("Montacargas2", montacargas2);
        values.put("Montacargas3", montacargas3);
        values.put("Montacargas4", montacargas4);
        values.put("Montacargas5", montacargas5);
        values.put("Montacargas6", montacargas6);
        values.put("Vehiculo1", vehiculo1);
        values.put("Vehiculo2", vehiculo2);
        values.put("Vehiculo3", vehiculo3);
        values.put("Vehiculo4", vehiculo4);
        values.put("Posiciones1", posiciones1);
        values.put("Posiciones2", posiciones2);
        values.put("riesgoCaida", riesgoCaida);
        values.put("Riesgos1", riesgos1);
        values.put("Riesgos4", riesgos4);
        values.put("Altura1", altura1);
        values.put("Altura2", altura2);
        values.put("Altura4", altura4);
        values.put("candados", candado);
        values.put("todosLosEquipos", todosLosEquipos);
        values.put("aislacionEnergia3", aislacionEnergia3);
        values.put("aislacionEnergia4", aislacionEnergia4);
        values.put("espacioConfinado1", espacioConfinado1);
        values.put("otros1", otros1);
        values.put("otros2", otros2);
        values.put("otros3", otros3);
        values.put("areaTrabajoLimpia", areaTrabajoLimpia);
        values.put("tarea", spinnerTarea);
        values.put("Sector", spinnerSector);
        values.put("site", site);
        values.put("usuario",usuario);
        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("fechaHora", fh);
        values.put("syncro", 0);

        boolean resultado=BaseDeDatos.insert("formulariostop", null, values)>0;
        BaseDeDatos.close();
        return resultado;
    }

    public JSONArray getStopPendientes() {
        JSONArray rows = new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            String sql = "SELECT * FROM formulariostop WHERE syncro='0'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    try{
                        JSONObject row = new JSONObject();
                        row.put("id", cursor.getString(cursor.getColumnIndex("id")));
                        row.put("site", cursor.getString(cursor.getColumnIndex("site")));
                        row.put("fechaHora", cursor.getString(cursor.getColumnIndex("fechaHora")));
                        row.put("Sector", cursor.getString(cursor.getColumnIndex("Sector")));
                        row.put("lugar", cursor.getString(cursor.getColumnIndex("lugar")));
                        row.put("tipoEmpleado", cursor.getString(cursor.getColumnIndex("tipoEmpleado")));
                        row.put("cantPersonas", cursor.getString(cursor.getColumnIndex("cantPersonas")));
                        row.put("tarea", cursor.getString(cursor.getColumnIndex("tarea")));
                        row.put("EPP1", cursor.getString(cursor.getColumnIndex("EPP1")));
                        row.put("EPP2", cursor.getString(cursor.getColumnIndex("EPP2")));
                        row.put("EPP3", cursor.getString(cursor.getColumnIndex("EPP3")));
                        row.put("Montacargas1", cursor.getString(cursor.getColumnIndex("Montacargas1")));
                        row.put("Montacargas2", cursor.getString(cursor.getColumnIndex("Montacargas2")));
                        row.put("Montacargas3", cursor.getString(cursor.getColumnIndex("Montacargas3")));
                        row.put("Montacargas4", cursor.getString(cursor.getColumnIndex("Montacargas4")));
                        row.put("Montacargas5", cursor.getString(cursor.getColumnIndex("Montacargas5")));
                        row.put("Montacargas6", cursor.getString(cursor.getColumnIndex("Montacargas6")));
                        row.put("Vehiculo1", cursor.getString(cursor.getColumnIndex("Vehiculo1")));
                        row.put("Vehiculo2", cursor.getString(cursor.getColumnIndex("Vehiculo2")));
                        row.put("Vehiculo3", cursor.getString(cursor.getColumnIndex("Vehiculo3")));
                        row.put("Vehiculo4", cursor.getString(cursor.getColumnIndex("Vehiculo4")));

                        row.put("Posiciones1", cursor.getString(cursor.getColumnIndex("Posiciones1")));
                        row.put("Posiciones2", cursor.getString(cursor.getColumnIndex("Posiciones2")));
                        row.put("HerramientasAdecuadas", cursor.getString(cursor.getColumnIndex("HerramientasAdecuadas")));
                        row.put("usoHerramientas", cursor.getString(cursor.getColumnIndex("usoHerramientas")));
                        row.put("HerramientasEstado", cursor.getString(cursor.getColumnIndex("HerramientasEstado")));
                        row.put("riesgoCaida", cursor.getString(cursor.getColumnIndex("riesgoCaida")));
                        row.put("Altura1", cursor.getString(cursor.getColumnIndex("Altura1")));
                        row.put("Altura2", cursor.getString(cursor.getColumnIndex("Altura2")));
                        row.put("Altura4", cursor.getString(cursor.getColumnIndex("Altura4")));
                        row.put("Riesgos1", cursor.getString(cursor.getColumnIndex("Riesgos1")));
                        row.put("Riesgos4", cursor.getString(cursor.getColumnIndex("Riesgos4")));
                        row.put("candados", cursor.getString(cursor.getColumnIndex("candados")));
                        row.put("todosLosEquipos", cursor.getString(cursor.getColumnIndex("todosLosEquipos")));
                        row.put("aislacionEnergia3", cursor.getString(cursor.getColumnIndex("aislacionEnergia3")));
                        row.put("aislacionEnergia4", cursor.getString(cursor.getColumnIndex("aislacionEnergia4")));
                        row.put("espacioConfinado1", cursor.getString(cursor.getColumnIndex("espacioConfinado1")));
                        row.put("otros1", cursor.getString(cursor.getColumnIndex("otros1")));
                        row.put("otros2", cursor.getString(cursor.getColumnIndex("otros2")));
                        row.put("otros3", cursor.getString(cursor.getColumnIndex("otros3")));
                        row.put("areaTrabajoLimpia", cursor.getString(cursor.getColumnIndex("areaTrabajoLimpia")));

                        row.put("observaciones", cursor.getString(cursor.getColumnIndex("observaciones")));
                        row.put("interacciones", cursor.getString(cursor.getColumnIndex("interacciones")));
                        row.put("usuario", cursor.getString(cursor.getColumnIndex("usuario")));
                        rows.put(row);
                    } catch (Exception e) {

                    }
                } while (cursor.moveToNext());
            }

            cursor.close();

        } catch (Exception e) {

        }
        db.close();
        return rows;
    }

    public  Boolean setStopPendientes(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;
        //for (int i = 0; i < array.length(); i++) {
            try{
                //JSONObject c = array.getJSONObject(i);
                //Integer id = c.getInt("id");
                ContentValues values = new ContentValues();
                values.put("syncro", 1);
                String where = "id = ?";
                String[] whereArgs = { id };
                success = db.update("formulariostop", values, where, whereArgs) > 0;
            } catch (Exception e) {
                success = false;
            }
        //}
        db.close();
        return success;
    }
}
