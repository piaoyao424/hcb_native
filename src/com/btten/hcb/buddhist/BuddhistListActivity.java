package com.btten.hcb.buddhist;

import android.os.Bundle;
import android.widget.ListView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class BuddhistListActivity extends BaseActivity {

	private ListView lv;
	private BuddhistListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buddhist_activity);
		setCurrentTitle("每日一禅");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		lv = (ListView) findViewById(R.id.buddhist_activity_lv);
		new BuddhistListScene().doScene(callBack);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			BuddhistListResult items = (BuddhistListResult) data;
			adapter = new BuddhistListAdapter(BuddhistListActivity.this,
					items.items);
			lv.setAdapter(adapter);
			HideProgress();
			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}

}
