package dev.projects.backend.service;

import dev.projects.backend.collection.Advertisement;
import dev.projects.backend.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdService {

    private final AdRepository adRepository;
    private final GenerateUUIDService uuid;


    public Advertisement save(Advertisement advertisement) {
        advertisement.setId(uuid.getUUID());
        return adRepository.save(advertisement);
    }

    public List<Advertisement> getAllAds() {
        return adRepository.findAll();
    }

    public Advertisement getAdWithId(String id) {
        Optional<Advertisement> optionalAdvertisement = adRepository.findById(id);
        if (optionalAdvertisement.isPresent()) {
            return optionalAdvertisement.get();
        } else {
            throw new NoSuchElementException();
        }
    }

}
