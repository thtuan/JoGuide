package com.boot.accommodation.vp.tourguide;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.TourGuideDTO;
import com.boot.accommodation.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thtuan on 3/15/17.
 */

public class TourGuideFilterAdapter extends ArrayAdapter<TourGuideDTO> {

    private Context context; //Context
    private List<TourGuideDTO> items = new ArrayList<>();
    private List<TourGuideDTO> itemsAll;
    private List<TourGuideDTO> suggestions;

    public TourGuideFilterAdapter(Context context, int resource, List<TourGuideDTO> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
        this.itemsAll = new ArrayList<>();
        cloneData(items);
        this.suggestions = new ArrayList<>();
    }


    /**
     * Clone data
     * @param items
     */
    private void cloneData(List<TourGuideDTO> items){
        for(TourGuideDTO item: items){
            TourGuideDTO dto = new TourGuideDTO();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setPhone(item.getPhone());
            dto.setImage(item.getImage());
            dto.setEmail(item.getEmail());
            dto.setTown(item.getTown());
            dto.setLocation(item.getLocation());
            dto.setTypeUser(item.getTypeUser());
            itemsAll.add(dto);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TourGuideDTO tourGuideDTO = items.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    R.layout.sp_item_day, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(tourGuideDTO.getTown());
        tv.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TourGuideDTO tourGuideDTO = items.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    R.layout.sp_item_day, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(tourGuideDTO.getTown());
        tv.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString( Object resultValue ) {
            TourGuideDTO str = (TourGuideDTO) (resultValue);
            return str.getTown();
        }

        @Override
        protected FilterResults performFiltering( CharSequence constraint ) {
            if (constraint != null) {
                suggestions.clear();
                for (TourGuideDTO dto : itemsAll) {
                    String temp1 = StringUtil.getEngStringFromUnicodeString(dto.getTown());
                    String temp2 = StringUtil.getEngStringFromUnicodeString(constraint.toString());
                    if (temp1.toLowerCase().contains(temp2.toString().toLowerCase())) {
                        suggestions.add(dto);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsAll;
                filterResults.count = itemsAll.size();
                return filterResults;
            }
        }

        @Override
        protected void publishResults( CharSequence constraint, FilterResults results ) {
            ArrayList<TourGuideDTO> filteredList = (ArrayList<TourGuideDTO>) results.values;
            clear();
            if (results != null && results.count > 0) {
                addAll(filteredList);
            }else {
                notifyDataSetChanged();
            }

        }
    };

    /**
     * Get all data in adapter
     * @return
     */
    public List<TourGuideDTO> getAllData() {
        return itemsAll;
    }
}
