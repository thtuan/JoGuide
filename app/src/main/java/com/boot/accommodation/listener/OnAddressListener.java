package com.boot.accommodation.listener;

import android.location.Address;

public interface OnAddressListener {
	public void showProgressLoading(boolean isShow);

	/**
	 * Set address
	 * @param address
	 * @param result
     */
	public void setAddress(Address address, String result);
}
