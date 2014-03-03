package com.btten.hcb.map;

import java.util.ArrayList;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapView;
import com.btten.base.BaseActivity;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.hcb.map.BMapActivity;
import com.btten.hcbvip.R;
import com.btten.vincenttools.MapManager;

import android.os.Bundle;

public class BMapActivity extends BaseActivity {
	private MapView mMapView;
	private MapManager mapManager;
	private ArrayList<JmsGps> jmsGps = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bmap_activity);
		initView();
	}

	@SuppressWarnings("unchecked")
	public void initView() {
		mMapView = (MapView) findViewById(R.id.mapId_wangdian_map);
		mapManager = new MapManager(getBaseContext());

		// 取出个人坐标信息
		double gps_la = VIPInfoManager.getInstance().getGpsla();
		double gps_lo = VIPInfoManager.getInstance().getGpslo();

		BDLocation userGps = new BDLocation();
		userGps.setLatitude(gps_la);
		userGps.setLongitude(gps_lo);

		// 初始化地图

		mapManager.initMap(mMapView, userGps);

		try {
			// 取出加盟商信息
			Bundle bundle = this.getIntent().getExtras();
			jmsGps = (ArrayList<JmsGps>) (bundle.get("KEY_JMSGPS"));
			if (jmsGps != null) {
				mapManager.initJMS(jmsGps, BMapActivity.this);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
