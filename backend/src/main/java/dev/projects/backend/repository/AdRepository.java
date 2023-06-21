package dev.projects.backend.repository;

import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.enums.BusinessCategory;
import dev.projects.backend.enums.PaymentCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends MongoRepository<Advertisement, String> {
    List<Advertisement> findAdvertisementsByBusinessCategories(BusinessCategory businessCategory);
    List<Advertisement> findAdvertisementsByPaymentCategory(PaymentCategory paymentCategory);
    List<Advertisement> findAdvertisementsByAveragePriceIsLessThanEqual(float averagePrice);
}
