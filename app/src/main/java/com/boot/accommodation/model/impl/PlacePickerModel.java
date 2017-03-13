package com.boot.accommodation.model.impl;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseModel;
import com.boot.accommodation.dto.response.PlacePickerDetailResponseDTO;
import com.boot.accommodation.dto.response.PlacePickerResponseDTO;
import com.boot.accommodation.dto.view.LocationCategoryTypeDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.mgr.PlacePickerModelMgr;
import com.boot.accommodation.util.StringUtil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Place picker model
 *
 * @author tuanlt
 * @since 4:03 CH 09/09/2016
 */
public class PlacePickerModel extends BaseModel implements PlacePickerModelMgr {

    private interface Interface {
        @GET
        Call<PlacePickerResponseDTO> getListPlaceGG(
            @Url String url );

        @POST
        Call<PlacePickerDetailResponseDTO> getPlaceDetail(
            @Url String url );

    }

    @Override
    public void getListPlaceGG(double lat, double lng, long categoryId, String name, ModelCallBack modelCallBack) {
        StringBuilder builder = new StringBuilder();
//        builder.append("https://maps.googleapis.com/maps/api/place/radarsearch/json?");
        builder.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        builder.append("location=").append(lat).append(",").append(lng);
        builder.append("&radius=").append(LocationCategoryTypeDTO.getRadiusByCategory(categoryId));
        builder.append("&ranking=").append("distance");
        builder.append("&types=").append(LocationCategoryTypeDTO.getNameByValue(categoryId));
        if(!StringUtil.isNullOrEmpty(name)){
            builder.append("&name=").append(name);
        }
        builder.append("&key=").append(StringUtil.getString(R.string.google_api_key));
        Interface service = RETROFIT.create(Interface.class);
        call = service.getListPlaceGG(builder.toString());
        requestAPI(modelCallBack);
    }

    @Override
    public void getPlaceDetail( String placeId, ModelCallBack modelCallBack ) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://maps.googleapis.com/maps/api/place/details/json?");
        builder.append("placeid=").append(placeId);
        builder.append("&key=").append(StringUtil.getString(R.string.google_api_key));
        Interface service = RETROFIT.create(Interface.class);
        call = service.getPlaceDetail(builder.toString());
        requestAPI(modelCallBack);
    }

    @Override
    public void getListPlaceGGNext(double lat, double lng, long categoryId, String pageToken, ModelCallBack
            modelCallBack) {
        StringBuilder builder = new StringBuilder();
//        builder.append("https://maps.googleapis.com/maps/api/place/radarsearch/json?");
        builder.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        builder.append("location=").append(lat).append(",").append(lng);
        builder.append("&radius=").append(LocationCategoryTypeDTO.getRadiusByCategory(categoryId));
        builder.append("&ranking=").append("distance");
        builder.append("&types=").append(LocationCategoryTypeDTO.getNameByValue(categoryId));
        builder.append("&hasNextPage=true&nextPage()=true&sensor=false");
        builder.append("&key=").append(StringUtil.getString(R.string.google_api_key));
        builder.append("&pagetoken=").append(pageToken);
        Interface service = RETROFIT.create(Interface.class);
        call = service.getListPlaceGG(builder.toString());
        requestAPI(modelCallBack);
    }

    @Override
    public void getListPlaceGGWithoutPosition(String textSearch, long categoryId, ModelCallBack modelCallBack) {
        StringBuilder builder = new StringBuilder();
//        builder.append("https://maps.googleapis.com/maps/api/place/radarsearch/json?");
        builder.append("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        builder.append("query=").append(textSearch);
        builder.append("&types=").append(LocationCategoryTypeDTO.getNameByValue(categoryId));
        builder.append("&key=").append(StringUtil.getString(R.string.google_api_key));
        Interface service = RETROFIT.create(Interface.class);
        call = service.getListPlaceGG(builder.toString());
        requestAPI(modelCallBack);
    }

    @Override
    public void getListPlaceGGNextWithoutPosition(long categoryId, String pageToken, ModelCallBack modelCallBack) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        builder.append("types=").append(LocationCategoryTypeDTO.getNameByValue(categoryId));
        builder.append("&hasNextPage=true&nextPage()=true&sensor=false");
        builder.append("&key=").append(StringUtil.getString(R.string.google_api_key));
        builder.append("&pagetoken=").append(pageToken);
        Interface service = RETROFIT.create(Interface.class);
        call = service.getListPlaceGG(builder.toString());
        requestAPI(modelCallBack);
    }

}
