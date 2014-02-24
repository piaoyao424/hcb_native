package com.btten.hcb.cardActive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.btten.base.BaseActivity;
import com.btten.hcb.Service.CallTaxiNotification;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.hcb.homeActivity.HomeActivity;
import com.btten.hcbvip.R;
import com.btten.msgcenter.MsgCenter;
import com.btten.msgcenter.MsgConst;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class CardActiveActivity extends BaseActivity {
	EditText phoneNum, pwd, cardNum, verifyCode_et;
	Button regist_btn, verifyCode_btn;
	String strPhoneNum, strPwd, strCardNum, strVerifyCode;
	boolean accept_terms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_active_activity);
		setCurrentTitle("卡激活");
		setBackKeyListner(true);
		init();
	}

	private void init() {
		phoneNum = (EditText) findViewById(R.id.cardactive_phone);
		pwd = (EditText) findViewById(R.id.cardactive_pwd);
		cardNum = (EditText) findViewById(R.id.cardactive_num);
		regist_btn = (Button) findViewById(R.id.cardactive_button_submit);
		regist_btn.setOnClickListener(listener);

		verifyCode_et = (EditText) findViewById(R.id.cardactive_edittext_verifycode);
		verifyCode_btn = (Button) findViewById(R.id.cardactive_button_verifycode);
		verifyCode_btn.setOnClickListener(requestCodeListener);
	}

	ProgressDialog progress;
	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (judge()) {

				if (progress == null) {
					progress = ProgressDialog.show(CardActiveActivity.this,
							"正在激活……", "请稍候……", true, false);
				}

				regist_btn.setClickable(false);

				CardActiveVerifyScene verifyScene = new CardActiveVerifyScene();
				verifyScene.doScene(new OnSceneCallBack() {

					@Override
					public void OnSuccess(Object data, NetSceneBase<?> netScene) {
						CardActiveScene scene = new CardActiveScene();
						scene.doScence(callBack, strPhoneNum, strPwd);
					}

					@Override
					public void OnFailed(int status, String info,
							NetSceneBase<?> netScene) {
						if (progress != null) {
							progress.dismiss();
							progress = null;
						}
						regist_btn.setClickable(true);
						ErrorAlert(status, info);
					}
				}, strPhoneNum, strVerifyCode);

			}

		}
	};

	private boolean judge() {
		strPhoneNum = phoneNum.getText().toString().trim();
		strPwd = pwd.getText().toString().trim();
		strCardNum = cardNum.getText().toString().trim();
		strVerifyCode = verifyCode_et.getText().toString().trim();

		if (strPhoneNum.equals("")) {
			Alert("手机号码不能为空！");
			return false;
		}
		if (!isPhone(strPhoneNum)) {
			Alert("无效手机号");
			return false;
		}
		if (strPwd.equals("")) {
			Alert("密码不能为空！");
			return false;
		}
		if (!isGoodPWD(strPwd)) {
			// Alert("密码长度必须为6~11位,且不能为非法字符！");
			return false;
		}
		if (strVerifyCode.equals("")) {
			Alert("请填写验证码！");
			return false;
		}

		return true;
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

	boolean isCounting = false;
	OnClickListener requestCodeListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			verifyCode_btn.setClickable(false);
			strPhoneNum = phoneNum.getText().toString().trim();
			if (strPhoneNum.equals("")) {
				Alert("请填写手机号!");
				verifyCode_btn.setClickable(true);
				return;
			}
			if (!isPhone(strPhoneNum)) {
				Alert("无效手机号");
				verifyCode_btn.setClickable(true);
				return;
			}

			CardActiveVerifyRequestScene requestScene = new CardActiveVerifyRequestScene();
			requestScene.doScene(requestCodeCallBack, strPhoneNum);

			if (count == null)
				count = new Thread(new Runnable() {

					@Override
					public void run() {
						int i = 60;

						isCounting = true;
						while (i >= 0 && isCounting) {
							Message msg = handler.obtainMessage();
							msg.arg1 = i;
							msg.what = ONCHANG;

							handler.sendMessage(msg);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							--i;
						}

						Message msg = handler.obtainMessage();
						msg.what = ONEND;
						handler.sendMessage(msg);
					}
				});

			if (count != null && !count.isAlive())
				count.start();
		}
	};

	private static final int ONCHANG = 1001;
	private static final int ONEND = 1010;

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {

			if (msg.what == ONCHANG)
				verifyCode_btn.setText(String.valueOf(msg.arg1));
			else if (msg.what == ONEND) {
				verifyCode_btn.setClickable(true);
				verifyCode_btn.setText("获取验证码");

				count = null;
			}
		};

	};

	Thread count = null;

	OnSceneCallBack requestCodeCallBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			Alert("验证码请求已发送，请稍后查收短信息！");
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			if (count != null && count.isAlive()) {
				isCounting = false;
			}

			ErrorAlert(status, info);
		}
	};

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			if (progress != null) {
				progress.dismiss();
				progress = null;
			}
			regist_btn.setClickable(true);
			ErrorAlert(status, info);
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			if (progress != null) {
				progress.dismiss();
				progress = null;
			}

			Toast.makeText(CardActiveActivity.this, "注册成功", 1000).show();
			CardActiveResultItems items = (CardActiveResultItems) data;
			CardActiveResultItem item = items.item;
			VIPInfoManager.getInstance().SetInfo(item.username, item.userid,
					"", "");
			MsgCenter.getInstance().PostMsg(MsgConst.REGIST_SUCCESS, this);

			SharedPreferences setting = getSharedPreferences("calltaxicfg",
					MODE_PRIVATE);
			setting.edit().putBoolean("rememberPWD", false)
					.putString("nameStr", strPhoneNum).putString("pwdStr", "")
					.commit();

			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			// 得到InputMethodManager的实例
			if (imm.isActive()) {
				// 如果开启
				imm.hideSoftInputFromWindow(phoneNum.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				// 关闭软键盘
			}

			CallTaxiNotification.getInstance().RegistApp();
			// TODO 成功后跳转到HOME
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					startActivity((new Intent(CardActiveActivity.this,
							HomeActivity.class)));
					ClearOtherActivity();
				}
			}, 200);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && resultCode == 1001) {
			accept_terms = data.getBooleanExtra("accept_terms_state", false);
		}
	}

	@Override
	protected void onPause() {

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启 关闭软键盘
			imm.hideSoftInputFromWindow(phoneNum.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}

		if (count != null && count.isAlive())
			count.interrupt();

		super.onPause();
	}
}
