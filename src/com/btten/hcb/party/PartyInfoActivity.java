package com.btten.hcb.party;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.tools.InfoQuery;

public class PartyInfoActivity extends BaseActivity {

	private TextView txtTitle, txtType, txtAddr, txtDate, txtProcess, txtOther;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.party_info_activity);
		setCurrentTitle("活动详情");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {

		txtTitle = (TextView) findViewById(R.id.party_info_title);
		txtType = (TextView) findViewById(R.id.party_info_type);
		txtAddr = (TextView) findViewById(R.id.party_info_addr);
		txtProcess = (TextView) findViewById(R.id.party_info_process);
		txtDate = (TextView) findViewById(R.id.party_info_date);
		txtOther = (TextView) findViewById(R.id.party_info_other);
		Bundle bundle = this.getIntent().getExtras();
		new PartyInfoScene().doScene(callBack, bundle.getString("KEY_ID"));
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			PartyInfoResult items = (PartyInfoResult) data;

			txtTitle.setText(items.item.title);
			txtType.setText(items.item.type);
			txtAddr.setText(items.item.addr);
			txtProcess.setText(InfoQuery.ToDBC(items.item.process));
			txtDate.setText("开始时间" + items.item.startDate + " 共"
					+ items.item.totleDate + "天");
			txtOther.setText(InfoQuery.ToDBC(items.item.other));

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
