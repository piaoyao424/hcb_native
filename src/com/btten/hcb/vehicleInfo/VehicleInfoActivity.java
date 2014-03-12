package com.btten.hcb.vehicleInfo;

//车辆模块
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class VehicleInfoActivity extends BaseActivity {
	private static final String[] month_toserver = { "01", "02", "03", "04",
			"05", "06", "07", "08", "09", "10", "11", "12", };
	private Button btn_chaxun = null;
	private String month_str = null;
	private String year_str = null;
	private ArrayAdapter<CharSequence> monthAdapter = null;
	private ArrayAdapter<String> yearAdapter = null;
	// 下拉框控制
	private Spinner monthSpinner = null;
	private Spinner yearSpinner = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_info_activity);
		initView();
	}

	public void initView() {
		yearSpinner = (Spinner) findViewById(R.id.insurance_year);
		monthSpinner = (Spinner) findViewById(R.id.insurance_mouth);

		// 初始化列表页面
		btn_chaxun = (Button) findViewById(R.id.insurance_button);
		btn_chaxun.setOnClickListener(listener);
		setSpinner();
	}

	OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			doRequest();
		}
	};

	private void doRequest() {
		ShowRunning();
		new VehicleInfoScene().doscene(callBack, month_str);
	}

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

	private void setSpinner() {
		// 初始化年份
		yearAdapter = new ArrayAdapter<String>(this, R.layout.spinnerlayout);
		int year = Calendar.getInstance().get(Calendar.YEAR);

		for (int i = 0; i < 20; i++) {
			yearAdapter.add(String.valueOf(year - i));
		}
		yearAdapter.setDropDownViewResource(R.layout.spinnerlayout);
		yearSpinner.setAdapter(yearAdapter);
		yearSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		year_str = String.valueOf(year);
		yearSpinner.setSelection(0, true);

		// 初始化月份
		// 将可选内容与ArrayAdapter连接起来
		monthAdapter = ArrayAdapter.createFromResource(this, R.array.month,
				R.layout.spinnerlayout);
		// 设置下拉列表的风格
		monthAdapter.setDropDownViewResource(R.layout.spinnerlayout);
		monthSpinner.setAdapter(monthAdapter);
		// 添加事件Spinner事件监听
		monthSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		// 设置默认值
		month_str = month_toserver[Calendar.MONTH - 1 - 1];
		monthSpinner.setSelection((Calendar.MONTH - 1 - 1), true);
		
		
	}

	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg0.equals(monthSpinner)) {
				month_str = month_toserver[arg2];
			} else if (arg0.equals(yearSpinner)) {
				year_str = String.valueOf((Calendar.getInstance().get(
						Calendar.YEAR) - arg2));
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
