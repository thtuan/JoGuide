package com.boot.accommodation.vp.tour;

/**
 * View mgr for upload file
 *
 * @author tuanlt
 * @since 11:44 PM 7/24/2016
 */
public interface ProfileViewMgr {
    /**
     * Upload file name
     *
     * @param fileName
     */
    void updateFileNameUpload( String fileName );

    /**
     * update bar
     * @param percent
     */
    void updateProgressBar(int percent);

    /**
     * finish update
     */
    void finishUpdatePhoto();

    /**
     * update error
     * @param message
     */
    void updateError(String message);

    /**
     * update Avatar
     * @param url
     */
    public void updateAvatar(String url);
}
