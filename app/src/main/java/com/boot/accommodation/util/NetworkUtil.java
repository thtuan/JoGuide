package com.boot.accommodation.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;

import java.math.BigDecimal;
import java.util.List;

public class NetworkUtil {

	// Loai mang
	public static final String NETWORK_NOT_CONNECTED = "Not Connected";
	public static final String NETWORK_TYPE_WIFI = "Wifi";
	public static final String NETWORK_TYPE_GPRS = "GPRS";
	public static final String NETWORK_TYPE_CDMA = "CDMA(2G)";
	public static final String NETWORK_TYPE_IDEN = "IDEN(2G)";
	public static final String NETWORK_TYPE_EDGE = "EDGE(2G)";
	public static final String NETWORK_TYPE_1xRTT = "1xRTT(2G)";
	public static final String NETWORK_TYPE_UMTS = "UMTS(3G)";
	public static final String NETWORK_TYPE_EVDO_0 = "EVDO_0(3G)";
	public static final String NETWORK_TYPE_EVDO_A = "EVDO_A(3G)";
	public static final String NETWORK_TYPE_HSDPA = "HSDPA(3G)";
	public static final String NETWORK_TYPE_HSPA = "HSPA(3G)";
	public static final String NETWORK_TYPE_HSUPA = "HSUPA(3G)";
	public static final String NETWORK_TYPE_EVDO_B = "EVDO_B(3G)";
	public static final String NETWORK_TYPE_EHRPD = "EHRPD(3G)";
	public static final String NETWORK_TYPE_HSPAP = "HSPAP(4G)";
	public static final String NETWORK_TYPE_LTE = "LTE(4G)";
	public static final String NETWORK_TYPE_UNKNOWN = "Unknown";
	public static final String NETWORK_TYPE_3G = "3G";
	public static final String NETWORK_TYPE_3G_UNKNOWN = "(3G)";

	// Toc do
	public static final int NETWORK_WEAK = 0;
	public static final int NETWORK_STRONG = 1;
	public static final int NETWORK_VERY_STRONG = 2;

	// Neu build tren API 13 thi khong can dung bien nay
	public final int API_NETWORK_TYPE_HSPAP = 15;

	int MIN_RSSI = -100;
	int MAX_RSSI = -55;
	int levels = 101;

	private int levelSignalMobile = 0;
	private PhoneStateListener listener;

	// Su dung inner class ket hop singleton
	public static class NetworkHolder {
		public static final NetworkUtil INSTANCE = new NetworkUtil();
	}

	private NetworkUtil() {
		listener = new MyPhoneStateListener();
	}

