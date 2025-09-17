package com.example.lab2.Model;

public class AccessPoint {
    public String id;
    public String marca;
    public String modelo;
    public String velocidad; // ej: "1200 Mbps"
    public Estado estado;    // OPERATIVO / EN_REPARACION / DADO_DE_BAJA
    public String banda;     // "2.4 GHz" / "5 GHz" / "6 GHz"

    public AccessPoint() {}

    public AccessPoint(String id, String marca, String modelo, String velocidad, Estado estado, String banda) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.velocidad = velocidad;
        this.estado = estado;
        this.banda = banda;
    }
}