package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.TourItineraryItemDTO;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.location.LocationDetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TourPlaceActivity extends BaseActivity {

    @Bind(R.id.ivBack)
    ImageView ivBack; // back
    @Bind(R.id.ivLocation)
    ImageView ivLocation; // back
    @Bind(R.id.tvTour)
    TextView tvTour; // textview Tour
    @Bind(R.id.fabDirection)
    FloatingActionButton fabDirection; // thong tin
    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;
    @Bind(R.id.tvTourPlace)
    TextView tvTourPlace; // dia diem cua mot tour
    @Bind(R.id.ivPhoto)
    ImageView ivPhoto; // photo
    String tourName; // Tour Name
    //false -> fab = close
    //true -> fab = open
    private TourItineraryItemDTO tourItineraryItemDTO = new TourItineraryItemDTO(); // thong tin tung place cua tour
    private long tourId; // tourId

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_tour_place);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.fabDirection, R.id.ivBack, R.id.tvTourPlace, R.id.ivPhoto, R.id.ivLocation})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fabDirection:
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, tourItineraryItemDTO.getLocation());
                goNextScreen(TourPlaceDirectionActivity.class, bundle);
                break;
            case R.id.ivBack:
                super.onBackPressed();
                break;
            case R.id.ivLocation:
                Bundle b = new Bundle();
                b.putSerializable(Constants.IntentExtra.PLACE_ITEM, tourItineraryItemDTO.getLocation());
                goNextScreen(LocationDetailActivity.class, b);
                break;
            case R.id.ivPhoto:
                Bundle data = new Bundle();
                data.putString(Constants.IntentExtra.TITLE, StringUtil.getString(R.string.text_title_photo_itinerary));
                data.putLong(Constants.IntentExtra.TOUR_ID, tourId);
                data.putLong(Constants.IntentExtra.TOUR_PLACE_ID, tourItineraryItemDTO.getLocation().getId());
                goNextScreen(TourPictureActivity.class, data);
                break;
        }
    }

    /**
     * Init view
     */
    private void initView() {
        if (getIntent() != null) {
            tourItineraryItemDTO = getIntent().getExtras().getParcelable(Constants.IntentExtra.TOUR_ITINERARY);
            tourName = getIntent().getStringExtra(Constants.IntentExtra.TOUR_NAME);
            tourId = getIntent().getLongExtra(Constants.IntentExtra.TOUR_ID, 0);
        }
        if (tourItineraryItemDTO != null) {
            tvTour.setText(tourName);
            tvTourPlace.setText(tourItineraryItemDTO.getLocation().getName());
            if (LocationTypeDTO.GOOGLE.getValue() == tourItineraryItemDTO.getLocation().getLocationType()) {
                ImageUtil.loadImage(ivPhoto, ImageUtil.getGoogleImageUrl(tourItineraryItemDTO.getLocation().getPhoto()));
            } else {
                ImageUtil.loadImage(ivPhoto, ImageUtil.getImageUrl(tourItineraryItemDTO.getLocation().getPhoto()));
            }
//            switchFragment(TourPlaceFragment.newInstance(bundle), R.id.frDetail, false);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentExtra.TOUR_ITINERARY, tourItineraryItemDTO);
        bundle.putLong(Constants.IntentExtra.TOUR_ID, tourId);
        switchFragment(TourPlaceInfoFragment.newInstance(bundle),R.id.frDetail,false);
    }
}

