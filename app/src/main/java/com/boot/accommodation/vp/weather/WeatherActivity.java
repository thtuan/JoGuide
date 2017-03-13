/*
package com.boot.accommodation.vp.weather;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.dto.response.WeatherResponseDTO;
import com.boot.accommodation.dto.view.WeatherItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.model.impl.WeatherModel;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.StringUtil;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

*
 * Activity duong di
 *
 *import static android.R.attr.author;

@author tuanlt
 * @since 3:11 PM 6/10/2016


public class WeatherActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, View
        .OnTouchListener, OnEventControl {

    @Bind(R.id.edDate)
    JoCoEditText edDate;
    @Bind(R.id.ivDelete)
    ImageView ivDelete;
    @Bind(R.id.rvWeather)
    RecyclerView rvWeather;
    WeatherModel weatherModel;
    WeatherAdapter weatherAdapter;
    private Calendar calendar;//calendar
    private DateFormat dateFormat;//fortmat date
    protected AlertDialog alertDialog; //Alert dialog
    private boolean isLoadAll = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        rvWeather.setLayoutManager(new LinearLayoutManager(this));
        rvWeather.setHasFixedSize(true);
        edDate.setImageClearVisibile(false);
        edDate.setOnTouchListener(this);
        edDate.setIsHandleDefault(false);
        edDate.setText(DateUtil.formatNow(DateUtil.FORMAT_DATE));
        weatherModel = new WeatherModel();
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat(DateUtil.FORMAT_DATE, Locale.getDefault());
        initData();
    }

    private void initData() {
        double lat = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude();
        double lng = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_DATE_TIME_ZONE);
        Timestamp timeStampDate = new Timestamp(calendar.getTime().getTime());
        weatherModel.getWeather(lat, lng, timeStampDate.getTime() / 1000, new ModelCallBack<WeatherResponseDTO>() {
            @Override
            public void onSuccess(WeatherResponseDTO response) {
                if (weatherAdapter == null) {
                    weatherAdapter = new WeatherAdapter(response.getHourly().getData(), isLoadAll);
                    weatherAdapter.setListener(WeatherActivity.this);
                    rvWeather.setAdapter(weatherAdapter);
                } else {
                    weatherAdapter.handleData(response.getHourly().getData(), isLoadAll);
                }
                weatherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int errorCode, String error) {

            }

        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == edDate) {
            if (!v.onTouchEvent(event)) {
                DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                        (Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        }
        return false;
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        updateTime();
    }

*
     * lấy thời gian


    private void updateTime() {
        //get day to send another class
        edDate.setText(dateFormat.format(calendar.getTime()));
        initData();
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        super.onEventControl(action, item, View, position);
        WeatherItemDTO data = (WeatherItemDTO) item;
        showPopupDetail(data);

    }

    private void showPopupDetail(WeatherItemDTO data) {
        if (alertDialog == null) {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.popup_weather_detail, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptsView);
            // Init view
            initView(promptsView, data);
            // set dialog message
            alertDialogBuilder.setPositiveButton(StringUtil.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            // create alert dialog
            alertDialog = alertDialogBuilder.create();
        }
        initView(null, data);
        alertDialog.show();
    }

    TextView tvSummary;
    TextView tvHumidity;
    TextView tvWind;
    TextView tvVisibility;
    TextView tvSunrise;
    TextView tvSunset;

    private void initView(View promptsView, WeatherItemDTO data) {
        if (tvSummary == null) {
            tvSummary = (TextView) promptsView.findViewById(R.id.tvSummary);
            tvHumidity = (TextView) promptsView.findViewById(R.id.tvHumidity);
            tvWind = (TextView) promptsView.findViewById(R.id.tvWind);
            tvVisibility = (TextView) promptsView.findViewById(R.id.tvVisibility);
            tvSunrise = (TextView) promptsView.findViewById(R.id.tvSunrise);
            tvSunset = (TextView) promptsView.findViewById(R.id.tvSunset);
        }
        tvSummary.setText(data.getSummary());
        tvHumidity.setText(data.getHumidity() * 100 + "%");
        tvWind.setText(data.getWindSpeed() + "km");
        tvVisibility.setText(data.getVisibility() + "km");

    }

    @OnClick(R.id.rlViewAll)
    public void onClick() {
        isLoadAll = !isLoadAll;
        initData();
    }
}
*/
