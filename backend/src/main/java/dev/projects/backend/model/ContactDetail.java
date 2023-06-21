package dev.projects.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ContactDetail {

    private String email;
    private String phoneNumber;
    private String homepageURL;
}
