package com.boot.accommodation.vp.category;


import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * List Category for create location Adapter
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class ListCategoryAdapter extends BaseRecyclerViewAdapter<CategoryItemDTO, ListCategoryAdapter.ViewHolder> {

    private boolean isShowCheck = true; //Variable show check or no

    public ListCategoryAdapter(List<CategoryItemDTO> items) {
        super(items);
    }

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

    public boolean isShowCheck() {
        return isShowCheck;
    }

    public void setShowCheck(boolean showCheck) {
        isShowCheck = showCheck;
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
            if (!isShowCheck) {
                cbCategory.setVisibility(View.GONE);
            }
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
                case R.id.rlCategory:
                    dto.setSelected(cbCategory.isChecked());
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(Constants.ActionEvent.ACTION_CHOOSE_CATEGORY, data, view, position);
            }
        }

    }
}
