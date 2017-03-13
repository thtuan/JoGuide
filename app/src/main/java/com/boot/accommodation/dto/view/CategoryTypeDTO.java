package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * response for profile of user
 *
 * @author tuanlt
 * @since 2:01 PM 21/9/2016
 */
public enum CategoryTypeDTO {
    LOCATION_BEST_FOR(1),
    LOCATION_CATEGORY(2),
    LOCATION_SERVICE(3),
    VOTE_TOUR(4),
    VOTE_TOUR_GUIDE(5),
    TOUR_ATTRIBUTE(6),
    CURRENCY(7),
    LOCATION_BEST_IN_TIME(8),
	LOCATION_BEST_IN_TIME_ATTRIBUTE(9);

    private int value;
    private static Map<Integer, CategoryTypeDTO> valueMapping = new HashMap<>();

    static {
        for (CategoryTypeDTO categoryType : CategoryTypeDTO.values()) {
            valueMapping.put(categoryType.getValue(), categoryType);
        }
    }

    CategoryTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static CategoryTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
