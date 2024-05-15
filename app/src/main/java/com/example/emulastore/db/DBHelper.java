package com.example.emulastore.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION =1;
    private static final String DB_NAME = "emulastore.db";
    public static final String TABLE_ROM = "ROM";
    public static final String TABLE_Emulador = "Emulador";
    public static final String TABLE_Usuario = "Usuario";
    public static final String TABLE_Perfil = "Perfil";
    public static final String TABLE_Historial = "HistorialDescargas";
    public static final String TABLE_Comentarios = "Comentarios";
    public static final String TABLE_Valoraciones = "Valoraciones";
    public static final String Categorias = "Categorias";



    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ROM + " (" +
                "idRom INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "url TEXT NOT NULL," +
                "urlimg TEXT NOT NULL," +
                "emulador TEXT NOT NULL," +
                "genero TEXT NOT NULL," +
                "Descargas INT DEFAULT 0," +
                "likes INT DEFAULT 0," +
                "dislike INT DEFAULT 0," +
                "descripcion TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_Emulador + " (" +
                "idEmulador INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "url TEXT NOT NULL," +
                "urlimg TEXT NOT NULL," +
                "tipo TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_Usuario + " (" +
                "idUsuario INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "idUsuarioRedSocial TEXT UNIQUE," +
                "nombreUsuario TEXT," +
                "email TEXT," +
                "fotoPerfil TEXT," +
                "tokenAcceso TEXT," +
                "fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        db.execSQL("CREATE TABLE  " + TABLE_Perfil + " (" +
                "idPerfil INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INT NOT NULL," +
                "nombreCompleto TEXT NOT NULL," +
                "fechaNacimiento DATE," +
                "genero TEXT," +
                "ciudad TEXT," +
                "pais TEXT," +
                "telefono TEXT," +
                "imagenPerfil TEXT," +
                "FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario))");
        db.execSQL("CREATE TABLE " + TABLE_Historial + " (" +
                "idDescarga INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INT NOT NULL," +
                "idRom INT," +
                "idEmulador INT," +
                "fechaDescarga TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)," +
                "FOREIGN KEY (idRom) REFERENCES Rom(idRom)," +
                "FOREIGN KEY (idEmulador) REFERENCES Emulador(idEmulador))");

        db.execSQL("CREATE TABLE " + TABLE_Comentarios + " (" +
                "idComentario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INT NOT NULL," +
                "idEntidad INT NOT NULL," +
                "tipoEntidad TEXT NOT NULL," +
                "contenido TEXT NOT NULL," +
                "fechaComentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (idUsuario) REFERENCES " + TABLE_Usuario + "(idUsuario)," +
                "FOREIGN KEY (idEntidad) REFERENCES " + TABLE_ROM + "(idRom)," +
                "FOREIGN KEY (tipoEntidad) REFERENCES " + TABLE_Emulador + "(idEmulador))");

        db.execSQL("CREATE TABLE " + TABLE_Valoraciones + " (" +
                "idValoracion INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INT NOT NULL," +
                "idEntidad INT NOT NULL," +
                "tipoEntidad TEXT NOT NULL," +
                "valor INT NOT NULL," +
                "fechaValoracion TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (idUsuario) REFERENCES " + TABLE_Usuario + "(idUsuario)," +
                "FOREIGN KEY (idEntidad, tipoEntidad) REFERENCES " + TABLE_ROM + "(idRom, tipoEntidad)," +
                "FOREIGN KEY (idEntidad, tipoEntidad) REFERENCES " + TABLE_Emulador + "(idEmulador, tipoEntidad))");

        db.execSQL("CREATE TABLE " + Categorias + " (" +
                "idCategoria INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Emulador);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Usuario);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Perfil);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Historial);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Comentarios);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Valoraciones);
        db.execSQL("DROP TABLE IF EXISTS " + Categorias);
        onCreate(db);
    }
}
