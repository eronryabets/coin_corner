package com.drunar.coincorner.service;

import java.io.InputStream;
import java.util.Optional;

public interface ImageService {

    String upload(String originalFileName, InputStream content);
    Optional<byte[]> get(String imagePath);
    boolean delete(String imagePath);

}
