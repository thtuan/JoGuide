package com.boot.accommodation.vp.tour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TourInformationDTO;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.TripItemDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class TourInfoFragment extends BaseFragment implements TourInfomationViewMgr {


    public static final int ACTION_CALL_TOURIST = 1000;//  action for even click
    public static final int ACTION_SEND_MESSAGE = 1001;//  action for even click
    public static final int ACTION_VIEW_LEADER_INFO = 1002;//  action for even click
    public static final int ACTION_VIEW_TOURIST_INFO = 1003;//  action for even click
    public static final int ACTION_VIEW_TOURIST_POSITION = 1004;//  vi tri tourist
    public static final int ACTION_VIEW_TOUR_DETAIL = 1005;//View tour relate

    @Bind(R.id.rvTourInfo)
    RecyclerView rvTourInfo; //listview chua thong tin tour
    @Bind(R.id.rvTourRelate)
    RecyclerView rvTourRelate; //listview chua thong tin tour relate
    TourInfomationPresenterMgr tourInfomationPresenterMgr;
    TourInfoAdapter tourInfoAdapter; // adapter
    private long tourId; // tourId
    private long tourPlanId; // tourPlanId
    private boolean isJoin;//is member of tour
    TourRelateAdapter tourRelateAdapter;// adapter for relate tour

    public static TourInfoFragment newInstance(Bundle data) {
        TourInfoFragment f = new TourInfoFragment();
        f.setArguments(data);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            tourId = args.getLong(Constants.IntentExtra.TOUR_ID);
            tourPlanId = args.getLong(Constants.IntentExtra.TOUR_PLAN_ID);
            isJoin = args.getBoolean(Constants.IntentExtra.IS_JOIN);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_tour_info;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        initData();
        initView();
    }

    /**
     * Init data
     */
    private void initData() {
        tourInfomationPresenterMgr = new TourInfomationPresenter(this);
        tourInfomationPresenterMgr.getTourInfo(tourId, tourPlanId);
    }

    private void initView() {
        rvTourInfo.setLayoutManager(new LinearLayoutManager(mActivity));
//        rvTourInfo.setHasFixedSize(true);
        rvTourRelate.setHasFixedSize(true);
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case ACTION_CALL_TOURIST:
                callPhoneTourist(((TouristDTO) item).getPhone());
                break;
            case ACTION_SEND_MESSAGE:
                textMessage(((TouristDTO) item).getPhone());
                break;
            case ACTION_VIEW_LEADER_INFO: {
                Bundle bundle = putData((TouristDTO) item);
                mActivity.goNextScreen(ProfileActivity.class, bundle);
                break;
            }
            case ACTION_VIEW_TOURIST_INFO:
                Bundle bdle = putData((TouristDTO) item);
                mActivity.goNextScreen(ProfileActivity.class, bdle);
                break;
            case Constants.ActionEvent.ACTION_VIEW_TOUR_INFO:
                Bundle bd = new Bundle();
                bd.putParcelable(Constants.IntentExtra.TRIP_ITEM, (TripItemDTO) item);
                mActivity.goNextScreen(TourActivity.class, bd);
                break;
            case ACTION_VIEW_TOURIST_POSITION:
                Bundle bundle = new Bundle();
                ArrayList<TouristDTO> lstTourist = (ArrayList<TouristDTO>) item;
                bundle.putParcelableArrayList(Constants.IntentExtra.INTENT_DATA, lstTourist);
                mActivity.goNextScreen(TouristPositionActivity.class, bundle);
                break;
        }
    }


    /**
     * method callphone tourist
     *
     * @param dialNumber
     */
    public void callPhoneTourist(String dialNumber) {
        try {
            Intent callIntent = new Intent();
            callIntent.setAction(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            startActivity(callIntent);
        } catch (Exception e) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            startActivity(callIntent);
        }
    }


    /**
     * method send message
     *
     * @param dialNumber
     */
    public void textMessage(String dialNumber) {
        Uri uri = Uri.parse("smsto:" + dialNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("", "");
        startActivity(it);
    }

    /**
     * put data to another class
     *
     * @param dto
     * @return
     */
    public Bundle putData(TouristDTO dto) {
        Bundle data = new Bundle();
        data.putParcelable(Constants.IntentExtra.TOURIST_DTO, dto);
        data.putBoolean(Constants.IntentExtra.IS_JOIN, isJoin);
        return data;
    }

    @Override
    public void renderLayout(TourInformationDTO tourInformationDTO) {
//        int position = tourInformationDTO.getTourGuideDto().size()+
//                tourInformationDTO.getTourInfoDto().size()+2;
        closeProgress();
        if (tourInfoAdapter == null) {
            tourInfoAdapter = new TourInfoAdapter(mActivity, tourInformationDTO);
            tourInfoAdapter.setListener(this);
            tourInfoAdapter.setMode(ExpandableRecyclerAdapter.MODE_NORMAL);
            tourInfoAdapter.expandAll();
            tourInfoAdapter.collapseItems(tourInfoAdapter.getPositionCollapse(), false);
            rvTourInfo.setAdapter(tourInfoAdapter);
        }
        tourInfoAdapter.notifyDataSetChanged();
        stopRefresh();
        //Update control function(feedback, share ...)
        if(mActivity instanceof TourActivity){
            ((TourActivity)mActivity).updateFunction(tourInformationDTO.isTourCompany());
        }
        tourInfomationPresenterMgr.getTourRelate(tourId);
    }

    @Override
    public void renderTourRelate(List<TripItemDTO> tripItemDTO) {
        closeProgress();
        //set adapter for tour relate result
        if (tourRelateAdapter == null) {
            tourRelateAdapter = new TourRelateAdapter(tripItemDTO);
            tourRelateAdapter.setListener(this);
            tourRelateAdapter.setEnableLoadMore(true);
            tourRelateAdapter.setHorizotalList(true);
            rvTourRelate.setAdapter(tourRelateAdapter);
        } else {
            tourRelateAdapter.setData(tripItemDTO);
        }
        tourRelateAdapter.notifyDataSetChanged();
        stopRefresh();
    }

    @Override
    public void onLoadMore(int position) {
        tourInfomationPresenterMgr.getMoreTourRelate(tourId, tourRelateAdapter);
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                tourInfomationPresenterMgr.getTourInfo(tourId, tourPlanId);
                break;
        }
    }
}
