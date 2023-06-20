package dev.projects.backend.service;

import dev.projects.backend.collection.Vendor;
import dev.projects.backend.enums.LoginRole;
import dev.projects.backend.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final GenerateUUIDService uuid;

    public Vendor saveVendor(Vendor vendor) {
            vendor.setPersonId(uuid.getUUID());
            vendor.setLoginRole(LoginRole.VENDOR);
            return vendorRepository.save(vendor);
        }
    public List<Vendor> getAllVendors() {
       return vendorRepository.findAll();
    }

    public Vendor getVendorById(String id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);
        if(optionalVendor.isPresent()){
            return optionalVendor.get();
        }else{
            throw new NoSuchElementException();
        }
    }

    public Vendor updateVendor(Vendor vendor, String id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);
        if(optionalVendor.isEmpty()){
            throw new NoSuchElementException();
        }else{
            return vendorRepository.save(vendor);
        }
    }

    public void deleteVendor(String id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);
        if(optionalVendor.isEmpty()){
            throw new NoSuchElementException();
        }else{
            vendorRepository.deleteById(id);
        }
    }
}
