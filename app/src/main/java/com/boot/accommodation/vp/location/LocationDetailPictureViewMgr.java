package com.boot.accommodation.vp.location;

import com.boot.accommodation.dto.view.PhotoDTO;

import java.util.List;

/**
 * Mgr view place photo
 *
 * @author Dungnx
 */
public interface LocationDetailPictureViewMgr {

    /**
     * Set photo owner
     * @param photo
     */
    void renderPhotoOwner( List<PhotoDTO> photo);

    /**
     * Set photo joco
     * @param photo
     */
    void renderPhotoJoco( List<PhotoDTO> photo);

    /**
     * Render no data
     */
    void renderNoData(boolean isShow);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    void finishProcessDialog();

}
