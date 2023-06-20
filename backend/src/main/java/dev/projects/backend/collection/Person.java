package dev.projects.backend.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.neuefische.backend.enums.LoginRole;
import de.neuefische.backend.model.Address;
import de.neuefische.backend.model.ContactDetail;
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
@Builder(builderMethodName = "personBuilder")
@Document(collection="person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

    @Id
    private String personId;
    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private List<ContactDetail> contactDetails;
    private List<Address> addresses;
    private LoginRole loginRole=LoginRole.USER;
    private String gpsData;

}
