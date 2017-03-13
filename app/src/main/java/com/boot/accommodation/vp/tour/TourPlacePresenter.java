package com.boot.accommodation.vp.tour;

import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.TourPlaceResponseDTO;
import com.boot.accommodation.dto.view.TourItineraryItemDTO;
import com.boot.accommodation.dto.view.TourPlaceViewDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.StringUtil;

/**
 * Tour place presenter
 *
 * @author tuanlt
 * @since 6:04 PM 5/26/2016
 */
public class TourPlacePresenter implements TourPlacePresenterMgr {

    TourPlaceInfoViewMgr tourPlaceViewMgr; // view tour place
    TourModelMgr tourModelMgr; // model request du lieu
    TourPlaceViewDTO tourPlaceViewDTO; // dto cho man hinh
    public  TourPlacePresenter( TourPlaceInfoViewMgr tourPlaceViewMgr){
        this.tourPlaceViewMgr = tourPlaceViewMgr;
        tourModelMgr = new TourModel();

    }

    @Override
    public void getTourPlaceInfo(String tourId, String placeId) {
        tourModelMgr.getTourPlaceInfo(tourId, placeId, new ModelCallBack<TourPlaceResponseDTO>() {
            @Override
            public void onSuccess(TourPlaceResponseDTO response) {
                tourPlaceViewDTO = response.getData();
                tourPlaceViewMgr.renderLayout(tourPlaceViewDTO);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourPlaceViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getTimeTour(TourItineraryItemDTO tourItineraryItemDTO) {
        StringBuilder time = new StringBuilder();
        String fromTime = StringUtil.splitStringWithCount(tourItineraryItemDTO.getFromDate(), Constants.STR_COLON, 2);
        String toTime = StringUtil.splitStringWithCount(tourItineraryItemDTO.getToDate(), Constants.STR_COLON, 2);
        String fromDate = DateUtil.convertDateWithFormat(tourItineraryItemDTO.getFromDate(), DateUtil
                .FORMAT_TIME, DateUtil.FORMAT_DATE_TIME);
        String toDate = DateUtil.convertDateWithFormat(tourItineraryItemDTO.getToDate(), DateUtil.FORMAT_TIME,
                DateUtil.FORMAT_DATE_TIME);
        time.append(StringUtil.getString(R.string.text_plan));
        if(!StringUtil.isNullOrEmpty(fromTime) || !StringUtil.isNullOrEmpty(toTime)) {
            time.append(Constants.STR_SPACE);
            time.append(fromTime);
            time.append(Constants.STR_SUBTRACTION);
            time.append(toTime);
        }
        String hours = DateUtil.subtractDatesToHourMinutes(toDate,fromDate);
        if(!StringUtil.isNullOrEmpty(hours)) {
            time.append(Constants.STR_BRACKET_LEFT);
            time.append(hours);
            time.append(Constants.STR_BRACKET_RIGHT);
        }
        tourPlaceViewMgr.setTimeTour(time);
    }
}
