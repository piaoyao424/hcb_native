package com.btten.hcb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.btten.base.BaseActivity;
import com.btten.hcb.Service.CallTaxiNotification;
import com.btten.hcb.account.MyAccountActivity;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.hcb.cardActive.CardActiveActivity;
import com.btten.hcb.changePassword.ChangePasswdActivity;
import com.btten.hcb.homeActivity.HomeActivity;
import com.btten.hcb.login.LoginActivity;
import com.btten.hcb.map.LocationClientService;
import com.btten.hcb.notice.FaqsActivity;
import com.btten.hcb.notice.NoticeInfoActivity;
import com.btten.hcb.publicNotice.PublicNoticeListActivity;
import com.btten.hcb.register.RegistActivity;
import com.btten.hcbvip.R;

public class MyHcbActivity extends BaseActivity {

	int[] relativeID = { R.id.myhcb_relative_login,
			R.id.myhcb_relative_register, R.id.myhcb_relative_changepw,
			R.id.myhcb_relative_activation, R.id.myhcb_relative_account,
			R.id.myhcb_relative_shopping, R.id.myhcb_relative_baseinfo,
			R.id.myhcb_relative_exchange, R.id.myhcb_relative_points_record,
			R.id.myhcb_relative_faqs, R.id.myhcb_relative_notices,
			R.id.myhcb_relative_about_us };
	List<RelativeLayout> list_rela = new ArrayList<RelativeLayout>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhcb_activity);
		initView();
		setBackKeyListner(true);
	}

	public void initView() {
		setCurrentTitle("我的惠车宝");

		Button tButton = (Button) findViewById(R.id.myhcb_button);
		tButton.setOnClickListener(clickListener);

		RelativeLayout tmpLayout = null;
		// 循环设置监听事件
		for (int i = 0; i < relativeID.length; i++) {
			tmpLayout = (RelativeLayout) findViewById(relativeID[i]);
			tmpLayout.setOnClickListener(clickListener);
			list_rela.add(tmpLayout);
		}

		if (VIPInfoManager.getInstance().IsLogin()) {
			// 登陆
			list_rela.get(0).setVisibility(View.GONE);
			// 注册
			list_rela.get(1).setVisibility(View.GONE);
		} else {
			// 修改密码
			list_rela.get(3).setVisibility(View.GONE);
			tButton.setVisibility(View.GONE);
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
			// 修改密码
			case R.id.myhcb_relative_changepw:
				intent = new Intent(MyHcbActivity.this,
						ChangePasswdActivity.class);
				break;
			// 我的账户
			case R.id.myhcb_relative_account:
				intent = new Intent(MyHcbActivity.this, MyAccountActivity.class);
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
				intent = new Intent(MyHcbActivity.this, PublicNoticeListActivity.class);
				break;
			// 关于我们
			case R.id.myhcb_relative_about_us:
				intent = new Intent(MyHcbActivity.this,
						NoticeInfoActivity.class);
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.ABOUTUS);
				break;
			// 注销登陆
			case R.id.myhcb_button:
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						// 清除缓存，
						ClearOtherActivity();
						clearTemp();
						// 注销到登录页面
						VIPInfoManager.getInstance().Logout();

						// 关闭位置
						if (LocationClientService.getInstance().isStarted())
							LocationClientService.getInstance().stop();
						LocationClientService.getInstance().destroy();

						CallTaxiNotification.getInstance().LogoutApp();
						startActivity((new Intent(MyHcbActivity.this,
								HomeActivity.class)));
						ClearAllActivity();
					}
				}, 200);
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
