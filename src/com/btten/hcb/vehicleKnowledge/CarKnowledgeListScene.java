package com.btten.hcb.vehicleKnowledge;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.umeng.common.Log;

public class CarKnowledgeListScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new CarKnowledgeListResult();
	}

	public void doScene(OnSceneCallBack callBack) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlMobile("carlife", "g", "cknowledge", "a",
				"list");
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
