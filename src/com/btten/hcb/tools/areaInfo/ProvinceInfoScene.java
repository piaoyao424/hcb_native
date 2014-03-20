package com.btten.hcb.tools.areaInfo;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class ProvinceInfoScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new ProvinceListResult();
	}

	public void doScene(OnSceneCallBack callBack,String areaId) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("PublicNotice",
				"getPublicNoticeMaster");
		ThreadPoolUtils.execute(this);
	}
}
