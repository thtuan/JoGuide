package com.boot.accommodation.vp.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.LanguagesDTO;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;

public class SpinnerLanguagesAdapter extends ArrayAdapter<LanguagesDTO> {

	private final Context con;
	private final ArrayList<LanguagesDTO> listLanguages;

	public SpinnerLanguagesAdapter(Context context, int textViewResourceId,
			ArrayList<LanguagesDTO> objects) {
		super(context, textViewResourceId, objects);
		con = context;
		listLanguages = objects;
	}

	@Override
	public int getCount() {
		return listLanguages.size();
	}

	@Override
	public LanguagesDTO getItem(int position) {
		return listLanguages.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LanguagesDTO lang = getItem(position);
		if (convertView == null) {
			LayoutInflater layout = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layout.inflate(R.layout.item_languages, parent, false);
		}
//		TextView tvLanguages = (TextView) convertView.findViewById(R.id.tvLanguages);
//		tvLanguages.setCompoundDrawablesWithIntrinsicBounds(0, 0, lang.getIcon(), 0);
//		tvLanguages.setText("");
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		LanguagesDTO lang = getItem(position);
		if (convertView == null) {
			LayoutInflater layout = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layout.inflate(R.layout.item_languages_dropdown, parent, false);
		}
		convertView.setBackgroundColor(Utils.getColor(R.color.white));
		TextView tvLanguages = (TextView) convertView.findViewById(R.id.tvLanguages);
		tvLanguages.setCompoundDrawablesWithIntrinsicBounds(lang.getIcon(), 0, 0, 0);
		tvLanguages.setText(lang.getName());
		return convertView;
	}
}
