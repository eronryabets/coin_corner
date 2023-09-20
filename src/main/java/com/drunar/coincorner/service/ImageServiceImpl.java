package com.drunar.coincorner.service;

import com.drunar.coincorner.util.FileUtilsProvider;
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

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    @Value("${app.image.bucket}")
    private final String bucket;
    private final FileUtilsProvider fileUtils;

    @Override
    @SneakyThrows
    public String upload(String originalFileName, InputStream content) {
        String fileExtension = fileUtils.getFileExtension(originalFileName);
        String uniqueFileName = fileUtils.generateUniqueFileName(fileExtension);

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

    @Override
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

    @Override
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

}
