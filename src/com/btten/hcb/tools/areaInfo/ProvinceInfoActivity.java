package com.btten.hcb.tools.areaInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.btten.base.BaseActivity;
import com.btten.hcb.tools.areaInfo.ProvinceListAdapter.ViewHolder;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class ProvinceInfoActivity extends BaseActivity {
	private ListView lv;
	private String areaName;
	private String areaid;
	private int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.province_info_activity);
		setCurrentTitle("城市信息");
		setBackKeyListner(true);
		initView();
		Intent intentA = new Intent(this, ProvinceListActivity.class);
		startActivityForResult(intentA, 1);
	}

	public void initView() {
		lv = (ListView) findViewById(R.id.province_info_activity_lv);
		lv.setOnItemClickListener(clickListener);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { // resultCode为回传的标记
		case 1:
			Bundle b = data.getExtras(); // data为B中回传的Intent
			areaid = b.getString("KEY_ID");
			areaName = b.getString("KEY_NAME");
			new ProvinceListScene().doScene(callBack, areaid);
			ShowRunning();
			break;
		default:
			break;
		}
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			ProvinceListResult item = (ProvinceListResult) data;
			ProvinceInfoAdapter adapter = new ProvinceInfoAdapter(
					ProvinceInfoActivity.this, item.items, type);
			type++;
			lv.setAdapter(adapter);
			HideProgress();
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			areaid = areaid + "@" + ((ViewHolder) view.getTag()).id;
			areaName = areaName + "@" + ((ViewHolder) view.getTag()).name;

			if (type == 1) {
				new ProvinceListScene().doScene(callBack,
						((ViewHolder) view.getTag()).id);
			} else if (type == 2) {
				BacktoActivity();
			}

		}
	};

	// 回到前一个activity
	private void BacktoActivity() {
		Intent intent = ProvinceInfoActivity.this.getIntent();
		intent.putExtra("KEY_AREAID", areaid);
		intent.putExtra("KEY_Name", areaName);
		setResult(1, intent);
		finish();
	}

	@Override
	public void initDate() {

	}

}
