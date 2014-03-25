package com.btten.hcb.pointRecord;

import android.util.Log;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class PointRecordsListScene extends NomalJsonSceneBase {
	public PointRecordsListScene() {
		super();
	}

	public void doscene(OnSceneCallBack callBack, String start, String end) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("UserBaseInfo", "getPoints",
				"vid", VIPInfoManager.getInstance().getUserid(), "begin",
				start, "end", end);
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new PointRecordsListResult();
	}

}
