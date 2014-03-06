package com.btten.hcb.carKnowledge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CarKnowledgeActivity extends BaseActivity {

	private ListView lv;
	private TextView txtTitle, txtContent;
	private ImageView imageView;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carknowledge_list_activity);
		setCurrentTitle("车知识");
		setBackKeyListner(true);
		initView();
	}

	public void initView() {
		txtTitle = (TextView) findViewById(R.id.carknowledge_list_title);
		txtContent = (TextView) findViewById(R.id.carknowledge_list_content);
		imageView = (ImageView) findViewById(R.id.carknowledge_list_image);
		button = (Button) findViewById(R.id.carknowledge_list_button);

		lv = (ListView) findViewById(R.id.carknowledge_activity_lv);
		new CarKnowledgeListScene().doScene(callBack);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			CarKnowledgeListResult item = (CarKnowledgeListResult) data;
			final CarKnowledgeListItem[] items = item.items;
			// 取最新的数据为今天推荐
			txtTitle.setText(items[0].title);
			txtContent.setText(items[0].content);
			ImageLoader.getInstance().displayImage(items[0].image, imageView);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(CarKnowledgeActivity.this,
							CarKnowledgeInfoActivity.class);
					intent.putExtra("KEY_ID", items[0].id);
					startActivity(intent);
				}
			});
			// 拿掉第一个数据
			for (int i = 0; i < items.length; i++) {
				items[i] = items[i + 1];
			}

			items[items.length - 1] = null;

			CarKnowledgeListAdapter adapter = new CarKnowledgeListAdapter(
					CarKnowledgeActivity.this, item.items);
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
