package com.boot.accommodation.vp.tour;

import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.SpannableObject;
import com.boot.accommodation.dto.view.SpinnerItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

/**
 * Spinner text co header
 * @author: TruongHN
 * @version: 1.1
 * @since: 1.0
 */
public class SpinnerTextAdapter extends ArrayAdapter<SpinnerItemDTO> {
	Context context;
	Vector<SpinnerItemDTO>items = new Vector<SpinnerItemDTO>();
	public boolean showHint = true;
	private String hint = "";
	TextView tvHint = null;

	public SpinnerTextAdapter( final Context context, final int textViewResourceId,
							   final Vector<SpinnerItemDTO> objects) {
		super(context, textViewResourceId ,android.R.id.text1, objects);
		this.items = objects;
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		tvHint = new TextView(parent.getContext());
		if (showHint && hint != null) {
			SpannableObject objSku = new SpannableObject();
			objSku.addSpan(hint, Utils.getColor(R.color.black),
					android.graphics.Typeface.NORMAL);
			tvHint.setText(objSku.getSpan());
			tvHint.setVisibility(View.VISIBLE);
		}else {
			tvHint.setVisibility(View.GONE);
		}
		v = tvHint;
		return v;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.spinner_text_row, parent, false);
		}
		if (position < items.size()){
			TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
			if (tvName != null){
				tvName.setText(items.get(position).name);
			}
			TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			if (tvContent != null) {
				if (!StringUtil.isNullOrEmpty(items.get(position).content)){
					tvContent.setText(items.get(position).content);
					tvContent.setVisibility(View.VISIBLE);
				} else {
					tvContent.setVisibility(View.GONE);
				}
			}
		}
		v = convertView;
		return v;
	}
	
	
	public void setHint(String strHint) {
		this.hint = strHint;
	}
}