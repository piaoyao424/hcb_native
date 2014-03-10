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
	private TextView txtAuthor, txtTitle, txtSynopsis;
	private ImageView imageView;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_list_activity);
		setCurrentTitle("购买网点");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		txtAuthor = (TextView) findViewById(R.id.book_list_author);
		txtTitle = (TextView) findViewById(R.id.book_list_title);
		txtSynopsis = (TextView) findViewById(R.id.book_list_synopsis);
		imageView = (ImageView) findViewById(R.id.book_list_image);
		button = (Button) findViewById(R.id.book_list_button);

		lv = (ListView) findViewById(R.id.booklist_activity_lv);
		DoRequest();
	}

	private void DoRequest() {
		new BranchListScene().doScene(callBack);
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
