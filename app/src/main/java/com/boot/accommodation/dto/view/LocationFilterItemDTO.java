package com.boot.accommodation.dto.view;

import java.util.ArrayList;
import java.util.List;

/**
 * Location filter item
 *
 * @author tuanlt
 * @since 10:33 AM 9/22/16
 */
public class LocationFilterItemDTO extends BaseDTO {
    private AreaDTO famousLocation = new AreaDTO();
    private List<CategoryItemDTO> categories = new ArrayList<>(); //List categoryIds choose
    private String categoryIds = ""; //Category choose from list categories above
    private int sortType = SortTypeDTO.DISTANCE.getValue(); //Sort type
    private String searchText = ""; //Search text

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public AreaDTO getFamousLocation() {
        return famousLocation;
    }

    public void setFamousLocation(AreaDTO famousLocation) {
        this.famousLocation = famousLocation;
    }

    public List<CategoryItemDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryItemDTO> categories) {
        this.categories = categories;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
}
