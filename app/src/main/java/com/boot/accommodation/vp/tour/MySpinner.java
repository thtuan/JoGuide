package com.boot.accommodation.vp.tour;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 *  Spinner override (co the nhan su kien khi click item cu)
 *  @version: 1.0
 *  @since: 1.0
 */
public class MySpinner extends Spinner {
	OnItemSelectedListener listener;
	private boolean isSendSpiner2Event = false;

	public MySpinner(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}

	@Override
	public void setSelection(int position) {
	    super.setSelection(position);
	    if (listener != null){
	    	if (isSendSpiner2Event) {
	    		listener.onItemSelected(this, this, position, 0);
			} else{
				listener.onItemSelected(null, null, position, 0);
			}
	    }
	}

	public void setOnItemSelectedEvenIfUnchangedListener(
	        OnItemSelectedListener listener) {
	    this.listener = listener;
	}

	public void setSendSpiner2Event(boolean isSendSpiner2Event) {
		this.isSendSpiner2Event  = isSendSpiner2Event;
	}
}
