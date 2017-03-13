package com.boot.accommodation.vp.tour;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.PickContactDTO;
import com.boot.accommodation.util.ImageUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Mo ta class
 *
 * @author thtuan
 * @since 12:17 AM 20-06-2016
 */
public class PickContactAdapter extends BaseRecyclerViewAdapter<PickContactDTO, PickContactAdapter.ViewHolder> {


    public PickContactAdapter(ArrayList<PickContactDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_pick_contact;
    }


    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.cbcheck)
        CheckBox cbcheck;
        @Bind(R.id.ivAvatar)
        CircularImageView ivAvatar;
        @Bind(R.id.tvName)
        CheckedTextView tvName;
        @Bind(R.id.tvAddress)
        TextView tvAddress;
        @Bind(R.id.llPick)
        LinearLayout llPick;

        @OnClick({R.id.llPick,R.id.cbcheck})
        public void onClick(View view) {
            int id = view.getId();
            if(id == R.id.llPick) {
                cbcheck.setChecked(!cbcheck.isChecked());
            }
        }

        public ViewHolder(View view) {
            super(view);
        }

        public void bindData(final PickContactDTO data, int position) {
            this.data = data;
            this.position = position;
            tvName.setText(data.getName());
            tvAddress.setText(data.getAddress());
            ImageUtil.loadImage(ivAvatar,data.getPhoto());
            cbcheck.setOnCheckedChangeListener(null);
            cbcheck.setChecked(data.isSend());
            cbcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.setSend(isChecked);
                }
            });
            //
            /*Glide.with(mContext).load(data.getPhoto())
                    .fitCenter()
                    .placeholder(R.drawable.img_default_error).crossFade()
                    .error(R.drawable.img_default_error).into(ivAvatar);*/
        }
    }


}
