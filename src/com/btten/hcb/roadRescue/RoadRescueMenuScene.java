package com.btten.hcb.roadRescue;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.umeng.common.Log;

public class RoadRescueMenuScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new RoadRescueMenuResult();
	}

	public void doScene(OnSceneCallBack callBack)
	{
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("JmsInfo","getJmsRescueItemList"
				);
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
