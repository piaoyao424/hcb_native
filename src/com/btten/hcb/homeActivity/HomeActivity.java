package com.btten.hcb.homeActivity;

import java.util.ArrayList;
import java.util.List;
import com.btten.base.BaseActivity;
import com.btten.hcb.HcbAPP;
import com.btten.hcb.MyHcbActivity;
import com.btten.hcb.Service.CallTaxiNotification;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.hcb.buddhist.BuddhistInfoActivity;
import com.btten.hcb.map.LocationClientService;
import com.btten.hcb.search.SearchActivity;
import com.btten.hcb.vehicleLife.VehicleLifeActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.uikit.ViewContainer;
import com.btten.vincenttools.CallTelephone;
import com.btten.vincenttools.NotifyTextView;
import com.umeng.update.UmengUpdateAgent;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeActivity extends BaseActivity {

	private Integer[] imageIntegers = { R.id.homeview_xichemeirong,
			R.id.homeview_jingcaihuodong, R.id.homeview_rencheshenghuo,
			R.id.homeview_qicheyongping, R.id.homeview_wodehuichebao,
			R.id.homeview_shiguchuli, R.id.homeview_weizhangchaxun,
			R.id.homeview_cheliangbaoxian, R.id.homeview_daolujiuyuan,
			R.id.homeview_shouye, R.id.homeview_telephone };
	private ViewContainer container = null;
	private List<ImageView> imageViewArray = new ArrayList<ImageView>();
	private NotifyTextView textView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homeview);
		UmengUpdateAgent.update(this);

		initView();
	}

	// 初始化控件
	public void initView() {
		textView = (NotifyTextView) findViewById(R.id.homeview_textview);
		container = (ViewContainer) findViewById(R.id.homeview_container);
		// 设置动态长宽
		int screenWidth = VIPInfoManager.getInstance().getScreenWidth();
		LinearLayout layout = (LinearLayout) findViewById(R.id.homeview_linear);
		container.setLayoutParams(new LinearLayout.LayoutParams(screenWidth,
				screenWidth / 2));
		layout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth,
				screenWidth * 390 / 320));

		for (int i = 0; i < imageIntegers.length; i++) {
			ImageView imageview = (ImageView) findViewById(imageIntegers[i]);
			imageview.setOnClickListener(clickListener);
			imageViewArray.add(imageview);
		}
		new TitleNoticeScene().doScene(callBack);
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// 默认爱车美容
			String cid = "0";
			Intent intent = null;
			switch (v.getId()) {
			case R.id.homeview_telephone:
				new CallTelephone(HomeActivity.this, "4006602020", "惠车宝")
						.call();
				break;
			case R.id.homeview_shouye:
				intent = new Intent("android.intent.action.VIEW",
						Uri.parse(getString(R.string.homepage)));
				break;
			case R.id.homeview_cheliangbaoxian:
				break;
			case R.id.homeview_wodehuichebao:
				intent = new Intent(HomeActivity.this, MyHcbActivity.class);
				break;
			case R.id.homeview_daolujiuyuan:
				break;
			case R.id.homeview_jingcaihuodong:
				break;
			case R.id.homeview_shiguchuli:
				break;
			case R.id.homeview_weizhangchaxun:
				break;
			case R.id.homeview_rencheshenghuo:
				intent = new Intent(HomeActivity.this, VehicleLifeActivity.class);
				break;
			case R.id.homeview_xichemeirong:
				cid = "20002";
				break;
			case R.id.homeview_qicheyongping:
				cid = "20005";
				break;
			default:
				cid = "20002";
				break;
			}
			if (cid.equals("0")) {
				if (intent != null) {
					startActivity(intent);
				}
			} else {
				intent = new Intent(HomeActivity.this, SearchActivity.class);
				intent.putExtra("KEY_MENUID", cid);
				startActivity(intent);
			}
		}
	};
	// 每日一禅回调接口
	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			TitleNoticeResult items = (TitleNoticeResult) data;
			initNotifyText(items.item[0].title, items.item[0].id);
			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			ErrorAlert(status, info);
		}
	};

	/**
	 * 初始化notify数据
	 * 
	 * @param title
	 * @param titleid
	 */
	private void initNotifyText(final String title, final String titleid) {

		textView.setText(title);

		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				disPlayNoticeContent(HomeActivity.this, titleid);
			}
		});
	}

	/**
	 * 显示notice的具体内容
	 * 
	 * @param context
	 * @param titleid
	 */
	public void disPlayNoticeContent(Context context, String titleid) {
		Intent intent = new Intent(context, BuddhistInfoActivity.class);
		intent.putExtra("KEY_ID", titleid);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("提示").setMessage("退出惠车宝？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						CallTaxiNotification.getInstance().ExitApp();
						LocationClientService.getInstance().destroy();
						HcbAPP.getInstance().exit();
						 
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub
		
	}
}
