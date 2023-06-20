package dev.projects.backend.controller;

import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.dto.AdvertisementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ad")
public class AdController {

    private final AdService adService;

    @PostMapping()
    public Advertisement save(@RequestBody AdvertisementDTO ad){
        Advertisement newAd =Advertisement.builder()
                .advertisementStatus(ad.getAdvertisementStatus())
                .paymentCategory(ad.getPaymentCategory())
                .company(ad.getCompany())
                .businessCategories(ad.getBusinessCategories())
                .photosID(ad.getPhotosID())
                .title(ad.getTitle())
                .aboutYourself(ad.getAboutYourself())
                .detailInformationForService(ad.getDetailInformationForService())
                .averagePrice(ad.getAveragePrice())
                .priceCategories(ad.getPriceCategories())
                .contacts(ad.getContacts())
                .locations(ad.getLocations())
                .personsID(ad.getPersonsID())
                .build();
        return adService.save(newAd);
    }

    @GetMapping()
    public List<Advertisement> getAllAds(){
        return adService.getAllAds();
    }
    @GetMapping("{id}")
    public Advertisement getAdWithId(@PathVariable String id){
        return adService.getAdWithId(id);
    }

}
