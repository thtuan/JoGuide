package com.boot.accommodation.vp.category;

import com.boot.accommodation.dto.view.CreateLocationViewDTO;
import com.boot.accommodation.dto.view.UploadPhotoDTO;

import java.util.List;

/**
 * CreateLocationViewMgr
 *
 * @author thtuan
 * @since 4:47 PM 31-07-2016
 */
public interface CreateLocationViewMgr {
    /**
     * update file name
     * @param fileName
     */
    void updateFileNameUpload(String fileName);

    /**
     * render layout
     * @param uploadPhotoDTOs
     */
    void renderLayout(List<UploadPhotoDTO> uploadPhotoDTOs);

    /**
     * on create location Success
     */
    void onSuccess();

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * Get category success
     */
    void getCategorySuccess(CreateLocationViewDTO createLocationViewDTO);
}
