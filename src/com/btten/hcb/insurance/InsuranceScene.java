package com.btten.hcb.insurance;

import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class InsuranceScene extends NomalJsonSceneBase {
	public InsuranceScene() {
		super();
	}

	public void doscene(OnSceneCallBack callBack, String month, String year) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("JmsInfo", "getSaleAccountHistory",
				"jid", "", "month", month, "year", year);
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new InsuranceResult();
	}

}
