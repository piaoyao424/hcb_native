package com.btten.hcb.vehicleInfo;

import android.util.Log;

import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class VehicleInfoSubmitScene extends NomalJsonSceneBase {

	public void doscene(OnSceneCallBack callBack, int flag, String vehicleID,
			String type, String area, String carNum, String frame, String date,
			String name, String driverLicense, String record) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("Peccancy", "addVehicle",
				"vid", VIPInfoManager.getInstance().getUserid(), "flag",
				String.valueOf(flag), "vehicleid", vehicleID, "type", type,
				"area", area, "carnum", carNum, "date", date, "name", name,
				"driverlicense", driverLicense, "record", record, "frame",
				frame);
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new VehicleInfoSubmitResult();
	}

}
