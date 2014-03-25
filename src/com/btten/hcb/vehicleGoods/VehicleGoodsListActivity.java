package com.btten.hcb.vehicleGoods;

import android.os.Bundle;
import android.widget.ListView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class VehicleGoodsListActivity extends BaseActivity {
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_goods_list_activity);
		setCurrentTitle("车产品");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		lv = (ListView) findViewById(R.id.vehicleGoods_activity_lv);
		new VehicleGoodsListScene().doScene(callBack);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			VehicleGoodsListResult item = (VehicleGoodsListResult) data;
			VehicleGoodsListAdapter adapter = new VehicleGoodsListAdapter(
					VehicleGoodsListActivity.this, item.items);
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
		// TODO Auto-generated method stub

	}

}
