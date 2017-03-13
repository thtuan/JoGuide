package com.boot.accommodation.dto.view;

/**
 * TourInviteDTO
 *
 * @author tuanlt
 * @since 2:05 PM 12/16/16
 */
public class TourInviteDTO extends BaseDTO {

    private String inviteToken; //Invite token

    public String getInviteToken() {
        return inviteToken;
    }

    public void setInviteToken(String inviteToken) {
        this.inviteToken = inviteToken;
    }
}
