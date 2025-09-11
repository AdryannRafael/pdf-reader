package com.adinho.pdf_reader.pdf_reader.controllers;

import com.adinho.pdf_reader.pdf_reader.ExtracaoCalulos;
import com.adinho.pdf_reader.pdf_reader.services.DocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("")
public class ExecutorController {

    private final DocumentService documentService;

    public ExecutorController(DocumentService documentService) {
        this.documentService = documentService;
    }
    @CrossOrigin(origins = "*") // Também pode ser aplicado a métodos específicos
    @PostMapping("/executar")
    public ResponseEntity<byte[]> executar(
            @RequestParam("numeroProcesso") String numeroProcesso,
            @RequestParam("modelo") MultipartFile modelo,
            @RequestParam("file") List<MultipartFile> file
    ) {
        ByteArrayOutputStream stream = documentService.calcular(modelo, numeroProcesso, file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=%s.docx".formatted(numeroProcesso))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(stream.toByteArray());
    }
}
