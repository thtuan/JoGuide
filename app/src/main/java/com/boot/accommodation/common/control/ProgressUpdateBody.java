package com.boot.accommodation.common.control;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * ProgressUpdateBody
 *
 * @author thtuan
 * @since 7:56 AM 28-07-2016
 */
public class ProgressUpdateBody extends RequestBody {

    private File mFile;
    private int mPosition = -1;
    private String mPath;
    private UploadCallbacks mListener;
    private MultiUploadCallbacks mMultiListener;
    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage);
        void onError();
        void onFinish();
    }
    public interface MultiUploadCallbacks {
        void onProgressUpdate(int percentage, int postion);
        void onError();
        void onFinish(int position);
    }
    public ProgressUpdateBody(final File file, final  MultiUploadCallbacks listener, int position) {
        mFile = file;
        mMultiListener = listener;
        mPosition = position;
    }

    public ProgressUpdateBody(final File file, final  UploadCallbacks listener) {
        mFile = file;
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("multipart/form-data");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                // update progress on UI thread
                if (mPosition == -1){
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                }else {
                    handler.post(new MultiProgressUpdater(uploaded, fileLength));
                }


            }
        } finally {
            in.close();
        }
    }
    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;
        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            mListener.onProgressUpdate((int)(100 * mUploaded / mTotal));
        }
    }
    private class MultiProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;
        public MultiProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            int percent = (int)(100 * mUploaded / mTotal);
            mMultiListener.onProgressUpdate(percent,mPosition);
            if (percent == 100){
                mMultiListener.onFinish(mPosition);
            }
        }
    }
}