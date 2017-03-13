package com.boot.accommodation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.R;
import com.boot.accommodation.common.control.TouchImageView;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.PhotoDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xdung on 5/25/2016.
 */
public class FullImageDialog extends DialogFragment implements OnEventControl, View.OnClickListener {

    public static final int ACTION_SET_PRIMARY_ITEM = 101;
    private Context mContext;
    private ViewPager vpImage;
    private PhotoDTO item; // photo item
    private List<PhotoDTO> listItem; // photo item
    private int position; // vi tri photo click
    private String urlPhoto;// duong dan hinh anh, danh cho hinh anh view binh thuong
    private boolean isShowInfo = false; //Variable check show info or no

    /**
     * set data
     *
     * @param context
     * @param item
     * @param position
     */
    public void setData(Context context, PhotoDTO item, int position) {
        this.mContext = context;
        this.item = item;
        this.position = position;
    }

    /**
     * set data
     *
     * @param context
     * @param listItem
     * @param position
     */
    public void setData(Context context, List<PhotoDTO> listItem, int position) {
        this.mContext = context;
        this.listItem = listItem;
        this.position = position;
    }

    /**
     * set data
     *
     * @param context
     * @param urlPhoto
     */
    public void setData(Context context, String urlPhoto) {
        this.mContext = context;
        this.urlPhoto = urlPhoto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_full_image_view, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initView(view);
//        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Khoi tao view
     *
     * @param v
     */
    private void initView(View v) {
//        llDetail = (LinearLayout) v.findViewById(R.id.llDetail);
        vpImage = (ViewPager) v.findViewById(R.id.vpImage);
//        ivPlaceDetailUserAvatar = (CircularImageView) v.findViewById(R.id.ivPlaceDetailUserAvatar);
//        tvCreateUser = (TextView) v.findViewById(R.id.tvCreateUser);
//        tvComment = (TextView) v.findViewById(R.id.tvComment);
//        tvCreateDate = (TextView) v.findViewById(R.id.tvCreateDate);
        if (item != null) {
            renderLayoutItem();
        } else if (listItem != null) {
            renderLayoutListItem();
        } else {
            renderLayoutSingleItem();
        }
    }

    /**
     * render layout dang item
     */
    private void renderLayoutItem() {
        FullImagePagerAdapter adapter = new FullImagePagerAdapter(mContext, this, item);
        vpImage.setAdapter(adapter);
//        updateInfo(item, 0);
    }

    /**
     * Render mot hinh anh dang String
     */
    private void renderLayoutSingleItem() {
        FullImagePagerAdapter adapter = new FullImagePagerAdapter(mContext, this, urlPhoto);
        vpImage.setAdapter(adapter);
    }

    /**
     * render slide image
     */
    private void renderLayoutListItem() {
        FullImagePagerAdapter adapter = new FullImagePagerAdapter(mContext, this, listItem);
        vpImage.setAdapter(adapter);
        vpImage.setCurrentItem(position);
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawableResource(R.color.black_trans40);
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
//        if (action == ACTION_SET_PRIMARY_ITEM) {
//            updateInfo(iposition);
//        }
    }

    @Override
    public void onLoadMore(int position) {

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public boolean isShowInfo() {
        return isShowInfo;
    }

    public void setShowInfo(boolean showInfo) {
        isShowInfo = showInfo;
    }

    public class FullImagePagerAdapter extends PagerAdapter {

        @Bind(R.id.ivPhoto)
        TouchImageView ivPhoto;
        @Bind(R.id.prLoading)
        ProgressBar prLoading;
        @Bind(R.id.ivPlaceDetailUserAvatar)
        CircularImageView ivPlaceDetailUserAvatar;
        @Bind(R.id.tvCreateUser)
        TextView tvCreateUser;
        @Bind(R.id.tvComment)
        TextView tvComment;
        @Bind(R.id.tvCreateDate)
        TextView tvCreateDate;
        @Bind(R.id.llDetail)
        LinearLayout llDetail;
        private Context context;
        private OnEventControl mListener;
        private String urlPhoto;
        private List<PhotoDTO> photoListl;
        private PhotoDTO mListPhoto;

        public FullImagePagerAdapter(Context context, OnEventControl listener, PhotoDTO listPhoto) {
            this.context = context;
            this.mListener = listener;
            this.mListPhoto = listPhoto;
        }

        public FullImagePagerAdapter(Context context, OnEventControl listener, String urlPhoto) {
            this.context = context;
            this.mListener = listener;
            this.urlPhoto = urlPhoto;
        }

        public FullImagePagerAdapter(Context context, OnEventControl listener, List<PhotoDTO> lists) {
            this.context = context;
            this.mListener = listener;
            this.photoListl = lists;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(context);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_full_image, collection, false);
            ButterKnife.bind(this, layout);
            if (mListPhoto != null) {
                if (LocationTypeDTO.GOOGLE.getValue() == item.getLocationType()) {
                    ImageUtil.loadImage(ivPhoto, ImageUtil.getGoogleImageUrl(item.getUrl(), Constants.MAX_FULL_IMAGE_HEIGHT_ZOOM));
                } else {
                    ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(item.getUrl(), Constants.MAX_FULL_IMAGE_HEIGHT_ZOOM));
                }
            } else if (listItem != null) {
                updateInfo(listItem.get(position), position);
                if (LocationTypeDTO.GOOGLE.getValue() == listItem.get(position).getLocationType()) {
                    ImageUtil.loadImage(ivPhoto, ImageUtil.getGoogleImageUrl(listItem.get(position).getUrl(),
                            Constants.MAX_FULL_IMAGE_HEIGHT_ZOOM), prLoading);
                } else {
                    ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(listItem.get(position).getUrl(), Constants
                            .MAX_FULL_IMAGE_HEIGHT_ZOOM), prLoading);
                }
            } else {
                ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(urlPhoto, Constants.MAX_FULL_IMAGE_HEIGHT_ZOOM));
            }
            collection.addView(layout);
            return layout;
        }

        /**
         * Update info
         *
         * @param photoItem
         * @param position
         */
        private void updateInfo(PhotoDTO photoItem, int position) {
//            if (position == 0 || this.position != position) {
//                this.position = position;

                if (isShowInfo) {
                    llDetail.setVisibility(View.VISIBLE);
                    if (TextUtils.isEmpty(photoItem.createUserAvatar)) {
                        ivPlaceDetailUserAvatar.setVisibility(View.GONE);
                    } else {
                        ivPlaceDetailUserAvatar.setVisibility(View.VISIBLE);
                        ImageUtil.loadImage(ivPlaceDetailUserAvatar, ImageUtil.getImageUrl(photoItem.createUserAvatar));
                    }
                    if (!TextUtils.isEmpty(photoItem.createUser)) {
                        tvCreateUser.setVisibility(View.VISIBLE);
                        tvCreateUser.setText(StringUtil.getString(R.string.text_full_image_create_by) + " " + photoItem.createUser);
                    } else {
                        tvCreateUser.setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(photoItem.comment)) {
                        tvComment.setVisibility(View.VISIBLE);
                        tvComment.setText("\" " + photoItem.comment + "\"");
                    } else {
                        tvComment.setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(photoItem.createDate)) {
                        tvCreateDate.setVisibility(View.VISIBLE);
                        tvCreateDate.setText(StringUtil.getString(R.string.text_full_image_create_date) + " " +
                                DateUtil.convertDateWithFormat(photoItem.createDate, DateUtil.FORMAT_DATE_TIME_ZONE, DateUtil.FORMAT_DATE_TIME));
                    } else {
                        tvCreateDate.setVisibility(View.GONE);
                    }
                } else {
                    ivPlaceDetailUserAvatar.setVisibility(View.GONE);
                    llDetail.setVisibility(View.GONE);
                }
//            }
        }

        @Override
        public int getCount() {
            return listItem != null ? listItem.size() : 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            ((ViewPager) container).removeView((ViewGroup) object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
//            if (mListPhoto != null) {
//                mListener.onEventControl(ACTION_SET_PRIMARY_ITEM, null, null, position);
//            }
        }
    }
}
