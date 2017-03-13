package com.boot.accommodation.vp.position;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.base.BaseActivity;

/**
 * GPS
 * @version 1.0
 */
public class LocationService extends Service implements
	GoogleApiClient.ConnectionCallbacks,
	GoogleApiClient.OnConnectionFailedListener, LocationListener {

	private GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;
	private PositionPresenterMgr positionPresenterMgr; // presenter day vi tri
	private final int GPS_TIME_OUT = 15000; //GPS stop
	private Handler handlerStopGPS; //Handle stop gps
	private final int MIN_ACCURACY = 30; //unit is m

	@Nullable
	@Override
	public IBinder onBind( Intent intent ) {
		return null;
	}
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		//Stop gps when GPS_TIME_OUT second
		if (handlerStopGPS == null) {
			handlerStopGPS = new Handler() {
				public void handleMessage(Message msg) {
					if (msg.what == 0) {
						stopGPS();
					}
					super.handleMessage(msg);
				}
			};
		}
		if (mLocationRequest == null) {
			mLocationRequest = LocationRequest.create();
		}
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(100); // Update location every second
//		mLocationRequest.setFastestInterval(1000);
//		mLocationRequest.setExpirationTime(GPS_TIME_OUT);
		if (mGoogleApiClient == null) {
			buildGoogleApiClient();
		}
		mGoogleApiClient.connect();
        return Service.START_STICKY;
    }

	synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
			.addConnectionCallbacks(this)
			.addOnConnectionFailedListener(this)
			.addApi(LocationServices.API)
			.build();

	}


	@Override
    public void onCreate() {
        super.onCreate();
    }


	@Override
	public void onConnected(@Nullable Bundle bundle) {
//		LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//				.addLocationRequest(mLocationRequest);
//		PendingResult<LocationSettingsResult> result =
//				LocationServices.SettingsApi.checkLocationSettings(mGoogleClient,
//						builder.build());
		Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		if (mLastLocation != null) {
			JoCoApplication.getInstance().getProfile().getMyGPSInfo().setLattitude(mLastLocation.getLatitude());
			JoCoApplication.getInstance().getProfile().getMyGPSInfo().setLongtitude(mLastLocation.getLongitude());
			JoCoApplication.getInstance().getProfile().save();
		}
		if (JoCoApplication.getInstance().getActivityContext() != null && !((BaseActivity) JoCoApplication.getInstance
				().getActivityContext()).isFinishing()) {
			if (ActivityCompat.checkSelfPermission(JoCoApplication.getInstance().getActivityContext(), android.Manifest
					.permission.ACCESS_FINE_LOCATION) !=
					PackageManager.PERMISSION_GRANTED &&
					ActivityCompat.checkSelfPermission(JoCoApplication.getInstance().getActivityContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
			handlerStopGPS.sendEmptyMessageDelayed(0, GPS_TIME_OUT);
			if (positionPresenterMgr == null) {
				positionPresenterMgr = new PostionPresenter();
			}
		}
	}

	@Override
	public void onConnectionSuspended( int i ) {
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
		}
	}

	/**
	 * Called when the location has changed.
	 * <p/>
	 * <p> There are no restrictions on the use of the supplied Location object.
	 *
	 * @param location The new location, as a Location object.
	 */
	@Override
	public void onLocationChanged( Location location ) {
		if (location != null) {
			String log = "Provider :  "
				+ location.getProvider()
				+ "  -  [lat,lng]: (" + location.getLatitude() + ","
				+ location.getLongitude() + ")" + "  -  Accuracy :  "
				+ location.getAccuracy() + " Radius : " + Locating.RADIUS;
			//sai so qua cho phep, ghi log
			if (location.getAccuracy() > Locating.RADIUS) {
				Log.d("Fused fail", log);
//				if (Math.abs(System.currentTimeMillis() - this.timeSendLogFusedLocating) >= TIME_SEND_LOG_LOCATING &&
//					Utils.isOnline(JoCoApplication.getInstance().getActivityContext())) {
//					this.timeSendLogFusedLocating = System.currentTimeMillis();
////					ServerLogger.sendLog("Locating", log, false, TabletActionLogDTO.LOG_CLIENT);
//				}
			} else{
//				Bundle bd = new Bundle();
//				bd.putParcelable(Constants.IntentExtra.INTENT_DATA, location);
//				((BaseActivity) JoCoApplication.getInstance().getAppContext()).sendBroadcast(Constants.ActionEvent
//					.ACTION_UPDATE_POSITION_SERVICE, bd);
				// If accuracy < min then stop gps to save battery
				if (location.getAccuracy() < MIN_ACCURACY) {
					stopGPS();
				}
				Log.d("fused pass", log);
				if (positionPresenterMgr != null) {
					positionPresenterMgr.updatePosition(location.getLatitude(), location.getLongitude(), location);
				}
            }
		}
	}


	@Override
	public void onConnectionFailed( @NonNull ConnectionResult connectionResult ) {
		buildGoogleApiClient();
	}

    @Override
    public void onDestroy() {
        super.onDestroy();
      	stopGPS();
    }

	/**
	 * Stop gps
	 */
	private void stopGPS () {
		if(mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
			mGoogleApiClient.disconnect();
		}
	}
}
