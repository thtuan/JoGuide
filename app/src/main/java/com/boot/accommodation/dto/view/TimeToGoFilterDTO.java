package com.boot.accommodation.dto.view;

/**
 * Filter time to go
 *
 * @author tuanlt
 * @since 11:35 AM 9/16/16
 */
public class TimeToGoFilterDTO extends BaseDTO {

    private String areaIds; //Area choose
    private String searchText = ""; //Month choose
    private int sortType = SortTypeDTO.RANDOM.getValue(); //Sort type
    private String monthIds; //Month ids
    private LocationDTO mapPoint; //Map point

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getMonthIds() {
        return monthIds;
    }

    public void setMonthIds(String monthIds) {
        this.monthIds = monthIds;
    }

    public LocationDTO getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(LocationDTO mapPoint) {
        this.mapPoint = mapPoint;
    }
}
