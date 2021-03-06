package com.btten.hcb;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.btten.uikit.BttenFlipper;
import com.btten.uikit.ViewFlowAdapter;
import com.btten.base.BaseActivity;
import com.btten.hcb.service.CallTaxiNotification;
import com.btten.hcbvip.R;

public class WelcomActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomeactivity);
		initView();
	}

	public void initView() {
		BttenFlipper viewFlow = (BttenFlipper) findViewById(R.id.viewFlow);
		viewFlow.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		viewFlow.setAdapter(new ViewFlowAdapter(this));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (IsFirstStart())
			CallTaxiNotification.getInstance().ExitApp();
	}

	private boolean IsFirstStart() {
		boolean isfirststart = true;

		SharedPreferences settings = getSharedPreferences("hcbcfg",
				MODE_PRIVATE);
		isfirststart = settings.getBoolean("IsFirstStart", true);

		return isfirststart;
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub
		
	}
}
