package com.PassManager.PassManager.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.PassManager.PassManager.BaseDeDatos.BDHelper;
import com.PassManager.PassManager.BaseDeDatos.Constantes;
import com.PassManager.PassManager.MainActivity;
import com.PassManager.PassManager.Modelo.Password;
import com.PassManager.PassManager.R;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class F_Ajustes extends Fragment {

    TextView eliminarRegistros, exportarArchivos, importarArchivos;
    Dialog dialog;
    BDHelper helper;
    String ordenarAsc = Constantes.C_TITULO + " ASC";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        View view = inflater.inflate(R.layout.fragment_f__ajustes, container, false);
        eliminarRegistros = view.findViewById(R.id.Eliminar_Todos_Registros);
        exportarArchivos = view.findViewById(R.id.Exportar_Archivo);
        importarArchivos = view.findViewById(R.id.Importar_Archivo);
        dialog = new Dialog(getActivity());
        helper = new BDHelper(getActivity());

        eliminarRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEliminarRegistros();
            }
        });

        exportarArchivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Exportar archivo", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    exportar();
                }else {
                    permisoExportar.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });

        importarArchivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("¿Importar CSV?");
                builder.setMessage("Se eliminarán todos los registros actuales");
                builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                            helper.eliminarTodosRegistros();
                            importar();
                        }else {
                            permisoImportar.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Importación cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }
        });

        return view;
    }

    private void importar() {
        String carpeta = Environment.getExternalStorageDirectory() + "/Documents/" + "/PassManager/" + "Registros.csv";
        File file = new File(carpeta);
        if (file.exists()){
            try {
                CSVReader reader = new CSVReader(new FileReader(file.getAbsoluteFile()));
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null){
                    String ids = nextLine[0];
                    String titulo = nextLine[1];
                    String cuenta = nextLine[2];
                    String nombre_usuario = nextLine[3];
                    String password = nextLine[4];
                    String sitioWeb = nextLine[5];
                    String nota = nextLine[6];
                    String tRegistro = nextLine[7];
                    String tActualizacion = nextLine[8];

                    long id = helper.insertarRegistro(""+titulo, ""+ cuenta, ""+ nombre_usuario, ""+ password, ""+sitioWeb, ""+ nota, ""+ tRegistro, ""+ tActualizacion);
                }
                Toast.makeText(getActivity(), "Importado con éxito", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(), "No existe un respaldo", Toast.LENGTH_SHORT).show();
        }
    }

    private void exportar() {
        File carpeta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "PassManager");
        boolean existe = false;

        if (!carpeta.exists()){
            existe = carpeta.mkdirs();
        }

        String csv = "Registros.csv";
        String carpetaArchivo = carpeta + "/" + csv;

        ArrayList<Password> registros = new ArrayList<>();
        registros.clear();
        registros = helper.obtenerRegistros(ordenarAsc);

        try {
            FileWriter fw = new FileWriter(carpetaArchivo);
            for (int i = 0; i < registros.size(); i++){
                fw.append("" + registros.get(i).getId());
                fw.append(",");
                fw.append("" + registros.get(i).getTitulo());
                fw.append(",");
                fw.append("" + registros.get(i).getCuenta());
                fw.append(",");
                fw.append("" + registros.get(i).getNombre_usuario());
                fw.append(",");
                fw.append("" + registros.get(i).getPassword());
                fw.append(",");
                fw.append("" + registros.get(i).getSitio_Web());
                fw.append(",");
                fw.append("" + registros.get(i).getNota());
                fw.append(",");
                fw.append("" + registros.get(i).getT_registro());
                fw.append(",");
                fw.append("" + registros.get(i).getT_actualizacion());
                fw.append("\n");
            }
            fw.flush();
            fw.close();
            Toast.makeText(getActivity(), "Se ha exportado el archivo", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ActivityResultLauncher<String> permisoExportar =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), Concede_Permiso_Exportar -> {
            if (Concede_Permiso_Exportar){
                exportar();
            }else {
                Toast.makeText(getActivity(), "Permiso Denegado", Toast.LENGTH_SHORT).show();
            }
        });

    private ActivityResultLauncher<String> permisoImportar =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), Concede_Permiso_Importar -> {
                if (Concede_Permiso_Importar){
                    importar();
                }else {
                    Toast.makeText(getActivity(), "Permiso Denegado", Toast.LENGTH_SHORT).show();
                }
            });



    private void dialogEliminarRegistros() {
        Button btnSi, btnCancelar;

        dialog.setContentView(R.layout.cuadro_dialogo_eliminar_todos_registros);
        btnSi = dialog.findViewById(R.id.Btn_Si);
        btnCancelar = dialog.findViewById(R.id.Btn_Cancelar);
        
        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.eliminarTodosRegistros();
                startActivity(new Intent(getActivity(), MainActivity.class));
                Toast.makeText(getActivity(), "Se han eliminado todos los registros", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Cancelar", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
    }
}