package com.boot.accommodation.vp.placepicker;

import android.util.Log;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.base.BaseResponse;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.PlacePickerDetailItemPhotoDTO;
import com.boot.accommodation.dto.response.PlacePickerDetailResponseDTO;
import com.boot.accommodation.dto.response.PlacePickerItemDTO;
import com.boot.accommodation.dto.view.AddressGGDTO;
import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.AreaTypeDTO;
import com.boot.accommodation.dto.view.ContactDTO;
import com.boot.accommodation.dto.view.CreateLocationDTO;
import com.boot.accommodation.dto.view.LocationTypeDTO;
import com.boot.accommodation.dto.view.MediaDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.model.impl.PlacePickerModel;
import com.boot.accommodation.model.mgr.HomeModelMgr;
import com.boot.accommodation.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Place picker presenter
 *
 * @author tuanlt
 * @since 3:57 CH 09/09/2016
 */
public class PlacePickerPresenter {

    PlacePickerViewMgr placePickerViewMgr; // View
    PlacePickerModel placePickerModel; // Place picker
    HomeModelMgr homeModelMgr;// Home mgr
    public final String hotel = "hotel"; // 105
    public final String restaurant = "restaurant";
    public final String attraction = "attraction";
    public final String museum = "museum";
    public final String bar = "bar";
    public final String natural_feature = "natural_feature";
    public final String park = "park";
    public final String amusement_park = "amusement_park";
//    public final String aquarium = "aquarium";
    public final String stadium = "stadium";
    public final String zoom = "zoom";

//    amusement_park, aquarium, art_gallery, casino, church, movie_theater, museum, night_club, park, place_of_worship, shopping_mall, stadium, zoo

    public PlacePickerPresenter( PlacePickerViewMgr placePickerViewMgr ) {
        this.placePickerViewMgr = placePickerViewMgr;
        placePickerModel = new PlacePickerModel();
        homeModelMgr = new HomeModel();
//        getListPlacesAll(10.784892, 106.68302);
    }

    /**
     * Get list place all
     */
    public void getListPlacesAll(double lat, double lng){
        getListPlaceGG(lat, lng, hotel);
        getListPlaceGG(lat, lng, restaurant);
//        getListPlaceGG(lat, lng, attraction);
        getListPlaceGG(lat, lng, museum);
//        getListPlaceGG(lat, lng, bar);
        getListPlaceGG(lat, lng, park);
        getListPlaceGG(lat, lng, amusement_park);
        getListPlaceGG(lat, lng, natural_feature);
//        getListPlaceGG(lat, lng, stadium);
//        getListPlaceGG(lat, lng, zoom);
    }

    /**
     * Get list place google
     *
     * @param lat
     * @param lng
     * @param type
     */
    public void getListPlaceGG(double lat, final double lng, final String type ) {
        // Out of vietnam
        if (lat < 8.45 || lng < 102.0
                || lat > 23.5 || lng > 110) {
            Log.d(Constants.LogName.LOG_GPS, "updatePosition, lng = "
                    + JoCoApplication.getInstance().getProfile().getMyGPSInfo()
                    .getLongtitude() + " lat = ");
            return;
        }
//        placePickerModel.getListPlaceGG(lat, lng, type, "", new ModelCallBack<PlacePickerResponseDTO>() {
//            @Override
//            public void onSuccess(PlacePickerResponseDTO response) {
//                if (response.getResults() != null) {
//                    placePickerViewMgr.savePlace(response.getResults(), getTypeValue(type));
//                    getPlaceDetail(response.getResults(), type);
////                    for(PlacePickerItemDTO item: response.getResults()){
////                        if(!placePickerViewMgr.checkPlaceScanned(item.getPlace_id())){
////                            getListPlaceGG(item.getGeometry().getLocation().getLat(), item.getGeometry().getLocation
////                                    ().getLng(), type);
////                        }
////                    }
//                }
//            }
//
//            @Override
//            public void onError(int errorCode, String error) {
//
//            }
//
//            @Override
//            public void onFinishAllProcess() {
//
//            }
//        });
    }

