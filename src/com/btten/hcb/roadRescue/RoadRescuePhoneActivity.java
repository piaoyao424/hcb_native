package com.btten.hcb.roadRescue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;

public class RoadRescuePhoneActivity extends BaseActivity {
	private TextView txtViewJmsName;
	private Button btCall;
	private String phoneNum;

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
		txtViewJmsName.setText(bundle.getString("KEY_JNAME"));
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new Handler().post(new Runnable() {
				@Override
				public void run() {
					new AlertDialog.Builder(RoadRescuePhoneActivity.this)
							.setTitle("提示")
							.setMessage("拨打电话给 " + phoneNum)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											RoadRescuePhoneActivity.this.startActivity(new Intent(
													Intent.ACTION_CALL, Uri
															.parse("tel:"
																	+ phoneNum)));
											dialog.dismiss();
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									}).show();
				}
			});
		}
	};
}
