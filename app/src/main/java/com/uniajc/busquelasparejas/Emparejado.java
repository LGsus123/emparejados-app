package com.uniajc.busquelasparejas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Emparejado extends AppCompatActivity {

    private ImageView cuadro1, cuadro2, cuadro3, cuadro4, cuadro5, cuadro6, cuadroTocadoAnterior;
    private TextView txvPuntaje;
    private int[] imagenesPorCuadro;
    private int contadorToques = 0, primeraImagenDestapada;
    private String etiquetaTocadaAnterior ="";

    // Variables para controlar los puntos para el jugador si acierta o falla en el intento
    private int puntosAcierto = 10;
    private int puntosEquivocacion = -5;

    // Variable con el puntaje del jugador
    private int puntajeJuego = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emparejado);

        // Se enlaza la variable del modelo con el componente de la vista
        cuadro1 = findViewById(R.id.imv1);
        cuadro2 = findViewById(R.id.imv2);
        cuadro3 = findViewById(R.id.imv3);
        cuadro4 = findViewById(R.id.imv4);
        cuadro5 = findViewById(R.id.imv5);
        cuadro6 = findViewById(R.id.imv6);

        // Se inicializa el text view donde se muestra el puntaje
        txvPuntaje = findViewById(R.id.txvPuntajeJuego);

        // Se inicializan el nombre de las imágenes q mostrará cada cuadro
        imagenesPorCuadro = new int[6];
        imagenesPorCuadro[0] = R.drawable.i2;
        imagenesPorCuadro[1] = R.drawable.i3;
        imagenesPorCuadro[2] = R.drawable.i1;
        imagenesPorCuadro[3] = R.drawable.i1;
        imagenesPorCuadro[4] = R.drawable.i2;
        imagenesPorCuadro[5] = R.drawable.i3;

        // Se inicializa el contador de toques (clics)
        contadorToques = 0;
        primeraImagenDestapada = 0;
    }

    public void ClicEnCuadro(View v) {
        String etiquetaTocada = "";
        int idImagen = imagenesPorCuadro[2]; //[(idCuadro - 1)];
        boolean coincidio = false;
        ImageView cuadroTocado;

        cuadroTocado = (ImageView) v;
        etiquetaTocada = cuadroTocado.getTag().toString();

        // Se destapa la imágen respectiva
        cuadroTocado.setImageResource(imagenesPorCuadro[Integer.parseInt(etiquetaTocada)]);

        // Se incrementa el contador de toques (¿cuántos cuadros se han destapado?)
        contadorToques++;

        System.out.println("Contador de Toques->" + contadorToques);

        if (contadorToques == 1) {
            etiquetaTocadaAnterior = etiquetaTocada;
            cuadroTocadoAnterior = (ImageView) v;
        }
        else if (contadorToques == 2) {
            if (imagenesPorCuadro[Integer.parseInt(etiquetaTocada)] == imagenesPorCuadro[Integer.parseInt(etiquetaTocadaAnterior)]) {
                // El usuario si encontró la pareja

                // Se actualiza el puntaje
                puntajeJuego += puntosAcierto;
                Toast.makeText(getApplicationContext(), "Resultado-> MATCH, Pareja Completada!" + "\nPuntaje->" + puntajeJuego, Toast.LENGTH_LONG).show();
            }
            else {
                // El usuario se equivocó en este intento

                // Se actualiza el puntaje
                puntajeJuego += puntosEquivocacion;
                Toast.makeText(getApplicationContext(), "Resultado-> INCORRECTO, Pareja Fallida!" + "\nPuntaje->" + puntajeJuego, Toast.LENGTH_LONG).show();

                // Se vuelven a ocultar  las imágnes
                cuadroTocado.setImageResource(R.drawable.tapao);
                cuadroTocadoAnterior.setImageResource(R.drawable.tapao);
            }

            // Se actualiza el puntaje en la pantalla
            txvPuntaje.setText(String.valueOf(puntajeJuego));

            // Se reinicia el contador de toques
            contadorToques = 0;
        }
    }

    public void guardaUsuario (View v) {
        // Este método almacena el usuario dentro de la base de datos SQLite

        // Se inicializa la base de datos
        gestorDBTablaUsuario gbdTablaUsuario = new gestorDBTablaUsuario(this, "BaseDatosEmparejado", null ,1);

        // Se abre la base de datos para lectura y escritura
        SQLiteDatabase sqdBaseDatos = gbdTablaUsuario.getWritableDatabase();

        int usrQuemadoCodigo = 4;
        String usrQuemadoNombre = "Rodrigo";
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
    }

    public void consultaUsuario (View v) {
        Cursor crsRegistroConsultaTabla;
        int codigoAConsultar = 4;

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
    }
}