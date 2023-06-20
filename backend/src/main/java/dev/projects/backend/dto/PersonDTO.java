package dev.projects.backend.dto;

import de.neuefische.backend.enums.LoginRole;
import de.neuefische.backend.model.Address;
import de.neuefische.backend.model.ContactDetail;
import lombok.Data;

import java.util.List;

@Data
public class PersonDTO {

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
