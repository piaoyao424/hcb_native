package com.btten.hcb.vehicleKnowledge;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CarKnowledgeInfoActivity extends BaseActivity {

	private TextView txtTitle, txtContent;
	private ImageView imageView;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carknowledge_info_activity);
		setCurrentTitle("车知识详情");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {

		txtTitle = (TextView) findViewById(R.id.carknowledge_info_title);
		imageView = (ImageView) findViewById(R.id.carknowledge_info_image);
		txtContent = (TextView) findViewById(R.id.carknowledge_info_content);

		layout = (LinearLayout) findViewById(R.id.carknowledge_info_linearlayout);

		Bundle bundle = this.getIntent().getExtras();
		new CarKnowledgeInfoScene().doScene(callBack,
				bundle.getString("KEY_ID"));
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			final CarKnowledgeInfoResult items = (CarKnowledgeInfoResult) data;
			
			txtContent.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
			txtContent.setText(Html.fromHtml(items.item.content));
			
			txtTitle.setText(items.item.title);

			ImageLoader.getInstance().displayImage(items.item.image, imageView);

			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(CarKnowledgeInfoActivity.this,
							CarKnowledgeInfoActivity.class);
					intent.putExtra("KEY_ID", items.item.id);
					startActivity(intent);
				}
			});
			HideProgress();
			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	/***
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	private static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
