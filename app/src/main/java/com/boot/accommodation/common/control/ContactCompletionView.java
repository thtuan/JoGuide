package com.boot.accommodation.common.control;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.dto.view.PickContactDTO;
import com.tokenautocomplete.TokenCompleteTextView;

/**
 * ContactCompletionView
 *
 * @author thtuan
 * @since 9:29 AM 06-09-2016
 */
public class ContactCompletionView extends TokenCompleteTextView<PickContactDTO> {

    public ContactCompletionView(Context context) {
        super(context);
    }

    public ContactCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(PickContactDTO contactDTO) {
        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView view = (TextView) l.inflate(R.layout.item_contact_choose, (ViewGroup) getParent(), false);
        view.setText(contactDTO.getAddress());
        return view;
    }

    @Override
    protected PickContactDTO defaultObject(String s) {
        int index = s.indexOf('@');
        PickContactDTO pickContactDTO= new PickContactDTO();
        if (index == -1) {
            pickContactDTO.setName(s);
            pickContactDTO.setAddress(s.replace(" ", "") + "@gmail.com");
        } else {
            pickContactDTO.setName(s.substring(0, index));
            pickContactDTO.setAddress(s);
        }
        return pickContactDTO;
    }
}
