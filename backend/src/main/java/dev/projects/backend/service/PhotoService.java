package dev.projects.backend.service;

import dev.projects.backend.collection.Photo;
import dev.projects.backend.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PhotoService{

    private final PhotoRepository photoRepository;
    private final GenerateUUIDService uuid;

    public String addPhoto(String originalFilename, MultipartFile image) throws IOException {
        Photo photo = new Photo();
        photo.setId(uuid.getUUID());
        photo.setTitle(originalFilename);
        photo.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        return photoRepository.save(photo).getId();
    }

    public Photo getPhoto(String id) {
        Optional<Photo> optionalPhoto = photoRepository.findById(id);
        if (optionalPhoto.isPresent()) {
            return optionalPhoto.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    public void deletePhoto(String id) {
        photoRepository.deleteById(id);
    }
}
