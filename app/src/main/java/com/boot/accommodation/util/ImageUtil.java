package com.boot.accommodation.util;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.common.info.ServerPath;
import com.boot.accommodation.constant.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Util xu ly hinh anh
 *
 * @author tuanlt
 * @since: 4:40 PM 5/10/2016
 */
public class ImageUtil {

    public static Uri photoUri; // duong dan hinh anh

    /**
     * Return image url Joco
     *
     * @return
     */
    public static String getImageUrl( String urlImage ) {
        if (StringUtil.isNullOrEmpty(urlImage)) {
            return "";
        }
        // Using when login with social
        if(urlImage.contains("http"))
            return urlImage;
        return ServerPath.getImageUrl(urlImage, Constants.MAX_FULL_IMAGE_HEIGHT_DOWNLOAD, Constants
                .MAX_FULL_IMAGE_HEIGHT_DOWNLOAD);
    }

    /**
     * Get image url with height
     * @param urlImage
     * @param height
     * @return
     */
    public static String getImageUrl( String urlImage, int height ) {
        if (StringUtil.isNullOrEmpty(urlImage)) {
            return "";
        }
        // Using when login with social
        if(urlImage.contains("http"))
            return urlImage;
        return ServerPath.getImageUrl(urlImage, height);
    }

    /**
     * Return image url Joco
     *
     * @return
     */
    public static String getGoogleImageUrl( String urlImage ) {
        if (StringUtil.isNullOrEmpty(urlImage)) {
            return "";
        }
        // Using when login with social
        if(urlImage.contains("http"))
            return urlImage;
        return ServerPath.getGoogleImageUrl() + "maxwidth=" + Constants.MAX_FULL_IMAGE_HEIGHT_DOWNLOAD + "&photo_reference="+
                urlImage + "&key="+ Utils.getString(R.string.google_api_key);
    }

    /**
     * Get google image with height
     * @param urlImage
     * @param height
     * @return
     */
    public static String getGoogleImageUrl( String urlImage, int height ) {
        if (StringUtil.isNullOrEmpty(urlImage)) {
            return "";
        }
        // Using when login with social
        if(urlImage.contains("http"))
            return urlImage;
        return ServerPath.getGoogleImageUrl() + "maxwidth=" + height + "&photo_reference="+
                urlImage + "&key="+ Utils.getString(R.string.google_api_key);
    }

    /**
     * Get image thumbnail
     * @param urlImage
     * @return
     */
    public static String getImageUrlThumb( String urlImage ) {
        if (StringUtil.isNullOrEmpty(urlImage)) {
            return "";
        }
        // Using when login with social
        if(urlImage.contains("http"))
            return urlImage;
        //http://104.199.165.232:9000/media/display?path=<duong dan trong db>&w=300&h=100&op=thumbnail
        return ServerPath.getImageUrl(urlImage, Constants.MAX_THUMB_IMAGE_WIDTH_DOWNLOAD, Constants
                .MAX_THUMB_IMAGE_HEIGHT_DOWNLOAD);
    }

    /**
     * Get google image url thumb
     * @param urlImage
     * @return
     */
    public static String getGoogleImageUrlThumb( String urlImage ) {
        if (StringUtil.isNullOrEmpty(urlImage)) {
            return "";
        }
        // Using when login with social
        if(urlImage.contains("http"))
            return urlImage;
        return ServerPath.getGoogleImageUrl() + "maxwidth=" + Constants.MAX_THUMB_IMAGE_WIDTH_DOWNLOAD + "&maxheight =" + Constants
                .MAX_THUMB_IMAGE_WIDTH_DOWNLOAD + "&photoreference="+ urlImage + "&key="+ Utils.getString(R.string.google_api_key);
    }

    /**
     * Get image url owner create tour
     * @param urlImage
     * @return
     */
    public static String getImageUrlOwnerAvatar( String urlImage ) {
        if (StringUtil.isNullOrEmpty(urlImage)) {
            return "";
        }
        // Using when login with social
        if(urlImage.contains("http"))
            return urlImage;
        return ServerPath.getImageUrl(urlImage, Constants.MAX_THUMB_IMAGE_WIDTH_AVATAR_OWNER_DOWNLOAD, Constants
                .MAX_THUMB_IMAGE_HEIGHT_AVATAR_OWNER_DOWNLOAD);
    }
    /**
     * Get google image thumb
     * @param urlImage
     * @param width
     * @param height
     * @return
     */
    public static String getGoogleImageUrlThumb( String urlImage, int width, int height ) {
        if (StringUtil.isNullOrEmpty(urlImage)) {
            return "";
        }
        // Using when login with social
        if(urlImage.contains("http"))
            return urlImage;
        return ServerPath.getGoogleImageUrl() + "maxwidth=" + Constants.MAX_IMAGE_MARKER_WIDTH + "&maxheight =" + Constants
                .MAX_IMAGE_MARKER_HEIGHT + "&photoreference="+ urlImage + "&key="+ Utils.getString(R.string.google_api_key);
    }

