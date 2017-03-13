package com.boot.accommodation.dto.view;

import android.graphics.drawable.Drawable;

import com.boot.accommodation.R;
import com.boot.accommodation.util.Utils;

/**
 * Created by Admin on 12/10/2015.
 */
public class LeftMenuItemDTO {

    public int id;
    public String name;
    public Drawable avatar;
    public int total;

    public LeftMenuItemDTO() {
        id = 0;
        name = "";
        avatar = Utils.getDrawable(R.drawable.img_default_error);
        total = 0;
    }

}
