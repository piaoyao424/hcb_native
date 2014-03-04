package com.btten.hcb.userInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class UserInfoActivity extends BaseActivity {

	private EditText et_province, et_city, et_area, et_model, et_carNum,
			et_date, et_email, et_username, et_addr, et_phone, et_consignee;
	private RadioGroup rdgrp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userifo_activity);
		setBackKeyListner(true);
		initView();
		IsLogin();
	}

	public void initView() {
		setCurrentTitle("我的信息");
		et_model = (EditText) findViewById(R.id.userinfo_model);
		et_carNum = (EditText) findViewById(R.id.userinfo_carnum);
		et_date = (EditText) findViewById(R.id.userinfo_date);
		et_email = (EditText) findViewById(R.id.userinfo_email);
		et_username = (EditText) findViewById(R.id.userinfo_username);
		et_addr = (EditText) findViewById(R.id.userinfo_addr);
		et_phone = (EditText) findViewById(R.id.userinfo_phone);
		et_consignee = (EditText) findViewById(R.id.userinfo_consignee);
		rdgrp = (RadioGroup) findViewById(R.id.userinfo_gerder_group);
		rdgrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 根据用户勾选的单选按钮来动态改变tip字符串的值
				String tip = checkedId == R.id.userinfo_gerder_male ? "您的性别是男人"
						: "您的性别是女人";

			}
		});

		// new JmsInfoScene().doScene(callBack, jmsInfo.id);
	}

	OnClickListener clickListener = new OnClickListener() {
		Intent intent;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.jmsinfo_relative_address:
				break;
			case R.id.jmsinfo_relative_phone:
				break;
			case R.id.jmsinfo_activity_star_layout:
				break;

			default:
				break;
			}
		}
	};

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
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

}
