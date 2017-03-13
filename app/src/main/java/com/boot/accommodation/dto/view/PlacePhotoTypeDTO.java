package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Photo type
 */
public enum PlacePhotoTypeDTO {
    ALL(1),// type
    TYPE_OWNER(2), // type owner
    TYPE_JOCO(3); // type joco

    private int value;
    private static Map<Integer, PlacePhotoTypeDTO> valueMapping = new HashMap<>();

    static {
        for (PlacePhotoTypeDTO itemType : PlacePhotoTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    PlacePhotoTypeDTO( int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static PlacePhotoTypeDTO parse( int value) {
        return valueMapping.get(value);
    }
}
