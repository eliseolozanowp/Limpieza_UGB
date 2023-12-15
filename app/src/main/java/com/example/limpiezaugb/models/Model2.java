package com.example.limpiezaugb.models;

public class Model2 {
    String zona, hora, estado;

    Model2()
    {

    }
    public Model2(String zona, String hora, String estado) {
        this.zona = zona;
        this.hora = hora;
        this.estado = estado;
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
