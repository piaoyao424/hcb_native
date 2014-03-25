package com.btten.hcb.buyCard;

import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class BuyCardItemScene extends NomalJsonSceneBase {

	public void doscene(OnSceneCallBack callBack) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("Alipay", "getShoppingItemList");
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new BuyCardItemResult();
	}

}
