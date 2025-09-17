package com.example.lab2.Model;

public class Switch {
    public String id;
    public String marca;
    public String modelo;
    public String velocidad;
    public Estado estado;
    public TipoSwitch tipo;

    public Switch() {}

    public Switch(String id, String marca, String modelo, String velocidad, Estado estado, TipoSwitch tipo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.velocidad = velocidad;
        this.estado = estado;
        this.tipo = tipo;
    }
}
