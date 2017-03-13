package com.boot.accommodation.vp.weather;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.WeatherItemDTO;
import com.boot.accommodation.util.DateUtil;
/*import com.thbs.skycons.library.CloudMoonView;
import com.thbs.skycons.library.CloudRainView;
import com.thbs.skycons.library.CloudSunView;
import com.thbs.skycons.library.CloudThunderView;
import com.thbs.skycons.library.CloudView;
import com.thbs.skycons.library.WindView;*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class WeatherAdapter extends BaseRecyclerViewAdapter<WeatherItemDTO, WeatherAdapter.ViewHolder> {

    List<WeatherItemDTO> result = new ArrayList<>();
    public WeatherAdapter(List<WeatherItemDTO> items, boolean isLoadAll) {
//        super(items);
        handleData(items, isLoadAll);
    }

    public void handleData(List<WeatherItemDTO> items, boolean isLoadAll){
        result.clear();
        if (!isLoadAll) {
            SimpleDateFormat defaultDateFormat = new SimpleDateFormat(DateUtil.FORMAT_DATE_TIME_ZONE);
            try {
                for (WeatherItemDTO item : items) {
                    String dateWeather = defaultDateFormat.format(new Date(item.getTime() * 1000L));
//                if(DateUtil.compareDateWithFormat(dateWeather, DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE),
//                        DateUtil.FORMAT_DATE_TIME_ZONE) == 1) {
                    Date date1 = defaultDateFormat.parse(dateWeather);
                    Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
                    calendar.setTime(date1);   // assigns calendar to given date
                    int hours = calendar.get(Calendar.HOUR_OF_DAY);
                    if (hours == 6 || hours == 9 || hours == 12 | hours == 15 || hours == 18) {
                        result.add(item);
                    }
//                }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            result.addAll(items);
        }
        setData(result);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_weather;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
//        holder.bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.tvTime)
        TextView tvTime;
        @Bind(R.id.tvSummary)
        TextView tvSummary;
        @Bind(R.id.tvTemp)
        TextView tvTemp;
        @Bind(R.id.llWeather)
        LinearLayout llWeather;

        ViewHolder(View view) {
            super(view);
        }

        /*public void bindData(WeatherItemDTO data, int position) {
            this.data = data;
            this.position = position;
            String date = new SimpleDateFormat(DateUtil.FORMAT_DATE_TIME_ZONE).format(new Date(data.getTime() * 1000L));
            tvTime.setText(DateUtil.convertDateWithFormat(date, DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_TIME));
            tvSummary.setText(data.getSummary());
            tvTemp.setText(String.valueOf(Math.round(data.getTemperature())) + "°C");
            llWeather.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            if ("cloudy".equals(data.getIcon())) {
                CloudView windView = new CloudView(mContext);
                windView.setLayoutParams(params);
                llWeather.addView(windView, 0);
            } else if ("partly-cloudy-night".equals(data.getIcon())) {
                CloudMoonView windView = new CloudMoonView(mContext);
                windView.setLayoutParams(params);
                llWeather.addView(windView, 0);
            } else if ("partly-cloudy-day".equals(data.getIcon())) {
                CloudSunView windView = new CloudSunView(mContext);
                windView.setLayoutParams(params);
                llWeather.addView(windView, 0);
            } else if("rain".equals(data.getIcon())){
                CloudRainView windView = new CloudRainView(mContext);
                windView.setLayoutParams(params);
                llWeather.addView(windView, 0);
            } else if("wind".equals(data.getIcon())){
                WindView windView = new WindView(mContext);
                windView.setLayoutParams(params);
                llWeather.addView(windView, 0);
            }else if("thunder".equals(data.getIcon())){
                CloudThunderView windView = new CloudThunderView(mContext);
                windView.setLayoutParams(params);
                llWeather.addView(windView, 0);
            } else {
                CloudView windView = new CloudView(mContext);
                windView.setLayoutParams(params);
                llWeather.addView(windView, 0);
            }
        }
*/
        @OnClick({R.id.llMain})
        public void OnClick(View v) {
            int action = 0;
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }


    }
}
