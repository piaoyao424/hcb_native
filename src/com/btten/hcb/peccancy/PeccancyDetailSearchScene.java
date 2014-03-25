package com.btten.hcb.peccancy;

import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.umeng.common.Log;

public class PeccancyDetailSearchScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new PeccancyDetailSearchResult();
	}

	public void doScene(OnSceneCallBack callBack, String vehicleid) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("Peccancy", "getVehiclePeccancy",
				"vid", VIPInfoManager.getInstance().getUserid(), "vehicleid",
				vehicleid);
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
