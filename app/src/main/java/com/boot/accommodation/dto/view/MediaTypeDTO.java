package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Type of Media
 *
 * @author Vuong-bv
 * @since: 11:28 AM 7/1/2016
 */
public enum MediaTypeDTO {
    IMAGE(1),// type image
    VIDEO(2);// type video

    private int value;
    private static Map<Integer, MediaTypeDTO> valueMapping = new HashMap<>();

    static {
        for (MediaTypeDTO itemType : MediaTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    MediaTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MediaTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
