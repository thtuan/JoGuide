package com.boot.accommodation.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageValidatorTakingPhoto extends ImageValidator {
    Intent data;

    /**
     * @param context
     * @param filePath
     * @param maxDimensionAvatar
     */
    public ImageValidatorTakingPhoto(BaseActivity context,
                                     String filePath, int maxDimensionAvatar) {
        super(context, filePath, maxDimensionAvatar);
    }

    public void setDataIntent(Intent data) {
        this.data = data;
    }

    @Override
    public boolean execute() {
        if (data != null) {
            Bitmap tempBitmap = null;
            tempBitmap = (Bitmap) data.getExtras().get("data");
            if (tempBitmap != null) {
                FileOutputStream out = null;
                try {
//                    int srcWidth = tempBitmap.getWidth();
//                    int srcHeight = tempBitmap.getHeight();

                    out = new FileOutputStream(filePath);

//                    if (srcWidth > 0 && srcHeight > 0 &&
//                            (srcWidth > Constants.MAX_FULL_IMAGE_WIDTH_UPLOAD || srcHeight > Constants.MAX_FULL_IMAGE_HEIGHT_UPLOAD)) {
//                        Bitmap scaledBitmap = ImageUtil.resizeImageWithOrignal(tempBitmap, Constants.MAX_FULL_IMAGE_HEIGHT_UPLOAD);
//                        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                        scaledBitmap.recycle();
//                        scaledBitmap = null;
//                    } else {
//                    }
                    tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } catch (FileNotFoundException e) {
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (tempBitmap != null) {
                            tempBitmap.recycle();
                        }
                        data = null;
                    } catch (IOException e) {
                    }
                }
            }
        } else {
            FileOutputStream out = null;
            Bitmap tempBitmap = null;
            try {
//                File file = new File(filePath);
//                BitmapFactory.Options resample = new BitmapFactory.Options();
//                resample.inJustDecodeBounds = false;
//                resample.inSampleSize = ImageUtil.computeSampleSizeFromFile(file,
//                        Constants.MAX_FULL_IMAGE_WIDTH_UPLOAD, Constants.MAX_FULL_IMAGE_HEIGHT_UPLOAD);
                tempBitmap = BitmapFactory.decodeFile(filePath);
//                int srcWidth = tempBitmap.getWidth();
//                int srcHeight = tempBitmap.getHeight();

                out = new FileOutputStream(filePath);

//                if (srcWidth > 0 && srcHeight > 0 &&
//                        (srcWidth > Constants.MAX_FULL_IMAGE_WIDTH_UPLOAD || srcHeight > Constants.MAX_FULL_IMAGE_HEIGHT_UPLOAD)) {
//                    Bitmap scaledBitmap = ImageUtil.resizeImageWithOrignal(tempBitmap, Constants.MAX_FULL_IMAGE_HEIGHT_UPLOAD);
//                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 95, out);
//                    scaledBitmap.recycle();
//                    scaledBitmap = null;
//                } else {
//                }
                tempBitmap.compress(Bitmap.CompressFormat.JPEG, 95, out);
            } catch (FileNotFoundException e) {
            } catch (OutOfMemoryError em) {
                System.gc();
                if (bitmapData != null && bitmapData.isRecycled()) {
                    bitmapData.recycle();
                    bitmapData = null;
                }
                context.showMessage(StringUtil.getString(R.string.ERROR_OUT_MEMORY));
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (tempBitmap != null) {
                        tempBitmap.recycle();
                    }
                } catch (IOException e) {
                }
            }
        }
        boolean result = super.execute();
        return result;
    }
}
