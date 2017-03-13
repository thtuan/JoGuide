package com.boot.accommodation.vp.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.StatisticsViewDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.vp.tour.FeedbackActivity;
import com.boot.accommodation.vp.tour.ListTourActivity;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticsActivity extends BaseActivity implements CalendarView.OnDateChangeListener, StatisticsMgr {
    public static final int ACTION_VIEW_FEEDBACK = 1004;//action for even click
    public static final int ACTION_VIEW_LISTTOUR = 1003;//  action for even click
    @Bind(R.id.cvCalendar)
    CalendarView cvCalendar;
    @Bind(R.id.rvStatistics)
    RecyclerView rvStatistics;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    private Calendar calendar;
    private DateFormat dateFormat;
    private StatisticsPresenterMgr statisticsPresenterMgr;
    private long currentSelectedDay; // luu ngay hien tai
    private StatisticsAdapter adapter; // adapter
    private String date;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        showProgress();
        initView();
        initData();
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        calendar = Calendar.getInstance();
        currentSelectedDay = calendar.getTimeInMillis();
        cvCalendar.setOnDateChangeListener(this);
        rvStatistics.setLayoutManager(new LinearLayoutManager(this));
        rvStatistics.setHasFixedSize(true);
    }

    /**
     * Init data
     */
    private void initData(){
        statisticsPresenterMgr = new StatisticsPresenter(this);
        statisticsPresenterMgr.getStatistics(DateUtil.formatDate(currentSelectedDay,DateUtil.FORMAT_DATE_TIME_ZONE));
    }


    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
//        String currentDateandTime = sdf.format(currentSelectedDay);
//        String selectDateandTime = sdf.format(view.getDate());
//        String calendarGet = sdf.format(calendar.getTimeInMillis());
//        String dateNow = sdf.format(Calendar.getInstance().getTime());
        if (view.getDate() > Calendar.getInstance().getTimeInMillis()) {
            showMessage("you can't select the day in future");
            view.setDate(currentSelectedDay);

        } else {
            if ((calendar.getTimeInMillis() - view.getDate()) > Constants.SCOPE_DAY_MILI) {
                showMessage("you just only select the day in 30 days");
                view.setDate(currentSelectedDay);

            } else {
                if (currentSelectedDay == view.getDate()) {
                } else {
                    currentSelectedDay = view.getDate();
                    statisticsPresenterMgr.getStatistics(DateUtil.formatDate(currentSelectedDay,DateUtil.FORMAT_DATE_TIME_ZONE));
                }

            }
        }
    }

    @Override
    public void renderLayout(StatisticsViewDTO statisticsViewDTO) {
        if (adapter == null) {
            adapter = new StatisticsAdapter(this, statisticsViewDTO);
            adapter.setListener(this);
            adapter.setMode(ExpandableRecyclerAdapter.MODE_NORMAL);
            adapter.expandAll();
            rvStatistics.setAdapter(adapter);
        }else{
            adapter.setData(statisticsViewDTO);
        }
        adapter.notifyDataSetChanged();
        closeProgress();
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        //get day and put to another class
        Bundle day = new Bundle();
        day.putString(Constants.IntentExtra.DATE_TIME, DateUtil.formatDate(currentSelectedDay,DateUtil.FORMAT_DATE_TIME_ZONE));
        switch (action) {
            case ACTION_VIEW_LISTTOUR:
                goNextScreen(ListTourActivity.class, day);
                break;
            case ACTION_VIEW_FEEDBACK:
                goNextScreen(FeedbackActivity.class, day);
                break;
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
    public void onLoadMore(int position) {

    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        finish();
    }


}
