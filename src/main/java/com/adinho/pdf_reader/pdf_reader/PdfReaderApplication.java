package com.adinho.pdf_reader.pdf_reader;

import com.adinho.pdf_reader.pdf_reader.core.gerador_documentos.GenerateDocumentProcess;
import com.adinho.pdf_reader.pdf_reader.core.processo.ProcessGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PdfReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfReaderApplication.class, args);
    }


//    @Bean
//    public ApplicationRunner run() {
//        return args -> {
//            try{
//
//            System.out.println("Aplicação iniciada com sucesso!");
//            MinioClient client = MinioClient.builder().endpoint("https://s3.adryannrafael.com.br")
//                    .credentials("adryann", "AdryannR@f@2024")
//                    .build();
//
//            File file = new File("C:\\Users\\d2ti\\Downloads\\pdf-reader\\src\\main\\resources\\ANEXO-TESTE.pdf");
//            FileInputStream stream = new FileInputStream(file);
//            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//                    .bucket("teste")
//                    .object(UUID.randomUUID().toString()+".pdf")
//                    .stream(stream, file.length(), -1)
//                    .contentType("application/pdf")
//                    .build();
//            client.putObject(putObjectArgs);
//
//            stream.close();
//
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//                throw e;
//            }
//
//        };
//    }


    @Bean
    public ProcessGateway processGateway() {

        return (GenerateDocumentProcess process) -> {
            return process;
        };
    }

}
