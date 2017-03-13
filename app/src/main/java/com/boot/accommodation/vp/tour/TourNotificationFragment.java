package com.boot.accommodation.vp.tour;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TourNotificationDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.RemindReceiver;
import com.boot.accommodation.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

public class TourNotificationFragment extends BaseFragment implements TourNotificationMgr {

    @Bind(R.id.rvListNotification)
    RecyclerView rvListNotification;
    private final static int USER = 1;
    private final static int PLAN = 0;
    public static final int CLICK_NOTIFICATION = 3;
    private TourNotificationAdapter mTourNotificationAdapter; // adapter tour notification
    private TourNotificationPresenterMgr tourNotificationPresenterMgr; //interface tournotificationPresenter
    private List<TourNotificationDTO> tourNotificationDTOs = new ArrayList<>(); // array list notification
    private Long id; // id item
    private int userPlan;// quyet dinh get notification theo user hay tour
    List<Long> lst = new ArrayList<>();
    public static TourNotificationFragment newInstance(Bundle bundle) {
        TourNotificationFragment z = new TourNotificationFragment();
        z.setArguments(bundle);
        return z;
    }

    /**
     * set view layout
     *
     * @return
     */
    @Override
    public int contentViewLayout() {
        return R.layout.fragment_notification;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        initData();
        initView();
    }

    /**
     * render layout
     *
     * @param tourNotificationDTOs goi du lieu render
     */
    @Override
    public void renderLayout(List<TourNotificationDTO> tourNotificationDTOs) {
        if (mTourNotificationAdapter == null) {
            mTourNotificationAdapter = new TourNotificationAdapter(tourNotificationDTOs, userPlan);
            mTourNotificationAdapter.setEnableLoadMore(true);
            mTourNotificationAdapter.setListener(this);
            rvListNotification.setAdapter(mTourNotificationAdapter);
        } else {
            mTourNotificationAdapter.setData(tourNotificationDTOs);
        }
        mTourNotificationAdapter.notifyDataSetChanged();
        setRemind(tourNotificationDTOs);
        closeProgress();
        stopRefresh();
    }
    @Override
    public void requestSeen(List<TourNotificationDTO> tourNotificationDTOs) {
        lst.clear();
        for (TourNotificationDTO dto : tourNotificationDTOs){
            if (dto.getIsNewNotification()){
                lst.add(dto.getId());
            }
        }
        if (!lst.isEmpty()){
            tourNotificationPresenterMgr.requestSeen(lst);
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

    private void setRemind(List<TourNotificationDTO> tourNotificationDTOs) {
        int id = 1;
        for (TourNotificationDTO tourNotificationDTO : tourNotificationDTOs) {
            if (tourNotificationDTO.getNotifyType() == TourNotificationDTO.REMIND && !StringUtil.isNullOrEmpty
                    (tourNotificationDTO.getRemindTime())) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT_DATE_WITHOUT_TIME_ZONE);
                    Date date = dateFormat.parse(tourNotificationDTO.getRemindTime());
                    Calendar calendar = Calendar.getInstance();
                    if (date.getTime() > calendar.getTimeInMillis()) {
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent alarmReceiver = new Intent(getContext(), RemindReceiver.class);
                        alarmReceiver.putExtra(Constants.ALARM_BUNDLE, tourNotificationDTO);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), PendingIntent.getBroadcast(getContext(), id, alarmReceiver, PendingIntent.FLAG_UPDATE_CURRENT));
                        id++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * khoi tao view
     */
    private void initView() {
        rvListNotification.setLayoutManager(new LinearLayoutManager(mActivity));
        rvListNotification.setHasFixedSize(true);
        userPlan = getArguments().getInt(Constants.IntentExtra.FROM_NOTIFICATION_MENU);
        if (userPlan == PLAN) {
            id = getArguments().getLong(Constants.IntentExtra.TOUR_PLAN_ID);
        }
        tourNotificationPresenterMgr.handleGetNotification(userPlan, String.valueOf(id));
    }

    /**
     * khoi tao data
     */
    private void initData() {
        tourNotificationPresenterMgr = new TourNotificationPresenter(this);
    }

    /**
     * nhan broadcast chi con su dung alarm
     *
     * @param action action nhan broadcast
     * @param extras bundle
     */
    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                tourNotificationPresenterMgr.handleGetNotification(userPlan, String.valueOf(id));
                break;
            case Constants.ActionEvent.RESULT_OK:
                tourNotificationPresenterMgr.handleGetNotification(userPlan, String.valueOf(id));
                break;
        }
    }

    /**
     * load them view
     *
     * @param position vi tri page lay them
     */
    @Override
    public void onLoadMore(int position) {
        tourNotificationPresenterMgr.handleLoadMoreNotification(mTourNotificationAdapter, userPlan, String.valueOf(id));
    }


}
