package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

public enum SearchTypeDTO {
	ALL(1), // search favourite and near by
    FAVOURITE(2),
    NEARBY(3);

    private int value;
    private static Map<Integer, SearchTypeDTO> valueMapping = new HashMap<>();

    static {
        for (SearchTypeDTO itemType : SearchTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    SearchTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SearchTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
