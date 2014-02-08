package com.btten.hcb.serviceEvaluation;

import android.util.Log;

import com.btten.account.VIPAccountManager;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;

public class ServiceEvaluationScene extends NomalJsonSceneBase {
	public static final String TAG = "SearchScene";

	public ServiceEvaluationScene() {
		super();
	}

	public void doServiceEvaluationListScene(OnSceneCallBack callBack, String JID) {

		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("PublicNotice", "getItems");
		ThreadPoolUtils.execute(this);
		Log.i(TAG, targetUrl);
	}

	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new ServiceEvaluationResultItems();
	}

}
