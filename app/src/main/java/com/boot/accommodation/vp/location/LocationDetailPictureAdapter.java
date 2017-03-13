package com.boot.accommodation.vp.location;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.PhotoDTO;
import com.boot.accommodation.util.ImageUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Adapter picture
 *
 * @author tuanlt
 * @since 9:51 AM 7/9/2016
 */
public class LocationDetailPictureAdapter extends BaseRecyclerViewAdapter<PhotoDTO, LocationDetailPictureAdapter.ViewHolder> {


    public LocationDetailPictureAdapter(List<PhotoDTO> lstPhoto) {
        super(lstPhoto);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_place_detail_picture;
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.setData(items.get(position), position);
    }

    public class ViewHolder extends BaseRecyclerViewHolder implements View.OnClickListener {
        ImageView ivPlaceDetailPictureItem;
        @Bind(R.id.prLoading)
        ProgressBar progressLoad;
        @Bind(R.id.ivDelete)
        ImageView ivDelete;
        @Bind(R.id.prLoadingUpload)
        ProgressBar prLoadingUpload;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPlaceDetailPictureItem = (ImageView) itemView.findViewById(R.id.ivPlaceDetailPictureItem);
            ivPlaceDetailPictureItem.setOnClickListener(this);
        }

        public void setData(PhotoDTO photoDTO, int position) {
            this.data = photoDTO;
            this.position = position;
            if (LocationTypeDTO.GOOGLE.getValue() == photoDTO.getLocationType()) {
                ImageUtil.loadImage(ivPlaceDetailPictureItem, ImageUtil.getGoogleImageUrlThumb(photoDTO.url), progressLoad);
            } else {
                ImageUtil.loadImage(ivPlaceDetailPictureItem, ImageUtil.getImageUrlThumb(photoDTO.url), progressLoad);
            }
            if ((photoDTO.getProgressPercentage() == 100)) {
                prLoadingUpload.setVisibility(View.GONE);
            } else {
                prLoadingUpload.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onEventControl(LocationDetailPictureFragment.ACTION_FULL_IMAGE_VIEW, data, null, position);
            }
        }
    }

}
