package com.boot.accommodation.vp.accommodation;

import com.boot.accommodation.base.BaseResponse;
import com.boot.accommodation.dto.response.FamousLocationResponseDTO;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.AreaTypeDTO;
import com.boot.accommodation.dto.view.FeedbackItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.CommonModel;
import com.boot.accommodation.model.impl.LocationModel;
import com.boot.accommodation.model.mgr.CommonModelMgr;
import com.boot.accommodation.model.mgr.LocationModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.List;

/**
 * AccommodationPresenter
 *
 * @author tuanlt
 * @since 4:44 PM 1/13/17
 */
public class AccommodationPresenter implements AccommodationPresenterMgr {

    private AccommodationViewMgr homeStayViewMgr; //Home stay view mgr
    private LocationModelMgr locationModelMgr; //
    private CommonModelMgr commonModelMgr; //
    private String TAG = ""; // Tag

    public AccommodationPresenter(AccommodationViewMgr homeStayViewMgr) {
        this.homeStayViewMgr = homeStayViewMgr;
        locationModelMgr = new LocationModel();
        commonModelMgr = new CommonModel();
        TAG = Utils.getTag(homeStayViewMgr.getClass());
    }

    @Override
    public void getAreaFilter() {
        locationModelMgr.getFilterAreas(new ModelCallBack<FamousLocationResponseDTO>(TAG) {
            @Override
            public void onSuccess(FamousLocationResponseDTO response) {
                homeStayViewMgr.renderAreaFilter(removeAreaRegion(response.getData()));
            }

            @Override
            public void onError(int errorCode, String error) {
                homeStayViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getAreaSpecial() {
        locationModelMgr.getAreaSpecial(new ModelCallBack<FamousLocationResponseDTO>(TAG) {
            @Override
            public void onSuccess(FamousLocationResponseDTO response) {
                homeStayViewMgr.renderAreaSpecial(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                homeStayViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void requestSendFeedback(FeedbackItemDTO feedbackItemDTO) {
        commonModelMgr.requestSendFeedback(feedbackItemDTO, new ModelCallBack(TAG) {
            @Override
            public void onSuccess( BaseResponse response ) {
                homeStayViewMgr.requestSendFeedbackSuccess();
            }

            @Override
            public void onError( int errorCode, String error ) {
                homeStayViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Remove area region
     */
    private List<AreaDTO> removeAreaRegion(List<AreaDTO> areaDTOs) {
        for ( int i = 0, size = areaDTOs.size(); i < size; i++ ){
            AreaDTO item =  areaDTOs.get(i);
            if (AreaTypeDTO.REGION.getValue() == item.getType()){
                areaDTOs.remove(item);
                i--;
                size--;
            }
        }
        return areaDTOs;
    }
}
