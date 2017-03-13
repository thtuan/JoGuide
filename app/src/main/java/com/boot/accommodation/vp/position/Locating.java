package com.boot.accommodation.vp.position;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.listener.LocatingListener;

import java.math.BigDecimal;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  Lay toa do hien hanh, ho tro timeout
 *  @version: 1.0
 *  @since: 1.0
 */
public class Locating implements LocationListener{

	public static int TIME_TRIG_POSITION = 5 * 60 * 1000; // thoi gian dinh vi gps dinh ki
	public static long RADIUS = 100; //Radius
	String providerName = "";//ten provider dinh vi(gps, lbs)
	Timer timeOutTimer = null;//quan ly timeout
	LocatingListener listener;//listener
	TimeOutTask timeOutTask;
	boolean isRuning = false;

	// The default search radius when searching for places nearby.
	// The maximum distance the user should travel between location updates.
	// The maximum time that should pass before the user gets a location update.
	public static final long MAX_TIME = 180000;
	public static final long MAX_TIME_RESET = 600000;
	class TimeOutTask extends TimerTask {

		public boolean isCancel = false;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			(JoCoApplication.getInstance().getHandler()).post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (!isCancel) {
						LocationManager locManager = (LocationManager) JoCoApplication
								.getInstance().getAppContext()
								.getSystemService(Context.LOCATION_SERVICE);
						locManager.removeUpdates(Locating.this);
						Locating.this.resetTimer();
						listener.onTimeout(Locating.this);
					}
				}
			});
		}

	}

	public Locating(String locationProviderName, LocatingListener listener) {
		providerName = locationProviderName;
		this.listener = listener;
	}

	public String getProviderName(){
		return providerName;
	}
	
	public void resetTimer() {
		// TODO Auto-generated method stub
		// MyLog.logToFile(Constants.LOG_LBS,"ResetTimer : provider = "+
		// providerName);
		if (timeOutTimer != null) {
			timeOutTimer.cancel();
			timeOutTimer = null;

			timeOutTask.isCancel = true;
			timeOutTask.cancel();
			timeOutTask = null;

			LocationManager locManager = (LocationManager) JoCoApplication
					.getInstance().getAppContext()
					.getSystemService(Context.LOCATION_SERVICE);
			locManager.removeUpdates(Locating.this);
		}
	}

	public boolean isEnableGPS() {
		if (JoCoApplication.getInstance().getAppContext() != null) {
			LocationManager locManager = (LocationManager) JoCoApplication
					.getInstance().getAppContext()
					.getSystemService(Context.LOCATION_SERVICE);
			if (locManager.isProviderEnabled(providerName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * thuc hien lay toa do
	 * @author: AnhND
	 * @param timeout
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public boolean requestLocating(int timeout) {
		// TODO Auto-generated method stub
		boolean result = false;
		// resetTimer();
		// if(isRuning) return false;
		LocationManager locManager = (LocationManager) JoCoApplication.getInstance()
				.getAppContext().getSystemService(Context.LOCATION_SERVICE);
		if (locManager.isProviderEnabled(providerName)) {
			locManager.requestLocationUpdates(providerName, 0, 0.0f,
					Locating.this);
			timeOutTimer = new Timer();
			timeOutTask = new TimeOutTask();
			timeOutTimer.schedule(timeOutTask, timeout);
			//goi de lay toa do cuoi cung gan day
			onLocationChanged(null);
			result = true;
		} else {
			result = false;
		}
		isRuning = result;
		return result;
	}

	@Override
	public void onLocationChanged(Location loc) {
		long minTime = System.currentTimeMillis() - MAX_TIME;
		
		Location oldLoc = JoCoApplication.getInstance().getLocation();
		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestTime = Long.MIN_VALUE;

		if (oldLoc != null && oldLoc.getTime() > minTime) {
			bestResult = oldLoc;
			bestTime = oldLoc.getTime();
			bestAccuracy = oldLoc.getAccuracy();
		}
		LocationManager locationManager = (LocationManager) JoCoApplication
				.getInstance().getAppContext()
				.getSystemService(Context.LOCATION_SERVICE);

		List<String> matchingProviders = locationManager.getAllProviders();
		for (String provider : matchingProviders) {
			Location location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				float accuracy = location.getAccuracy();
				long time = location.getTime();

				if ((time > minTime && accuracy < bestAccuracy)) {
					bestResult = location;
					bestAccuracy = accuracy;
					bestTime = time;
				} else if (time < minTime && BigDecimal.valueOf(bestAccuracy).compareTo(BigDecimal.valueOf(Float.MAX_VALUE)) == 0
						&& time > bestTime) {
					bestResult = location;
					bestTime = time;
				}
			}
		}
		
		if (bestResult != null
				&& (bestTime > minTime && bestAccuracy <= JoCoApplication.getInstance().getRadiusPosition())) {
			if (bestAccuracy < PositionManager.ACCURACY) {
				resetTimer();
			}
			JoCoApplication.getInstance().setLocation(bestResult);
			if (listener != null) {
				listener.onLocationChanged(bestResult);
			}
		}
//		else if (bestResult != null && DateUtils.isInAttendaceTime()
//				&& GlobalUtil.isNearNPPPosition(bestResult)){
//			//uu tien neu trong thoi gian cham cong,
//			//ma toa do tra ve gan NPP thi van chap nhan
//			if (listener != null) {
//				listener.onLocationChanged(bestResult);
//			}
//		}else if(bestResult != null && GlobalUtil.isNearCustomerVisitingPosition(bestResult)){
//			//vi tri gan vi tri khach hang dang ghe tham
//			if (listener != null) {
//				listener.onLocationChanged(bestResult);
//			}
//		} else{
//			if(bestResult != null){
//				if (bestAccuracy > JoCoApplication.getInstance().getRadiusOfPosition()
//						&& (System.currentTimeMillis() - JoCoApplication.getInstance().getTimeSendLogLocating()) >= PositionManager.TIME_SEND_LOG_LOCATING
//						&& Util.isOnline()) {
//					JoCoApplication.getInstance().setTimeSendLogLocating(System
//							.currentTimeMillis());
////					ServerLogger.sendLog("Locating", "Provider :  "
////							+ bestResult.getProvider().toString()
////							+ "  -  [lat,lng]: (" + bestResult.getLatitude() + ","
////							+ bestResult.getLongitude() + ")" + "  -  Accuracy :  "
////							+ bestAccuracy + " Radius: " + JoCoApplication.getInstance().getRadiusOfPosition()
////							, false, TabletActionLogDTO.LOG_CLIENT);
//				}
//			}
//		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		resetTimer();
		if (listener != null) {
			listener.onProviderDisabled(provider);
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.onStatusChanged(provider, status, extras);
		}
	}

	/**
	 * Called when the provider is enabled by the user.
	 *
	 * @param provider the name of the location provider associated with this
	 *                 update.
	 */
	@Override
	public void onProviderEnabled( String provider ) {
		if (listener != null) {
			listener.onProviderEnabled(provider);
		}
	}
}
