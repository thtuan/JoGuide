package com.boot.accommodation.vp.position;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.response.CommonResponseDTO;
import com.boot.accommodation.dto.view.GPSInfoDTO;
import com.boot.accommodation.dto.view.SettingViewDTO;
import com.boot.accommodation.dto.view.UserPositionLogDTO;
import com.boot.accommodation.listener.ModelCallBack;
import com.boot.accommodation.model.impl.PositionModel;
import com.boot.accommodation.model.mgr.PositionModelMgr;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;

/**
 * Xu li vi tri
 *
 * @author tuanlt
 * @since 2:31 PM 6/7/2016
 */
public class PostionPresenter implements PositionPresenterMgr {

    PositionModelMgr positionModelMgr; // model cap nhat vi tri
    public PostionPresenter(){
        positionModelMgr = new PositionModel();
    }
    /**
     * Cap nhat vi tri len server
     *
     * @param lng
     * @param lat
     * @param loc
     */
    @Override
    public void updatePosition( double lat, double lng, Location loc ) {
        //kiem tra toa do nam trong lanh tho VN
//        if (lat < 8.45 || lng < 102.0
//            || lat > 23.5 || lng > 110) {
//            Log.d(Constants.LogName.LOG_GPS, "updatePosition, lng = "
//                + JoCoApplication.getInstance().getProfile().getMyGPSInfo()
//                .getLongtitude() + " lat = ");
//            return;
//        }

        // kiem tra ghi log vi tri len server
        if (lat != Constants.LAT_LNG_NULL && lng != Constants.LAT_LNG_NULL
            && JoCoApplication.getInstance().getProfile() != null
            && JoCoApplication.getInstance().getProfile().getUserData() != null) {

//            double lastLat = JoCoApplication.getInstance().getProfile()
//                .getMyGPSInfo().getLatitude();
//            double lastLng = JoCoApplication.getInstance().getProfile()
//                .getMyGPSInfo().getLongtitude();
            //han che goi nhieu + vi tri dang tin
            //show locating success
//            ((BaseActivity) JoCoApplication.getInstance().getActivityContext())
//                .showMessage(StringUtil.getString(R.string
//                    .text_locating_success));

            JoCoApplication.getInstance().setTimeSendLogPosition(System.currentTimeMillis());

            //phai trong thoi gian dong bo thi goi
            Log.d("Locating", "send log ................. ");
//            UserPositionLogDTO userPositionLogDTO = new UserPositionLogDTO();
//            userPositionLogDTO.setCreateAt(DateUtil.formatNow(DateUtil.FORMAT_DATE_TIME_ZONE));
//            userPositionLogDTO.setLat(lat);
//            userPositionLogDTO.setLng(lng);
//            userPositionLogDTO.setAccuracy(loc.getAccuracy());
//            userPositionLogDTO.setUserId(Long.valueOf(JoCoApplication.getInstance().getProfile().getUserData().getId
//                ()));
//            userPositionLogDTO.setBattery(Utils.getBatteryPercent());
//            NetworkUtil.NetworkInfo network = null;
//            try {
//                network = NetworkUtil.me().getCurrentTypeConnect();
//                if (network != null) {
//                    userPositionLogDTO.setNetworkType(network.type);
//                    userPositionLogDTO.setNetworkSpeed(network.value);
//                }
//            } catch (Exception e) {
//                Log.e("Locating", TraceExceptionUtils.getReportFromThrowable(e));
//            }

            if (!StringUtil.isNullOrEmpty(JoCoApplication.getInstance().getProfile().getUserData().getId())
                && Utils.getNumAsyncTaskActive() <= 3
                && Utils.isOnline(JoCoApplication.getInstance().getActivityContext())) {
//                handleUpdatePosition(userPositionLogDTO);
            } else {
                // luu vi tri de dong bo offline
//                JoCoApplication.getInstance().addPosition(userPositionLogDTO);
            }
        }

        //luu vi tri moi
        JoCoApplication.getInstance().setLocation(loc);
        JoCoApplication.getInstance().getProfile().setMyGPSInfo(new GPSInfoDTO(lat,lng));
//        JoCoApplication.getInstance().getProfile().getMyGPSInfo().setLattitude(lat);
//        JoCoApplication.getInstance().getProfile().save();

        Bundle bd = new Bundle();
        if (JoCoApplication.getInstance().getActivityContext() instanceof BaseActivity) {
            ((BaseActivity) JoCoApplication.getInstance().getActivityContext())
                .sendBroadcast(Constants.ActionEvent.ACTION_UPDATE_POSITION, bd);
        }
    }

    /**
     * handle goi update Position
     * @param userPositionLogDTO
     */
    private void handleUpdatePosition(final UserPositionLogDTO userPositionLogDTO) {
        if (JoCoApplication.getInstance().getSettingLocation() == SettingViewDTO.SEND_LOCATION) {
            positionModelMgr.updatePosition(userPositionLogDTO, new ModelCallBack<CommonResponseDTO>() {
                @Override
                public void onSuccess(CommonResponseDTO response) {

                }

                @Override
                public void onError(int errorCode, String error) {
//                    JoCoApplication.getInstance().addPosition(userPositionLogDTO);
                }

            });
        }
    }
}
