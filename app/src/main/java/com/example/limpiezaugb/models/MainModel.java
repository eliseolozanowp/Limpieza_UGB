package com.example.limpiezaugb.models;

public class MainModel {
    String nombre, zona, hora;

    MainModel()
    {

    }
    public MainModel(String nombre, String zona, String hora) {
        this.nombre = nombre;
        this.zona = zona;
        this.hora = hora;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getZona() { return zona; }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}