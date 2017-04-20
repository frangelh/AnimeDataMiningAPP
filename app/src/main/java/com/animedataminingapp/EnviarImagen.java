package com.animedataminingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by frang on 12/13/2016.
 */

public class EnviarImagen extends AsyncTask<String,Void,String> {
    Context ctx;
    ProgressDialog progressDialog;
    Imagen imagen;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    public EnviarImagen(Context ctx, ProgressDialog progressDialog, Imagen imagen) {
        this.ctx = ctx;
        this.progressDialog = progressDialog;
        this.imagen = imagen;
    }

    @Override
    protected String doInBackground(String... urls) {
        //JSONObject json = new JSONObject();
        //ObjectMapper mapper = new ObjectMapper();
       /* try {
            //String valor = mapper.writeValueAsString(imagen);
            //json.put("imagen",valor);
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }*/
        System.out.println("SERVIDOR: "+urls[0]);
        //System.out.println("JSON ENVIADO:"+json.toString());
        System.out.println("Imagen Enviada: "+imagen.data);
        return HttpHelper.POST(urls[0], "imagen="+imagen.data);
    }

    @Override
    protected void onPostExecute(final String result) {

            String mensaje = "";
                try {
                    JSONObject json = new JSONObject(result);
                    boolean esError = json.getBoolean("error");

                    if(esError){
                        mensaje = json.getString("mensaje");
                    }else {
                     mensaje = " Correctamente...";
                    }

                    System.out.println("LOG REGISTRO LABOR TERMINADA: "+result);
                    mProgressStatus = 100;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        progressDialog.setProgress(mProgressStatus);
                    }
                });

            Toast.makeText(ctx, "Terminando Proceso:"+mensaje, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();


    }
}
