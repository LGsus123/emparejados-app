package com.uniajc.busquelasparejas;

import androidx.appcompat.app.AppCompatActivity;

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
        txtCodigo=(EditText) findViewById(R.id.txtCodigo);
        txtNombre=(EditText) findViewById(R.id.txtNombre);
        txtPuntaje=(EditText) findViewById(R.id.txtPuntaje);

        tableDynamic = new TableDynamic(tableLayout, getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(getClients());
        tableDynamic.backgroundHeader(Color.BLUE);
        tableDynamic.backgroundData(Color.RED, Color.YELLOW);
        tableDynamic.lineColor(Color.BLACK);
        tableDynamic.textColorData(Color.WHITE);
        tableDynamic.textColorHeader(Color.MAGENTA);
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
}