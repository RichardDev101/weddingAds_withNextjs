package dev.projects.backend.service;

import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.enums.BusinessCategory;
import dev.projects.backend.enums.PaymentCategory;
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
    AdService adService = new AdService(adRepository,generateUUIDService);

    @Test
    void testSaveAdvertisement_ShouldSaveAdvertisementAndReturnExpected() {
        // ARRANGE
        Advertisement ad = new Advertisement();
        Advertisement expected = new Advertisement();
        // ACT
        when(adRepository.save(ad)).thenReturn(expected);
        Advertisement actual = adService.save(ad);
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
        List<Advertisement> actual = adService.getAllAds();
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
        Advertisement actualAd = adService.getAdWithId(id);
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
        assertThrows(NoSuchElementException.class, () -> adService.getAdWithId(id));
        verify(adRepository).findById(id);
    }

    @Test
    void testGetAdByBusiness_ShouldReturnListOfAdvertisementsForGivenBusinessCategory() {
        // ARRANGE
        BusinessCategory businessCategory = BusinessCategory.BAKERY;
        List<Advertisement> expected = List.of(new Advertisement(), new Advertisement());

        // ACT
        when(adRepository.findAdvertisementsByBusinessCategories(businessCategory)).thenReturn(expected);
        List<Advertisement> actual = adService.getAdByBusiness(businessCategory);

        // ASSERT
        verify(adRepository).findAdvertisementsByBusinessCategories(businessCategory);
        assertEquals(expected, actual);
    }

    @Test
    void testGetAdsByPaymentCategory_ShouldReturnListOfAdvertisementsForGivenPaymentCategory() {
        // ARRANGE
        PaymentCategory paymentCategory = PaymentCategory.PREMIUM;
        List<Advertisement> expected = List.of(new Advertisement(), new Advertisement());

        // ACT
        when(adRepository.findAdvertisementsByPaymentCategory(paymentCategory)).thenReturn(expected);
        List<Advertisement> actual = adService.getAdsByPaymentCategory(paymentCategory);

        // ASSERT
        verify(adRepository).findAdvertisementsByPaymentCategory(paymentCategory);
        assertEquals(expected, actual);
    }

    @Test
    void testGetAdsByAveragePriceIsLessOrEqual_ShouldReturnListOfAdvertisementsWithAveragePriceLessOrEqual() {
        // ARRANGE
        float averagePrice = 100.00f;
        List<Advertisement> expected = List.of(new Advertisement(), new Advertisement());

        // ACT
        when(adRepository.findAdvertisementsByAveragePriceIsLessThanEqual(averagePrice)).thenReturn(expected);
        List<Advertisement> actual = adService.getAdsByAveragePriceIsLessOrEqual(averagePrice);

        // ASSERT
        verify(adRepository).findAdvertisementsByAveragePriceIsLessThanEqual(averagePrice);
        assertEquals(expected, actual);
    }

    @Test
    void testUpdateAd_ShouldUpdateAdvertisementAndReturnId() {
        // ARRANGE
        String id = "3423352";
        Advertisement originalAdvertisement = new Advertisement();
        originalAdvertisement.setId(id);

        String updatedId= "updatedId3423352";
        Advertisement updatedAdvertisement = new Advertisement();
        updatedAdvertisement.setId(updatedId);

        when(adRepository.findById(id)).thenReturn(Optional.of(originalAdvertisement));
        when(adRepository.save(updatedAdvertisement)).thenReturn(updatedAdvertisement);
        // ACT
        String result = adService.updateAd(updatedAdvertisement, id);

        // ASSERT
        verify(adRepository).findById(id);
        verify(adRepository).save(updatedAdvertisement);
        assertEquals(updatedId, result);
    }

    @Test
    void testDelete_ShouldDeleteAdvertisement() {
        // ARRANGE
        String id = "946546";
        when(adRepository.findById(id)).thenReturn(Optional.of(new Advertisement()));

        // ACT
        adService.delete(id);

        // ASSERT
        verify(adRepository).findById(id);
        verify(adRepository).deleteById(id);
    }

}