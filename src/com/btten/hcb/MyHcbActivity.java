package com.btten.hcb;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.btten.base.BaseActivity;
import com.btten.hcb.cardActive.CardActiveActivity;
import com.btten.hcb.login.LoginActivity;
import com.btten.hcb.notice.FaqsActivity;
import com.btten.hcb.notice.NoticeInfoActivity;
import com.btten.hcb.register.RegistActivity;
import com.btten.hcbvip.R;

public class MyHcbActivity extends BaseActivity {

	int[] relativeID = { R.id.myhcb_relative_login,
			R.id.myhcb_relative_register, R.id.myhcb_relative_activation,
			R.id.myhcb_relative_account, R.id.myhcb_relative_shopping,
			R.id.myhcb_relative_baseinfo, R.id.myhcb_relative_exchange,
			R.id.myhcb_relative_points_record, R.id.myhcb_relative_faqs,
			R.id.myhcb_relative_notices, R.id.myhcb_relative_about_us };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhcb_activity);
		initView();
		setBackKeyListner(true);
	}

	public void initView() {
		setCurrentTitle("我的惠车宝");
		RelativeLayout tmpLayout = null;
		// 循环设置监听事件
		for (int i = 0; i < relativeID.length; i++) {
			tmpLayout = (RelativeLayout) findViewById(relativeID[i]);
			tmpLayout.setOnClickListener(clickListener);
		}
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = null;
			switch (v.getId()) {
			// 登陆
			case R.id.myhcb_relative_login:
				intent = new Intent(MyHcbActivity.this, LoginActivity.class);
				break;
			// 注册
			case R.id.myhcb_relative_register:
				intent = new Intent(MyHcbActivity.this, RegistActivity.class);
				break;
			// 激活
			case R.id.myhcb_relative_activation:
				intent = new Intent(MyHcbActivity.this,
						CardActiveActivity.class);
				break;
			// 我的账户
			case R.id.myhcb_relative_account:
				intent = new Intent(MyHcbActivity.this, LoginActivity.class);
				break;
			// 我的消费
			case R.id.myhcb_relative_shopping:
				intent = new Intent(MyHcbActivity.this, LoginActivity.class);
				break;
			// 我的信息
			case R.id.myhcb_relative_baseinfo:
				intent = new Intent(MyHcbActivity.this, LoginActivity.class);
				break;
			// 积分兑换
			case R.id.myhcb_relative_exchange:
				intent = new Intent(MyHcbActivity.this,
						NoticeInfoActivity.class);
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.POINTSEXCHANGE);
				break;
			// 积分记录
			case R.id.myhcb_relative_points_record:
				intent = new Intent(MyHcbActivity.this, LoginActivity.class);
				break;
			// 常见问题
			case R.id.myhcb_relative_faqs:
				intent = new Intent(MyHcbActivity.this, FaqsActivity.class);
				break;
			// 惠车宝公告
			case R.id.myhcb_relative_notices:
				intent = new Intent(MyHcbActivity.this, LoginActivity.class);
				break;
			// 关于我们
			case R.id.myhcb_relative_about_us:
				intent = new Intent(MyHcbActivity.this,
						NoticeInfoActivity.class);
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.ABOUTUS);
				break;
			}

			if (intent == null) {

			} else {
				startActivity(intent);
			}
		}
	};

	/**
	 * 清空/sdcard/calltaxi/Temp
	 */
	private void clearTemp() {
		String tempPath;
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			tempPath = Environment.getExternalStorageDirectory().getPath()
					.toString()
					+ getResources().getString(R.string.TEMP_PATH);
			clearFiles(new File(tempPath));
		}
	}

	private void clearFiles(File file) {
		if (file.isDirectory()) {
			File temp = null;
			File[] files = file.listFiles();
			int lenth = files.length;
			for (int i = 0; i < lenth; i++) {
				temp = files[i];
				clearFiles(temp);
			}
		} else if (file.isFile())
			file.delete();
	}
}
