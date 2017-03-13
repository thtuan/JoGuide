package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dialog.FullImageDialog;
import com.boot.accommodation.dto.view.PhotoDTO;
import com.boot.accommodation.vp.location.GridSpacingItemDecoration;
import com.boot.accommodation.vp.location.LocationDetailPictureAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Fragment thong tin chi tiet dia diem cua tour
 *
 * @author tuanlt
 * @since 3:05 PM 5/26/2016
 */
public class TourPictureFragment extends BaseFragment implements TourPlacePictureViewMgr {

    @Bind(R.id.rvPicture)
    RecyclerView rvPicture;
    private TourPlacePicturePresenterMgr tourPlacePicturePresenterMgr; // Tour place picture presenter
    private LocationDetailPictureAdapter placeDetailPictureAdapter; // grid image owner
    private long tourId; //Id tour
    private long placeId; // Id place
    List<PhotoDTO> listPhotos = new ArrayList<>();
    public static final int ACTION_FULL_IMAGE_VIEW = 101;// view hinh anh
    GridLayoutManager gridLayoutManagerVertical = null;

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_tour_place_picture;
    }

    public static TourPictureFragment newInstance( Bundle bundle ) {
        TourPictureFragment f = new TourPictureFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tourId = bundle.getLong(Constants.IntentExtra.TOUR_ID);
            placeId = bundle.getLong(Constants.IntentExtra.TOUR_PLACE_ID);
        }
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * Init data
     */
    private void initData() {
         gridLayoutManagerVertical =
                new GridLayoutManager(mActivity, 3,
                        LinearLayoutManager.VERTICAL, false);
        rvPicture.setLayoutManager(gridLayoutManagerVertical);
        rvPicture.setHasFixedSize(true);
        showProgress();
        tourPlacePicturePresenterMgr = new TourPlacePicturePresenter(this);
        tourPlacePicturePresenterMgr.getTourPlacePicture(tourId, placeId);
    }

    @Override
    public void renderLayout( List<PhotoDTO> photos ) {
        this.listPhotos = photos;
        closeProgress();
        stopRefresh();
        if(placeDetailPictureAdapter == null){
            placeDetailPictureAdapter = new LocationDetailPictureAdapter(photos);
            placeDetailPictureAdapter.setListener(this);
            placeDetailPictureAdapter.setEnableLoadMore(true);
            rvPicture.setAdapter(placeDetailPictureAdapter);
            if(photos == null || photos.size() == 0){
               // rvPicture.addItemDecoration(new GridSpacingItemDecoration(1, 0, true));
                gridLayoutManagerVertical.setSpanCount(1);
            }else{
                rvPicture.addItemDecoration(new GridSpacingItemDecoration(3, 16, true));
            }
        } else {
            placeDetailPictureAdapter.setData(photos);
        }
        placeDetailPictureAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessageErr( int errorCode, String error ) {
        stopRefresh();
        closeProgress();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    @Override
    public void onLoadMore( int position ) {
        tourPlacePicturePresenterMgr.getMoreTourPlacePicture(placeDetailPictureAdapter);
    }

    @Override
    protected void receiveBroadcast( int action, Bundle extras ) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                tourPlacePicturePresenterMgr.getTourPlacePicture(tourId, placeId);
                break;
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case ACTION_FULL_IMAGE_VIEW:
                android.support.v4.app.FragmentManager fm = mActivity.getSupportFragmentManager();
                FullImageDialog fullImageDialog = new FullImageDialog();
                fullImageDialog.setData(mActivity, listPhotos, position);
                fullImageDialog.show(fm, "fragment_help");
                break;
        }
    }
}
