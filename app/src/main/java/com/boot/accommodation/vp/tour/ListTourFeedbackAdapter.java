package com.boot.accommodation.vp.tour;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.FeedbackTourItemDTO;

import java.util.ArrayList;

/**
 * List tour for feedback
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class ListTourFeedbackAdapter extends BaseAdapter {

    ArrayList<FeedbackTourItemDTO> listTourFeedbackDTO;
    LayoutInflater inflater;

    public ListTourFeedbackAdapter(Context context, ArrayList<FeedbackTourItemDTO> listTourFeedbackDTO) {
        inflater = LayoutInflater.from(context);
        this.listTourFeedbackDTO = listTourFeedbackDTO;
    }

    @Override
    public int getCount() {
        return listTourFeedbackDTO.size();
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_listtour_feedback, null);
        }
        TextView tvTourName = (TextView) convertView.findViewById(R.id.tvTourName);
        tvTourName.setText(listTourFeedbackDTO.get(position).getTourName());
        return convertView;
    }


}
