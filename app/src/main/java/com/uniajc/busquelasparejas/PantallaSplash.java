package com.uniajc.busquelasparejas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PantallaSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_splash);

        // Se agrega el codeigo para causar el efecto de espera de la pantalla de Splahs
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Se invoca la actividad en memoria
                // 1er parametro la clase dle activity splash
                // 2do parametro la siguiente activity que se mostrara despeus del splash
                Intent ActivitySplash = new Intent(PantallaSplash.this, activity_emparejado.class);
                startActivity(ActivitySplash);
                finish();
            }
        }, 5000);
    }
}