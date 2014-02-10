package com.btten.hcb;

import com.btten.base.BaseActivity;
import com.btten.hcb.SplashScreen;
import com.btten.hcb.Service.LocationClientService;
import com.btten.hcb.account.VIPAccountManager;
import com.btten.hcb.login.LoginActivity;
import com.btten.hcb.login.LoginScene;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.umeng.analytics.MobclickAgent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		// 自动报错
		MobclickAgent.onError(this);
		// 更新配置
		MobclickAgent.updateOnlineConfig(this);
		// 打开GPS
		LocationClientService.getInstance().start();

		DelayLoad();
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
				if (VIPAccountManager.getInstance().IsLogin()) {
					SharedPreferences settings = getSharedPreferences("jmscfg",
							MODE_PRIVATE);
					String nameStr = settings.getString("nameStr", "");
					String pwdStr = settings.getString("pwdStr", "");

					(new LoginScene()).doScene(new OnSceneCallBack() {

						@Override
						public void OnSuccess(Object data,
								NetSceneBase<?> netScene) {
							intent = new Intent(SplashScreen.this,
									MainActivity.class);
							startActivity(intent);
							finish();
							overridePendingTransition(R.anim.in_right_left,
									R.anim.out_right_left);
						}

						@Override
						public void OnFailed(int status, String info,
								NetSceneBase<?> netScene) {
							intent = new Intent(SplashScreen.this,
									LoginActivity.class);
							startActivity(intent);
							finish();
							overridePendingTransition(R.anim.in_right_left,
									R.anim.out_right_left);
						}
					}, nameStr, pwdStr);

				} else {
					// intent = new Intent(SplashScreen.this,
					// LoginActivity.class);
					intent = new Intent(SplashScreen.this, MainActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.in_right_left,
							R.anim.out_right_left);
				}
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
}
