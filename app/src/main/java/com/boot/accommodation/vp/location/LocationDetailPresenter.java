package com.boot.accommodation.vp.location;

import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.view.PlacePickerDetailItemDTO;
import com.boot.accommodation.dto.response.PlacePickerDetailItemPhotoDTO;
import com.boot.accommodation.dto.response.PlacePickerDetailResponseDTO;
import com.boot.accommodation.dto.response.PlaceResponseDTO;
import com.boot.accommodation.dto.view.ContactDTO;
import com.boot.accommodation.dto.view.CreateLocationDTO;
import com.boot.accommodation.dto.view.GPSInfoDTO;
import com.boot.accommodation.dto.view.LocationDTO;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.MediaDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.model.impl.LocationModel;
import com.boot.accommodation.model.impl.PlacePickerModel;
import com.boot.accommodation.model.mgr.HomeModelMgr;
import com.boot.accommodation.model.mgr.LocationModelMgr;
import com.boot.accommodation.model.mgr.PlacePickerModelMgr;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter cho place detail
 *
 * @author tuanlt
 * @since: 11:31 AM 5/5/2016
 */
public class LocationDetailPresenter implements LocationDetailPresenterMgr {

    private LocationDetailViewMgr placeDetailViewMgr;
    private LocationModelMgr locationModelMgr;
    private int page = 0;
    private int pageSize = 5;
    private List<PlaceItemDTO> lstPlaceDTO = new ArrayList<>();
    private GPSInfoDTO locationInfo;
    private String TAG = ""; // Tag
    private HomeModelMgr homeModelMgr; //Home model
    PlacePickerModelMgr placePickerModelMgr; // Place picker

    public LocationDetailPresenter(LocationDetailViewMgr placeDetailViewMgr){
        this.placeDetailViewMgr = placeDetailViewMgr;
        locationModelMgr = new LocationModel();
        homeModelMgr = new HomeModel();
        placePickerModelMgr = new PlacePickerModel();
        TAG = Utils.getTag(placeDetailViewMgr.getClass());
    }

    @Override
    public void initDataHeader(PlaceItemDTO item) {
//        placeDetailViewMgr.renderHeader(item);
        placeDetailViewMgr.initViewPager(item);
    }

