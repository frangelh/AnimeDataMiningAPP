package com.animedataminingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.List;

/**
 * Created by frang on 1/31/2016.
 */
public class Helper {
    public static void showAlert(Context ctx, String texto){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(texto);
        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

}
