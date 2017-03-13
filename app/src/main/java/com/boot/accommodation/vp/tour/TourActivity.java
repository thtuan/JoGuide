package com.boot.accommodation.vp.tour;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.common.control.ContactCompletionView;
import com.boot.accommodation.common.info.ServerPath;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.constant.ErrorCode;
import com.boot.accommodation.dto.view.FeedbackItemDTO;
import com.boot.accommodation.dto.view.PickContactDTO;
import com.boot.accommodation.dto.view.TourInviteDTO;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.home.CustomViewPager;
import com.boot.accommodation.vp.home.HomeAdapter;
import com.boot.accommodation.vp.location.LocationDetailReviewFragment;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Admin on 13/11/2015.
 */
public class TourActivity extends BaseActivity implements TourViewMgr, View.OnTouchListener, ResultCallback<People
        .LoadPeopleResult>, TokenCompleteTextView.TokenListener {

    @Bind(R.id.tvTourName)
    TextView tvTourName;
    @Bind(R.id.ivOwnerAvatar)
    CircularImageView ivOwnerAvatar;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.htab_tabs)
    TabLayout htabTabs;
    @Bind(R.id.htab_viewpager)
    CustomViewPager htabViewpager;
    @Bind(R.id.crMain)
    CoordinatorLayout crMain;
    @Bind(R.id.ivTourPhoto)
    ImageView ivTourPhoto;
    @Bind(R.id.fbAddNotification)
    FloatingActionButton fbAddNotification;
    @Bind(R.id.appBar)
    AppBarLayout appBar;
    @Bind(R.id.ivDotMenu)
    ImageView ivDotMenu;
    @Bind(R.id.llShare)
    LinearLayout llShare;
    @Bind(R.id.llInvite)
    LinearLayout llInvite;
    @Bind(R.id.llPhone)
    LinearLayout llPhone;
    @Bind(R.id.llMenuDot)
    LinearLayout llMenuDot;
    @Bind(R.id.llFeedback)
    LinearLayout llFeedback;
    @Bind(R.id.llVote)
    LinearLayout llVote;
    @Bind(R.id.prLoading)
    ProgressBar prLoading;
    String photoTrip;
    TourPresenterMgr tourPresenterMgr;
    ArrayAdapter<PickContactDTO> arrayAdapter;
    TripItemDTO tripItem = new TripItemDTO();
    int positionImage = 0;
    @Bind(R.id.llJoinTour)
    LinearLayout llJoinTour;
    private boolean isShowMenu = false; // bien kiem tra dang show menu hay ko
    private long tourId;
    private long tourPlanId;
    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Email.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.Email.PHOTO_THUMBNAIL_URI
    };
    private boolean isInvited = false; //Variable check invite or no
    private TourInviteDTO tourInviteDTO; //Info invite tour

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_tour_trip);
        ButterKnife.bind(this);
        tourPresenterMgr = new TourPresenter(this);
        Uri uri = getIntent().getData();
        if (uri != null) {
            handleDeepLink(uri);
        } else if (getIntent().hasExtra(Constants.IntentExtra.TOUR_ID)) {//Check one {
            tourId = getIntent().getLongExtra(Constants.IntentExtra.TOUR_ID, 0);
            tourPlanId = getIntent().getLongExtra(Constants.IntentExtra.TOUR_PLAN_ID, 0);
            tourPresenterMgr.getTourPlan(tourId, tourPlanId);
        } else {
            initViews();
        }
    }

    /**
     * Init browser
     *
     * @param tourId
     * @param tourPlanId
     */
    private void initFromBrowser(long tourId, long tourPlanId) {
//        if (intent.getAction().equals("android.intent.action.VIEW")){
//            PreferenceUtils.saveString(Constants.Preference.PREFERENCE_FROM, "http");
//            PreferenceUtils.saveString(Constants.Preference.PREFERENCE_URI, uri.getScheme()+"://"+uri.getHost()+uri.getPath());
//        }
        PreferenceUtils.saveLong(Constants.Preference.PREFERENCE_DEEP_LINK_TOUR_ID, tourId);
        PreferenceUtils.saveLong(Constants.Preference.PREFERENCE_DEEP_LINK_TOUR_PLAN_ID, tourPlanId);
        tourPresenterMgr.getTourPlan(tourId, tourPlanId);
    }

    /**
     * Khoi tao view
     */
    private void initViews() {
        if (getIntent() != null && getIntent().hasExtra(Constants.IntentExtra.TRIP_ITEM)) {
            tripItem = getIntent().getParcelableExtra(Constants.IntentExtra.TRIP_ITEM);
            tourId = tripItem.getTourId();
            tourPlanId = tripItem.getTourPlanId();
            positionImage = getIntent().getIntExtra(Constants.IntentExtra.POSITION, 0);
            if (tripItem.getPhoto() != null) {
                photoTrip = tripItem.getPhoto().get(positionImage);
            }

        }
        initToolbar();
        initViewPager();
        initHeader();
        //        initMenuClick();
        crMain.setOnTouchListener(this);
        toolbar.setOnTouchListener(this);
        appBar.setOnTouchListener(this);
        htabViewpager.setOnTouchListener(this);
    }

    /**
     * Update hotline
     */
    public void updateFunction(boolean isTourCompany) {
        //check attend
        if (!tripItem.getIsJoin()) {
            llFeedback.setVisibility(View.GONE);
            llVote.setVisibility(View.GONE);
            llInvite.setVisibility(View.GONE);
        } else {
            llFeedback.setVisibility(View.VISIBLE);
            llVote.setVisibility(View.VISIBLE);
            llInvite.setVisibility(View.VISIBLE);
        }
        if (!isTourCompany) {
            llPhone.setVisibility(View.GONE);
            llFeedback.setVisibility(View.GONE);
            llVote.setVisibility(View.GONE);
        } else {
            llPhone.setVisibility(View.VISIBLE);
        }
        if (tourPlanId > 0) {
            llJoinTour.setVisibility(View.VISIBLE);
            //Check have tour plan if want join tour
            tourPresenterMgr.getIInvited(tourId, tourPlanId);
        } else {
            llJoinTour.setVisibility(View.GONE);
        }
        if (DateUtil.compareDateWithFormat(tripItem.getStartDate(), DateUtil.formatNow(DateUtil
                .FORMAT_DATE_TIME_ZONE),DateUtil
                .FORMAT_DATE_TIME_ZONE) <= 0) {
            llJoinTour.setVisibility(View.GONE);
        } else {
            llJoinTour.setVisibility(View.VISIBLE);
        }
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initViewPager() {
        setupViewPager(htabViewpager);
        htabTabs.setupWithViewPager(htabViewpager);
        htabViewpager.setOffscreenPageLimit(4);
        htabViewpager.setSwipeable(true);
        if (getIntent() != null && getIntent().hasExtra(Constants.IntentExtra.PAGE_INDEX)) {
            htabViewpager.setCurrentItem(getIntent().getIntExtra(Constants.IntentExtra.PAGE_INDEX, 0));
        }
    }

    /**
     * Init header
     */
    private void initHeader() {
        ImageUtil.loadImage(ivTourPhoto, ImageUtil.getImageUrl(photoTrip), prLoading);
        ImageUtil.loadImage(ivOwnerAvatar, ImageUtil.getImageUrlOwnerAvatar(tripItem.getUserOwner().getAvatar()));
        tvTourName.setText(tripItem.getName());
    }

    /**
     * Khoi tao view pager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        Bundle data = new Bundle();
        data.putString(Constants.IntentExtra.UNIT_PRICE, tripItem.getPriceUnit());
        data.putLong(Constants.IntentExtra.TOUR_ID, tripItem.getTourId());
        //Put item id, type for review
        data.putLong(Constants.IntentExtra.ITEM_ID, tripItem.getTourId());
        data.putInt(Constants.IntentExtra.TYPE_REVIEW, LocationDetailReviewFragment.TYPE_REVIEW_TOUR);
        data.putLong(Constants.IntentExtra.TOUR_PLAN_ID, tripItem.getTourPlanId());
        data.putBoolean(Constants.IntentExtra.IS_JOIN, tripItem.getIsJoin());
        data.putInt(Constants.IntentExtra.FROM_NOTIFICATION_MENU, 0);
        final HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager());
        adapter.addFrag(TourInfoFragment.newInstance(data), getString(R.string.text_tour_information).toUpperCase());
        adapter.addFrag(TourItineraryFragment.newInstance(putDataForTourist()), getString(R.string.text_tour_itinerary).toUpperCase());

        //adapter.addFrag(TourTouristInfoFragment.newInstance(putDataForTourist()), getString(R.string
        //        .text_tour_tourist).toUpperCase());
        adapter.addFrag(TourNotificationFragment.newInstance(data), getString(R.string.text_tour_notification).toUpperCase());
        adapter.addFrag(LocationDetailReviewFragment.newInstance(data), getString(R.string.text_discuss).toUpperCase());
        viewPager.setOffscreenPageLimit(adapter.getFragmentList().size());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                handleChangeTab(position, adapter);
            }

            @Override
            public void onPageSelected(int position) {
                handleChangeTab(position, adapter);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Handle change tab layout
     *
     * @param position
     * @param adapter
     */
    private void handleChangeTab(int position, HomeAdapter adapter) {
        if (adapter.getFragmentList().get(position) instanceof TourNotificationFragment && ((tripItem.getIsJoin
                () && (tripItem.getTourRole() == UserItemDTO.TYPE_LEADER) || JoCoApplication.getInstance()
                .getProfile().getUserData().getUserType() == UserItemDTO
                .TYPE_ADMIN && tripItem.getTourPlanId() != 0))) {
            fbAddNotification.setVisibility(View.VISIBLE);
        } else {
            fbAddNotification.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.ivOwnerAvatar, R.id.ivBack, R.id.fbAddNotification, R.id.ivDotMenu, R.id.llShare, R.id.llInvite, R
            .id.llPhone, R.id.llMenuDot, R.id.llFeedback, R.id.llVote, R.id.ivTourPhoto, R.id.llJoinTour})
    public void OnClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                break;
            case R.id.ivOwnerAvatar:
                TouristDTO touristDTO = new TouristDTO();
                bundle.putParcelable(Constants.IntentExtra.TOURIST_DTO, touristDTO.convertToTouristDTO(tripItem.getUserOwner()));
                goNextScreen(ProfileActivity.class, bundle);
                break;
            case R.id.fbAddNotification:
                Bundle bundleId = new Bundle();
                bundleId.putLong(Constants.IntentExtra.TOUR_ID, tripItem.getTourId());
                bundleId.putLong(Constants.IntentExtra.TOUR_PLAN_ID, tripItem.getTourPlanId());
                goNextScreen(ScheduleActivity.class, bundleId);
                break;
            case R.id.ivDotMenu:
                if (isShowMenu) {
                    isShowMenu = false;
                    llMenuDot.setVisibility(View.GONE);
                } else {
                    isShowMenu = true;
                    llMenuDot.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.llShare:
                shareFacebook();
                llMenuDot.setVisibility(View.GONE);
                isShowMenu = false;
                break;
            case R.id.llInvite:
                openPopUpEmail();
                llMenuDot.setVisibility(View.GONE);
                isShowMenu = true;
                break;
            case R.id.llPhone:
                callHotLine(tripItem.getUserOwner().getPhone());
                llMenuDot.setVisibility(View.GONE);
                isShowMenu = false;
                break;
            case R.id.llVote: {
                llMenuDot.setVisibility(View.GONE);
                Bundle data = new Bundle();
                data.putParcelable(Constants.IntentExtra.TRIP_ITEM, tripItem);
                goNextScreen(TourVoteActivity.class, data);
                break;
            }
            case R.id.llFeedback:
                llMenuDot.setVisibility(View.GONE);
                openPopUpFeedBack();
                break;
            case R.id.ivTourPhoto: {
                Bundle data = new Bundle();
                data.putString(Constants.IntentExtra.TITLE, StringUtil.getString(R.string.text_title_photo_tour));
                data.putLong(Constants.IntentExtra.TOUR_ID, tripItem.getTourId());
                goNextScreen(TourPictureActivity.class, data);
                break;
            }
            case R.id.llJoinTour:
                handleJoinTour();
                llMenuDot.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Handle join tour
     */
    private void handleJoinTour() {
        String title = StringUtil.getString(R.string.text_invited_join_tour);
        String accept = StringUtil.getString(R.string.text_accept);
        String decline = StringUtil.getString(R.string.text_decline);
        if (!isInvited) {
            title = StringUtil.getString(R.string.text_ask_join_tour);
            accept = StringUtil.getString(R.string.text_ok);
            decline = StringUtil.getString(R.string.text_cancel);
        }
        showAlert(title, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!isInvited) {
//                    tourPresenterMgr.requestAskToJoinTour(tourId, tourPlanId);
                    tourPresenterMgr.requestCheckAskedToJoinTour(tourId, tourPlanId);
                } else {
                    tourPresenterMgr.requestAcceptJoinTour(tourId, tourPlanId, tourInviteDTO.getInviteToken());
                }
            }
        }, accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isInvited) {
                    tourPresenterMgr.requestDeclineJoinTour(tourId, tourPlanId, tourInviteDTO.getInviteToken());
                }
            }
        }, decline, true);
    }

    /**
     * Show popup to write feedback
     */
    public void openPopUpFeedBack() {
        final Context context = this;
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup_write_feedback, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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
                            Utils.hideKeyboardInput(TourActivity.this, userInput);
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.text_validate_send_feedback), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        // show it
        alertDialog.show();
    }

    /**
     * open popup menu
     */
    public void openPopUpEmail() {
        final Context context = this;
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup_pick_contact, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);
        // set dialog message
        alertDialogBuilder.setPositiveButton(Utils.getString(R.string.text_send), null).setNegativeButton(Utils.getString(R
                .string.text_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
        final ContactCompletionView edSearch = (ContactCompletionView) promptsView.findViewById(R.id.edSearch);
        ArrayList<PickContactDTO> list = getEmails();
        Collections.sort(list);
        arrayAdapter = new FilteredArrayAdapter<PickContactDTO>(this, R.layout.item_pick_contact, list) {
            @Override
            protected boolean keepObject(PickContactDTO obj, String mask) {
                mask = StringUtil.getEngStringFromUnicodeString(mask.toLowerCase());
                String compare = StringUtil.getEngStringFromUnicodeString(obj.getName().toLowerCase() + obj
                        .getAddress().toLowerCase());
                return compare.contains(mask);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.item_pick_contact, parent, false);
                }
                PickContactDTO pickContactDTO = getItem(position);
                CircularImageView ivAvatar = (CircularImageView) convertView.findViewById(R.id.ivAvatar);
                TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
                TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
                ImageUtil.loadImage(ivAvatar, pickContactDTO.getPhoto());
                tvName.setText(pickContactDTO.getName());
                tvAddress.setText(pickContactDTO.getAddress());
                return convertView;
            }
        };
        edSearch.setTokenListener(this);
        edSearch.setThreshold(1);
        edSearch.setAdapter(arrayAdapter);

        Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboardInput(TourActivity.this, view);
                ArrayList<String> contactLst = new ArrayList<>();
                boolean isReady = false;
                for (PickContactDTO dto : edSearch.getObjects()) {
                    if (Utils.isEmailValid(dto.getAddress())) {
                        contactLst.add(dto.getAddress());
                        isReady = true;
                    } else {
                        showMessage(dto.getAddress() + Utils.getString(R.string.text_validate_fortmat_email));
                        isReady = false;
                        break;
                    }
                }
                if (isReady) {
                    showProgressDialog(true);
                    tourPresenterMgr.requestInvite(tourId, tourPlanId, contactLst);
                    alertDialog.dismiss();
                }
            }
        });
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
        item.setTourId(tripItem.getTourId());
        item.setTourPlanId(tripItem.getTourPlanId());
        item.setUserId(Long.parseLong(JoCoApplication.getInstance().getProfile().getUserData().getId()));
        //call presenter request
