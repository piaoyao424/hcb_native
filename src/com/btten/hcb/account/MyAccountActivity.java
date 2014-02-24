package com.btten.hcb.account;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class MyAccountActivity extends BaseActivity {
	private Button btn_chaxun;
	private TextView txt_name, txt_hb, txt_point;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_activity);
		setBackKeyListner(true);
		initView();
		if (IsLogin()) {
			new MyAccountScene().doscene(callBack);
		}
	}

	public void initView() {
		setCurrentTitle("我的账户");
		btn_chaxun = (Button) findViewById(R.id.point_records_button);
		btn_chaxun.setOnClickListener(listener);

		txt_name = (TextView) findViewById(R.id.myaccount_activity_name);
		txt_hb = (TextView) findViewById(R.id.myaccount_activity_hb);
		txt_point = (TextView) findViewById(R.id.myaccount_activity_point);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.point_records_start_time:
			case R.id.point_records_end_time:
			case R.id.point_records_button:
				break;
			default:
				break;
			}
		}
	};

	OnSceneCallBack callBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();
			MyAccountResult item = (MyAccountResult) data;
			txt_name.setText(item.item.name);
			txt_hb.setText(item.item.hbValue);
			txt_point.setText(item.item.pointValue);
		}
	};
}
