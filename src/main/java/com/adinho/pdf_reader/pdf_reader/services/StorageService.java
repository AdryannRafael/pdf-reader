package com.adinho.pdf_reader.pdf_reader.services;

import com.adinho.pdf_reader.pdf_reader.core.kernel.Produtos;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class StorageService {
    private final MinioClient client;

    @Value("${storage.s3.bucket}")
    private String bucket;

    public StorageService(
            @Value("${storage.s3.url}") String url,
            @Value("${storage.s3.user}") String user,
            @Value("${storage.s3.password}") String password
    ) {
        this.client = MinioClient.builder()
                .endpoint(url)
                .credentials(user, password)
                .build();
    }

    public String sendDocx(InputStream file, int fileSize, String nameFile, Produtos produto) {
        try{
            String path = produto.getPath() + "/6d360977-748a-4e39-8eb8-971762248a09/" + nameFile + ".docx";
            PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucket)
                .contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                .stream(file, fileSize, -1)
                .object(path)
                .build();
        client.putObject(args);
        file.close();
        return path;
    } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        }
    }
}
