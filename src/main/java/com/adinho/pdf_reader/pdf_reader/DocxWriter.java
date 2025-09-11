package com.adinho.pdf_reader.pdf_reader;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Text;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DocxWriter {

    private WordprocessingMLPackage docx;
    List<Object> nodes;
    private Path outputPath;

    public DocxWriter(MultipartFile file, Path outputPath) throws IOException, Docx4JException, JAXBException {
        this.docx = WordprocessingMLPackage.load(file.getInputStream());
        this.nodes = pegarNodes("//w:t");
        this.outputPath = outputPath;
    }

    public DocxWriter(Path path, Path outputPath) throws Docx4JException, JAXBException {
        this.docx = WordprocessingMLPackage.load(path.toFile());
        this.nodes = pegarNodes("//w:t");
        this.outputPath = outputPath;
    }

    private List<Object> pegarNodes(String tipo) throws JAXBException, XPathBinderAssociationIsPartialException {
        MainDocumentPart main = this.docx.getMainDocumentPart();
        return main.getJAXBNodesViaXPath(tipo, true);
    }


    public void write(String replaced, String conteudo) {
        System.out.println(this.nodes);
        for (Object node : this.nodes) {
            if (JAXBElement.class.isAssignableFrom(node.getClass())) {
                JAXBElement element = (JAXBElement) node;
                Text text = (Text) element.getValue();
                if (text.getValue().equals(replaced)) {
                    text.setValue(conteudo);
                    break;
                }
            }
        }
    }


    public void save() throws IOException, Docx4JException {
        if (Files.notExists(this.outputPath)) {
            Files.createFile(this.outputPath);
        }
        try (OutputStream out = Files.newOutputStream(this.outputPath)) {
            docx.save(out);
            out.flush();
        } catch (Exception e) {
            throw e;
        }

    }

}
