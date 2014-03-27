package com.btten.hcb.shoppingRecord;

import android.util.Log;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class ShoppingDetaiScene extends NomalJsonSceneBase {
	public ShoppingDetaiScene() {
		super();
	}

	public void doscene(OnSceneCallBack callBack, String id) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("UserBaseInfo", "getDetailExpense",
				"vid", VIPInfoManager.getInstance().getUserid(), "id", id);
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new ShoppingRecordsListResult();
	}

}