    @Override
    public void requestFavouritePlace(Long placeId) {
        locationModelMgr.requestFavouritePlace(placeId, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                placeDetailViewMgr.setFavourite((Boolean) response.getData());
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeDetailViewMgr.showMessageErr(errorCode, error);
            }

        });

    }


    @Override
    public void requestCheckin(GPSInfoDTO locationInfo) {
        page = 0;
        this.locationInfo = locationInfo;
        lstPlaceDTO.clear();
        locationModelMgr.getAllPlaceCheckIn(page, locationInfo.getLatitude(), locationInfo.getLongtitude(), new
            ModelCallBack<PlaceResponseDTO>(TAG) {
            @Override
            public void onSuccess(PlaceResponseDTO response) {
                getPlaceSuccess(response);
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeDetailViewMgr.showMessageErr(errorCode, error);
            }

            });
    }

    /**
     * create checkin
     * @param placeItemDTO
     */
    @Override
    public void createCheckIn(PlaceItemDTO placeItemDTO) {
        locationModelMgr.createLocationCheckIn(placeItemDTO, new ModelCallBack<PlaceResponseDTO>(TAG) {
            @Override
            public void onSuccess(PlaceResponseDTO response) {
            }

            @Override
            public void onError( int errorCode, String error ) {
                placeDetailViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    /**
     * get more place check in
     * @param adapter
     */
    @Override
    public void getMorePlaces(BaseRecyclerViewAdapter adapter) {
        // Xu li load more nua hay ko
        if (Utils.checkLoadMore(adapter, pageSize, lstPlaceDTO.size())) {
            page++;
            requestCheckin(locationInfo);
        }
    }

    @Override
    public void getPlaceDetailGG(final PlaceItemDTO item) {
        placePickerModelMgr.getPlaceDetail(item.getPlaceIdGG(), new
                ModelCallBack<PlacePickerDetailResponseDTO>(TAG) {
                    @Override
                    public void onSuccess(PlacePickerDetailResponseDTO response) {
                        PlacePickerDetailItemDTO data = response.getResult();
                        //Image have size > 0
                        if (response != null) {
                            item.setName(data.getName());
                            item.setLocationType(LocationTypeDTO.GOOGLE.getValue());
                            item.setAddress(data.getFormatted_address());
                            item.setPhoneNumber(data.getFormatted_phone_number());
                            item.setWebsite(data.getWebsite());
                            item.setLat(data.getGeometry().getLocation().getLat());
                            item.setLng(data.getGeometry().getLocation().getLng());
                            if (data.getPhotos() != null) {
                                item.setPhoto(data.getPhotos().size() > 0 ? response
                                        .getResult().getPhotos().get(0)
                                        .getPhoto_reference() : "");
                                item.setPhotos(new ArrayList<String>());
                                for (PlacePickerDetailItemPhotoDTO photoItem : data.getPhotos()) {
                                    item.getPhotos().add(photoItem.getPhoto_reference());
                                }
                            }
//                            placeItemDTO.setOpeningTime(data.getOpening_hours().getWeekday_text().toString());
                            requestSendPlaceToServer(item);
                        }

                    }

                    @Override
                    public void onError(int errorCode, String error) {
                        placeDetailViewMgr.showMessageErr(errorCode, error);
                    }

                });
    }

    /**
     * get places success
     * @param response
     */
    private void getPlaceSuccess( PlaceResponseDTO response ) {
        page = response.getData().getPaging().getPage();
        pageSize = response.getData().getPaging().getPageSize();
        lstPlaceDTO.addAll(response.getData().getLocations());
        if(page == 0){
            placeDetailViewMgr.checkinSucess(lstPlaceDTO);
        } else {
            placeDetailViewMgr.renderPopup(lstPlaceDTO);
        }
    }

    /**
     * Request send place to server
     * @param item
     */
    private void requestSendPlaceToServer(final PlaceItemDTO item){
        List<MediaDTO> mediaDTOs = new ArrayList<>();
        MediaDTO avatar = new MediaDTO();
        boolean isFirst = false;
        // Handle image
        for (String photo : item.getPhotos()) {
            MediaDTO dto = new MediaDTO();
            dto.setUri(photo);
            if (!isFirst) {
                isFirst = true;
                avatar = dto;
            } else {
                mediaDTOs.add(dto);
            }
        }
        CreateLocationDTO createLocationDTO = new CreateLocationDTO();
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setPhone(item.getPhoneNumber());
        createLocationDTO.setAddress(item.getAddress());
        createLocationDTO.setContact(contactDTO);
        createLocationDTO.setName(item.getName());
        createLocationDTO.setMedias(mediaDTOs);
        createLocationDTO.setAvatar(avatar);
        createLocationDTO.setDescription("");
        createLocationDTO.setCoordinate(new LocationDTO(item.getLat(),item.getLng()));
        createLocationDTO.setCategories(item.getCategoryId());
        createLocationDTO.setLocationType(LocationTypeDTO.GOOGLE.getValue());
        createLocationDTO.setWebsite(item.getWebsite());
//        createLocationDTO.setOpeningText(item.getO.toString());
        homeModelMgr.requestCreateLocation(createLocationDTO, new ModelCallBack<CommonResponseDTO>(TAG) {
            @Override
            public void onSuccess(CommonResponseDTO response) {
                item.setId(Math.round((Double)response.getData()));
                initDataHeader(item);
            }

            @Override
            public void onError(int errorCode, String error) {
                placeDetailViewMgr.showMessageErr(errorCode, error);
            }

        });
    }
}
