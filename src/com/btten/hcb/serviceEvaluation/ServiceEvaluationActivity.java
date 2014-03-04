package com.btten.hcb.serviceEvaluation;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class ServiceEvaluationActivity extends BaseActivity {
	private ListView lv_evaluation;
	private TextView txt_jmsName;
	private RatingBar tRatingBar;
	private String jmsID, jname;
	private float jstar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation_service_activity);

		initData();
		initView();

		ShowProgress("查询中", "请稍候……");

		new ServiceEvaluationScene().doServiceEvaluationListScene(
				SearchcallBack, jmsID);
	}

	// 初始化控件
	public void initView() {
		setCurrentTitle("服务评价");
		lv_evaluation = (ListView) findViewById(R.id.evaluation_service_list);
		txt_jmsName = (TextView) findViewById(R.id.evaluation_service_jmsname);
		tRatingBar = (RatingBar) findViewById(R.id.evaluation_service_ratingbar);

		txt_jmsName.setText(jname);
		tRatingBar.setRating(jstar);

		setBackKeyListner(true);
	}

	private void initData() {
		Bundle bundle = this.getIntent().getExtras();
		jmsID = bundle.getString("KEY_JID");
		jname = bundle.getString("KEY_JNAME");
		jstar = bundle.getFloat("KEY_JSTAR");
	}

	// 查询回调
	OnSceneCallBack SearchcallBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			ServiceEvaluationListAdapter adapter = new ServiceEvaluationListAdapter(
					ServiceEvaluationActivity.this);

			ServiceEvaluationItem[] sItems = ((ServiceEvaluationResult) data).items;
			lv_evaluation.setAdapter(adapter);

			adapter.setItems(sItems);
			adapter.notifyDataSetChanged();
			HideProgress();
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub
		
	}
}
