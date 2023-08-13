package com.drunar.coincorner.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket}")
    private final String bucket;

    @SneakyThrows
    public void upload(String filePath, InputStream content) {
        Path fullFilePath = Path.of(bucket, filePath);

        try (OutputStream outputStream = Files.newOutputStream(fullFilePath, CREATE, TRUNCATE_EXISTING)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = content.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);

        if (Files.exists(fullImagePath)) {
            try (InputStream inputStream = Files.newInputStream(fullImagePath)) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return Optional.of(outputStream.toByteArray());
            }
        } else {
            return Optional.empty();
        }
    }


}
