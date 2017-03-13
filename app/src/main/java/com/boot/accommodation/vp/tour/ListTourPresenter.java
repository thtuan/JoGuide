package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.ListTourResponseDTO;
import com.boot.accommodation.dto.view.ListTourDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter  cho man hinh list tour
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public class ListTourPresenter implements ListTourPresenterMgr {

    private ListTourViewMgr listTourViewMgr; // view listTour
    private TourModelMgr tourModelMgr;//model mgr of list tour
    private int page = 0;// so trang load
    private int pageSize = 0; // tong trang
    List<ListTourDTO> listTourDTO = new ArrayList<>();//list tour
    private String TAG = ""; // Tag

    public ListTourPresenter(ListTourViewMgr listTourViewMgr) {
        this.listTourViewMgr = listTourViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(listTourViewMgr.getClass());
    }

    @Override
    public void getListTour( String date) {
        listTourDTO.clear();
        tourModelMgr.getListTour( date, page, new ModelCallBack<ListTourResponseDTO>(TAG) {
            @Override
            public void onSuccess(ListTourResponseDTO response) {
                getProfileSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                listTourViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getMoreTour( String date, BaseRecyclerViewAdapter adapter) {
        if (Utils.checkLoadMore(adapter, pageSize, listTourDTO.size())) {
            page++;
            tourModelMgr.getListTour( date, page, new ModelCallBack<ListTourResponseDTO>(TAG) {
                @Override
                public void onSuccess(ListTourResponseDTO response) {
                    getProfileSuccess(response);
                }

                @Override
                public void onError( int errorCode, String error ) {
                    listTourViewMgr.showMessageErr(errorCode, error);
                }

            });
        }
    }

    /**
     * get list tour success
     *
     * @param response
     */
    private void getProfileSuccess(ListTourResponseDTO response) {
        page = response.getData().getPage().getPage();
        pageSize = response.getData().getPage().getPageSize();
        listTourDTO.addAll(response.getData().getListTourDTO());
        listTourViewMgr.renderLayout(listTourDTO);
    }
}
