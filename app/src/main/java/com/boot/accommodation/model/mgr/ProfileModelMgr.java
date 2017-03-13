package com.boot.accommodation.model.mgr;

import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.dto.view.FollowDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.listener.ModelCallBack;

import java.io.File;

/**
 * Mgr profile
 *
 * @author tuanlt
 * @since 3:42 PM 7/11/2016
 */
public interface ProfileModelMgr {

    /**
     * Get profile collection
     *
     * @param modelCallBack call\back
     */
    void getProfileCollection( long userId, ModelCallBack modelCallBack );

    /**
     * get collection for admin profile
     *
     * @param adminId       id
     * @param modelCallBack callback
     */
    void getAdminCollection( long adminId, int page, ModelCallBack modelCallBack );

    /**
     * Request edit profile info of tourist
     *
     * @param modelCallBack
     */
    void requestEditProfileInfo( UserItemDTO userItemDTO, ModelCallBack modelCallBack );

    /**
     * Get profile of tourist
     * @param modelCallBack
     */
    void getProfileUser(long touristId, ModelCallBack modelCallBack);

    /**
     * Request follow user
     * @param followDTO
     * @param modelCallBack
     */
    void requestFollow( FollowDTO followDTO, ModelCallBack modelCallBack);

    /**
     * get profile info of admin
     * @param id
     * @param modelCallBack
     */
    void getAdminProfile(long id, ModelCallBack modelCallBack);

    /**
     * Upload photo
     * @param file
     * @param modelCallBack
     */
    void uploadPhoto(File file, ModelCallBack modelCallBack, ProgressUpdateBody.UploadCallbacks uploadCallbacks);

    /**
     * Request upload photo to server host
     * @param userId
     * @param photo
     */
    void requestUploadPhoto(long userId, String photo, ModelCallBack modelCallBack );

}
