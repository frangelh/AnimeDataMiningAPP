package com.animedataminingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView mImageView;
    Button btnCamera;
    Button btnProcesar;
    ProgressDialog progressDialog;

    Bitmap imageBitmap;

    String servidor = "http://192.168.0.251:8080/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView)findViewById(R.id.iv_imagen);
        btnCamera = (Button)findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });
        btnProcesar = (Button)findViewById(R.id.btn_enviar);
        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();

                String imagenEnviar = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                BigDecimal cantidad = new BigDecimal(imageBytes.length);
                cantidad = cantidad.divide(new BigDecimal(1024));
                System.out.println("IMAGEN:"+imagenEnviar);
                // iniciando proceso hilo para consumir el servicio
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Enviando imagen al servidor");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Imagen img = new Imagen(imagenEnviar,cantidad);
                EnviarImagen enviar = new EnviarImagen(getBaseContext(),progressDialog,img);
                enviar.execute(servidor+"/procesarImagen");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);




        }
    }
    private void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
