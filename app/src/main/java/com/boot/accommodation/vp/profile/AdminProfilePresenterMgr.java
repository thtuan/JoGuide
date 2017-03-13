package com.boot.accommodation.vp.profile;

/**
 * presenter for admin profile
 *
 * @author vuongbv
 * @since 6:47 PM 7/14/2016
 */
public interface AdminProfilePresenterMgr {

    /**
     * get profile admin
     * @param id
     */
    void getAdminProfile(long id);

    /**
     * method follow admin
     * @param id
     * @param idUserFollow
     */
    void followAdmin(long id, long idUserFollow);
}
