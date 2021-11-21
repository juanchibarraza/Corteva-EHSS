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

public class ControladorPruebasACampo extends AdminSQLiteOpenHelper {

    public ControladorPruebasACampo (Context context){
        super(context);
    }

    public boolean Insert(String site, String fecha, String observadorUno, String observadorDos, String nombreProcedimiento,
                          String ejecutante, String sector,
                          String cumpleAlcance, String valorDeReferenciaAlcance, String observacionesAlcance,
                          String propuestaMejoraAlcance, String accionAlcance, String responsableAlcance,
                          String cumpleCategoria, String valorDeReferenciaCategoria, String observacionesCategoria,
                          String propuestaMejoraCategoria, String accionCategoria, String responsableCategoria,
                          String cumpleAtributos, String valorDeReferenciaAtributos, String observacionesAtributos,
                          String propuestaMejoraAtributos, String accionAtributos, String responsableAtributos,
                          String cumpleRiesgosYPrecauciones, String valorDeReferenciaRiesgosYPrecauciones, String observacionesRiesgosYPrecauciones,
                          String propuestaMejoraRiesgosYPrecauciones, String accionRiesgosYPrecauciones, String responsableRiesgosYPrecauciones,
                          String cumpleEPP, String valorDeReferenciaEPP, String observacionesEPP,
                          String propuestaMejoraEPP, String accionEPP, String responsableEPP,
                          String cumpleHerramientas, String valorDeReferenciaHerramientas, String observacionesHerramientas,
                          String propuestaMejoraHerramientas, String accionHerramientas, String responsableHerramientas,
                          String cumplePersonalMinimo, String valorDeReferenciaPersonalMinimo, String observacionesPersonalMinimo,
                          String propuestaMejoraPersonalMinimo, String accionPersonalMinimo, String responsablePersonalMinimo,
                          String cumpleDesviaciones, String valorDeReferenciaDesviaciones, String observacionesDesviaciones,
                          String propuestaMejoraDesviaciones, String accionDesviaciones, String responsableDesviaciones,
                          String cumplePasoAPaso, String valorDeReferenciaPasoAPaso, String observacionesPasoAPaso,
                          String propuestaMejoraPasoAPaso, String accionPasoAPaso, String responsablePasoAPaso,
                          String cumpleDocumentosRelacionados, String valorDeReferenciaDocumentosRelacionados, String observacionesDocumentosRelacionados,
                          String propuestaMejoraDocumentosRelacionados, String accionDocumentosRelacionados, String responsableDocumentosRelacionados,
                          String cumpleControlRegistro, String valorDeReferenciaControlRegistro, String observacionesControlRegistro,
                          String propuestaMejoraControlRegistro, String accionControlRegistro, String responsableControlRegistro,
                          String cumpleValidacion, String valorDeReferenciaValidacion, String observacionesValidacion,
                          String propuestaMejoraValidacion, String accionValidacion, String responsableValidacion,
                          String cumpleAprobado, String valorDeReferenciaAprobado, String observacionesAprobado,
                          String propuestaMejoraAprobado, String accionAprobado, String responsableAprobado,
                          String cumpleMOC, String valorDeReferenciaMOC, String observacionesMOC,
                          String propuestaMejoraMOC, String accionMOC, String responsableMOC,
                          String cumplePruebaMOC, String valorDeReferenciaPruebaMOC, String observacionesPruebaMOC,
                          String propuestaMejoraPruebaMOC, String accionPruebaMOC, String responsablePruebaMOC,
                          String cumpleUltimaRevision, String valorDeReferenciaUltimaRevision, String observacionesUltimaRevision,
                          String propuestaMejoraUltimaRevision, String accionUltimaRevision, String responsableUltimaRevision,
                          String cumpleCopiaControlada, String valorDeReferenciaCopiaControlada, String observacionesCopiaControlada,
                          String propuestaMejoraCopiaControlada, String accionCopiaControlada, String responsableCopiaControlada,
                          String cumpleTest, String valorDeReferenciaTest, String observacionesTest,
                          String propuestaMejoraTest, String accionTest, String responsableTest, Integer target, String usuario){

        SQLiteDatabase BaseDeDatos=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("site", site);
        values.put("fecha", fecha);
        values.put("observadorUno", observadorUno);
        values.put("observadorDos", observadorDos);
        values.put("nombreDelProcedimiento", nombreProcedimiento);
        values.put("ejecutante", ejecutante);
        values.put("sector", sector);
        values.put("cumpleAlcance", cumpleAlcance);
        values.put("valorReferenciaAlcance", valorDeReferenciaAlcance);
        values.put("observacionesAlcance", observacionesAlcance);
        values.put("propuestaMejoraAlcance", propuestaMejoraAlcance);
        values.put("accionAlcance", accionAlcance);
        values.put("responsableAlcance", responsableAlcance);
        values.put("cumpleCategoria", cumpleCategoria);
        values.put("valorReferenciaCategoria", valorDeReferenciaCategoria);
        values.put("observacionesCategoria", observacionesCategoria);
        values.put("propuestaMejoraCategoria", propuestaMejoraCategoria);
        values.put("accionCategoria", accionCategoria);
        values.put("responsableCategoria", responsableCategoria);
        values.put("cumpleAtributos", cumpleAtributos);
        values.put("valorReferenciaAtributos", valorDeReferenciaAtributos);
        values.put("observacionesAtributos", observacionesAtributos);
        values.put("propuestaMejoraAtributos", propuestaMejoraAtributos);
        values.put("accionAtributos", accionAtributos);
        values.put("responsableAtributos", responsableAtributos);
        values.put("cumpleRiesgosYPrecauciones", cumpleRiesgosYPrecauciones);
        values.put("valorReferenciaRiesgosYPrecauciones", valorDeReferenciaRiesgosYPrecauciones);
        values.put("observacionesRiesgosYPrecauciones", observacionesRiesgosYPrecauciones);
        values.put("propuestaMejoraRiesgosYPrecauciones", propuestaMejoraRiesgosYPrecauciones);
        values.put("accionRiesgosYPrecauciones", accionRiesgosYPrecauciones);
        values.put("responsableRiesgosYPrecauciones", responsableRiesgosYPrecauciones);
        values.put("cumpleEPP", cumpleEPP);
        values.put("valorReferenciaEPP", valorDeReferenciaEPP);
        values.put("observacionesEPP", observacionesEPP);
        values.put("propuestaMejoraEPP", propuestaMejoraEPP);
        values.put("accionEPPPruebaACampo", accionEPP);
        values.put("responsableEPP", responsableEPP);
        values.put("cumpleHerramientas", cumpleHerramientas);
        values.put("valorReferenciaHerramientas", valorDeReferenciaHerramientas);
        values.put("observacionesHerramientas", observacionesHerramientas);
        values.put("propuestaMejoraHerramientas", propuestaMejoraHerramientas);
        values.put("accionHerramientas", accionHerramientas);
        values.put("responsableHerramientas", responsableHerramientas);
        values.put("cumplePersonalMinimo", cumplePersonalMinimo);
        values.put("valorReferenciaPersonalMinimo", valorDeReferenciaPersonalMinimo);
        values.put("observacionesPersonalMinimo", observacionesPersonalMinimo);
        values.put("propuestaMejoraPersonalMinimo", propuestaMejoraPersonalMinimo);
        values.put("accionPersonalMinimo", accionPersonalMinimo);
        values.put("responsablePersonalMinimo", responsablePersonalMinimo);
        values.put("cumpleDesviaciones", cumpleDesviaciones);
        values.put("valorReferenciaDesviaciones", valorDeReferenciaDesviaciones);
        values.put("observacionesDesviaciones", observacionesDesviaciones);
        values.put("propuestaMejoraDesviaciones", propuestaMejoraDesviaciones);
        values.put("accionDesviaciones", accionDesviaciones);
        values.put("responsableDesviaciones", responsableDesviaciones);
        values.put("cumplePasoAPaso", cumplePasoAPaso);
        values.put("valorReferenciaPasoAPaso", valorDeReferenciaPasoAPaso);
        values.put("observacionesPasoAPaso", observacionesPasoAPaso);
        values.put("propuestaMejoraPasoAPaso", propuestaMejoraPasoAPaso);
        values.put("accionPasoAPaso", accionPasoAPaso);
        values.put("responsablePasoAPaso", responsablePasoAPaso);
        values.put("cumpleDocumentosRelacionados", cumpleDocumentosRelacionados);
        values.put("valorReferenciaDocumentosRelacionados", valorDeReferenciaDocumentosRelacionados);
        values.put("observacionesDocumentosRelacionados", observacionesDocumentosRelacionados);
        values.put("propuestaMejoraDocumentosRelacionados", propuestaMejoraDocumentosRelacionados);
        values.put("accionDocumentosRelacionados", accionDocumentosRelacionados);
        values.put("responsableDocumentosRelacionados", responsableDocumentosRelacionados);
        values.put("cumpleControlRegistro", cumpleControlRegistro);
        values.put("valorReferenciaControlRegistro", valorDeReferenciaControlRegistro);
        values.put("observacionesControlRegistro", observacionesControlRegistro);
        values.put("propuestaMejoraControlRegistro", propuestaMejoraControlRegistro);
        values.put("accionControlRegistro", accionControlRegistro);
        values.put("responsableControlRegistro", responsableControlRegistro);
        values.put("cumpleValidacion", cumpleValidacion);
        values.put("valorReferenciaValidacion", valorDeReferenciaValidacion);
        values.put("observacionesValidacion", observacionesValidacion);
        values.put("propuestaMejoraValidacion", propuestaMejoraValidacion);
        values.put("accionValidacion", accionValidacion);
        values.put("responsableValidacion", responsableValidacion);
        values.put("cumpleAprobado", cumpleAprobado);
        values.put("valorReferenciaAprobado", valorDeReferenciaAprobado);
        values.put("observacionesAprobado", observacionesAprobado);
        values.put("propuestaMejoraAprobado", propuestaMejoraAprobado);
        values.put("accionAprobado", accionAprobado);
        values.put("responsableAprobado", responsableAprobado);
        values.put("cumpleMOC", cumpleMOC);
        values.put("valorReferenciaMOC", valorDeReferenciaMOC);
        values.put("observacionesMOC", observacionesMOC);
        values.put("propuestaMejoraMOC", propuestaMejoraMOC);
        values.put("accionMOC", accionMOC);
        values.put("responsableMOC", responsableMOC);
        values.put("cumplePruebaMOC", cumplePruebaMOC);
        values.put("valorReferenciaPruebaMOC", valorDeReferenciaPruebaMOC);
        values.put("observacionesPruebaMOC", observacionesPruebaMOC);
        values.put("propuestaMejoraPruebaMOC", propuestaMejoraPruebaMOC);
        values.put("accionPruebaMOC", accionPruebaMOC);
        values.put("responsablePruebaMOC", responsablePruebaMOC);
        values.put("cumpleUltimaRevision", cumpleUltimaRevision);
        values.put("valorReferenciaUltimaRevision", valorDeReferenciaUltimaRevision);
        values.put("observacionesUltimaRevision", observacionesUltimaRevision);
        values.put("propuestaMejoraUltimaRevision", propuestaMejoraUltimaRevision);
        values.put("accionUltimaRevision", accionUltimaRevision);
        values.put("responsableUltimaRevision", responsableUltimaRevision);
        values.put("cumpleCopiaControlada", cumpleCopiaControlada);
        values.put("valorReferenciaCopiaControlada", valorDeReferenciaCopiaControlada);
        values.put("observacionesCopiaControlada", observacionesCopiaControlada);
        values.put("propuestaMejoraCopiaControlada", propuestaMejoraCopiaControlada);
        values.put("accionCopiaControlada", accionCopiaControlada);
        values.put("responsableCopiaControlada", responsableCopiaControlada);
        values.put("cumpleTest", cumpleTest);
        values.put("valorReferenciaTest", valorDeReferenciaTest);
        values.put("observacionesTest", observacionesTest);
        values.put("propuestaMejoraTest", propuestaMejoraTest);
        values.put("accionTest", accionTest);
        values.put("responsableTest", responsableTest);
        values.put("cumplimiento", target);
        values.put("usuario",usuario);
        String fh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        values.put("fechaHora", fh);
        values.put("syncro", 0);
        boolean resultado=BaseDeDatos.insert("PruebasACampo",null,values)>0;
        BaseDeDatos.close();
        return resultado;
    }

