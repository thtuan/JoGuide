package com.boot.accommodation.vp.profile;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;

/**
 * AdminCollectionPresenterMgr
 *
 * @author thtuan
 * @since 2:45 PM 14-07-2016
 */
public interface AdminCollectionPresenterMgr {

    /**
     *  get collection
     * @param itemId
     */
    void getCollection(Long itemId);

    /**
     * get more collection
     * @param adapter
     * @param adminId
     */
    void getMoreCollection(BaseRecyclerViewAdapter adapter, long adminId);
}
