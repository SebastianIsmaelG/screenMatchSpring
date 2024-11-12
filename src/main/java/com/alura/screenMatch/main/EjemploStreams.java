package com.alura.screenMatch.main;

import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Brenda","Luis","Maria","Fernanda","Eric","Genesis");
        nombres.stream().sorted().limit(4).forEach(System.out::println);
    }
}
