package com.uniajc.busquelasparejas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        getPuntajes();

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

    private ArrayList<String[]> getPuntajes() {
        ArrayList<String[]> puntajes = new ArrayList<>();

        // Obtener una instancia de la base de datos en modo lectura
        gestorDBTablaUsuario dbHelper = new gestorDBTablaUsuario(this, "BaseDatosEmparejado", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Realizar la consulta a la tabla "usuario" y obtener los registros
        Cursor cursor = db.rawQuery("SELECT * FROM usuario", null);
        if (cursor.moveToFirst()) {
            do {
                String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String puntaje = cursor.getString(cursor.getColumnIndex("puntaje"));
                String[] registro = {codigo, nombre, puntaje};
                puntajes.add(registro);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();
        mostrarPuntajes(puntajes);
        return puntajes;
    }

    private void mostrarPuntajes(ArrayList<String[]> puntajes) {
        for (String[] puntaje : puntajes) {
            String codigo = puntaje[0];
            String nombre = puntaje[1];
            String puntajeStr = puntaje[2];
            System.out.println("Código: " + codigo + ", Nombre: " + nombre + ", Puntaje: " + puntajeStr);
        }
    }

    private void mostrarTablaPosiciones() {
       // TableLayout tableLayout = findViewById(R.id.tableLayout); // Reemplaza "R.id.tableLayout" con el ID correcto de tu TableLayout
        TableDynamic tableDynamic = new TableDynamic(tableLayout, this);
        String[] header = {"Código", "Nombre", "Puntaje"};
        tableDynamic.addHeader(header);

        ArrayList<String[]> puntajes = getPuntajes();
        tableDynamic.addData(puntajes);
    }


    public void volver(View V){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}