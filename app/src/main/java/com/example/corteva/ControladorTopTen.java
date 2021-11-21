package com.example.corteva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorTopTen extends AdminSQLiteOpenHelper {
    public ControladorTopTen (Context context){
        super(context);
    }

    public boolean Insert(String PTSFecha, String PTSConfecciono, String PTSObservaciones, /*String PTSValidezPermiso,
                          String PTSFirmasResponsables, String PTSSectorTrabajo, String PTSListadoPersonal,
                          String PTSDescripcionTrabajo, String PTSInspeccionSector, String PTSRiesgosFisicos,
                          String PTSRiesgosQuimicos, String PTSRiesgosAmbientales, String PTSEPPAdicional, String PTSEntrenamiento,
                          String PTSCierre,*/
                          String core1, String core2, String core3, String core4, String core5, String core6, String core7,
                          String core8, String core9, String core10,
                          String TarjetasRojasFecha, String TarjetasRojasConfecciono, String TarjetasRojasObservaciones,
                          String TarjetasRojasCantTarj, String TarjetasRojasEquipoFuera, String TarjetasRojasRazon,
                          String TarjetasRojasTarea, String TarjetasRojasDpto, String TarjetasRojasAcepto,
                          String TarjetasRojasApellido, String TarjetasRojasCancelacion, String TarjetasRojasRepDeLaInst,
                          String TarjetasRojasAislacionFisica, String TarjetasRojasRepTrabajo, String TarjetasRojasTarjetasRetiradas,
                          String TarjetasRojasNroTarjeta, String TarjetasRojasUbicacion, String TarjetasRojasColocadoPor,
                          String TarjetasRojasFechaRetiro, String TarjetasRojasRetirada, String TarjetasRojasConciliado,
                          String EspacioConfinadoFecha, String EspacioConfinadoConfecciono, String EspacioConfinadoObservaciones,
                          String EspacioConfinadoIdentificacionEspacioConfinado, String EspacioConfinadoPermisoAEC,
                          String EspacioConfinadoVigenciaDelPermiso, String EspacioConfinadoIngresoEspacioConfinado,
                          String EspacioConfinadoVigiaExterno, String EspacioConfinadoVigia, String EspacioConfinadoPersonalInvolucrado,
                          String EspacioConfinadoCheckListAdicionales, String EspacioConfinadoAislacion,
                          String EspacioConfinadoEquiposElectricos, String EspacioConfinadoLimpiezaVentilacion,
                          String EspacioConfinadoHerramientasEquipos, String EspacioConfinadoMonitoreo,
                          String EspaciosConfinadosEntrada, String EspaciosConfinadosRescate, String EspaciosConfinadosFirmaResponsable,
                          String TrabajoEnAlturaFecha,
                          String TrabajoEnAlturaConfecciono, String TrabajoEnAlturaObservaciones,
                          String TrabajoEnAlturaIdentificacionSector, String TrabajoEnAlturaPTSCaidas,
                          String TrabajoEnAlturaVigenciaPT, String TrabajoEnAlturaEPPCaidas,
                          String TrabajoEnAlturaHabilitacionEPP, String TrabajoEnAlturaAndamioHabilitado,
                          String TrabajoEnAlturaBarandasCondiciones, String TrabajoEnAlturaCondicionesEscaleras,
                          String TrabajoEnAlturaEscaleraSujeta, String TrabajoEnAlturaSenalizacionSector,
                          String TrabajoEnAlturaOperarioAtado, String TrabajoEnAlturaHerramientasEquipos,
                          String TrabajoEnAlturaDesenergizacion, String TrabajoEnAlturaColocacionGuardaCritica,
                          String TrabajoEnAlturaGuardaCritica, String TrabajoEnAlturaFirmaResponsable,
                          String TrabajoEnCalienteFecha, String TrabajoEnCalienteConfecciono,
                          String TrabajoEnCalienteObservaciones, String TrabajoEnCalienteIdentificacionSector,
                          String TrabajoEnCalientePTSTrabajoEnCaliente, String TrabajoEnCalienteVigenciaPT,
                          String TrabajoEnCalienteEPPTrabajoEnCaliente, String TrabajoEnCalienteHabilitacionHerramientas,
                          String TrabajoEnCalienteSenalizacionSector, String TrabajoEnCalienteInflamablesFueraPerimetro,
                          String TrabajoEnCalienteCanerias, String TrabajoEnCalienteObservadorFuego,
                          String TrabajoEnCalienteEquipoExtinsion, String TrabajoEnCalienteMonitoreo,
                          String TrabajoEnCalienteEstadoEquipo, String TrabajoEnCalientePurgado,
                          String TrabajoEnCalienteBloqueosDobles, String TrabajoEnCalienteTrabajoAltaEnergia,
                          String TrabajoEnCalienteFirmaResponsable, String AperturaLineasFecha, String AperturaLineasConfecciono,
                          String AperturaLineasObservaciones, String AperturaLineasSectorYUbicacion, String AperturaLineasEstadoLinea,
                          String AperturaLineasSenalizacion, String AperturaLineasAislacionLineaEquipo,
                          String AperturaLineasIdentificacionApertura, String AperturaLineasVerificacionPuntosDificiles,
                          String AperturaLineasSoldadas, String AperturaLineasDespresurizacion, String AperturaLineasLimpiezaLineaEquipo,
                          String AperturaLineasLineaEquipo, String AperturaLineasOtrosPeligros, String AperturaLineasFirmasResponsables,
                          String HidrolavadoraFecha, String HidrolavadoraConfecciono, String HidrolavadoraObservaciones,
                          String HidrolavadoraIdentificacionSector, String HidrolavadoraPTSAnalisis, String HidrolavadoraVigenciaPT,
                          String HidrolavadoraOperariosHabilitados, String HidrolavadoraEPPAdecuados, String HidrolavadoraHidro,
                          String HidrolavadoraValladoPerimetral, String HidrolavadoraColocacionMangueras, String HidrolavadoraVigiaPresente,
                          String HidrolavadoraVigia, String HidrolavadoraCheckListAdicionales, String HidrolavadoraHerramientasEquipos,
                          String HidrolavadoraDesenergizacion, String HidrolavadoraTempMax, String HidrolavadoraProteccionEquiposElectricos,
                          String HidrolavadoraFirmaResponsable, String spinnerSector, String spinnerObservadorUno, String spinnerObservadorDos,
                          String imagenPTS, String imagenTarjetasRojas, String imagenEspacioConfinado, String imagenTrabajoEnAltura,
                          String imagenAperturaLinea, String imagenTrabajoEnCaliente, String imagenHidrolavadora, String site, String usuario){

        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("PTSFecha", PTSFecha);
        values.put("PTSConfecciono", PTSConfecciono);
        values.put("core1", core1);
        values.put("core2", core2);
        values.put("core3", core3);
        values.put("core4", core4);
        values.put("core5", core5);
        values.put("core6", core6);
        values.put("core7", core7);
        values.put("core8", core8);
        values.put("core9", core9);
        values.put("core10", core10);
        //values.put("PTSValidezPermiso", PTSValidezPermiso);
        //values.put("PTSInspeccionSector", PTSInspeccionSector);
        //values.put("PTSRiesgosFisicos", PTSRiesgosFisicos);
        //values.put("PTSRiesgosQuimicos", PTSRiesgosQuimicos);
        //values.put("PTSRiegosAmbientales", PTSRiesgosAmbientales);
        //values.put("PTSEPPAdicionales", PTSEPPAdicional);
        //values.put("PTSEntrenamiento", PTSEntrenamiento);
        //values.put("PTSCierre", PTSCierre);
        //values.put("PTSFirmaResponsable", PTSFirmasResponsables);
        //values.put("PTSSectorTrabajo", PTSSectorTrabajo);
        //values.put("PTSListadoTrabajo", PTSListadoPersonal);
        //values.put("PTSDescripcionTrabajo", PTSDescripcionTrabajo);
        values.put("PTSObservaciones", PTSObservaciones);
        values.put("TarjetasRojasFecha", TarjetasRojasFecha);
        values.put("TarjetasRojasConfecciono", TarjetasRojasConfecciono);
        values.put("TarjetasRojasObservaciones", TarjetasRojasObservaciones);
        values.put("TarjetasRojasCantTarj", TarjetasRojasCantTarj);
        values.put("TarjetasRojasEquipoFuera", TarjetasRojasEquipoFuera);
        values.put("TarjetasRojasRazon", TarjetasRojasRazon);
        values.put("TarjetasRojasTarea", TarjetasRojasTarea);
        values.put("TarjetasRojasDpto", TarjetasRojasDpto);
        values.put("TarjetasRojasAcepto", TarjetasRojasAcepto);
        values.put("TarjetasRojasApellido", TarjetasRojasApellido);
        values.put("TarjetasRojasCancelacion", TarjetasRojasCancelacion);
        values.put("TarjetasRojasRepDeLaInst", TarjetasRojasRepDeLaInst);
        values.put("TarjetasRojasAislacionFisica", TarjetasRojasAislacionFisica);
        values.put("TarjetasRojasRepTrabajo", TarjetasRojasRepTrabajo);
        values.put("TarjetasRojasTarjetasRetiradas", TarjetasRojasTarjetasRetiradas);
        values.put("TarjetasRojasNroTarjeta", TarjetasRojasNroTarjeta);
        values.put("TarjetasRojasUbicacion", TarjetasRojasUbicacion);
        values.put("TarjetasRojasColocadoPor", TarjetasRojasColocadoPor);
        values.put("TarjetasRojasFechaRetiro", TarjetasRojasFechaRetiro);
        values.put("TarjetasRojasRetirada" , TarjetasRojasRetirada);
        values.put("TarjetasRojasConciliado", TarjetasRojasConciliado);
        values.put("EspacioConfinadoFecha", EspacioConfinadoFecha);
        values.put("EspacioConfinadoConfecciono", EspacioConfinadoConfecciono);
        values.put("EspacioConfinadoIdentificacionEspacioConfinado", EspacioConfinadoIdentificacionEspacioConfinado);
        values.put("EspacioConfinadoPermisoAEC", EspacioConfinadoPermisoAEC);
        values.put("EspacioConfinadoVigenciaDelPermiso", EspacioConfinadoVigenciaDelPermiso);
        values.put("EspacioConfinadoIngresoEspacioConfinado", EspacioConfinadoIngresoEspacioConfinado);
        values.put("EspacioConfinadoVigiaExterno", EspacioConfinadoVigiaExterno);
        values.put("EspacioConfinadoVigia", EspacioConfinadoVigia);
        values.put("EspacioConfinadoPersonalInvolucrado", EspacioConfinadoPersonalInvolucrado);
        values.put("EspacioConfinadoCheckListAdicionales", EspacioConfinadoCheckListAdicionales);
        values.put("EspacioConfinadoAislacion", EspacioConfinadoAislacion);
        values.put("EspacioConfinadoEquiposElectricos", EspacioConfinadoEquiposElectricos);
        values.put("EspacioConfinadoLimpiezaVentilacion", EspacioConfinadoLimpiezaVentilacion);
        values.put("EspacioConfinadoHerramientasEquipos", EspacioConfinadoHerramientasEquipos);
        values.put("EspacioConfinadoMonitoreo", EspacioConfinadoMonitoreo);
        values.put("EspacioConfinadoEntrada", EspaciosConfinadosEntrada);
        values.put("EspacioConfinadoRescate", EspaciosConfinadosRescate);
        values.put("EspacioConfinadoFirmasResponsables", EspaciosConfinadosFirmaResponsable);
        values.put("EspacioConfinadoObservaciones", EspacioConfinadoObservaciones);
        values.put("TrabajoEnAlturaFecha", TrabajoEnAlturaFecha);
        values.put("TrabajoEnAlturaConfecciono", TrabajoEnAlturaConfecciono);
        values.put("TrabajoEnAlturaIdentificacionSector", TrabajoEnAlturaIdentificacionSector);
        values.put("TrabajoEnAlturaPTSCaidas", TrabajoEnAlturaPTSCaidas);
        values.put("TrabajoEnAlturaVigenciaPT", TrabajoEnAlturaVigenciaPT);
        values.put("TrabajoEnAlturaEPPCaidas", TrabajoEnAlturaEPPCaidas);
        values.put("TrabajoEnAlturaEPPTrabajoAltura", TrabajoEnAlturaHabilitacionEPP);
        values.put("TrabajoEnAlturaAndamioHabilitado", TrabajoEnAlturaAndamioHabilitado);
        values.put("TrabajoEnAlturaBarandasCondiciones", TrabajoEnAlturaBarandasCondiciones);
        values.put("TrabajoEnAlturaCondicionesEscaleras", TrabajoEnAlturaCondicionesEscaleras);
        values.put("TrabajoEnAlturaEscaleraSujeta", TrabajoEnAlturaEscaleraSujeta);
        values.put("TrabajoEnAlturaSenalizacionSector", TrabajoEnAlturaSenalizacionSector);
        values.put("TrabajoEnAlturaOperarioAtado", TrabajoEnAlturaOperarioAtado);
        values.put("TrabajoEnAlturaHerramientasEquipos", TrabajoEnAlturaHerramientasEquipos);
        values.put("TrabajoEnAlturaDesenergizacion", TrabajoEnAlturaDesenergizacion);
        values.put("TrabajoEnAlturaColocoGuardaCritica", TrabajoEnAlturaColocacionGuardaCritica);
        values.put("TrabajoEnAlturaGuardaCritica", TrabajoEnAlturaGuardaCritica);
        values.put("TrabajoEnAlturaFirmaResponsable", TrabajoEnAlturaFirmaResponsable);
        values.put("TrabajoEnAlturaObservaciones", TrabajoEnAlturaObservaciones);
        values.put("TrabajoEnCalienteFecha", TrabajoEnCalienteFecha);
        values.put("TrabajoEnCalienteConfecciono", TrabajoEnCalienteConfecciono);
        values.put("TrabajoEnCalienteIdentificacionSector", TrabajoEnCalienteIdentificacionSector);
        values.put("TrabajoEnCalientePTSCaliente", TrabajoEnCalientePTSTrabajoEnCaliente);
        values.put("TrabajoEnCalienteVigenciaPT", TrabajoEnCalienteVigenciaPT);
        values.put("TrabajoEnCalienteEPPCaliente", TrabajoEnCalienteEPPTrabajoEnCaliente );
        values.put("TrabajoEnCalienteHabilitacionHerramientas", TrabajoEnCalienteHabilitacionHerramientas);
        values.put("TrabajoEnCalienteSenalizacionSector", TrabajoEnCalienteSenalizacionSector);
        values.put("TrabajoEnCalienteObservadorFuego", TrabajoEnCalienteObservadorFuego);
        values.put("TrabajoEnCalienteEquipoExtinsion", TrabajoEnCalienteEquipoExtinsion);
        values.put("TrabajoEnCalienteMonitoreoInflamable", TrabajoEnCalienteMonitoreo);
        values.put("TrabajoEnCalienteEstadoEquipo", TrabajoEnCalienteEstadoEquipo);
        values.put("TrabajoEnCalientePurgado", TrabajoEnCalientePurgado);
        values.put("TrabajoEnCalienteBloqueosDobles", TrabajoEnCalienteBloqueosDobles);
        values.put("TrabajoEnCalienteAltaEnergia", TrabajoEnCalienteTrabajoAltaEnergia);
        values.put("TrabajoEnCalienteFirmasResponsables", TrabajoEnCalienteFirmaResponsable);
        values.put("TrabajoEnCalienteObservaciones", TrabajoEnCalienteObservaciones);
        values.put("TrabajoEnCalienteInflamable", TrabajoEnCalienteInflamablesFueraPerimetro);
        values.put("TrabajoEnCalienteCanerias", TrabajoEnCalienteCanerias);
        values.put("AperturaLineaFecha", AperturaLineasFecha);
        values.put("AperturaLineaConfecciono", AperturaLineasConfecciono);
        values.put("AperturaLineaSector", AperturaLineasSectorYUbicacion);
        values.put("AperturaLineaEstadoLinea", AperturaLineasEstadoLinea);
        values.put("AperturaLineaSenalizacion", AperturaLineasSenalizacion);
        values.put("AperturaLineaAislacionLinea", AperturaLineasAislacionLineaEquipo);
        values.put("AperturaLineaIdentificacionApertura", AperturaLineasIdentificacionApertura);
        values.put("AperturaLineaPuntosDificiles", AperturaLineasVerificacionPuntosDificiles);
        values.put("AperturaLineaSoldada", AperturaLineasSoldadas);
        values.put("AperturaLineaDesprezuracion", AperturaLineasDespresurizacion);
        values.put("AperturaLineaLimpiezaLinea", AperturaLineasLimpiezaLineaEquipo);
        values.put("AperturaLineaInflamables", AperturaLineasLineaEquipo);
        values.put("AperturaLineaOtrosPeligros", AperturaLineasOtrosPeligros);
        values.put("AperturaLineaFirmasResponsables", AperturaLineasFirmasResponsables);
        values.put("AperturaLineaObservaciones", AperturaLineasObservaciones);
        values.put("HidrolavadoraFecha", HidrolavadoraFecha);
        values.put("HidrolavadoraConfecciono", HidrolavadoraConfecciono);
        values.put("HidrolavadoraIdentificacionSector", HidrolavadoraIdentificacionSector);
        values.put("HidrolavadoraPTSAnalisisRiesgos", HidrolavadoraPTSAnalisis);
        values.put("HidrolavadoraVigenciaPT", HidrolavadoraVigenciaPT);
        values.put("HidrolavadoraOperariosHabilitados", HidrolavadoraOperariosHabilitados);
        values.put("HidrolavadoraEPPAdecuados", HidrolavadoraEPPAdecuados);
        values.put("HidrolavadoraHabilitada", HidrolavadoraHidro);
        values.put("HidrolavadoraValladoPerimetral", HidrolavadoraValladoPerimetral);
        values.put("HidrolavadoraMangueras", HidrolavadoraColocacionMangueras);
        values.put("HidrolavadoraVigiaPresente", HidrolavadoraVigiaPresente);
        values.put("HidrolavadoraVigia", HidrolavadoraVigia);
        values.put("HidrolavadoraCheckListAdicionales", HidrolavadoraCheckListAdicionales);
        values.put("HidrolavadoraHerramientasEquipos", HidrolavadoraHerramientasEquipos);
        values.put("HidrolavadoraDesenergizacion", HidrolavadoraDesenergizacion);
        values.put("HidrolavadoraTempMax", HidrolavadoraTempMax);
        values.put("HidrolavadoraProteccionEquipos", HidrolavadoraProteccionEquiposElectricos);
        values.put("HidrolavadoraFirmasResponsables", HidrolavadoraFirmaResponsable);
        values.put("HidrolavadoraObservaciones", HidrolavadoraObservaciones);
        values.put("sector", spinnerSector);
        values.put("observador1", spinnerObservadorUno);
        values.put("observador2", spinnerObservadorDos);
        values.put("site",site);
        values.put("imagenPTS",imagenPTS);
        values.put("imagenTarjetasRojas",imagenTarjetasRojas);
        values.put("imagenEspacioConfinado",imagenEspacioConfinado);
        values.put("imagenTrabajoEnAltura",imagenTrabajoEnAltura);
        values.put("imagenAperturaLinea",imagenAperturaLinea);
        values.put("imagenTrabajoEnCaliente",imagenTrabajoEnCaliente);
        values.put("imagenHidrolavadora",imagenHidrolavadora);
        values.put("usuario",usuario);

        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("fechaHora", fh);
        values.put("syncro", 0);

        boolean resultado=BaseDeDatos.insert("TopTen",null,values)>0;
        BaseDeDatos.close();
        return resultado;

    }

    public JSONArray getTopTenPendientes() {
        JSONArray rows = new JSONArray();
        String sql = "SELECT * FROM TopTen WHERE syncro='0'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                try{
                    JSONObject row = new JSONObject();
                    row.put("id", cursor.getInt(cursor.getColumnIndex("id")));
                    row.put("site", cursor.getString(cursor.getColumnIndex("site")));
                    row.put("fechaHora", cursor.getString(cursor.getColumnIndex("fechaHora")));
                    row.put("sector", cursor.getString(cursor.getColumnIndex("sector")));
                    row.put("observador1", cursor.getString(cursor.getColumnIndex("observador1")));
                    row.put("observador2", cursor.getString(cursor.getColumnIndex("observador2")));
                    row.put("PTSFecha", cursor.getString(cursor.getColumnIndex("PTSFecha")));
                    row.put("PTSConfecciono", cursor.getString(cursor.getColumnIndex("PTSConfecciono")));
                    row.put("core1", cursor.getString(cursor.getColumnIndex("core1")));
                    row.put("core2", cursor.getString(cursor.getColumnIndex("core2")));
                    row.put("core3", cursor.getString(cursor.getColumnIndex("core3")));
                    row.put("core4", cursor.getString(cursor.getColumnIndex("core4")));
                    row.put("core5", cursor.getString(cursor.getColumnIndex("core5")));
                    row.put("core6", cursor.getString(cursor.getColumnIndex("core6")));
                    row.put("core7", cursor.getString(cursor.getColumnIndex("core7")));
                    row.put("core8", cursor.getString(cursor.getColumnIndex("core8")));
                    row.put("core9", cursor.getString(cursor.getColumnIndex("core9")));
                    row.put("core10", cursor.getString(cursor.getColumnIndex("core10")));
                    /*row.put("PTSValidezPermiso", cursor.getString(cursor.getColumnIndex("PTSValidezPermiso")));
                    row.put("PTSFirmaResponsable", cursor.getString(cursor.getColumnIndex("PTSFirmaResponsable")));
                    row.put("PTSSectorTrabajo", cursor.getString(cursor.getColumnIndex("PTSSectorTrabajo")));
                    row.put("PTSListadoTrabajo", cursor.getString(cursor.getColumnIndex("PTSListadoTrabajo")));
                    row.put("PTSDescripcionTrabajo", cursor.getString(cursor.getColumnIndex("PTSDescripcionTrabajo")));
                    row.put("PTSInspeccionSector", cursor.getString(cursor.getColumnIndex("PTSInspeccionSector")));
                    row.put("PTSRiesgosFisicos", cursor.getString(cursor.getColumnIndex("PTSRiesgosFisicos")));
                    row.put("PTSRiesgosQuimicos", cursor.getString(cursor.getColumnIndex("PTSRiesgosQuimicos")));
                    row.put("PTSRiegosAmbientales", cursor.getString(cursor.getColumnIndex("PTSRiegosAmbientales")));
                    row.put("PTSEPPAdicionales", cursor.getString(cursor.getColumnIndex("PTSEPPAdicionales")));
                    row.put("PTSEntrenamiento", cursor.getString(cursor.getColumnIndex("PTSEntrenamiento")));
                    row.put("PTSCierre", cursor.getString(cursor.getColumnIndex("PTSCierre")));*/
                    row.put("PTSObservaciones", cursor.getString(cursor.getColumnIndex("PTSObservaciones")));
                    row.put("TarjetasRojasFecha", cursor.getString(cursor.getColumnIndex("TarjetasRojasFecha")));
                    row.put("TarjetasRojasConfecciono", cursor.getString(cursor.getColumnIndex("TarjetasRojasConfecciono")));
                    row.put("TarjetasRojasObservaciones", cursor.getString(cursor.getColumnIndex("TarjetasRojasObservaciones")));
                    row.put("TarjetasRojasCantTarj", cursor.getString(cursor.getColumnIndex("TarjetasRojasCantTarj")));
                    row.put("TarjetasRojasEquipoFuera", cursor.getString(cursor.getColumnIndex("TarjetasRojasEquipoFuera")));
                    row.put("TarjetasRojasRazon", cursor.getString(cursor.getColumnIndex("TarjetasRojasRazon")));
                    row.put("TarjetasRojasTarea", cursor.getString(cursor.getColumnIndex("TarjetasRojasTarea")));
                    row.put("TarjetasRojasDpto", cursor.getString(cursor.getColumnIndex("TarjetasRojasDpto")));
                    row.put("TarjetasRojasAcepto", cursor.getString(cursor.getColumnIndex("TarjetasRojasAcepto")));
                    row.put("TarjetasRojasApellido", cursor.getString(cursor.getColumnIndex("TarjetasRojasApellido")));
                    row.put("TarjetasRojasCancelacion", cursor.getString(cursor.getColumnIndex("TarjetasRojasCancelacion")));
                    row.put("TarjetasRojasRepDeLaInst", cursor.getString(cursor.getColumnIndex("TarjetasRojasRepDeLaInst")));
                    row.put("TarjetasRojasAislacionFisica", cursor.getString(cursor.getColumnIndex("TarjetasRojasAislacionFisica")));
                    row.put("TarjetasRojasRepTrabajo", cursor.getString(cursor.getColumnIndex("TarjetasRojasRepTrabajo")));
                    row.put("TarjetasRojasTarjetasRetiradas", cursor.getString(cursor.getColumnIndex("TarjetasRojasTarjetasRetiradas")));
                    row.put("TarjetasRojasNroTarjeta", cursor.getString(cursor.getColumnIndex("TarjetasRojasNroTarjeta")));
                    row.put("TarjetasRojasUbicacion", cursor.getString(cursor.getColumnIndex("TarjetasRojasUbicacion")));
                    row.put("TarjetasRojasColocadoPor", cursor.getString(cursor.getColumnIndex("TarjetasRojasColocadoPor")));
                    row.put("TarjetasRojasFechaRetiro", cursor.getString(cursor.getColumnIndex("TarjetasRojasFechaRetiro")));
                    row.put("TarjetasRojasRetirada", cursor.getString(cursor.getColumnIndex("TarjetasRojasRetirada")));
                    row.put("TarjetasRojasConciliado", cursor.getString(cursor.getColumnIndex("TarjetasRojasConciliado")));
                    row.put("EspacioConfinadoFecha", cursor.getString(cursor.getColumnIndex("EspacioConfinadoFecha")));
                    row.put("EspacioConfinadoConfecciono", cursor.getString(cursor.getColumnIndex("EspacioConfinadoConfecciono")));
                    row.put("EspacioConfinadoIdentificacionEspacioConfinado", cursor.getString(cursor.getColumnIndex("EspacioConfinadoIdentificacionEspacioConfinado")));
                    row.put("EspacioConfinadoPermisoAEC", cursor.getString(cursor.getColumnIndex("EspacioConfinadoPermisoAEC")));
                    row.put("EspacioConfinadoVigenciaDelPermiso", cursor.getString(cursor.getColumnIndex("EspacioConfinadoVigenciaDelPermiso")));
                    row.put("EspacioConfinadoIngresoEspacioConfinado", cursor.getString(cursor.getColumnIndex("EspacioConfinadoIngresoEspacioConfinado")));
                    row.put("EspacioConfinadoVigiaExterno", cursor.getString(cursor.getColumnIndex("EspacioConfinadoVigiaExterno")));
                    row.put("EspacioConfinadoVigia", cursor.getString(cursor.getColumnIndex("EspacioConfinadoVigia")));
                    row.put("EspacioConfinadoPersonalInvolucrado", cursor.getString(cursor.getColumnIndex("EspacioConfinadoPersonalInvolucrado")));
                    row.put("EspacioConfinadoCheckListAdicionales", cursor.getString(cursor.getColumnIndex("EspacioConfinadoCheckListAdicionales")));
                    row.put("EspacioConfinadoAislacion", cursor.getString(cursor.getColumnIndex("EspacioConfinadoAislacion")));
                    row.put("EspacioConfinadoEquiposElectricos", cursor.getString(cursor.getColumnIndex("EspacioConfinadoEquiposElectricos")));
                    row.put("EspacioConfinadoLimpiezaVentilacion", cursor.getString(cursor.getColumnIndex("EspacioConfinadoLimpiezaVentilacion")));
                    row.put("EspacioConfinadoHerramientasEquipos", cursor.getString(cursor.getColumnIndex("EspacioConfinadoHerramientasEquipos")));
                    row.put("EspacioConfinadoMonitoreo", cursor.getString(cursor.getColumnIndex("EspacioConfinadoMonitoreo")));
                    row.put("EspacioConfinadoEntrada", cursor.getString(cursor.getColumnIndex("EspacioConfinadoEntrada")));
                    row.put("EspacioConfinadoRescate", cursor.getString(cursor.getColumnIndex("EspacioConfinadoRescate")));
                    row.put("EspacioConfinadoFirmasResponsables", cursor.getString(cursor.getColumnIndex("EspacioConfinadoFirmasResponsables")));
                    row.put("EspacioConfinadoObservaciones", cursor.getString(cursor.getColumnIndex("EspacioConfinadoObservaciones")));
                    row.put("TrabajoEnAlturaFecha", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaFecha")));
                    row.put("TrabajoEnAlturaConfecciono", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaConfecciono")));
                    row.put("TrabajoEnAlturaIdentificacionSector", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaIdentificacionSector")));
                    row.put("TrabajoEnAlturaPTSCaidas", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaPTSCaidas")));
                    row.put("TrabajoEnAlturaVigenciaPT", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaVigenciaPT")));
                    row.put("TrabajoEnAlturaEPPCaidas", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaEPPCaidas")));
                    row.put("TrabajoEnAlturaEPPTrabajoAltura", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaEPPTrabajoAltura")));
                    row.put("TrabajoEnAlturaAndamioHabilitado", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaAndamioHabilitado")));
                    row.put("TrabajoEnAlturaBarandasCondiciones", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaBarandasCondiciones")));
                    row.put("TrabajoEnAlturaCondicionesEscaleras", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaCondicionesEscaleras")));
                    row.put("TrabajoEnAlturaEscaleraSujeta", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaEscaleraSujeta")));
                    row.put("TrabajoEnAlturaSenalizacionSector", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaSenalizacionSector")));
                    row.put("TrabajoEnAlturaOperarioAtado", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaOperarioAtado")));
                    row.put("TrabajoEnAlturaHerramientasEquipos", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaHerramientasEquipos")));
                    row.put("TrabajoEnAlturaDesenergizacion", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaDesenergizacion")));
                    row.put("TrabajoEnAlturaColocoGuardaCritica", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaColocoGuardaCritica")));
                    row.put("TrabajoEnAlturaGuardaCritica", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaGuardaCritica")));
                    row.put("TrabajoEnAlturaFirmaResponsable", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaFirmaResponsable")));
                    row.put("TrabajoEnAlturaObservaciones", cursor.getString(cursor.getColumnIndex("TrabajoEnAlturaObservaciones")));
                    row.put("AperturaLineaFecha", cursor.getString(cursor.getColumnIndex("AperturaLineaFecha")));
                    row.put("AperturaLineaConfecciono", cursor.getString(cursor.getColumnIndex("AperturaLineaConfecciono")));
                    row.put("AperturaLineaSector", cursor.getString(cursor.getColumnIndex("AperturaLineaSector")));
                    row.put("AperturaLineaEstadoLinea", cursor.getString(cursor.getColumnIndex("AperturaLineaEstadoLinea")));
                    row.put("AperturaLineaSenalizacion", cursor.getString(cursor.getColumnIndex("AperturaLineaSenalizacion")));
                    row.put("AperturaLineaAislacionLinea", cursor.getString(cursor.getColumnIndex("AperturaLineaAislacionLinea")));
                    row.put("AperturaLineaIdentificacionApertura", cursor.getString(cursor.getColumnIndex("AperturaLineaIdentificacionApertura")));
                    row.put("AperturaLineaPuntosDificiles", cursor.getString(cursor.getColumnIndex("AperturaLineaPuntosDificiles")));
                    row.put("AperturaLineaSoldada", cursor.getString(cursor.getColumnIndex("AperturaLineaSoldada")));
                    row.put("AperturaLineaDesprezuracion", cursor.getString(cursor.getColumnIndex("AperturaLineaDesprezuracion")));
                    row.put("AperturaLineaLimpiezaLinea", cursor.getString(cursor.getColumnIndex("AperturaLineaLimpiezaLinea")));
                    row.put("AperturaLineaInflamables", cursor.getString(cursor.getColumnIndex("AperturaLineaInflamables")));
                    row.put("AperturaLineaOtrosPeligros", cursor.getString(cursor.getColumnIndex("AperturaLineaOtrosPeligros")));
                    row.put("AperturaLineaFirmasResponsables", cursor.getString(cursor.getColumnIndex("AperturaLineaFirmasResponsables")));
                    row.put("AperturaLineaObservaciones", cursor.getString(cursor.getColumnIndex("AperturaLineaObservaciones")));
                    row.put("TrabajoEnCalienteFecha", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteFecha")));
                    row.put("TrabajoEnCalienteConfecciono", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteConfecciono")));
                    row.put("TrabajoEnCalienteIdentificacionSector", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteIdentificacionSector")));
                    row.put("TrabajoEnCalientePTSCaliente", cursor.getString(cursor.getColumnIndex("TrabajoEnCalientePTSCaliente")));
                    row.put("TrabajoEnCalienteVigenciaPT", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteVigenciaPT")));
                    row.put("TrabajoEnCalienteEPPCaliente", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteEPPCaliente")));
                    row.put("TrabajoEnCalienteHabilitacionHerramientas", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteHabilitacionHerramientas")));
                    row.put("TrabajoEnCalienteSenalizacionSector", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteSenalizacionSector")));
                    row.put("TrabajoEnCalienteInflamable", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteInflamable")));
                    row.put("TrabajoEnCalienteCanerias", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteCanerias")));
                    row.put("TrabajoEnCalienteObservadorFuego", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteObservadorFuego")));
                    row.put("TrabajoEnCalienteEquipoExtinsion", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteEquipoExtinsion")));
                    row.put("TrabajoEnCalienteMonitoreoInflamable", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteMonitoreoInflamable")));
                    row.put("TrabajoEnCalienteEstadoEquipo", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteEstadoEquipo")));
                    row.put("TrabajoEnCalientePurgado", cursor.getString(cursor.getColumnIndex("TrabajoEnCalientePurgado")));
                    row.put("TrabajoEnCalienteBloqueosDobles", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteBloqueosDobles")));
                    row.put("TrabajoEnCalienteAltaEnergia", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteAltaEnergia")));
                    row.put("TrabajoEnCalienteFirmasResponsables", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteFirmasResponsables")));
                    row.put("TrabajoEnCalienteObservaciones", cursor.getString(cursor.getColumnIndex("TrabajoEnCalienteObservaciones")));
                    row.put("HidrolavadoraFecha", cursor.getString(cursor.getColumnIndex("HidrolavadoraFecha")));
                    row.put("HidrolavadoraConfecciono", cursor.getString(cursor.getColumnIndex("HidrolavadoraConfecciono")));
                    row.put("HidrolavadoraIdentificacionSector", cursor.getString(cursor.getColumnIndex("HidrolavadoraIdentificacionSector")));
                    row.put("HidrolavadoraPTSAnalisisRiesgos", cursor.getString(cursor.getColumnIndex("HidrolavadoraPTSAnalisisRiesgos")));
                    row.put("HidrolavadoraVigenciaPT", cursor.getString(cursor.getColumnIndex("HidrolavadoraVigenciaPT")));
                    row.put("HidrolavadoraOperariosHabilitados", cursor.getString(cursor.getColumnIndex("HidrolavadoraOperariosHabilitados")));
                    row.put("HidrolavadoraEPPAdecuados", cursor.getString(cursor.getColumnIndex("HidrolavadoraEPPAdecuados")));
                    row.put("HidrolavadoraHabilitada", cursor.getString(cursor.getColumnIndex("HidrolavadoraHabilitada")));
                    row.put("HidrolavadoraValladoPerimetral", cursor.getString(cursor.getColumnIndex("HidrolavadoraValladoPerimetral")));
                    row.put("HidrolavadoraMangueras", cursor.getString(cursor.getColumnIndex("HidrolavadoraMangueras")));
                    row.put("HidrolavadoraVigiaPresente", cursor.getString(cursor.getColumnIndex("HidrolavadoraVigiaPresente")));
                    row.put("HidrolavadoraVigia", cursor.getString(cursor.getColumnIndex("HidrolavadoraVigia")));
                    row.put("HidrolavadoraCheckListAdicionales", cursor.getString(cursor.getColumnIndex("HidrolavadoraCheckListAdicionales")));
                    row.put("HidrolavadoraHerramientasEquipos", cursor.getString(cursor.getColumnIndex("HidrolavadoraHerramientasEquipos")));
                    row.put("HidrolavadoraDesenergizacion", cursor.getString(cursor.getColumnIndex("HidrolavadoraDesenergizacion")));
                    row.put("HidrolavadoraTempMax", cursor.getString(cursor.getColumnIndex("HidrolavadoraTempMax")));
                    row.put("HidrolavadoraProteccionEquipos", cursor.getString(cursor.getColumnIndex("HidrolavadoraProteccionEquipos")));
                    row.put("HidrolavadoraFirmasResponsables", cursor.getString(cursor.getColumnIndex("HidrolavadoraFirmasResponsables")));
                    row.put("HidrolavadoraObservaciones", cursor.getString(cursor.getColumnIndex("HidrolavadoraObservaciones")));
                    row.put("imagenPTS", cursor.getString(cursor.getColumnIndex("imagenPTS")));
                    row.put("imagenTarjetasRojas", cursor.getString(cursor.getColumnIndex("imagenTarjetasRojas")));
                    row.put("imagenEspacioConfinado", cursor.getString(cursor.getColumnIndex("imagenEspacioConfinado")));
                    row.put("imagenTrabajoEnAltura", cursor.getString(cursor.getColumnIndex("imagenTrabajoEnAltura")));
                    row.put("imagenAperturaLinea", cursor.getString(cursor.getColumnIndex("imagenAperturaLinea")));
                    row.put("imagenTrabajoEnCaliente", cursor.getString(cursor.getColumnIndex("imagenTrabajoEnCaliente")));
                    row.put("imagenHidrolavadora", cursor.getString(cursor.getColumnIndex("imagenHidrolavadora")));
                    row.put("usuario", cursor.getString(cursor.getColumnIndex("usuario")));

                    rows.put(row);
                } catch (Exception e) {
                    Log.e("SINCRONIZACION SENDING", "Exception: " + e.getMessage());
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return rows;
    }

    //public Boolean setTopTenPendientes(JSONArray array) {
    public Boolean setTopTenPendientes(String id) {
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
                success = db.update("TopTen", values, where, whereArgs) > 0;
            } catch (Exception e) {
                success = false;
            }
        //}
        db.close();
        return success;
    }
}