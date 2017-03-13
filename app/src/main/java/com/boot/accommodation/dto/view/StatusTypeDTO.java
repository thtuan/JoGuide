package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * StatusTypeDTO
 *
 * @author tuanlt
 * @since 11:58 AM 12/26/16
 */
public enum StatusTypeDTO {

    CANCEL(-1),
    NEW(0),
    APPROVED(1),
    APPROVED_LVL2(2);

    private int value;
    private static Map<Integer, StatusTypeDTO> valueMapping = new HashMap<>();

    static {
        for (StatusTypeDTO itemType : StatusTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    StatusTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static StatusTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