    /**
     * Get image url thumb
     * @param urlImage
     * @param width
     * @param height
     * @return
     */
    public static String getImageUrlThumb( String urlImage, int width, int height ) {
        if (StringUtil.isNullOrEmpty(urlImage)) {
            return "";
        }
        // Using when login with social
        if(urlImage.contains("http"))
            return urlImage;
        return ServerPath.getImageUrl(urlImage, Constants.MAX_IMAGE_MARKER_WIDTH, Constants
                .MAX_IMAGE_MARKER_HEIGHT);
    }

    /**
     * Load image
     *
     * @param imgView
     * @param urlImage
     */
    public static void loadImage(ImageView imgView, String urlImage) {
        Glide.with(JoCoApplication.getInstance().getApplicationContext()).load(urlImage).asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .placeholder(Utils.getDrawable(R.drawable.img_default_error))
            .error(Utils.getDrawable(R.drawable.img_no_image)).into(imgView);
    }

    /**
     * Load image
     * @param imgView
     * @param urlImage
     * @param progressBar
     */
    public static void loadImage(ImageView imgView, String urlImage, final ProgressBar progressBar) {
        progressBar.getIndeterminateDrawable().setColorFilter(Utils.getColor(R.color.white), PorterDuff
            .Mode
            .SRC_IN);
        Glide.with(JoCoApplication.getInstance().getApplicationContext()).load(urlImage).asBitmap()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .placeholder(Utils.getDrawable(R.drawable.img_default_error))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(Utils.getDrawable(R.drawable.img_no_image)).into(imgView).getRequest().isComplete();
    }

    /**
     * Load hinh anh rounded
     *
     * @param imgView
     * @param urlImage
     * @param width
     * @param height
     */
    public static void loadImageRounded(ImageView imgView, String urlImage, int width, int height) {
        Glide.with(JoCoApplication.getInstance().getApplicationContext()).load(urlImage)
                .placeholder(Utils.getDrawable(R.drawable.img_default_error))
                .error(Utils.getDrawable(R.drawable.img_default_error)).centerCrop().into(imgView);
    }

    public static Bitmap readImageFromSDCard(String path, int maxDimension)
            throws Exception {
        int degrees = 0;

        Bitmap retBitmap = null;
        BitmapFactory.Options resample = new BitmapFactory.Options();
        resample.inJustDecodeBounds = false;
        resample.inSampleSize = computeSampleSizeFromFile(new File(path),
                Constants.MAX_FULL_IMAGE_WIDTH_UPLOAD, Constants.MAX_FULL_IMAGE_WIDTH_UPLOAD);
        Bitmap bitmap = BitmapFactory.decodeFile(path, resample);
        if (bitmap == null) {
            throw new Exception("decode image failed");
        }
        retBitmap = bitmap;

        degrees = degreesRotateImage(path);
        if (Math.max(bitmap.getWidth(), bitmap.getHeight()) > maxDimension) {
            retBitmap = resizeImageWithOrignal(bitmap, maxDimension,
                    degrees);
            bitmap.recycle();
            bitmap = null;
        }
        return retBitmap;
    }

    /**
     * kiem tra anh can rotate bao nhieu do de quay ve anh ban dau
     */
    public static int degreesRotateImage(String filePath) {
        int degrees = 0;
        try {
            ExifInterface exifi = new ExifInterface(filePath);
            String tag = exifi.getAttribute(ExifInterface.TAG_ORIENTATION);
            if (tag.equals(ExifInterface.ORIENTATION_ROTATE_90)) {
                degrees = 90;
            } else if (tag.equals(ExifInterface.ORIENTATION_ROTATE_180)) {
                degrees = 180;
            } else if (tag.equals(ExifInterface.ORIENTATION_ROTATE_270)) {
                degrees = -90;
            }
        } catch (Throwable e) {
        }
        return degrees;
    }

