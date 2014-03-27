package com.btten.hcb.carClub;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.btten.hcb.carClub.slideMenu.SlideMenuItem;
import com.btten.hcb.carClub.slideMenu.SlideMenuResult;
import com.btten.hcb.carClub.slideMenu.SlideMenuScene;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class CarClubListActivity extends BaseActivity {
	private ListView lv;
	private String slideMenuID;
	private int slideX = 0, processType = 1;
	private CarClubListItem[] itemArray;
	private RadioButton radioGoing, radioStop;
	private CarClubListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_club_activity);
		setCurrentTitle("车友会");
		setBackKeyListner(true);
		initView();
		new SlideMenuScene().doScene(slideMenuCallBack);
		ShowRunning();

	}

	public void initView() {
		lv = (ListView) findViewById(R.id.car_club_lv);
		radioGoing = (RadioButton) findViewById(R.id.car_club_radiogoing);
		radioStop = (RadioButton) findViewById(R.id.car_club_radiostop);
		RadioGroup grp = (RadioGroup) findViewById(R.id.car_club_radiogroup);
		grp.setOnCheckedChangeListener(listener);

	}

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.car_club_radiogoing:
				processType = 1;
				break;
			case R.id.car_club_radiostop:
				processType = 0;
				break;
			default:
				break;
			}
			showList();
		}
	};

	OnSceneCallBack slideMenuCallBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			SlideMenuResult item = (SlideMenuResult) data;
			setSlideMenu(item.items);
			handler.sendEmptyMessage(0);
			HideProgress();
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			Alert("获取活动类别失败！");
			finish();

		}
	};

	OnSceneCallBack partyCallBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			CarClubListResult item = (CarClubListResult) data;
			itemArray = item.items;
			handler.sendEmptyMessage(1);
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

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 目录加载完毕
			case 0:
				ShowRunning();
				new CarClubListScene().doScene(partyCallBack);
				break;
			case 1:
				showList();
				break;
			default:
				break;
			}
		};
	};

	// 根据类型显示列表
	public void showList() {
		List<CarClubListItem> tmpArray = new ArrayList<CarClubListItem>();

		for (int i = 0; i < itemArray.length; i++) {
			if (itemArray[i].partyType == Integer.valueOf(slideMenuID)) {
				if (itemArray[i].processType == processType) {
					tmpArray.add(itemArray[i]);
				}
			}
		}

		if (tmpArray.size() > 1) {
			adapter = new CarClubListAdapter(CarClubListActivity.this,
					tmpArray.toArray(new CarClubListItem[1]));
			lv.setAdapter(adapter);
		} else {
			Alert("没有该分类的活动！");
			adapter = new CarClubListAdapter(CarClubListActivity.this, null);
			lv.setAdapter(adapter);
		}

	}

	// 初始化活动目录
	private void setSlideMenu(final SlideMenuItem[] items) {
		// 底部滑动效果
		final View mImageView;
		final HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.car_club_horizontalScrollView);
		// 包含TextView的LinearLayout
		LinearLayout menuLinerLayout = (LinearLayout) findViewById(R.id.car_club_linear);
		mImageView = findViewById(R.id.car_club_bottom_image);

		slideMenuID = items[0].id;
		// 添加TextView控件
		for (int i = 0; i < items.length; i++) {
			// 从XML中加载布局
			View view = LayoutInflater.from(this).inflate(
					R.layout.car_club_slide_text, null);
			final TextView tvMenu = (TextView) view
					.findViewById(R.id.car_club_slide_text);
			tvMenu.setText(items[i].title);
			view.setTag(items[i].id);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					slideMenuID = (String) v.getTag();
					AnimationSet _AnimationSet = new AnimationSet(true);
					TranslateAnimation translateAnimation;
					translateAnimation = new TranslateAnimation(
							Animation.ABSOLUTE, slideX, Animation.ABSOLUTE, v
									.getLeft(), Animation.ABSOLUTE, 0f,
							Animation.ABSOLUTE, 0f);
					slideX = v.getLeft();
					_AnimationSet.addAnimation(translateAnimation);
					_AnimationSet.setFillAfter(true);
					_AnimationSet.setDuration(100);
					mImageView.startAnimation(_AnimationSet);
					hScrollView.scrollBy(1, 0);
					handler.sendEmptyMessage(1);
				}
			});
			menuLinerLayout.addView(view);
		}
	}
}
