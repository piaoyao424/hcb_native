package com.btten.hcb.userInfo;

import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.umeng.common.Log;

public class UserInfoScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new UserInfoResult();
	}

	public void doScene(OnSceneCallBack callBack) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("UserBaseInfo", "getBaseInfo", "vid",
				VIPInfoManager.getInstance().getUserid());
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
