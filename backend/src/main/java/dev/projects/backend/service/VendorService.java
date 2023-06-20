package dev.projects.backend.service;

import dev.projects.backend.collection.Vendor;

import java.util.List;

public interface VendorService {
    Vendor saveVendor(Vendor vendor);
    List<Vendor> getAllVendors();
    Vendor getVendorById(String id);
    Vendor updateVendor(Vendor vendor, String id);
    void deleteVendor(String id);
}
