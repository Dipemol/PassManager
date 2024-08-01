package com.PassManager.PassManager.BaseDeDatos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.PassManager.PassManager.Modelo.Password;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    public BDHelper(@Nullable Context context) {
        super(context, Constantes.BD, null, Constantes.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Constantes.TABLE_NAME);
        onCreate(db);
    }

    public long insertarRegistro(String titulo, String cuenta, String nombre_usuario, String password, String sitio_web, String nota, String T_registro, String T_Actualizacion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Insertar datos
        values.put(Constantes.C_TITULO, titulo);
        values.put(Constantes.C_CUENTA, cuenta);
        values.put(Constantes.C_NOMBRE_USUARIO, nombre_usuario);
        values.put(Constantes.C_PASSWORD, password);
        values.put(Constantes.C_SITIO_WEB, sitio_web);
        values.put(Constantes.C_NOTA, nota);
        values.put(Constantes.C_TIEMPO_REGISTRO, T_registro);
        values.put(Constantes.C_TIEMPO_ACTUALIZACION, T_Actualizacion);

        //Insertar fila
        long id = db.insert(Constantes.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public void actualizarRegistro(String id, String titulo, String cuenta, String nombre_usuario, String password, String sitio_web, String nota, String T_registro, String T_Actualizacion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Actualizar datos
        values.put(Constantes.C_TITULO, titulo);
        values.put(Constantes.C_CUENTA, cuenta);
        values.put(Constantes.C_NOMBRE_USUARIO, nombre_usuario);
        values.put(Constantes.C_PASSWORD, password);
        values.put(Constantes.C_SITIO_WEB, sitio_web);
        values.put(Constantes.C_NOTA, nota);
        values.put(Constantes.C_TIEMPO_REGISTRO, T_registro);
        values.put(Constantes.C_TIEMPO_ACTUALIZACION, T_Actualizacion);

        //Actualizar fila
        db.update(Constantes.TABLE_NAME, values, Constantes.C_ID + " =? ", new String[]{id});
        db.close();


    }

    public void eliminarRegistro(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constantes.TABLE_NAME, Constantes.C_ID + " = ?", new String[]{id});
        db.close();
    }
    public void eliminarTodosRegistros(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Constantes.TABLE_NAME);
        db.close();
    }
    public ArrayList<Password> obtenerRegistros(String ordenar){
        ArrayList<Password> passwords = new ArrayList<>();

        String sql = "SELECT * FROM " + Constantes.TABLE_NAME + " ORDER BY " + ordenar;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Password p = new Password(
                        ""+cursor.getInt(cursor.getColumnIndex(Constantes.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_TITULO)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_CUENTA)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_NOMBRE_USUARIO)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_PASSWORD)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_SITIO_WEB)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_NOTA)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_TIEMPO_REGISTRO)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_TIEMPO_ACTUALIZACION)));
                passwords.add(p);
            }while (cursor.moveToNext());
        }
        db.close();
        return passwords;
    }

    public ArrayList<Password> buscarRegistros(String consulta){
        ArrayList<Password> passwords = new ArrayList<>();

        String sql = "SELECT * FROM " + Constantes.TABLE_NAME + " WHERE " + Constantes.C_TITULO + " LIKE '%" + consulta + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Password p = new Password(
                        ""+cursor.getInt(cursor.getColumnIndex(Constantes.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_TITULO)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_CUENTA)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_NOMBRE_USUARIO)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_PASSWORD)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_SITIO_WEB)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_NOTA)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_TIEMPO_REGISTRO)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_TIEMPO_ACTUALIZACION)));
                passwords.add(p);
            }while (cursor.moveToNext());
        }
        db.close();
        return passwords;
    }
    public int obtenerNumeroRegistros(){
        String count = "SELECT * FROM " + Constantes.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(count, null);

        int contador = cursor.getCount();

        cursor.close();

        return contador;
    }
}
