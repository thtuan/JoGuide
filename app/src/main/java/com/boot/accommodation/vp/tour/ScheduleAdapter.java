package com.boot.accommodation.vp.tour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boot.accommodation.R;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 2:22 PM 18-05-2016
 */
public class ScheduleAdapter extends BaseAdapter {

    private String[] name;
    private int[] ids;
    LayoutInflater inflater;

    public ScheduleAdapter(Context context,String[] name, int[] ids) {
        this.name = name;
        this.ids = ids;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_schedule,null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivIconSchedule);
        TextView textView = (TextView) convertView.findViewById(R.id.tvSchedule);
        imageView.setImageResource(ids[position]);
        textView.setText(name[position]);
        return convertView;
    }
}
