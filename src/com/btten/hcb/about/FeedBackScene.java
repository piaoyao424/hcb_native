package com.btten.hcb.about;

import android.util.Log;

import com.btten.hcb.account.VIPAccountManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class FeedBackScene extends NomalJsonSceneBase{
	public FeedBackScene(){
		super();
	}
	
	public void doscene(OnSceneCallBack callBack, String content){
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("UserBaseInfo",
										 "feedBack",
										 "userid", VIPAccountManager.getInstance().getUserid(),
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
