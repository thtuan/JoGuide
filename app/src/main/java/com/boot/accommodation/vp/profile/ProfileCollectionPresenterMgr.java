package com.boot.accommodation.vp.profile;

/**
 * Mgr thong tin user
 *
 * @author tuanlt
 * @since 6:45 PM 6/6/2016
 */
public interface ProfileCollectionPresenterMgr {
    /**
     * Get profile collection
     *
     * @param userId
     */
    void getProfileCollection( long userId );
}
