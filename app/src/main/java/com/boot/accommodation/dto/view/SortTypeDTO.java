package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Area type
 *
 * @author tuanlt
 * @since: 11:28 AM 7/1/2016
 */
public enum SortTypeDTO {
    RANDOM(10),
    DISTANCE(20),
    FAVOURITE(30),
    PRICE(40);

    private int value;
    private static Map<Integer, SortTypeDTO> valueMapping = new HashMap<>();

    static {
        for (SortTypeDTO itemType : SortTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    SortTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SortTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
