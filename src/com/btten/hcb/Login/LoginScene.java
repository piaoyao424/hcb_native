package com.btten.hcb.Login;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class LoginScene extends NomalJsonSceneBase {
	public static final String TAG = "LoginScene";

	public LoginScene() {
		super();
	}

	public void doScene(OnSceneCallBack callBack, String name, String pwd) {

		SetCallBack(callBack);

		targetUrl = UrlFactory.GetUrlNew("UserBaseInfo", "loginVIP", "pw", pwd,
				"mobile", name);
		ThreadPoolUtils.execute(this);
		Log.i(TAG, targetUrl);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new LoginResultItems();
	}

}
