package com.btten.hcb.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.msgcenter.MsgCenter;
import com.btten.msgcenter.MsgConst;
import com.btten.network.NetConst;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.tools.algorithm.MD5;

public class LoginActivity extends BaseActivity {

	Button bt_loginButton = null;
	EditText editText_name = null;
	EditText editText_password = null;
	// 配置信息
	SharedPreferences Setting = null;
	String nameStr = null;
	String pwdStr = null;
	boolean boxIsChecked = false;

	// 是否勾选

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		setCurrentTitle("会员登录");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {
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
		Setting = getSharedPreferences("cfg", MODE_PRIVATE);
		boxIsChecked = Setting.getBoolean("rememberPWD", false);

		nameStr = Setting.getString("nameStr", "");
		if (boxIsChecked) {
			pwdStr = Setting.getString("pwdStr", "");
		} else {
			pwdStr = "";
		}
	}

	private void savePersonalInfo() {

		Setting.edit().putBoolean("rememberPWD", true)
				.putString("nameStr", nameStr).putString("pwdStr", pwdStr)
				.commit();

	}

	LoginScene loginScene = null;
	ProgressDialog progress = null;

	private void doLogin() {
		nameStr = editText_name.getText().toString().trim();
		pwdStr = editText_password.getText().toString().trim();

		if (nameStr.length() <= 0) {
			Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
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

		pwdStr = MD5.getMD5Str(pwdStr);
		ShowProgress("登录中", "请稍候……");

		loginScene = new LoginScene();
		loginScene.doScene(callBack, nameStr, pwdStr);

	}

	private boolean isPhone(String name) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
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

			LoginResult items = (LoginResult) data;
			LoginItem item = items.item;
			VIPInfoManager.getInstance().SetInfo(nameStr, item.userid, nameStr,
					null);

			MsgCenter.getInstance().PostMsg(MsgConst.LOGIN_SUCCESS, this);

			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			// 得到InputMethodManager的实例
			if (imm.isActive()) {
				// 如果开启
				imm.hideSoftInputFromWindow(editText_password.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
			}

			// TODO 返回成功后跳转到HOME页面
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					setResult(RESULT_OK);
					finish();
				}
			}, 200);
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub
		initPersonalInfo();
	}
}
