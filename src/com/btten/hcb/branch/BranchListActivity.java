package com.btten.hcb.branch;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class BranchListActivity extends BaseActivity {

	private ListView lv;
	private TextView txtTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.branch_activity);
		setCurrentTitle("购买网点");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {

		txtTitle = (TextView) findViewById(R.id.branch_activity_title);
		lv = (ListView) findViewById(R.id.branch_activity_lv);
		Bundle bundle = getIntent().getExtras();
		txtTitle.setText(bundle.getString("KEY_NAME"));
		String id = bundle.getString("KEY_ID");
		
		new BranchListScene().doScene(callBack,id);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			BranchListResult item = (BranchListResult) data;
			final BranchListItem[] items = item.items;

			BranchListAdapter adapter = new BranchListAdapter(
					BranchListActivity.this, item.items);
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
