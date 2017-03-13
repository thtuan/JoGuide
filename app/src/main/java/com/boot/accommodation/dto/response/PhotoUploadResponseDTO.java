package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.PhotoUploadItemDTO;

import java.util.List;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since 5:00 PM 7/21/2016
 */
public class PhotoUploadResponseDTO extends BaseResponseDTO {
    private List<PhotoUploadItemDTO> data;

    public List<PhotoUploadItemDTO> getData() {
        return data;
    }

    public void setData( List<PhotoUploadItemDTO> data ) {
        this.data = data;
    }
}
