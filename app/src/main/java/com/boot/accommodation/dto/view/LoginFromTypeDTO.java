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
public enum LoginFromTypeDTO {
	MOBILE(1), WEB(2);
	private int value;
	private static Map<Integer, LoginFromTypeDTO> valueMapping = new HashMap<>();

	static {
		for (LoginFromTypeDTO itemType : LoginFromTypeDTO.values()) {
			valueMapping.put(itemType.getValue(), itemType);
		}
	}

	LoginFromTypeDTO( int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static LoginFromTypeDTO parse( int value) {
		return valueMapping.get(value);
	}
}
