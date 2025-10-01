package com.adinho.pdf_reader.pdf_reader.services;

import com.adinho.pdf_reader.pdf_reader.Calculos;
import com.adinho.pdf_reader.pdf_reader.DocxWriter;
import com.adinho.pdf_reader.pdf_reader.ExtracaoCalulos;
import com.adinho.pdf_reader.pdf_reader.MoneyFormatter;
import jakarta.xml.bind.JAXBException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@Service
public class DocumentService {

    private final BigDecimal honorariosSucumbencial = BigDecimal.valueOf(10).divide(BigDecimal.valueOf(100));
    private final BigDecimal honorariosContratual = BigDecimal.valueOf(20).divide(BigDecimal.valueOf(100));

    public DocumentService() {
    }

    public ByteArrayOutputStream calcular(MultipartFile modelo, String numeroProcesso, List<MultipartFile> files) {

        /*  Tudo que deve ser passado o replace
         *   #numeroProcesso
         *   #nome(index)
         *   #cpf(index)
         *   #restituido(index)
         *   #juros(index)
         *   #total(index)
         *   #honorarios(index)
         *   #valorTotal
         *   #honorariosTotal
         * */
        try {

            DocxWriter docxWriter = new DocxWriter(modelo, null);
            ExtracaoCalulos extracaoCalulos = this.extrairValoresTotais(files, docxWriter);


            BigDecimal honorariosTotal = extracaoCalulos.getTotal().multiply(honorariosSucumbencial);
            /**/
            docxWriter.write("#numeroProcesso", numeroProcesso);
            docxWriter.write("#valorTotal", MoneyFormatter.formatSemSimbolo(extracaoCalulos.getTotal()));
            docxWriter.write("#honorariosTotal", MoneyFormatter.formatSemSimbolo(honorariosTotal));

            return docxWriter.getOutputSteam();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ExtracaoCalulos extrairValoresTotais(List<MultipartFile> files, DocxWriter docxWriter) throws JAXBException, IOException, Docx4JException, ParseException {
        ExtracaoCalulos ex = new ExtracaoCalulos();
        /*  Tudo que deve ser passado o replace aqui enquanto percorre
         *   #nome(index)
         *   #cpf(index)
         *   #restituido(index)
         *   #juros(index)
         *   #total(index)
         *   #honorarios(index)
         * */
        List<ExtracaoCalulos> calculosEncontrados = files.stream().map(Calculos::extrair).toList();
        for (int i = 1; i <= calculosEncontrados.size(); i++) {
            ExtracaoCalulos calculo = calculosEncontrados.get(i - 1);
            /*Calculando valores*/
            BigDecimal juros = calculo.getJuros().add(calculo.getSelic());
            BigDecimal honorarios = calculo.getTotal().multiply(honorariosContratual);

            /*Formatando valores*/
            String restituicaoFormatodo = MoneyFormatter.formatSemSimbolo(calculo.getPrincipal());
            String jurosFormatado = MoneyFormatter.formatSemSimbolo(juros);
            String totalFormatodo = MoneyFormatter.formatSemSimbolo(calculo.getTotal());
            String honorariosFormatodo = MoneyFormatter.formatSemSimbolo(honorarios);

            /*Escrevendo no docx*/
            docxWriter.write("#nome" + i, calculo.getNome());
            String cpf = formatCPF(calculo.getCpf());

            docxWriter.write("#cpf" + i, cpf);
            docxWriter.write("#restituido" + i, restituicaoFormatodo);
            docxWriter.write("#juros" + i, jurosFormatado);
            docxWriter.write("#total" + i, totalFormatodo);
            docxWriter.write("#honorarios" + i, honorariosFormatodo);

            System.out.println(calculo);
            /*Somando valores para o total geral*/
            ex.setPrincipal(ex.getPrincipal().add(calculo.getPrincipal()));
            ex.setJuros(ex.getJuros().add(calculo.getJuros()));
            ex.setSelic(ex.getSelic().add(calculo.getSelic()));
            ex.setTotal(ex.getTotal().add(calculo.getTotal()));
        }
        return ex;
    }

    static String formatCPF(String cpf) {
        if (cpf == null) return "";
        // remove tudo que não for número
        cpf = cpf.replaceAll("[^0-9]", "");

        // garante que tenha 11 dígitos
        if (cpf.length() != 11) return cpf;

        return cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})",
                "$1.$2.$3-$4");
    }
}
