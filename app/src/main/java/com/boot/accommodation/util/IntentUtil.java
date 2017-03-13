package com.boot.accommodation.util;

import android.content.Intent;
import android.net.Uri;
import com.boot.accommodation.JoCoApplication;
import java.io.File;

public class IntentUtil {

    public static void shareImage(String imagePath) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.setType("image/*");
        File imageFileToShare = new File(imagePath);
        Uri uri = Uri.fromFile(imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        ActivityUtil.switchActivity(JoCoApplication.getInstance().getAppContext(), Intent.createChooser(share, "Share Image!"));
    }
}
