package dev.projects.backend.service;

import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.enums.BusinessCategory;
import dev.projects.backend.enums.PaymentCategory;

import java.util.List;

public interface AdService {
    Advertisement save(Advertisement advertisement);
    List<Advertisement>  getAllAds();
    Advertisement getAdWithId(String id);
    List<Advertisement> getAdByBusiness(BusinessCategory businessCategory);
    List<Advertisement> getAdsByPaymentCategory(PaymentCategory paymentCategory);
    List<Advertisement> getAdsByAveragePriceIsLessOrEqual(float averagePrice);
    String updateAd(Advertisement advertisement, String id);
    void delete(String id);


}
