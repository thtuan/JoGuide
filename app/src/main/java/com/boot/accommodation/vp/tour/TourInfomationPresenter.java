package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.TourInfomationResponseDTO;
import com.boot.accommodation.dto.response.TourRelateResponseDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter  cho man hinh tour info expand
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public class TourInfomationPresenter implements TourInfomationPresenterMgr {
    private String TAG = ""; // Tag
    private TourInfomationViewMgr tourInfomationViewMgr; // tour info view
    private TourModelMgr tourModelMgr;
    private int page = 0;// so trang load
    private int pageSize = 0; //tong trang
    List<TripItemDTO> listTripItemDTO = new ArrayList<>();

    public TourInfomationPresenter(TourInfomationViewMgr tourInfoViewMgr) {
        this.tourInfomationViewMgr = tourInfoViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(tourInfomationViewMgr.getClass());
    }

    @Override
    public void getTourInfo(long tourId, long tourPlanId) {
        tourModelMgr.getTourInfomation(tourId, tourPlanId, new ModelCallBack<TourInfomationResponseDTO>(TAG) {
            @Override
            public void onSuccess(TourInfomationResponseDTO response) {
                tourInfomationViewMgr.renderLayout(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                tourInfomationViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getTourRelate(long tourId) {
        page = 0;
        pageSize = 0;
        listTripItemDTO.clear();
        handleGetTourRelate(tourId);
    }

    @Override
    public void getMoreTourRelate(long tourId, BaseRecyclerViewAdapter adapter) {
        if (Utils.checkLoadMore(adapter, pageSize, listTripItemDTO.size())) {
            page++;
            handleGetTourRelate(tourId);
        }
    }

    /**
     * Handle get tour relate
     * @param tourId
     */
    private void handleGetTourRelate(long tourId) {
        tourModelMgr.getTourRelate(tourId, 0, page, new ModelCallBack<TourRelateResponseDTO>(TAG) {

            @Override
            public void onSuccess( TourRelateResponseDTO response ) {
                getTourRelateSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourInfomationViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * Xu li lay tour relate info thanh cong
     */
    private void getTourRelateSuccess(TourRelateResponseDTO response) {
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
        listTripItemDTO.addAll(response.getData().getTourRelate());
        tourInfomationViewMgr.renderTourRelate(listTripItemDTO);
    }
}
