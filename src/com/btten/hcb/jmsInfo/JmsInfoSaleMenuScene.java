package com.btten.hcb.jmsInfo;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.umeng.common.Log;

public class JmsInfoSaleMenuScene extends NomalJsonSceneBase {
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new JmsInfoSaleMenuItems();
	}

	public void doScene(OnSceneCallBack callBack, String jid) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("JmsInfo", "getJmsSellItems", "jid", jid);
		System.out.println(targetUrl);
		Log.d("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
}
