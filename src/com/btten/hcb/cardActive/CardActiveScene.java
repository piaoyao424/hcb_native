package com.btten.hcb.cardActive;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.btten.tools.Log;

public class CardActiveScene extends NomalJsonSceneBase{
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new CardActiveResultItems();
	}
	public void doScence(OnSceneCallBack oncallBack,String name,String pwd){
		SetCallBack(oncallBack);
		targetUrl=UrlFactory.GetUrlOld("DoRegister",
									"name", name,
									"pwd", pwd);
		ThreadPoolUtils.execute(this);
		Log.i("url", targetUrl);
	}
}
