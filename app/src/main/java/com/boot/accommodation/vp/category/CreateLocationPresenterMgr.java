package com.boot.accommodation.vp.category;

import com.boot.accommodation.dto.view.CreateLocationDTO;
import com.boot.accommodation.dto.view.UploadPhotoDTO;

/**
 * CreateLocationPresenterMgr
 *
 * @author thtuan
 * @since 4:47 PM 31-07-2016
 */
public interface CreateLocationPresenterMgr {
    /**
     * upload photo
     * @param uploadPhotoDTO
     */
    void uploadPhoto(UploadPhotoDTO uploadPhotoDTO);

    /**
     * create location on mobile
     */
    void requestCreateLocation(CreateLocationDTO createLocationDTO);

    /**
     * delete photo at position
     * @param position
     */
    void deletePhoto(int position);

    /**
     * add photo
     * @param uploadPhotoDTO
     */
    void addPhoto(UploadPhotoDTO uploadPhotoDTO);

    /**
     * check whether all photo uploaded or not
     * @return
     */
    boolean checkUploadDone();

    /**
     * GetCategory
     */
    void getCategory();
}
