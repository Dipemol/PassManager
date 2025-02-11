package com.PassManager.PassManager.BaseDeDatos;

public class Constantes {

    //Nombre de la base de datos
    public static final String BD = "PassManager";

    //Version de la basse de datos
    public static final int BD_VERSION = 1;

    //Nombre de la tabla
    public static final String TABLE_NAME = "PASSWORD_TABLE";

    public static final String C_ID = "ID";
    public static final String C_TITULO = "TITULO";
    public static final String C_CUENTA = "CUENTA";
    public static final String C_NOMBRE_USUARIO = "NOMBRE_USUARIO";
    public static final String C_PASSWORD = "PASSWORD";
    public static final String C_SITIO_WEB = "SITIO_WEB";
    public static final String C_NOTA = "NOTA";
    public static final String C_TIEMPO_REGISTRO = "TIEMPO_REGISTRO";
    public static final String C_TIEMPO_ACTUALIZACION = "TIEMPO_ACTUALIZACION";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_TITULO + " TEXT,"
            + C_CUENTA + " TEXT,"
            + C_NOMBRE_USUARIO + " TEXT,"
            + C_PASSWORD + " TEXT,"
            + C_SITIO_WEB + " TEXT,"
            + C_NOTA + " TEXT,"
            + C_TIEMPO_REGISTRO + " TEXT,"
            + C_TIEMPO_ACTUALIZACION + " TEXT"
            + ")";
}
