package com.btten.hcb.publicNotice;

import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class PublicNoticeInfoScene extends NomalJsonSceneBase {
	public PublicNoticeInfoScene() {
		super();
	}

	public void doscene(OnSceneCallBack callBack, String ggid) {
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
		return new PublicNoticeInfoItems();
	}

}
