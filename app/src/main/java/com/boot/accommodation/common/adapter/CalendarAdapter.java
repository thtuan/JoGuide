package com.boot.accommodation.common.adapter;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.CalendarDTO;
import com.boot.accommodation.listener.OnEventControl;
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
    ViewHolder viewHolder;
    Calendar today;
    Calendar calendar;
    OnEventControl listener;
    public static final int ACTION_CLICK = 1;

    public CalendarAdapter(List<CalendarDTO> items) {
        super(items);
        today = Calendar.getInstance();
        calendar = Calendar.getInstance();
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return viewHolder = new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_calendar;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    public void setListener(OnEventControl listener) {
        this.listener = listener;
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
            if (calendar.get(Calendar.MONTH) != today.get(Calendar.MONTH) || calendar.get(Calendar.YEAR) != today.get(Calendar.YEAR)) {
                // if this day is outside current month, grey it out
                tvDay.setTextColor(ContextCompat.getColor(mContext, R.color.grey_bg));
            } else if (calendar.get(Calendar.DATE) == today.get(Calendar.DATE)) {
                // if it is today, set it to blue/bold
                tvDay.setTypeface(null, Typeface.BOLD);
                tvDay.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
            }
            tvDay.setText(String.valueOf(calendar.get(Calendar.DATE)));

            if (data.getMeetings()!= null){
                switch (data.getMeetings().size()){
                    case 0:
                        break;
                    case 1:
                        tvDay.setBackgroundColor(Utils.getColor(R.color.green));
                        break;
                    case 2:
                        tvDay.setBackgroundColor(Utils.getColor(R.color.yellow));
                        break;
                    default:
                        tvDay.setBackgroundColor(Utils.getColor(R.color.red));
                        break;
                }
            }

            tvDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.setDate(today.getTime());
                    if (listener != null) {
                        listener.onEventControl(ACTION_CLICK, data, view, position);
                    }
                }
            });
        }
    }
}
