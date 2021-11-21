package com.example.corteva;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    public AdminSQLiteOpenHelper(Context context) {
        super(context,"ehss",null,13);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        try {

            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS usuarios(idUsuario INTEGER primary key AUTOINCREMENT, user text, password text, nombre text, site text )");
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS config(id INTEGER primary key, last_syncro DATETIME, bbpanual INT, bbpmes INT, bbpultimostres INT, momentosanual INT, momentosmes INT, momentosultimostres INT, pruebasanual INT, pruebasmes INT, pruebasultimostres INT, reportesanual INT, reportesmes INT, reportesultimostres INT, toptenanual INT, toptenmes INT, toptenultimostres INT, uacanual INT, uacmes INT, uacultimostres INT)");
            BaseDeDatos.execSQL("insert into config (id, last_syncro) values (1, '2018-12-01 10:00:00')");
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS tareas(idTarea INTEGER primary key AUTOINCREMENT, nombreTarea text, Activo INT )");
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS sectores(idSector INTEGER primary key AUTOINCREMENT, site text, nombre text, Activo INT)");
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS responsables(idResponsable INTEGER primary key AUTOINCREMENT, nombre text, Sector text, Activo INT )");
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS grupos(idGrupo INTEGER primary key, grupo text, Sector text, Activo INT)");
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS clasificacion(idClasificacion INTEGER primary key, Clasificacion text, Activo INT)");
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS sites(idSite INTEGER primary key, Site text, Activo INT)");
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS sai_desvios(idSai INTEGER primary key, Desvio text, Severidad Text, Activo INT)");

            //COMENTARIOS
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS comentarios(id INTEGER primary key AUTOINCREMENT, fechaHora DATETIME, Site text, Comentario TEXT, Usuario TEXT, syncro INTEGER )");

            //SAI
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS ehss_sai (id INTEGER primary key AUTOINCREMENT, fechaHora DATETIME," +
                    "Site TEXT, fecha TEXT, Hora_Inicio TEXT, Hora_Fin TEXT, Observadores TEXT, Total_SAI TEXT, Total_Empleados TEXT," +
                    "Total_Contratistas TEXT, usuario TEXT, syncro INTEGER)");

            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS ehss_sai_sectores (id INTEGER primary key AUTOINCREMENT, idSAI INTEGER, Sector TEXT," +
                    "Cantidad_Contratista INTEGER, Cantidad_Empleado INTEGER, Desvios TEXT, Observaciones TEXT, Total_SAI TEXT, Total_Empleados TEXT, Total_Contratistas TEXT)");

            //SODA
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS formularioSODA(id INTEGER primary key AUTOINCREMENT, site text, area text," +
                    "agregarProteccion text, cambioPosicion text, reacomodaTrabajo text, dejarTrabajar text, colocanTierras text, colocanBloqueos text," +
                    "cabeza text, ojosyCara text, oidos text, aparatoRespiratorio text, brazosyManos text, tronco text, piernasyPies text, " +
                    "golpearContraObjetos text, serGolpeadoContraObjetos text, atrapado text, caidas text, contactoTemperaturas text, contactoElectricidad text," +
                    "inhalacion text, absorcion text, ingestion text, sobreEsfuerzo text, movimientoRepetitivos text, posicionesIncomodas text, " +
                    "herramientasInadecuadas text, empleoIncorrecto text, empleoInseguro text, procedimientosInadecuados text, procedimientosDesconocidos text," +
                    "procedimientoNoCumplen text, localSucio text, localDesorganizado text, localFugas text, dispocisionInadecuada text," +
                    "caminaSinCelular text, sendasPeatonales text, pasamanos text, etActosSeguros text, etActosInseguros text, " +
                    "vehiculo text, montacargas1 text, montacargas2 text, montacargas3 text, " +
                    "etFechaSODA text, etFechaCreacion DATETIME, sector_empleado_observado TEXT, usuario text, syncro INTEGER)");

            //BBP YA NO SE USA SE DEJA POR COMPATIBILIDAD
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS formulariostop(id INTEGER primary key AUTOINCREMENT, fechaHora DATETIME," +
                    " Sector text, lugar text, cantPersonas text, tarea text, tipoEmpleado text, EPP1 text" +
                    ", EPP2 text, EPP3 text, Montacargas1 text, Montacargas2 text, Montacargas3 text, Montacargas4 text," +
                    " Montacargas5 text, Montacargas6 text, Vehiculo1 text, Vehiculo2 text, Vehiculo3 text, Vehiculo4 text," +
                    " Posiciones1 text, Posiciones2 text, Riesgos1 text, Riesgos4 text, Altura1 text, Altura2 text," +
                    " Altura4 text, aislacionEnergia3 text," +
                    " aislacionEnergia4 text, espacioConfinado1 text, otros1 text, otros2 text, otros3 text," +
                    " interacciones text, observaciones text, syncro INTEGER, site text, areaTrabajoLimpia text, candados text," +
                    " todosLosEquipos text, riesgoCaida text, HerramientasAdecuadas text, usoHerramientas text, HerramientasEstado text, usuario text)");
            //Momentos de Seguridad
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS momentosSeguridad(id INTEGER primary key AUTOINCREMENT, comentario text, tema text, " +
                    "responsable text, sector text, fechaHora DATETIME, syncro INTEGER, imagen text, filesPath text, site text, tipo text, " +
                    "grupo text, usuario text, fecha text)");
            //Reportes Seguridad
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS reportes(id INTEGER primary key AUTOINCREMENT, lugar text, nombreOperario text, " +
                    "funcionOperario text, describirOcurrido text, lesion text, parteCuerpoAfectada text, personalInvolucrado text, " +
                    "responsable text, sector text, fechaHora DATETIME, syncro INTEGER, imagen text, accionesChild text," +
                    "potencial text, site text, clasificacion text, empresa text, usuario text, fechahoraincidente text)");
            //UAC
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS uac(id INTEGER primary key AUTOINCREMENT, tipo TEXT, site text, fecha TEXT, " +
                    "fechaHoraCreacion DATETIME, sector text, estado text, usuarioCreacion text, " +
                    "lugar text, accion text, responsable text, vencimiento text, riesgo text, condicionInsegura text, " +
                    "comentarioCierre text, fechaHoraCierre DATETIME, usuarioCierre text, imagenUAC text," +
                    "imagenUACCIERRE text, numero text, Lider_recorrida TEXT, Participantes TEXT, Categoria TEXT, syncro INTEGER)");
            //TOPTEN
            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS TopTen(id INTEGER primary key AUTOINCREMENT, fechaHora DATETIME, sector text," +
                    "observador1 text, observador2 text, PTSFecha text, PTSConfecciono text, core1 text," +
                    "core2 text, core3 text, core4 text, core5 text, core6 text, core7 text, core8 text, core9 text," +
                    "core10 text,/*PTSValidezPermiso text," +
                    "PTSFirmaResponsable text,PTSSectorTrabajo text, PTSListadoTrabajo text, PTSDescripcionTrabajo text," +
                    "PTSInspeccionSector text, PTSRiesgosFisicos text, PTSRiesgosQuimicos text, PTSRiegosAmbientales text," +
                    "PTSEPPAdicionales text, PTSEntrenamiento text, PTSCierre text,*/ PTSObservaciones text," +
                    "TarjetasRojasFecha text, TarjetasRojasConfecciono text, TarjetasRojasObservaciones text," +
                    "TarjetasRojasCantTarj text, TarjetasRojasEquipoFuera text, TarjetasRojasRazon text, TarjetasRojasTarea text," +
                    "TarjetasRojasDpto text, TarjetasRojasAcepto text, TarjetasRojasApellido text, TarjetasRojasCancelacion text," +
                    "TarjetasRojasRepDeLaInst text, TarjetasRojasAislacionFisica text, TarjetasRojasRepTrabajo text, TarjetasRojasTarjetasRetiradas text," +
                    "TarjetasRojasNroTarjeta text, TarjetasRojasUbicacion text, TarjetasRojasColocadoPor text," +
                    "TarjetasRojasFechaRetiro text, TarjetasRojasRetirada text, TarjetasRojasConciliado text," +
                    "EspacioConfinadoFecha text, EspacioConfinadoConfecciono text," +
                    "EspacioConfinadoIdentificacionEspacioConfinado text, EspacioConfinadoPermisoAEC text," +
                    "EspacioConfinadoVigenciaDelPermiso text, EspacioConfinadoIngresoEspacioConfinado text," +
                    "EspacioConfinadoVigiaExterno text, EspacioConfinadoVigia text, EspacioConfinadoPersonalInvolucrado text," +
                    "EspacioConfinadoCheckListAdicionales text, EspacioConfinadoAislacion text," +
                    "EspacioConfinadoEquiposElectricos text, EspacioConfinadoLimpiezaVentilacion text," +
                    "EspacioConfinadoHerramientasEquipos text, EspacioConfinadoMonitoreo text, EspacioConfinadoEntrada text," +
                    "EspacioConfinadoRescate text, EspacioConfinadoFirmasResponsables, EspacioConfinadoObservaciones," +
                    "TrabajoEnAlturaFecha text, TrabajoEnAlturaConfecciono text, TrabajoEnAlturaIdentificacionSector text," +
                    "TrabajoEnAlturaPTSCaidas text, TrabajoEnAlturaVigenciaPT text, TrabajoEnAlturaEPPCaidas text, " +
                    "TrabajoEnAlturaEPPTrabajoAltura text, TrabajoEnAlturaAndamioHabilitado text, TrabajoEnAlturaBarandasCondiciones text," +
                    "TrabajoEnAlturaCondicionesEscaleras text, TrabajoEnAlturaEscaleraSujeta text, TrabajoEnAlturaSenalizacionSector text," +
                    "TrabajoEnAlturaOperarioAtado text, TrabajoEnAlturaHerramientasEquipos text, TrabajoEnAlturaDesenergizacion text," +
                    "TrabajoEnAlturaColocoGuardaCritica text, TrabajoEnAlturaGuardaCritica text, TrabajoEnAlturaFirmaResponsable text," +
                    "TrabajoEnAlturaObservaciones text," +
                    "AperturaLineaFecha text, AperturaLineaConfecciono text, AperturaLineaSector text, AperturaLineaEstadoLinea text," +
                    "AperturaLineaSenalizacion text, AperturaLineaAislacionLinea text, AperturaLineaIdentificacionApertura text," +
                    "AperturaLineaPuntosDificiles text, AperturaLineaSoldada text, AperturaLineaDesprezuracion text," +
                    "AperturaLineaLimpiezaLinea text, AperturaLineaInflamables text, AperturaLineaOtrosPeligros text," +
                    "AperturaLineaFirmasResponsables text, AperturaLineaObservaciones text," +
                    "TrabajoEnCalienteFecha text, TrabajoEnCalienteConfecciono text, TrabajoEnCalienteIdentificacionSector text," +
                    "TrabajoEnCalientePTSCaliente text, TrabajoEnCalienteVigenciaPT text, TrabajoEnCalienteEPPCaliente text," +
                    "TrabajoEnCalienteHabilitacionHerramientas text, TrabajoEnCalienteSenalizacionSector text," +
                    "TrabajoEnCalienteInflamable text, TrabajoEnCalienteCanerias text," +
                    "TrabajoEnCalienteObservadorFuego text, TrabajoEnCalienteEquipoExtinsion text," +
                    "TrabajoEnCalienteMonitoreoInflamable text, TrabajoEnCalienteEstadoEquipo text,TrabajoEnCalientePurgado text," +
                    "TrabajoEnCalienteBloqueosDobles text, TrabajoEnCalienteAltaEnergia text, TrabajoEnCalienteFirmasResponsables text," +
                    "TrabajoEnCalienteObservaciones text," +
                    "HidrolavadoraFecha text, HidrolavadoraConfecciono text, HidrolavadoraIdentificacionSector text, " +
                    "HidrolavadoraPTSAnalisisRiesgos text, HidrolavadoraVigenciaPT text, HidrolavadoraOperariosHabilitados text," +
                    "HidrolavadoraEPPAdecuados text, HidrolavadoraHabilitada text, HidrolavadoraValladoPerimetral text, " +
                    "HidrolavadoraMangueras text, HidrolavadoraVigiaPresente text, HidrolavadoraVigia text," +
                    "HidrolavadoraCheckListAdicionales text, HidrolavadoraHerramientasEquipos text, HidrolavadoraDesenergizacion text," +
                    "HidrolavadoraTempMax text, HidrolavadoraProteccionEquipos text, HidrolavadoraFirmasResponsables text," +
                    "HidrolavadoraObservaciones text, syncro INTEGER, imagenPTS TEXT, imagenTarjetasRojas TEXT, imagenEspacioConfinado TEXT," +
                    "imagenTrabajoEnAltura TEXT, imagenAperturaLinea TEXT, imagenTrabajoEnCaliente TEXT, imagenHidrolavadora TEXT, site text, usuario text)");

            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS PruebasACampo(id INTEGER primary key AUTOINCREMENT, fechaHora DATETIME, site text, " +
                    "fecha text, observadorUno text, observadorDos text, nombreDelProcedimiento text, ejecutante text, sector text," +
                    "cumpleAlcance text, valorReferenciaAlcance text, observacionesAlcance text, propuestaMejoraAlcance text, accionAlcance text," +
                    "responsableAlcance text," +
                    "cumpleCategoria text, valorReferenciaCategoria text, observacionesCategoria text, propuestaMejoraCategoria text, accionCategoria text," +
                    "responsableCategoria text," +
                    "cumpleAtributos text, valorReferenciaAtributos text, observacionesAtributos text, propuestaMejoraAtributos text, accionAtributos text," +
                    "responsableAtributos text," +
                    "cumpleRiesgosYPrecauciones text, valorReferenciaRiesgosYPrecauciones text, observacionesRiesgosYPrecauciones text, propuestaMejoraRiesgosYPrecauciones text, accionRiesgosYPrecauciones text," +
                    "responsableRiesgosYPrecauciones text," +
                    "cumpleEPP text, valorReferenciaEPP text, observacionesEPP text, propuestaMejoraEPP text, accionEPPPruebaACampo text," +
                    "responsableEPP text," +
                    "cumpleHerramientas text, valorReferenciaHerramientas text, observacionesHerramientas text, propuestaMejoraHerramientas text, accionHerramientas text," +
                    "responsableHerramientas text," +
                    "cumplePersonalMinimo text, valorReferenciaPersonalMinimo text, observacionesPersonalMinimo text, propuestaMejoraPersonalMinimo text, accionPersonalMinimo text," +
                    "responsablePersonalMinimo text," +
                    "cumpleDesviaciones text, valorReferenciaDesviaciones text, observacionesDesviaciones text, propuestaMejoraDesviaciones text, accionDesviaciones text," +
                    "responsableDesviaciones text," +
                    "cumplePasoAPaso text, valorReferenciaPasoAPaso text, observacionesPasoAPaso text, propuestaMejoraPasoAPaso text, accionPasoAPaso text," +
                    "responsablePasoAPaso text," +
                    "cumpleDocumentosRelacionados text, valorReferenciaDocumentosRelacionados text, observacionesDocumentosRelacionados text, propuestaMejoraDocumentosRelacionados text, accionDocumentosRelacionados text," +
                    "responsableDocumentosRelacionados text," +
                    "cumpleControlRegistro text, valorReferenciaControlRegistro text, observacionesControlRegistro text, propuestaMejoraControlRegistro text, accionControlRegistro text," +
                    "responsableControlRegistro text," +
                    "cumpleValidacion text, valorReferenciaValidacion text, observacionesValidacion text, propuestaMejoraValidacion text, accionValidacion text," +
                    "responsableValidacion text," +
                    "cumpleAprobado text, valorReferenciaAprobado text, observacionesAprobado text, propuestaMejoraAprobado text, accionAprobado text," +
                    "responsableAprobado text," +
                    "cumpleMOC text, valorReferenciaMOC text, observacionesMOC text, propuestaMejoraMOC text, accionMOC text," +
                    "responsableMOC text," +
                    "cumplePruebaMOC text, valorReferenciaPruebaMOC text, observacionesPruebaMOC text, propuestaMejoraPruebaMOC text, accionPruebaMOC text," +
                    "responsablePruebaMOC text," +
                    "cumpleUltimaRevision text, valorReferenciaUltimaRevision text, observacionesUltimaRevision text, propuestaMejoraUltimaRevision text, accionUltimaRevision text," +
                    "responsableUltimaRevision text," +
                    "cumpleCopiaControlada text, valorReferenciaCopiaControlada text, observacionesCopiaControlada text, propuestaMejoraCopiaControlada text, accionCopiaControlada text," +
                    "responsableCopiaControlada text," +
                    "cumpleTest text, valorReferenciaTest text, observacionesTest text, propuestaMejoraTest text, accionTest text," +
                    "responsableTest text, cumplimiento INTEGER, syncro INTEGER, usuario text)");

            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS miscargasdetalle(tipo TEXT, fechahora DATETIME, datos TEXT, usuario TEXT, UNIQUE(tipo, fechaHora))");

            BaseDeDatos.execSQL("CREATE TABLE IF NOT EXISTS calidad_nc(id INTEGER primary key AUTOINCREMENT, Lugar TEXT, Tipo TEXT, Fecha_carga DATETIME," +
                    "Fecha_deteccion DATETIME, Fecha_ocurrencia DATETIME, Procedencia TEXT, Impacto TEXT, Observaciones TEXT, Evidencia TEXT," +
                    "Emisores TEXT, Accion_inmediata TEXT, syncro INTEGER, usuario TEXT)");

        } catch (Exception er) {
            Log.e("Error", "Error al crear DB "+ er.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS config"); //para que no falle en linea 21
        if (oldVersion<10){
            // Drop older tables if existed
            db.execSQL("DROP TABLE IF EXISTS tareas");
            db.execSQL("DROP TABLE IF EXISTS sectores");
            db.execSQL("DROP TABLE IF EXISTS responsables");
            db.execSQL("DROP TABLE IF EXISTS grupos");
            db.execSQL("DROP TABLE IF EXISTS clasificacion");
            db.execSQL("DROP TABLE IF EXISTS formulariostop");
            db.execSQL("DROP TABLE IF EXISTS momentosSeguridad");
            db.execSQL("DROP TABLE IF EXISTS reportes");
            db.execSQL("DROP TABLE IF EXISTS formularioSODA");
            db.execSQL("DROP TABLE IF EXISTS uac");
            db.execSQL("DROP TABLE IF EXISTS TopTen");
            db.execSQL("DROP TABLE IF EXISTS PruebasACampo");
            db.execSQL("DROP TABLE IF EXISTS miscargasdetalle");
        }else if(oldVersion<13){
            db.execSQL("DROP TABLE IF EXISTS formularioSODA");
            db.execSQL("DROP TABLE IF EXISTS uac");
        }
        // Create tables again
        onCreate(db);
    }
}