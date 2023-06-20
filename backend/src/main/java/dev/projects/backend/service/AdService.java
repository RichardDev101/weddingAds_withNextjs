package dev.projects.backend.service;

import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.enums.BusinessCategory;
import dev.projects.backend.enums.PaymentCategory;

import java.util.List;

public interface AdService {
    Advertisement save(Advertisement advertisement);
    List<Advertisement>  getAllAds();
    Advertisement getAdWithId(String id);
}
