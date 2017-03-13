package com.boot.accommodation.vp.tour;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.SearchTourResponseDTO;
import com.boot.accommodation.dto.view.SearchTourViewDTO;
import com.boot.accommodation.dto.view.SearchTypeDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.TourModel;
import com.boot.accommodation.model.mgr.TourModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * Presenter  cho man hinh tour info expand
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public class TourSearchPresenter implements TourSearchPresenterMgr {

    private TourSearchViewMgr tourSearchViewMgr; // place Search
    private TourModelMgr tourModelMgr; // model
    private int pageFav = 0; // page favourite
    private int pageNearBy = 0; // page gan nhat
    private int pageSizeFav = 0; //  size fav
    private int pageSizeNearBy = 0; // size near by
    private SearchTourViewDTO searchTourViewDTO = new SearchTourViewDTO(); // dto search
    private String TAG = ""; // Tag

    public TourSearchPresenter( TourSearchViewMgr tourSearchViewMgr ) {
        this.tourSearchViewMgr = tourSearchViewMgr;
        tourModelMgr = new TourModel();
        TAG = Utils.getTag(tourSearchViewMgr.getClass());
    }


    @Override
    public void getSearchTour( String keyWord, String date ) {
        pageFav = 0;
        pageNearBy = 0;
        pageSizeFav = 0;
        pageSizeNearBy = 0;
        // type = 0: tuc la lay du lieu search lan dau
        tourModelMgr.getSearchTour(SearchTypeDTO.ALL.getValue(), 0, keyWord, date, new
            ModelCallBack<SearchTourResponseDTO>(TAG) {
                @Override
                public void onSuccess( SearchTourResponseDTO response ) {
                    searchTourViewDTO = response.getData();
                    pageFav = response.getData().getFavourite().getPaging().getPage();
                    pageSizeFav = response.getData().getFavourite().getPaging().getPageSize();
                    pageNearBy = response.getData().getNearBy().getPaging().getPage();
                    pageSizeNearBy = response.getData().getNearBy().getPaging().getPageSize();
                    tourSearchViewMgr.renderLayout(searchTourViewDTO);
                }

                @Override
                public void onError( int errorCode, String error ) {
                    tourSearchViewMgr.getResultSearchError(errorCode, error);
                }

            });
    }

    @Override
    public void getMoreFavouriteTour( BaseRecyclerViewAdapter adapter, String keyWord, String date ) {
        if (Utils.checkLoadMore(adapter, pageSizeFav, searchTourViewDTO.getFavourite().getSearch().size())) {
            pageFav++;
            tourModelMgr.getSearchTour(SearchTypeDTO.FAVOURITE.getValue(), pageFav, keyWord, date, new
                ModelCallBack<SearchTourResponseDTO>(TAG) {
                    @Override
                    public void onSuccess( SearchTourResponseDTO response ) {
                        pageFav = response.getData().getFavourite().getPaging().getPage();
                        pageSizeFav = response.getData().getFavourite().getPaging().getPageSize();
                        searchTourViewDTO.getFavourite().getSearch().addAll(response.getData().getFavourite()
                            .getSearch());
                        tourSearchViewMgr.renderLayout(searchTourViewDTO);
                    }

                    @Override
                    public void onError( int errorCode, String error ) {
                        tourSearchViewMgr.getResultSearchError(errorCode, error);
                    }

                });
        }
    }

    @Override
    public void getMoreNearByTour( BaseRecyclerViewAdapter adapter, String keyWord, String date ) {
        if (Utils.checkLoadMore(adapter, pageSizeNearBy, searchTourViewDTO.getNearBy().getSearch().size())) {
            pageNearBy++;
            tourModelMgr.getSearchTour(SearchTypeDTO.NEARBY.getValue(), pageNearBy, keyWord, date, new
                ModelCallBack<SearchTourResponseDTO>(TAG) {
                    @Override
                    public void onSuccess( SearchTourResponseDTO response ) {
                        pageNearBy = response.getData().getNearBy().getPaging().getPage();
                        pageSizeNearBy = response.getData().getNearBy().getPaging().getPageSize();
                        searchTourViewDTO.getNearBy().getSearch().addAll(response.getData().getNearBy()
                            .getSearch());
                        tourSearchViewMgr.renderLayout(searchTourViewDTO);
                    }

                    @Override
                    public void onError( int errorCode, String error ) {
                        tourSearchViewMgr.getResultSearchError(errorCode, error);
                    }

                });
        }
    }

}
