package com.btten.hcb.pointRecord;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.hcb.tools.wheelview.WheelDateShow;
import com.btten.hcb.tools.wheelview.WheelShow;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class PointRecordsActivity extends BaseActivity {
	private Button btn_chaxun;
	private String begin;
	private String end;
	private WheelShow startDate;
	private WheelShow endDate;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.point_record);
		initView();
	}

	public void initView() {
		setCurrentTitle("积分记录");
		// 初始化时间按钮
		startDate = (WheelShow) findViewById(R.id.point_records_start_time);
		endDate = (WheelShow) findViewById(R.id.point_records_end_time);
		startDate.setMyOnClickListener(listener);
		endDate.setMyOnClickListener(listener);

		btn_chaxun = (Button) findViewById(R.id.point_records_button);
		btn_chaxun.setOnClickListener(listener);

		listView = (ListView) findViewById(R.id.point_records_list);
		setBackKeyListner(true);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.point_records_start_time:
			case R.id.point_records_end_time:
			case R.id.point_records_button:
				if (requireData()) {
					new PointRecordsListScene().doscene(callBack, begin, end);
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
			PointRecordsActivity.this.Alert("结束日期不能小于开始日期！");
			return false;
		}
		begin += " 00:00:00";
		end += " 23:59:59";
		return true;
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase netScene) {
			HideProgress();
			PointRecordsListResult items = (PointRecordsListResult) data;
			PointRecordsListAdapter adapter = new PointRecordsListAdapter(
					PointRecordsActivity.this);

			TextView textView = (TextView) findViewById(R.id.point_records_totle);
			textView.setText(String.valueOf(items.remainPoints));

			adapter.setItems(items.items);
			listView.setAdapter(adapter);

			if (items.items.length == 0)
				PointRecordsActivity.this.Alert("没有数据！");

		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
