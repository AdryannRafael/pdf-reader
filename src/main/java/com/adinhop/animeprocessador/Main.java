package com.adinhop.animeprocessador;


import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("teste-prod.docx");
        Calculos calculos = new Calculos(path);
        ExtracaoCalulos extracaoCalulos = calculos.extrair();
        System.out.println(extracaoCalulos);

    }


}