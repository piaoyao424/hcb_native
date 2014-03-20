package com.btten.hcb.userInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.btten.base.BaseActivity;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.hcb.tools.areaInfo.ProvinceInfoActivity;
import com.btten.hcb.tools.areaInfo.ProvinceListScene;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class UserInfoActivity extends BaseActivity {

	private EditText et_email, et_username, et_addr, et_phone, et_consignee,
			et_province, et_city, et_area;
	private RadioGroup rdgrp;
	private TextView txt_usermobile;
	private RadioButton sexMale, sexFemale;
	private Button submit;
	private LinearLayout layout;
	private int sex;
	private String provinceid, cityid, areaid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userifo_activity);
		IsLogin();
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		setCurrentTitle("我的信息");
		txt_usermobile = (TextView) findViewById(R.id.userinfo_user_mobile);
		txt_usermobile.setText(VIPInfoManager.getInstance().getUserPhone());
		et_province = (EditText) findViewById(R.id.userinfo_province);
		et_city = (EditText) findViewById(R.id.userinfo_city);
		et_area = (EditText) findViewById(R.id.userinfo_area);
		layout = (LinearLayout) findViewById(R.id.userinfo_area_linear);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoActivity.this,
						ProvinceInfoActivity.class);
				startActivityForResult(intent, 1);
			}
		});

		et_email = (EditText) findViewById(R.id.userinfo_email);
		et_username = (EditText) findViewById(R.id.userinfo_username);
		et_addr = (EditText) findViewById(R.id.userinfo_addr);
		et_phone = (EditText) findViewById(R.id.userinfo_phone);
		et_consignee = (EditText) findViewById(R.id.userinfo_consignee);
		submit = (Button) findViewById(R.id.userinfo_button);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShowRunning();
				new UserInfoSubmitScene().doScene(
						new OnSceneCallBack() {

							@Override
							public void OnSuccess(Object data,
									NetSceneBase<?> netScene) {
								Alert(((UserInfoSubmitResult) data).info);
								HideProgress();
								onBackPressed();
							}

							@Override
							public void OnFailed(int status, String info,
									NetSceneBase<?> netScene) {
								HideProgress();
								ErrorAlert(status, info);
							}
						}, provinceid, cityid, areaid, String.valueOf(sex),
						et_addr.getText().toString().trim(), et_phone.getText()
								.toString().trim(), et_consignee.getText()
								.toString().trim(), et_username.getText()
								.toString().trim(),
						et_email.getText().toString().trim());
			}
		});

		sexMale = (RadioButton) findViewById(R.id.userinfo_gerder_male);
		sexFemale = (RadioButton) findViewById(R.id.userinfo_gerder_female);
		rdgrp = (RadioGroup) findViewById(R.id.userinfo_gerder_group);
		// 默认男性
		sex = 0;
		rdgrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 根据用户勾选的单选按钮来动态改变sex的值
				sex = checkedId == R.id.userinfo_gerder_male ? 0 : 1;
			}
		});

		new UserInfoScene().doScene(callBack);
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			UserInfoResult items = (UserInfoResult) data;
			et_email.setText(items.item.email);
			et_username.setText(items.item.username);
			et_addr.setText(items.item.address);
			et_phone.setText(items.item.phone);
			et_consignee.setText(items.item.consignee);
			if (items.item.gerder == 1) {
				sexFemale.setChecked(true);
				sexMale.setChecked(false);
			}
			et_province.setText(items.item.province);
			provinceid = items.item.provinceid;
			et_city.setText(items.item.city);
			cityid = items.item.cityid;
			et_area.setText(items.item.area);
			areaid = items.item.areaid;

			HideProgress();
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { // resultCode为回传的标记
		case 1:
			Bundle b = data.getExtras(); // data为B中回传的Intent
			String id = b.getString("KEY_ID");
			String name = b.getString("KEY_NAME");

			String[] temp = id.split("@");
			provinceid = temp[0];
			cityid = temp[1];
			areaid = temp[2];
			temp = name.split("@");
			et_province.setText(temp[0]);
			et_city.setText(temp[1]);
			et_area.setText(temp[2]);
			break;
		default:
			break;
		}
	}
}
