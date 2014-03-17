package com.btten.hcb.vehicleInfo;

import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class VehicleInfoSubmitScene extends NomalJsonSceneBase {
	public VehicleInfoSubmitScene() {
		super();
	}

	public void doscene(OnSceneCallBack callBack, int flag, String vehicleID,
			String type, String area, String carNum, String date, String name,
			String driverLicense, String record) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("JmsInfo", "getSaleAccountHistory",
				"flag", String.valueOf(flag), "vehicleid", vehicleID, "type",
				type, "area", area, "carnum", carNum, "date", date, "name",
				name, "driverlicense", driverLicense, "record", record);
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new VehicleInfoResult();
	}

}
