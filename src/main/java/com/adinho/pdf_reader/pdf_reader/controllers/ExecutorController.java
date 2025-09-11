package com.adinho.pdf_reader.pdf_reader.controllers;

import com.adinho.pdf_reader.pdf_reader.ExtracaoCalulos;
import com.adinho.pdf_reader.pdf_reader.services.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
public class ExecutorController {

    private final DocumentService documentService;

    public ExecutorController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/executar")
    public ResponseEntity<ExtracaoCalulos> executar(
            @RequestParam("numeroProcesso") String numeroProcesso,
            @RequestParam("file") MultipartFile[] files) {
        return ResponseEntity.ok(documentService.calcular(numeroProcesso, files));
    }
}
