package com.example.limpiezaugb;

public class AreaAsignada {
    private String nombre;
    private String zona;
    private String hora;
    private long timestamp;

    // Constructor vacío requerido por Firebase Realtime Database
    public AreaAsignada() {
    }

    public AreaAsignada(String nombre, String zona, String hora, long timestamp) {
        this.nombre = nombre;
        this.zona = zona;
        this.hora = hora;
        this.timestamp = timestamp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

