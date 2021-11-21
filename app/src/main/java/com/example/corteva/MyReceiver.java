package com.example.corteva;

import android.content.Context;
import android.widget.Toast;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadServiceBroadcastReceiver;

public class MyReceiver extends UploadServiceBroadcastReceiver {
    @Override
    public void onProgress(Context context, UploadInfo uploadInfo) {
        // your implementation
    }

    @Override
    public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
        // your implementation
    }

    @Override
    public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
        try {
            String response = serverResponse.getBodyAsString();
            if (!response.contains("Error")){
                //Significa que se realizo OK
                /*if (uploadInfo.getUploadId().contains("setStop")){
                    Boolean success = new ControladorStop(context).setStopPendientes(response);
                }*/
                if (uploadInfo.getUploadId().contains("setStop")){
                    Boolean success = new ControladorSODA(context).setSODAPendientes(response);
                }else if(uploadInfo.getUploadId().contains("setSODA")){
                    Boolean success = new ControladorSODA(context).setSODAPendientes(response);
                }else if(uploadInfo.getUploadId().contains("setReportes")){
                    Boolean success = new ControladorReportes(context).setReportesPendientes(response);
                }else if(uploadInfo.getUploadId().contains("setSeguridad")){
                    Boolean success = new ControladorSeguridad(context).setSeguridadPendientes(response);
                }else if(uploadInfo.getUploadId().contains("setTopTen")){
                    Boolean success = new ControladorTopTen(context).setTopTenPendientes(response);
                }else if(uploadInfo.getUploadId().contains("setUAC")){
                    Boolean success = new ControladorUAC(context).setUACSincronizados(response);
                }else if(uploadInfo.getUploadId().contains("setPruebas")){
                    Boolean success = new ControladorPruebasACampo(context).setPruebasPendientes(response);
                }else if(uploadInfo.getUploadId().contains("setSAI")){
                    Boolean success = new ControladorSAI(context).setSAIPendientes(response);
                }else if(uploadInfo.getUploadId().contains("setComentario")){
                    Boolean success = new ControladorComentarios(context).setComentariosPendientes(response);
                }
            }
        } catch (Exception exc) {
            Toast.makeText(context, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelled(Context context, UploadInfo uploadInfo) {
        // your implementation
    }
}
