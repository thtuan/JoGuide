package com.boot.accommodation.vp.category;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.QualityAssessmentViewDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.tour.FeedbackActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Chat luong tour
 *
 * @author tuanlt
 * @since 2:31 PM 6/17/2016
 */
public class QualityAssessmentActivity extends BaseActivity implements QualityAssessmentViewMgr {

    @Bind(R.id.pieChart)
    PieChart pieChart;
    @Bind(R.id.rvQuality)
    RecyclerView rvQuality;
    @Bind(R.id.tvNumFeedBack)
    TextView tvNumFeedBack;
    @Bind(R.id.llGotoFeedback)
    LinearLayout llGotoFeedback;
    // Du lieu render
    protected String[] title = new String[]{StringUtil.getString(R.string.text_excellent), StringUtil.getString(R
        .string.text_good), StringUtil.getString(R.string.text_fair), StringUtil.getString(R.string.text_bad)
    };
    QualityAssessmentAdapter qualityAssessmentAdapter; // adapter render du lieu
    QualityAssessmentViewDTO qualityAssessmentViewDTO = new QualityAssessmentViewDTO(); // dto cho view
    QualityAssessmentPresenterMgr qualityAssessmentPresenterMgr; // presneter cho view
    private long tourId; // tourId
    private long tourPlanId; // tour plan id
    private String date;//date
    private int numFeedback;//number of feedback
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        showProgress();
        enableSlidingMenu(false);
        setContentView(R.layout.activity_quality_assessment);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * Khoi tao du lieu
     */
    private void initData(){
        Intent intent = getIntent();
        if (intent != null) {
            tourId = intent.getLongExtra(Constants.IntentExtra.TOUR_ID,0);
            tourPlanId = intent.getLongExtra(Constants.IntentExtra.TOUR_PLAN_ID,0);
            date = intent.getStringExtra(Constants.IntentExtra.DATE_TIME);
        }
        qualityAssessmentPresenterMgr = new QualityAssessmentPresenter(this);
        qualityAssessmentPresenterMgr.getQualityAssessment(tourId, tourPlanId, date);
    }
    /**
     * Khoi tao view
     */
    private void initView() {
        rvQuality.setLayoutManager(new LinearLayoutManager(this));
        rvQuality.setHasFixedSize(true);
    }

    /**
     * render Layout Chart
     */
    private void renderLayoutChart() {
        this.closeProgress();
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawHoleEnabled(false);
        setData(title.length, 100);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // pieChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setEnabled(false);
    }

    /**
     * Set data chart
     *
     * @param count
     * @param range
     */
    private void setData(int count, float range) {
        float mult = range;
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//        for (int i = 0; i < count; i++) {
//            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
//        }
        double total = qualityAssessmentViewDTO.getTotalQualityAssessment().getExcellent() +
                qualityAssessmentViewDTO.getTotalQualityAssessment().getGood() +
                qualityAssessmentViewDTO.getTotalQualityAssessment().getBad() +
                qualityAssessmentViewDTO.getTotalQualityAssessment().getFair();
        yVals1.add(new Entry((float) (qualityAssessmentViewDTO.getTotalQualityAssessment().getExcellent() *
                mult / total), 0));
        yVals1.add(new Entry((float) (qualityAssessmentViewDTO.getTotalQualityAssessment().getGood() * mult / total),
                1));
        yVals1.add(new Entry((float) (qualityAssessmentViewDTO.getTotalQualityAssessment().getFair() * mult / total), 2));
        yVals1.add(new Entry((float) (qualityAssessmentViewDTO.getTotalQualityAssessment().getBad() * mult / total), 3));
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(title[i % title.length]);
        }
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        dataSet.setValueLinePart1OffsetPercentage(90.f);
        dataSet.setValueLinePart1Length(0.6f);
        dataSet.setValueLinePart2Length(0.5f);
        // dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.setExtraOffsets(0, 0, 0, Utils.getDimension(R.dimen.padding_bottom));
        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }


    @OnClick({R.id.ivBack,R.id.llGotoFeedback})
    public void onClick( View view ) {
        switch (view.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                break;
            case R.id.llGotoFeedback:
                if (numFeedback!= 0){
                    //get day and put to another class
                    Bundle data = new Bundle();
                    data.putString(Constants.IntentExtra.DATE_TIME, date);
                    data.putLong(Constants.IntentExtra.TOUR_ID, tourId);
                    data.putLong(Constants.IntentExtra.TOUR_PLAN_ID, tourPlanId);
                    goNextScreen(FeedbackActivity.class, data);
                }
                break;
        }
    }

    /**
     * render layout
     *
     * @param qualityAssessmentViewDTO
     */
    @Override
    public void renderLayout( QualityAssessmentViewDTO qualityAssessmentViewDTO ) {
        this.qualityAssessmentViewDTO = qualityAssessmentViewDTO;
        if (qualityAssessmentViewDTO != null){
            renderLayoutChart();
            tvNumFeedBack.setText(qualityAssessmentViewDTO.getNumFeedback()+"");
            numFeedback = qualityAssessmentViewDTO.getNumFeedback();
            if (qualityAssessmentAdapter == null) {
                qualityAssessmentAdapter = new QualityAssessmentAdapter(this, qualityAssessmentViewDTO);
                qualityAssessmentAdapter.setMode(ExpandableRecyclerAdapter.MODE_NORMAL);
                qualityAssessmentAdapter.expandAll();
                rvQuality.setAdapter(qualityAssessmentAdapter);
            }
            qualityAssessmentAdapter.notifyDataSetChanged();
        }
        closeProgress();
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
