package com.btten.hcb.carClub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CarClubPartyInfoActivity extends BaseActivity {

	private TextView txtTitle, txtContent, txtInitiator, txtstartDate,
			txttotleDate, txtParticipantNum, txtOther, txtMenuContent,
			txtMenuFaq, txtMenuMember, txtEvalu;
	// 导航栏底部滑动效果
	private View mImageView;
	private ImageView adImage;
	private LinearLayout contentLinear, memberLinear;
	private String content, other;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_club_detail);
		setCurrentTitle("活动详情");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {

		txtTitle = (TextView) findViewById(R.id.car_club_detail_title);
		txtInitiator = (TextView) findViewById(R.id.car_club_detail_initiator);
		txtstartDate = (TextView) findViewById(R.id.car_club_detail_startdate);
		txttotleDate = (TextView) findViewById(R.id.car_club_detail_totledate);
		txtParticipantNum = (TextView) findViewById(R.id.car_club_detail_participantNum);
		adImage = (ImageView) findViewById(R.id.car_club_detail_ad_image);
		txtEvalu = (TextView) findViewById(R.id.car_club_detail_menu_evaluate);

		txtContent = (TextView) findViewById(R.id.car_club_detail_content_txt);
		txtContent.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
		contentLinear = (LinearLayout) findViewById(R.id.car_club_detail_content_linear);
		memberLinear = (LinearLayout) findViewById(R.id.car_club_detail_member_linear);
		
		txtMenuContent = (TextView) findViewById(R.id.car_club_detail_menu_content);
		txtMenuContent.setOnClickListener(clickListener);
		txtMenuFaq = (TextView) findViewById(R.id.car_club_detail_menu_faq);
		txtMenuFaq.setOnClickListener(clickListener);
		txtMenuMember = (TextView) findViewById(R.id.car_club_detail_menu_member);
		txtMenuMember.setOnClickListener(clickListener);
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
			txtTitle.setText(items.item.title + items.item.addr);
			txtInitiator.setText(items.item.initiator);
			txtstartDate.setText(items.item.startDate + "出发");
			txttotleDate.setText("共" + items.item.totleDate + "天");
			txtParticipantNum.setText(items.item.participantNum + "人参加");
			ImageLoader.getInstance().displayImage(items.item.image, adImage);
			txtEvalu.setText("评论（" + items.item.evalu + "）");

			content = items.item.content;
			other = items.item.other;
			txtContent.setText(Html.fromHtml(content));
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
				handler.sendEmptyMessage(0);
				i = 1;
				break;
			case R.id.car_club_detail_menu_faq:
				handler.sendEmptyMessage(1);
				i = 1;
				break;
			case R.id.car_club_detail_menu_member:
				handler.sendEmptyMessage(2);
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

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				if (memberLinear.getVisibility() == View.VISIBLE) {
					memberLinear.setVisibility(View.GONE);
				}
				contentLinear.setVisibility(View.VISIBLE);
				txtContent.setText(Html.fromHtml(content));
				break;
			case 1:
				if (memberLinear.getVisibility() == View.VISIBLE) {
					memberLinear.setVisibility(View.GONE);
				}
				contentLinear.setVisibility(View.VISIBLE);
				txtContent.setText(Html.fromHtml(other));
				break;
			case 2:
				if (contentLinear.getVisibility() == View.VISIBLE) {
					contentLinear.setVisibility(View.GONE);
				}
				memberLinear.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}

		};
	};
}
