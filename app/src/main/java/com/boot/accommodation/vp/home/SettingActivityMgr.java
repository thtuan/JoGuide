package com.boot.accommodation.vp.home;

/**
 * SettingActivityMgr
 *
 * @author thtuan
 * @since 5:05 PM 06-09-2016
 */
public interface SettingActivityMgr {
    /**
     * handle if request change password not error
     * @param response true if change password success, the other is false
     */
    void changePasswordSuccess( Boolean response);

    /**
     * called when request error
     * @param response
     */
    void changePasswordError( Boolean response);
}
