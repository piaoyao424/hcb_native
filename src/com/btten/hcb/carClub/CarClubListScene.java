package com.btten.hcb.carClub;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.umeng.common.Log;

public class CarClubListScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new CarClubListResult();
	}

	public void doScene(OnSceneCallBack callBack) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlMobile("club","g","club","a","list") ;
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
