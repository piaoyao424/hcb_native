package com.btten.hcb.homeActivity;

import com.btten.base.BaseActivity;
import com.btten.hcb.HcbAPP;
import com.btten.hcb.service.CallTaxiNotification;
import com.btten.hcb.carClub.CarClubListActivity;
import com.btten.hcb.map.LocationClientService;
import com.btten.hcbvip.R;
import com.btten.vincenttools.CallTelephone;
import com.umeng.update.UmengUpdateAgent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HomeFirstActivity extends BaseActivity {

	LinearLayout linear_web, linear_phone;
	RelativeLayout cheyouhuiLayout, qichefuwuLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		UmengUpdateAgent.update(this);
		initView();
	}

	// 初始化控件
	public void initView() {
		linear_phone = (LinearLayout) findViewById(R.id.home_linear_phone);
		linear_phone.setOnClickListener(clickListener);

		linear_web = (LinearLayout) findViewById(R.id.home_linear_web);
		linear_web.setOnClickListener(clickListener);

		cheyouhuiLayout = (RelativeLayout) findViewById(R.id.home_relative_cheyouhui);
		cheyouhuiLayout.setOnClickListener(clickListener);

		qichefuwuLayout = (RelativeLayout) findViewById(R.id.home_relative_qichefuwu);
		qichefuwuLayout.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 默认爱车美容
			Intent intent = null;
			switch (v.getId()) {
			case R.id.home_linear_phone:
				new CallTelephone(HomeFirstActivity.this, "4006602020", "惠车宝")
						.call();
				return;
			case R.id.home_linear_web:
				intent = new Intent("android.intent.action.VIEW",
						Uri.parse(getString(R.string.homepage)));
				break;
			case R.id.home_relative_cheyouhui:
				intent = new Intent(HomeFirstActivity.this,
						CarClubListActivity.class);
				break;
			case R.id.home_relative_qichefuwu:
				intent = new Intent(HomeFirstActivity.this, HomeActivity.class);
				break;
			default:
				break;
			}
			startActivity(intent);
		};
	};

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("提示").setMessage("退出惠车宝？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						CallTaxiNotification.getInstance().ExitApp();
						LocationClientService.getInstance().destroy();
						HcbAPP.getInstance().exit();

					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}