package com.example.limpiezaugb.models;

public class MainModel {
    String nombre, zona, estado;
    long fechaAsignacionMillis;

    MainModel()
    {

    }
    public MainModel(String nombre, String zona, String estado) {
        this.nombre = nombre;
        this.zona = zona;
        this.estado = estado;
        this.fechaAsignacionMillis = System.currentTimeMillis();
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getZona() { return zona; }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getFechaAsignacionMillis() {
        return fechaAsignacionMillis;
    }

    public void setFechaAsignacionMillis(long fechaAsignacionMillis) {
        this.fechaAsignacionMillis = fechaAsignacionMillis;
    }
}