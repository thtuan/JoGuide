package com.boot.accommodation.vp.tour;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import com.android.datetimepicker.date.DatePickerDialog;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.FeedbackItemDTO;
import com.boot.accommodation.dto.view.FeedbackTourItemDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Feedback View
 *
 * @author Vuong-bv
 * @since: 6/4/2016
 */
public class FeedbackActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, FeedbackViewMgr,
        View.OnTouchListener{

    public static final int ACTION_VIEW_POPUP = 10001; // select view popup
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitleProfile)
    TextView tvTitleProfile;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    JoCoEditText edDate;
    @Bind(R.id.spListTour)
    Spinner spListTour;
    @Bind(R.id.rvListFeedbeck)
    RecyclerView rvListFeedbeck;
    @Bind(R.id.ivDelete)
    ImageView ivDelete;
    private long tourPlanId;//tour plan id
    private long tourId;// tour id
    private String date;//date
    FeedbackPresenterMgr feedbackPresenterMgr;
    private FeedbackAdapter feedbackAdapter; // feedback adapter
    ArrayList<FeedbackTourItemDTO> listTourFeedbackDTO = new ArrayList<>();//list tour for user select
    private Calendar calendar;//calendar
    private DateFormat dateFormat;//fortmat date
    private int indexSp = -1;
    ListTourFeedbackAdapter adapter; //Adapter list tour

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        showProgress();
        Intent intent = getIntent();
        if (intent != null) {
            tourPlanId = intent.getLongExtra(Constants.IntentExtra.TOUR_PLAN_ID,0);
            tourId = intent.getLongExtra(Constants.IntentExtra.TOUR_ID,0);
            date = intent.getStringExtra(Constants.IntentExtra.DATE_TIME);
        }
        initView();
        initData();
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat(DateUtil.FORMAT_DATE, Locale.getDefault());

        //spinner list tour for user choose : show feedback of this tour they choose
        spListTour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(indexSp != position) {
                    indexSp = position;
                    tourPlanId = listTourFeedbackDTO.get(indexSp).getTourPlanId();
                    tourId = listTourFeedbackDTO.get(indexSp).getTourId();
                    selectTour();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Khoi tao control
     */
    private void initView() {
        edDate = (JoCoEditText)findViewById(R.id.edDate);
        edDate.setIsHandleDefault(false);
        edDate.setImageClearVisibile(false);
        edDate.setOnTouchListener(this);
        if (date == null) {
            edDate.setText(StringUtil.getString(R.string.text_title_select_date_feedback));
        } else {
            edDate.setText(DateUtil.convertDateWithFormat(date, DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_DATE));
        }
        rvListFeedbeck.setLayoutManager(new LinearLayoutManager(this));
        rvListFeedbeck.setHasFixedSize(true);
    }

    /**
     * innit data
     */
    private void initData() {
        feedbackPresenterMgr = new FeedbackPresenter(this);
        indexSp = 0;
        feedbackPresenterMgr.getFeedback(tourId,tourPlanId, date);
    }

    @Override
    public void renderLayout(ArrayList<FeedbackItemDTO> lstFeedback) {
        if (feedbackAdapter == null) {
            feedbackAdapter = new FeedbackAdapter(lstFeedback);
            feedbackAdapter.setListener(this);
            feedbackAdapter.setEnableLoadMore(true);
            rvListFeedbeck.setAdapter(feedbackAdapter);
        } else {
            feedbackAdapter.setData(lstFeedback);
        }
        feedbackAdapter.notifyDataSetChanged();
        closeProgress();
    }

    @Override
    public void renderListTour(ArrayList<FeedbackTourItemDTO> listTour) {
        listTourFeedbackDTO.clear();
        listTourFeedbackDTO.addAll(listTour);
        if(adapter == null) {
            adapter = new ListTourFeedbackAdapter(this, listTourFeedbackDTO);
            spListTour.setAdapter(adapter);
        }
        int index = 0;
        for(FeedbackTourItemDTO item: listTourFeedbackDTO){
            if (item.getTourId() != null && item.getTourPlanId() != null && tourId == item.getTourId() && tourPlanId ==
                item.getTourPlanId()) {
                break;
            }
            index++;
        }
        if(index < listTourFeedbackDTO.size()) {
            indexSp = index;
        }
        spListTour.setSelection(indexSp);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getFeedbacBlank() {
       // showMessage(getString(R.string.text_get_feedback_null));
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    @OnClick({R.id.ivBack, R.id.edDate,R.id.ivDelete})
    public void onClick(View v) {
        int action = 0;
        switch (v.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                break;
            case R.id.edDate:
                break;
            case R.id.ivDelete:
                edDate.setText(StringUtil.getString(R.string.text_title_select_date_feedback));
                date = null;
                feedbackPresenterMgr.getFeedback(tourId,tourPlanId, date);
                break;
        }
    }

    /**
     * lấy thời gian
     */
    private void updateTime() {
        //get day to send another class
        date = DateUtil.formatDate(calendar.getTimeInMillis(),DateUtil.FORMAT_DATE_TIME_ZONE);
        edDate.setText(dateFormat.format(calendar.getTime()));
        feedbackPresenterMgr.getFeedback(tourId,tourPlanId, date);
    }

    /**
     * when user select a tour, call this method request to server get feedback for it
     *
     */
    private void selectTour() {
        if(!StringUtil.isNullOrEmpty(date)) {
            DateUtil.convertDateWithFormat(date, DateUtil.FORMAT_DATE, DateUtil.FORMAT_DATE_TIME_ZONE);
        }
        feedbackPresenterMgr.getFeedback(tourId,tourPlanId, date);
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        updateTime();
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case ACTION_VIEW_POPUP:
                openPopUpFeedBack((FeedbackItemDTO) item);
                break;
        }
    }

    @Override
    public void onLoadMore(int position) {
        feedbackPresenterMgr.getMoreFeedback(tourId, tourPlanId, date, feedbackAdapter);
    }

    @Override
    public void receiveBroadcast( int action, Bundle bundle ) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                tourPlanId = 0;
                tourId = 0;
                date = null;
                indexSp = -1;
                if (tourPlanId == 0) {
                    feedbackPresenterMgr.getFeedback(null,null, date);
                }else {
                    feedbackPresenterMgr.getFeedback(tourId,tourPlanId, date);
                }
                break;
        }
    }

    /**
     * view popup for feedback > 2 line(too long)
     *
     * @param dto
     */
    public void openPopUpFeedBack(final FeedbackItemDTO dto) {
        final Context context = this;
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.item_feedback_popup, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final ImageView ivImage = (ImageView) promptsView.findViewById(R.id.ivImagePopup);
        final TextView tvTourAndName = (TextView) promptsView.findViewById(R.id.tvTourAndNamePopup);
        final TextView tvDateTime = (TextView) promptsView.findViewById(R.id.tvDateTimePopup);
        final TextView tvContent = (TextView) promptsView.findViewById(R.id.tvContentPopup);
        ImageUtil.loadImage(ivImage, ImageUtil.getImageUrl(dto.getUserAvatar()));
        tvTourAndName.setText(dto.getUserName() + " " + "►" + " " + dto.getTourName());
        tvDateTime.setText(DateUtil.convertDateWithFormat(dto.getDate(), DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_DATE));
        tvContent.setText(dto.getContent());
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // this is result u get from edit text
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v == edDate) {
            if (!v.onTouchEvent(event)) {
                DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                        (Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }else{
                date = null;
            }
        }
        return false;
    }
}
