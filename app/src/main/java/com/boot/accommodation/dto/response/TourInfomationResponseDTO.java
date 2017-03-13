package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TourInformationDTO;

/**
 * Tourinfomationresponse  cho man hinh tour info expand
 *
 * @author vuong-bv
 * @since: 9:25 AM 5/31/2016
 */
public class TourInfomationResponseDTO extends BaseResponseDTO {
    /**
     * set data
     */
    private TourInformationDTO data;

    /**
     * method get
     * @return
     */
    public TourInformationDTO getData() {
        return data;
    }

    /**
     * method set
     * @param data
     */
    public void setData( TourInformationDTO data ) {
        this.data = data;
    }
}
