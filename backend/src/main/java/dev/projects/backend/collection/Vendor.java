package dev.projects.backend.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.projects.backend.dto.VendorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(builderMethodName = "vendorBuilder")
@Document(collection="vendor")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vendor extends Person{

    private List<String> advertisementsId;

    public Vendor (VendorDTO vendor) {
        super(
                vendor.getPersonId(),
                vendor.getUserName(),
                vendor.getPassWord(),
                vendor.getFirstName(),
                vendor.getLastName(),
                vendor.getContactDetails(),
                vendor.getAddresses(),
                vendor.getLoginRole(),
                vendor.getGpsData()
        );
        this.setAdvertisementsId(vendor.getAdvertisementsId());
    }
}
