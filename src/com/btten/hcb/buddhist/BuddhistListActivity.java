package com.btten.hcb.buddhist;

import android.os.Bundle;
import android.widget.ListView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class BuddhistListActivity extends BaseActivity {

	private ListView lv;
	private PublicNoticeListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publicnotice_activity);
		setCurrentTitle("公告信息");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {

		lv = (ListView) findViewById(R.id.publicnotice_activity_lv);
		DoRequest();
		adapter = new PublicNoticeListAdapter(this);
	}

	private void DoRequest() {
		new PublicNoticeListScene().doScene(callBack);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			PublicNoticeListResult items = (PublicNoticeListResult) data;
			adapter.setItems(items.items);
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
