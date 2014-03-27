package com.btten.hcb.shoppingRecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class ShoppingDetailActivity extends BaseActivity {
	private Button btn_pingjia;
	private ListView listView;
	private TextView tvTitle, tvSaleNum, tvMoney, tvPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_detail);
		setCurrentTitle("消费详情");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		tvTitle = (TextView) findViewById(R.id.shopping_detail_title);
		tvSaleNum = (TextView) findViewById(R.id.shopping_detail_salenum);
		listView = (ListView) findViewById(R.id.shopping_detail_lv);

		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View mFooterView = layoutInflater.inflate(
				R.layout.shopping_detail_footer, null);
		btn_pingjia = (Button) mFooterView
				.findViewById(R.id.shopping_detail_footer_button);
		btn_pingjia.setOnClickListener(listener);
		tvMoney = (TextView) mFooterView
				.findViewById(R.id.shopping_detail_footer_money);
		tvPoint = (TextView) mFooterView
				.findViewById(R.id.shopping_detail_footer_point);
		listView.addFooterView(mFooterView);
		String id = getIntent().getExtras().getString("KEY_ID");
		new ShoppingDetaiScene().doscene(callBack, id);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ShowRunning();
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
			ShoppingDetailResult items = (ShoppingDetailResult) data;
			ShoppingDetailAdapter adapter = new ShoppingDetailAdapter(
					ShoppingDetailActivity.this);
			adapter.setItems(items.items);
			listView.setAdapter(adapter);

			tvTitle.setText(String.valueOf(items.title));
			tvSaleNum.setText(items.saleNum);
			tvMoney.setText(String.valueOf(items.points));
			tvPoint.setText(String.valueOf(items.money));

			if (items.items.length == 0)
				ShoppingDetailActivity.this.Alert("没有数据！");
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
