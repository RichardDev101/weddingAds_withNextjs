package dev.projects.backend.controller;

import de.neuefische.backend.collection.Advertisement;
import de.neuefische.backend.dto.AdvertisementDTO;
import de.neuefische.backend.enums.BusinessCategory;
import de.neuefische.backend.enums.PaymentCategory;
import de.neuefische.backend.service.AdService;
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
    @GetMapping("/business")
    public List<Advertisement> getAdsByBusiness(@RequestParam("businessCategory")BusinessCategory businessCategory){
        return adService.getAdByBusiness(businessCategory);
    }
    @GetMapping("/payment")
    public List<Advertisement> getAdsByPaymentCategory(@RequestParam("paymentCategory") PaymentCategory paymentCategory){
        return adService.getAdsByPaymentCategory(paymentCategory);
    }
    @GetMapping("/average-price")
    public List<Advertisement> getAdsByAveragePriceIsLessOrEqual(@RequestParam("averagePrice") float averagePrice){
        return adService.getAdsByAveragePriceIsLessOrEqual(averagePrice);
    }
    //UPDATE
    @PutMapping("{id}")
    public String update(@RequestBody AdvertisementDTO ad, @PathVariable String id){
        Advertisement updateAd = Advertisement.builder()
                .id(id)
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
        return adService.updateAd(updateAd, id);
    }
    //DELETE
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        adService.delete(id);
    }

}
