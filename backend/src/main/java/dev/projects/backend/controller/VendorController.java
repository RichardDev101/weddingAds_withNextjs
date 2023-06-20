package dev.projects.backend.controller;

import dev.projects.backend.collection.Vendor;
import dev.projects.backend.dto.VendorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VendorController {

    private final VendorService vendorService;

    //CREATE
    @PostMapping("/vendor")
    //@PreAuthorize("hasAuthority('LoginRole=ADMIN')")
    public Vendor saveVendor(@RequestBody VendorDTO vendor){
            Vendor newVendor = new Vendor(vendor);
        return vendorService.saveVendor(newVendor);
    }
    //READ
    @GetMapping("/vendors")
    public List<Vendor> getAllVendors(){
        return vendorService.getAllVendors();
    }
    @GetMapping("/vendor/{id}")
    public Vendor getVendorWithId(@PathVariable String id){
        return vendorService.getVendorById(id);
    }
    //UPDATE
    @PutMapping("/vendor/{id}")
    public Vendor update(@RequestBody VendorDTO vendor, @PathVariable String id){
        Vendor newVendor = new Vendor(vendor);
        newVendor.setPersonId(id);
        return vendorService.updateVendor(newVendor, id);
    }
    //DELETE
    @DeleteMapping("/vendor/{id}")
    public void deleteVendor(@PathVariable String id){
        vendorService.deleteVendor(id);
    }
}
