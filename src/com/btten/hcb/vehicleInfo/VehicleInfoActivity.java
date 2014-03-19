package com.btten.hcb.vehicleInfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.btten.base.BaseActivity;
import com.btten.hcb.wheelview.WheelShow;
import com.btten.hcbvip.R;
import com.btten.model.BaseJsonItem;
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
	private EditText carNumSecond, edtName, edtDriverLicense, edtRecord,
			edtFrame;
	private WheelShow date;
	private String[] strCity;
	private int flag = 0;
	private String vehicleID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_info_activity);
		initView();

		Bundle bundle = getIntent().getExtras();
		try {
			// 编辑模式
			vehicleID = bundle.getString("KEY_ID");
			if (vehicleID == null || vehicleID.equals("")) {
				throw new Exception();
			}
			flag = 0;
			new VehicleInfoScene().doscene(callBack, vehicleID);
		} catch (Exception e) {
			// 新增模式
			flag = 1;
			vehicleID = "";
		}
		strCity = getResources().getStringArray(R.array.city);
	}

	public void initView() {
		citySpinner = (Spinner) findViewById(R.id.vehicle_info_city);
		citySpinner.setOnItemSelectedListener(lSelectedListener);
		typeSpinner = (Spinner) findViewById(R.id.vehicle_info_type);

		btn_submit = (Button) findViewById(R.id.vehicle_info_submit);
		btn_submit.setOnClickListener(listener);
		btn_delete = (Button) findViewById(R.id.vehicle_info_delete);
		btn_delete.setOnClickListener(listener);

		carNumFirst = (TextView) findViewById(R.id.vehicle_info_car_number_first);
		carNumSecond = (EditText) findViewById(R.id.vehicle_info_car_number_second);
		edtName = (EditText) findViewById(R.id.vehicle_info_name);
		edtDriverLicense = (EditText) findViewById(R.id.vehicle_info_car_driverlicense);
		edtRecord = (EditText) findViewById(R.id.vehicle_info_record);
		edtFrame = (EditText) findViewById(R.id.vehicle_info_frame);

		date = (WheelShow) findViewById(R.id.vehicle_info_date);

		setSpinner();
	}

	OnItemSelectedListener lSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String city = parent.getItemAtPosition(position).toString();
			if (city.equals("武汉")) {
				carNumFirst.setText("鄂A");
			} else if (city.equals("孝感")) {
				carNumFirst.setText("鄂K");
			} else if (city.equals("宜昌")) {
				carNumFirst.setText("鄂E");
			} else if (city.equals("荆州")) {
				carNumFirst.setText("鄂D");
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};

	@SuppressLint("DefaultLocale")
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
								+ carNumSecond.getText().toString()
										.toUpperCase().trim(), edtFrame
								.getText().toString().trim(), date.getText()
								.toString(), edtName.getText().toString()
								.trim(), edtDriverLicense.getText().toString()
								.trim(), edtRecord.getText().toString().trim());
				break;
			case R.id.vehicle_info_delete:
				new VehicleInfoDeleteScene().doscene(submitCallBack, vehicleID);
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
			for (int i = 0; i < strCity.length; i++) {
				if (items.item.area.equals(strCity[i])) {
					citySpinner.setSelection(i, true);
				}
			}

			if (items.item.type.equals("小型车")) {
				typeSpinner.setSelection(0, true);
			} else if (items.item.type.equals("大型车")) {
				typeSpinner.setSelection(1, true);
			}
			carNumSecond.setText(items.item.carNo.substring(items.item.carNo
					.length() - 5));
			edtName.setText(items.item.name);
			edtDriverLicense.setText(items.item.drivingLicence);
			edtRecord.setText(items.item.fileNo);
			edtFrame.setText(items.item.frame);
			date.setText(items.item.date);
		}
	};

	OnSceneCallBack submitCallBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();
			Alert(((BaseJsonItem) data).info);
			onBackPressed();
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
