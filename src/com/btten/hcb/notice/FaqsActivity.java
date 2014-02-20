package com.btten.hcb.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.btten.base.BaseActivity;
import com.btten.hcb.notice.NoticeInfoActivity;
import com.btten.hcbvip.R;

public class FaqsActivity extends BaseActivity {

	int[] relativeID = { R.id.faqs_relative_cardactive,
			R.id.faqs_relative_huibiusage, R.id.faqs_relative_bankcard,
			R.id.faqs_relative_jmslist, R.id.faqs_relative_privilege,
			R.id.faqs_relative_integral_rules, R.id.faqs_relative_car_insurance };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faqs_activity);
		initView();
		setBackKeyListner(true);
	}

	public void initView() {
		setCurrentTitle("常见问题");
		RelativeLayout tmpLayout = null;
		// 循环设置监听事件
		for (int i = 0; i < relativeID.length; i++) {
			tmpLayout = (RelativeLayout) findViewById(relativeID[i]);
			tmpLayout.setOnClickListener(clickListener);
		}
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = null;
			intent = new Intent(FaqsActivity.this, NoticeInfoActivity.class);

			switch (v.getId()) {
			// 卡片激活
			case R.id.faqs_relative_cardactive:
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.CARDACTIVE);
				break;
			// 惠币使用
			case R.id.faqs_relative_huibiusage:
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.HUIBIUSAGE);
				break;
			// 刷银行卡
			case R.id.faqs_relative_bankcard:
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.BANKCARD);
				break;
			// 合作网点
			case R.id.faqs_relative_jmslist:
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID, -1);
				break;
			// 会员特权
			case R.id.faqs_relative_privilege:
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.PRIVILEGE);
				break;
			// 积分规则
			case R.id.faqs_relative_integral_rules:
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID,
						NoticeInfoActivity.INTEGRALRULES);
				break;
			// 车辆保险
			case R.id.faqs_relative_car_insurance:
				intent.putExtra(NoticeInfoActivity.KEY_NOTICEID, -1);
				break;
			}
			startActivity(intent);
		}
	};
}
