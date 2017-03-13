package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * CurrencyTypeDTO
 *
 * @author tuanlt
 * @since 10:37 AM 1/24/17
 */
public enum CurrencyTypeDTO {
    VND(119),
    USD(120);

    private int value;
    private static Map<Integer, CurrencyTypeDTO> valueMapping = new HashMap<>();

    static {
        for (CurrencyTypeDTO currency : CurrencyTypeDTO.values()) {
            valueMapping.put(currency.getValue(), currency);
        }
    }

    CurrencyTypeDTO(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static CurrencyTypeDTO parse(int value) {
        return valueMapping.get(value);
    }
}
