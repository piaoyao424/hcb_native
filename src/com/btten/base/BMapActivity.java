package com.btten.base;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapView;
import com.btten.account.VIPAccountManager;
import com.btten.base.BMapActivity;
import com.btten.hcbvip.R;
import com.vincentTools.MapManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class BMapActivity extends BaseActivity {
	private MapView mMapView;
	private MapManager mapManager;
	private String[] jmsinfo_split_str;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bmap_activity);
	}

	private void init() {
		mMapView = (MapView) findViewById(R.id.mapId_wangdian_map);
		// 取出加盟商信息
		SharedPreferences jmsInfo = HcbAPP.getInstance().getSharedPreferences(
				AppTitle.JMS_MAG, Context.MODE_PRIVATE);
		String jms_map_info_str = jmsInfo.getString(AppTitle.JMS_INFO, null);
		jmsinfo_split_str = splitStrToStr2(jms_map_info_str);
		// 取出个人坐标信息
		double gps_la = VIPAccountManager.getInstance().getGpsla(); 
		double gps_lo = VIPAccountManager.getInstance().getGpslo();

		BDLocation userGps = new BDLocation();
		userGps.setLatitude(gps_la);
		userGps.setLongitude(gps_lo);

		// 初始化地图
		mapManager.initMap(mMapView, userGps);
		mapManager.initJMS(jmsinfo_split_str, BMapActivity.this);
	}

	private String[] splitStrToStr2(String info_str) {
		String xiaofengefu = "\\@";
		String[] temp_str = new String[255];

		temp_str = info_str.split(xiaofengefu);
		return temp_str;
	}
}
