package com.boot.accommodation.vp.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.dto.view.UploadPhotoDTO;
import com.boot.accommodation.util.ImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class UploadPhotoAdapter extends BaseRecyclerViewAdapter<UploadPhotoDTO, UploadPhotoAdapter.ViewHolder> {



    public UploadPhotoAdapter(List<UploadPhotoDTO> items) {
        super(items);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_upload_photo;
    }

    @Override
    public int getItemCount() {
        if (items != null && !items.isEmpty()) {
            return isEnableFooter ? items.size() + 1 : items.size();
        }else {
            return super.getItemCount();
        }
    }

    @Override
    protected void bindViewHoder(ViewHolder holder, int position) {
        holder.bindData(items.get(position), position);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.prLoading)
        ProgressBar prLoading;
        @Bind( R.id.ivDelete)
        ImageView  ivDelete;

        ViewHolder(View view) {
            super(view);
        }

        public void bindData(UploadPhotoDTO data, int position) {
            this.data = data;
            this.position = position;
            if (position < items.size()) {
                ivPhoto.setVisibility(View.VISIBLE);
                ImageUtil.loadImage(ivPhoto, data.getPath());
                prLoading.setProgress(data.getProgressPercentage());
                if ((data.getProgressPercentage() == 100)) {
                    prLoading.setVisibility(View.GONE);
                    ivDelete.setEnabled(true);
                } else {
                    prLoading.setVisibility(View.VISIBLE);
                    ivDelete.setEnabled(false);
                }
            }
        }

        @OnClick({R.id.ivPhoto, R.id.ivDelete})
        public void onClick(View view) {
            int action = 0;
            switch (view.getId()) {
                case R.id.ivPhoto:
//                    clickImage(position);
                    break;
                case R.id.ivDelete:
                    deleteImage(position);
                    break;
            }
        }
        /**
         * Su kien click hinh anh cua tour
         *
         */
        private void deleteImage(int positionImage) {
            if (listener != null && data != null) {
                listener.onEventControl(CreateLocationActivity.ACTION_DELETE_PHOTO, data, view, position);
            }
        }
        private void clickImage(int positionImage) {
            if (listener != null && data != null) {
                listener.onEventControl(CreateLocationActivity.ACTION_VIEW_DETAIL, data, view, position);
            }
        }
    }



}

