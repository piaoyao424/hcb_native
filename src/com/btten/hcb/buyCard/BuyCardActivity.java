package com.btten.hcb.buyCard;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.hcb.tools.areaInfo.ProvinceListScene;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class BuyCardActivity extends BaseActivity {

	private TextView txt_submit, txt_add, txt_sub, txt_num, txt_money;
	private ArrayAdapter<String> typeAdapter = null;
	private Spinner typeSpinner = null;
	private RadioButton rd_bank, rd_zhifubao;
	private List<String> type;
	private Double item_value, item_price;
	private int item_num;
	private BuyCardItem[] item;
	private String itemName, itemID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_card_activity);
		setCurrentTitle("购买洗车卡");
		setBackKeyListner(true);
		IsLogin();
		initView();
	}

	public void initView() {
		typeSpinner = (Spinner) findViewById(R.id.buy_card_type);

		txt_submit = (TextView) findViewById(R.id.buy_card_submit);
		txt_submit.setOnClickListener(listener);

		txt_add = (TextView) findViewById(R.id.buy_card_add);
		txt_add.setOnClickListener(listener);

		txt_sub = (TextView) findViewById(R.id.buy_card_sub);
		txt_sub.setOnClickListener(listener);

		txt_money = (TextView) findViewById(R.id.buy_card_money);
		txt_num = (TextView) findViewById(R.id.buy_card_num);
		item_num = 1;

		rd_bank = (RadioButton) findViewById(R.id.buy_card_radio_bank);
		rd_zhifubao = (RadioButton) findViewById(R.id.buy_card_radio_zhifubao);

		new BuyCardItemScene().doscene(itemCallBack);
	}

	OnSceneCallBack itemCallBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			BuyCardItemResult items = (BuyCardItemResult) data;
			type = new ArrayList<String>();
			item = items.item;
			for (int i = 0; i < item.length; i++) {
				type.add(item[i].name);
			}
			setSpinner();
			HideProgress();
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.buy_card_submit:
				if (rd_bank.isChecked()) {
					Alert("暂时不支持网银支付！");
					return;
				}
				if (rd_zhifubao.isChecked()) {

					Intent intent = new Intent(BuyCardActivity.this,
							BuyCardWebActivity.class);
					intent.putExtra("KEY_ID", itemID);
					intent.putExtra("KEY_NAME", itemName);
					intent.putExtra("KEY_PRICE", item_price);
					intent.putExtra("KEY_VALUE", item_value);
					intent.putExtra("KEY_NUM", item_num);
					startActivityForResult(intent, 1);
				}
				break;
			// 赋值数量
			case R.id.buy_card_add:
				int num = Integer.valueOf(txt_num.getText().toString());
				num += 1;
				txt_num.setText(String.valueOf(num));
				item_num = num;
				calculateTotleMoney();
				break;
			case R.id.buy_card_sub:
				num = Integer.valueOf(txt_num.getText().toString());
				if (num >= 2) {
					num -= 1;
				}
				txt_num.setText(String.valueOf(num));
				item_num = num;
				calculateTotleMoney();
				break;
			default:
				break;
			}
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
		}
	};

	private void setSpinner() {
		// 初始化商品
		typeAdapter = new ArrayAdapter<String>(this, R.layout.spinnerlayout);

		for (int i = 0; i < type.size(); i++) {
			typeAdapter.add(type.get(i));
		}

		typeSpinner.setAdapter(typeAdapter);
		typeSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		typeSpinner.setSelection(0, true);
	}

	class SpinnerSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String Name = parent.getItemAtPosition(position).toString();
			// 赋值商品信息
			for (int i = 0; i < item.length; i++) {
				if (item[i].name.equals(Name)) {
					itemName = Name;
					itemID = item[i].id;
					item_price = item[i].price;
					item_value = item[i].value;
					calculateTotleMoney();
					break;
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			Alert("请选择合适的充值类型！");
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			switch (resultCode) { // resultCode为回传的标记
			case 0:
				Alert("支付失败，请重新提交！");
				break;
			case 1:
				finish();
				break;
			default:
				break;
			}
		}
	}

	private void calculateTotleMoney() {
		txt_money.setText(String.valueOf(item_num * item_price));
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
