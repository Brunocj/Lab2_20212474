package com.example.lab2_20212474.Controller;

import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

@Data
public class Bingo {
    private String tamano;
    private String numTarjetas;
    private Integer tarjetaBingo;
    public ArrayList<Integer> listaNumeros;
    private String numero;
    private Integer randInt(){
        Random random = new Random();
        return random.nextInt(99) + 1;
    }
    public Integer[][] inicializarTarjeta(int tamano){
        Integer[][] tarjeta = new Integer[tamano][tamano];
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                boolean cont = true;
                while(cont){
                    boolean rellenar = true;
                    Integer randNum = randInt();
                    for (int i_p = 0; i_p < tamano; i_p++) {
                        for (int j_p = 0; j_p < tamano; j_p++) {
                            if(tarjeta[i_p][j_p] == randNum){
                                rellenar = false;
                                break;
                            }
                        }
                    }
                    if(rellenar){
                        tarjeta[i][j] = randNum;
                        cont = false;
                    }
                }
            }
        }
        return tarjeta;
    }

}
