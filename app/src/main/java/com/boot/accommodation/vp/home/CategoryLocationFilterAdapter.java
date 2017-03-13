package com.boot.accommodation.vp.home;


import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Category location
 *
 * @author tuanlt
 * @since: 5/13/2016
 */
public class CategoryLocationFilterAdapter extends ExpandableRecyclerAdapter<CategoryLocationFilterAdapter.CategoryItem> {

    public static final int TYPE_ITEM_CATEGORY = 1001; //Category item
    protected OnEventControl listener; // listener
    private List<CategoryItem> items = new ArrayList<>(); // list items
    private List<CategoryItemDTO> categoryLocationItems = new ArrayList<>(); //List category choose
    private List<Long> categoryIds = new ArrayList<>(); //List category choose

    public CategoryLocationFilterAdapter(Context context, List<CategoryItemDTO> categoryItems) {
        super(context);
        setData(categoryItems);
    }

    public void setListener(OnEventControl listener) {
        this.listener = listener;
    }

    /**
     * Set data
     */
    public void setData(List<CategoryItemDTO> categoryItems) {
        this.categoryLocationItems = categoryItems;
        updateChooseCategory(true);
        items.clear();
        getCategoryItems(categoryItems);
        setItems(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.item_category_location, parent));
            default:
                return new CategoryItemViewHolder(inflate(R.layout.item_category_location, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            default:
                ((CategoryItemViewHolder) holder).bind(position);
                break;
        }
    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder implements View.OnClickListener {
        TextView tvName;
        CheckBox cbCategory;
        ImageView ivHierarchy;
        RelativeLayout rlCategory;
        private CategoryItemDTO headerCategory; //Header category

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.ivAccross));
            tvName = (TextView) view.findViewById(R.id.tvName);
            cbCategory = (CheckBox) view.findViewById(R.id.cbCategory);
            rlCategory = (RelativeLayout) view.findViewById(R.id.rlCategory);
            rlCategory.setOnClickListener(this);
            cbCategory.setOnClickListener(this);
            cbCategory.setVisibility(View.GONE);
            ivHierarchy = (ImageView) view.findViewById(R.id.ivHierarchy);
            ivHierarchy.setVisibility(View.GONE);
            tvName.setTypeface(null, Typeface.BOLD);
        }

        public void bind(int position) {
            super.bind(position);
            headerCategory = visibleItems.get(position).headerCategory;
            tvName.setText(headerCategory.getName());
            cbCategory.setChecked(headerCategory.getSelected());
        }

        @OnClick({R.id.rlCategory, R.id.cbCategory})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cbCategory:
                    headerCategory.setSelected(cbCategory.isChecked());
                    handleSelected(true, headerCategory);
                    break;
                case R.id.rlCategory:
                    handleClick();
//                    if (isExpanded(position)) {
//                        collapseItems(position, true);
//                    } else {
//                        expandItems(position, true);
//                    }
                    break;
            }
        }
    }

    public class CategoryItemViewHolder extends ViewHolder implements View.OnClickListener {
        TextView tvName;
        CheckBox cbCategory;
        RelativeLayout rlCategory;
        CategoryItemDTO itemCategory; //Header category
        ImageView ivHierarchy;

        public CategoryItemViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            cbCategory = (CheckBox) view.findViewById(R.id.cbCategory);
            rlCategory = (RelativeLayout) view.findViewById(R.id.rlCategory);
            rlCategory.setOnClickListener(this);
            ivHierarchy = (ImageView) view.findViewById(R.id.ivHierarchy);
            ivHierarchy.setVisibility(View.VISIBLE);
            cbCategory.setOnClickListener(this);
            tvName.setTypeface(null, Typeface.NORMAL);
            tvName.setPadding(0, Utils.dip2Pixel(Utils.getDimension(R.dimen.padding_top)), 0, Utils.dip2Pixel(Utils
                    .getDimension(R.dimen.padding_right)));
        }


        public void bind(int position) {
            itemCategory = visibleItems.get(position).itemCategory;
            tvName.setText(itemCategory.getName());
            cbCategory.setChecked(itemCategory.getSelected());
        }

        @OnClick({R.id.rlCategory, R.id.cbCategory})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rlCategory:
                    cbCategory.setChecked(!cbCategory.isChecked());
                case R.id.cbCategory:
                    itemCategory.setSelected(cbCategory.isChecked());
//                    handleSelected(false, itemCategory);
                    if(listener != null){
                        listener.onEventControl(Constants.ActionEvent.ACTION_CHOOSE_CATEGORY, null, null, 0);
                    }
                    break;
            }
        }
    }

    /**
     * Category
     */
    public static class CategoryItem extends ExpandableRecyclerAdapter.ListItem {
        private CategoryItemDTO headerCategory; //Header category
        private CategoryItemDTO itemCategory; //Category

        public CategoryItem(boolean isHeader, CategoryItemDTO headerCategory) {
            super(TYPE_HEADER);
            this.headerCategory = headerCategory;
        }

        public CategoryItem(CategoryItemDTO itemCategory) {
            super(TYPE_ITEM_CATEGORY);
            this.itemCategory = itemCategory;
        }
    }

    /**
     * Handle category
     *
     * @param categoryItems
     */
    private void getCategoryItems(List<CategoryItemDTO> categoryItems) {
        for (CategoryItemDTO item : categoryItems) {
            if (item.getParentCategoryId() == 0) {
                items.add(new CategoryItem(true, item));
            } else {
                items.add(new CategoryItem(item));
            }
        }
    }

    /**
     * Update choose category
     * @param isChoose
     */
    public void updateChooseCategory(boolean isChoose) {
        if (!isChoose) {
            for (CategoryItemDTO item : categoryLocationItems) {
                boolean isExisted = false;
                for (Long id : categoryIds) {
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
            categoryIds.clear();
            for (CategoryItemDTO item : categoryLocationItems) {
                if (item.getSelected()) {
                    categoryIds.add(item.getId());
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Handle selected
     */
    private void handleSelected(boolean isHeader, CategoryItemDTO itemChoose) {
        if (isHeader) {
            //Update child
            for (CategoryItemDTO item : categoryLocationItems) {
                if (item.getParentCategoryId() == itemChoose.getId()) {
                    item.setSelected(itemChoose.getSelected());
                }
            }
        } else {
            boolean checkAll = true;
            for (CategoryItemDTO item : categoryLocationItems) {
                if (itemChoose.getParentCategoryId() == item.getParentCategoryId()) {
                    if (!item.getSelected()) {
                        checkAll = false;
                        break;
                    }
                }
            }
            //Update parent
            for (CategoryItemDTO item : categoryLocationItems) {
                if (itemChoose.getParentCategoryId() == item.getId()) {
                    item.setSelected(checkAll);
                }
            }
        }
//        notifyDataSetChanged();
    }



}
