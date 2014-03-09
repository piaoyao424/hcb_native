package com.btten.hcb.tools.areaInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.btten.base.BaseActivity;
import com.btten.hcb.tools.areaInfo.ProvinceListAdapter.ViewHolder;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class ProvinceActivity extends BaseActivity {
	ListView lv;
	int[] relativeId = { R.id.province_relative_bj, R.id.province_relative_sh,
			R.id.province_relative_tj, R.id.province_relative_cq };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.province_activity);
		setCurrentTitle("省份信息");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		for (int i = 0; i < relativeId.length; i++) {
			RelativeLayout layout = (RelativeLayout) findViewById(relativeId[i]);
			layout.setOnClickListener(listener);
		}
		lv = (ListView) findViewById(R.id.province_activity_lv);
		lv.setOnItemClickListener(clickListener);
		new ProvinceListScene().doScene(callBack);
		ShowRunning();
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String areaId = null;
			switch (v.getId()) {
			case R.id.province_relative_bj:
				areaId = "1";
				break;
			case R.id.province_relative_sh:
				areaId = "9";
				break;
			case R.id.province_relative_tj:
				areaId = "2";
				break;
			case R.id.province_relative_cq:
				areaId = "22";
				break;
			default:
				break;
			}

			if (areaId == null) {

			} else {
				BacktoActivity(areaId);
			}
		}
	};

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			ProvinceListResult item = (ProvinceListResult) data;
			ProvinceListAdapter adapter = new ProvinceListAdapter(
					ProvinceActivity.this, item.items);
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
		Intent intent = ProvinceActivity.this.getIntent();
		intent.putExtra("KEY_AREAID", areaId);
		setResult(1, intent);
		finish();
	}

	@Override
	public void initDate() {

	}

}
