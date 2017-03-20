package com.boot.accommodation.common.adapter;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.CalendarDTO;
import com.boot.accommodation.dto.view.TourGuideEventDTO;
import com.boot.accommodation.util.Utils;

import java.util.Calendar;
import java.util.List;

/**
 * CalendarAdapter
 *
 * @author thtuan
 * @since 2:56 PM 04-09-2016
 */
public class CalendarAdapter extends BaseRecyclerViewAdapter<CalendarDTO, CalendarAdapter.ViewHolder> {
    Calendar today;
    Calendar calendar;
    private List<TourGuideEventDTO> events;
    public static final int ACTION_CLICK = 1;
    public static final int ACTION_LONG_CLICK = 2;
    public static final int ACTION_CANCEL_EDIT = 3;

    public CalendarAdapter(List<CalendarDTO> items) {
        super(items);
        today = Calendar.getInstance();
        calendar = Calendar.getInstance();
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_calendar;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tvDay;

        public ViewHolder(View view) {
            super(view);
            tvDay = (TextView) view.findViewById(R.id.tvDay);

        }

        /**
         * gan data vao adapter
         *
         * @param data
         * @param position
         */
        public void bindData(final CalendarDTO data, final int position) {
            this.data = data;
            this.position = position;
            calendar.setTime(data.getDate());
            tvDay.setTypeface(null, Typeface.NORMAL);
            tvDay.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            if ((int) calendar.get(Calendar.MONTH) != data.getCurrentViewMonth()) {
                // if this day is outside current month, grey it out
                tvDay.setTextColor(ContextCompat.getColor(mContext, R.color.grey_bg));

            } else if (calendar.get(Calendar.DATE) == today.get(Calendar.DATE) && calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
                // if it is today, set it to blue/bold
                tvDay.setTypeface(null, Typeface.BOLD);
                tvDay.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
            }

            tvDay.setText(String.valueOf(calendar.get(Calendar.DATE)));
            if (events != null && events.size() > 0) {
                for (TourGuideEventDTO event : events) {
                    if (event.getDate().getYear() == data.getDate().getYear()
                            && event.getDate().getMonth() == data.getDate().getMonth()
                            && event.getDate().getDate() == data.getDate().getDate()) {
                        tvDay.setBackgroundColor(Utils.getColor(R.color.red));
                        break;
                    }
                    else {
                        tvDay.setBackgroundColor(Utils.getColor(R.color.white));
                    }
                }
            }

            tvDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onEventControl(ACTION_CLICK, data, view, position);
                    }
                }
            });
            tvDay.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (data.isEdit()) {
                        if (listener != null) {
                            data.setEdit(false);
                            listener.onEventControl(ACTION_CANCEL_EDIT, data, view, position);
                        }
                    } else {
                        if (listener != null) {
                            data.setEdit(true);
                            listener.onEventControl(ACTION_LONG_CLICK, data, view, position);
                        }
                    }

                    return true;
                }
            });
        }
    }

    public List<TourGuideEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<TourGuideEventDTO> events) {
        this.events = events;
        notifyDataSetChanged();
    }
}
