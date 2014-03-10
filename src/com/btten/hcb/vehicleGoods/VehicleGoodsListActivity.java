package com.btten.hcb.vehicleGoods;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class VehicleGoodsListActivity extends BaseActivity {

	private ListView lv;
	private TextView txtAuthor, txtTitle, txtSynopsis;
	private ImageView imageView;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_info_activity);
		setCurrentTitle("车产品");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		txtAuthor = (TextView) findViewById(R.id.book_list_author);
		txtTitle = (TextView) findViewById(R.id.book_list_title);
		txtSynopsis = (TextView) findViewById(R.id.book_list_synopsis);
		imageView = (ImageView) findViewById(R.id.book_list_image);
		button = (Button) findViewById(R.id.book_list_button);

		lv = (ListView) findViewById(R.id.booklist_activity_lv);
		DoRequest();
	}

	private void DoRequest() {
		new VehicleGoodsListScene().doScene(callBack);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			VehicleGoodsListResult item = (VehicleGoodsListResult) data;
			VehicleGoodsListItem[] items = item.items;
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
