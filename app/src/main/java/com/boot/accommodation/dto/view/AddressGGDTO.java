package com.boot.accommodation.dto.view;

import java.util.List;

/**
 * Address type dto
 *
 * @author tuanlt
 * @since 11:35 AM 9/16/16
 */
public class AddressGGDTO extends BaseDTO {
    private String long_name; //Long name
    private String short_name; //Short name
    private List<String> types; //List type

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
