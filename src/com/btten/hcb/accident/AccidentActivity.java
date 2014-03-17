package com.btten.hcb.accident;

import com.btten.base.BaseActivity;
import com.btten.hcb.commission.CommissionActivity;
import com.btten.hcb.insurance.InsuranceCallActivity;
import com.btten.hcbvip.R;
import com.btten.vincenttools.CallTelephone;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AccidentActivity extends BaseActivity {

	private Integer[] linearId = { R.id.accident_camera_linear,
			R.id.accident_judge_linear, R.id.accident_call_linear,
			R.id.accident_commission_linear };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accident_activity);
		initView();
	}

	// 初始化控件
	public void initView() {
		for (int i = 0; i < linearId.length; i++) {
			LinearLayout layout = (LinearLayout) findViewById(linearId[i]);
			layout.setOnClickListener(clickListener);
		}
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = null;
			switch (v.getId()) {
			case R.id.accident_camera_linear:
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				break;
			case R.id.accident_judge_linear:
				new CallTelephone(AccidentActivity.this, "122", "122").call();
				break;
			case R.id.accident_call_linear:
				intent = new Intent(AccidentActivity.this,
						InsuranceCallActivity.class);
				break;
			case R.id.accident_commission_linear:
				intent = new Intent(AccidentActivity.this,
						CommissionActivity.class);
				break;
			default:
				break;
			}

			if (intent != null) {
				startActivity(intent);
			}
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
