package com.btten.hcb.insurance;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;
import com.btten.vincenttools.CallTelephone;

public class InsuranceCallActivity extends BaseActivity {
	private int[] linearID = { R.id.insurance_call_linear_95500,
			R.id.insurance_call_linear_95505, R.id.insurance_call_linear_95512,
			R.id.insurance_call_linear_95518, R.id.insurance_call_linear_95556,
			R.id.insurance_call_linear_95585, R.id.insurance_call_linear_95590 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insurance_call_activity);
		setCurrentTitle("联系保险公司");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		for (int i = 0; i < linearID.length; i++) {
			LinearLayout layout = (LinearLayout) findViewById(linearID[i]);
			layout.setOnClickListener(listener);
		}
	}

	OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			String phone = null;
			String name = null;
			switch (v.getId()) {

			case R.id.insurance_call_linear_95500:
				phone = "95500";
				name = "太平洋保险";
				break;
			case R.id.insurance_call_linear_95505:
				phone = "95505";
				name = "天安保险";
				break;
			case R.id.insurance_call_linear_95512:
				phone = "95512";
				name = "平安保险";
				break;
			case R.id.insurance_call_linear_95518:
				phone = "95518";
				name = "人民财产保险";
				break;
			case R.id.insurance_call_linear_95556:
				phone = "95556";
				name = "华安保险";
				break;
			case R.id.insurance_call_linear_95585:
				phone = "95585";
				name = "中华联合保险";
				break;
			case R.id.insurance_call_linear_95590:
				phone = "95590";
				name = "大地保险";
				break;
			default:
				break;
			}
			new CallTelephone(InsuranceCallActivity.this, phone, name).call();
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
