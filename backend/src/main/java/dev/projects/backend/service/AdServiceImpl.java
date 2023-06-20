package dev.projects.backend.service;

import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.enums.BusinessCategory;
import dev.projects.backend.enums.PaymentCategory;
import dev.projects.backend.repository.AdRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class AdServiceImpl implements AdService{

    @Autowired
    private AdRepository adRepository;
    @Autowired
    private GenerateUUIDService uuid;

    @Override
    public Advertisement save(Advertisement advertisement) {
        advertisement.setId(uuid.getUUID());
        return adRepository.save(advertisement);
    }

    @Override
    public List<Advertisement> getAllAds() {
        return adRepository.findAll();
    }
    @Override
    public Advertisement getAdWithId(String id) {
        Optional<Advertisement> optionalAdvertisement = adRepository.findById(id);
        if (optionalAdvertisement.isPresent()) {
            return optionalAdvertisement.get();
        } else {
            throw new NoSuchElementException();
        }
    }
    @Override
    public List<Advertisement> getAdByBusiness(BusinessCategory businessCategory) {
        return adRepository.findAdvertisementsByBusinessCategories(businessCategory);
    }
    @Override
    public List<Advertisement> getAdsByPaymentCategory(PaymentCategory paymentCategory) {
        return adRepository.findAdvertisementsByPaymentCategory(paymentCategory);
    }
    @Override
    public List<Advertisement> getAdsByAveragePriceIsLessOrEqual(float averagePrice) {
        return adRepository.findAdvertisementsByAveragePriceIsLessThanEqual(averagePrice);
    }
    @Override
    public String updateAd(Advertisement advertisement, String id) {
        Optional<Advertisement> optionalAdvertisement = adRepository.findById(id);
        if(optionalAdvertisement.isEmpty()){
            throw new NoSuchElementException();
        }else {
            return adRepository.save(advertisement).getId();
        }
    }
    @Override
    public void delete(String id) {
        Optional<Advertisement> optionalAdvertisement = adRepository.findById(id);
        if(optionalAdvertisement.isEmpty()){
            throw new NoSuchElementException();
        }else {
            adRepository.deleteById(id);
        }
    }
}
