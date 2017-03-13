package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.response.TouristLeaderResponseDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 5/19/2016
 */
public class TouristLeaderPresenter implements TouristLeaderPresenterMgr {

    private TouristLeaderViewMgr touristLeaderViewMgr;
    private TourModelMgr tourModelMgr;
    private int page = 0;
    private String TAG = ""; // Tag

    public TouristLeaderPresenter(TouristLeaderViewMgr touristLeaderViewMgr) {
        this.touristLeaderViewMgr = touristLeaderViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(touristLeaderViewMgr.getClass());
    }

    @Override
    public void getLeaderInfo(String touristId) {
        tourModelMgr.getLeaderInfo(touristId, page, new ModelCallBack<TouristLeaderResponseDTO>(TAG) {
            @Override
            public void onSuccess(TouristLeaderResponseDTO response) {
                touristLeaderViewMgr.renderLayout(response.getData());
            }

            @Override
            public void onError( int errorCode, String error ) {
                touristLeaderViewMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
