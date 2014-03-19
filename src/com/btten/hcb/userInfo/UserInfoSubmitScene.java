package com.btten.hcb.userInfo;

import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.umeng.common.Log;

public class UserInfoSubmitScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new UserInfoResult();
	}

	public void doScene(OnSceneCallBack callBack, String province, String city,
			String area, String gerder, String address, String phone,
			String consignee, String username,String email) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("UserBaseInfo", "saveUserInfo", "vid",
				VIPInfoManager.getInstance().getUserid(), "province", province,
				"city", city, "area", area, "gerder", gerder, "address",
				address, "phone", phone, "consignee", consignee, "username",
				username,"email",email);
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
