package com.boot.accommodation.dto.view;

import com.boot.accommodation.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO chua profile cua user dang nhap
 *
 * @author tuanlt
 * @since 12:06 PM 5/14/2016
 */
public class ProfileDTO extends BaseDTO {
    private GPSInfoDTO myGPSInfo = new GPSInfoDTO();
    private UserItemDTO userData = new UserItemDTO();
    private List<Long> tourPlanIds = new ArrayList<>();

    public List<Long> getTourPlanIds() {
        return tourPlanIds;
    }

    public void setTourPlanIds(List<Long> tourPlanIds) {
        this.tourPlanIds = tourPlanIds;
        save();
    }

    public UserItemDTO getUserData() {
        return userData;
    }

    public void setUserData( UserItemDTO userData ) {
        this.userData = userData;
        save();
    }

    /**
     * Luu thong tin profile
     */
    public void save() {
        PreferenceUtils.saveObject(this, PreferenceUtils.REFERENCE_PROFILE);
    }

    public GPSInfoDTO getMyGPSInfo() {
        return myGPSInfo;
    }

    public void setMyGPSInfo(GPSInfoDTO myGPSInfo) {
        this.myGPSInfo = myGPSInfo;
        save();
    }

}
