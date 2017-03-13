package com.boot.accommodation.vp.profile;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.TripResponseDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.ProfileModel;
import com.boot.accommodation.model.mgr.ProfileModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * AdminCollectionPresenter
 *
 * @author thtuan
 * @since 2:45 PM 14-07-2016
 */
public class AdminCollectionPresenter implements AdminCollectionPresenterMgr {
    private AdminCollectionFragmentMgr adminCollectionFragmentMgr;
    private ProfileModelMgr profileModelMgr;
    private int page = 0;
    private int pageSize = 0;
    private String TAG = ""; // Tag

    private List<TripItemDTO> lstTrips = new ArrayList<>();
    public AdminCollectionPresenter(AdminCollectionFragmentMgr adminCollectionFragmentMgr) {
        this.adminCollectionFragmentMgr = adminCollectionFragmentMgr;
        profileModelMgr = new ProfileModel();
        TAG = Utils.getTag(adminCollectionFragmentMgr.getClass());
    }


    @Override
    public void getCollection(Long adminId) {
        profileModelMgr.getAdminCollection(adminId, page, new ModelCallBack<TripResponseDTO>(TAG) {
            @Override
            public void onSuccess(TripResponseDTO response) {
                getCollectionSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                adminCollectionFragmentMgr.showMessageErr(errorCode, error);
            }

        });
    }

    private void getCollectionSuccess( TripResponseDTO response ) {
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
        lstTrips.addAll(response.getData().getTours());
        adminCollectionFragmentMgr.renderLayout(lstTrips);
    }

    /**
     * get more collection
     * @param adapter
     * @param adminId
     */
    @Override
    public void getMoreCollection(BaseRecyclerViewAdapter adapter, long adminId) {
        // Xu li load more nua hay ko
        if (Utils.checkLoadMore(adapter, pageSize, lstTrips.size())) {
            page++;
            profileModelMgr.getAdminCollection(adminId, page, new ModelCallBack<TripResponseDTO>(TAG) {
                @Override
                public void onSuccess(TripResponseDTO response) {
                    getCollectionSuccess(response);
                }

                @Override
                public void onError( int errorCode, String error ) {
                    adminCollectionFragmentMgr.showMessageErr(errorCode, error);
                }

            });
        }
    }
}
