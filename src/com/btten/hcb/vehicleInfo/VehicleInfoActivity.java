package com.btten.hcb.vehicleInfo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.btten.base.BaseActivity;
import com.btten.hcb.wheelview.WheelShow;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class VehicleInfoActivity extends BaseActivity {

	private ArrayAdapter<CharSequence> cityAdapter = null;
	private ArrayAdapter<CharSequence> typeAdapter = null;
	private Spinner citySpinner = null;
	private Spinner typeSpinner = null;
	private Button btn_submit = null;
	private Button btn_delete = null;
	private TextView carNumFirst;
	private EditText carNumSecond;
	private WheelShow date;

	int flag = 0;
	String vehicleID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_info_activity);
		initView();
	}

	public void initView() {
		citySpinner = (Spinner) findViewById(R.id.vehicle_info_city);
		typeSpinner = (Spinner) findViewById(R.id.vehicle_info_type);

		btn_submit = (Button) findViewById(R.id.vehicle_info_submit);
		btn_submit.setOnClickListener(listener);
		btn_delete = (Button) findViewById(R.id.vehicle_info_delete);
		btn_delete.setOnClickListener(listener);

		carNumFirst = (TextView) findViewById(R.id.vehicle_info_car_number_first);
		carNumSecond = (EditText) findViewById(R.id.vehicle_info_car_number_second);

		date = (WheelShow) findViewById(R.id.vehicle_info_date);
		
		setSpinner();
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ShowRunning();
			switch (v.getId()) {
			case R.id.vehicle_info_submit:
				new VehicleInfoSubmitScene().doscene(submitCallBack, flag,
						vehicleID, typeSpinner.getSelectedItem().toString(),
						citySpinner.getSelectedItem().toString(), carNumFirst
								.getText().toString()
								+ carNumSecond.getText().toString().trim(),
						 null, null, null);
				break;
			case R.id.vehicle_info_delete:
				new VehicleInfoDeleteScene().doscene(submitCallBack,
						citySpinner.getSelectedItem().toString());
				break;
			default:
				break;
			}

		}

	};

	OnSceneCallBack callBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();
			VehicleInfoResult items = (VehicleInfoResult) data;
			Alert(items.price);
		}
	};

	OnSceneCallBack submitCallBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();

			Alert("");
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	private void setSpinner() {

		// 初始化城市
		// 将可选内容与ArrayAdapter连接起来
		cityAdapter = ArrayAdapter.createFromResource(this, R.array.city,
				R.layout.spinnerlayout);
		cityAdapter.setDropDownViewResource(R.layout.spinnerlayout);
		citySpinner.setAdapter(cityAdapter);
		citySpinner.setSelection(0, true);

		// 初始化车型
		// 将可选内容与ArrayAdapter连接起来
		typeAdapter = ArrayAdapter.createFromResource(this, R.array.car_type,
				R.layout.spinnerlayout);
		typeAdapter.setDropDownViewResource(R.layout.spinnerlayout);
		typeSpinner.setAdapter(typeAdapter);
		typeSpinner.setSelection(0, true);
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
