package com.btten.hcb.branch;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.umeng.common.Log;

public class BranchListScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new BranchListResult();
	}

	public void doScene(OnSceneCallBack callBack, String id) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlMobile("carlife", "g", "cgood", "a",
				"branch", "i", id);
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
