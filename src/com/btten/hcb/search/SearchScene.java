package com.btten.hcb.search;

import android.util.Log;

import com.btten.hcb.account.VIPAccountManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class SearchScene extends NomalJsonSceneBase {
	public static final String TAG = "SearchScene";
	private int Flag = 0;

	public SearchScene() {
		super();
	}

	public void doAreaScene(OnSceneCallBack callBack, String Areaid) {

		SetCallBack(callBack);
		Flag = 0;
		targetUrl = UrlFactory.GetUrlNew("PublicNotice", "getAreaItems",
				"areaid", Areaid);
		ThreadPoolUtils.execute(this);
		Log.i(TAG, targetUrl);
	}

	public void doSalesListScene(OnSceneCallBack callBack, String JID) {

		SetCallBack(callBack);
		Flag = 1;
		targetUrl = UrlFactory.GetUrlNew("PublicNotice", "getItems");
		ThreadPoolUtils.execute(this);
		Log.i(TAG, targetUrl);
	}

	public void doSearchScene(OnSceneCallBack callBack, String areaID,
			String itemID) {
		SetCallBack(callBack);
		Flag = 2;
		targetUrl = UrlFactory.GetUrlNew("PublicNotice", "getJmsList",
				"areaID", areaID, "itemID", itemID, "gpsla",
				String.valueOf(VIPAccountManager.getInstance().getGpsla()),
				"gpslo",
				String.valueOf(VIPAccountManager.getInstance().getGpslo()));
		ThreadPoolUtils.execute(this);
		Log.i(TAG, targetUrl);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new SearchResultItems(Flag);
	}

}
