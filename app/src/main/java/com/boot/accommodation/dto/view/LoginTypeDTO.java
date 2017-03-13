package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Login type
 * 
 * @author tuanlt
 * @date Jul 11, 2016
 * @since 1.0
 */
public enum LoginTypeDTO {
	NORMAL(1), FACEBOOK(2), GOOGLE(3);
	private int value;
	private static Map<Integer, LoginTypeDTO> valueMapping = new HashMap<>();
	static {
		for (LoginTypeDTO itemType : LoginTypeDTO.values()) {
			valueMapping.put(itemType.getValue(), itemType);
		}
	}

	LoginTypeDTO(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static LoginTypeDTO parse(int value) {
		return valueMapping.get(value);
	}
}
