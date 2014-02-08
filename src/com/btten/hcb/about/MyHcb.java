package com.btten.hcb.about;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.btten.base.BaseActivity;
import com.btten.hcb.login.LoginActivity;
import com.btten.hcbvip.R;

public class MyHcb extends BaseActivity {

	int[] relativeID = { R.id.myhcb_relative_login,
			R.id.myhcb_relative_register, R.id.myhcb_relative_activation,
			R.id.myhcb_relative_account, R.id.myhcb_relative_shopping,
			R.id.myhcb_relative_baseinfo, R.id.myhcb_relative_exchange,
			R.id.myhcb_relative_points_record, R.id.myhcb_relative_faqs,
			R.id.myhcb_relative_notices, R.id.myhcb_relative_about_us };

	public MyHcb(Activity context) {
		super();
		initView();
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

			switch (v.getId()) {
			// 登陆
			case R.id.myhcb_relative_login:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 注册
			case R.id.myhcb_relative_register:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 激活
			case R.id.myhcb_relative_activation:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 我的账户
			case R.id.myhcb_relative_account:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 我的消费
			case R.id.myhcb_relative_shopping:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 我的信息
			case R.id.myhcb_relative_baseinfo:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 积分兑换
			case R.id.myhcb_relative_exchange:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 积分记录
			case R.id.myhcb_relative_points_record:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 常见问题
			case R.id.myhcb_relative_faqs:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 惠车宝公告
			case R.id.myhcb_relative_notices:
				startActivity(new Intent(MyHcb.this, LoginActivity.class));
				break;
			// 关于我们
			case R.id.myhcb_relative_about_us:
				startActivity(new Intent(MyHcb.this, GuideActivity.class));
				break;
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
