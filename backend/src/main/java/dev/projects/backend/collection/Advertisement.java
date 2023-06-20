package dev.projects.backend.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.projects.backend.enums.AdvertisementStatus;
import dev.projects.backend.enums.BusinessCategory;
import dev.projects.backend.enums.PaymentCategory;
import dev.projects.backend.enums.PriceCategory;
import dev.projects.backend.model.Address;
import dev.projects.backend.model.Company;
import dev.projects.backend.model.ContactDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection="advertisement")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Advertisement {

    @Id
    private String id;
    private AdvertisementStatus advertisementStatus;
    private PaymentCategory paymentCategory;//payment Categories which the vendor pays to us
    private Company company;
    private List<BusinessCategory>businessCategories;
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
