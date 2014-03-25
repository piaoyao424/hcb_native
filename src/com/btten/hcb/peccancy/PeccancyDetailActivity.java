package com.btten.hcb.peccancy;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class PeccancyDetailActivity extends BaseActivity {
	private TextView txtCount, txtDate;
	private ListView lv;
	private String vehicleid;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.peccancy_detail);
		setCurrentTitle("违章详情");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		Bundle bundle = this.getIntent().getExtras();
		vehicleid = bundle.getString("KEY_ID");

		txtCount = (TextView) findViewById(R.id.peccancy_detail_count);
		txtDate = (TextView) findViewById(R.id.peccancy_detail_date);
		lv = (ListView) findViewById(R.id.peccancy_detail_lv);
		layout = (LinearLayout) findViewById(R.id.peccancy_detail_search);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShowRunning();
				new PeccancyDetailSearchScene().doScene(searchCallBack,
						vehicleid);
			}
		});

		new PeccancyDetailListScene().doScene(callBack, vehicleid);
		txtDate.setText("更新时间:" + bundle.getString("KEY_DATE"));
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			PeccancyDetailListResult items = (PeccancyDetailListResult) data;
			PeccancyDetailListAdapter adapter = new PeccancyDetailListAdapter(
					PeccancyDetailActivity.this, items.items);
			lv.setAdapter(adapter);
			txtCount.setText("共" + items.items.length + "条违章记录");
			HideProgress();
			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	OnSceneCallBack searchCallBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			new PeccancyDetailListScene().doScene(callBack, vehicleid);
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
