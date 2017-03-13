package com.boot.accommodation.vp.category;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseResponse;
import com.boot.accommodation.common.control.ProgressUpdateBody;
import com.boot.accommodation.dto.response.PhotoUploadResponseDTO;
import com.boot.accommodation.dto.view.CategoryItemDTO;
import com.boot.accommodation.dto.view.CreateLocationDTO;
import com.boot.accommodation.dto.view.CreateLocationViewDTO;
import com.boot.accommodation.dto.view.LocationCategoryTypeDTO;
import com.boot.accommodation.dto.view.UploadPhotoDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.HomeModel;
import com.boot.accommodation.model.mgr.HomeModelMgr;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * CreateLocationPresenter
 *
 * @author thtuan
 * @since 4:46 PM 31-07-2016
 */
public class CreateLocationPresenter implements CreateLocationPresenterMgr {
    private HomeModelMgr homeModelMgr;
    private CreateLocationViewMgr createLocationViewMgr;
    private List<UploadPhotoDTO> uploadPhotoDTOs = new ArrayList<>();
    private int position = 0; // position update
    private String TAG = ""; // Tag

    public CreateLocationPresenter(CreateLocationViewMgr createLocationViewMgr) {
        this.createLocationViewMgr = createLocationViewMgr;
        homeModelMgr = new HomeModel();
        TAG = Utils.getTag(createLocationViewMgr.getClass());
    }

    @Override
    public void uploadPhoto(final UploadPhotoDTO uploadPhotoDTO) {
        /*final UploadPhotoDTO uploadPhotoDTO = new UploadPhotoDTO();
        final int holdPostion = position;
        position ++;
        uploadPhotoDTO.setPath(url);
        uploadPhotoDTO.setProgressPercentage(0);
        uploadPhotoDTOs.add(holdPostion,uploadPhotoDTO);*/
//        createLocationViewMgr.renderLayoutMonth(uploadPhotoDTOs);
        homeModelMgr.uploadPhoto(uploadPhotoDTO.getFile(), new ModelCallBack<PhotoUploadResponseDTO>(TAG) {
            @Override
            public void onSuccess(PhotoUploadResponseDTO response) {
                if (response.getData().size() > 0) {
                    createLocationViewMgr.updateFileNameUpload(response.getData().get(0).getURL());
                }
            }

            @Override
            public void onError(int errorCode, String error) {
                createLocationViewMgr.showMessageErr(errorCode, error);
            }

        }, new ProgressUpdateBody.MultiUploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage, int postion) {
                uploadPhotoDTO.setProgressPercentage(percentage);
                createLocationViewMgr.renderLayout(uploadPhotoDTOs);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onFinish(int postion) {

            }
        },0 );
    }

    /**
     *  create location on mobile
     * @param createLocationDTO
     */
    @Override
    public void requestCreateLocation(CreateLocationDTO createLocationDTO) {
        homeModelMgr.requestCreateLocation(createLocationDTO, new ModelCallBack(TAG) {
            @Override
            public void onSuccess(BaseResponse response) {
                createLocationViewMgr.onSuccess();
            }

            @Override
            public void onError(int errorCode, String error) {
                createLocationViewMgr.showMessageErr(errorCode, error);
            }

        });
    }

    @Override
    public void getCategory() {
        List<CategoryItemDTO> categoryLocationItems = new ArrayList<>();
        CategoryItemDTO hotel = new CategoryItemDTO();
        hotel.setId(LocationCategoryTypeDTO.HOTEL.getValue());
        hotel.setName(StringUtil.getString(R.string.text_hotel));
        CategoryItemDTO hostel = new CategoryItemDTO();
        hostel.setId(LocationCategoryTypeDTO.HOSTEL.getValue());
        hostel.setName(Character.toString(LocationCategoryTypeDTO.HOSTEL.name().charAt(0)).toUpperCase()
                + LocationCategoryTypeDTO.HOSTEL.name().substring(1).toLowerCase());
        CategoryItemDTO homeStay = new CategoryItemDTO();
        homeStay.setId(LocationCategoryTypeDTO.HOMESTAY.getValue());
        homeStay.setName(Character.toString(LocationCategoryTypeDTO.HOMESTAY.name().charAt(0)).toUpperCase()
                + LocationCategoryTypeDTO.HOMESTAY.name().substring(1).toLowerCase());
        categoryLocationItems.add(hotel);
        categoryLocationItems.add(hostel);
        categoryLocationItems.add(homeStay);
        CreateLocationViewDTO createLocationViewDTO = new CreateLocationViewDTO();
        createLocationViewDTO.setCategory(categoryLocationItems);
        createLocationViewMgr.getCategorySuccess(createLocationViewDTO);
//        homeModelMgr.getCategory(new ModelCallBack<CategoryLocationReponseDTO>(TAG) {
//            @Override
//            public void onSuccess(CategoryLocationReponseDTO response) {
//                createLocationViewMgr.getCategorySuccess(response.getData());
//            }
//
//            @Override
//            public void onError(int errorCode, String error) {
//                createLocationViewMgr.showMessageErr(errorCode, error);
//            }
//
//        });

    }

    @Override
    public void deletePhoto(int position) {
        uploadPhotoDTOs.remove(position);
        createLocationViewMgr.renderLayout(uploadPhotoDTOs);
    }

    @Override
    public void addPhoto(UploadPhotoDTO uploadPhotoDTO) {
        uploadPhotoDTOs.add(0,uploadPhotoDTO);
        uploadPhoto(uploadPhotoDTOs.get(0));
        createLocationViewMgr.renderLayout(uploadPhotoDTOs);
    }

    @Override
    public boolean checkUploadDone() {
        for (UploadPhotoDTO upload:
             uploadPhotoDTOs) {
            if (upload.getProgressPercentage() < 100){
                return false;
            }
        }
        return true;
    }
}