	public void startListenerSignal() {
		TelephonyManager telManager = getTelephonyManager();
		telManager.listen(listener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
	}

	public void stopListenerSignal() {
		TelephonyManager telManager = getTelephonyManager();
		telManager.listen(listener, PhoneStateListener.LISTEN_NONE);
		levelSignalMobile = NETWORK_WEAK;
	}

	public static NetworkUtil me() {
		return NetworkHolder.INSTANCE;
	}

	public void updateLevelSignalMobile(int level) {
		levelSignalMobile = level;
	}

	public TelephonyManager getTelephonyManager() {
		Context mContext = JoCoApplication.getInstance().getAppContext();
		TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		return tm;
	}

	public ConnectivityManager getNetworkManager() {
		Context mContext = JoCoApplication.getInstance().getAppContext();
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm;
	}

	public WifiManager getWifiManager() {
		Context mContext = JoCoApplication.getInstance().getAppContext();
		WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		return wifi;
	}

	/**
	 * Get the network info
	 *
	 * @return: NetworkInfo
	 * @throws:
	 * @return
	 */
	public android.net.NetworkInfo getNetworkInfo() {
		ConnectivityManager cm = getNetworkManager();
		return cm.getActiveNetworkInfo();
	}

	/**
	 * Tra ve loai mang dang connect
	 * 
	 * @return: String
	 * @throws:
	 * @return
	 * @throws Exception
	 */
	public NetworkInfo getCurrentTypeConnect() throws Exception {
		NetworkInfo dmsInfo = null;
		android.net.NetworkInfo info = getNetworkInfo();
		if (Utils.isOnline(JoCoApplication.getInstance().getApplicationContext()) && info != null) {
			int type = info.getType();
			int subType = info.getSubtype();
			dmsInfo = getTypeConnect(type, subType);
		}

		if (dmsInfo == null) {
			dmsInfo = new NetworkInfo();
			dmsInfo.type = NETWORK_NOT_CONNECTED;
			dmsInfo.value = NETWORK_WEAK;
		}

		return dmsInfo;
	}

	/**
	 * Tra ve loai mang dang connect
	 * 
	 * @return: String
	 * @throws:
	 * @param type
	 * @param subType
	 * @return
	 */
	private NetworkInfo getTypeConnect( int type, int subType)
			throws Exception {
		NetworkInfo info = new NetworkInfo();
		if (type == ConnectivityManager.TYPE_WIFI) {
			Context mContext = JoCoApplication.getInstance().getAppContext();
			WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifi.getConnectionInfo();
			info.type = NETWORK_TYPE_WIFI;

			if (wifiInfo != null) {
				info.value = getSignalStrengthWifi(wifiInfo);
			} else {
				info.value = NETWORK_WEAK;
			}

		} else if (type == ConnectivityManager.TYPE_MOBILE) {
			switch (subType) {
			case TelephonyManager.NETWORK_TYPE_GPRS: // ~ 100 kbps
				info.type = NETWORK_TYPE_GPRS;
				break;
			case TelephonyManager.NETWORK_TYPE_CDMA: // ~ 14-64 kbps
				info.type = NETWORK_TYPE_CDMA;
				break;
			case TelephonyManager.NETWORK_TYPE_IDEN: // ~ 25 kbps
				info.type = NETWORK_TYPE_IDEN;
				break;
			case TelephonyManager.NETWORK_TYPE_EDGE: // ~ 50-100 kbps
				info.type = NETWORK_TYPE_EDGE;
				break;
			case TelephonyManager.NETWORK_TYPE_1xRTT: // ~ 50-100 kbps
				info.type = NETWORK_TYPE_1xRTT;
				break;
			case TelephonyManager.NETWORK_TYPE_UMTS: // ~ 400-7000 kbps
				info.type = NETWORK_TYPE_UMTS;
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_0: // ~ 400-1000 kbps
				info.type = NETWORK_TYPE_EVDO_0;
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_A: // ~ 600-1400 kbps
				info.type = NETWORK_TYPE_EVDO_A;
				break;
			case TelephonyManager.NETWORK_TYPE_HSDPA: // ~ 2-14 Mbps
				info.type = NETWORK_TYPE_HSDPA;
				break;
			case TelephonyManager.NETWORK_TYPE_HSPA: // ~ 700-1700 kbps
				info.type = NETWORK_TYPE_HSPA;
				break;
			case TelephonyManager.NETWORK_TYPE_HSUPA: // ~ 1-23 Mbps
				info.type = NETWORK_TYPE_HSUPA;
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_B: // ~ 5 Mbps
				info.type = NETWORK_TYPE_EVDO_B;
				break;
			case TelephonyManager.NETWORK_TYPE_EHRPD: // ~ 1-2 Mbps
				info.type = NETWORK_TYPE_EHRPD;
				break;
			// Sau nay neu build API >= 13 thi mo ra
			// case TelephonyManager.NETWORK_TYPE_HSPAP:
			case API_NETWORK_TYPE_HSPAP: // ~ 10-20 Mbps
				info.type = NETWORK_TYPE_HSPAP;
				break;
			case TelephonyManager.NETWORK_TYPE_LTE: // ~ 10+ Mbps
				info.type = NETWORK_TYPE_LTE;
				break;
			// Unknown
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				info.type = NETWORK_TYPE_3G;
				break;
			default:
				info.type = NETWORK_TYPE_3G_UNKNOWN;
			}

			info.value = levelSignalMobile;
		} else {
			info.type = NETWORK_TYPE_UNKNOWN;
			info.value = NETWORK_WEAK;
		}

		return info;
	}

	/**
     * Lay tin hieu wifi
	 * @return: int
	 * @throws:
	 * @param wifiInfo
	 * @return
	 */
	private int getSignalStrengthWifi(WifiInfo wifiInfo) {
		int strength = NETWORK_WEAK;
		List<ScanResult> results = getWifiManager().getScanResults();
		if (results != null) {
			for (ScanResult result : results) {
				if (result != null && result.BSSID != null
						&& result.BSSID.equals(wifiInfo.getBSSID())) {
					int level = calculateSignalLevel(wifiInfo.getRssi(), result.level);
					level = level * 100 / result.level;
					strength = getSignalStrength(level);
					break;
				}
			}
		}
		return strength;
	}

	public class NetworkInfo {
		public String type; // "WIFI" or "MOBILE"
		public int value; // 0 - yeu, 1 - manh, 2 - rat manh
	}

	public int calculateSignalLevel(int rssi, int numLevels) {
		int level = 0;
		if (rssi <= MIN_RSSI) {
			level = 0;
		} else if (rssi >= MAX_RSSI) {
			level = numLevels - 1;
		} else {
			double inputRange = (MAX_RSSI - MIN_RSSI);
			double outputRange = (numLevels - 1);
//			if (inputRange != 0) {			
			if (BigDecimal.valueOf(inputRange).compareTo(BigDecimal.ZERO) != 0) {
				level = (int) ((rssi - MIN_RSSI) * outputRange / inputRange);
			}
		}

		return level;
	}

	public int getSignalStrength(int level) {
		int strength = 0;
		if (level > 75) {
			strength = NETWORK_VERY_STRONG; // Rat manh
		} else if (level > 50) {
			strength = NETWORK_STRONG; // Manh
		} else {
			strength = NETWORK_WEAK; // Yeu
		}

		return strength;
	}

	private class MyPhoneStateListener extends PhoneStateListener {

		public int singalStenths = 0;

		@Override
		public void onSignalStrengthsChanged(SignalStrength signalStrength) {
			super.onSignalStrengthsChanged(signalStrength);
			singalStenths = signalStrength.getGsmSignalStrength();

			if (singalStenths > 30) {
				updateLevelSignalMobile(NETWORK_VERY_STRONG);
			} else if (singalStenths > 20 && singalStenths < 30) {
				updateLevelSignalMobile(NETWORK_STRONG);
			} else if (singalStenths < 20) {
				updateLevelSignalMobile(NETWORK_WEAK);
			}
		}
	};

	public boolean isDisconnectOrUnknown(String networkType) {
		return NETWORK_NOT_CONNECTED.equalsIgnoreCase(networkType)
				|| NETWORK_TYPE_UNKNOWN.equalsIgnoreCase(networkType);
	}

	/**
     * Lay toc do mang
	 * @return: String
	 * @throws:
	 * @return
	 */
	public String getSpeed(int networkSpeed) {
		String speed = "";
		switch (networkSpeed) {
		case NETWORK_VERY_STRONG:
			speed = StringUtil.getString(R.string.text_network_speed_very_strong);
			break;
		case NETWORK_STRONG:
			speed = StringUtil.getString(R.string.text_network_speed_strong);
			break;
		default:
			speed = StringUtil.getString(R.string.text_network_speed_weak);
			break;
		}

		return speed;
	}
}