package com.btten.hcb.buddhist;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class BuddhistInfoActivity extends BaseActivity {

	private TextView txtTitle;
	private TextView txtDate;
	private TextView txtContent;
	String id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buddhist_info_activity);
		setCurrentTitle("每日一禅");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {

		txtTitle = (TextView) findViewById(R.id.buddhist_info_title);
		txtDate = (TextView) findViewById(R.id.buddhist_info_date);
		txtContent = (TextView) findViewById(R.id.buddhist_info_content);

		Bundle bundle = this.getIntent().getExtras();
		id = bundle.getString("KEY_ID");
		if (id == null) {
			Toast.makeText(this, "数据异常，请联系惠车宝！", Toast.LENGTH_SHORT).show();
		} else {
			new BuddhistInfoScene().doScene(callBack, id);
			ShowRunning();
		}

	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			BuddhistInfoResult items = (BuddhistInfoResult) data;
			txtTitle.setText(items.item.title);
			txtDate.setText(items.item.date);
			txtContent.setText(ToDBC(items.item.content));
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
