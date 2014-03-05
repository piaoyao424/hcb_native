package com.btten.hcb.buddhist;

import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class BuddhistInfoScene extends NomalJsonSceneBase {
	public BuddhistInfoScene() {
		super();
	}

	public void doScene(OnSceneCallBack callBack, String ggid) {
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("PublicNotice",
										 "getPublicNoticeDetail",
										 "id",ggid
										 );
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new PublicNoticeInfoResult();
	}

}
