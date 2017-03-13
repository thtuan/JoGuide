package com.boot.accommodation.vp.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Area adapter
 *
 * @author tuanlt
 * @since 2:10 PM 9/20/16
 */
public class AreaAdapter extends ArrayAdapter<AreaDTO> {

    private Context context; //Context
    private List<AreaDTO> items = new ArrayList<>();
    private List<AreaDTO> itemsAll;
    private List<AreaDTO> suggestions;

    public AreaAdapter(Context context, int resource, List<AreaDTO> items) {
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
    private void cloneData(List<AreaDTO> items){
        for(AreaDTO item: items){
            AreaDTO dto = new AreaDTO();
            dto.setId(item.getId());
            dto.setLongName(item.getLongName());
            dto.setShortName(item.getShortName());
            dto.setType(item.getType());
            dto.setParentAreaId(item.getParentAreaId());
            dto.setFullLongName(item.getFullLongName());
            itemsAll.add(dto);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AreaDTO areaDTO = items.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    R.layout.sp_item_day, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(areaDTO.getFullLongName());
        tv.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        AreaDTO areaDTO = items.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    R.layout.sp_item_day, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(areaDTO.getFullLongName());
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
            AreaDTO str = (AreaDTO) (resultValue);
            return str.getFullLongName();
        }

        @Override
        protected FilterResults performFiltering( CharSequence constraint ) {
            if (constraint != null) {
                suggestions.clear();
                for (AreaDTO dto : itemsAll) {
                    String temp1 = StringUtil.getEngStringFromUnicodeString(dto.getFullLongName());
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
            ArrayList<AreaDTO> filteredList = (ArrayList<AreaDTO>) results.values;
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
    public List<AreaDTO> getAllData() {
        return itemsAll;
    }
}
