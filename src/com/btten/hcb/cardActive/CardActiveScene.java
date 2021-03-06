package com.btten.hcb.cardActive;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.btten.tools.Log;

public class CardActiveScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new CardActiveResult();
	}

	public void doScence(OnSceneCallBack oncallBack, String name, String content) {
		SetCallBack(oncallBack);
		targetUrl = UrlFactory.GetUrlNew("Register", "activateCard", "name",
				name, "content", content);
		ThreadPoolUtils.execute(this);
		Log.i("url", targetUrl);
	}
}
