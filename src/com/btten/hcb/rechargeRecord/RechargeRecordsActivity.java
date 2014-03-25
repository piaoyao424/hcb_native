package com.btten.hcb.rechargeRecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.hcb.tools.wheelview.WheelShow;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class RechargeRecordsActivity extends BaseActivity {
	private Button btn_chaxun;
	private String begin;
	private String end;
	private WheelShow startDate;
	private WheelShow endDate;
	private ListView listView;
	private TextView footTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recharge_record);
		setCurrentTitle("充值记录");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		// 初始化时间按钮
		startDate = (WheelShow) findViewById(R.id.point_records_start_time);
		endDate = (WheelShow) findViewById(R.id.point_records_end_time);
		startDate.setMyOnClickListener(listener);
		endDate.setMyOnClickListener(listener);
		btn_chaxun = (Button) findViewById(R.id.point_records_button);
		btn_chaxun.setOnClickListener(listener);
		listView = (ListView) findViewById(R.id.point_records_list);

		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View mFooterView = layoutInflater.inflate(
				R.layout.recharge_record_list_footer, null);
		footTxt = (TextView) mFooterView
				.findViewById(R.id.recharge_records_list_footer_totle);
		listView.addFooterView(mFooterView);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.point_records_start_time:
			case R.id.point_records_end_time:
			case R.id.point_records_button:
				if (requireData()) {
					new RechargeRecordsListScene()
							.doscene(callBack, begin, end);
					ShowRunning();
				}
				break;
			default:
				break;
			}
		}
	};

	private boolean requireData() {
		begin = startDate.getText().toString().trim();
		end = endDate.getText().toString().trim();

		if (begin.length() <= 0 || end.length() <= 0)
			return false;

		StringBuffer startString = new StringBuffer("");
		StringBuffer endString = new StringBuffer("");
		String[] temp1 = begin.split("-", 0);
		String[] temp2 = end.split("-", 0);
		for (int i = 0; i < 3; ++i) {
			startString.append(temp1[i]);
			endString.append(temp2[i]);
		}

		if (Integer.parseInt(startString.toString()) > Integer
				.parseInt(endString.toString())) {
			RechargeRecordsActivity.this.Alert("结束日期不能小于开始日期！");
			return false;
		}
		begin += " 00:00:00";
		end += " 23:59:59";
		return true;
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();
			RechargeRecordsListResult items = (RechargeRecordsListResult) data;
			RechargeRecordsListAdapter adapter = new RechargeRecordsListAdapter(
					RechargeRecordsActivity.this);

			adapter.setItems(items.items);
			listView.setAdapter(adapter);
			footTxt.setText(String.valueOf(items.Points) + "元");
			if (items.items.length == 0)
				RechargeRecordsActivity.this.Alert("没有数据！");

		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
