package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * MobileTypeDTO
 *
 * @author tuanlt
 * @since 2:08 PM 12/15/16
 */
public enum MobileTypeDTO {
    ANDROID(0),
    IOS(1);

    private int value;
    private static Map<Integer, MobileTypeDTO> valueMapping = new HashMap<>();

    static {
        for (MobileTypeDTO itemType : MobileTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    MobileTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MobileTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
