package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.SearchTourViewDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.DateUtil;

import butterknife.Bind;

/**
 * Search tour fragment
 *
 * @author Vuong-bv
 * @since: 6/10/2016
 */
public class TourSearchFragment extends BaseFragment implements TourSearchViewMgr {

    public static final int ACTION_VIEW_TOUR_DETAIL = 1000;//  action for even click
    TourFavouriteResultAdapter favouriteAdapter;// adapter for favourite tour
    TourNearbyResultAdapter nearByAdapter;// adapter for nearby tour
    TourSearchPresenterMgr tourSearchPresenterMgr;// presenter mgr

    @Bind(R.id.rvFavouriteTour)
    RecyclerView rvFavouriteTour;
    @Bind(R.id.rvNearbyTour)
    RecyclerView rvNearbyTour;
    private String keyWord; //Key word

    public static TourSearchFragment newInstance(Bundle data) {
        TourSearchFragment f = new TourSearchFragment();
        f.setArguments(data);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_search_tour;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            keyWord = args.getString(Constants.IntentExtra.KEYWORD);
        }
        initView();
        initData();
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        rvFavouriteTour.setHasFixedSize(true);
        rvNearbyTour.setHasFixedSize(true);
    }

    /**
     * Khoi tao data
     */
    private void initData() {
        tourSearchPresenterMgr = new TourSearchPresenter(this);
        getTourSearch(keyWord, false);
        showProgress();
    }

    @Override
    public void renderLayout(SearchTourViewDTO searchTourDto) {
        closeProgress();
        //set adapter for favourite result
        if (favouriteAdapter == null) {
            favouriteAdapter = new TourFavouriteResultAdapter(searchTourDto.getFavourite().getSearch());
            favouriteAdapter.setEnableLoadMore(true);
            favouriteAdapter.setHorizotalList(true);
            favouriteAdapter.setListener(new OnEventControl() {
                @Override
                public void onEventControl(int action, Object item, View View, int position) {
                    gotoTour((TripItemDTO) item);
                }

                @Override
                public void onLoadMore(int position) {
                    tourSearchPresenterMgr.getMoreFavouriteTour(favouriteAdapter, keyWord, DateUtil.formatNow
                            (DateUtil.FORMAT_DATE_TIME_ZONE));
                }
            });
            rvFavouriteTour.setAdapter(favouriteAdapter);
        } else {
            favouriteAdapter.setData(searchTourDto.getFavourite().getSearch());
        }
        favouriteAdapter.notifyDataSetChanged();
        //set adapter for neaby result
        if (nearByAdapter == null) {
            nearByAdapter = new TourNearbyResultAdapter(searchTourDto.getNearBy().getSearch());
            nearByAdapter.setListener(this);
            nearByAdapter.setEnableLoadMore(true);
            nearByAdapter.setListener(new OnEventControl() {
                @Override
                public void onEventControl(int action, Object item, View View, int position) {
                    gotoTour((TripItemDTO) item);
                }

                @Override
                public void onLoadMore(int position) {
                    tourSearchPresenterMgr.getMoreNearByTour(nearByAdapter, keyWord, DateUtil.formatNow(DateUtil
                            .FORMAT_DATE_TIME_ZONE));
                }
            });
            rvNearbyTour.setAdapter(nearByAdapter);
        } else {
            nearByAdapter.setData(searchTourDto.getNearBy().getSearch());
        }
        nearByAdapter.notifyDataSetChanged();
        stopRefresh();
    }

    @Override
    public void getResultSearchError(int errorCode, String error ) {
        closeProgress();
        stopRefresh();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case ACTION_VIEW_TOUR_DETAIL:
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.IntentExtra.TRIP_ITEM, (TripItemDTO) item);
                mActivity.goNextScreen(TourActivity.class, bundle);
                break;
        }
    }

    /**
     * Go to tour
     *
     * @param item
     */
    private void gotoTour(TripItemDTO item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentExtra.TRIP_ITEM, item);
        mActivity.goNextScreen(TourActivity.class, bundle);
    }

    /**
     * search when user input keyword
     * @param keyWord
     */
    public void getTourSearch(String keyWord, boolean isShowRefresh) {
        if (isShowRefresh) {
            showRefresh();
        }
        this.keyWord = keyWord;
        tourSearchPresenterMgr.getSearchTour(keyWord, DateUtil.formatNow
                (DateUtil.FORMAT_DATE_TIME_ZONE));
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                getTourSearch(keyWord,false);
                break;
        }

    }

}
