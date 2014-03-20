package com.btten.hcb.peccancy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.btten.hcb.vehicleInfo.VehicleInfoActivity;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class PeccancyActivity extends BaseActivity {
	private ListView lv;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.peccancy_activity);
		setCurrentTitle("违章查询");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {

		button = (Button) findViewById(R.id.peccancy_activity_linear);
		button.setOnClickListener(clickListener);
		lv = (ListView) findViewById(R.id.peccancy_activity_lv);
		DoRequest();
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(PeccancyActivity.this,
					VehicleInfoActivity.class);
			intent.putExtra("KEY_ID", "");
			startActivity(intent);
		}
	};

	private void DoRequest() {
		new PeccancyListScene().doScene(callBack);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			PeccancyListResult item = (PeccancyListResult) data;
			PeccancyListAdapter adapter = new PeccancyListAdapter(
					PeccancyActivity.this, item.items);
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
