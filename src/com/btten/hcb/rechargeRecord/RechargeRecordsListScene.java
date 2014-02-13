package com.btten.hcb.rechargeRecord;

import android.util.Log;
import com.btten.hcb.account.VIPAccountManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class RechargeRecordsListScene extends NomalJsonSceneBase {
	public RechargeRecordsListScene() {
		super();
	}

	public void doscene(OnSceneCallBack callBack, String month, String year) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("JmsInfo", "getSaleAccountHistory",
				"vid", VIPAccountManager.getInstance().getUserid(), "start",
				month, "end", year);
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new RechargeRecordsListResult();
	}

}
