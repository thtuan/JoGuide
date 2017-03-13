package com.boot.accommodation.vp.home;

import com.boot.accommodation.dto.view.AreaDTO;
import com.boot.accommodation.dto.view.LocationFilterViewDTO;

import java.util.List;

/**
 * Home view mgr
 *
 * @author tuanlt
 * @since 5:58 PM 9/20/16
 */
public interface HomeViewMgr {

    /**
     * List category
     *
     * @param locationFilterViewDTO
     */
    void openPopUpCategory(LocationFilterViewDTO locationFilterViewDTO);

    /**
     * Show message error
     *
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * Famous place
     * @param famousPlaces
     */
    void renderFamousPlace(List<AreaDTO> famousPlaces);

    /**
     * Show province error
     */
    void showProvinceError();

    /**
     * Show famous place error
     */
    void showFamousPlaceError();

    /**
     * Render filter areas
     * @param areaDTOs
     */
    void renderFilterAreas(List<AreaDTO> areaDTOs);

}
