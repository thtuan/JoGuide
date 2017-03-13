package com.boot.accommodation.vp.tour;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.ScheduleDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScheduleActivity extends BaseActivity implements ScheduleMgr, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @Bind(R.id.spNotificationSchedule)
    Spinner spNotificationSchedule;
    @Bind(R.id.etNotificationTitleSchedule)
    EditText etNotificationTitleSchedule;
    @Bind(R.id.etNotificationMessageSchedule)
    EditText etNotificationMessageSchedule;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    boolean sending = false;
    private SchedulePresenterMgr schedulePresenterMgr;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dateTimeFormat;
    private int type;
    private int pos;
    private long tourId; // tourId
    private long tourPlanId; // tourPlanId
    private String dateTimeZone;
    private TripItemDTO tripItemDTO; // tripItemDTO
    String[] name = {"NOTICE", "REMIND", "ATTENTION"};
    int[] ids = {R.drawable.ic_speaker_green, R.drawable.ic_alarm_green, R.drawable.ic_attention};
    private Bundle bundle = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        tourId = getIntent().getLongExtra(Constants.IntentExtra.TOUR_ID,0);
        tourPlanId = getIntent().getLongExtra(Constants.IntentExtra.TOUR_PLAN_ID, 0);
        /**
         * khai bao adapter
         */
        ScheduleAdapter adapter = new ScheduleAdapter(this, name, ids);
        spNotificationSchedule.setAdapter(adapter);
        pos = 0;
        schedulePresenterMgr = new SchedulePresenter(this);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat(DateUtil.FORMAT_DATE, Locale.getDefault());
        timeFormat = new SimpleDateFormat(DateUtil.FORMAT_TIME_SECOND, Locale.getDefault());
        dateTimeFormat = new SimpleDateFormat(DateUtil.FORMAT_DATE_TIME_ZONE, Locale.getDefault());
        spNotificationSchedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position + 1;
                pos = position;
                if (pos == 1){
                    tvDate.setVisibility(View.VISIBLE);
                    tvTime.setVisibility(View.VISIBLE);
                }
                else {
                    tvDate.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = 0;
            }
        });
        if (getIntent() != null && getIntent().hasExtra(Constants.IntentExtra.TOUR_ID)) {
                tourId = getIntent().getExtras().getLong(Constants.IntentExtra.TOUR_ID);
                tourPlanId = getIntent().getExtras().getLong(Constants.IntentExtra.TOUR_PLAN_ID);
        }
    }

    /**
     * send schedule
     *
     * @param tourid      id
     * @param scheduleDTO thong tin schedule
     */

    public void sendSchedule(String tourid, String tourPlanId, ScheduleDTO scheduleDTO) {
        if(!sending) {
            schedulePresenterMgr.createSchedule(tourid, tourPlanId, scheduleDTO);
            sending = true;
        }
    }

    /**
     * lấy thời gian
     */
    private void update() {
        tvDate.setText(dateFormat.format(calendar.getTime()));
        tvTime.setText(timeFormat.format(calendar.getTime()));
        dateTimeZone = dateTimeFormat.format(calendar.getTime());
    }

    @OnClick({R.id.tvDate, R.id.tvTime, R.id.tvNotificationSendSchedule, R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * chon ngay
             */
            case R.id.tvDate:
                DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                break;
            /**
             * chon gio
             */
            case R.id.tvTime:
                TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                break;
            /**
             * click send
             */
            case R.id.tvNotificationSendSchedule: {
                showAlert(getString(R.string.text_confirm_create_notify), null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = etNotificationTitleSchedule.getText().toString();
                        String content = etNotificationMessageSchedule.getText().toString();
                        if (checkSchedule()) {
                            /**
                             * Khi ô ngày giờ không nhập
                             */
                            ScheduleDTO scheduleDTO = new ScheduleDTO();
                            if (tvDate.getText().toString().equals("") || tvTime.getText().toString().equals("")) {

                                scheduleDTO.setTitle(title);
                                scheduleDTO.setContent(content);
                                scheduleDTO.setNotifyType(pos + 1);
                                scheduleDTO.setUserId(Long.parseLong(JoCoApplication.getInstance().getProfile().getUserData().getId()));
                                scheduleDTO.setCreateAt(DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
                                /**
                                 * set id su dung la 17
                                 */
                                sendSchedule(String.valueOf(tourId), String.valueOf(tourPlanId), scheduleDTO);
                            } else {
                                scheduleDTO.setTitle(title);
                                scheduleDTO.setContent(content);
                                scheduleDTO.setNotifyType(pos + 1);
                                scheduleDTO.setUserId(Long.parseLong(JoCoApplication.getInstance().getProfile().getUserData().getId()));
                                scheduleDTO.setRemindTime(dateTimeZone);
                                scheduleDTO.setCreateAt(DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
                                /**
                                 * set id su dung la 17
                                 */
                                sendSchedule(String.valueOf(tourId), String.valueOf(tourPlanId), scheduleDTO);
                            }

                        }
                    }
                }, getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }, getString(R.string.text_cancel), true);
                break;
            }
            case R.id.ivBack:
                super.onBackPressed();
                break;
        }
    }

    /**
     * set ngay cho calendar
     */

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        calendar.set(Calendar.SECOND,0);
        update();
    }

    /**
     * set gio cho calendar
     *
     * @param view      view
     * @param hourOfDay gio
     * @param minute    phut
     */
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND,0);
        update();
    }

    @Override
    public void showMessage() {
        showMessage(StringUtil.getString(R.string.text_send_notifi_success));
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        sending = false;
        closeProgress();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    /**
     * result cho du lieu tra ve
     */
    @Override
    public void returnResult(ScheduleDTO tourNotificationDTO) {
        sending = false;
        bundle.putParcelable(Constants.IntentExtra.NOTIFICATION,tourNotificationDTO);
        sendBroadcast(Constants.ActionEvent.RESULT_OK, bundle);
        finish();
    }

    private boolean checkSchedule() {
        if (etNotificationTitleSchedule != null && etNotificationMessageSchedule != null && tvDate != null && tvTime != null) {
            if (!etNotificationTitleSchedule.getText().toString().equals("")) {
                if(!etNotificationMessageSchedule.getText().toString().equals("")){
                    if (name[pos].equals("REMIND")) {

                        if (!tvDate.getText().toString().equals("") && !tvTime.getText().toString().equals("")) {

                            return true;
                        } else {
                            showMessage(StringUtil.getString(R.string.text_please_input_date_and_time));
                            return false;
                        }

                    } else {
                        return true;
                    }
                } else {
                    showMessage(StringUtil.getString(R.string.text_please_input_message));
                    etNotificationMessageSchedule.setFocusableInTouchMode(true);
                    etNotificationMessageSchedule.requestFocus();
                    return false;
                }

            } else {
                showMessage(StringUtil.getString(R.string.text_please_input_title));
                etNotificationTitleSchedule.setFocusableInTouchMode(true);
                etNotificationTitleSchedule.requestFocus();
                return false;
            }
        } else {
            showMessage(StringUtil.getString(R.string.error_unknown_error));
            return false;
        }
    }

}
