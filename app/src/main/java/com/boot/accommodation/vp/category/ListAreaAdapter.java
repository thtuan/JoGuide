package com.boot.accommodation.vp.category;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * List area dto
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class ListAreaAdapter extends BaseRecyclerViewAdapter<AreaDTO, ListAreaAdapter.ViewHolder> {

    private List<AreaDTO> itemsAll; //Items all
    private List<AreaDTO> areaChoose = new ArrayList<>(); //Area choose

    public ListAreaAdapter(List<AreaDTO> items) {
        super(items);
        this.items = items;
        this.itemsAll = new ArrayList<>();
        this.itemsAll.addAll(items);
//        cloneData(items);
    }

//    /**
//     * Clone data
//     * @param items
//     */
//    private void cloneData(List<AreaDTO> items){
//        for(AreaDTO item: items){
//            AreaDTO dto = new AreaDTO();
//            dto.setId(item.getId());
//            dto.setLongName(item.getLongName());
//            dto.setShortName(item.getShortName());
//            dto.setType(item.getType());
//            dto.setParentAreaId(item.getParentAreaId());
//            dto.setFullLongName(item.getFullLongName());
//            itemsAll.add(dto);
//        }
//    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_category_location;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.cbCategory)
        CheckBox cbCategory;
        @Bind(R.id.rlCategory)
        RelativeLayout rlCategory;

        ViewHolder(View view) {
            super(view);
        }

        public void bindData(AreaDTO data, int position) {
            this.data = data;
            this.position = position;
            tvName.setText(data.getFullLongName());
            cbCategory.setChecked(data.isSelected());
            rlCategory.setOnClickListener(this);
            cbCategory.setOnClickListener(this);
        }

        @OnClick({R.id.rlCategory, R.id.cbCategory})
        public void onClick(View v) {
            AreaDTO dto = ((AreaDTO) data);
            switch (v.getId()) {
                case R.id.cbCategory:
                    dto.setSelected(cbCategory.isChecked());
                    break;
                case R.id.rlCategory:
                    cbCategory.setChecked(!cbCategory.isChecked());
                    dto.setSelected(cbCategory.isChecked());
                    notifyDataSetChanged();
                    break;
            }
        }
    }

    /**
     * Get area id choose
     * @return
     */
    public String getAreaIdsChoose() {
        areaChoose.clear();
        StringBuilder category = new StringBuilder();
        for (int i = 0; i < itemsAll.size(); i++) {
            if (itemsAll.get(i).isSelected()) {
                if (StringUtil.isNullOrEmpty(category.toString())) {
                    category.append(itemsAll.get(i).getId());
                } else {
                    category.append(Constants.STR_TOKEN + itemsAll.get(i).getId());
                }
                areaChoose.add(itemsAll.get(i));
            }
        }
        return category.toString();
    }

    /**
     * Cancel choose
     */
    public void cancelChoose() {
        for (AreaDTO item: itemsAll) {
            boolean isChoose = false;
            for (AreaDTO itemChoose: areaChoose){
                if (item.getId() == itemChoose.getId()) {
                    isChoose = true;
                    break;
                }
            }
            item.setSelected(isChoose);
        }
    }

    /**
     * Update search area
     * @param search
     */
    public void updateSearchArea(String search) {
        items.clear();
        for (AreaDTO item: itemsAll) {
            String area = StringUtil.getEngStringFromUnicodeString(item.getFullLongName());
            search = StringUtil.getEngStringFromUnicodeString(search);
            if (area.trim().toUpperCase().contains(search.trim().toUpperCase())) {
                items.add(item);
            }
        }
        notifyDataSetChanged();
    }

}
