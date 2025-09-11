package com.adinho.pdf_reader.pdf_reader;

import jakarta.xml.bind.JAXBElement;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Calculos {
    private static final List<String> keys = List.of("Selic", "Juros");
    private static final Pattern pattern = Pattern.compile("(%s)".formatted(String.join("|", keys)));

    public Calculos() {

    }


    public static ExtracaoCalulos procurar(WordprocessingMLPackage wordMLPackage) {
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        List<JAXBElement> content = documentPart.getContent()
                .stream()
                .filter(JAXBElement.class::isInstance)
                .map(col -> (JAXBElement) col)
                .toList();

//        linhas
        /*3 - 4 - 8 - 9 - 10 - 11
         *2 - 3 - 7 - 8 - 9 - 10*/
        Tbl tabela = (Tbl) content.get(0).getValue();
        Tr linhaNome = (Tr) tabela.getContent().get(2);
        Tr linhaCpf = (Tr) tabela.getContent().get(3);
        Tr linhaPrincipal = (Tr) tabela.getContent().get(7);
        Tr linhaJuros = (Tr) tabela.getContent().get(8);
        Tr linhaSelic = (Tr) tabela.getContent().get(9);
        Tr linhaTotal = (Tr) tabela.getContent().get(10);
        List<Tr> colunasComAsInfoQueQuero = List.of(linhaNome, linhaCpf, linhaPrincipal, linhaJuros, linhaSelic, linhaTotal);
        List<String> values = colunasComAsInfoQueQuero.stream().map(el -> {
                    JAXBElement cordenada;
                    if (el == linhaNome || el == linhaCpf) {
                        cordenada = (JAXBElement) el.getContent().get(1);
                        return extrairValorDaCordenadaString(cordenada);
                    } else {
                        cordenada = (JAXBElement) el.getContent().get(7);
                        return extrairValorDaCordenadaNumero(cordenada);
                    }

                })
                .toList();


        if (values == null || values.size() < keys.size()) {
            throw new RuntimeException("Nao foi possivel encontrar os valores no documento.");
        }

        String nome = values.get(0).trim();
        String cpf = values.get(1).trim();
        BigDecimal principal = convertTOBigDecimal(values.get(2).trim());
        BigDecimal juros = convertTOBigDecimal(values.get(3).trim());
        BigDecimal selic = convertTOBigDecimal(values.get(4).trim());
        BigDecimal total = convertTOBigDecimal(values.get(5).trim());

        return new ExtracaoCalulos(nome, cpf, principal, juros, selic, total);
    }

    private static String extrairValorDaCordenadaNumero(JAXBElement cordenada){
        Tc colContent = (Tc) cordenada.getValue();
        if (!colContent.getContent().isEmpty()) {
            P paragrafo = (P) colContent.getContent().get(0);
            if (!paragrafo.getContent().isEmpty()) {
                R r = (R) paragrafo.getContent().get(0);
                if (!r.getContent().isEmpty()) {
                    JAXBElement elDentroLinha = (JAXBElement) r.getContent().get(0);
                    if (Text.class.isAssignableFrom(elDentroLinha.getValue().getClass())) {
                        Text t = (Text) elDentroLinha.getValue();
                        return t.getValue();
                    }
                    return null;
                }
                return null;
            }
            return null;
        }
        return null;
    }
    private static String extrairValorDaCordenadaString(JAXBElement cordenada){
        Tc colContent = (Tc) cordenada.getValue();
        if (!colContent.getContent().isEmpty()) {
            P paragrafo = (P) colContent.getContent().get(0);
            String rsidRPr = paragrafo.toString();
            return rsidRPr;
        }
        return null;
    }
    public static ExtracaoCalulos extrair(Path arquivo) {
        try {
            File docxFile = arquivo.toFile();
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxFile);
            return procurar(wordMLPackage);

        } catch (Exception e) {
            throw new RuntimeException("Deu merda na extração dos dados", e);
        }
    }

    public static ExtracaoCalulos extrair(MultipartFile file) {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(file.getInputStream());
            return procurar(wordMLPackage);

        } catch (Exception e) {
            throw new RuntimeException("Deu merda na extração dos dados", e);
        }
    }

    private static List<Text> findInContent(List<Object> content, List<String> keys) {
        List<Text> texts = content.stream()
                .filter(R.class::isInstance)
                .map(el -> (R) el)
                .flatMap(row -> row.getContent().stream())
                .filter(JAXBElement.class::isInstance)
                .map(col -> (JAXBElement) col)
                .map(jaxbElement -> jaxbElement.getValue())
                .filter(Text.class::isInstance)
                .map(value -> (Text) value)
                .filter(text -> pattern.matcher(text.getValue()).find())
                .toList();
        return texts;
    }

    private static BigDecimal convertTOBigDecimal(String valor) {
        try {
            // Usar Locale brasileiro para interpretar a vírgula como decimal
            NumberFormat format = java.text.NumberFormat.getNumberInstance(new Locale("pt", "BR"));
            Number number = ((java.text.NumberFormat) format).parse(valor);
            return new BigDecimal(number.toString());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Valor inválido: " + valor, e);
        }

    }

}
