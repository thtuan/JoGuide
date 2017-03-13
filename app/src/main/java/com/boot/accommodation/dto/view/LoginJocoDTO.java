package com.boot.accommodation.dto.view;

/**
 * DTO login joco
 *
 * @author tuanlt
 * @since 4:08 CH 10/08/2016
 */
public class LoginJocoDTO {
    private String email; // email
    private String password; // password
    private String phone; // phone
    private int loginFrom = LoginFromTypeDTO.MOBILE.getValue(); // Login from mobile/ web

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public int getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(int loginFrom) {
        this.loginFrom = loginFrom;
    }
}
