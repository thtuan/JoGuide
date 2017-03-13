package com.boot.accommodation.vp.tour;

import java.io.File;

/**
 * Mgr presenter
 *
 * @author tuanlt
 * @since 11:54 PM 7/24/2016
 */
public interface ProfilePresenterMgr {
    /**
     * Upload photo to server statistic
     *
     * @param file
     */
    void uploadPhoto( File file );

    /**
     * Upload photo to server host( not server statistic)
     *
     * @param userId
     * @param photo
     */
    void requestUpLoadPhoto( long userId, String photo );

}
