package com.btten.hcb.rechargeRecord;

import android.util.Log;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class RechargeRecordsListScene extends NomalJsonSceneBase {

	public void doscene(OnSceneCallBack callBack, String begin, String end) {
		SetCallBack(callBack);
		// targetUrl = UrlFactory.GetUrlNew("UserBaseInfo",
		// "getHuibiRecordList",
		// "vid", VIPInfoManager.getInstance().getUserid(), "begin",
		// begin, "end", end);
		targetUrl = UrlFactory.GetUrlNew("UserBaseInfo", "getHuibiRecordList",
				"vid", "10001450", "begin",
				begin, "end", end);
		
		
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new RechargeRecordsListResult();
	}

}
