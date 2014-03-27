package com.btten.hcb.homeActivity;

import java.util.ArrayList;
import java.util.List;
import com.btten.base.BaseActivity;
import com.btten.hcb.HcbAPP;
import com.btten.hcb.MyHcbActivity;
import com.btten.hcb.service.CallTaxiNotification;
import com.btten.hcb.accident.AccidentActivity;
import com.btten.hcb.account.VIPInfoManager;
import com.btten.hcb.buddhist.BuddhistInfoActivity;
import com.btten.hcb.carClub.CarClubListActivity;
import com.btten.hcb.insurance.InsuranceActivity;
import com.btten.hcb.map.LocationClientService;
import com.btten.hcb.peccancy.PeccancyListActivity;
import com.btten.hcb.roadRescue.RoadRescueActivity;
import com.btten.hcb.search.SearchActivity;
import com.btten.hcb.vehicleLife.VehicleLifeActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.uikit.ViewContainer;
import com.btten.vincenttools.CallTelephone;
import com.btten.vincenttools.NotifyTextView;
import com.umeng.update.UmengUpdateAgent;
import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;

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
	private static String TYPE = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homeview);
		initView();
	}

	// 初始化控件
	public void initView() {
		textView = (NotifyTextView) findViewById(R.id.homeview_textview);
		container = (ViewContainer) findViewById(R.id.homeview_container);
		// 设置动态长宽
		int screenWidth = VIPInfoManager.getInstance().getScreenWidth();
		LinearLayout layout = (LinearLayout) findViewById(R.id.homeview_linear);
		layout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth,
				screenWidth * 400 / 320));
		container.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth,
				screenWidth / 2));
		for (int i = 0; i < imageIntegers.length; i++) {
			ImageView imageview = (ImageView) findViewById(imageIntegers[i]);
			imageview.setOnClickListener(clickListener);
			imageViewArray.add(imageview);
		}
		new TitleNoticeScene().doScene(callBack, TYPE);
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
				// intent = new Intent(HomeActivity.this,
				// InsuranceActivity.class);
				Alert("正在开发中，敬请期待");
				return;
//				break;
			case R.id.homeview_wodehuichebao:
				intent = new Intent(HomeActivity.this, MyHcbActivity.class);
				break;
			case R.id.homeview_daolujiuyuan:
				intent = new Intent(HomeActivity.this, RoadRescueActivity.class);
				break;
			case R.id.homeview_jingcaihuodong:
				intent = new Intent(HomeActivity.this,
						CarClubListActivity.class);
				break;
			case R.id.homeview_shiguchuli:
				intent = new Intent(HomeActivity.this, AccidentActivity.class);
				break;
			case R.id.homeview_weizhangchaxun:
				intent = new Intent(HomeActivity.this,
						PeccancyListActivity.class);
				break;
			case R.id.homeview_rencheshenghuo:
				intent = new Intent(HomeActivity.this,
						VehicleLifeActivity.class);
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
			if (items.item.length > 0) {
				initNotifyText(items.item[0].title, items.item[0].id);
			}
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

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				if (titleid == null || titleid.isEmpty()) {

				} else {
					disPlayNoticeContent(HomeActivity.this, titleid);
				}

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
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
