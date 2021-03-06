package com.btten.hcb.changePassword;

import android.util.Log;

import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class ChangePwdScene extends NomalJsonSceneBase {
	public static final String TAG = "ChangePwdScene";

	public ChangePwdScene() {
		super();
	}

	public void doChangePwdScene(OnSceneCallBack callBack, String oldpwd,
			String newpwd) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("Register", "changePasswordForVip",
				"mobile", VIPInfoManager.getInstance().getUserPhone(), "oldpw",
				oldpwd, "newpw", newpwd);
		ThreadPoolUtils.execute(this);
		Log.i(TAG, targetUrl);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new ChangePwdResult();
	}

}
