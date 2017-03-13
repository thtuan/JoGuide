package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.TourNotificationResponseDTO;
import com.boot.accommodation.dto.view.TourNotificationDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * tour notification presenter
 *
 * @author thtuan
 * @since 2:38 PM 17-05-2016
 */
public class TourNotificationPresenter implements TourNotificationPresenterMgr {
    private TourModelMgr tourModelMgr;
    private TourNotificationMgr tourNotificationMgr;
    private int page; // so trang
    private int pageSize; // tong trang
    private final static int USER = 1;
    private final static int PLAN = 0;
    private List<TourNotificationDTO>  lstNotification = new ArrayList<>();
    private String TAG = ""; // Tag

    /**
     * khoi tao gia tri
     * @param tourNotificationMgr
     */
    public TourNotificationPresenter(TourNotificationMgr tourNotificationMgr) {
        this.tourNotificationMgr = tourNotificationMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(tourNotificationMgr.getClass());
    }


    /**
     * @param id get notification theo tour plan id
     */
    private void getPlanNotification( String id) {
        tourModelMgr.getPlanNotification(page, id, new ModelCallBack<TourNotificationResponseDTO>(TAG) {
            @Override
            public void onSuccess(TourNotificationResponseDTO response) {
                getNotificationSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourNotificationMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * @param id get notification theo tour plan id
     */
    private void getUserNotification(String id) {

        tourModelMgr.getUserNotification(page, new ModelCallBack<TourNotificationResponseDTO>(TAG) {
            @Override
            public void onSuccess(TourNotificationResponseDTO response) {
                getNotificationSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourNotificationMgr.showMessageErr(errorCode, error);
            }

        });
    }


    private void loadMorePlanNotification(String id) {
        tourModelMgr.getPlanNotification(page, id, new ModelCallBack<TourNotificationResponseDTO>(TAG) {
            @Override
            public void onSuccess(TourNotificationResponseDTO response) {
                getNotificationSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourNotificationMgr.showMessageErr(errorCode, error);
            }

        });
    }

    private void loadMoreUserNotification(String id) {
        tourModelMgr.getUserNotification(page, new ModelCallBack<TourNotificationResponseDTO>(TAG) {
            @Override
            public void onSuccess(TourNotificationResponseDTO response) {
                getNotificationSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                tourNotificationMgr.showMessageErr(errorCode, error);
            }

        });
    }


    @Override
    public void handleGetNotification(int userPlan, String id) {
        page = 0;
        lstNotification.clear();
        switch (userPlan){
            case PLAN:
                getPlanNotification(id);
                break;
            case USER:
                getUserNotification(id);
                break;
        }
    }

    @Override
    public void handleLoadMoreNotification(TourNotificationAdapter adapter, int userPlan, String id) {
       if  (Utils.checkLoadMore(adapter, pageSize, lstNotification.size())){
           page++;
           switch (userPlan){
               case PLAN:
                   loadMorePlanNotification(id);
                   break;
               case USER:
                   loadMoreUserNotification(id);
                   break;
           }
        }
    }

    @Override
    public void requestSeen(final List<Long> lstId) {

        tourModelMgr.requestSeen(lstId, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {

            }

            @Override
            public void onError(int errorCode, String error) {
                tourNotificationMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * response success
     *
     * @param response
     */
    private void getNotificationSuccess(TourNotificationResponseDTO response) {
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
        lstNotification.addAll(response.getData().getNotification());
        tourNotificationMgr.renderLayout(lstNotification);
        tourNotificationMgr.requestSeen(response.getData().getNotification());
    }
}
