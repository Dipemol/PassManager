package com.PassManager.PassManager.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.PassManager.PassManager.MainActivity;
import com.PassManager.PassManager.R;

public class Login extends AppCompatActivity {

    EditText txtPassword;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    boolean dobleToqueParaSalir;
    private static final String SHARED_PREF = "mi_pref";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_login);
        inicializaVistas();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPassword = txtPassword.getText().toString().trim();
                String passwordSP = sharedPreferences.getString(KEY_PASSWORD, null);

                if (sPassword.equals("")){
                    Toast.makeText(Login.this, "El campo es obligatorio", Toast.LENGTH_SHORT).show();
                } else if (!sPassword.equals(passwordSP)) {
                    Toast.makeText(Login.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void inicializaVistas(){
        txtPassword = findViewById(R.id.etPasswordU);
        btnLogin = findViewById(R.id.BtnEntrar);
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