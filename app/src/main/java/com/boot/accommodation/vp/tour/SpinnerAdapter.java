package com.boot.accommodation.vp.tour;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Mo ta muc dich cua lop (interface)
 * 
 */
public class SpinnerAdapter extends ArrayAdapter<String> {
	Context context;
	String[] items = new String[] {};

	public SpinnerAdapter(final Context context, final int textViewResourceId,
			final String[] objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(
					android.R.layout.simple_spinner_item, parent, false);
		}
		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		tv.setText(items[position]);
		tv.setTextColor(Color.BLACK);
		return convertView;
	}
}