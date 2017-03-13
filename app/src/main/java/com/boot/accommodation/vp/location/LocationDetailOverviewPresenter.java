package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.PlaceDetailResponseDTO;
import com.boot.accommodation.dto.response.TourRelateResponseDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.LocationModel;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.LocationModelMgr;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Mo ta class
 *
 * @author Dungnx
 */
public class LocationDetailOverviewPresenter implements LocationDetailOverviewPresenterMgr {
    LocationDetailOverviewMgr placeDetailViewMgr;
    LocationModelMgr locationModelMgr;
    private int page = 0;// so trang load
    private int pageSize = 0; //tong trang
    List<TripItemDTO> listTripItemDTO = new ArrayList<>();
    private TourModelMgr tourModelMgr;
    private String TAG = ""; // Tag

    public LocationDetailOverviewPresenter(LocationDetailOverviewMgr placeDetailViewMgr) {
        this.placeDetailViewMgr = placeDetailViewMgr;
        locationModelMgr = new LocationModel();
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(placeDetailViewMgr.getClass());
    }

    @Override
    public void getOverviewInfo(PlaceItemDTO dto) {
        locationModelMgr.getPlaceDetail(dto.getId(), new ModelCallBack<PlaceDetailResponseDTO>(TAG) {
            @Override
            public void onSuccess(PlaceDetailResponseDTO response) {
                placeDetailViewMgr.renderLayout(response.getData());
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailViewMgr.showMessageErr(errorCode, error);
            }

        });
    }


    @Override
    public void getTourRelate(long locationId) {
        page = 0;
        pageSize = 0;
        listTripItemDTO.clear();
        handleGetTourRelate(locationId);
    }

    @Override
    public void getMoreTourRelate(long locationId, BaseRecyclerViewAdapter adapter) {
        if (Utils.checkLoadMore(adapter, pageSize, listTripItemDTO.size())) {
            page++;
            handleGetTourRelate(locationId);
        }
    }

    /**
     * Handle get tour relate
     *
     * @param locationId
     */
    private void handleGetTourRelate(long locationId) {
        tourModelMgr.getTourRelate(0, locationId, page, new ModelCallBack<TourRelateResponseDTO>(TAG) {

            @Override
            public void onSuccess(TourRelateResponseDTO response) {
                getTourRelateSuccess(response);
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailViewMgr.showMessageErr(errorCode, error);
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
        placeDetailViewMgr.renderTourRelate(listTripItemDTO);
    }
}
