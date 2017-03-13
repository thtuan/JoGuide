package com.boot.accommodation.vp.category;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.google.android.gms.maps.model.LatLng;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.JoCoEditText;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dialog.FullImageDialog;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.ContactDTO;
import com.boot.accommodation.dto.view.CreateLocationDTO;
import com.boot.accommodation.dto.view.CreateLocationViewDTO;
import com.boot.accommodation.dto.view.GPSInfoDTO;
import com.boot.accommodation.dto.view.LocationCategoryTypeDTO;
import com.boot.accommodation.dto.view.LocationDTO;
import com.boot.accommodation.dto.view.MediaDTO;
import com.boot.accommodation.dto.view.PriceDTO;
import com.boot.accommodation.dto.view.UploadPhotoDTO;
import com.boot.accommodation.listener.OnEditTextControlListener;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.home.ListCategoryFilterAdapter;
import com.boot.accommodation.vp.tour.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * CreateLocationActivity
 *
 * @author thtuan
 * @since 1:39 PM 31-07-2016
 */
public class CreateLocationActivity extends BaseActivity implements CreateLocationViewMgr {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ivUploadImage)
    ImageView ivUploadImage;
    @Bind(R.id.llUploadImage)
    LinearLayout llUploadImage;
    @Bind(R.id.rvUploadPhoto)
    RecyclerView rvUploadPhoto;
    @Bind(R.id.llUploadPhoto)
    LinearLayout llUploadPhoto;
    @Bind(R.id.etNameLocation)
    EditText etNameLocation;
    @Bind(R.id.etAddressLocation)
    EditText etAddressLocation;
    @Bind(R.id.etTypeLocation)
    EditText etTypeLocation;
    @Bind(R.id.etPhoneLocation)
    EditText etPhoneLocation;
    @Bind(R.id.etDescription)
    EditText etDescription;
    @Bind(R.id.frGoogleMap)
    FrameLayout frGoogleMap;
    @Bind(R.id.ncvView)
    NestedScrollView ncvView;
    @Bind(R.id.trChooseType)
    TableRow trChooseType;
    @Bind(R.id.ivGoogle)
    ImageView ivGoogle;
    @Bind(R.id.etWebsite)
    EditText etWebsite;
    @Bind(R.id.etOutStanding)
    JoCoEditText etOutStanding;
    @Bind(R.id.etBestTimeGo)
    JoCoEditText etBestTimeGo;
    @Bind(R.id.etPriceFrom)
    JoCoEditText etPriceFrom;
    @Bind(R.id.etPriceTo)
    JoCoEditText etPriceTo;
    @Bind(R.id.etCurrency)
    JoCoEditText etCurrency;
    private UploadPhotoAdapter uploadPhotoAdapter;
    private List<UploadPhotoDTO> uploadPhotoDTOs;
    CreateLocationPresenterMgr createLocationPresenterMgr;
    public static final int SET_TEXT_TYPE_CHOOSED = 200;// when user choose type we set to textview
    public static final int ACTION_DELETE_PHOTO = 0;
    public static final int ACTION_VIEW_DETAIL = 1;
    private List<MediaDTO> mediaDTOs = new ArrayList<>();
    public static final int ACTION_ADD_PHOTO = 19;
    List<Long> categoryIds = new ArrayList<>();
    List<Long> monthIds = new ArrayList<>(); //List id choose month
    CreateLocationViewDTO createLocationViewDTO = new CreateLocationViewDTO(); //Create location view dto
    private boolean sending = false;// to check is sending
    LocationDTO locationDTO = new LocationDTO(); // Location
    AlertDialog alertDialogBestTime; //Alert dialog best time
    private ListCategoryAdapter bestTimeAdapter; //Best time adapter
    private final int ACTION_CHOOSE_CATEGORY_OK = 1000; //Action choose category ok
    private final int ACTION_CHOOSE_CATEGORY_CANCEL = 1001; //Action choose category cancel
    private final int ACTION_SHOW_CHOOSE_CATEGORY = 1002; //Action choose show category
    private AlertDialog alertDialog; //Alert dialog
    private ListCategoryFilterAdapter categoryAdapter; //Category adapter
    private SpinnerAdapter adapterCurrency;// Adapter currency

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_location);
        enableSlidingMenu(false);
        ButterKnife.bind(this);
        initView();
        initData();
        // Restart location again
        reStartLocating();

    }

    /**
     * Init data
     */
    private void initData() {
        categoryIds.add(LocationCategoryTypeDTO.HOMESTAY.getValue());
        createLocationPresenterMgr = new CreateLocationPresenter(this);
        createLocationPresenterMgr.getCategory();
        GPSInfoDTO gpsInfoDTO = JoCoApplication.getInstance().getProfile().getMyGPSInfo();
        locationDTO = new LocationDTO(gpsInfoDTO.getLatitude(), gpsInfoDTO.getLongtitude());
        Bundle bundle = putLocationData();
        switchFragment(ChooseLocationFragment.newInstance(bundle), R.id.frGoogleMap, false);
    }

    @Override
    public void getCategorySuccess(CreateLocationViewDTO createLocationViewDTO) {
        this.createLocationViewDTO = createLocationViewDTO;
    }

    /**
     * Init view
     */
    private void initView() {
        rvUploadPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvUploadPhoto.setHasFixedSize(true);
        ivGoogle.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        ncvView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;
                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        ncvView.requestDisallowInterceptTouchEvent(false);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        ncvView.requestDisallowInterceptTouchEvent(true);
                        return false;
                    default:
                        return true;
                }
            }
        });
        String[] currency = new String[] {StringUtil.getString(R.string.text_unit_price_vnd), StringUtil.getString(R
                .string.text_unit_price_usd)};
        adapterCurrency = new SpinnerAdapter(this,
                R.layout.sp_item_day, currency);
        etCurrency.setAdapter(adapterCurrency);
        etCurrency.setImageClearVisibile(false);
        etCurrency.setListener(new OnEditTextControlListener() {
            @Override
            public void onTouchControl() {
                etCurrency.showDropDown();
            }

            @Override
            public void onClear() {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void renderLayout(List<UploadPhotoDTO> uploadPhotoDTOs) {
        if (uploadPhotoDTOs.size() != 0) {
            llUploadImage.setVisibility(View.GONE);
            llUploadPhoto.setVisibility(View.VISIBLE);
        } else {
            llUploadImage.setVisibility(View.VISIBLE);
            llUploadPhoto.setVisibility(View.GONE);
        }
        if (uploadPhotoAdapter == null) {
            uploadPhotoAdapter = new UploadPhotoAdapter(uploadPhotoDTOs);
            uploadPhotoAdapter.setListener(this);
            uploadPhotoAdapter.setEnableFooter(true);
            rvUploadPhoto.setAdapter(uploadPhotoAdapter);
        } else {
            uploadPhotoAdapter.setData(uploadPhotoDTOs);
        }
        uploadPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess() {
        sending = false;
        finish();
        closeProgress();
        showMessage(StringUtil.getString(R.string.text_create_accommodation_success));
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        sending = false;
        closeProgress();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    /**
     * Show popup category
     */
    public void showPopUpCategory() {
        if (alertDialog == null) {
            alertDialog = Utils.showDialog(this, this, createLocationViewDTO.getCategory(), R.layout.popup_choose_location_category,
                    ACTION_CHOOSE_CATEGORY_OK, StringUtil.getString(R.string.text_ok), ACTION_CHOOSE_CATEGORY_CANCEL,
                    StringUtil.getString(R.string.text_cancel), ACTION_SHOW_CHOOSE_CATEGORY);
        }
        alertDialog.show();
    }

    /**
     * Init view
     */
    private void initView(View view, List<CategoryItemDTO> categoryLocationItems) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvListCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //render list category
        if (categoryAdapter == null) {
            categoryAdapter = new ListCategoryFilterAdapter(categoryLocationItems);
            categoryAdapter.setListener(this);
            recyclerView.setAdapter(categoryAdapter);
        }
    }

    @Override
    public void updateFileNameUpload(String fileName) {
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setUri(fileName);
        mediaDTOs.add(mediaDTO);
    }

    @OnClick({R.id.iv_back, R.id.llUploadImage, R.id.iv_post, R.id.btn_post, R.id.trChooseType, R.id.etTypeLocation,
            R.id.ivFullScreen, R.id.etAddressLocation, R.id.etBestTimeGo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (checkPreFinish()) {
                    finish();
                } else {
                    showBackDialog();
                }
                break;
            case R.id.llUploadImage:
                ImageUtil.selectMultiImage(this);
                break;
            case R.id.iv_post:
                if (!sending) {
                    createLocation(mediaDTOs);
                }
                break;
            case R.id.etTypeLocation:
                showPopUpCategory();
                break;
            case R.id.ivFullScreen:
                Bundle bundle = putLocationData();
                goNextScreen(PickLocationActivity.class, bundle);
                break;
            case R.id.etBestTimeGo:
                showPopupBestTime();
                break;
        }
    }

    @NonNull
    private Bundle putLocationData() {
        Bundle bundle = new Bundle();
        bundle.putDouble(PickLocationActivity.KEY_LAT, locationDTO.getLat());
        bundle.putDouble(PickLocationActivity.KEY_LONG, locationDTO.getLng());
        return bundle;
    }

    @Override
    public void onEventControl(int action, Object item, View view, int position) {
        switch (action) {
            case ACTION_VIEW_DETAIL:
                showFullImageView((UploadPhotoDTO) item);
                break;
            case ACTION_DELETE_PHOTO:
                createLocationPresenterMgr.deletePhoto(position);
                break;
            case ACTION_ADD_PHOTO:
                ImageUtil.selectMultiImage(this);
                break;
            case ACTION_CHOOSE_CATEGORY_OK:
//                new string builder add name of type user selected
                categoryIds.clear();
                StringBuilder stringName = new StringBuilder();
                stringName.setLength(0);
                for (int i = 0, size = createLocationViewDTO.getCategory().size(); i < size; i++) {
                    CategoryItemDTO category = createLocationViewDTO.getCategory().get(i);
                    if (category.getSelected()) {
                        if (StringUtil.isNullOrEmpty(stringName.toString())) {
                            stringName.append(category.getName());
                        } else {
                            stringName.append(Constants.STR_TOKEN + category.getName());
                        }
                        categoryIds.add(category.getId());
                    }
                }
                etTypeLocation.setText("");
                etTypeLocation.setText(stringName);
                alertDialog.dismiss();
                break;
            case ACTION_CHOOSE_CATEGORY_CANCEL:
                categoryAdapter.updateCategoryChose(false);
                break;
            case ACTION_SHOW_CHOOSE_CATEGORY: {
                // Init view
                final List<CategoryItemDTO> categoryLocationItems = (List<CategoryItemDTO>) item;
                initView(view, categoryLocationItems);
                categoryAdapter.updateCategoryChose(false);
                break;
            }
        }
    }

    /**
     * Show full hinh anh
     */
    private void showFullImageView(UploadPhotoDTO item) {
        FragmentManager fm = getSupportFragmentManager();
        FullImageDialog fullImageDialog = new FullImageDialog();
        fullImageDialog.setData(this, item.getPath());
        fullImageDialog.show(fm, "fragment_help");
    }

    private void createLocation(List<MediaDTO> mediaDTOs) {
        if (createLocationPresenterMgr.checkUploadDone()) {
            if (checkValidate()) {
                showProgress();
                CreateLocationDTO createLocationDTO = new CreateLocationDTO();
                MediaDTO avatar = new MediaDTO();
                ContactDTO contactDTO = new ContactDTO();
                //Default image first to avatar location
                if (mediaDTOs != null && mediaDTOs.size() > 0) {
                    avatar.setUri(mediaDTOs.get(0).getUri());
//                    mediaDTOs.remove(0);
                }
//                avatar.setUri(JoCoApplication.getInstance().getProfile().getUserData().getAvatar());
                contactDTO.setPhone(etPhoneLocation.getText().toString().trim());
                createLocationDTO.setAddress(etAddressLocation.getText().toString().trim());
                createLocationDTO.setContact(contactDTO);
                createLocationDTO.setName(etNameLocation.getText().toString().trim());
                createLocationDTO.setMedias(mediaDTOs.size() > 1 ? mediaDTOs.subList(1, mediaDTOs.size()) : null);
                createLocationDTO.setAvatar(avatar);
                createLocationDTO.setDescription(etDescription.getText().toString().trim());
                createLocationDTO.setWebsite(etWebsite.getText().toString().trim());
                createLocationDTO.setCoordinate(locationDTO);
                createLocationDTO.setCategories(categoryIds);
                createLocationDTO.setOutstanding(etOutStanding.getText().toString().trim());
                createLocationDTO.setBestInTimes(monthIds);
                PriceDTO priceDTO = new PriceDTO();
                if (!StringUtil.isNullOrEmpty(etPriceFrom.getText().toString())) {
                    priceDTO.setFrom(Double.valueOf(etPriceFrom.getText().toString()));
                }
                if (!StringUtil.isNullOrEmpty(etPriceTo.getText().toString())) {
                    priceDTO.setTo(Double.valueOf(etPriceTo.getText().toString()));
                }
                priceDTO.setCurrency(etCurrency.getText().toString());
                createLocationDTO.setPrice(priceDTO);
                createLocationPresenterMgr.requestCreateLocation(createLocationDTO);
                sending = true;
            }
        }
    }

    /**
     * Validate data
     *
     * @return
     */
    boolean checkValidate() {
        if (etTypeLocation.getText().toString().equals("")) {
            showMessage(Utils.getString(R.string.text_choose_type_location));
            return false;
        } else if (etNameLocation.getText().toString().equals("")) {
            showMessage(Utils.getString(R.string.text_please_input_location_name));
            return false;
        } else if (etAddressLocation.getText().toString().equals("")) {
            showMessage(Utils.getString(R.string.text_choose_location_on_map));
            return false;
        } else if ((locationDTO != null && locationDTO.getLat() == Constants.LAT_LNG_NULL && locationDTO.getLng() ==
                Constants.LAT_LNG_NULL) || StringUtil.isNullOrEmpty(etAddressLocation.getText().toString())) {
            showMessage(Utils.getString(R.string.text_choose_location_on_map));
            return false;
        } else if (mediaDTOs == null || mediaDTOs.size() == 0) {
            showMessage(Utils.getString(R.string.text_choose_picture));
            return false;
        } else {
            return true;
        }
    }


    /**
     * Render address
     *
     * @param address
     */
    public void renderAddress(Address address, String result) {
        etAddressLocation.setText(result);
    }

    /**
     * Show back dialog
     */
    private void showBackDialog() {
        showAlert(getString(R.string.text_do_you_want_to_go_back), null,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }, StringUtil.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }, StringUtil.getString(R.string.text_cancel), true);
    }

    /**
     * Check before finish
     *
     * @return
     */
    private boolean checkPreFinish() {
        if (!etTypeLocation.getText().toString().equals("")) {
            return false;
        } else if (!etNameLocation.getText().toString().equals("")) {
            return false;
        } else if (!etAddressLocation.getText().toString().equals("")) {
            return false;
        } else if (!etPhoneLocation.getText().toString().equals("")) {
            return false;
        } else if (!etDescription.getText().toString().equals("")) {
            return false;
        } else if (!etWebsite.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (checkPreFinish()) {
            finish();
        } else {
            showBackDialog();
        }

    }

    /**
     * Update location choose on map
     *
     * @param latLng
     */
    void updateLocation(LatLng latLng) {
        locationDTO.setLat(latLng.latitude);
        locationDTO.setLng(latLng.longitude);
    }

    @Override
    public void receiveBroadcast(int action, Bundle bundle) {
        switch (action) {
            case PickLocationActivity.ACTION_DONE:
                LatLng latLng = new LatLng(bundle.getDouble(PickLocationActivity.KEY_LAT), bundle.getDouble
                        (PickLocationActivity.KEY_LONG));
                if (latLng != null) {
                    ChooseLocationFragment f = (ChooseLocationFragment) getSupportFragmentManager().findFragmentById(R.id.frGoogleMap);
                    f.drawLocationMap(latLng);
                }
        }
    }

    /**
     * Show popup best time
     */
    private void showPopupBestTime() {
        // get prompts.xml view
        if (alertDialogBestTime == null) {
            final Context context = this;
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.popup_best_time_go, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptsView);
            final RecyclerView recyclerView = (RecyclerView) promptsView.findViewById(R.id.rvListMonth);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            //render list category
            if (bestTimeAdapter == null) {
                bestTimeAdapter = new ListCategoryAdapter(createLocationViewDTO.getMonth());
                bestTimeAdapter.setListener(this);
                recyclerView.setAdapter(bestTimeAdapter);
            }
            // set dialog message
            alertDialogBuilder.setCancelable(false).setPositiveButton(StringUtil.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //new string builder add name of type user selected
                    monthIds.clear();
                    StringBuilder stringName = new StringBuilder();
                    stringName.setLength(0);
                    for (int i = 0, size = createLocationViewDTO.getMonth().size(); i < size; i++) {
                        CategoryItemDTO month = createLocationViewDTO.getMonth().get(i);
                        if (month.getSelected()) {
                            if (StringUtil.isNullOrEmpty(stringName.toString())) {
                                stringName.append(month.getName());
                            } else {
                                stringName.append(Constants.STR_TOKEN + month.getName());
                            }
                            monthIds.add(month.getId());
                        }
                    }
                    etBestTimeGo.setText("");
                    etBestTimeGo.setText(stringName);
                }
            }).setNegativeButton(StringUtil.getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            // create alert dialog
            alertDialogBestTime = alertDialogBuilder.create();
        }
        alertDialogBestTime.show();
    }

    @Override
    protected void updatePhoto(String fileTaken) {
        UploadPhotoDTO uploadPhotoDTO = new UploadPhotoDTO();
        uploadPhotoDTO.setFile(takenPhoto.getAbsoluteFile());
        uploadPhotoDTO.setPath(takenPhoto.getAbsolutePath());
        uploadPhotoDTO.setProgressPercentage(0);
        createLocationPresenterMgr.addPhoto(uploadPhotoDTO);
    }

}
