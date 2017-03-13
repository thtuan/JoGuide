package com.boot.accommodation.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.boot.accommodation.JoCoApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PreferenceUtils {

    private static String TAG = PreferenceUtils.class.getName();

    private static Context context;
    public static String REFERENCE_NAME = "JoCoSharePreferences"; // luu cac thong tin lien quan dang nhap
    public static final String REFERENCE_POSITION = "JoCoSharePreferencesPosition";
    public static final String REFERENCE_PROFILE = "JoCoSharePreferencesProfile";

    public static void init(Context context) {
        PreferenceUtils.context = context;
    }

    public static void clear() {
        SharedPreferences prefs = context.getSharedPreferences(
                REFERENCE_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
        // Clear profile and position file
        PreferenceUtils.saveObject(null ,PreferenceUtils.REFERENCE_PROFILE);
        PreferenceUtils.saveObject(null ,PreferenceUtils.REFERENCE_POSITION);
    }

    public static void saveBoolean(String key, boolean value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            prefs.edit().putBoolean(key, value).commit();
        } else {
            Log.e(TAG, "Init first");
        }
    }

    public static boolean getBoolean(String key, boolean value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            return prefs.getBoolean(key, value);
        } else {
            Log.e(TAG, "Init first");
            return value;
        }
    }

    public static void saveString(String key, String value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            prefs.edit().putString(key, value).commit();
        } else {
            Log.e(TAG, "Init first");
        }
    }

    public static String getString(String key, String value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            return prefs.getString(key, value);
        } else {
            Log.e(TAG, "Init first");
            return value;
        }
    }

    public static void saveInt(String key, int value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            prefs.edit().putInt(key, value).commit();
        } else {
            Log.e(TAG, "Init first");
        }
    }

    public static int getInt(String key, int value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            return prefs.getInt(key, value);
        } else {
            Log.e(TAG, "Init first");
            return value;
        }
    }

    public static void saveLong(String key, long value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            prefs.edit().putLong(key, value).commit();
        } else {
            Log.e(TAG, "Init first");
        }
    }

    public static long getLong(String key, long value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            return prefs.getLong(key, value);
        } else {
            Log.e(TAG, "Init first");
            return value;
        }
    }

    public static void saveFloat(String key, float value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            prefs.edit().putFloat(key, value).commit();
        } else {
            Log.e(TAG, "Init first");
        }
    }

    public static float getFloat(String key, float value) {
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(
                    REFERENCE_NAME, Context.MODE_PRIVATE);
            return prefs.getFloat(key, value);
        } else {
            Log.e(TAG, "Init first");
            return value;
        }
    }

    /**
     * Luu object toi file name
     * @param object
     * @param fileName
     */
    public static void saveObject(Object object, String fileName) {
        try {
            FileOutputStream fos = JoCoApplication.getInstance().getAppContext()
                .openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (Exception e) {
            Log.e("", TraceExceptionUtils.getReportFromThrowable(e));
        }
    }

    /**
     * Doc object tu mot class
     * @param fileName
     * @return
     */
    public static Object readObject(String fileName) {
        Object object = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            if (FileUtil.isExistFile(fileName)) {
                fis = JoCoApplication.getInstance().getAppContext()
                    .openFileInput(fileName);
                if (fis != null) {// ton tai file
                    ois = new ObjectInputStream(fis);
                    object = ois.readObject();

                }
            }
        } catch (Exception e) {
            object = null;
            Log.w("readObject", TraceExceptionUtils.getReportFromThrowable(e));
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
            }
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
        return object;
    }


}
