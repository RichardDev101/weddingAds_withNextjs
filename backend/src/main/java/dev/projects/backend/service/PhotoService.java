package dev.projects.backend.service;

import dev.projects.backend.collection.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    String addPhoto(String originalFilename, MultipartFile image) throws IOException;
    Photo getPhoto(String id);
    void deletePhoto(String id);
}
