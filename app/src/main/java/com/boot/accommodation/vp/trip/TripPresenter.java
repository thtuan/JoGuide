package com.boot.accommodation.vp.trip;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.TripResponseDTO;
import com.boot.accommodation.dto.view.ItemTypeDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.model.mgr.HomeModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;

/**
 * Presenter cho man hinh ds tour
 *
 * @author tuanlt
 * @since 9:53 AM 5/5/2016
 */
public class TripPresenter implements TripPresenterMgr {
    private ArrayList<TripItemDTO> lstTrips = new ArrayList<>();
    private TripViewMgr tripViewMgr;
    private HomeModelMgr tripModelMgr;
    private int page = 0;// so trang load
    private int pageSize = 0; // tong trang
    private String userId; // user id
    private String search; // search text
    private int typeTour = TripFragment.ACTION_GET_TOUR_DEFAULT; // tour default, tour favourite, tour
    private String TAG = ""; // Tag

    public TripPresenter(TripViewMgr tripViewMgr) {
        this.tripViewMgr = tripViewMgr;
        this.tripModelMgr = new HomeModel();
        TAG = Utils.getTag(tripViewMgr.getClass());
    }


    /**
     * handle get tour
     * @param typeTour tour tyoe all, my tour , favorite
     * @param userId user id
     * @param search search text
     */
    @Override
    public void getTours(int typeTour, String userId, String search) {
        // refresh lai tour
        this.userId = userId;
        this.search = search;
        this.typeTour = typeTour;
        // lay lai tu dau
        handleGetTours(userId, search);
    }

    /**
     * Handle get tours
     */
    private void handleGetTours(String userId, String search) {
        page = 0;
        lstTrips.clear();

        if (typeTour == TripFragment.ACTION_GET_TOUR_DEFAULT) {
            getAllTour();
        } else if (typeTour == TripFragment.ACTION_GET_MY_TOUR_TRIP) {
            getMyTour(userId, search);
        } else if (typeTour == TripFragment.ACTION_GET_TOUR_FAVOURITE){
            getFavouriteTour(userId, search);
        } else if (typeTour == TripFragment.ACTION_GET_COLLECTION){
            getCollection(userId,search);
        }

    }


    @Override
    public void getMoreTours(BaseRecyclerViewAdapter adapter) {
        // Xu li load more nua hay ko
        if (Utils.checkLoadMore(adapter, pageSize, lstTrips.size())) {
            page++;
            if (typeTour == TripFragment.ACTION_GET_TOUR_DEFAULT) {
                getAllTour();
            } else if (typeTour == TripFragment.ACTION_GET_MY_TOUR_TRIP) {
                getMyTour(userId, search);
            } else if (typeTour == TripFragment.ACTION_GET_TOUR_FAVOURITE){
                getFavouriteTour(userId, search);
            } else if (typeTour == TripFragment.ACTION_GET_COLLECTION){
                getCollection(userId,search);
            }
        }
    }

    @Override
    public void requestFavouriteTour(final TripItemDTO item, final long itemId ) {
        tripModelMgr.requestFavouriteTour(item.getTourPlanId() > 0 ? ItemTypeDTO.TOUR_PLAN.getValue() : ItemTypeDTO
                .TOUR.getValue(), item.getTourPlanId() > 0 ? item.getTourPlanId() : itemId, new
            ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                item.setIsFavourite((Boolean) response.getData());
                tripViewMgr.renderLayout(lstTrips);
            }

            @Override
            public void onError(int errorCode, String error) {

            }

            });
    }

    /**
     * Xu li lay tour thanh cong
     */
    private void getToursSuccess( TripResponseDTO response ) {
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
        lstTrips.addAll(response.getData().getTours());
        tripViewMgr.setNotification(response.getData().getCountNotification());
        tripViewMgr.renderLayout(lstTrips);
    }

    /**
     * Get tour default
     */
    private void getAllTour() {
        tripModelMgr.getAllTour(page, new ModelCallBack<TripResponseDTO>(TAG) {
            @Override
            public void onSuccess(TripResponseDTO response) {
                getToursSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tripViewMgr.getToursError(errorCode , error);
            }

        });
    }

    /**
     * get favorite tour
     *
     * @param userId user id
     * @param search search text
     */
    @Override
    public void getFavouriteTour(String userId, String search) {
        // error when pageSize = 5
        tripModelMgr.getFavouriteTour(userId, search, page, new ModelCallBack<TripResponseDTO>(TAG) {
            @Override
            public void onSuccess(TripResponseDTO response) {
                getToursSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tripViewMgr.getToursError(errorCode, error);
            }

        });
    }

    /**
     * get my tour
     *
     * @param userId user id
     * @param search search text
     */
    @Override
    public void getMyTour( String userId, String search) {
        // error when pageSize = 5
        tripModelMgr.getMyTour(userId, search, page, new ModelCallBack<TripResponseDTO>(TAG) {
            @Override
            public void onSuccess(TripResponseDTO response) {
                getToursSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tripViewMgr.getToursError(errorCode , error);
            }

        });
    }

    /**
     * getCollection
     *
     * @param adminId
     * @param search
     */
    @Override
    public void getCollection(String adminId, String search) {
        // error when pageSize = 5
        tripModelMgr.getCollection(adminId, search, page,  new ModelCallBack<TripResponseDTO>(TAG) {
            @Override
            public void onSuccess(TripResponseDTO response) {
                getToursSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tripViewMgr.getToursError(errorCode , error);
            }

        });
    }
}
