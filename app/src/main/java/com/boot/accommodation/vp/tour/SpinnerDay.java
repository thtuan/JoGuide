/**
 * Copyright 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.boot.accommodation.vp.tour;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.boot.accommodation.R;
import com.boot.accommodation.util.Utils;

/**
 * thong tin chung
 * since   : 1.0
 * version : 1.0
 */
public class SpinnerDay extends LinearLayout {
	MySpinner mySpinner;

	public SpinnerDay( Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	
	public SpinnerDay( Context context, int useFor) {
		super(context);
		initView(context);
	}
	
	private void initView(Context mContext) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.sp_map, this);
		mySpinner = (MySpinner) findViewById( R.id.spinerRoute);
	}
	
	public void setAdapter(SpinnerAdapter apdater){
		mySpinner.setAdapter(apdater);
		
	}
	public void setAdapter(SpinnerTextAdapter apdater){
		mySpinner.setAdapter(apdater);
		
	}
	
	public void setPrompt(String prompt){
		mySpinner.setPrompt(prompt);
	}
	
	public void setMinimumWidth(int w){
		mySpinner.setMinimumWidth(Utils.dip2Pixel(w));
	}
	
	public void setSelection(int selection){
		mySpinner.setSelection(selection);
	}
	
	public int getSelectedItemPosition(){
		return mySpinner.getSelectedItemPosition();
	}
	
	
	public void setOnItemSelectedListener(OnItemSelectedListener listener){
		mySpinner.setOnItemSelectedEvenIfUnchangedListener(listener);
	}
	
	public Spinner getSpiner(){
		return mySpinner;
	}
}
