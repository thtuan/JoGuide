package com.boot.accommodation.vp.tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.ListTourDTO;
import com.boot.accommodation.vp.category.QualityAssessmentActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Profile fragment
 *
 * @author Vuong-bv
 * @since: 6/4/2016
 */
public class ListTourActivity extends BaseActivity implements ListTourViewMgr{

    public static final int ACTION_GO_TO_DETAIL = 1000; // chon fil
    @Bind(R.id.rvListTour)
    RecyclerView rvListTour;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    private ListTourAdapter listTourAdapter; // listtour adapter
    private ListTourPresenterMgr listTourPresenterMgr;
    private String date;//date get from another class
    private long tourPlanId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tour);
        ButterKnife.bind(this);
        showProgress();
        Intent intent = getIntent();
        if (intent != null) {
            date = intent.getStringExtra(Constants.IntentExtra.DATE_TIME);
        }
        initView();
        innitData();
    }


    /**
     * Khoi tao control
     */
    private void initView() {
        rvListTour.setLayoutManager(new LinearLayoutManager(this));
        rvListTour.setHasFixedSize(true);

    }

    /**
     * innit data for class
     */
    private void innitData(){
        listTourPresenterMgr = new ListTourPresenter(this);
        listTourPresenterMgr.getListTour(date);
    }

    /**
     * redner data checked in
     */
    @Override
    public void renderLayout(List<ListTourDTO> listTourDTO) {
        if (listTourAdapter == null) {
            listTourAdapter = new ListTourAdapter(listTourDTO);
            listTourAdapter.setListener(this);
            listTourAdapter.setEnableLoadMore(true);
            rvListTour.setAdapter(listTourAdapter);
        } else {
            listTourAdapter.setData(listTourDTO);
        }
        listTourAdapter.notifyDataSetChanged();
        closeProgress();
    }

    @OnClick({R.id.ivBack})
    public void onClick(View v) {
        int action = 0;
        switch (v.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case ACTION_GO_TO_DETAIL:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.IntentExtra.DATE_TIME, date);
                bundle.putLong(Constants.IntentExtra.TOUR_ID, ((ListTourDTO) item).getTourId());
                bundle.putLong(Constants.IntentExtra.TOUR_PLAN_ID, ((ListTourDTO) item).getTourPlanId());
                goNextScreen(QualityAssessmentActivity.class, bundle);
                break;
        }
    }

    @Override
    public void onLoadMore(int position) {
        listTourPresenterMgr.getMoreTour( date, listTourAdapter);
    }

    @Override
    public void receiveBroadcast(int action, Bundle bundle) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                listTourPresenterMgr.getListTour( date);
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
}
