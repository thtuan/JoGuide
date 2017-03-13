package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.TourPlacePictureResponseDTO;
import com.boot.accommodation.dto.view.PhotoDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Tour place picture presenter
 *
 * @author tuanlt
 * @since 10:44 CH 14/08/2016
 */
public class TourPlacePicturePresenter implements TourPlacePicturePresenterMgr {

    private TourPlacePictureViewMgr tourPlacePictureViewMgr;
    private TourModelMgr tourModelMgr;
    private int page = 0;// so trang load
    private int pageSize = 0; // tong trang
    private List<PhotoDTO> lstPhoto = new ArrayList<>(); // lst photo
    private long placeId; // place id
    private long tourId; // place id
    private String TAG = ""; // Tag

    public TourPlacePicturePresenter( TourPlacePictureViewMgr tourPlacePictureViewMgr ) {
        this.tourPlacePictureViewMgr = tourPlacePictureViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(tourPlacePictureViewMgr.getClass());
    }

    @Override
    public void getTourPlacePicture(long tourId, long placeId ) {
        this.placeId = placeId;
        this.tourId = tourId;
        lstPhoto.clear();
        page = 0;
        pageSize = 0;
        handleGetPicture(tourId, placeId);
    }

    /**
     * Handle get picture of tour place
     *
     * @param placeId
     */
    private void handleGetPicture(long tourId, long placeId ) {
        if (placeId > 0) {
            tourModelMgr.getTourPlacePicture(page, tourId, placeId, new
                ModelCallBack<TourPlacePictureResponseDTO>(TAG) {
                @Override
                public void onSuccess( TourPlacePictureResponseDTO response ) {
                    getTourPlacePictureSuccess(response);
                }

                @Override
                public void onError( int errorCode, String error ) {
                    tourPlacePictureViewMgr.showMessageErr(errorCode, error);
                }

                });
        } else {
            tourModelMgr.getTourPicture(page, tourId, new ModelCallBack<TourPlacePictureResponseDTO>(TAG) {
                @Override
                public void onSuccess( TourPlacePictureResponseDTO response ) {
                    getTourPlacePictureSuccess(response);
                }

                @Override
                public void onError( int errorCode, String error ) {
                    tourPlacePictureViewMgr.showMessageErr(errorCode, error);
                }

            });
        }
    }

    @Override
    public void getMoreTourPlacePicture( BaseRecyclerViewAdapter adapter ) {
        if (Utils.checkLoadMore(adapter, pageSize, lstPhoto.size())) {
            page++;
            handleGetPicture(tourId, placeId);
        }
    }

    /**
     * @param response
     */
    private void getTourPlacePictureSuccess( TourPlacePictureResponseDTO response ) {
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
        lstPhoto.addAll(response.getData().getPhotos());
        tourPlacePictureViewMgr.renderLayout(lstPhoto);
    }
}
