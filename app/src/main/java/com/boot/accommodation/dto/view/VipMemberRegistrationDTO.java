package com.boot.accommodation.dto.view;

/**
 * VipMemberRegistrationDTO
 *
 * @author tuanlt
 * @since 8:23 PM 12/25/16
 */
public class VipMemberRegistrationDTO extends BaseDTO {

    private String firstName; //First name
    private String lastName; //Last name
    private String email; //Email
    private String idNumber; //id number

    private String idNumberIssueDate; //Date create id
    private String idNumberIssuePlace;  //Place create id

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumberIssueDate() {
        return idNumberIssueDate;
    }

    public void setIdNumberIssueDate(String idNumberIssueDate) {
        this.idNumberIssueDate = idNumberIssueDate;
    }

    public String getIdNumberIssuePlace() {
        return idNumberIssuePlace;
    }

    public void setIdNumberIssuePlace(String idNumberIssuePlace) {
        this.idNumberIssuePlace = idNumberIssuePlace;
    }


}
