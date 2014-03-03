package com.btten.hcb.userInfo;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.hcb.map.BMapActivity;
import com.btten.hcb.map.JmsGps;
import com.btten.hcb.serviceEvaluation.ServiceEvaluationActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.vincenttools.CallTelephone;

public class UserInfoActivity extends BaseActivity {

	private TextView txt_jname, txt_scope, txt_commentNum, txt_address,
			txt_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jmsinfo_activity);

		initView();
	}

	public void initView() {
		setCurrentTitle("网点详情");
		txt_jname = (TextView) findViewById(R.id.jmsinfo_jname);
		txt_scope = (TextView) findViewById(R.id.jmsinfo_scope);
		txt_commentNum = (TextView) findViewById(R.id.jmsinfo_numstar);
		txt_address = (TextView) findViewById(R.id.jmsinfo_address);
		txt_phone = (TextView) findViewById(R.id.jmsinfo_phone);

		setBackKeyListner(true);
		new JmsInfoScene().doScene(callBack, jmsInfo.id);
	}

	OnClickListener clickListener = new OnClickListener() {
		Intent intent;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.jmsinfo_relative_address:
				break;
			case R.id.jmsinfo_relative_phone:
				break;
			case R.id.jmsinfo_activity_star_layout:
				break;

			default:
				break;
			}
		}
	};

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

}
