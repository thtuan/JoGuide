package com.boot.accommodation.base;

import android.util.Log;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.common.info.ServerPath;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.constant.ErrorCode;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.util.DateUtil;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Model co ban
 *
 * @author tuanlt
 * @since: 11:56 AM 5/6/2016
 */
public class BaseModel<T extends BaseResponse>  {
    protected static final String XAPITOKEN = "X-APITOKEN"; //header token
    protected static final String X_CLIENT_TIME = "X-CLIENT-TIME";
    protected static final String X_MOBILE_VERSION = "X-MOBILE-VERSION";
    public static final String X_SECRET_MESSAGE = "X-SECRET-MESSAGE";
    protected static OkHttpClient client = new OkHttpClient.Builder()
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES).addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder ongoing = chain.request().newBuilder();
                ongoing.addHeader(XAPITOKEN, PreferenceUtils.getString(Constants.Preference
                                .PREFERENCE_USER_TOKEN, ""));
                ongoing.addHeader(X_CLIENT_TIME, DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
                ongoing.addHeader(X_MOBILE_VERSION, JoCoApplication.getInstance().getAppVersion());
                ongoing.addHeader(X_SECRET_MESSAGE, "chim cut goi dai bang! di phuot deee...");
                return chain.proceed(ongoing.build());
            }
        }).build();
    protected static final Retrofit RETROFIT = new Retrofit.Builder()
        .baseUrl(ServerPath.getHostUrl())
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    protected Call<T> call;
    int numProcessing = 0;
    protected static HashMap<String, List<Call>> lstCall = new HashMap<>();
    /**
     * Thuc hien request API
     * @param callback
     */
    public void requestAPI( final ModelCallBack callback) {
        addRequest(call, callback.getTag());
        numProcessing++;
        call.enqueue(new retrofit2.Callback<T>() {
            @Override
            public void onResponse(Call<T> call ,Response<T> response) {
                if (response == null || !response.isSuccess()) {
                    if (response.code() == ErrorCode.UN_AUTHORIZE) {
                        callback.onError(ErrorCode.UN_AUTHORIZE, null);
                    } else {
                        callback.onError(ErrorCode.ERR_COMMON, null
                        );
                    }
                    return;
                }

                T body = response.body();
                boolean isSuccess = body.getErrorCode() == ErrorCode.NO_ERROR ? true : false;
                if (isSuccess) {
                    callback.onSuccess(body);
                } else {
                    Log.e(Constants.LogName.EXCEPTION, call.request().method() + call.request().body());
                    callback.onError(body.getErrorCode(),body.getError());
                }
                finishProccess();
            }

            @Override
            public void onFailure(Call<T> call,Throwable t) {
                if(!call.isCanceled()) {
                    if (t instanceof UnknownHostException) {
                        callback.onError(ErrorCode.NO_CONNECTION, Utils.getString(R.string.error_network));
                        return;
                    }
                    callback.onError(ErrorCode.ERR_COMMON, Utils.getString(R.string.error_unknown_error));
                    finishProccess();
                }
            }

            private void finishProccess() {
                numProcessing--;
//                if(lstCall != null) {
//                    lstCall.remove(call);
                    if(numProcessing <= 0) {
                        numProcessing = 0;
//                        callback.onFinishAllProcess();
                    }
//                }
            }
        });
    }

    /**
     * Add request to list
     * @param call
     */
    protected void addRequest(Call call, String tag){
        if(!StringUtil.isNullOrEmpty(tag)) {
            Iterator myVeryOwnIterator = lstCall.keySet().iterator();
            List<Call> data = new ArrayList<>();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                if (tag.equals(key)) {
                    data = lstCall.get(key);
                    break;
                }
            }
            data.add(call);
            lstCall.put(tag, data);
        }
    }

    /**
     * Cancel request
     * @param tag
     */
    protected void cancelRequest(String tag){
        if (!StringUtil.isNullOrEmpty(tag)) {
            Iterator myVeryOwnIterator = lstCall.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                if (tag.equals(key)) {
                    List<Call> data = lstCall.get(key);
                    for (Call callItem : data) {
                        if (!callItem.isCanceled()) {
                            callItem.cancel();
                        }
                    }
                    myVeryOwnIterator.remove();
                    break;
                }
            }
        }
    }

}

