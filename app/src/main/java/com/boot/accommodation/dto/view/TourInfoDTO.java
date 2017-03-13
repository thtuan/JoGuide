package com.boot.accommodation.dto.view;

/**
 * Dto tourinfo for expand list tourinfo
 *
 * @author Vuong-bv
 * @since: 5/30/2016
 */
public class TourInfoDTO extends BaseDTO {
    private  String title;//title
    private String describe;//describe for title

    public TourInfoDTO(String title, String describe) {
        this.title = title;
        this.describe = describe;
    }

    public TourInfoDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

