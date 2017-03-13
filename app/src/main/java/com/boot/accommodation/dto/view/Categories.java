package com.boot.accommodation.dto.view;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 6/1/2016
 */
public class Categories extends BaseDTO {
    private  String id;

    public Categories( String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
