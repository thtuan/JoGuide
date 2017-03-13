package com.boot.accommodation.vp.home;


import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * List Category for create location Adapter
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class ListCategoryFilterAdapter extends BaseRecyclerViewAdapter<CategoryItemDTO, ListCategoryFilterAdapter.ViewHolder> {

    private List<Long> categoryIdsChoose = new ArrayList<>(); //Category id choose
    public ListCategoryFilterAdapter(List<CategoryItemDTO> items) {
        super(items);
        updateCategoryChose(true);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_category_fitler_location;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(items.get(position), position);
    }

    /**
     * Update category choose
     */
    public void updateCategoryChose(boolean isChoose){
        if (!isChoose) {
            for (CategoryItemDTO item : items) {
                boolean isExisted = false;
                for (Long id : categoryIdsChoose) {
                    if (item.getId() == id.longValue()) {
                        item.setSelected(true);
                        isExisted = true;
                    }
                }
                if (!isExisted) {
                    item.setSelected(false);
                }
            }
        } else {
            categoryIdsChoose.clear();
            for (CategoryItemDTO item : items) {
                if (item.getSelected()) {
                    categoryIdsChoose.add(item.getId());
                }
            }
        }
        notifyDataSetChanged();

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

        public void bindData(CategoryItemDTO data, int position) {
            this.data = data;
            this.position = position;
            if (Utils.getString(R.string.Attraction).equals(StringUtil.getStringResourceByName(data.getName()))){
                tvName.setText(StringUtil.getStringResourceByName(data.getName()));
            }else if (Utils.getString(R.string.Hotel).equals(StringUtil.getStringResourceByName(data.getName()))){
                tvName.setText(StringUtil.getStringResourceByName(data.getName()));
            }else if (Utils.getString(R.string.Museum).equals(StringUtil.getStringResourceByName(data.getName()))){
                tvName.setText(StringUtil.getStringResourceByName(data.getName()));
            }else if (Utils.getString(R.string.Park).equals(StringUtil.getStringResourceByName(data.getName()))){
                tvName.setText(StringUtil.getStringResourceByName(data.getName()));
            }else if (Utils.getString(R.string.Restaurant).equals(StringUtil.getStringResourceByName(data.getName()))){
                tvName.setText(StringUtil.getStringResourceByName(data.getName()));
            }else {
                tvName.setText(data.getName());
            }

            cbCategory.setChecked(data.getSelected());
            rlCategory.setOnClickListener(ViewHolder.this);
            cbCategory.setOnClickListener(ViewHolder.this);
        }

        @OnClick({R.id.rlCategory, R.id.cbCategory})
        public void onClick(View v) {
            CategoryItemDTO dto = ((CategoryItemDTO) data);
            switch (v.getId()) {
                case R.id.cbCategory:
                    dto.setSelected(cbCategory.isChecked());
//                    unSelectedRemain(dto.getId());
                    break;
                case R.id.rlCategory:
//                    if(!cbCategory.isChecked()) {
//                        cbCategory.setChecked(!cbCategory.isChecked());
//                    }
                    dto.setSelected(!cbCategory.isChecked());
//                    unSelectedRemain(dto.getId());
                    notifyDataSetChanged();
                    break;
            }
        }

//        /**
//         * Unselected remain item in list
//         */
//        private void unSelectedRemain(Long id) {
//            for (CategoryLocationItem item : items) {
//                if (item.getId() != id) {
//                    item.setSelected(false);
//                }
//            }
//            notifyDataSetChanged();
//        }

    }
}
