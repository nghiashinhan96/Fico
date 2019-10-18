package vn.com.tpf.microservices.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import vn.com.tpf.microservices.models.*;
import vn.com.tpf.microservices.models.Automation.*;
import vn.com.tpf.microservices.models.QuickLead.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataInitial {
    public static Map<String, Object> getDataFromDE_QL(Application application) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("ApplicationDTO", application);
        return map;
    }

    public static Map<String, Object> getDataFromDE(Application application) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();

        map.put("ApplicationDTO", application);
        map.put("AppId", application.getApplicationId());

        ////********************************APPLICATIONINFO DTO************************************////
        //PersonalInfo
        PersonalInfo personalInfo=application.getApplicationInformation().getPersonalInformation().getPersonalInfo();

        //Identification
        List<IdentificationDTO> identificationDTOs = new ArrayList<>();
        for (Identification identification : application.getApplicationInformation().getPersonalInformation().getIdentifications()) {
            identificationDTOs.add(new IdentificationDTO(identification.getIdentificationType(), identification.getIdentificationNumber(), identification.getIssueDate(), identification.getExpiryDate(), identification.getIssuingCountry()));
        }

        //Address
        List<AddressDTO> addressDTOs = new ArrayList<>();
        for (Address address : application.getApplicationInformation().getPersonalInformation().getAddresses()) {
            AddressDTO addressDTO = AddressDTO.builder()
                    .addressType(address.getAddressType())
                    .country(address.getCountry())
                    .region(address.getState())
                    .city(address.getCity())
                    .area(address.getArea())
                    .building(address.getAddressLine1())
                    .house(address.getAddressLine2())
                    .ward(address.getAddressLine3())
                    .residentDurationYear(address.getYearsInCurrentAddress())
                    .residentDurationMonth(address.getMonthsInCurrentAddress())
                    .mobilePhone(address.getPhoneNumbers().stream().filter(c->c.getPhoneType().equals("Mobile Phone")).findAny().isPresent()?address.getPhoneNumbers().stream().filter(c->c.getPhoneType().equals("Mobile Phone")).findAny().get().getPhoneNumber():"").build();
            addressDTOs.add(addressDTO);
        }

        //Family
        List<FamilyDTO> familyDTOs = new ArrayList<>();
        for (Family family : application.getApplicationInformation().getPersonalInformation().getFamily()) {
            FamilyDTO familyDTO = FamilyDTO.builder()
                    .relationshipType(family.getRelationship())
                    .memberName(family.getMemberName())
                    .phoneNumber(family.getPhoneNumber())
                    .educationStatus("")
                    .comName("").build();
            familyDTOs.add(familyDTO);
        }

        //Employment Detail
        EmploymentDetails employmentDetails=application.getApplicationInformation().getEmploymentDetails();
        EmploymentDTO employmentDTO = EmploymentDTO.builder()
                .occupationType(employmentDetails.getOccupationType())
                .taxCode(employmentDetails.getEmployerCode())
                .natureOfBussiness(employmentDetails.getNatureOfBusiness())
                .natureOfOccupation("Unemployed")
                .industry(employmentDetails.getIndustry())
                .department(employmentDetails.getDepartmentName())
                .level(employmentDetails.getDesignation())
                .employmentStatus(employmentDetails.getEmploymentStatus())
                .durationYears(employmentDetails.getYearsInJob())
                .durationMonths(employmentDetails.getMonthsInJob()).build();

        //Income Detail
        List<IncomeDetailDTO> incomeDetailDTOList=new ArrayList<>();
        for(FinancialDetail financialDetail : application.getApplicationInformation().getFinancialDetails())
        {
            IncomeDetailDTO incomeDetailDTO=IncomeDetailDTO.builder()
                    .incomeHead(financialDetail.getIncomeExpense())
                    .frequency("Monthly")
                    .amount(financialDetail.getAmount())
                    .percentage("100")
                    .modeOfPayment(financialDetail.getModeOfPayment())
                    .dayOfSalaryPayment(financialDetail.getDayOfSalaryPayment())
                    .build();
            incomeDetailDTOList.add(incomeDetailDTO);
        }

        ApplicationInfoDTO applicationInfoDTO = ApplicationInfoDTO.builder()
                .gender(personalInfo.getGender())
                .firstName(personalInfo.getLastName())
                .middleName(personalInfo.getMiddleName())
                .lastName(personalInfo.getFirstName())
                .dateOfBirth(personalInfo.getDateOfBirth())
                .placeOfIssue("Hà Nội")
                .maritalStatus(personalInfo.getMaritalStatus())
                .national(personalInfo.getNationality())
                .education(personalInfo.getCustomerCategoryCode())
                .identification(identificationDTOs)
                .address(addressDTOs)
                .email(application.getApplicationInformation().getPersonalInformation().getCommunicationDetails().getPrimaryEmailId())
                .family(familyDTOs)
                .employmentDetails(employmentDTO)
                .incomeDetails(incomeDetailDTOList)
                .build();
        map.put("ApplicationInfoDTO", applicationInfoDTO);
        ////********************************END APPLICATIONINFO DTO************************************////

        ////******************************** LOAN DETAIL DTO************************************////
        //LoanDetails
        LoanDetails loanDetails=application.getLoanDetails();
        LoanDetailsDTO loanDetailsDTO = LoanDetailsDTO.builder()
                .branch("VINID") //set branch default la FPT
                .channel("VINID")
                .applicationNumber(loanDetails.getSourcingDetails().chassisApplicationNum)
                .loanAppType(loanDetails.getSourcingDetails().loanApplicationType)
                .productName(loanDetails.getSourcingDetails().productCode)
                .scheme(loanDetails.getSourcingDetails().getSchemeCode())
                .loanAmount(loanDetails.getSourcingDetails().getLoanAmountRequested())
                .loanTerm(loanDetails.getSourcingDetails().getRequestedTenure())
                .saleAgentCode(loanDetails.getSourcingDetails().getSaleAgentCode())
                .build();
        map.put("LoanDetailsDTO", loanDetailsDTO);

        //LoanVAP
        LoanDetailsVapDTO loanDetailsVapDTO = LoanDetailsVapDTO.builder()
                .vapProduct(loanDetails.getVapDetails().vapProduct) //set branch default la FPT
                .vapTreatment(loanDetails.getVapDetails().vapTreatment)
                .insuranceCompany(loanDetails.getVapDetails().getInsuranceCompany())
                .build();
        map.put("LoanDetailsVapDTO", loanDetailsVapDTO);

        ////********************************END LOAN DETAIL DTO************************************////

        ////******************************** DOCUMENT DTO ************************************////
        List<DocumentDTO> documentDTOS = new ArrayList<>();
        if(application.getDocuments()!=null && application.getDocuments().size()>0){
            for(Document document : application.getDocuments()) {
                DocumentDTO documentDTO = DocumentDTO.builder ()
                        .originalname(document.getOriginalname())
                        .filename(document.getFilename()).build();
                documentDTOS.add(documentDTO);
            }
        }
        map.put("DocumentDTO", documentDTOS);
        ////********************************END DOCUMENT DTO************************************////

        ////******************************** REFERENCE DTO ************************************////

        List<ReferenceDTO> referenceDTOs = new ArrayList<>();
        for(Reference reference : application.getReferences()) {
            ReferenceDTO referenceDTO = ReferenceDTO.builder()
                    .fullName(reference.getName())
                    .relationShip(reference.getRelationship())
                    .mobilePhoneNumber(reference.getPhoneNumbers().stream().filter(c->c.getPhoneType().equals("Mobile Phone")).findAny().isPresent()?reference.getPhoneNumbers().stream().filter(c->c.getPhoneType().equals("Mobile Phone")).findAny().get().getPhoneNumber():"").build();
            referenceDTOs.add(referenceDTO);
        }
        map.put("ReferenceDTO", referenceDTOs);
        ////********************************END REFERENCE DTO************************************////

        ////******************************** DYNAMIC FORM ************************************////
        List<DynamicForm> dynamicFormList=new ArrayList<>();
        for(DynamicForm dynamicForm: application.getDynamicForm()){
            if(dynamicForm.getFormName().equals("frmAppDtl")){
                MiscFrmAppDtlDTO miscFrmAppDtlDTO = MiscFrmAppDtlDTO.builder()
                        .loanPurpose(dynamicForm.getLoanPurpose())
                        .numOfDependents(dynamicForm.getNumberOfDependents())
                        .houseOwnership(dynamicForm.getHouseOwnership())
                        .mortgagePaymentCost(dynamicForm.getMonthlyFee())
                        .newBankCardNumber(dynamicForm.getNewBankCardNumber())
                        .salesAgentCode(dynamicForm.getSaleAgentCode())
                        .maxRequestRate(dynamicForm.getMaximumInterestedRate())
                        .totalMonthlyPayable(dynamicForm.getTotalMonthlyPayable())
                        .remark(dynamicForm.getRemark()).build();
                map.put("MiscFrmAppDtlDTO", miscFrmAppDtlDTO);
            }


//            //VINID
//           if(dynamicForm.getFormName().equals("frm2WL")){
//               MiscVinIdDTO miscVinIdDTO = MiscVinIdDTO.builder()
//                        .model(dynamicForm.getModel())
//                        .goodCode(dynamicForm.getGoodCode())
//                        .goodType(dynamicForm.getGoodType())
//                        .quantity(dynamicForm.getQuantity())
//                        .venCode(dynamicForm.getVenCode())
//                        .vinCode(dynamicForm.getVinCode())
//                        .saleChannel(dynamicForm.getSaleChannel())
//                        .dealerCode(dynamicForm.getDealerCode())
//                        .productName(dynamicForm.getProductName())
//                        .goodPrice(dynamicForm.getGoodPrice())
//                        .downPayment(dynamicForm.getDownPayment())
//                        .employeeCardNum(dynamicForm.getEmployeeCard()).build();
//                map.put("MiscVinIdDTO", miscVinIdDTO);
//            }

//            //FPT
//            if(dynamicForm.getFormName().equals("frmFPT")){
//                 MiscFptDTO  miscFptDTO = MiscFptDTO.builder()
//                            .model(dynamicForm.getModel())
//                            .goodCode(dynamicForm.getGoodCode())
//                            .goodType(dynamicForm.getGoodType())
//                            .quantity(dynamicForm.getQuantity())
//                            .goodPrice(dynamicForm.getGoodPrice())
//                            .downPayment(dynamicForm.getDownPayment())
//                            .employeeCardNum(dynamicForm.getEmployeeCard()).build();
//                map.put("MiscFptDTO", miscFptDTO);
//            }

        }
        ////********************************ENDDYNAMIC FORM************************************////

        return map;
    }

    public static Map<String, Object> getDataMomo(Application application){
        Map<String, Object> map = new HashMap<>();

        map.put("ApplicationDTO", application);
        map.put("AppId", application.getApplicationId());

        ////********************************APPLICATIONINFO DTO************************************////
        //PersonalInfo
        PersonalInfo personalInfo=application.getApplicationInformation().getPersonalInformation().getPersonalInfo();
        personalInfo.setCustomerCategoryCode("Highschool");

        //Identification
        List<IdentificationDTO> identificationDTOs = new ArrayList<>();
        for (Identification identification : application.getApplicationInformation().getPersonalInformation().getIdentifications()) {
            identificationDTOs.add(IdentificationDTO.builder().documentType(identification.getIdentificationType())
                                                                .documentNumber(identification.identificationNumber)
                                                                .issueDate(identification.getIssueDate()==null?"":identification.getIssueDate())
                                                                .expirationDate("")
                                                                .countryOfIssue(identification.getIssuingCountry()).build());
        }

        //Address
        List<AddressDTO> addressDTOs = new ArrayList<>();
        for (Address address : application.getApplicationInformation().getPersonalInformation().getAddresses()) {
            AddressDTO addressDTO = AddressDTO.builder()
                    .addressType(address.getAddressType())
                    .country(address.getCountry())
                    .region("ALL")
                    .city(address.getCity())
                    .area(address.getArea())
                    .building(address.getAddressLine1())
                    .house(address.getAddressLine2())
                    .ward(address.getAddressLine3())
                    .residentDurationYear("1")
                    .residentDurationMonth("1")
                    .mobilePhone(address.getPhoneNumbers().stream().filter(c->c.getPhoneType().equals("Mobile Phone")).findAny().isPresent()?address.getPhoneNumbers().stream().filter(c->c.getPhoneType().equals("Mobile Phone")).findAny().get().getPhoneNumber().replace("+84", ""):"").build();
            addressDTOs.add(addressDTO);
        }
        AddressDTO addressDTO1 = AddressDTO.builder()
                .addressType("Family Book Address")
                .country(addressDTOs.get(0).getCountry())
                .region("ALL")
                .city(addressDTOs.get(0).getCity())
                .area(addressDTOs.get(0).getArea())
                .building(addressDTOs.get(0).getBuilding())
                .house(addressDTOs.get(0).getHouse())
                .ward(addressDTOs.get(0).getWard())
                .residentDurationYear("")
                .residentDurationMonth("")
                .mobilePhone("").build();
        addressDTOs.add(addressDTO1);

        AddressDTO addressDTO2 = AddressDTO.builder()
                .addressType("Working Address")
                .country(addressDTOs.get(0).getCountry())
                .region("ALL")
                .city(addressDTOs.get(0).getCity())
                .area(addressDTOs.get(0).getArea())
                .building(addressDTOs.get(0).getBuilding())
                .house(addressDTOs.get(0).getHouse())
                .ward(addressDTOs.get(0).getWard())
                .residentDurationYear("")
                .residentDurationMonth("")
                .mobilePhone("").build();
        addressDTOs.add(addressDTO2);

        //Family
        List<FamilyDTO> familyDTOs = new ArrayList<>();
        if(application.getApplicationInformation().getPersonalInformation().getFamily()!=null && application.getApplicationInformation().getPersonalInformation().getFamily().size()>0) {
            for (Family family : application.getApplicationInformation().getPersonalInformation().getFamily()) {
                FamilyDTO familyDTO = FamilyDTO.builder()
                        .relationshipType(family.getRelationship())
                        .memberName(family.getMemberName())
                        .phoneNumber(family.getPhoneNumber())
                        .educationStatus("")
                        .comName("").build();
                familyDTOs.add(familyDTO);
            }
        }

        //check MarialStatus: married
        if(personalInfo.getMaritalStatus().equals("Married")){
            AddressDTO addressDTO3 = AddressDTO.builder()
                    .addressType("Spouse Address")
                    .country(addressDTOs.get(0).getCountry())
                    .region("ALL")
                    .city(addressDTOs.get(0).getCity())
                    .area(addressDTOs.get(0).getArea())
                    .building(addressDTOs.get(0).getBuilding())
                    .house(addressDTOs.get(0).getHouse())
                    .ward(addressDTOs.get(0).getWard())
                    .residentDurationYear("")
                    .residentDurationMonth("")
                    .mobilePhone(familyDTOs.stream().filter(c->c.getRelationshipType().equals("Spouse")).findAny().isPresent()?familyDTOs.stream().filter(c->c.getRelationshipType().equals("Spouse")).findAny().get().getPhoneNumber():"").build();
            addressDTOs.add(addressDTO3);
        }

        //Employment Detail
        EmploymentDetails employmentDetails=application.getApplicationInformation().getEmploymentDetails();
        EmploymentDTO employmentDTO = EmploymentDTO.builder()
                .occupationType("Others")
                .natureOfOccupation("Unemployed")
                .build();

        //Income Detail
        List<IncomeDetailDTO> incomeDetailDTOList=new ArrayList<>();
        for(FinancialDetail financialDetail : application.getApplicationInformation().getFinancialDetails())
        {
            IncomeDetailDTO incomeDetailDTO=IncomeDetailDTO.builder()
                    .incomeHead(financialDetail.getIncomeExpense())
                    .frequency("Monthly")
                    .amount(financialDetail.getAmount())
                    .percentage("100")
                    .build();
            incomeDetailDTOList.add(incomeDetailDTO);
        }

        ApplicationInfoDTO applicationInfoDTO = ApplicationInfoDTO.builder()
                .gender(personalInfo.getGender())
                .firstName(personalInfo.getLastName())
                .middleName(personalInfo.getMiddleName())
                .lastName(personalInfo.getFirstName())
                .dateOfBirth(personalInfo.getDateOfBirth())
                .placeOfIssue("Hà Nội")
                .maritalStatus(personalInfo.getMaritalStatus())
                .national(personalInfo.getNationality())
                .education(personalInfo.getCustomerCategoryCode())
                .identification(identificationDTOs)
                .address(addressDTOs)
                .email(application.getApplicationInformation().getPersonalInformation().getCommunicationDetails().getPrimaryEmailId())
                .family(familyDTOs)
                .employmentDetails(employmentDTO)
                .incomeDetails(incomeDetailDTOList)
                .build();
        map.put("ApplicationInfoDTO", applicationInfoDTO);
        ////********************************END APPLICATIONINFO DTO************************************////

        ////******************************** LOAN DETAIL DTO************************************////
        //LoanDetails
        LoanDetails loanDetails=application.getLoanDetails();
        LoanDetailsDTO loanDetailsDTO = LoanDetailsDTO.builder()
                .branch("MOMO") //set branch default la FPT
                .channel("MOMO")
                .applicationNumber(loanDetails.getSourcingDetails().chassisApplicationNum)
                .loanAppType(loanDetails.getSourcingDetails().loanApplicationType)
                .productName(loanDetails.getSourcingDetails().productCode)
                .scheme(loanDetails.getSourcingDetails().getSchemeCode())
                .loanAmount(loanDetails.getSourcingDetails().getLoanAmountRequested())
                .loanTerm(loanDetails.getSourcingDetails().getRequestedTenure())
                .saleAgentCode(loanDetails.getSourcingDetails().getSaleAgentCode())
                .build();


        if (loanDetailsDTO.getProductName().equals("DG_01"))
        {
            loanDetailsDTO.setScheme("DG01_MOMO TRIAL");
        }
        else
        {
            loanDetailsDTO.setScheme("DG02_MOMO ENTRY");
        }
        loanDetailsDTO.setProductName("DGL_CASH");
        map.put("LoanDetailsDTO", loanDetailsDTO);

        //LoanVAP
        LoanDetailsVapDTO loanDetailsVapDTO = LoanDetailsVapDTO.builder()
                .vapProduct(loanDetails.getVapDetails().vapProduct) //set branch default la FPT
                .vapTreatment(loanDetails.getVapDetails().vapTreatment)
                .insuranceCompany(loanDetails.getVapDetails().getInsuranceCompany())
                .build();
        map.put("LoanDetailsVapDTO", loanDetailsVapDTO);

        ////********************************END LOAN DETAIL DTO************************************////

        ////******************************** DOCUMENT DTO ************************************////
        List<DocumentDTO> documentDTOS = new ArrayList<>();
        if(application.getDocuments()!=null && application.getDocuments().size()>0){
            for(Document document : application.getDocuments()) {
                DocumentDTO documentDTO = DocumentDTO.builder ()
                        .originalname(document.getOriginalname())
                        .filename(document.getFilename()).build();
                documentDTOS.add(documentDTO);
            }
        }
        map.put("DocumentDTO", documentDTOS);
        ////********************************END DOCUMENT DTO************************************////

        ////******************************** REFERENCE DTO ************************************////

        List<ReferenceDTO> referenceDTOs = new ArrayList<>();
        for(Reference reference : application.getReferences()) {
            ReferenceDTO referenceDTO = ReferenceDTO.builder()
                    .fullName(reference.getName())
                    .relationShip(reference.getRelationship())
                    .mobilePhoneNumber(reference.getPhoneNumbers().stream().filter(c->c.getPhoneType().equals("Mobile Phone")).findAny().isPresent()?reference.getPhoneNumbers().stream().filter(c->c.getPhoneType().equals("Mobile Phone")).findAny().get().getPhoneNumber():"").build();
            referenceDTOs.add(referenceDTO);
        }
        map.put("ReferenceDTO", referenceDTOs);
        ////********************************END REFERENCE DTO************************************////

        ////******************************** DYNAMIC FORM ************************************////

        MiscFrmAppDtlDTO miscFrmAppDtlDTO = MiscFrmAppDtlDTO.builder()
                .loanPurpose("Education, sports")
                .numOfDependents("0")
                .houseOwnership("Family Owned without Mortgage")
                .mortgagePaymentCost("0")
                .newBankCardNumber("2222222222222222")
                .salesAgentCode("DS0001100")
                .maxRequestRate("62").build();
        map.put("MiscFrmAppDtlDTO", miscFrmAppDtlDTO);

        ////********************************ENDDYNAMIC FORM************************************////

        return map;
    }
}