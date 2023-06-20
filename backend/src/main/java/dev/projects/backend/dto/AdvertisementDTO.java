package dev.projects.backend.dto;

import dev.projects.backend.enums.AdvertisementStatus;
import dev.projects.backend.enums.BusinessCategory;
import dev.projects.backend.enums.PaymentCategory;
import dev.projects.backend.enums.PriceCategory;
import dev.projects.backend.model.Address;
import dev.projects.backend.model.Company;
import dev.projects.backend.model.ContactDetail;
import lombok.Data;

import java.util.List;

@Data
public class AdvertisementDTO {

    private String id;
    private AdvertisementStatus advertisementStatus;
    private PaymentCategory paymentCategory;//payment Categories which the vendor pays to us
    private Company company;
    private List<BusinessCategory> businessCategories;
    private List<String> photosID;
    private String title;
    private String aboutYourself; // Section where one writes about himself in a shot manner
    private String detailInformationForService; //Description about the Service someone ist offering
    private float averagePrice; // Price someone requests for his service
    private List<PriceCategory> priceCategories; //per hour/ per day... etc.
    private List<ContactDetail> contacts;
    private List<Address> locations;
    private String personsID;
}
