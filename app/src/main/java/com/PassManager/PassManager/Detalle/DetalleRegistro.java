package com.PassManager.PassManager.Detalle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.PassManager.PassManager.BaseDeDatos.BDHelper;
import com.PassManager.PassManager.BaseDeDatos.Constantes;
import com.PassManager.PassManager.R;

import java.util.Calendar;
import java.util.Locale;

public class DetalleRegistro extends AppCompatActivity {

    TextView D_Titulo, D_Cuenta, D_NombreUsuario, D_SitioWeb, D_Nota, D_TiempoRegistro, D_TiempoActualizacion;
    EditText  D_Password;
    String idRegistro;
    BDHelper helper;

    ImageButton Im_Ir_Web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_detalle_registro);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        idRegistro = intent.getStringExtra("IdRegistro");

        helper = new BDHelper(this);
        inicializaVistas();
        mostrarInformacionRegistro();

        String tituloRegistro = D_Titulo.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(tituloRegistro);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        Im_Ir_Web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = D_SitioWeb.getText().toString().trim();
                
                if (!url.equals("")){
                    abrirWeb(url);
                }else {
                    Toast.makeText(DetalleRegistro.this, "No existe una url", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirWeb(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + url));
        startActivity(intent);
    }

    private void mostrarInformacionRegistro(){
        String consulta = "SELECT * FROM "+ Constantes.TABLE_NAME + " WHERE " + Constantes.C_ID + " =\"" + idRegistro + "\"";
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta,null);

        if (cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String id = "" + cursor.getInt(cursor.getColumnIndex(Constantes.C_ID));
                @SuppressLint("Range") String titulo = "" + cursor.getString(cursor.getColumnIndex(Constantes.C_TITULO));
                @SuppressLint("Range") String cuenta = "" + cursor.getString(cursor.getColumnIndex(Constantes.C_CUENTA));
                @SuppressLint("Range") String nombreUsuario = "" + cursor.getString(cursor.getColumnIndex(Constantes.C_NOMBRE_USUARIO));
                @SuppressLint("Range") String password = "" + cursor.getString(cursor.getColumnIndex(Constantes.C_PASSWORD));
                @SuppressLint("Range") String sitio_web = "" + cursor.getString(cursor.getColumnIndex(Constantes.C_SITIO_WEB));
                @SuppressLint("Range") String nota = "" + cursor.getString(cursor.getColumnIndex(Constantes.C_NOTA));
                @SuppressLint("Range") String tRegistro = "" + cursor.getString(cursor.getColumnIndex(Constantes.C_TIEMPO_REGISTRO));
                @SuppressLint("Range") String tActualizacion = "" + cursor.getString(cursor.getColumnIndex(Constantes.C_TIEMPO_ACTUALIZACION));

                // Tiempo Registro
                Calendar calendarTR = Calendar.getInstance(Locale.getDefault());
                calendarTR.setTimeInMillis(Long.parseLong(tRegistro));
                String tiempoRegistro = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendarTR);

                // Tiempo Actualizacion
                Calendar calendarTA = Calendar.getInstance(Locale.getDefault());
                calendarTA.setTimeInMillis(Long.parseLong(tActualizacion));
                String tiempoActualizacion = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendarTA);

                // Asignar la informacion en las vistas
                D_Titulo.setText(titulo);
                D_Cuenta.setText(cuenta);
                D_NombreUsuario.setText(nombreUsuario);
                D_Password.setText(password);
                D_Password.setEnabled(false);
                D_Password.setBackgroundColor(Color.TRANSPARENT);
                D_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                D_SitioWeb.setText(sitio_web);
                D_Nota.setText(nota);
                D_TiempoRegistro.setText(tiempoRegistro);
                D_TiempoActualizacion.setText(tiempoActualizacion);
            }while (cursor.moveToNext());
        }
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void inicializaVistas(){
        D_Titulo = findViewById(R.id.D_Titulo);
        D_Cuenta = findViewById(R.id.D_Cuenta);
        D_NombreUsuario = findViewById(R.id.D_NombreUsuario);
        D_Password = findViewById(R.id.D_Password);
        D_SitioWeb = findViewById(R.id.D_SitioWeb);
        D_Nota = findViewById(R.id.D_Nota);
        D_TiempoRegistro = findViewById(R.id.D_TiempoRegistro);
        D_TiempoActualizacion = findViewById(R.id.D_TiempoActualizacion);
        Im_Ir_Web = findViewById(R.id.Im_Ir_Web);
    }
}