package com.boot.accommodation.vp.location;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dialog.FullImageDialog;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.PhotoDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xdung on 3/8/2016.
 */
public class LocationDetailPictureFragment extends BaseFragment implements LocationDetailPictureViewMgr {

    @Bind(R.id.rvOwnerList)
    RecyclerView rvOwnerList;
    @Bind(R.id.rvPacker)
    RecyclerView rvPacker;
    @Bind(R.id.tvOwnerList)
    TextView tvOwnerList;
    @Bind(R.id.tvPacker)
    TextView tvPacker;
    @Bind(R.id.tvNoData)
    TextView tvNoData;
    LocationDetailPictureAdapter adapterOwner; // grid image owner
    LocationDetailPictureAdapter adapterJoco; // grid adapter joco
    public static final int ACTION_FULL_IMAGE_VIEW = 101;
    LocationDetailPhotosPresenterMgr placeDetailPicturePresenter;
    PlaceItemDTO placeItemDTO; // place item
    List<PhotoDTO> ownerList = new ArrayList<>();//list photo of owner
    List<PhotoDTO> jocoList = new ArrayList<>();//list photo of joco

    @Override
    public int contentViewLayout() {
        return R.layout.activity_place_detail_picture_fragment;
    }

    public static Fragment newInstance(Bundle data) {
        LocationDetailPictureFragment fragment = new LocationDetailPictureFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableRefresh(true);

        Bundle b = getArguments();
        if (b != null) {
            placeItemDTO = b.getParcelable(Constants.IntentExtra.PLACE_ITEM);
            placeDetailPicturePresenter = new LocationDetailPhotosPresenter(this);
            placeDetailPicturePresenter.getPlacePhoto(placeItemDTO.getId());
        }

        GridLayoutManager gridLayoutManagerVertical =
                new GridLayoutManager(mActivity, 3,
                        LinearLayoutManager.VERTICAL, false);
        rvOwnerList.setLayoutManager(gridLayoutManagerVertical);
        rvOwnerList.addItemDecoration(new GridSpacingItemDecoration(3, 16, true));

        GridLayoutManager gridLayoutManagerVertical1 =
                new GridLayoutManager(mActivity, 3,
                        LinearLayoutManager.VERTICAL, false);
        rvPacker.setLayoutManager(gridLayoutManagerVertical1);
        rvPacker.addItemDecoration(new GridSpacingItemDecoration(3, 16, true));
    }

    @Override
    public void renderPhotoOwner(List<PhotoDTO> photo) {
        this.ownerList = photo;
        if (photo != null && photo.size() > 0) {
            tvOwnerList.setVisibility(View.VISIBLE);
            rvOwnerList.setVisibility(View.VISIBLE);
        } else {
            tvOwnerList.setVisibility(View.GONE);
            rvOwnerList.setVisibility(View.GONE);
        }
        if (adapterOwner == null) {
            adapterOwner = new LocationDetailPictureAdapter(photo);
            adapterOwner.setListener(new OnEventControl() {
                @Override
                public void onEventControl(int action, Object item, View View, int position) {
                    handleShowPhoto(ownerList, position, true);
                }

                @Override
                public void onLoadMore(int position) {
                    placeDetailPicturePresenter.getMorePlacePhotoOwner(placeItemDTO.getId(), adapterOwner);
                }
            });
            adapterOwner.setEnableLoadMore(true);
            rvOwnerList.setAdapter(adapterOwner);
        } else {
            adapterOwner.setData(photo);
        }
        stopRefresh();
        adapterOwner.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.ACTION_UPDATE_INFO_LOCATION:
            case Constants.ActionEvent.NOTIFY_REFRESH:
                placeDetailPicturePresenter.getPlacePhoto(placeItemDTO.getId());
                break;
            case Constants.ActionEvent.ACTION_UPLOAD_PICTURE:
                ImageUtil.selectImage(mActivity, this);
                break;
        }
    }

    @Override
    public void renderPhotoJoco(final List<PhotoDTO> photo) {
        this.jocoList = photo;
        if (photo != null && photo.size() > 0) {
            tvPacker.setVisibility(View.VISIBLE);
            rvPacker.setVisibility(View.VISIBLE);
        } else {
            tvPacker.setVisibility(View.GONE);
            rvPacker.setVisibility(View.GONE);
        }
        if (adapterJoco == null) {
            adapterJoco = new LocationDetailPictureAdapter(photo);
            adapterJoco.setListener(new OnEventControl() {
                @Override
                public void onEventControl(int action, Object item, View View, int position) {
                    handleShowPhoto(jocoList, position, false);
                }

                @Override
                public void onLoadMore(int position) {
                    placeDetailPicturePresenter.getMorePlacePhotoJoco(placeItemDTO.getId(), adapterJoco);
                }
            });
            adapterJoco.setEnableLoadMore(true);
            rvPacker.setAdapter(adapterJoco);
        } else {
            adapterJoco.setData(photo);
        }
        adapterJoco.notifyDataSetChanged();
        stopRefresh();
    }

    @Override
    public void renderNoData(boolean isShow) {
        tvNoData.setVisibility(!isShow ? View.GONE : View.VISIBLE);
    }

    /**
     * Handle show photo
     *
     * @param item
     * @param position
     * @param showInfo
     */
    private void handleShowPhoto(List<PhotoDTO> item, int position, boolean showInfo) {
        // show full image view
        showFullImageView(item, position, showInfo);
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        stopRefresh();
        closeProgress();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void finishProcessDialog() {
    }

    /**
     * Show full hinh anh
     */
    private void showFullImageView(List<PhotoDTO> item, int position, boolean showInfo) {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FullImageDialog fullImageDialog = new FullImageDialog();
        fullImageDialog.setData(mActivity, item, position);
        fullImageDialog.setShowInfo(showInfo);
        fullImageDialog.show(fm, "fragment_help");
    }

    @Override
    protected void updatePhoto(String fileTaken) {
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.createDate = DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE);
        photoDTO.createUser = JoCoApplication.getInstance().getProfile().getUserData().getId();
        photoDTO.setProgressPercentage(0);
        photoDTO.setLocationType(LocationTypeDTO.JOCO.getValue());
        placeDetailPicturePresenter.requestUpLoadPhotoToStatic(placeItemDTO.getId(), mActivity.takenPhoto
                .getAbsoluteFile(), photoDTO);
    }

}
