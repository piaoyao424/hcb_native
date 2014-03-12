package com.btten.hcb.peccancy;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class PeccancyDetailActivity extends BaseActivity {

	private TextView txtAuthor, txtTitle;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_info_activity);
		setCurrentTitle("违章详情");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {

		Bundle bundle = this.getIntent().getExtras();
		
		new PeccancyDetailListScene().doScene(callBack,
				bundle.getString("KEY_ID"));
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			final PeccancyDetailListResult items = (PeccancyDetailListResult) data;
			PeccancyDetailListAdapter adapter = new PeccancyDetailListAdapter(
					PeccancyDetailActivity.this, items.items);
			lv.setAdapter(adapter);
			HideProgress();
			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
