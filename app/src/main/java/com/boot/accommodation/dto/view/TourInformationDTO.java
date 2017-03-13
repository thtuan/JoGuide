package com.boot.accommodation.dto.view;

import java.util.ArrayList;

/**
 * DTO for expand tour info
 *
 * @author Vuong-bv
 * @since: 5/13/2016
 */
public class TourInformationDTO extends BaseDTO {

    private ArrayList<TourInfoDTO> tourInfoDto;//dto tour info
    private ArrayList<TouristDTO> tourGuideDto;// dto tour guide
    private ArrayList<TouristDTO> touristDto;//dto tourist
    private boolean isTourCompany; //Tour company check

    public TourInformationDTO( ArrayList<TourInfoDTO> tourInfoDto, ArrayList<TouristDTO> tourGuideDto, ArrayList<TouristDTO> touristDto) {
        this.tourInfoDto = tourInfoDto;
        this.tourGuideDto = tourGuideDto;
        this.touristDto = touristDto;
    }

    public TourInformationDTO() {
    }

    public ArrayList<TourInfoDTO> getTourInfoDto() {
        return tourInfoDto;
    }

    public void setTourInfoDto(ArrayList<TourInfoDTO> tourInfoDto) {
        this.tourInfoDto = tourInfoDto;
    }

    public ArrayList<TouristDTO> getTourGuideDto() {
        return tourGuideDto;
    }

    public void setTourGuideDto(ArrayList<TouristDTO> tourGuideDto) {
        this.tourGuideDto = tourGuideDto;
    }

    public ArrayList<TouristDTO> getTouristDto() {
        return touristDto;
    }

    public void setTouristDto(ArrayList<TouristDTO> touristDto) {
        this.touristDto = touristDto;
    }

    public boolean isTourCompany() {
        return isTourCompany;
    }

    public void setTourCompany(boolean tourCompany) {
        isTourCompany = tourCompany;
    }
}
