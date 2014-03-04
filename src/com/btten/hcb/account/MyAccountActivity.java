package com.btten.hcb.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.base.BaseActivity;
import com.btten.hcb.cardActive.CardActiveActivity;
import com.btten.hcb.notice.NoticeInfoActivity;
import com.btten.hcb.pointRecord.PointRecordsActivity;
import com.btten.hcb.rechargeRecord.RechargeRecordsActivity;
import com.btten.hcb.shoppingRecord.ShoppingRecordsActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class MyAccountActivity extends BaseActivity {
	private Button btn_recharge, btn_rechangeRecord, btn_shoppingrecord,
			btn_exchange, btn_pointRecord, btn_exchangeRole;
	private TextView txt_name, txt_hb, txt_point;
	private ImageView levelView;
	private int[] imageSrc = { R.drawable.viplevel01, R.drawable.viplevel02,
			R.drawable.viplevel03 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_activity);
		setBackKeyListner(true);
		initView();
		initDate();
	}

	public void initView() {
		setCurrentTitle("我的账户");

		btn_recharge = (Button) findViewById(R.id.myaccount_activity_button_charge);
		btn_recharge.setOnClickListener(listener);
		btn_rechangeRecord = (Button) findViewById(R.id.myaccount_activity_button_charge_record);
		btn_rechangeRecord.setOnClickListener(listener);
		btn_shoppingrecord = (Button) findViewById(R.id.myaccount_activity_button_shopping_record);
		btn_shoppingrecord.setOnClickListener(listener);
		btn_exchange = (Button) findViewById(R.id.myaccount_activity_button_exchange);
		btn_exchange.setOnClickListener(listener);
		btn_pointRecord = (Button) findViewById(R.id.myaccount_activity_button_point_record);
		btn_pointRecord.setOnClickListener(listener);
		btn_exchangeRole = (Button) findViewById(R.id.myaccount_activity_button_exchange_role);
		btn_exchangeRole.setOnClickListener(listener);

		txt_name = (TextView) findViewById(R.id.myaccount_activity_name);
		txt_hb = (TextView) findViewById(R.id.myaccount_activity_hb);
		txt_point = (TextView) findViewById(R.id.myaccount_activity_point);
		levelView = (ImageView) findViewById(R.id.myaccount_activity_level);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = null;
			switch (v.getId()) {

			case R.id.myaccount_activity_button_charge:
				intent = new Intent(MyAccountActivity.this,
						CardActiveActivity.class);
				break;
			case R.id.myaccount_activity_button_charge_record:
				intent = new Intent(MyAccountActivity.this,
						RechargeRecordsActivity.class);
				break;
			case R.id.myaccount_activity_button_shopping_record:
				intent = new Intent(MyAccountActivity.this,
						ShoppingRecordsActivity.class);
				break;
			case R.id.myaccount_activity_button_exchange:
				intent = new Intent(MyAccountActivity.this,
						NoticeInfoActivity.class);
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.POINTSEXCHANGE);
				break;
			case R.id.myaccount_activity_button_point_record:
				intent = new Intent(MyAccountActivity.this,
						PointRecordsActivity.class);
				break;
			case R.id.myaccount_activity_button_exchange_role:
				intent = new Intent(MyAccountActivity.this,
						NoticeInfoActivity.class);
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.INTEGRALRULES);
				break;
			default:
				break;
			}
			if (intent == null) {

			} else {
				startActivity(intent);
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
			MyAccountResult item = (MyAccountResult) data;
			txt_name.setText(item.item.name);
			txt_hb.setText(item.item.hbValue);
			txt_point.setText(item.item.pointValue);

			if (item.item.level.equals("2")) {
				levelView.setImageResource(imageSrc[1]);
			} else if (item.item.level.equals("3")) {
				levelView.setImageResource(imageSrc[2]);
			} else {

			}
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub
		if (IsLogin()) {
			new MyAccountScene().doscene(callBack);
		}
	}
}
