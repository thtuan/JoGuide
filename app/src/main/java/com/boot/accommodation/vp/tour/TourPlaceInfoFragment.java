package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TourItineraryItemDTO;
import com.boot.accommodation.dto.view.TourPlaceViewDTO;
import com.boot.accommodation.util.StringUtil;

import butterknife.Bind;

/**
 * Fragment thong tin chi tiet dia diem cua tour
 *
 * @author tuanlt
 * @since 3:05 PM 5/26/2016
 */
public class TourPlaceInfoFragment extends BaseFragment implements TourPlaceInfoViewMgr {

    @Bind(R.id.tvTittlePlan)
    TextView tvTittlePlan; // title ke hoach
    @Bind(R.id.tvPlan)
    TextView tvPlan; // ke hoach
    @Bind(R.id.tvTittleToDo)
    TextView tvTittleToDo; // title viec can lam
    @Bind(R.id.tvToDo)
    TextView tvToDo; // viec can lam
    @Bind(R.id.tvTittleTips)
    TextView tvTittleTips; // title tip
    @Bind(R.id.tvTips)
    TextView tvTips; // tip
    TourItineraryItemDTO tourItineraryItemDTO; // dto thong tin lich trinh tour
    TourPlacePresenterMgr tourPlacePresenterMgr;//presenter
    @Bind(R.id.llPlan)
    LinearLayout llPlan;
    @Bind(R.id.llToDo)
    LinearLayout llToDo;
    @Bind(R.id.llTip)
    LinearLayout llTip;

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_tour_place;
    }

    public static TourPlaceInfoFragment newInstance(Bundle bundle) {
        TourPlaceInfoFragment f = new TourPlaceInfoFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tourItineraryItemDTO = bundle.getParcelable(Constants.IntentExtra.TOUR_ITINERARY);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        initData();
        enableRefresh(false);
    }

    /**
     * Khoi tao data
     */
    private void initData() {
        tourPlacePresenterMgr = new TourPlacePresenter(this);
        tourPlacePresenterMgr.getTimeTour(tourItineraryItemDTO);
        renderLayout(new TourPlaceViewDTO());
    }

    @Override
    public void setTimeTour(StringBuilder time) {
        tvTittlePlan.setText(time);
    }

    @Override
    public void renderLayout(TourPlaceViewDTO tourPlaceViewDTO) {
        closeProgress();
        tvPlan.setText(tourItineraryItemDTO.getDescription());
        if (!StringUtil.isNullOrEmpty(tourItineraryItemDTO.getTodo())) {
            llToDo.setVisibility(View.VISIBLE);
            tvToDo.setText(tourItineraryItemDTO.getTodo());
        } else {
            llToDo.setVisibility(View.GONE);
        }
        if (!StringUtil.isNullOrEmpty(tourItineraryItemDTO.getTip())) {
            llTip.setVisibility(View.VISIBLE);
            tvTips.setText(tourItineraryItemDTO.getTip());
        } else {
            llTip.setVisibility(View.GONE);
        }
        if (!StringUtil.isNullOrEmpty(tourItineraryItemDTO.getDescription())) {
            llPlan.setVisibility(View.VISIBLE);
            tvPlan.setText(tourItineraryItemDTO.getDescription());
        } else {
            llPlan.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

}
