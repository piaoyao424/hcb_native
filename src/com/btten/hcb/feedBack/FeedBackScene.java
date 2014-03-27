package com.btten.hcb.feedBack;

import android.util.Log;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class FeedBackScene extends NomalJsonSceneBase{
	
	public void doscene(OnSceneCallBack callBack, String content){
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("UserBaseInfo",
										 "feedBack",
										 "userid", VIPInfoManager.getInstance().getUserid(),
										 "content", content
										 );
		Log.i("url", targetUrl);
		ThreadPoolUtils.execute(this);
	}
	
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new FeedBackResult();
	}

}
