package dev.projects.backend.controller;

import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.dto.AdvertisementDTO;
import dev.projects.backend.enums.BusinessCategory;
import dev.projects.backend.enums.PaymentCategory;
import dev.projects.backend.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ad")
public class AdController {

    @Autowired
    private AdService adService;

    //CREATE
    @PostMapping()
    //@PreAuthorize("hasAuthority('LoginRole=ADMIN')")
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

    //READ
    @GetMapping()
    public List<Advertisement> getAllAds(){
        return adService.getAllAds();
    }
    @GetMapping("{id}")
    public Advertisement getAdWithId(@PathVariable String id){
        return adService.getAdWithId(id);
    }

}
