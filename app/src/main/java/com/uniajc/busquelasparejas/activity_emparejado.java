package com.uniajc.busquelasparejas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class activity_emparejado extends AppCompatActivity {
    public EditText edtCodigo, edtNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emparejado);

        edtCodigo = findViewById(R.id.edtCodigo);
        edtNombre = findViewById(R.id.edtNombre);
    }

    public void guardaUsuario (View v) {
        // Este método almacena el usuario dentro de la base de datos SQLite

        // Se inicializa la base de datos
        gestorDBTablaUsuario gbdTablaUsuario = new gestorDBTablaUsuario(this, "BaseDatosEmparejado", null ,1);

        // Se abre la base de datos para lectura y escritura
        SQLiteDatabase sqdBaseDatos = gbdTablaUsuario.getWritableDatabase();
        String codigoTexto = edtCodigo.getText().toString();

        int usrQuemadoCodigo = Integer.parseInt(codigoTexto);
        String usrQuemadoNombre = edtNombre.getText().toString();
        //String usrQuemadoNombre = "Rodrigo";
        int usrQuemadoPuntaje = 10;
        long resultado = 0;

        // Se instancia un objeto para tener en memoria los datos del nuevo usuario
        ContentValues cvRegistroTablaUsuario = new ContentValues();
        cvRegistroTablaUsuario.put("codigo", usrQuemadoCodigo);
        cvRegistroTablaUsuario.put("nombre", usrQuemadoNombre);
        cvRegistroTablaUsuario.put("puntaje", usrQuemadoPuntaje);

        // Se inserta el registro en la base de datos
        resultado = sqdBaseDatos.insert("usuario", null, cvRegistroTablaUsuario);
        sqdBaseDatos.close();

        if (resultado == -1) {
            Toast.makeText(this, "Se presentó un error al insertar el usuario", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Usuario insertado exitosamente!", Toast.LENGTH_LONG).show();
        }


        Intent intent = new Intent(activity_emparejado.this, MainActivity.class);
        //intent.putExtra("codigo", usrQuemadoNombre);
        intent.putExtra("codigoUsuario", codigoTexto.toString());
        startActivity(intent);
    }

    public void consultaUsuario (View v) {
        Cursor crsRegistroConsultaTabla;
        String codigoTexto = edtCodigo.getText().toString();

        int codigoAConsultar = Integer.parseInt(codigoTexto);
        // Se inicializa la base de datos
        gestorDBTablaUsuario gbdTablaUsuario = new gestorDBTablaUsuario(this, "BaseDatosEmparejado", null ,1);

        // Se abre la base de datos para lectura y escritura
        SQLiteDatabase sqdBaseDatos = gbdTablaUsuario.getWritableDatabase();
        crsRegistroConsultaTabla = sqdBaseDatos.rawQuery("select codigo, nombre, puntaje from usuario where codigo = " + codigoAConsultar, null);

        if (crsRegistroConsultaTabla.moveToFirst()) {
            Toast.makeText(this, "Usuario encontrado con estos datos. Código->" + crsRegistroConsultaTabla.getInt(0)
                    + ", Nombre->" + crsRegistroConsultaTabla.getString(1)
                    + ", Puntaje->" + crsRegistroConsultaTabla.getInt(2), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }

        // Se cierra la conexión a la base de datos
        sqdBaseDatos.close();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("codigoUsuario", codigoTexto.toString());
        startActivity(intent);
    }

}