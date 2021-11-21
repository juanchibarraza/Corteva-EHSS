package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorSODA extends AdminSQLiteOpenHelper {

    public  ControladorSODA(Context context){
        super(context);
    }

    public boolean Insert (String site, String area, String agregarProteccion, String cambioPosicion, String reacomodaTrabajo,
                           String dejarTrabajar, String colocanTierras, String colocanBloqueos, String cabeza, String ojosyCara,
                           String oidos, String aparatoRespiratorio, String brazosyManos, String tronco, String piernasyPies, String golpearContraObjetos,
                           String serGolpeadoContraObjetos, String atrapado, String caidas, String contactoTemperaturas, String contactoElectricidad,
                           String inhalacion, String absorcion, String ingestion, String sobreEsfuerzo, String movimientoRepetitivos, String posicionesIncomodas,
                           String herramientasInadecuadas, String empleoIncorrecto, String empleoInseguro, String procedimientosInadecuados,
                           String procedimientosDesconocidos, String procedimientoNoCumplen, String localSucio, String localDesorganizado,
                           String localFugas, String dispocisionInadecuada, String caminaSinCelular, String sendasPeatonales, String pasamanos,
                           String etActosSeguros, String etActosInseguro, String Vehiculo, String Montacargas1, String Montacargas2,
                           String Montacargas3, String etFechaSODA, String sector_empleado, String usuario){
        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("site",site);
        values.put("area",area);
        values.put("agregarProteccion",agregarProteccion);
        values.put("cambioPosicion",cambioPosicion);
        values.put("reacomodaTrabajo",reacomodaTrabajo);
        values.put("dejarTrabajar",dejarTrabajar);
        values.put("colocanTierras",colocanTierras);
        values.put("colocanBloqueos",colocanBloqueos);
        values.put("cabeza",cabeza);
        values.put("ojosyCara",ojosyCara);
        values.put("oidos",oidos);
        values.put("aparatoRespiratorio",aparatoRespiratorio);
        values.put("brazosyManos",brazosyManos);
        values.put("tronco",tronco);
        values.put("piernasyPies",piernasyPies);
        values.put("golpearContraObjetos",golpearContraObjetos);
        values.put("serGolpeadoContraObjetos",serGolpeadoContraObjetos);
        values.put("atrapado",atrapado);
        values.put("caidas",caidas);
        values.put("contactoTemperaturas",contactoTemperaturas);
        values.put("contactoElectricidad",contactoElectricidad);
        values.put("inhalacion",inhalacion);
        values.put("absorcion",absorcion);
        values.put("ingestion",ingestion);
        values.put("sobreEsfuerzo",sobreEsfuerzo);
        values.put("movimientoRepetitivos",movimientoRepetitivos);
        values.put("posicionesIncomodas",posicionesIncomodas);
        values.put("herramientasInadecuadas",herramientasInadecuadas);
        values.put("empleoIncorrecto",empleoIncorrecto);
        values.put("empleoInseguro",empleoInseguro);
        values.put("procedimientosInadecuados",procedimientosInadecuados);
        values.put("procedimientosDesconocidos",procedimientosDesconocidos);
        values.put("procedimientoNoCumplen",procedimientoNoCumplen);
        values.put("localSucio",localSucio);
        values.put("localDesorganizado",localDesorganizado);
        values.put("localFugas",localFugas);
        values.put("dispocisionInadecuada",dispocisionInadecuada);
        values.put("caminaSinCelular",caminaSinCelular);
        values.put("sendasPeatonales",sendasPeatonales);
        values.put("pasamanos",pasamanos);
        values.put("etActosSeguros",etActosSeguros);
        values.put("etActosInseguros",etActosInseguro);
        values.put("vehiculo",Vehiculo);
        values.put("montacargas1",Montacargas1);
        values.put("montacargas2",Montacargas2);
        values.put("montacargas3",Montacargas3);
        values.put("etFechaSODA",etFechaSODA);
        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("etFechaCreacion",fh);
        values.put("sector_empleado_observado", sector_empleado);
        values.put("usuario",usuario);
        values.put("syncro",0);

        boolean resultado=BaseDeDatos.insert("formularioSODA", null, values)>0;
        BaseDeDatos.close();
        return resultado;

    }

    public JSONArray getSODAPendientes() {
        JSONArray rows = new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            String sql = "SELECT * FROM formularioSODA WHERE syncro='0'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    try{
                        //Cambias campos de STOP por los de SODA, preguntar si hago el metodo getSODAPerndientes()
                        //Preguntar si hago el metodo setSODAPendientes
                        JSONObject row = new JSONObject();
                        row.put("id", cursor.getString(cursor.getColumnIndex("id")));
                        row.put("site", cursor.getString(cursor.getColumnIndex("site")));
                        row.put("area", cursor.getString(cursor.getColumnIndex("area")));
                        row.put("agregarProteccion", cursor.getString(cursor.getColumnIndex("agregarProteccion")));
                        row.put("cambioPosicion", cursor.getString(cursor.getColumnIndex("cambioPosicion")));
                        row.put("reacomodaTrabajo", cursor.getString(cursor.getColumnIndex("reacomodaTrabajo")));
                        row.put("dejarTrabajar", cursor.getString(cursor.getColumnIndex("dejarTrabajar")));
                        row.put("colocanTierras", cursor.getString(cursor.getColumnIndex("colocanTierras")));
                        row.put("colocanBloqueos", cursor.getString(cursor.getColumnIndex("colocanBloqueos")));
                        row.put("cabeza", cursor.getString(cursor.getColumnIndex("cabeza")));
                        row.put("ojosyCara", cursor.getString(cursor.getColumnIndex("ojosyCara")));
                        row.put("oidos", cursor.getString(cursor.getColumnIndex("oidos")));
                        row.put("aparatoRespiratorio", cursor.getString(cursor.getColumnIndex("aparatoRespiratorio")));
                        row.put("brazosyManos", cursor.getString(cursor.getColumnIndex("brazosyManos")));
                        row.put("tronco", cursor.getString(cursor.getColumnIndex("tronco")));
                        row.put("piernasyPies", cursor.getString(cursor.getColumnIndex("piernasyPies")));
                        row.put("golpearContraObjetos", cursor.getString(cursor.getColumnIndex("golpearContraObjetos")));
                        row.put("serGolpeadoContraObjetos", cursor.getString(cursor.getColumnIndex("serGolpeadoContraObjetos")));
                        row.put("atrapado", cursor.getString(cursor.getColumnIndex("atrapado")));
                        row.put("caidas", cursor.getString(cursor.getColumnIndex("caidas")));
                        row.put("contactoTemperaturas", cursor.getString(cursor.getColumnIndex("contactoTemperaturas")));
                        row.put("contactoElectricidad", cursor.getString(cursor.getColumnIndex("contactoElectricidad")));
                        row.put("inhalacion", cursor.getString(cursor.getColumnIndex("inhalacion")));
                        row.put("absorcion", cursor.getString(cursor.getColumnIndex("absorcion")));
                        row.put("ingestion", cursor.getString(cursor.getColumnIndex("ingestion")));
                        row.put("sobreEsfuerzo", cursor.getString(cursor.getColumnIndex("sobreEsfuerzo")));
                        row.put("movimientoRepetitivos", cursor.getString(cursor.getColumnIndex("movimientoRepetitivos")));
                        row.put("posicionesIncomodas", cursor.getString(cursor.getColumnIndex("posicionesIncomodas")));
                        row.put("herramientasInadecuadas", cursor.getString(cursor.getColumnIndex("herramientasInadecuadas")));
                        row.put("empleoIncorrecto", cursor.getString(cursor.getColumnIndex("empleoIncorrecto")));
                        row.put("empleoInseguro", cursor.getString(cursor.getColumnIndex("empleoInseguro")));
                        row.put("procedimientosInadecuados", cursor.getString(cursor.getColumnIndex("procedimientosInadecuados")));
                        row.put("procedimientosDesconocidos", cursor.getString(cursor.getColumnIndex("procedimientosDesconocidos")));
                        row.put("procedimientoNoCumplen", cursor.getString(cursor.getColumnIndex("procedimientoNoCumplen")));
                        row.put("localSucio", cursor.getString(cursor.getColumnIndex("localSucio")));
                        row.put("localDesorganizado", cursor.getString(cursor.getColumnIndex("localDesorganizado")));
                        row.put("localFugas", cursor.getString(cursor.getColumnIndex("localFugas")));
                        row.put("dispocisionInadecuada", cursor.getString(cursor.getColumnIndex("dispocisionInadecuada")));
                        row.put("caminaSinCelular", cursor.getString(cursor.getColumnIndex("caminaSinCelular")));
                        row.put("sendasPeatonales", cursor.getString(cursor.getColumnIndex("sendasPeatonales")));
                        row.put("pasamanos", cursor.getString(cursor.getColumnIndex("pasamanos")));
                        row.put("etActosSeguros", cursor.getString(cursor.getColumnIndex("etActosSeguros")));
                        row.put("etActosInseguros", cursor.getString(cursor.getColumnIndex("etActosInseguros")));
                        row.put("vehiculo", cursor.getString(cursor.getColumnIndex("vehiculo")));
                        row.put("montacargas1", cursor.getString(cursor.getColumnIndex("montacargas1")));
                        row.put("montacargas2", cursor.getString(cursor.getColumnIndex("montacargas1")));
                        row.put("montacargas1", cursor.getString(cursor.getColumnIndex("montacargas1")));
                        row.put("etFechaSODA", cursor.getString(cursor.getColumnIndex("etFechaSODA")));
                        row.put("etFechaCreacion", cursor.getString(cursor.getColumnIndex("etFechaCreacion")));
                        row.put("sector_empleado_observado", cursor.getString(cursor.getColumnIndex("sector_empleado_observado")));
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

    public  Boolean setSODAPendientes(String id) {
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
            success = db.update("formularioSODA", values, where, whereArgs) > 0;
        } catch (Exception e) {
            success = false;
        }
        //}
        db.close();
        return success;
    }

}
