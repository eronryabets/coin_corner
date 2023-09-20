package com.drunar.coincorner.util;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileUtils implements FileUtilsProvider{

    @Override
    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    @Override
    public String generateUniqueFileName(String fileExtension) {
        UUID uuid = UUID.randomUUID();
        return uuid + "." + fileExtension;
    }
}
