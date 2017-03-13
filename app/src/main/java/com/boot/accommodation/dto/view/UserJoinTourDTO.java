package com.boot.accommodation.dto.view;

/**
 * UserJoinTourDTO
 *
 * @author tuanlt
 * @since 3:30 PM 12/16/16
 */
public class UserJoinTourDTO extends BaseDTO {

    private Long userId;
    private String userIdentifier;
//    private User userType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

//    public UserType getUserType() {
//        return userType;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }
}
