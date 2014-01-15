package com.btten.base;

import java.util.ArrayList;
import java.util.List;
import com.btten.Jms.R;
import com.btten.hcb.Gonggao.NoticeInfoActivity;
import com.btten.hcb.Notice.TitleNoticeItem;
import com.btten.hcb.Notice.TitleNoticeItems;
import com.btten.hcb.Notice.TitleNoticeScene;
import com.btten.hcb.Service.CallTaxiNotification;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.umeng.update.UmengUpdateAgent;
import com.vincentTools.ImageView_Win8;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends BaseActivity {

	private ViewFlipper viewFlipper = null;
	private Integer[] imageIntegers = { R.id.homeview_aiCheMeiRong,
			R.id.homeview_qiCheYongPin, R.id.homeview_cheLiangBaoXian,
			R.id.homeview_aiCheQingJie, R.id.homeview_woDeHuiCheBao,
			R.id.homeview_jingCaiHuoDong, R.id.homeview_aiCheBaoYang,
			R.id.homeview_daoLuJiuYuan, R.id.homeview_telephone,
			R.id.homeview_webHome };
	private List<ImageView_Win8> imageViewArray = new ArrayList<ImageView_Win8>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		setContentView(R.layout.homeview);
		init();
	}

	// 初始化控件
	public void init() {
		viewFlipper = (ViewFlipper) findViewById(R.id.home_viewflipper);

		TitleNoticeScene titleNoticeScene = new TitleNoticeScene();
		titleNoticeScene.doScene(callBack);

		for (int i = 0; i < imageIntegers.length; i++) {
			ImageView_Win8 imageview = (ImageView_Win8) findViewById(imageIntegers[i]);
			imageview.setOnClickListener(clickListener);
			imageViewArray.add(imageview);
		}
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.homeview_aiCheMeiRong:

				break;
			case R.id.homeview_qiCheYongPin:

				break;
			case R.id.homeview_cheLiangBaoXian:

				break;
			case R.id.homeview_aiCheQingJie:

				break;
			case R.id.homeview_woDeHuiCheBao:

				break;
			case R.id.homeview_jingCaiHuoDong:

				break;
			case R.id.homeview_aiCheBaoYang:

				break;
			case R.id.homeview_daoLuJiuYuan:

				break;
			case R.id.homeview_telephone:

				break;
			case R.id.homeview_webHome:

				break;
			default:
				break;
			}
		}
	};

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			TitleNoticeItems items = (TitleNoticeItems) data;
			initViewFlipper(items.item);
			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			ErrorAlert(status, info);
		}
	};

	private void initViewFlipper(TitleNoticeItem[] text) {
		viewFlipper.removeAllViews();
		for (int i = 0; i < text.length; i++) {

			TextView textView = new TextView(MainActivity.this);
			textView.setText(((TitleNoticeItem) text[i]).title);
			textView.setOnClickListener(new NoticeTitleOnClickListener(
					MainActivity.this, ((TitleNoticeItem) text[i]).id));
			LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			viewFlipper.addView(textView, lp);
		}
		viewFlipper.startFlipping();
	}

	/**
	 * 公告title监听
	 */
	class NoticeTitleOnClickListener implements OnClickListener {
		private Context context = null;
		private String titleid = null;

		public NoticeTitleOnClickListener(Context context, String whichText) {
			this.context = context;
			this.titleid = whichText;
		}

		public void onClick(View v) {
			disPlayNoticeContent(context, titleid);
		}

	}

	/**
	 * 显示notice的具体内容
	 * 
	 * @param context
	 * @param titleid
	 */
	public void disPlayNoticeContent(Context context, String titleid) {
		Intent intent = new Intent(context, NoticeInfoActivity.class);
		intent.putExtra("KEY_GGID", titleid);
		startActivity(intent);
	}

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
									dialog.dismiss();
								}
							}).show();
		}
		return super.onOptionsItemSelected(item);
	}

}
