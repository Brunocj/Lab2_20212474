package com.example.lab2_20212474.Controller;

import org.springframework.aot.generate.InMemoryGeneratedFiles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class GameController {
    @GetMapping(value = "/config")
    public String config(Model model){
        Bingo bingo = new Bingo();
        model.addAttribute("bingo", bingo);
        return "config";
    }
    @PostMapping(value = "/bingo")
    public String bingo(Model model, Bingo bingo){
        try{
            Integer.parseInt(bingo.getTamano());
            Integer.parseInt(bingo.getNumTarjetas());
            ArrayList<Integer[][]> listaTarjetas = new ArrayList<>();
            for(int i = 0; i<Integer.parseInt(bingo.getNumTarjetas()); i++){
                listaTarjetas.add(bingo.inicializarTarjeta(Integer.parseInt(bingo.getTamano())));
            }
            model.addAttribute("listaTarjetas", listaTarjetas);

            model.addAttribute("bingo", bingo);
            return "bingo";
        }catch (NumberFormatException e){
            String msg = "Ingrese correctamente los datos solicitados";
            model.addAttribute("msg", msg);
            return "config";
        }


    }
    @GetMapping(value = "/bingoInit")
    public String BingoIniciado(Bingo bingo, Model model){
        /*bingo.getListaNumeros().add(Integer.parseInt(bingo.getNumero()));
        System.out.println(bingo.getNumero());*/
        model.addAttribute("bingo", bingo);
        return "bingo";
    }

}
