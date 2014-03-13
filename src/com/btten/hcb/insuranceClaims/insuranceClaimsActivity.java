package com.btten.hcb.insuranceClaims;

import android.os.Bundle;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;

public class insuranceClaimsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insurance_claims_activity);
		setCurrentTitle("车险理赔流程");
		setBackKeyListner(true);
	}

	@Override
	public void initDate() {

	}

}
