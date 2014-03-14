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

	public void doScene(OnSceneCallBack callBack,String partyType,int processType) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("PublicNotice",
				"getPublicNoticeMaster");
		System.out.println(targetUrl);
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
