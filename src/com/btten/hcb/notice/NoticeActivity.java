package com.btten.hcb.notice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;

public class NoticeActivity extends BaseActivity {
	private TextView txtViewJmsName;
	private Button btCall;
	private String phoneNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_base_activity);

		initView();
	}

	private void initView() {
		setCurrentTitle("积分兑换");

		Bundle bundle = this.getIntent().getExtras();
		phoneNum = bundle.getString("KEY_PHONE");
		txtViewJmsName.setText(bundle.getString("KEY_JNAME"));

		final LayoutInflater inflater = LayoutInflater.from(this);
		final LinearLayout lin = (LinearLayout) findViewById(R.id.notice_base_linear);

		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.notice_points_exchange_activity, null).findViewById(
				R.id.notice_base_layout);

		lin.removeAllViews();
		lin.addView(layout);
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new Handler().post(new Runnable() {
				@Override
				public void run() {
					new AlertDialog.Builder(NoticeActivity.this)
							.setTitle("提示")
							.setMessage("拨打电话给 " + phoneNum)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											NoticeActivity.this
													.startActivity(new Intent(
															Intent.ACTION_CALL,
															Uri.parse("tel:"
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
