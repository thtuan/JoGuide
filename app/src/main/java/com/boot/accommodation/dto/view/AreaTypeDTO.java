package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Area type
 *
 * @author tuanlt
 * @since: 11:28 AM 7/1/2016
 */
public enum AreaTypeDTO {
    COUNTRY(10), //Country
    REGION(15), //Province
    PROVINCE(20), //Province
    FAMOUSLOCATION(30),//Famouse location
    ROUTE(40),//Route
    STREET_NUMBER(50); //Street number

    private int value;
    private static Map<Integer, AreaTypeDTO> valueMapping = new HashMap<>();

    static {
        for (AreaTypeDTO itemType : AreaTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    AreaTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static AreaTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