//        tourPresenterMgr.requestSendFeedback(item);
        showProgressDialog(true);
    }

    /**
     * call hot line
     *
     * @param dialNumber
     */
    public void callHotLine(String dialNumber) {
        try {
            Intent callIntent = new Intent();
            callIntent.setAction(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            startActivity(callIntent);
        } catch (Exception e) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dialNumber));
            startActivity(callIntent);
        }
    }

    /**
     * method put data to another activity or fragment
     *
     * @return Bundle
     */
    public Bundle putDataForTourist() {
        //get trip item to touristFragment
        Bundle dataForTourist = new Bundle();
        dataForTourist.putParcelable(Constants.IntentExtra.TRIP_ITEM, tripItem);
        dataForTourist.putString(Constants.IntentExtra.TRIP_PHOTO, photoTrip);
        return dataForTourist;
    }

    @Override
    public void hideFloatingButton(boolean isHide, int position) {
        super.hideFloatingButton(isHide, position);
        fbAddNotification.setVisibility(isHide ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * Share facebook
     */
    private void shareFacebook() {
        ShareLinkContent content = new ShareLinkContent.Builder().setContentTitle(tripItem.getName()).
                setContentUrl(Uri.parse(ServerPath.getTourPath() + tripItem.getTourId())).setImageUrl(Uri.parse(ImageUtil
                .getImageUrl
                        (photoTrip))).build();
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float eventX = ev.getX();
        float eventY = ev.getY();
        if (eventX > llMenuDot.getX() && eventX < (llMenuDot.getX() + llMenuDot.getWidth()) && eventY > llMenuDot.getY() && eventY < (llMenuDot.getY() + llMenuDot.getHeight()) + 50) {
        } else {
            if (isShowMenu) {
                llMenuDot.setVisibility(View.GONE);
                isShowMenu = false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public ArrayList<PickContactDTO> getEmails() {
        ArrayList<PickContactDTO> contactDTOs = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            try {
//                final int contactIdIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID);
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int photoIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.PHOTO_THUMBNAIL_URI);
                final int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                String name, email, photo;
                while (cursor.moveToNext()) {
                    name = cursor.getString(displayNameIndex);
                    email = cursor.getString(emailIndex);
                    photo = cursor.getString(photoIndex);
                    PickContactDTO contactDTO = new PickContactDTO(photo, name, email);
                    if (email != null) {
                        contactDTOs.add(contactDTO);
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return contactDTOs;
    }

    @Override
    public void onResult(@NonNull People.LoadPeopleResult peopleData) {
        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            ArrayList<PickContactDTO> list = new ArrayList<>();
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {
                    /*PickContactDTO dto = new PickContactDTO(personBuffer.get(i).getImage().getUrl(), personBuffer.get(i)
                            .getDisplayName(), "chua co email");
                    list.add(dto);
                    Intent intent = new Intent(getApplicationContext(), PickContactActivity.class);
                    intent.putParcelableArrayListExtra(Constants.PICK_CONTACT, list);
                    startActivity(intent);*/
                    Log.d("Google", "Display name: " + personBuffer.get(i).getName());
                }
            } finally {
                personBuffer.release();
            }
        } else {
            Log.e("Google", "Error requesting visible circles: " + peopleData.getStatus());
        }
    }

    @Override
    public void showMessage(boolean check) {
        closeProgressDialog();
        if (check == true) {
            Toast.makeText(getApplicationContext(), getString(R.string.text_send_feedback_success), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.text_send_feedback_false), Toast.LENGTH_LONG).show();
        }

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
    public void getTourPlanSuccess(TripItemDTO data) {
        closeProgress();
        this.tripItem = data;
        if (tripItem.getPhoto() != null) {
            photoTrip = tripItem.getPhoto().get(0);
        }
        initToolbar();
        initViewPager();
        // If exist tour id from server(maybe delete tour)
        if (tripItem.getTourId() > 0) {
            initHeader();
        }
        crMain.setOnTouchListener(this);
        toolbar.setOnTouchListener(this);
        appBar.setOnTouchListener(this);
        htabViewpager.setOnTouchListener(this);
        //Clear tour id, tour plan id deep link
        PreferenceUtils.saveLong(Constants.Preference.PREFERENCE_DEEP_LINK_TOUR_ID, 0);
        PreferenceUtils.saveLong(Constants.Preference.PREFERENCE_DEEP_LINK_TOUR_PLAN_ID, 0);
    }

    @Override
    public void inviteTourSuccess() {
        showMessage(StringUtil.getString(R.string.text_invite_tour_success));
        closeProgressDialog();
    }

    @Override
    public void getIInvitedSuccess(TourInviteDTO tourInviteDTO) {
        isInvited = true;
        this.tourInviteDTO = tourInviteDTO;
        if (tourInviteDTO == null || StringUtil.isNullOrEmpty(tourInviteDTO.getInviteToken())) {
            isInvited = false;
        } else {
            showAlertJoinTour();
        }
    }

    @Override
    public void requestAcceptJoinTourSuccess() {
        showMessage(R.string.text_accept_join_tour_success);
        clearData();
    }

    @Override
    public void requestAskJoinTourSuccess(Integer result) {
        if (ErrorCode.BUS_USER_ALREADY_BEEN_IN_TOUR_PLAN_WAITING_LIST == result) {
            showMessage(R.string.text_already_in_tour_waiting_list);
        } else {
            showMessage(R.string.text_request_join_tour_success);
        }
    }

    @Override
    public void requestCheckAskedJoinTourSuccess(Integer result) {
        if (ErrorCode.BUS_USER_HAS_ALREADY_SENT_JOINING_TOUR_REQUEST == result) {
            showMessage(R.string.text_already_send_join_tour);
        } else if (ErrorCode.BUS_USER_HAS_ALREADY_BEEN_MEMBER_OF_TOUR == result) {
            showMessage(R.string.text_already_member_of_tour);
        } else {
            tourPresenterMgr.requestAskToJoinTour(tourId, tourPlanId);
        }
    }

    @Override
    public void requestDeclineJoinTourSuccess() {
        showMessage(R.string.text_decline_join_tour_success);
        clearData();
    }

    @Override
    public void onTokenAdded(Object o) {

    }

    @Override
    public void onTokenRemoved(Object o) {

    }

    /**
     * Show alert alert jointour
     */
    public void showAlertJoinTour() {
        String alert = StringUtil.getString(R.string.text_alert_invited_join_tour);
        final Snackbar snackbar = Snackbar
                .make(crMain, alert, Snackbar.LENGTH_INDEFINITE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                snackbar.dismiss();
            }
        }, 10000);
        snackbar.show();
    }

    /**
     * Clear data
     */
    private void clearData() {
        tourInviteDTO = null;
        isInvited = false;
    }

    /**
     * Handle deep link
     */
    private void handleDeepLink(Uri uri) {
//        if ( uri != null){
        try {
            // https://joco.com.vn/tour/265/plan/134
            String[] path = uri.toString().split("/");
            if (path.length > 6) {
                tourId = Long.valueOf(path[4]);
                String[] queryTourPlan = path[6].split("\\?");
                if (queryTourPlan.length > 0) {
                    tourPlanId = Long.valueOf(queryTourPlan[0]);
                }
                initFromBrowser(tourId, tourPlanId);
            }
//                if (uri.getScheme() != null && uri.getScheme().equals("http") && uri.getHost() != null && uri.getHost()
//                        .equals("joco.com.vn")) {
//                    String[] path = uri.getPath().split("/", 5);
//                    tourId = Long.valueOf(path[2]);
//                    tourPlanId = Long.valueOf(path[4]);
//                    initFromBrowser(tourId, tourPlanId, uri, getIntent());
//                }
        } catch (Exception e) {
            Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(e));
        }
        //    else {
//            initViews();
//        }
//        initData();
    }


}
