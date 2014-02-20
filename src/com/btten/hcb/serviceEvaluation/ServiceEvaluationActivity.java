package com.btten.hcb.serviceEvaluation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
	private String jmsID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation_service_list_item);

		initView();

		ShowProgress("查询中", "请稍候……");
		new ServiceEvaluationScene().doServiceEvaluationListScene(
				SearchcallBack, jmsID);
	}

	// 初始化控件
	private void initView() {
		lv_evaluation = (ListView) findViewById(R.id.evaluation_service_list);
		txt_jmsName = (TextView) findViewById(R.id.evaluation_service_jmsname);
		tRatingBar = (RatingBar) findViewById(R.id.evaluation_service_ratingbar);

		setBackKeyListner(true);

		Bundle bundle = this.getIntent().getExtras();
		jmsID = bundle.getString("KEY_JID");
	}

	// txt监听
	OnClickListener listener = new OnClickListener() {

		@SuppressLint("ResourceAsColor")
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.map_key_imagebutton:
				break;
			}
		}
	};

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

			ServiceEvaluationResultItem[] sItems = ((ServiceEvaluationResultItems) data).items;
			txt_jmsName.setText(((ServiceEvaluationResultItems) data).jmsName);
			tRatingBar.setRating(((ServiceEvaluationResultItems) data).star);
			lv_evaluation.setAdapter(adapter);

			adapter.setItems(sItems);
			adapter.notifyDataSetChanged();
			HideProgress();
		}
	};
}
