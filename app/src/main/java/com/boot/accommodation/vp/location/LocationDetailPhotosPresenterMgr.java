package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.view.PhotoDTO;

import java.io.File;

/**
 * Preseter place photo mgr
 * @author Dungnx
 */
public interface LocationDetailPhotosPresenterMgr {

    /**
     * Get place photo
     * @param placeId
     */
    void getPlacePhoto(long placeId);

    /**
     * Get more place photo
     * @param placeId
     * @param adapter
     */
    void getMorePlacePhotoJoco( long placeId, BaseRecyclerViewAdapter adapter);

    /**
     * Get more photo owner
     * @param placeId
     * @param adapter
     */
    void getMorePlacePhotoOwner( long placeId,BaseRecyclerViewAdapter adapter);

    /**
     * Add photo
     * @param file
     * @param photoDTO
     */
    void requestUpLoadPhotoToStatic(long locationId, File file, PhotoDTO photoDTO);
}
