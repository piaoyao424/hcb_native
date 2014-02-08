package com.btten.hcb.about;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;

public class GuideActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us_guide);
		setCurrentTitle("关于我们");
		setBackKeyListner(backListener);
	}

	OnClickListener backListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			onBackPressed();
		}
	};

}
