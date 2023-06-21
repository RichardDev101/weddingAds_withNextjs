package dev.projects.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Address {

    private String streetName;
    private String houseNo;
    private String apartment;
    private String zipCode;
    private String city;
    private String country;
    private String geoData;
}
