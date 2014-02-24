package com.btten.hcb.notice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;
import com.btten.vincenttools.CallTelephone;

public class NoticeInfoActivity extends BaseActivity {
	public static final String KEY_NOTICEID = "KEY_NOTICEID";
	public static final int POINTSEXCHANGE = 0;
	public static final int ABOUTUS = 1;
	public static final int CARDACTIVE = 2;
	public static final int HUIBIUSAGE = 3;
	public static final int PRIVILEGE = 4;
	public static final int BANKCARD = 5;
	public static final int INTEGRALRULES = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_base_activity);
		initView();
		setBackKeyListner(true);
	}

	private void initView() {
		setCurrentTitle("常见问题");
		Bundle bundle = getIntent().getExtras();
		int NoticeID = bundle.getInt(KEY_NOTICEID);
		if (NoticeID > -1) {
			// 动态加载不同的布局文件
			LinearLayout lin = null;
			LinearLayout layout = null;
			View view = null;
			String titleString = null;
			String contentString = null;

			lin = (LinearLayout) findViewById(R.id.notice_base_linear);
			LayoutInflater inflater = LayoutInflater.from(this);

			switch (NoticeID) {
			// 布局文件跟一般的常见问题不一样，单独加载
			case POINTSEXCHANGE:
				setCurrentTitle("积分兑换");
				view = inflater.inflate(R.layout.notice_points_exchange_layout,
						null);

				layout = (LinearLayout) view
						.findViewById(R.id.notice_pointsexchange_layout);
				ImageView imageView = (ImageView) view
						.findViewById(R.id.notice_pointsexchange_gohome);
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setAction("android.intent.action.VIEW");
						Uri content_url = Uri
								.parse(getString(R.string.homepage));
						intent.setData(content_url);
						startActivity(intent);
					}
				});

				TextView textView = (TextView) view
						.findViewById(R.id.notice_pointsecchange_phone);
				textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						new CallTelephone(NoticeInfoActivity.this,
								"4006602020", "惠车宝").call();
					}
				});
				break;
			case INTEGRALRULES:
				view = inflater.inflate(
						R.layout.notice_integral_rules_activity, null);
				layout = (LinearLayout) view
						.findViewById(R.id.notice_integral_rules_layout);
				break;
			case ABOUTUS:
				titleString = "关于我们";
				contentString = getString(R.string.aboutUs);
				break;
			case CARDACTIVE:
				titleString = "卡片激活";
				contentString = getString(R.string.cardActive);
				break;
			case HUIBIUSAGE:
				titleString = "惠币使用";
				contentString = getString(R.string.huibiUsage);
				break;
			case PRIVILEGE:
				titleString = "会员特权";
				contentString = getString(R.string.privilege);
				break;
			case BANKCARD:
				titleString = "刷银行卡";
				contentString = getString(R.string.bankCard);
				break;
			default:
				break;
			}

			if (titleString == null || contentString == null) {

			} else {
				// 填充数据
				view = inflater.inflate(R.layout.notice_base_layout, null);
				layout = (LinearLayout) view
						.findViewById(R.id.notice_base_layout);
				TextView titleTextView = (TextView) view
						.findViewById(R.id.notice_base_title);
				TextView contentTextView = (TextView) view
						.findViewById(R.id.notice_base_content);
				titleTextView.setText(titleString);
				contentTextView.setText(contentString);
			}

			if (lin == null || layout == null) {

			} else {
				// 加载布局
				lin.removeAllViews();
				lin.addView(layout);
			}
		}

	}
}
