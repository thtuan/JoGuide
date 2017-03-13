package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.dto.response.PhotoUploadResponseDTO;
import com.boot.accommodation.dto.response.PlaceDetailPhotoResponseDTO;
import com.boot.accommodation.dto.view.MediaDTO;
import com.boot.accommodation.dto.view.PhotoDTO;
import com.boot.accommodation.dto.view.PlacePhotoTypeDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.model.impl.LocationModel;
import com.boot.accommodation.model.mgr.HomeModelMgr;
import com.boot.accommodation.model.mgr.LocationModelMgr;
import com.boot.accommodation.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Mo ta class
 *
 * @author Dungnx
 */
public class LocationDetailPhotosPresenter implements LocationDetailPhotosPresenterMgr {
    LocationDetailPictureViewMgr placeDetailPhotosMgr;
    LocationModelMgr locationModelMgr;
    private HomeModelMgr homeModelMgr;
    private int pageOwner = 0; // page
    private int pageJoco = 0; // page
    private int pageSizeJoco = 0; // page size
    private int pageSizeOwner = 0; // page
    private List<PhotoDTO> lstPhotoJoco = new ArrayList<>(); // photo joco
    private List<PhotoDTO> lstPhotoOwner = new ArrayList<>(); // photo owner
    private String TAG = ""; // Tag

    public LocationDetailPhotosPresenter(LocationDetailPictureViewMgr placeDetailViewMgr) {
        this.placeDetailPhotosMgr = placeDetailViewMgr;
        locationModelMgr = new LocationModel();
        homeModelMgr = new HomeModel();
        TAG = Utils.getTag(placeDetailPhotosMgr.getClass());
    }

    @Override
    public void getPlacePhoto(long placeId) {
        pageOwner = 0;
        pageJoco = 0;
        pageSizeJoco = 0;
        pageSizeOwner = 0;
        lstPhotoJoco.clear();
        lstPhotoOwner.clear();
        locationModelMgr.getPlacePhoto(placeId, PlacePhotoTypeDTO.ALL.getValue(), 0, new
            ModelCallBack<PlaceDetailPhotoResponseDTO>(TAG) {
                @Override
                public void onSuccess( PlaceDetailPhotoResponseDTO response ) {
                    lstPhotoJoco.addAll(response.getData().getPhotoJoco().getPhoto());
                    pageSizeJoco = response.getData().getPhotoJoco().getPaging().getPageSize();
                    placeDetailPhotosMgr.renderPhotoJoco(lstPhotoJoco);
                    lstPhotoOwner.addAll(response.getData().getPhotoOwner().getPhoto());
                    pageSizeOwner = response.getData().getPhotoOwner().getPaging().getPageSize();
                    if(lstPhotoJoco.size() > 0 || lstPhotoOwner.size() > 0) {
                        placeDetailPhotosMgr.renderPhotoOwner(lstPhotoOwner);
                        placeDetailPhotosMgr.renderNoData(false);
                    } else {
                        placeDetailPhotosMgr.renderNoData(true);
                    }
                }

                @Override
                public void onError( int errorCode, String error ) {
                    placeDetailPhotosMgr.showMessageErr(errorCode, error);
                }

            });
    }

    @Override
    public void getMorePlacePhotoJoco( long placeId, BaseRecyclerViewAdapter adapter ) {
        if (Utils.checkLoadMore(adapter, pageSizeJoco, lstPhotoJoco.size())) {
            pageJoco++;
            locationModelMgr.getPlacePhoto(placeId, PlacePhotoTypeDTO.TYPE_JOCO.getValue(), pageJoco, new
                ModelCallBack<PlaceDetailPhotoResponseDTO>(TAG) {
                    @Override
                    public void onSuccess( PlaceDetailPhotoResponseDTO response ) {
                        pageSizeJoco = response.getData().getPhotoJoco().getPaging().getPageSize();
                        lstPhotoJoco.addAll(response.getData().getPhotoJoco().getPhoto());
                        placeDetailPhotosMgr.renderPhotoJoco(lstPhotoJoco);
                    }

                    @Override
                    public void onError( int errorCode, String error ) {
                        placeDetailPhotosMgr.showMessageErr(errorCode, error);
                    }

                });
        }
    }

    @Override
    public void getMorePlacePhotoOwner( long placeId, BaseRecyclerViewAdapter adapter ) {
        if (Utils.checkLoadMore(adapter, pageSizeOwner, lstPhotoOwner.size())) {
            pageOwner++;
            locationModelMgr.getPlacePhoto(placeId, PlacePhotoTypeDTO.TYPE_OWNER.getValue(), pageOwner, new
                ModelCallBack<PlaceDetailPhotoResponseDTO>(TAG) {
                    @Override
                    public void onSuccess( PlaceDetailPhotoResponseDTO response ) {
                        pageSizeOwner = response.getData().getPhotoOwner().getPaging().getPageSize();
                        lstPhotoOwner.addAll(response.getData().getPhotoOwner().getPhoto());
                        placeDetailPhotosMgr.renderPhotoOwner(lstPhotoOwner);
                    }

                    @Override
                    public void onError( int errorCode, String error ) {
                        placeDetailPhotosMgr.showMessageErr(errorCode, error);
                    }

                });
        }
    }

    @Override
    public void requestUpLoadPhotoToStatic(final long locationId, File file, final PhotoDTO photoDTO) {
        lstPhotoOwner.add(photoDTO);
        homeModelMgr.uploadPhoto(file, new ModelCallBack<PhotoUploadResponseDTO>(TAG) {
            @Override
            public void onSuccess(PhotoUploadResponseDTO response) {
                if (response.getData().size() > 0) {
                    photoDTO.setProgressPercentage(100);
                    if (response.getData().size() > 0) {
                        List<MediaDTO> mediaDTOs = new ArrayList<>();
                        MediaDTO mediaDTO = new MediaDTO();
                        mediaDTO.setUri(response.getData().get(0).getURL());
                        mediaDTOs.add(mediaDTO);
                        photoDTO.setUrl(response.getData().get(0).getURL());
                        requestUpLoadPhoto(photoDTO, locationId, mediaDTOs);
                    }
                }
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailPhotosMgr.showMessageErr(errorCode, error);
            }

        }, new ProgressUpdateBody.MultiUploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage, int postion) {
                photoDTO.setProgressPercentage(percentage);
                placeDetailPhotosMgr.renderPhotoOwner(lstPhotoOwner);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onFinish(int position) {

            }
        },0 );
    }

    /**
     * Upload photo location
     * @param locationId
     */
    public void requestUpLoadPhoto(final PhotoDTO photoDTO, final long locationId, List<MediaDTO> mediaDTOs) {
        locationModelMgr.requestUploadLocationPhoto(locationId, mediaDTOs, new ModelCallBack<PhotoUploadResponseDTO>() {
            @Override
            public void onSuccess(PhotoUploadResponseDTO response) {
                if(response != null && response.getData().size() > 0){
                    photoDTO.setUrl(response.getData().get(0).getURL());
                    placeDetailPhotosMgr.renderPhotoOwner(lstPhotoOwner);
                }
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailPhotosMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
