package com.adinhop.animeprocessador.controllers;

import com.adinhop.animeprocessador.Calculos;
import com.adinhop.animeprocessador.ExtracaoCalulos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@RestController
@RequestMapping("")
public class ExecutorController {

    @PostMapping("/executar")
    public ResponseEntity<ExtracaoCalulos> executar() {
        Path path = Path.of("teste-prod.docx");
        Calculos calculos = new Calculos(path);
        ExtracaoCalulos extracaoCalulos = calculos.extrair();
        System.out.println(extracaoCalulos);
        return ResponseEntity.ok(extracaoCalulos);
    }
}