    int i = 0;
    /**
     * Get place detail
     */
    public void getPlaceDetail(List<PlacePickerItemDTO> lstPlace, final String type) {
        for (PlacePickerItemDTO item : lstPlace) {
            if (!placePickerViewMgr.checkPlaceScanned(item.getPlace_id())) {
                placePickerViewMgr.savePlaceScanned(item.getPlace_id());
//            if (i > 50)
//                break;
//            i++;
                placePickerModel.getPlaceDetail(item.getPlace_id(), new ModelCallBack<PlacePickerDetailResponseDTO>() {
                    @Override
                    public void onSuccess(PlacePickerDetailResponseDTO response) {
                        if (response != null && response.getResult().getTypes() != null && response.getResult()
                                .getTypes().contains(type) ) {
                            List<MediaDTO> mediaDTOs = new ArrayList<>();
                            MediaDTO avatar = new MediaDTO();
                            boolean isFirst = false;
                            // Handle image
                            if (response.getResult().getPhotos() != null) {
                                for (PlacePickerDetailItemPhotoDTO photo : response.getResult().getPhotos()) {
                                    MediaDTO dto = new MediaDTO();
                                    dto.setUri(photo.getPhoto_reference());
                                    mediaDTOs.add(dto);
                                    if (!isFirst) {
                                        isFirst = true;
                                        avatar = dto;
                                    }
                                }
                            }
                            CreateLocationDTO createLocationDTO = new CreateLocationDTO();
                            ContactDTO contactDTO = new ContactDTO();
                            contactDTO.setPhone(response.getResult().getFormatted_phone_number());
                            createLocationDTO.setAddress(response.getResult().getFormatted_address());
                            createLocationDTO.setContact(contactDTO);
                            createLocationDTO.setName(response.getResult().getName());
                            createLocationDTO.setMedias(mediaDTOs);
                            createLocationDTO.setAvatar(avatar);
                            createLocationDTO.setDescription("");
                            createLocationDTO.setCoordinate(response.getResult().getGeometry().getLocation());
                            createLocationDTO.setCategories(placePickerViewMgr.getListCategory(response.getResult().getPlace_id()));
                            createLocationDTO.setLocationType(LocationTypeDTO.GOOGLE.getValue());
                            createLocationDTO.setWebsite(response.getResult().getWebsite());
                            if(response.getResult().getOpening_hours() != null) {
                                StringBuilder openTime = new StringBuilder();
                                for (String str : response.getResult().getOpening_hours()
                                        .getWeekday_text() ) {
                                    if(!StringUtil.isNullOrEmpty(openTime.toString())){
                                        openTime.append(Constants.STR_TOKEN);
                                    }
                                    openTime.append(str.toString());
                                }
                                createLocationDTO.setOpeningText(openTime.toString());
                            }
                            List<AreaDTO> areas = new ArrayList<AreaDTO>();
                            createLocationDTO.setAreas(areas);
                            if (response.getResult().getAddress_components() != null && response.getResult()
                                    .getAddress_components().size() > 0) {
                                int size = response.getResult().getAddress_components().size();
                                for (int i = size - 1; i >= 0; i--) {
                                    AddressGGDTO addressTypeDTO = response.getResult().getAddress_components().get(i);
                                    AreaDTO areaDTO = new AreaDTO();
                                    areaDTO.setLongName(addressTypeDTO.getLong_name().trim());
                                    areaDTO.setShortName(addressTypeDTO.getShort_name().trim());
                                    if (addressTypeDTO.getTypes() != null && addressTypeDTO.getTypes().size() > 0) {
                                        if ("country".equals(addressTypeDTO.getTypes().get(0))) {
                                            areaDTO.setType(AreaTypeDTO.COUNTRY.getValue());
                                            areas.add(areaDTO);
                                            if(!("VN".equals(areaDTO.getShortName().trim()))){
                                                return;
                                            }
                                        } else if ("administrative_area_level_1".equals(addressTypeDTO.getTypes().get(0))) {
                                            areaDTO.setType(AreaTypeDTO.PROVINCE.getValue());
                                            areas.add(areaDTO);
                                        }
//                                        else if ("locality".equals(addressTypeDTO.getTypes().get(0))) {
//                                            areaDTO.setType(AreaTypeDTO.COUNTRY.getValue());
//                                        } else if ("route".equals(addressTypeDTO.getTypes().get(0))) {
//                                            areaDTO.setType(AreaTypeDTO.ROUTE.getValue());
//                                        } else if ("street_number".equals(addressTypeDTO.getTypes().get(0))) {
//                                            areaDTO.setType(AreaTypeDTO.STREET_NUMBER.getValue());
//                                        }
                                    }
                                }
                            }
                            homeModelMgr.requestCreateLocation(createLocationDTO, new ModelCallBack() {
                                @Override
                                public void onSuccess(BaseResponse response) {

                                }

                                @Override
                                public void onError(int errorCode, String error) {

                                }

                            });
                        }

                    }

                    @Override
                    public void onError(int errorCode, String error) {

                    }

                });
            }
        }
    }

    /**
     * Get value of type
     * @param type
     * @return
     */
    private long getTypeValue(String type){
        switch (type){
            case hotel:
                return 105;
            case restaurant:
                return 106;
            case attraction:
                return 107;
            case museum:
                return 108;
            case park:
            case amusement_park:
                return 121;
            case natural_feature:
                return 122;
            case stadium:
                return 123;
            case zoom:
                return 124;
        }
        return 109;
    }

}
