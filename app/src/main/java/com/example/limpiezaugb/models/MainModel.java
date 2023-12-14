package com.example.limpiezaugb.models;

public class MainModel {
    String nombre, zona, estado;

    MainModel()
    {

    }
    public MainModel(String nombre, String zona, String estado) {
        this.nombre = nombre;
        this.zona = zona;
        this.estado = estado;
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
}