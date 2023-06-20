package dev.projects.backend.service;

import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.repository.AdRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AdServiceTest {

    AdRepository adRepository = mock(AdRepository.class);
    GenerateUUIDService generateUUIDService = mock(GenerateUUIDService.class);
    AdService adServiceImpl = new AdService(adRepository,generateUUIDService);

    @Test
    void testSaveAdvertisement_ShouldSaveAdvertisementAndReturnExpected() {
        // ARRANGE
        Advertisement ad = new Advertisement();
        Advertisement expected = new Advertisement();
        // ACT
        when(adRepository.save(ad)).thenReturn(expected);
        Advertisement actual = adServiceImpl.save(ad);
        // ASSERT
        verify(adRepository).save(expected);
        verify(generateUUIDService).getUUID();
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllAds_ShouldReturnListOfAdvertisements() {
        // ARRANGE
        List<Advertisement> expected = List.of(new Advertisement(), new Advertisement());
        // ACT
        when(adRepository.findAll()).thenReturn(expected);
        List<Advertisement> actual =adServiceImpl.getAllAds();
        // ASSERT
        verify(adRepository).findAll();
        assertEquals(actual, expected);
    }

    @Test
    void testGetAdWithId_ExistingId_ReturnsAdvertisement() {
        // ARRANGE
        String id = "4645";
        Advertisement expectedAd = new Advertisement();
        expectedAd.setId(id);
        when(adRepository.findById(id)).thenReturn(Optional.of(expectedAd));
        // ACT
        Advertisement actualAd = adServiceImpl.getAdWithId(id);
        // ASSERT
        assertEquals(expectedAd, actualAd);
        verify(adRepository).findById(id);
    }
    @Test
    void testGetAdWithId_NonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        String id = "65487";
        when(adRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> adServiceImpl.getAdWithId(id));
        verify(adRepository).findById(id);
    }

}