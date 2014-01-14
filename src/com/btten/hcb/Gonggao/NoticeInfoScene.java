package com.btten.hcb.Gonggao;

import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class NoticeInfoScene extends NomalJsonSceneBase {
	public NoticeInfoScene() {
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
		return new NoticeInfoItems();
	}

}
