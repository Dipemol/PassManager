package com.PassManager.PassManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;


import com.PassManager.PassManager.Fragments.F_Ajustes;
import com.PassManager.PassManager.Fragments.F_Todas;
import com.PassManager.PassManager.Login.Login;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    boolean dobleToqueParaSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Fragmento al iniciar la aplicacion
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new F_Todas()).commit();
            navigationView.setCheckedItem(R.id.Opcion_Todas);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Opcion_Todas){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new F_Todas()).commit();
        }
        if (id == R.id.Opcion_Ajustes){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new F_Ajustes()).commit();
        }
        if (id == R.id.Opcion_Salir){
            cerrarSesion();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrarSesion() {
        startActivity(new Intent(MainActivity.this, Login.class));
        Toast.makeText(this, "Cerraste sesión", Toast.LENGTH_SHORT).show();
        finish();
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