    /**
     * tinh sample size tu file
     */
    public static int computeSampleSizeFromFile(File file, int maxWidth, int maxHeight) {
        if (maxWidth == 0 || maxHeight == 0)
            return 1;
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), bmpFactoryOptions);
        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
                / (float) maxHeight);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                / (float) maxWidth);
        // If both of the ratios are greater than 1,
        // one of the sides of the image is greater than the screen
        bmpFactoryOptions.inSampleSize = 1;
        if (heightRatio > 1 && widthRatio > 1) {
            if (heightRatio > widthRatio) {
                // Height ratio is larger, scale according to it
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                // Width ratio is larger, scale according to it
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }
        return bmpFactoryOptions.inSampleSize;
    }

    public static Bitmap resizeImageWithOrignal(Bitmap orignal,
                                                int maxDimension, int degrees) {
        System.gc();
        int width = orignal.getWidth();
        int height = orignal.getHeight();

        if (width > maxDimension || height > maxDimension) {
            int new_width;
            int new_height;
            float ratio = (float) width / height;
            if (ratio > 1.0f) {
                new_width = maxDimension;
                new_height = (int) ((float) new_width / ratio);
            } else {
                new_height = maxDimension;
                new_width = (int) ((float) new_height * ratio);
            }
            float scaleWidth = ((float) new_width) / width;
            float scaleHeight = ((float) new_height) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            if (degrees != 0) {
                matrix.postRotate(degrees);
            }
            Bitmap resizedBitmap = Bitmap.createBitmap(orignal, 0, 0, width,
                    height, matrix, true);
            return resizedBitmap;
        }
        return orignal;
    }

    /**
     * get bitmap khi thuc hien chon anh tu thu muc picasa cua gallery
     */
    public static Bitmap getBitmapFromPicasaUri(Context c, Uri image) throws IOException {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = c.getContentResolver().openInputStream(image);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read;
            byte[] b = new byte[4096];
            while ((read = is.read(b)) != -1) {
                out.write(b, 0, read);
            }
            byte[] raw = out.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(raw, 0, raw.length);
        } catch (FileNotFoundException e) {
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return bitmap;
    }

    public static Bitmap resizeImageWithOrignal(Bitmap orignal, int maxDimension) {
        int width = orignal.getWidth();
        int height = orignal.getHeight();

        if (width > maxDimension || height > maxDimension) {
            int new_width;
            int new_height;
            float ratio = (float) width / height;
            if (ratio > 1.0f) {
                new_width = maxDimension;
                new_height = (int) ((float) new_width / ratio);
            } else {
                new_height = maxDimension;
                new_width = (int) ((float) new_height * ratio);
            }
            float scaleWidth = ((float) new_width) / width;
            float scaleHeight = ((float) new_height) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(orignal, 0, 0, width,
                    height, matrix, true);
            return resizedBitmap;
        }
        return orignal;
    }

    /**
     * Chon hinh anh tu chup hinh hoac gallery
     */
    public static void selectImage(final BaseActivity mActivity, final BaseFragment mFragment) {
        final CharSequence[] items = {StringUtil.getString(R.string.text_take_photo), StringUtil.getString(R.string
                .text_choose_from_gallery),
                StringUtil.getString(R.string.text_cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(StringUtil.getString(R.string.text_add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utils.checkPermission(mActivity, 0, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (StringUtil.getString(R.string.text_take_photo).equals(items[item])) {
                    if (result) {
                        takePicture(mActivity, mFragment);
                    }
                } else if (StringUtil.getString(R.string
                        .text_choose_from_gallery).equals(items[item])) {
                    if (result) {
                        chooseGallery(mFragment);
                    }
                } else if (StringUtil.getString(R.string.text_cancel).equals(items[item])) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Chon hinh anh tu chup hinh hoac gallery
     */
    public static void selectImage(final BaseActivity mActivity) {
        final CharSequence[] items = {StringUtil.getString(R.string.text_take_photo), StringUtil.getString(R.string
                .text_choose_from_gallery),
                StringUtil.getString(R.string.text_cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(StringUtil.getString(R.string.text_add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utils.checkPermission(mActivity, 0, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (StringUtil.getString(R.string.text_take_photo).equals(items[item])) {
                    if (result) {
                        takePicture(mActivity);
                    }
                } else if (StringUtil.getString(R.string
                        .text_choose_from_gallery).equals(items[item])) {
                    if (result) {
                        chooseGallery(mActivity);
                    }
                } else if (StringUtil.getString(R.string.text_cancel).equals(items[item])) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Chon hinh anh tu chup hinh hoac gallery
     */
    public static void selectMultiImage(final BaseActivity mActivity) {
        final CharSequence[] items = {StringUtil.getString(R.string.text_take_photo), StringUtil.getString(R.string
                .text_choose_from_gallery),
                StringUtil.getString(R.string.text_cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(StringUtil.getString(R.string.text_add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utils.checkPermission(mActivity, 0, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (StringUtil.getString(R.string.text_take_photo).equals(items[item])) {
                    if (result) {
                        takePicture(mActivity);
                    }
                } else if (StringUtil.getString(R.string
                        .text_choose_from_gallery).equals(items[item])) {
                    if (result) {
                        chooseMultiGallery(mActivity);
                    }
                } else if (StringUtil.getString(R.string.text_cancel).equals(items[item])) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Chup hinh
     */
    private static void takePicture(BaseActivity mActivity, BaseFragment mFragment) {
        mActivity.fragmentRequestTakePhoto = mFragment;
        mActivity.takenPhoto = takePhoto(mFragment, BaseActivity.REQUEST_IMAGE_CAPTURE);
    }


    private static void takePicture(BaseActivity mActivity) {
        mActivity.takenPhoto = takePhoto(mActivity, BaseActivity.REQUEST_IMAGE_CAPTURE);
    }

    /**
     * Chon file tu gallery
     */
    private static void chooseGallery(BaseFragment mFragment) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        mFragment.startActivityForResult(Intent.createChooser(intent, "Select File"), BaseActivity.REQUEST_IMAGE_SELECTOR);
    }

    /**
     * Choose image from gallery
     * @param mActivity
     */
    private static void chooseGallery(BaseActivity mActivity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        mActivity.startActivityForResult(Intent.createChooser(intent, "Select File"), BaseActivity.REQUEST_IMAGE_SELECTOR);
    }

    /**
     * Choose multi image from gallery
     * @param mActivity
     */
    private static void chooseMultiGallery(BaseActivity mActivity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        mActivity.startActivityForResult(Intent.createChooser(intent, "Select File"), BaseActivity.REQUEST_IMAGE_SELECTOR);
    }

    /**
     * Get image orientation
     * @param imagePath
     * @return
     */
    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
//            if("SAMSUNG".equals(JoCoApplication.getManuFacturerName()))
//                return 90;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            Log.d("", TraceExceptionUtils.getReportFromThrowable(e));
        }
        return rotate;
    }

    /**
     * rorate image when user take a photo
     * @param source
     * @param angle
     * @return
     */
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

    /**
     * Tao file tu bitmap
     * @param sender
     * @param bitmap
     */
    public static File createFileFromBitMap(BaseActivity sender, Bitmap bitmap) {
        String timeSave = DateUtil.formatNow(DateUtil.FORMAT_DATE_FOR_FILE);
        String fileName = Constants.TEMP_IMG + "_" + timeSave + ".jpg";
        return handleCreateFile(bitmap, fileName);
    }

    /**
     * Create bit map for choose multi file with offset because time save is same
     * @param sender
     * @param bitmap
     * @param offset
     * @return
     */
    public static File createFileFromBitMap(BaseActivity sender, Bitmap bitmap, int offset) {
        String timeSave = DateUtil.formatNow(DateUtil.FORMAT_DATE_FOR_FILE);
        String fileName = Constants.TEMP_IMG + "_" + timeSave + "_" + offset + ".jpg";
        return handleCreateFile(bitmap, fileName);
    }

    @NonNull
    private static File handleCreateFile(Bitmap bitmap, String fileName) {
        File retFile = new File(FileUtil.getTakenPhotoPath(), fileName);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapData = bos.toByteArray();
        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(retFile);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.w("", TraceExceptionUtils.getReportFromThrowable(e));
        } catch (IOException e) {
            Log.w("", TraceExceptionUtils.getReportFromThrowable(e));
        }
        return retFile;
    }

    /**
     * Chup hinh xu li cho base activity
     * @param sender
     * @param requestCode
     * @return
     */
    private static File takePhoto(BaseActivity sender, int requestCode) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File retFile = FileUtil.genFileTaken();
        if(retFile != null && intent.resolveActivity(sender.getPackageManager()) != null) {
            photoUri = sender.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            sender.startActivityForResult(intent, requestCode);
        }
        return retFile;
    }

    /**
     * Chup hinh voi fragment
     * @param sender
     * @param requestCode
     * @return
     */
    private static  File takePhoto(BaseFragment sender, int requestCode) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File retFile = FileUtil.genFileTaken();
        if (retFile != null && intent.resolveActivity(sender.getActivity().getPackageManager()) != null) {
            photoUri = sender.getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new ContentValues());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            sender.startActivityForResult(intent, requestCode, "");
        }
        return retFile;
    }

    /**
     * Get real image from uri
     * @param baseActivity
     * @param contentUri
     * @return
     */
    public static String getRealPathFromURI(BaseActivity baseActivity, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = baseActivity.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }
}
