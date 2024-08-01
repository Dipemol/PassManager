package com.PassManager.PassManager.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.PassManager.PassManager.BaseDeDatos.BDHelper;
import com.PassManager.PassManager.Detalle.DetalleRegistro;
import com.PassManager.PassManager.MainActivity;
import com.PassManager.PassManager.Modelo.Password;
import com.PassManager.PassManager.OpcionesPassword.Agregar_Actualizar_Registro;
import com.PassManager.PassManager.R;

import java.util.ArrayList;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.HolderPassword> {

    private Context context;
    private ArrayList<Password> objects;
    Dialog dialog;
    BDHelper helper;
    public PasswordAdapter(Context context, ArrayList<Password> objects) {
        this.context = context;
        this.objects = objects;
        dialog = new Dialog(context);
        helper = new BDHelper(context);
    }

    @NonNull
    @Override
    public PasswordAdapter.HolderPassword onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.password_view_holder, parent, false);
        return new HolderPassword(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordAdapter.HolderPassword holder, @SuppressLint("RecyclerView") int position) {
        Password p = objects.get(position);
        String id = p.getId();
        String titulo = p.getTitulo();
        String cuenta = p.getCuenta();
        String nombre_usuario = p.getNombre_usuario();
        String password = p.getPassword();
        String sitio_web = p.getSitio_Web();
        String nota = p.getNota();
        String t_registro = p.getT_registro();
        String t_actualizacion = p.getT_actualizacion();

        holder.lbTitulo.setText(titulo);
        holder.lbCuenta.setText(cuenta);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetalleRegistro.class);
                intent.putExtra("IdRegistro", id);
                context.startActivity(intent);
            }
        });

        holder.ibMasOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarEliminar(""+position, id, titulo, cuenta, nombre_usuario, password, sitio_web, nota, t_registro, t_actualizacion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class HolderPassword extends RecyclerView.ViewHolder {

        TextView lbTitulo, lbCuenta, lbNombreUsuario, lbPassword, lbSitioWeb, lbNota;
        ImageButton ibMasOpciones;

        public HolderPassword(@NonNull View itemView) {
            super(itemView);
            lbTitulo = itemView.findViewById(R.id.Item_titulo);
            lbCuenta = itemView.findViewById(R.id.Item_cuenta);
            lbNombreUsuario = itemView.findViewById(R.id.Item_nombre_usuario);
            lbPassword = itemView.findViewById(R.id.Item_password);
            lbSitioWeb = itemView.findViewById(R.id.Item_sitio_web);
            lbNota = itemView.findViewById(R.id.Item_nota);
            ibMasOpciones = itemView.findViewById(R.id.Ib_mas_opciones);
        }
    }

    private void editarEliminar(String position, String id, String titulo, String cuenta, String nombre_usuario, String password, String sitio_web, String nota, String t_registro, String t_actualizacion){
        Button btnEditar, btnEliminar;

        dialog.setContentView(R.layout.cuadro_dialogo_editar_eliminar);
        btnEditar = dialog.findViewById(R.id.BtnEditarRegistro);
        btnEliminar = dialog.findViewById(R.id.BtnEliminarRegistro);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Agregar_Actualizar_Registro.class);
                intent.putExtra("POSICION", position);
                intent.putExtra("ID", id);
                intent.putExtra("TITULO", titulo);
                intent.putExtra("CUENTA", cuenta);
                intent.putExtra("NOMBRE_USUARIO", nombre_usuario);
                intent.putExtra("PASSWORD", password);
                intent.putExtra("SITIO_WEB", sitio_web);
                intent.putExtra("NOTA", nota);
                intent.putExtra("T_REGISTRO", t_registro);
                intent.putExtra("T_ACTUALIZACION", t_actualizacion);
                intent.putExtra("EDICION", true);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.eliminarRegistro(id);
                Intent intent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context, "Registro eliminado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(true);
    }
}
