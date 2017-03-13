package com.boot.accommodation.dto.response;

import com.boot.accommodation.dto.view.TouristLeaderDTO;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 5/19/2016
 */
public class TouristLeaderResponseDTO extends BaseResponseDTO {
    private TouristLeaderDTO  data;
    public TouristLeaderDTO getData(){
        return data;
    }
    public void setData(TouristLeaderDTO data){
        this.data=  data;
    }

}
