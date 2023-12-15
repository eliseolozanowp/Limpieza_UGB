package com.example.limpiezaugb.models;

public class Model2 {
    String zona, hora;

    Model2()
    {

    }
    public Model2(String zona, String hora) {
        this.zona = zona;
        this.hora = hora;
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
}
