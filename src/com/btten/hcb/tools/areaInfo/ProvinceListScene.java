package com.btten.hcb.tools.areaInfo;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.umeng.common.Log;

public class ProvinceListScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new ProvinceListResult();
	}

	public void doScene(OnSceneCallBack callBack, String id) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("PublicNotice", "getAreaList", "id",
				id);
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
