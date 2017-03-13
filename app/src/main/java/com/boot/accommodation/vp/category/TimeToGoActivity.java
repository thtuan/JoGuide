package com.boot.accommodation.vp.category;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.TimeToGoFilterDTO;
import com.boot.accommodation.dto.view.SortTypeDTO;
import com.boot.accommodation.listener.OnEditTextControlListener;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.location.LocationListFragment;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Time to go places
 *
 * @author tuanlt
 * @since 10:39 AM 9/22/2016
 */
public class TimeToGoActivity extends BaseActivity implements TimeToGoViewMgr {

    AlertDialog alertDialogBestTime; //Alert dialog best time
    @Bind(R.id.llFilter)
    LinearLayout llFilter;
    @Bind(R.id.edSearch)
    JoCoEditText edSearch;
    @Bind(R.id.frDetail)
    FrameLayout frDetail;
    @Bind(R.id.tvTitleLocationShouldGo)
    TextView tvTitleLocationShouldGo;
    @Bind(R.id.rdRandom)
    RadioButton rdRandom;
    @Bind(R.id.rdDistance)
    RadioButton rdDistance;
    @Bind(R.id.ivUpDistance)
    ImageView ivUpDistance;
    @Bind(R.id.rdLike)
    RadioButton rdLike;
    private ListAreaAdapter bestTimeAdapter; //Best time adapter
    private TimeToGoPresenterMgr timeToGoPresenterMgr; //Time to go presenter
    private MonthAdapter monthAdapter; //Month
    private TimeToGoFilterDTO timeToGoFilterDTO = new TimeToGoFilterDTO(); //Time to go filter
    private LocationListFragment placeListFragment; //Place list fragment
    private List<CategoryItemDTO> months; //List month
    private String textMonthChoose = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_to_go);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * Init view
     */
    private void initView() {
        updateMonthChoose();
        enableSlidingMenu(false);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        Utils.hideKeyboardInput(this, edSearch);
        edSearch.setImageSearchVisible(R.drawable.ic_calendar_green, true);
        edSearch.setImageClearVisibile(R.drawable.ic_clear_grey, true);
        edSearch.setListener(new OnEditTextControlListener() {
            @Override
            public void onTouchControl() {
                if (monthAdapter == null) {
                    showProgressDialog(true);
                    timeToGoPresenterMgr.getMonth();
                } else {
                    edSearch.showDropDown();
                }
            }

            @Override
            public void onClear() {
            }
        });
        edSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.hideKeyboardInput(TimeToGoActivity.this, edSearch);
                getTimeToGo();
            }
        });
        edSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "search" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Utils.hideKeyboardInput(TimeToGoActivity.this, edSearch);
                    getTimeToGo();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Init data
     */
    private void initData() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IntentExtra.FROM_TIME_TO_GO, true);
        placeListFragment = LocationListFragment.newInstance(bundle);
        switchFragment(placeListFragment, R.id.frDetail, true);
        timeToGoPresenterMgr = new TimeToGoPresenter(this);
        timeToGoPresenterMgr.getMonth();
    }

    @OnClick({R.id.ivMenu, R.id.ivFilter, R.id.rdDistance, R.id.rdRandom, R.id.rdLike, R.id.ivUpDistance})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMenu:
                getSlidingMenu().showMenu(true);
                Utils.hideSoftKeyboard(this);
                break;
            case R.id.ivFilter:
                if (bestTimeAdapter == null) {
                    showProgressDialog(true);
                    timeToGoPresenterMgr.getAreaFilter();
                } else {
                    showPopupArea(null);
                }
                break;
            case R.id.rdRandom:
                timeToGoFilterDTO.setSortType(SortTypeDTO.RANDOM.getValue());
                getTimeToGo();
                break;
            case R.id.rdLike:
                timeToGoFilterDTO.setSortType(SortTypeDTO.FAVOURITE.getValue());
                getTimeToGo();
                break;
            case R.id.ivUpDistance:
            case R.id.rdDistance:
                if (Utils.validateMyLocation(frDetail)) {
                    timeToGoFilterDTO.setSortType(SortTypeDTO.DISTANCE.getValue());
                    getTimeToGo();
                } else {
                    rdDistance.setChecked(false);
                    updateChooseSort();
                }
                break;
        }
    }

    /**
     * Show popup best time
     */
    private void showPopupArea(final List<AreaDTO> areaDTOs) {
        if (alertDialogBestTime == null) {
            final Context context = this;
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.popup_area, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptsView);
            final RecyclerView recyclerView = (RecyclerView) promptsView.findViewById(R.id.rvListArea);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            final JoCoEditText etSearch = (JoCoEditText) promptsView.findViewById(R.id.etSearch);
            etSearch.setImageClearVisibile(R.drawable.ic_clear_grey, true);
            etSearch.setImageSearchVisible(R.drawable.ic_search_green, true);
            handleSearch(etSearch);
            //render list category
            if (bestTimeAdapter == null) {
                bestTimeAdapter = new ListAreaAdapter(areaDTOs);
                bestTimeAdapter.setListener(this);
                recyclerView.setAdapter(bestTimeAdapter);
            }
            // set dialog message
            alertDialogBuilder.setCancelable(false).setPositiveButton(StringUtil.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    StringBuilder stringName = new StringBuilder();
                    stringName.setLength(0);
                    timeToGoFilterDTO.setAreaIds(bestTimeAdapter.getAreaIdsChoose());
                    getTimeToGo();
                }
            }).setNegativeButton(StringUtil.getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    bestTimeAdapter.cancelChoose();
                }
            });
            // create alert dialog
            alertDialogBestTime = alertDialogBuilder.create();
            alertDialogBestTime.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    etSearch.setText(Constants.STR_BLANK);
                }
            });
        }
        alertDialogBestTime.show();
    }

    @Override
    public void renderLayoutMonth(List<CategoryItemDTO> month) {
        this.months = month;
        closeProgressDialog();
        if (monthAdapter == null) {
            monthAdapter = new MonthAdapter(this, android.R.layout.simple_spinner_item, month);
            edSearch.setAdapter(monthAdapter);
        }
//        edSearch.showDropDown();
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgressDialog();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void renderLayoutLocationFilter(List<AreaDTO> areaDTOs) {
        closeProgressDialog();
        showPopupArea(areaDTOs);
    }

    /**
     * Handle event search
     *
     * @param etSearch
     */
    private void handleSearch(JoCoEditText etSearch) {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bestTimeAdapter.updateSearchArea(s.toString());
            }
        });

    }

    /**
     * Check data existed
     */
    private void getDataFilter() {
        String searchText = edSearch.getText().toString().trim();
        String monthIds = "";
        textMonthChoose = "";
        if (months != null) {
            for (CategoryItemDTO item : months) {
                String temp1 = StringUtil.getEngStringFromUnicodeString(item.getName());
                String temp2 = StringUtil.getEngStringFromUnicodeString(searchText);
                if (temp1.toLowerCase().equals(temp2.toString().toLowerCase())) {
                    // Code if using choose multi month
                    if (StringUtil.isNullOrEmpty(monthIds)) {
                        monthIds = item.getId() + "";
                        textMonthChoose = item.getName();
                    } else {
                        monthIds += Constants.STR_TOKEN + item.getId();
                        textMonthChoose += Constants.STR_TOKEN + item.getName();
                    }
                }
            }
        }
        if (!StringUtil.isNullOrEmpty(monthIds)) {
            timeToGoFilterDTO.setMonthIds(monthIds);
            timeToGoFilterDTO.setSearchText(Constants.STR_BLANK);
        } else {
            timeToGoFilterDTO.setSearchText(searchText);
            timeToGoFilterDTO.setMonthIds(Constants.STR_BLANK);
        }
    }

    /**
     * Get month choose
     */
    private void updateMonthChoose() {
        if (StringUtil.isNullOrEmpty(textMonthChoose)) {
            Calendar c = Calendar.getInstance();
            int month = c.get(Calendar.MONTH) + 1;
            textMonthChoose = StringUtil.getString(R.string.text_month) + Constants.STR_SPACE + month;
        }
        String month = String.format(StringUtil.getString(R.string.text_location_should_go), textMonthChoose);
        tvTitleLocationShouldGo.setText(month);
    }

    /**
     * Get time to go
     */
    private void getTimeToGo() {
        getDataFilter();
        updateMonthChoose();
        updateChooseSort();
        placeListFragment.getTimeToGo(timeToGoFilterDTO);
    }

    /**
     * Update choose sort
     */
    private void updateChooseSort() {
        rdRandom.setTextColor(Utils.getColor(R.color.text_color));
        rdLike.setTextColor(Utils.getColor(R.color.text_color));
        rdDistance.setTextColor(Utils.getColor(R.color.text_color));
        ivUpDistance.setImageResource(R.drawable.ic_down_grey);
        rdLike.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_down_grey,0);
        if (SortTypeDTO.RANDOM.getValue() == timeToGoFilterDTO.getSortType()) {
            rdRandom.setTextColor(Utils.getColor(R.color.primary_500));
            rdRandom.setChecked(true);
        } else if (SortTypeDTO.DISTANCE.getValue() == timeToGoFilterDTO.getSortType()) {
            rdDistance.setTextColor(Utils.getColor(R.color.primary_500));
            ivUpDistance.setImageResource(R.drawable.ic_down_green);
            rdDistance.setChecked(true);
        } else {
            rdLike.setTextColor(Utils.getColor(R.color.primary_500));
            rdLike.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_down_green,0);
            rdLike.setChecked(true);
        }
    }
}
