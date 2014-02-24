package com.btten.hcb.account;

import android.util.Log;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class MyAccountScene extends NomalJsonSceneBase {
	public MyAccountScene() {
		super();
	}

	public void doscene(OnSceneCallBack callBack) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("UserBaseInfo", "getBaseInfo", "vid",
				VIPInfoManager.getInstance().getUserid());
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new MyAccountResult();
	}

}
