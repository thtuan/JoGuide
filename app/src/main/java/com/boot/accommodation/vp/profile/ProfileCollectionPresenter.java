package com.boot.accommodation.vp.profile;

import com.boot.accommodation.dto.response.ProfileCollectionResponseDTO;
import com.boot.accommodation.dto.view.PlaceCollectionDTO;
import com.boot.accommodation.dto.view.TourCollectionDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.ProfileModel;
import com.boot.accommodation.model.mgr.ProfileModelMgr;
import com.boot.accommodation.util.Utils;

/**
 * Presenter collection
 *
 * @author tuanlt
 * @since 6:47 PM 6/6/2016
 */
public class ProfileCollectionPresenter implements ProfileCollectionPresenterMgr {

    private ProfileCollectionViewMgr profileCollectionViewMgr; // view
    private ProfileModelMgr profileModelMgr; // profile model
    private PlaceCollectionDTO favouritePlaces = new PlaceCollectionDTO();  // lst favourite
    private TourCollectionDTO favouriteTours = new TourCollectionDTO(); //Tour collection model
    private TourCollectionDTO tourCreated = new TourCollectionDTO();
    private PlaceCollectionDTO placesCreated = new PlaceCollectionDTO();  // lst favourite
    private String TAG = ""; // Tag

    public ProfileCollectionPresenter(ProfileCollectionViewMgr profileCollectionViewMgr) {
        this.profileCollectionViewMgr = profileCollectionViewMgr;
        profileModelMgr = new ProfileModel();
        TAG = Utils.getTag(profileCollectionViewMgr.getClass());
    }

    @Override
    public void getProfileCollection( long userId) {
        profileModelMgr.getProfileCollection(userId, new ModelCallBack<ProfileCollectionResponseDTO>(TAG) {

            @Override
            public void onSuccess( ProfileCollectionResponseDTO response ) {
                favouriteTours = response.getData().getFavouriteTours();
                favouritePlaces = response.getData().getFavouritePlaces();
                tourCreated = response.getData().getToursCreated();
                placesCreated = response.getData().getLocationsCreated();
                profileCollectionViewMgr.renderFavouriteTours(favouriteTours);
                profileCollectionViewMgr.renderFavouritePlaces(favouritePlaces);
                profileCollectionViewMgr.renderToursCreated(tourCreated);
                profileCollectionViewMgr.renderPlacesCreated(placesCreated);
            }

            @Override
            public void onError( int errorCode, String error ) {
                profileCollectionViewMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
