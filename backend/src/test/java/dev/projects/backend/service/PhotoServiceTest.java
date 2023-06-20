package dev.projects.backend.service;

import dev.projects.backend.collection.Photo;
import dev.projects.backend.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PhotoServiceTest {

    private final PhotoRepository photoRepository = mock(PhotoRepository.class);
    private final GenerateUUIDService generateUUIDService = mock(GenerateUUIDService.class);
    private final PhotoService photoServiceImpl = new PhotoService(photoRepository, generateUUIDService);

    @Test
    void testAddPhoto_ShouldSavePhotoAndReturnPhotoId() throws IOException {
        // ARRANGE
        String originalFilename = "photo.jpg";
        String photoId = "123";
        MultipartFile image = new MockMultipartFile("image", new byte[]{});

        Photo photo = new Photo();
        photo.setId(photoId);
        photo.setTitle(originalFilename);

        when(generateUUIDService.getUUID()).thenReturn(photoId);
        when(photoRepository.save(any(Photo.class))).thenReturn(photo);

        // ACT
        String result = photoServiceImpl.addPhoto(originalFilename, image);

        // ASSERT
        verify(generateUUIDService).getUUID();
        verify(photoRepository).save(any(Photo.class));
        assertEquals(photoId, result);
    }

    @Test
    void testGetPhoto_ShouldReturnPhotoById() {
        // ARRANGE
        String photoId = "123";
        Photo photo = new Photo();
        photo.setId(photoId);

        when(photoRepository.findById(photoId)).thenReturn(Optional.of(photo));

        // ACT
        Photo result = photoServiceImpl.getPhoto(photoId);

        // ASSERT
        verify(photoRepository).findById(photoId);
        assertEquals(photo, result);
    }

    @Test
    void testGetPhoto_ShouldThrowNoSuchElementException() {
        // ARRANGE
        String photoId = "123";

        when(photoRepository.findById(photoId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThrows(NoSuchElementException.class, () -> photoServiceImpl.getPhoto(photoId));
        verify(photoRepository).findById(photoId);
    }

    @Test
    void testDeletePhoto_ShouldDeletePhotoById() {
        // ARRANGE
        String photoId = "123";

        // ACT
        photoServiceImpl.deletePhoto(photoId);

        // ASSERT
        verify(photoRepository).deleteById(photoId);
    }
}