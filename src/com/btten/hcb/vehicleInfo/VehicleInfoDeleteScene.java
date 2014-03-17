package com.btten.hcb.vehicleInfo;

import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class VehicleInfoDeleteScene extends NomalJsonSceneBase {
	public VehicleInfoDeleteScene() {
		super();
	}

	public void doscene(OnSceneCallBack callBack, String vehicleID) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("JmsInfo", "getSaleAccountHistory",
				"jid", vehicleID);
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new VehicleInfoResult();
	}

}
