package com.btten.hcb.discuss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class DiscussListActivity extends BaseActivity {

	private ListView lv;
	private EditText txtContent;
	private Button button;
	private String id;
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discuss_activity);
		setCurrentTitle("互动交流");
		setBackKeyListner(true);

		try {
			Bundle bundle = this.getIntent().getExtras();
			id = bundle.getString("KEY_ID");
			type = bundle.getInt("KEY_TYPE");
		} catch (Exception e) {
			type = 0;
		}
		
		initView();
	}

	public void initView() {
		txtContent = (EditText) findViewById(R.id.discuss_activity_content);
		button = (Button) findViewById(R.id.discuss_activity_submit);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DiscussSubmitScene().doScene(submitCallBack, txtContent
						.getText().toString().trim(), id);
			}
		});

		lv = (ListView) findViewById(R.id.discuss_activity_lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (type == 0) {
					Intent intent = new Intent(DiscussListActivity.this,
							DiscussListActivity.class);
					intent.putExtra("KEY_ID",
							((DiscussListAdapter.ViewHolder) view.getTag()).id);
					intent.putExtra("KEY_TYPE", 1);
					startActivity(intent);
				}
			}
		});

		new DiscussListScene().doScene(listCallBack);
		ShowRunning();
	}

	OnSceneCallBack listCallBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			DiscussListResult item = (DiscussListResult) data;

			DiscussListAdapter adapter = new DiscussListAdapter(
					DiscussListActivity.this, item.items);
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

	OnSceneCallBack submitCallBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();
			Alert("发送成功！");

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
