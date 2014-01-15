package com.btten.hcb.search;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class SearchScene extends NomalJsonSceneBase {
	public static final String TAG = "SearchScene";

	public SearchScene() {
		super();
	}

	public void doAreaScene(OnSceneCallBack callBack, String Areaid) {

		SetCallBack(callBack);

		targetUrl = UrlFactory.GetUrlOld("DoAreaList", "areaid", Areaid);
		ThreadPoolUtils.execute(this);
		Log.i(TAG, targetUrl);
	}

	public void doSalesListScene(OnSceneCallBack callBack, String JID) {

		SetCallBack(callBack);

		targetUrl = UrlFactory.GetUrlOld("Sale", "getItems", "jid", JID);
		ThreadPoolUtils.execute(this);
		Log.i(TAG, targetUrl);
	}

	public void doSearchScene(OnSceneCallBack callBack, String name, String pwd) {
		SetCallBack(callBack);

		targetUrl = UrlFactory
				.GetUrlOld("DoJmsLogin", "pwd", pwd, "name", name);
		ThreadPoolUtils.execute(this);
		Log.i(TAG, targetUrl);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		// TODO Auto-generated method stub
		return new SearchResultItems();
	}

}
