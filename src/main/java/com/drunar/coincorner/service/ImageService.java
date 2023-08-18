package com.drunar.coincorner.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket}")
    private final String bucket;

    @SneakyThrows
    public String upload(String originalFileName, InputStream content) {
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = generateUniqueFileName(fileExtension);

        Path fullFilePath = Path.of(bucket, uniqueFileName);

        try (OutputStream outputStream = Files.newOutputStream(fullFilePath, CREATE, TRUNCATE_EXISTING)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = content.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return uniqueFileName;
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

    @SneakyThrows
    public boolean delete(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            Path fullImagePath = Path.of(bucket, imagePath);

            if (Files.exists(fullImagePath)) {
                try {
                    Files.delete(fullImagePath);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    private String generateUniqueFileName(String fileExtension) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + "." + fileExtension;
    }


}
