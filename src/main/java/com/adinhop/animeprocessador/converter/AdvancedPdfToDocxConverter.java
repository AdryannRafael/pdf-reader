package com.adinhop.animeprocessador.converter;

import org.apache.poi.xwpf.usermodel.*;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Path;

public class AdvancedPdfToDocxConverter {

    private boolean preservePageBreaks = true;
    private boolean addPageNumbers = false;

    public void setPreservePageBreaks(boolean preservePageBreaks) {
        this.preservePageBreaks = preservePageBreaks;
    }

    public void setAddPageNumbers(boolean addPageNumbers) {
        this.addPageNumbers = addPageNumbers;
    }

    public void convert(String pdfPath, String docxPath) throws IOException {
        convert(new File(pdfPath), new File(docxPath));
    }

    public void convert(File pdfFile, File docxFile) throws IOException {
        try {

            PdfReader reader = new PdfReader(pdfFile.getAbsolutePath());
            XWPFDocument doc = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(docxFile);

                PdfReaderContentParser parser = new PdfReaderContentParser(reader);
                int totalPages = reader.getNumberOfPages();

                for (int i = 1; i <= totalPages; i++) {
                    processPage(parser, doc, i, totalPages);
                }

                doc.write(out);

        }catch (Exception e){
            throw new IOException("Erro ao converter PDF para DOCX: " + e.getMessage(), e);
        }
    }

    public void convert(byte[] pdfBytes, String docxPath) throws IOException {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("pdf_convert", ".pdf");
            Files.write(tempFile, pdfBytes);
            convert(tempFile.toString(), docxPath);
        } finally {
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }
    }

    private void processPage(PdfReaderContentParser parser, XWPFDocument doc,
                             int pageNumber, int totalPages) throws IOException {

        TextExtractionStrategy strategy =
                parser.processContent(pageNumber, new SimpleTextExtractionStrategy());
        String pageText = strategy.getResultantText();

        if (pageText != null && !pageText.trim().isEmpty()) {
            // Adicionar número da página (opcional)
            if (addPageNumbers) {
                addPageHeader(doc, pageNumber, totalPages);
            }

            // Adicionar conteúdo da página
            XWPFParagraph paragraph = doc.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(pageText);

            // Adicionar quebra de página
            if (preservePageBreaks && pageNumber < totalPages) {
                run.addBreak(BreakType.PAGE);
            }
        }
    }

    private void addPageHeader(XWPFDocument doc, int currentPage, int totalPages) {
        XWPFParagraph header = doc.createParagraph();
        XWPFRun run = header.createRun();
        run.setText("Página " + currentPage + " de " + totalPages);
        run.setBold(true);
        run.setFontSize(10);
        run.addBreak();
    }
}