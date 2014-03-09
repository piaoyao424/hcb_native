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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.province_info_activity);
		setCurrentTitle("城市信息");
		setBackKeyListner(true);
		initView();
		Intent intentA = new Intent(this, ProvinceActivity.class);
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
			new ProvinceInfoScene()
					.doScene(callBack, b.getString("KEY_AREAID"));
			ShowRunning();
			break;
		default:
			break;
		}
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			ProvinceInfoResult item = (ProvinceInfoResult) data;
			ProvinceInfoAdapter adapter = new ProvinceInfoAdapter(
					ProvinceInfoActivity.this, item.items);
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
			BacktoActivity(((ViewHolder) view.getTag()).id);
		}
	};

	// 回到前一个activity
	private void BacktoActivity(String areaId) {
		Intent intent = ProvinceInfoActivity.this.getIntent();
		intent.putExtra("KEY_AREAID", areaId);
		setResult(1, intent);
		finish();
	}

	@Override
	public void initDate() {

	}

}