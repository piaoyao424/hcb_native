package com.btten.hcb.commission;

import android.os.Bundle;
import android.widget.ListView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class CommissionActivity extends BaseActivity {
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commission_activity);
		setCurrentTitle("车务代办");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		lv = (ListView) findViewById(R.id.commission_activity_lv);
		new CommissionListScene().doScene(callBack);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			CommissionListResult item = (CommissionListResult) data;
			CommissionListAdapter adapter = new CommissionListAdapter(
					CommissionActivity.this, item.items);
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

	}

}
