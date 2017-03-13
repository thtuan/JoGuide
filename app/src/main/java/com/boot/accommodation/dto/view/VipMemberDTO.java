package com.boot.accommodation.dto.view;

/**
 * VipMemberDTO
 *
 * @author tuanlt
 * @since 8:30 PM 12/25/16
 */
public class VipMemberDTO extends BaseDTO {

    private long id; //Id
    private int status; //Status
    private String registrationCode; //Registration code
    private String registrationDate; //Registration date
    private String description; //Description

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
