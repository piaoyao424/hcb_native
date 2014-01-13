package com.btten.base;

import com.btten.Jms.R;
import com.btten.calltaxi.Service.CallTaxiNotification;
import com.btten.calltaxi.Service.core.JmsMapManager;
import com.btten.ui.view.BaseView;
import com.btten.uikit.ViewContainer;
import com.umeng.update.UmengUpdateAgent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends BaseActivity {
	private ViewContainer vContainer = null ;
	public JmsMapManager mManager = null ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		setContentView(R.layout.main_activity);
		// 登录成功
		init();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getCurrentBaseView().OnViewShow();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		getCurrentBaseView().OnViewHide();
		super.onPause();
	}

	public void init() {
		// 屏幕中间
		vContainer = (ViewContainer) findViewById(R.id.home_center_container);
	}

	public void GoToView(int index) {
		vContainer.FlipToView(index);
	}

	public BaseView getCurrentBaseView() {
		return (BaseView) vContainer.getCurrentView().getTag();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		getCurrentBaseView().Backward();
	}

	public OnActivityResultListener baseListener;

	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case BasicInfoView.TAKE_SMALL_PICTURE:
		case BasicInfoView.CHOOSE_BIG_PICTURE:
		case BasicInfoView.CROP_SMALL_PICTURE:
		case BasicInfoView.CHOOSE_SMALL_PICTURE:
			if (baseListener != null)
				baseListener.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}*/

	/**
	 * 菜单项
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem exit = menu.add(0, 0, 0, "退出");		
		exit.setIcon(R.drawable.home_tab_exit_icon);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getGroupId() == 0 && item.getItemId() == 0) {
			new AlertDialog.Builder(this)
					.setMessage("退出惠车宝？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									CallTaxiNotification.getInstance()
											.ExitApp();
									ClearAllActivity();
									System.exit(0);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							}).show();
		} else if (item.getGroupId() == 0 && item.getItemId() == 1) {
			Intent intent = new Intent();
//			intent = new Intent(MainActivity.this, SendGiftActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
