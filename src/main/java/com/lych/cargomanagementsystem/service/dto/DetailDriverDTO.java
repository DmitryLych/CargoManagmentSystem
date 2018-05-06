package com.lych.cargomanagementsystem.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetailDriverDTO implements Serializable {

    private static final long serialVersionUID = -7024247093448891433L;

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate yearOfIssue;
    private String address;
    private String tepephoneNumber;
    private String email;
    private Boolean status;
    private DriverLicenseDTO driverLicense;
    private MedicalExaminationDTO medicalExamination;
    //private SearchTruckDTO truck;
    //private List<SearchInsurancePoliciesDTO> insurancePolicies;
    //private List<SearchOrderDTO> orders;
}
