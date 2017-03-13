package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TourItineraryItemDTO;
import com.boot.accommodation.dto.view.TripItemDTO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class TourItineraryFragment extends BaseFragment implements TourItineraryViewMgr {

    @Bind(R.id.rvTourItinerary)
    RecyclerView rvTourItinerary; // recycle view du lieu
    TourItineraryAdapter tourItineraryAdapter; // adapter du lieu
    @Bind(R.id.ivMap)
    ImageView ivMap; // click vao map
    private TourItineraryPresenterMgr tourItineraryPresenterMgr; // presenter
    private TripItemDTO tripItemDTO; // dto cua mot trip
    private String photoTrip = ""; // photo chon cua tour
    public static final int ACTION_CLICK_TOUR_ITINERARY = 1000; // xu li click
    ArrayList<TourItineraryItemDTO> tourItinerary = new ArrayList<>(); // ds theo ngay cac dia diem di cua tour
    public static TourItineraryFragment newInstance( Bundle bundle ) {
        TourItineraryFragment t = new TourItineraryFragment();
        t.setArguments(bundle);
        return t;
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            tripItemDTO = args.getParcelable(Constants.IntentExtra.TRIP_ITEM);
            photoTrip = args.getString(Constants.IntentExtra.TRIP_PHOTO);
        }
    }


    @Override
    public int contentViewLayout() {
        return R.layout.fragment_tour_itinerary;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        initViews();
        initData();
        loadData();
    }

    /**
     * Khoi tao view
     */
    private void initViews() {
        rvTourItinerary.setLayoutManager(new LinearLayoutManager(mActivity));
        rvTourItinerary.setHasFixedSize(true);
    }

    /**
     * Khoi tao data
     */
    private void initData() {
        tourItineraryPresenterMgr = new TourItineraryPresenter(this);
    }

    /**
     * Load data
     */
    private void loadData() {
        tourItineraryPresenterMgr.getTourItenirary(tripItemDTO.getTourId());
    }

    @Override
    public void renderLayout( ArrayList<TourItineraryItemDTO> tourItinerary ) {
        this.tourItinerary = tourItinerary;
        if (tourItineraryAdapter == null) {
            tourItineraryAdapter = new TourItineraryAdapter(mActivity, this);
            rvTourItinerary.setAdapter(tourItineraryAdapter);
            // set mode
            tourItineraryAdapter.setData(tripItemDTO.getStartDate(),tourItinerary);
            tourItineraryAdapter.setMode(ExpandableRecyclerAdapter.MODE_NORMAL);
        } else {
            tourItineraryAdapter.setData(tripItemDTO.getStartDate(), tourItinerary);
        }
        // expand all view
        tourItineraryAdapter.expandAll();
        tourItineraryAdapter.notifyDataSetChanged();
        checkShowMap(tourItinerary);
        closeProgress();
        stopRefresh();
    }

    /**
     * Check show map
     * @param tourItinerary
     */
    private void checkShowMap(ArrayList<TourItineraryItemDTO> tourItinerary) {
        if (tourItinerary == null || tourItinerary.size() == 0){
            ivMap.setVisibility(View.GONE);
        } else {
            ivMap.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    @Override
    public void onEventControl( int action, Object item, View View, int position ) {
        switch (action) {
            case ACTION_CLICK_TOUR_ITINERARY:
                TourItineraryItemDTO dto = (TourItineraryItemDTO) item;
                Bundle bundle = new Bundle();
                bundle.putString(Constants.IntentExtra.TOUR_NAME, tripItemDTO.getName());
                bundle.putLong(Constants.IntentExtra.TOUR_ID, tripItemDTO.getTourId());
                /*bbundle.putParcelable(Constants.IntentExtra.TRIP_ITEM, tripItemDTO);
                bundle.putString(Constants.IntentExtra.TRIP_PHOTO, photoTrip);*/
                bundle.putParcelable(Constants.IntentExtra.TOUR_ITINERARY, dto);
                mActivity.goNextScreen(TourPlaceActivity.class, bundle);
                break;
        }

    }

    @Override
    protected void receiveBroadcast( int action, Bundle extras ) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                tourItineraryPresenterMgr.getTourItenirary(tripItemDTO.getTourId());
                break;
        }
    }


    @OnClick(R.id.ivMap)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivMap:
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants.IntentExtra.INTENT_DATA, tourItinerary);
                mActivity.goNextScreen(TourPlacePositionActivity.class, bundle);
                break;
        }
    }
}
