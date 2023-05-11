package com.uniajc.busquelasparejas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TableLayout;

import java.util.ArrayList;

public class tabla_posiciones extends AppCompatActivity {
    private TableLayout tableLayout;
    private EditText txtCodigo, txtNombre, txtPuntaje;
    private String[]header = {"Cód", "Nombre",  "Apellido"};
    private ArrayList<String[]> rows = new ArrayList<>();
    private TableDynamic tableDynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_posiciones);
        tableLayout=(TableLayout)findViewById(R.id.table);
        //txtCodigo=(EditText) findViewById(R.id.txtCodigo);
        //txtNombre=(EditText) findViewById(R.id.txtNombre);
        //txtPuntaje=(EditText) findViewById(R.id.txtPuntaje);

        tableDynamic = new TableDynamic(tableLayout, getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(getClients());
        tableDynamic.backgroundHeader(Color.rgb(0,51,102));   // Azul más tenue
        tableDynamic.backgroundData(Color.rgb(255, 255, 255), Color.rgb(224, 224, 224));   // Rojo y amarillo más tenues
        tableDynamic.lineColor(Color.rgb(128, 128, 128));   // Negro más tenue
        tableDynamic.textColorData(Color.rgb(0, 0, 51));   // Blanco más tenue
        tableDynamic.textColorHeader(Color.rgb(255, 255, 255));   // Magenta más tenue

    }

    public void save(View view){
        String[]item = new String[]{"5", txtCodigo.getText().toString(), txtNombre.getText().toString()};
        tableDynamic.addItems(item);
    }

    private ArrayList<String[]> getClients(){
        rows.add(new String[]{"1", "Pedro", "52"});
        rows.add(new String[]{"2", "Jesu", "23"});
        rows.add(new String[]{"3", "Dario", "12"});
        rows.add(new String[]{"4", "Maria", "22"});
        return rows;
    }

    public void volver(View V){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}