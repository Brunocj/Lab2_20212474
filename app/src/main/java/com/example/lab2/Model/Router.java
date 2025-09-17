package com.example.lab2.Model;

public class Router {

    public String id;
    public String marca;
    public String modelo;
    public Estado estado;
    public String velocidad;

    public Router(String id, String marca, String modelo, Estado estado, String velocidad) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
        this.velocidad = velocidad;
    }
}