    public JSONArray getPruebasPendientes() {
        JSONArray rows = new JSONArray();
        String sql = "SELECT * FROM PruebasACampo WHERE syncro='0'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                try{
                    JSONObject row = new JSONObject();
                    row.put("id", cursor.getString(cursor.getColumnIndex("id")));
                    row.put("fechaHora", cursor.getString(cursor.getColumnIndex("fechaHora")));
                    row.put("site",cursor.getString(cursor.getColumnIndex("site")));
                    row.put("fecha",cursor.getString(cursor.getColumnIndex("fecha")));
                    row.put("observadorUno",cursor.getString(cursor.getColumnIndex("observadorUno")));
                    row.put("observadorDos",cursor.getString(cursor.getColumnIndex("observadorDos")));
                    row.put("nombreDelProcedimiento",cursor.getString(cursor.getColumnIndex("nombreDelProcedimiento")));
                    row.put("ejecutante",cursor.getString(cursor.getColumnIndex("ejecutante")));
                    row.put("sector",cursor.getString(cursor.getColumnIndex("sector")));
                    row.put("cumpleAlcance",cursor.getString(cursor.getColumnIndex("cumpleAlcance")));
                    row.put("valorReferenciaAlcance",cursor.getString(cursor.getColumnIndex("valorReferenciaAlcance")));
                    row.put("observacionesAlcance",cursor.getString(cursor.getColumnIndex("observacionesAlcance")));
                    row.put("propuestaMejoraAlcance",cursor.getString(cursor.getColumnIndex("propuestaMejoraAlcance")));
                    row.put("accionAlcance",cursor.getString(cursor.getColumnIndex("accionAlcance")));
                    row.put("responsableAlcance",cursor.getString(cursor.getColumnIndex("responsableAlcance")));
                    row.put("cumpleCategoria",cursor.getString(cursor.getColumnIndex("cumpleCategoria")));
                    row.put("valorReferenciaCategoria",cursor.getString(cursor.getColumnIndex("valorReferenciaCategoria")));
                    row.put("observacionesCategoria",cursor.getString(cursor.getColumnIndex("observacionesCategoria")));
                    row.put("propuestaMejoraCategoria",cursor.getString(cursor.getColumnIndex("propuestaMejoraCategoria")));
                    row.put("accionCategoria",cursor.getString(cursor.getColumnIndex("accionCategoria")));
                    row.put("responsableCategoria",cursor.getString(cursor.getColumnIndex("responsableCategoria")));
                    row.put("cumpleAtributos",cursor.getString(cursor.getColumnIndex("cumpleAtributos")));
                    row.put("valorReferenciaAtributos",cursor.getString(cursor.getColumnIndex("valorReferenciaAtributos")));
                    row.put("observacionesAtributos",cursor.getString(cursor.getColumnIndex("observacionesAtributos")));
                    row.put("propuestaMejoraAtributos",cursor.getString(cursor.getColumnIndex("propuestaMejoraAtributos")));
                    row.put("accionAtributos",cursor.getString(cursor.getColumnIndex("accionAtributos")));
                    row.put("responsableAtributos",cursor.getString(cursor.getColumnIndex("responsableAtributos")));
                    row.put("cumpleRiesgosYPrecauciones",cursor.getString(cursor.getColumnIndex("cumpleRiesgosYPrecauciones")));
                    row.put("valorReferenciaRiesgosYPrecauciones",cursor.getString(cursor.getColumnIndex("valorReferenciaRiesgosYPrecauciones")));
                    row.put("observacionesRiesgosYPrecauciones",cursor.getString(cursor.getColumnIndex("observacionesRiesgosYPrecauciones")));
                    row.put("propuestaMejoraRiesgosYPrecauciones",cursor.getString(cursor.getColumnIndex("propuestaMejoraRiesgosYPrecauciones")));
                    row.put("accionRiesgosYPrecauciones",cursor.getString(cursor.getColumnIndex("accionRiesgosYPrecauciones")));
                    row.put("responsableRiesgosYPrecauciones",cursor.getString(cursor.getColumnIndex("responsableRiesgosYPrecauciones")));
                    row.put("cumpleEPP",cursor.getString(cursor.getColumnIndex("cumpleEPP")));
                    row.put("valorReferenciaEPP",cursor.getString(cursor.getColumnIndex("valorReferenciaEPP")));
                    row.put("observacionesEPP",cursor.getString(cursor.getColumnIndex("observacionesEPP")));
                    row.put("propuestaMejoraEPP",cursor.getString(cursor.getColumnIndex("propuestaMejoraEPP")));
                    row.put("accionEPPPruebaACampo",cursor.getString(cursor.getColumnIndex("accionEPPPruebaACampo")));
                    row.put("responsableEPP",cursor.getString(cursor.getColumnIndex("responsableEPP")));
                    row.put("cumpleHerramientas",cursor.getString(cursor.getColumnIndex("cumpleHerramientas")));
                    row.put("valorReferenciaHerramientas",cursor.getString(cursor.getColumnIndex("valorReferenciaHerramientas")));
                    row.put("observacionesHerramientas",cursor.getString(cursor.getColumnIndex("observacionesHerramientas")));
                    row.put("propuestaMejoraHerramientas",cursor.getString(cursor.getColumnIndex("propuestaMejoraHerramientas")));
                    row.put("accionHerramientas",cursor.getString(cursor.getColumnIndex("accionHerramientas")));
                    row.put("responsableHerramientas",cursor.getString(cursor.getColumnIndex("responsableHerramientas")));
                    row.put("cumplePersonalMinimo",cursor.getString(cursor.getColumnIndex("cumplePersonalMinimo")));
                    row.put("valorReferenciaPersonalMinimo",cursor.getString(cursor.getColumnIndex("valorReferenciaPersonalMinimo")));
                    row.put("observacionesPersonalMinimo",cursor.getString(cursor.getColumnIndex("observacionesPersonalMinimo")));
                    row.put("propuestaMejoraPersonalMinimo",cursor.getString(cursor.getColumnIndex("propuestaMejoraPersonalMinimo")));
                    row.put("accionPersonalMinimo",cursor.getString(cursor.getColumnIndex("accionPersonalMinimo")));
                    row.put("responsablePersonalMinimo",cursor.getString(cursor.getColumnIndex("responsablePersonalMinimo")));
                    row.put("cumpleDesviaciones",cursor.getString(cursor.getColumnIndex("cumpleDesviaciones")));
                    row.put("valorReferenciaDesviaciones",cursor.getString(cursor.getColumnIndex("valorReferenciaDesviaciones")));
                    row.put("observacionesDesviaciones",cursor.getString(cursor.getColumnIndex("observacionesDesviaciones")));
                    row.put("propuestaMejoraDesviaciones",cursor.getString(cursor.getColumnIndex("propuestaMejoraDesviaciones")));
                    row.put("accionDesviaciones",cursor.getString(cursor.getColumnIndex("accionDesviaciones")));
                    row.put("responsableDesviaciones",cursor.getString(cursor.getColumnIndex("responsableDesviaciones")));
                    row.put("cumplePasoAPaso",cursor.getString(cursor.getColumnIndex("cumplePasoAPaso")));
                    row.put("valorReferenciaPasoAPaso",cursor.getString(cursor.getColumnIndex("valorReferenciaPasoAPaso")));
                    row.put("observacionesPasoAPaso",cursor.getString(cursor.getColumnIndex("observacionesPasoAPaso")));
                    row.put("propuestaMejoraPasoAPaso",cursor.getString(cursor.getColumnIndex("propuestaMejoraPasoAPaso")));
                    row.put("accionPasoAPaso",cursor.getString(cursor.getColumnIndex("accionPasoAPaso")));
                    row.put("responsablePasoAPaso",cursor.getString(cursor.getColumnIndex("responsablePasoAPaso")));
                    row.put("cumpleDocumentosRelacionados",cursor.getString(cursor.getColumnIndex("cumpleDocumentosRelacionados")));
                    row.put("valorReferenciaDocumentosRelacionados",cursor.getString(cursor.getColumnIndex("valorReferenciaDocumentosRelacionados")));
                    row.put("observacionesDocumentosRelacionados",cursor.getString(cursor.getColumnIndex("observacionesDocumentosRelacionados")));
                    row.put("propuestaMejoraDocumentosRelacionados",cursor.getString(cursor.getColumnIndex("propuestaMejoraDocumentosRelacionados")));
                    row.put("accionDocumentosRelacionados",cursor.getString(cursor.getColumnIndex("accionDocumentosRelacionados")));
                    row.put("responsableDocumentosRelacionados",cursor.getString(cursor.getColumnIndex("responsableDocumentosRelacionados")));
                    row.put("cumpleControlRegistro",cursor.getString(cursor.getColumnIndex("cumpleControlRegistro")));
                    row.put("valorReferenciaControlRegistro",cursor.getString(cursor.getColumnIndex("valorReferenciaControlRegistro")));
                    row.put("observacionesControlRegistro",cursor.getString(cursor.getColumnIndex("observacionesControlRegistro")));
                    row.put("propuestaMejoraControlRegistro",cursor.getString(cursor.getColumnIndex("propuestaMejoraControlRegistro")));
                    row.put("accionControlRegistro",cursor.getString(cursor.getColumnIndex("accionControlRegistro")));
                    row.put("responsableControlRegistro",cursor.getString(cursor.getColumnIndex("responsableControlRegistro")));
                    row.put("cumpleValidacion",cursor.getString(cursor.getColumnIndex("cumpleValidacion")));
                    row.put("valorReferenciaValidacion",cursor.getString(cursor.getColumnIndex("valorReferenciaValidacion")));
                    row.put("observacionesValidacion",cursor.getString(cursor.getColumnIndex("observacionesValidacion")));
                    row.put("propuestaMejoraValidacion",cursor.getString(cursor.getColumnIndex("propuestaMejoraValidacion")));
                    row.put("accionValidacion",cursor.getString(cursor.getColumnIndex("accionValidacion")));
                    row.put("responsableValidacion",cursor.getString(cursor.getColumnIndex("responsableValidacion")));
                    row.put("cumpleAprobado",cursor.getString(cursor.getColumnIndex("cumpleAprobado")));
                    row.put("valorReferenciaAprobado",cursor.getString(cursor.getColumnIndex("valorReferenciaAprobado")));
                    row.put("observacionesAprobado",cursor.getString(cursor.getColumnIndex("observacionesAprobado")));
                    row.put("propuestaMejoraAprobado",cursor.getString(cursor.getColumnIndex("propuestaMejoraAprobado")));
                    row.put("accionAprobado",cursor.getString(cursor.getColumnIndex("accionAprobado")));
                    row.put("responsableAprobado",cursor.getString(cursor.getColumnIndex("responsableAprobado")));
                    row.put("cumpleMOC",cursor.getString(cursor.getColumnIndex("cumpleMOC")));
                    row.put("valorReferenciaMOC",cursor.getString(cursor.getColumnIndex("valorReferenciaMOC")));
                    row.put("observacionesMOC",cursor.getString(cursor.getColumnIndex("observacionesMOC")));
                    row.put("propuestaMejoraMOC",cursor.getString(cursor.getColumnIndex("propuestaMejoraMOC")));
                    row.put("accionMOC",cursor.getString(cursor.getColumnIndex("accionMOC")));
                    row.put("responsableMOC",cursor.getString(cursor.getColumnIndex("responsableMOC")));
                    row.put("cumplePruebaMOC",cursor.getString(cursor.getColumnIndex("cumplePruebaMOC")));
                    row.put("valorReferenciaPruebaMOC",cursor.getString(cursor.getColumnIndex("valorReferenciaPruebaMOC")));
                    row.put("observacionesPruebaMOC",cursor.getString(cursor.getColumnIndex("observacionesPruebaMOC")));
                    row.put("propuestaMejoraPruebaMOC",cursor.getString(cursor.getColumnIndex("propuestaMejoraPruebaMOC")));
                    row.put("accionPruebaMOC",cursor.getString(cursor.getColumnIndex("accionPruebaMOC")));
                    row.put("responsablePruebaMOC",cursor.getString(cursor.getColumnIndex("responsablePruebaMOC")));
                    row.put("cumpleUltimaRevision",cursor.getString(cursor.getColumnIndex("cumpleUltimaRevision")));
                    row.put("valorReferenciaUltimaRevision",cursor.getString(cursor.getColumnIndex("valorReferenciaUltimaRevision")));
                    row.put("observacionesUltimaRevision",cursor.getString(cursor.getColumnIndex("observacionesUltimaRevision")));
                    row.put("propuestaMejoraUltimaRevision",cursor.getString(cursor.getColumnIndex("propuestaMejoraUltimaRevision")));
                    row.put("accionUltimaRevision",cursor.getString(cursor.getColumnIndex("accionUltimaRevision")));
                    row.put("responsableUltimaRevision",cursor.getString(cursor.getColumnIndex("responsableUltimaRevision")));
                    row.put("cumpleCopiaControlada",cursor.getString(cursor.getColumnIndex("cumpleCopiaControlada")));
                    row.put("valorReferenciaCopiaControlada",cursor.getString(cursor.getColumnIndex("valorReferenciaCopiaControlada")));
                    row.put("observacionesCopiaControlada",cursor.getString(cursor.getColumnIndex("observacionesCopiaControlada")));
                    row.put("propuestaMejoraCopiaControlada",cursor.getString(cursor.getColumnIndex("propuestaMejoraCopiaControlada")));
                    row.put("accionCopiaControlada",cursor.getString(cursor.getColumnIndex("accionCopiaControlada")));
                    row.put("responsableCopiaControlada",cursor.getString(cursor.getColumnIndex("responsableCopiaControlada")));
                    row.put("cumpleTest",cursor.getString(cursor.getColumnIndex("cumpleTest")));
                    row.put("valorReferenciaTest",cursor.getString(cursor.getColumnIndex("valorReferenciaTest")));
                    row.put("observacionesTest",cursor.getString(cursor.getColumnIndex("observacionesTest")));
                    row.put("propuestaMejoraTest",cursor.getString(cursor.getColumnIndex("propuestaMejoraTest")));
                    row.put("accionTest",cursor.getString(cursor.getColumnIndex("accionTest")));
                    row.put("responsableTest",cursor.getString(cursor.getColumnIndex("responsableTest")));
                    row.put("cumplimiento",cursor.getString(cursor.getColumnIndex("cumplimiento")));
                    row.put("usuario", cursor.getString(cursor.getColumnIndex("usuario")));
                    rows.put(row);
                } catch (Exception e) {
                    //Log.e("getPruebasPendientes", e.getMessage().toString());
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rows;
    }

    public Boolean setPruebasPendientes(String id) {
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
            success = db.update("PruebasACampo", values, where, whereArgs) > 0;
        } catch (Exception e) {
            success = false;
        }
        //}
        db.close();
        return success;
    }
}
