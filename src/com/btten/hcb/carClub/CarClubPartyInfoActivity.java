package com.btten.hcb.carClub;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.tools.InfoQuery;

public class CarClubPartyInfoActivity extends BaseActivity {

	private TextView txtTitle, txtContent, txtAddr, txtDate, txtProcess,
			txtOther, txtMenuContent, txtMenuFaq, txtMenuMember;
	// 导航栏底部滑动效果
	private View mImageView;
	private LinearLayout contentLinear, memberLinear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_club_detail);
		setCurrentTitle("活动详情");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {

		txtTitle = (TextView) findViewById(R.id.party_info_title);
		txtAddr = (TextView) findViewById(R.id.party_info_addr);
		txtProcess = (TextView) findViewById(R.id.party_info_process);
		txtDate = (TextView) findViewById(R.id.party_info_date);

		txtContent = (TextView) findViewById(R.id.car_club_detail_content_txt);
		contentLinear = (LinearLayout) findViewById(R.id.car_club_detail_content_linear);
		txtMenuContent = (TextView) findViewById(R.id.car_club_detail_menu_content);
		txtMenuFaq = (TextView) findViewById(R.id.car_club_detail_menu_faq);
		txtMenuMember = (TextView) findViewById(R.id.car_club_detail_menu_member);
		mImageView = findViewById(R.id.car_club_detail_menu_image);

		Bundle bundle = this.getIntent().getExtras();
		new CarClubPartyInfoScene().doScene(callBack,
				bundle.getString("KEY_ID"));
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			CarClubPartyInfoResult items = (CarClubPartyInfoResult) data;

			txtTitle.setText(items.item.title);
			txtAddr.setText(items.item.addr);
			txtDate.setText("开始时间" + items.item.startDate + " 共"
					+ items.item.totleDate + "天");
			txtOther.setText(InfoQuery.ToDBC(items.item.other));

			HideProgress();
			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int i = 0;
			switch (v.getId()) {
			case 0:
				i = 0;
				break;
			case R.id.car_club_detail_menu_content:
				memberLinear.setVisibility(View.GONE);
				contentLinear.setVisibility(View.VISIBLE);
				txtContent.setText("");
				i = 1;
				break;
			case R.id.car_club_detail_menu_faq:
				memberLinear.setVisibility(View.GONE);
				contentLinear.setVisibility(View.VISIBLE);
				txtContent.setText("");
				i = 1;
				break;
			case R.id.car_club_detail_menu_member:
				contentLinear.setVisibility(View.GONE);
				memberLinear.setVisibility(View.VISIBLE);
				i = 1;
				break;
			default:

				break;
			}
			if (i == 1) {
				AnimationSet _AnimationSet = new AnimationSet(true);
				TranslateAnimation translateAnimation;
				translateAnimation = new TranslateAnimation(Animation.ABSOLUTE,
						mImageView.getLeft(), Animation.ABSOLUTE, v.getLeft(),
						Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 0f);
				_AnimationSet.addAnimation(translateAnimation);
				_AnimationSet.setFillAfter(true);
				_AnimationSet.setDuration(100);
				mImageView.setLayoutParams(new LayoutParams(v.getWidth(), 6));
				mImageView.startAnimation(_AnimationSet);
			}

		}
	};
}
