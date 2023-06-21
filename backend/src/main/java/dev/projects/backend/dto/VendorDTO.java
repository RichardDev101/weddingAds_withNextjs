package dev.projects.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper = true)
public class VendorDTO extends PersonDTO {

    private List<String> advertisementsId;

}
