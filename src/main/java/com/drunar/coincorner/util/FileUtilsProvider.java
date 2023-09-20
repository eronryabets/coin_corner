package com.drunar.coincorner.util;

public interface FileUtilsProvider {
    String getFileExtension(String fileName);
    String generateUniqueFileName(String fileExtension);
}
