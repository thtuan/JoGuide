package com.boot.accommodation.dto.view;

/**
 * ChangePasswordDTO
 *
 * @author thtuan
 * @since 3:27 PM 07-09-2016
 */
public class ChangePasswordDTO {
    private String oldPass; // current password
    private String newPass; // new password
    public String getOldPass() {
        return oldPass;
    }
    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }
    public String getNewPass() {
        return newPass;
    }
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
