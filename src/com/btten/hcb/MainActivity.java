package com.btten.hcb;

import java.util.ArrayList;
import java.util.List;

import com.btten.base.BaseActivity;
import com.btten.hcb.gonggao.NoticeInfoActivity;
import com.btten.hcb.notice.TitleNoticeItem;
import com.btten.hcb.notice.TitleNoticeItems;
import com.btten.hcb.notice.TitleNoticeScene;
import com.btten.hcb.search.SearchActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.vincentTools.CallTelephone;
import com.umeng.update.UmengUpdateAgent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
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
	private List<ImageView> imageViewArray = new ArrayList<ImageView>();

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
			ImageView imageview = (ImageView) findViewById(imageIntegers[i]);
			imageview.setOnClickListener(clickListener);
			imageViewArray.add(imageview);
		}
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 默认爱车美容
			String cid = "0";
			switch (v.getId()) {
			case R.id.homeview_telephone:
				new CallTelephone(MainActivity.this, "4006602020").call();
				break;
			case R.id.homeview_webHome:
				Intent viewIntent = new Intent("android.intent.action.VIEW",
						Uri.parse("http://www.huichebo.com"));
				startActivity(viewIntent);
				break;
			case R.id.homeview_cheLiangBaoXian:
				break;
			case R.id.homeview_woDeHuiCheBao:
				break;
			case R.id.homeview_daoLuJiuYuan:
				break;
			case R.id.homeview_jingCaiHuoDong:
				break;
			case R.id.homeview_aiCheMeiRong:
				cid = "20002";
				break;
			case R.id.homeview_qiCheYongPin:
				cid = "20005";
				break;
			case R.id.homeview_aiCheQingJie:
				cid = "20001";
				break;
			case R.id.homeview_aiCheBaoYang:
				cid = "20003";
				break;
			default:
				cid = "20002";
				break;
			}
			if (cid.equals("0")) {

			} else {
				Intent intent = new Intent(MainActivity.this,
						SearchActivity.class);
				intent.putExtra("KEY_MENUID", cid);
				startActivity(intent);
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
}
