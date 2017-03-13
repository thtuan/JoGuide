package com.boot.accommodation.vp.tour;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.FollowItemDTO;
import com.boot.accommodation.util.ImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Adapter for profile user
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class ProfileUserAdapter extends BaseRecyclerViewAdapter<FollowItemDTO, ProfileUserAdapter.ViewHolder> {

    public ProfileUserAdapter(List<FollowItemDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_followers;
    }

    @Override
    protected void bindViewHoder( ViewHolder holder, int position ) {
        holder.bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.ivImage)
        CircularImageView ivImage;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.ivAddFriend)
        ImageView ivAddFriend;
        @Bind(R.id.tvPhone)
        TextView tvPhone;
        @Bind(R.id.tvId)
        TextView tvId;
        @Bind(R.id.rlGoProfile)
        RelativeLayout rlGoProfile;
        ViewHolder(View view) {
            super(view);
        }

        public void bindData( FollowItemDTO data, int position) {
            this.data = data;
            this.position = position;
            //set data for leader
            ImageUtil.loadImage(ivImage, ImageUtil.getImageUrl(data.getImage()));
//            tvId.setText(data.getId());
            tvName.setText(data.getName());
            if (!String.valueOf(data.getId()).equals(JoCoApplication.getInstance().getProfile().getUserData().getId())){
                if (!data.isFriend()){
                    ivAddFriend.setImageResource(R.drawable.ic_add_user_green);
                } else {
                    ivAddFriend.setImageResource(R.drawable.ic_unfriend_green);
                }
            }
        }

        @OnClick({R.id.rlGoProfile, R.id.ivAddFriend})
        public void OnClick(View v) {
            int action = 0;
            switch (v.getId()) {
                case R.id.rlGoProfile:
                    //view leader info
                    action = FollowActivity.ACTION_VIEW_TOURIST_INFO;
                    break;
                case R.id.ivAddFriend:
                    //view leader info
                    action = FollowActivity.ACTION_ADD_FRIEND;
                    break;
            }
            if (listener != null && data != null) {
                listener.onEventControl(action, data, view, position);
            }
        }
    }

}