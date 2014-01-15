package com.btten.hcb.Login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.btten.Jms.R;
import com.btten.account.VIPAccountManager;
import com.btten.base.BaseActivity;
import com.btten.base.ClassTools;
import com.btten.base.MainActivity;
import com.btten.hcb.Service.CallTaxiNotification;
import com.btten.hcb.Service.LocationClientService;
import com.btten.msgcenter.MsgCenter;
import com.btten.msgcenter.MsgConst;
import com.btten.network.NetConst;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class LoginActivity extends BaseActivity {

	Button bt_loginButton = null;
	EditText editText_name = null;
	EditText editText_password = null;
	// 加盟商配置信息
	SharedPreferences jmsSetting = null;
	String nameStr = null;
	String pwdStr = null;
	boolean boxIsChecked = false;

	// 是否勾选

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		setCurrentTitle("加盟商登录");

		init();
		initPersonalInfo();
	}

	private void init() {
		bt_loginButton = (Button) findViewById(R.id.login_button);
		bt_loginButton.setOnClickListener(listener);

		editText_name = (EditText) findViewById(R.id.login_name);
		editText_password = (EditText) findViewById(R.id.login_pwd);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_button:
				doLogin();
				break;
			}
		}
	};

	private void initPersonalInfo() {
		jmsSetting = getSharedPreferences("jmscfg", MODE_PRIVATE);
		boxIsChecked = jmsSetting.getBoolean("rememberPWD", false);

		nameStr = jmsSetting.getString("nameStr", "");
		if (boxIsChecked) {
			pwdStr = jmsSetting.getString("pwdStr", "");
		} else {
			pwdStr = "";
		}
	}

	private void savePersonalInfo() {

		jmsSetting.edit().putBoolean("rememberPWD", true)
				.putString("nameStr", nameStr).putString("pwdStr", pwdStr)
				.commit();

	}

	LoginScene loginScene = null;
	ProgressDialog progress = null;

	private void doLogin() {
		nameStr = editText_name.getText().toString().trim();
		pwdStr = editText_password.getText().toString().trim();

		if (nameStr.length() <= 0) {
			Toast.makeText(this, "请输入加盟商手机号", Toast.LENGTH_SHORT).show();
			return;
		} else if (!isPhone(nameStr)) {
			Toast.makeText(this, "无效的手机号", Toast.LENGTH_SHORT).show();
			return;
		} else if (pwdStr.length() <= 0) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		} else if (!isGoodPWD(pwdStr)) {
			Toast.makeText(this, "无效密码", Toast.LENGTH_SHORT).show();
			editText_password.setText("");
			return;
		}

		ShowProgress("登录中", "请稍候……");

		loginScene = new LoginScene();
		loginScene.doJmsScene(callBack, nameStr, pwdStr);

	}

	private boolean isPhone(String name) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	private boolean isGoodPWD(String pwd) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9_]{6,18}$");
		Matcher m = p.matcher(pwd);
		return m.matches();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);

			if (status != NetConst.NetConnectError) {
				// 清空密码
				editText_password.setText("");
			}
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();
			savePersonalInfo();

			LoginResultItems items = (LoginResultItems) data;
			LoginResultItem item = items.item;
			VIPAccountManager.getInstance().SetJmsInfo(nameStr,
					item.username, item.userid);

			MsgCenter.getInstance().PostMsg(MsgConst.LOGIN_SUCCESS, this);

			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			// 得到InputMethodManager的实例
			if (imm.isActive()) {
				// 如果开启
				imm.hideSoftInputFromWindow(editText_password.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
			}

			ClassTools.getInstance().isRequesting = false;
			// TODO 返回成功后跳转到HOME页面
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					startActivity((new Intent(LoginActivity.this,
							MainActivity.class)));
					finish();
					BaseActivity.ClearOtherActivity();
				}
			}, 200);
		}
	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage("退出惠车宝？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						CallTaxiNotification.getInstance().ExitApp();
						if (LocationClientService.getInstance().getMapManager() != null)
							LocationClientService.getInstance().getMapManager()
									.destroy();
						ClearAllActivity();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();
	}
}
