package com.boot.accommodation.vp.home;

import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.CategoryItemDTO;

import java.util.List;

/**
 * Home presenter
 *
 * @author tuanlt
 * @since 6:02 PM 9/20/16
 */
public interface HomePresenterMgr {

    /**
     * Get location filter
     */
    void getInfoFilterLocation();

    /**
     * Get category choose
     */
    List<CategoryItemDTO> getCategoryChoose();

    /**
     * Validate data filter
     * @param famousPlace
     * @return
     */
    boolean validateDataFilter(String famousPlace);

    /**
     * Get famous place choose
     * @param famousPlace
     * @return
     */
    AreaDTO getFamousPlaceChoose(String famousPlace);

    /**
     * Get category id choose
     * @param categories
     * @return
     */
    String getCategoryIdChoose(List<CategoryItemDTO> categories);

    /**
     * Render filter area
     */
    void getFilterAreas();
}
