package com.btten.hcb.roadRescue;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;
import com.btten.vincenttools.CallTelephone;

public class RoadRescuePhoneActivity extends BaseActivity {
	private TextView txtViewJmsName;
	private Button btCall;
	private String phoneNum;
	private String jname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.road_rescue_phone_activity);

		initView();
	}

	private void initView() {
		txtViewJmsName = (TextView) findViewById(R.id.road_rescue_phone_name);
		btCall = (Button) findViewById(R.id.road_rescue_phone_button);
		btCall.setOnClickListener(clickListener);
		setCurrentTitle("道路救援");

		Bundle bundle = this.getIntent().getExtras();
		phoneNum = bundle.getString("KEY_PHONE");
		jname = bundle.getString("KEY_JNAME");
		txtViewJmsName.setText(jname);
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new CallTelephone(RoadRescuePhoneActivity.this, phoneNum, jname);
		}
	};
}
