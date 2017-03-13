package com.boot.accommodation.vp.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseAdapter;
import com.boot.accommodation.base.BaseHolder;
import com.boot.accommodation.dto.view.LeftMenuItemDTO;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Admin on 12/10/2015.
 */
public class LeftMenuAdapter extends BaseAdapter<LeftMenuItemDTO, LeftMenuAdapter.ViewHolder> {

    public LeftMenuAdapter(List<LeftMenuItemDTO> items) {
        super(items);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_left_menu;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindData(ViewHolder holder, LeftMenuItemDTO data, int position) {
        holder.bind(data);
    }

    static class ViewHolder extends BaseHolder {

        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            super(view);
        }

        public void bind(final LeftMenuItemDTO item) {
            ivAvatar.setImageDrawable(item.avatar);
            tvName.setText(item.name);
        }
    }

}
