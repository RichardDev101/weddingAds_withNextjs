package dev.projects.backend.service;

import dev.projects.backend.collection.Vendor;
import dev.projects.backend.enums.LoginRole;
import dev.projects.backend.repository.VendorRepository;
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
public class VendorServiceImpl implements VendorService{

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private GenerateUUIDService uuid;

    @Override
    public Vendor saveVendor(Vendor vendor) {
            vendor.setPersonId(uuid.getUUID());
            vendor.setLoginRole(LoginRole.VENDOR);
            return vendorRepository.save(vendor);
        }
    @Override
    public List<Vendor> getAllVendors() {
       return vendorRepository.findAll();
    }

    @Override
    public Vendor getVendorById(String id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);
        if(optionalVendor.isPresent()){
            return optionalVendor.get();
        }else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public Vendor updateVendor(Vendor vendor, String id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);
        if(optionalVendor.isEmpty()){
            throw new NoSuchElementException();
        }else{
            return vendorRepository.save(vendor);
        }
    }

    @Override
    public void deleteVendor(String id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);
        if(optionalVendor.isEmpty()){
            throw new NoSuchElementException();
        }else{
            vendorRepository.deleteById(id);
        }
    }
}
