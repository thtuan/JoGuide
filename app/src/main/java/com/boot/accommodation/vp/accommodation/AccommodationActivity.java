package com.boot.accommodation.vp.accommodation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.FeedbackItemDTO;
import com.boot.accommodation.dto.view.LocationCategoryTypeDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.listener.OnEditTextControlListener;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.DeleteFileUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.home.AreaAdapter;
import com.boot.accommodation.vp.location.LocationServiceActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Favourite user
 *
 * @author tuanlt
 * @since 5:10 PM 12/12/16
 */
public class AccommodationActivity extends BaseActivity implements AccommodationViewMgr {

    @Bind(R.id.etArea)
    JoCoEditText etArea;
    @Bind(R.id.llMain)
    LinearLayout llMain;
    @Bind(R.id.rvArea)
    RecyclerView rvArea;
//    @Bind(R.id.ivTransparent)
//    ImageView ivTransparent;
    private AccommodationPresenterMgr accommodationPresenterMgr; //Homestay presenter
    private AreaAdapter areaAdapter; // Area adapter to search
    private LocationFilterItemDTO locationFilterItemDTO = new LocationFilterItemDTO(); //Location filter
    private List<AreaDTO> areaDTOs;// List area filter
    private AreaAccommodationAdapter areaAccommodationAdapter; //Area accomodation adapter
    public static final int ACTION_CHOOSE_AREA = 1000; //Action choose area

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        // customize the SlidingMenu
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        setContentView(R.layout.activity_accommodation);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reStartLocating();
    }

    /**
     * Init view
     */
    private void initView() {
        enableRefresh(true);
        etArea.setImageSearchVisible( R.drawable.ic_search_blue, true);
        etArea.setImageClearVisibile(R.drawable.ic_clear_grey, true);
        //get event : enter from keyboard for search
        etArea.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    handleSearch(false, true);
                    Utils.hideKeyboardInput(AccommodationActivity.this, etArea);
                    return true;
                }
                return false;
            }
        });
        etArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.hideKeyboardInput(AccommodationActivity.this, etArea);
                handleSearch(false, true);
            }
        });
        etArea.setListener(new OnEditTextControlListener() {
            @Override
            public void onTouchControl() {
                if (areaAdapter != null) {
                    etArea.showDropDown();
                } else {
                    showProgressDialog(true);
                    accommodationPresenterMgr.getAreaFilter();
                }
            }

            @Override
            public void onClear() {

            }
        });
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
        rvArea.setLayoutManager(lm);
        rvArea.setHasFixedSize(true);
    }

    /**
     * Init data
     */
    private void initData() {
        checkAppVersion();
        // Delete file image
        new DeleteFileUtil().execute("");
        accommodationPresenterMgr = new AccommodationPresenter(this);
        showProgressDialog(true);
        accommodationPresenterMgr.getAreaSpecial();
        accommodationPresenterMgr.getAreaFilter();
    }

    @OnClick({R.id.ivMenu, R.id.tvFindNear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMenu:
                getSlidingMenu().showMenu(true);
                break;
            case R.id.tvFindNear:
                if (Utils.validateMyLocation(llMain)) {
                    handleSearch(true, false);
                }
                break;
        }
    }

    @Override
    public void renderAreaFilter(List<AreaDTO> areaDTOs) {
        closeProgressDialog();
        stopRefresh();
        this.areaDTOs = areaDTOs;
        if (areaAdapter == null) {
            areaAdapter = new AreaAdapter(this, android.R.layout.simple_spinner_item, areaDTOs);
            etArea.setAdapter(areaAdapter);
        }
    }

    @Override
    public void renderAreaSpecial(List<AreaDTO> areaDTOs) {
        closeProgressDialog();
        stopRefresh();
        if (areaAccommodationAdapter == null) {
            areaAccommodationAdapter = new AreaAccommodationAdapter(this,areaDTOs);
            areaAccommodationAdapter.setListener(this);
            rvArea.setAdapter(areaAccommodationAdapter);
        } else {
            areaAccommodationAdapter.setData(areaDTOs);
        }
        areaAccommodationAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgressDialog();
        super.handleError(errorCode, error);
    }

    /**
     * Handle search
     */
    private void handleSearch(boolean isHavePosition, boolean chooseFilter) {
        Bundle bundle = new Bundle();
        CategoryItemDTO category = new CategoryItemDTO();
        category.setId(LocationCategoryTypeDTO.HOMESTAY.getValue());
        locationFilterItemDTO.getCategories().add(category);
        locationFilterItemDTO.setCategoryIds("" + category.getId());
        bundle.putSerializable(Constants.IntentExtra.LOCATION_FILTER, locationFilterItemDTO);
        if (areaAdapter != null) {
            bundle.putParcelableArrayList(Constants.IntentExtra.LIST_AREA, (ArrayList<? extends Parcelable>)
                    areaAdapter.getAllData());
        } else {
            bundle.putParcelableArrayList(Constants.IntentExtra.LIST_AREA, (ArrayList<? extends Parcelable>)
                    this.areaDTOs);
        }
        if (isHavePosition) {
            locationFilterItemDTO.setFamousLocation(null);
            locationFilterItemDTO.setSearchText("");
            bundle.putDouble(Constants.IntentExtra.LAT, JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude());
            bundle.putDouble(Constants.IntentExtra.LNG, JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude());
        } else {
            if (chooseFilter) {
                String keyWord = etArea.getText().toString();
                AreaDTO areaDTO = Utils.getAreaExisted(this.areaDTOs, keyWord);
                //If don't choose area, it will be search text
                locationFilterItemDTO.setFamousLocation(areaDTO);
                if (areaDTO.getId() > 0) {
                    locationFilterItemDTO.setSearchText("");
                } else {
                    locationFilterItemDTO.setSearchText(keyWord);
                }
            }
            bundle.putDouble(Constants.IntentExtra.LAT, Constants.LAT_LNG_NULL);
            bundle.putDouble(Constants.IntentExtra.LNG, Constants.LAT_LNG_NULL);
        }
        goNextScreen(LocationServiceActivity.class, bundle);
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                if (areaAdapter == null) {
                    accommodationPresenterMgr.getAreaFilter();
                }
                accommodationPresenterMgr.getAreaSpecial();
                break;
        }
    }

    @Override
    public void onEventControl(int action, Object item, View view, int position) {
        switch (action){
            case ACTION_CHOOSE_AREA:
                locationFilterItemDTO.setFamousLocation((AreaDTO)item);
                handleSearch(false, false);
                break;
        }
    }

    /**
     * Show popup to write feedback
     */
    public void openPopUpFeedBack() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.popup_write_feedback, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.etContentFeedback);
        // set dialog message
        alertDialogBuilder.setPositiveButton(Utils.getString(R.string.text_ok), null).setNegativeButton(Utils.getString(R
                .string.text_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String result = String.valueOf(userInput.getText());
                        if (!StringUtil.isNullOrEmpty(result)) {
                            requestFeedback(result);
                            alertDialog.dismiss();
                            Utils.hideKeyboardInput((BaseActivity) JoCoApplication.getInstance().getActivityContext(),
                                    userInput);
                        } else {
                            showMessage(R.string.text_validate_send_feedback);
                        }
                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }

    /**
     * Request feedback to server
     *
     * @param contentFeedback
     */
    public void requestFeedback(String contentFeedback) {
        FeedbackItemDTO item = new FeedbackItemDTO();
        item.setContent(contentFeedback);
        item.setDate(DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
        item.setUserId(Long.parseLong(JoCoApplication.getInstance().getProfile().getUserData().getId()));
        accommodationPresenterMgr.requestSendFeedback(item);
        showProgressDialog(true);
    }

    @Override
    public void requestSendFeedbackSuccess() {
        closeProgressDialog();
        showMessage(R.string.text_send_feedback_JoCo_success);
    }
}
