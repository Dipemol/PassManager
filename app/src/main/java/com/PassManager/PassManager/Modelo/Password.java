package com.PassManager.PassManager.Modelo;

public class Password {

    private String id;
    private String titulo;
    private String cuenta;
    private String nombre_usuario;
    private String password;
    private String sitio_Web;
    private String nota;
    private String t_registro;
    private String t_actualizacion;

    public Password(String id, String titulo, String cuenta, String nombre_usuario, String password, String sitio_Web, String nota, String t_registro, String t_actualizacion) {
        this.id = id;
        this.titulo = titulo;
        this.cuenta = cuenta;
        this.nombre_usuario = nombre_usuario;
        this.password = password;
        this.sitio_Web = sitio_Web;
        this.nota = nota;
        this.t_registro = t_registro;
        this.t_actualizacion = t_actualizacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSitio_Web() {
        return sitio_Web;
    }

    public void setSitio_Web(String sitio_Web) {
        this.sitio_Web = sitio_Web;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getT_registro() {
        return t_registro;
    }

    public void setT_registro(String t_registro) {
        this.t_registro = t_registro;
    }

    public String getT_actualizacion() {
        return t_actualizacion;
    }

    public void setT_actualizacion(String t_actualizacion) {
        this.t_actualizacion = t_actualizacion;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id='" + id + '\'' +
                ", cuenta='" + cuenta + '\'' +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", password='" + password + '\'' +
                ", sitio_Web='" + sitio_Web + '\'' +
                ", nota='" + nota + '\'' +
                ", t_registro='" + t_registro + '\'' +
                ", t_actualizacion='" + t_actualizacion + '\'' +
                '}';
    }
}
