package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.response.TourItineraryResponseDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * Presenter lich trinh tour
 *
 * @author tuanlt
 * @since: 5/19/2016
 */
public class TourItineraryPresenter implements TourItineraryPresenterMgr {

    private TourItineraryViewMgr tourItineraryViewMgr; // view
    private TourModelMgr tourModelMgr; // model
    private String TAG = ""; // Tag

    public TourItineraryPresenter(TourItineraryViewMgr tourItineraryViewMgr) {
        this.tourItineraryViewMgr = tourItineraryViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(tourItineraryViewMgr.getClass());
    }

    @Override
    public void getTourItenirary(long tourId) {
        tourModelMgr.getTourItinerary(tourId, new ModelCallBack<TourItineraryResponseDTO>(TAG) {
            @Override
            public void onSuccess(TourItineraryResponseDTO response) {
                getTourItinerarySuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourItineraryViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Xu li lay lich trinh tour thanh cong
     */
    private void getTourItinerarySuccess(TourItineraryResponseDTO response){
        tourItineraryViewMgr.renderLayout(response.getData().getTourItinerary());
    }

}
