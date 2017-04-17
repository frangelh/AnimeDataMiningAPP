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
    String secureToken;
    ProgressDialog progressDialog;
    String imagen;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    public EnviarImagen(Context ctx, String secureToken, ProgressDialog progressDialog, String imagen) {
        this.ctx = ctx;
        this.secureToken = secureToken;
        this.progressDialog = progressDialog;
        this.imagen = imagen;
    }

    @Override
    protected String doInBackground(String... urls) {
        JSONObject json = new JSONObject();
        try {
            json.put("imagen",imagen);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("JSON ENVIADO:"+json.toString());
        return HttpHelper.POST_JSON(urls[0], json.toString());
    }

    @Override
    protected void onPostExecute(final String result) {

            String mensaje = "";
                try {
                    JSONObject json = new JSONObject(result);
                    boolean esError = json.getBoolean("error");

                    if(esError){
                        mensaje = json.getJSONObject("errorTrama").getString("mensaje");
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

            Toast.makeText(ctx, "Terminando Inspeccion:"+mensaje, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();


    }
}
