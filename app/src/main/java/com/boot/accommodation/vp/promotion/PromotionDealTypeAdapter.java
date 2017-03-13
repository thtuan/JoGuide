package com.boot.accommodation.vp.promotion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Area adapter
 *
 * @author tuanlt
 * @since 2:10 PM 12/12/16
 */
public class PromotionDealTypeAdapter extends ArrayAdapter<CategoryItemDTO> {

    private Context context; //Context
    private List<CategoryItemDTO> items = new ArrayList<>();
    private List<CategoryItemDTO> itemsAll; //Item all
    private List<CategoryItemDTO> suggestions; //Items suggest

    public PromotionDealTypeAdapter(Context context, int resource, List<CategoryItemDTO> items) {
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
    private void cloneData(List<CategoryItemDTO> items){
        for(CategoryItemDTO item: items){
            CategoryItemDTO dto = new CategoryItemDTO();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setValue(item.getValue());
            dto.setSelected(item.getSelected());
            dto.setParentCategoryId(item.getParentCategoryId());
            itemsAll.add(dto);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryItemDTO categoryItemDTO = items.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    R.layout.sp_item_day, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(categoryItemDTO.getName());
        tv.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        CategoryItemDTO categoryItemDTO = items.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(
                    R.layout.sp_item_day, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(categoryItemDTO.getName());
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
            CategoryItemDTO str = (CategoryItemDTO) (resultValue);
            return str.getName();
        }

        @Override
        protected FilterResults performFiltering( CharSequence constraint ) {
            if (constraint != null) {
                suggestions.clear();
                for (CategoryItemDTO dto : itemsAll) {
                    String temp1 = StringUtil.getEngStringFromUnicodeString(dto.getName());
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
            ArrayList<CategoryItemDTO> filteredList = (ArrayList<CategoryItemDTO>) results.values;
            clear();
            if (results != null && results.count > 0) {
                addAll(filteredList);
            }else {
                notifyDataSetChanged();
            }

        }
    };
}
