package com.uniajc.busquelasparejas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class gestorDBTablaUsuario extends SQLiteOpenHelper {
    public gestorDBTablaUsuario(@Nullable Context context, @Nullable String name,
                                @Nullable SQLiteDatabase.CursorFactory factory, int version){
        // Este es el método constructor de la clase que se encargará de la tabla de USUARIO en la base de datos
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Este método se invoca automáticamente cuando la base de datos se crea por 1ra vez
        // Aquí se define la estructura de la tabla y también se puede hacer una carga inicial de datos
        db.execSQL("CREATE TABLE usuario (codigo integer primary key, nombre text, puntaje integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
