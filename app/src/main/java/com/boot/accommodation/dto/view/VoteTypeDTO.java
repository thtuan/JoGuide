package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Vote type
 *
 * @author tuanlt
 * @date Jul 11, 2016
 * @since 1.0
 */
public enum VoteTypeDTO {
    EXCELLENT(1),
    GOOD(2),
    FAIR(3),
    BAD(4);
    public static String excellent = "Excellent";
    public static String good = "Good";
    public static String fair = "Fair";
    public static String bad = "Bad";

    private int value;
    private static Map<Integer, VoteTypeDTO> valueMapping = new HashMap<>();

    static {
        for (VoteTypeDTO itemType : VoteTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    VoteTypeDTO( int value ) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue( int value ) {
        this.value = value;
    }

    public static VoteTypeDTO parse( int value ) {
        return valueMapping.get(value);
    }
}
