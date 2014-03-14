package com.btten.hcb;

import com.btten.base.BaseActivity;
import com.btten.hcb.SplashScreen;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.hcb.carClub.CarClubListActivity;
import com.btten.hcb.homeActivity.HomeActivity;
import com.btten.hcb.login.LoginActivity;
import com.btten.hcb.login.LoginScene;
import com.btten.hcb.map.LocationClientService;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

public class SplashScreen extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		// 自动报错
		MobclickAgent.onError(this);
		// 更新配置
		MobclickAgent.updateOnlineConfig(this);
		UmengUpdateAgent.setUpdateOnlyWifi(false);

		initView();
	}

	Intent intent = null;

	private void DelayLoad() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// if (IsFirstStart()) {
				// intent = new Intent(SplashScreen.this, WelcomActivity.class);
				// startActivity(intent);
				// finish();
				// overridePendingTransition(R.anim.in_right_left,
				// R.anim.out_right_left);
				// } else {
				if (VIPInfoManager.getInstance().IsLogin()) {
					SharedPreferences settings = getSharedPreferences("cfg",
							MODE_PRIVATE);
					String nameStr = settings.getString("nameStr", "");
					String pwdStr = settings.getString("pwdStr", "");

					(new LoginScene()).doScene(new OnSceneCallBack() {

						@Override
						public void OnSuccess(Object data,
								NetSceneBase<?> netScene) {
						}

						@Override
						public void OnFailed(int status, String info,
								NetSceneBase<?> netScene) {
						}
					}, nameStr, pwdStr);

				}

				intent = new Intent(SplashScreen.this, HomeActivity.class);
				// intent = new Intent(SplashScreen.this,
				// CarClubListActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.in_right_left,
						R.anim.out_right_left);
				// }
			}
		}, 2000);
	}

	private boolean IsFirstStart() {
		boolean isfirststart = false;

		SharedPreferences settings = getSharedPreferences("calltaxicfg",
				MODE_PRIVATE);
		isfirststart = settings.getBoolean("IsFirstStart", true);

		return isfirststart;
	}

	public void initView() {
		// TODO Auto-generated method stub
		// 打开GPS
		LocationClientService.getInstance().start();

		DelayLoad();
		// 获取屏幕宽高
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		// int height = metric.heightPixels; // 屏幕高度（像素）
		// float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
		// int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）

		VIPInfoManager.getInstance().setScreenWidth(width);
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
