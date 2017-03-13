package com.boot.accommodation.vp.tour;

import com.boot.accommodation.R;
import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.response.PhotoUploadResponseDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.ProfileModel;
import com.boot.accommodation.model.mgr.ProfileModelMgr;
import com.boot.accommodation.util.Utils;

import java.io.File;

/**
 * Profile presenter
 *
 * @author tuanlt
 * @since 11:55 PM 7/24/2016
 */
public class ProfilePresenter implements ProfilePresenterMgr {

    ProfileViewMgr profileViewMgr; // profile view
    ProfileModelMgr profileModelMgr; // profile model
    private String TAG = ""; // Tag

    public ProfilePresenter(ProfileViewMgr profileViewMgr){
        this.profileViewMgr = profileViewMgr;
        profileModelMgr = new ProfileModel();
        TAG = Utils.getTag(profileViewMgr.getClass());
    }

    @Override
    public void uploadPhoto( File file ) {
        profileModelMgr.uploadPhoto(file, new ModelCallBack<PhotoUploadResponseDTO>(TAG) {
            @Override
            public void onSuccess(PhotoUploadResponseDTO response) {
                if (response.getData().size() > 0) {
                    profileViewMgr.updateFileNameUpload(response.getData().get(0).getURL());
                }
            }

            @Override
            public void onError(int errorCode, String error) {

            }

        }, new ProgressUpdateBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                profileViewMgr.updateProgressBar(percentage);
            }

            @Override
            public void onError() {
                profileViewMgr.updateError(Utils.getString(R.string.error_unknown_error));
            }

            @Override
            public void onFinish() {
                profileViewMgr.finishUpdatePhoto();
            }
        });
    }

    @Override
    public void requestUpLoadPhoto( long userId,final String photo ) {
        profileModelMgr.requestUploadPhoto(userId, photo, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess( CommonResponseDTO response ) {
                profileViewMgr.updateAvatar(String.valueOf(response.getData()));
            }

            @Override
            public void onError( int errorCode, String error ) {

            }

        });
    }
}
