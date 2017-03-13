    package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * ItemType to compare
 *
 * @author Vuong-bv
 * @since: 11:28 AM 7/1/2016
 */
public enum ItemTypeDTO {
    LOCATION(10),// type location
    TOUR(20),// type tour
    TOUR_DAY(30),//type tour_day
    REVIEW(40),//type review
    USER(50),//type user
    TOUR_PLAN(60);//type tour_plan

    private int value;
    private static Map<Integer, ItemTypeDTO> valueMapping = new HashMap<>();

    static {
        for (ItemTypeDTO itemType : ItemTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ItemTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ItemTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
