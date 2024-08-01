package com.PassManager.PassManager.OpcionesPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.PassManager.PassManager.BaseDeDatos.BDHelper;
import com.PassManager.PassManager.MainActivity;
import com.PassManager.PassManager.R;

public class Agregar_Actualizar_Registro extends AppCompatActivity {

    EditText txtTitulo;
    EditText txtCuenta;
    EditText txtNombreUsuario;
    EditText txtPassword;
    EditText txtSitioWeb;
    EditText txtNota;

    String id, titulo, cuenta, nombre_usuario, password, sitio_web, nota, tiempoRegistro, tiempoActualizacion;
    private boolean edicion = false;

    private BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_agregar_password);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        inicializaVistas();
        obtenerInformacion();
    }

    private void  obtenerInformacion(){
        Intent intent = getIntent();
        edicion = intent.getBooleanExtra("EDICION", false);

        if (edicion){
            id = intent.getStringExtra("ID");
            titulo = intent.getStringExtra("TITULO");
            cuenta = intent.getStringExtra("CUENTA");
            nombre_usuario = intent.getStringExtra("NOMBRE_USUARIO");
            password = intent.getStringExtra("PASSWORD");
            sitio_web = intent.getStringExtra("SITIO_WEB");
            nota = intent.getStringExtra("NOTA");
            tiempoRegistro = intent.getStringExtra("T_REGISTRO");
            tiempoActualizacion= intent.getStringExtra("T_ACTUALLIZACION");

            txtTitulo.setText(titulo);
            txtCuenta.setText(cuenta);
            txtNombreUsuario.setText(nombre_usuario);
            txtPassword.setText(password);
            txtSitioWeb.setText(sitio_web);
            txtNota.setText(nota);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Guardar_Password){
            agregarActualizarPassword();
        }
        return super.onOptionsItemSelected(item);
    }

    private void agregarActualizarPassword(){
        titulo = txtTitulo.getText().toString();
        cuenta = txtCuenta.getText().toString();
        nombre_usuario = txtNombreUsuario.getText().toString();
        password = txtPassword.getText().toString();
        sitio_web = txtSitioWeb.getText().toString();
        nota = txtNota.getText().toString();

        if (edicion){
            String tiempo = "" + System.currentTimeMillis();
            bdHelper.actualizarRegistro("" + id, "" + titulo, "" + cuenta, ""+ nombre_usuario, ""+ password, "" + sitio_web, "" + nota, ""+ tiempoRegistro, ""+ tiempo);
            Toast.makeText(this, "Se ha actualizado el registro", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Agregar_Actualizar_Registro.this, MainActivity.class));
            finish();
        }else {
            if (!titulo.equals("")){
                String tiempo = ""+System.currentTimeMillis();
                long id = bdHelper.insertarRegistro(""+titulo, ""+cuenta, ""+nombre_usuario, ""+password, ""+sitio_web,""+nota,""+tiempo,""+tiempo);
                Toast.makeText(this, "Se ha guardado el registro", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Agregar_Actualizar_Registro.this, MainActivity.class));
                finish();
            }else {
                txtTitulo.setError("Campo obligatorio");
                txtTitulo.setFocusable(true);
            }
        }
    }

    private void inicializaVistas(){
        txtTitulo = findViewById(R.id.txtTitulo);
        txtCuenta = findViewById(R.id.txtCuenta);
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        txtSitioWeb = findViewById(R.id.txtSitioWeb);
        txtNota = findViewById(R.id.txtNota);
        bdHelper = new BDHelper(this);
    }
}