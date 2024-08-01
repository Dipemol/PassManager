package com.PassManager.PassManager.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.PassManager.PassManager.Login.Login;
import com.PassManager.PassManager.MainActivity;
import com.PassManager.PassManager.R;

public class Registro extends AppCompatActivity {

    EditText txtPassword, txtCPassword;
    Button btnRegistro;
    boolean dobleToqueParaSalir;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF = "mi_pref";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CPASSWORD = "c_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_registro);
        inicializaVistas();
        verificarPassword();

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPassword = txtPassword.getText().toString().trim();
                String sCPassword = txtCPassword.getText().toString().trim();

                if (TextUtils.isEmpty(sPassword)) {
                    Toast.makeText(Registro.this, "Introduce una contraseña", Toast.LENGTH_SHORT).show();
                } else if (sCPassword.length()<6) {
                    Toast.makeText(Registro.this, "Contraseña muy corta", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(sCPassword)) {
                    Toast.makeText(Registro.this, "Confirma la contraseña", Toast.LENGTH_SHORT).show();
                } else if (!sPassword.equals(sCPassword)) {
                    Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_CPASSWORD, sCPassword);
                    editor.putString(KEY_PASSWORD, sPassword);
                    editor.apply();

                    Intent intent = new Intent(Registro.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void verificarPassword(){
        String password = sharedPreferences.getString(KEY_PASSWORD, null);

        if (password != null){
            Intent intent = new Intent(Registro.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    private void inicializaVistas(){
        txtCPassword = findViewById(R.id.etPassword);
        txtPassword = findViewById(R.id.etPasswordU);
        btnRegistro = findViewById(R.id.BtnRegistrar);
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        if (dobleToqueParaSalir){
            super.onBackPressed();
            Toast.makeText(this, "Saliste de la aplicación", Toast.LENGTH_SHORT).show();
            return;
        }

        this.dobleToqueParaSalir = true;
        Toast.makeText(this, "Presiona dos veces para salir", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dobleToqueParaSalir = false;
            }
        }, 2000);
    }
}