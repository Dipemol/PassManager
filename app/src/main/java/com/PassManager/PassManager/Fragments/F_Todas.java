package com.PassManager.PassManager.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.PassManager.PassManager.Adapters.PasswordAdapter;
import com.PassManager.PassManager.BaseDeDatos.BDHelper;
import com.PassManager.PassManager.BaseDeDatos.Constantes;
import com.PassManager.PassManager.OpcionesPassword.Agregar_Actualizar_Registro;
import com.PassManager.PassManager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class F_Todas extends Fragment {
    RecyclerView recyclerView_Registros;
    FloatingActionButton agregarPassword;
    BDHelper helper;

    String ordenarNuevos = Constantes.C_TIEMPO_REGISTRO + " DESC";
    String ordenarPasados = Constantes.C_TIEMPO_REGISTRO + " ASC";
    String ordenarTituloASC = Constantes.C_TITULO + " ASC";
    String ordenarTituloDESC = Constantes.C_TITULO + " DESC";

    String estadoOrden = ordenarTituloASC;
    Dialog dialog, dialogOrdenar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        View view = inflater.inflate(R.layout.fragment_f__todas, container, false);

        recyclerView_Registros = view.findViewById(R.id.recyclerView_Registros);
        helper = new BDHelper(getActivity());
        dialog = new Dialog(getActivity());
        dialogOrdenar = new Dialog(getActivity());
        cargarRegistros(ordenarTituloASC);

        agregarPassword = view.findViewById(R.id.FAB_AgregarPassword);
        agregarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Agregar_Actualizar_Registro.class);
                intent.putExtra("EDICION",false);
                startActivity(intent);
            }
        });

        return view;
    }

    private void cargarRegistros(String orderby) {
        estadoOrden = orderby;
        PasswordAdapter adapter = new PasswordAdapter(getActivity(), helper.obtenerRegistros(orderby));
        recyclerView_Registros.setAdapter(adapter);
    }

    private void buscarRegistros(String consulta){
        PasswordAdapter adapter = new PasswordAdapter(getActivity(), helper.buscarRegistros(consulta));
        recyclerView_Registros.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragmento__todos, menu);

        MenuItem item = menu.findItem(R.id.menu_Buscar_registros);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscarRegistros(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarRegistros(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_Ordenar_Registros){
            ordenarRegistros();
            return true;
        }
        if (id == R.id.menu_Numero_Registros){
            totalRegistros();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        cargarRegistros(estadoOrden);
        super.onResume();
    }

    private void totalRegistros(){
        TextView Total;
        Button btnEntendidoTotal;

        dialog.setContentView(R.layout.cuadro_dialogo_total_registros);
        Total = dialog.findViewById(R.id.Total);
        btnEntendidoTotal = dialog.findViewById(R.id.BtnEntendidoTotal);

        int total = helper.obtenerNumeroRegistros();
        String total_string = String.valueOf(total);
        Total.setText(total_string);

        btnEntendidoTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
    }

    private void ordenarRegistros(){
        Button btnNuevos, btnPasados, btnAsc, btnDes;

        dialogOrdenar.setContentView(R.layout.cuadro_dialogo_ordenar_registros);

        btnNuevos = dialogOrdenar.findViewById(R.id.Btn_Nuevos);
        btnPasados = dialogOrdenar.findViewById(R.id.Btn_Pasados);
        btnAsc = dialogOrdenar.findViewById(R.id.Btn_Asc);
        btnDes = dialogOrdenar.findViewById(R.id.Btn_Des);
        
        btnNuevos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarRegistros(ordenarNuevos);
                dialogOrdenar.dismiss();
            }
        });
        btnPasados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarRegistros(ordenarPasados);
                dialogOrdenar.dismiss();
            }
        });
        btnAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarRegistros(ordenarTituloASC);
                dialogOrdenar.dismiss();
            }
        });
        btnDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarRegistros(ordenarTituloDESC);
                dialogOrdenar.dismiss();
            }
        });

        dialogOrdenar.show();
        dialogOrdenar.setCancelable(true);
    }
}