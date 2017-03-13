package com.boot.accommodation.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.constant.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util cac ham lien quan toi file
 *
 * @author tuanlt
 * @since 12:22 PM 5/14/2016
 */
public class FileUtil {
    private static String TAG = FileUtil.class.getName();
    public static final String JOCO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/APP_DATA/";
    public static final String JOCO_TAKEN_PHOTO_FOLDER = "TAKEN_PHOTOS";

    /**
     * Kiem tra file co ton tai hay ko
     *
     * @param fileName
     * @return
     */
    public static boolean isExistFile(String fileName) {
        try {
            if (!StringUtil.isNullOrEmpty(fileName)) {
                String[] s = JoCoApplication.getInstance().getAppContext()
                        .fileList();
                for (int i = 0, size = s.length; i < size; i++) {
                    if (fileName.equals(s[i].toString())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.w(TAG, TraceExceptionUtils.getReportFromThrowable(e));
        }
        return false;
    }

    /**
     * Duong dan chua hinh anh chup
     * @return
     */
    public static File getTakenPhotoPath() {
        final File root = new File(JOCO_PATH);
        if (!root.exists()) {
            root.mkdir();
        }
        final File path = new File(JOCO_PATH + JOCO_TAKEN_PHOTO_FOLDER);
        if (!path.exists()) {
            path.mkdir();
        }
        return path;

    }

    /**
     * Save image to disk
     * @param bm
     * @param file
     * @param fileType
     * @return
     */
    public static boolean saveImageToDisk(Bitmap bm, File file,
                                          String fileType) {
        boolean isWrite = false;
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        Log.e("ImageUtil", "extStorageDirectory: " + extStorageDirectory);
        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
            if (fileType.endsWith("PNG")) {
                bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            } else if (fileType.endsWith("JPEG")) {
                bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            }
            try {
                outStream.flush();
                outStream.close();
                isWrite = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return isWrite;
    }

    /**
     * Delete file > num day
     */
    public static void deleteTakenPhoto( int numDay ) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT_DATE_FOR_FILE);
            Date d2 = dateFormat.parse(DateUtil.formatNow(DateUtil.FORMAT_DATE_FOR_FILE));
            File yourDir = new File(FileUtil.getTakenPhotoPath().getAbsolutePath());
            String name;
            for (File f : yourDir.listFiles()) {
                if (f.isFile() && f.getName().length() > DateUtil.FORMAT_DATE_FOR_FILE.length() && f.getName().contains("_")) {
                    try {// co the co file ko phai dinh dang ngay thang
                        name = f.getName();
                        name = name.substring(11, 30);
                        Date d1 = dateFormat.parse(name);
                        int days = DateUtil.daysBetween(d1, d2);
                        if (days > numDay) {
                            f.delete();
                        }
                    } catch (Exception e) {
                        Log.e(Utils.getTag(FileUtil.class), TraceExceptionUtils.getReportFromThrowable(e));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(Utils.getTag(FileUtil.class), TraceExceptionUtils.getReportFromThrowable(e));
        }
    }

    /**
     * Gen file taken photo
     * @return
     */
    public static File genFileTaken() {
        String timeSave = DateUtil.formatNow(DateUtil.FORMAT_DATE_FOR_FILE);
        String fileName = Constants.TEMP_IMG + "_" + timeSave + ".jpg";
        File retFile = new File(FileUtil.getTakenPhotoPath(), fileName);
        boolean isCreateSuccess = false;
        if (!retFile.exists()) {
            try {
                isCreateSuccess = retFile.createNewFile();
            } catch (IOException e) {
                // log
                e.printStackTrace();
            }
        }
        return isCreateSuccess? retFile: null;
    }
}
