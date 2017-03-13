package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * ItemType to compare
 *
 * @author Vuong-bv
 * @since: 11:28 AM 7/1/2016
 */
public enum LocationTypeDTO {
    JOCO(0),
    GOOGLE(1),
    AGODA(2);

    private int value;
    private static Map<Integer, LocationTypeDTO> valueMapping = new HashMap<>();

    static {
        for (LocationTypeDTO itemType : LocationTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    LocationTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static LocationTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
