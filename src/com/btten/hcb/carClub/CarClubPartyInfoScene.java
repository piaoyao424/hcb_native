package com.btten.hcb.carClub;

import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class CarClubPartyInfoScene extends NomalJsonSceneBase {

	public void doScene(OnSceneCallBack callBack, String id) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlMobile("club", "g", "club", "a", "info",
				"i", id);
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new CarClubPartyInfoResult();
	}

}
