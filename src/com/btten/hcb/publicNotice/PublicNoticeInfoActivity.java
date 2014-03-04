package com.btten.hcb.publicNotice;

import android.os.Bundle;
import android.widget.TextView;
import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class PublicNoticeInfoActivity extends BaseActivity {

	private TextView tv_ggconten_title;
	private TextView tv_ggconten_date;
	private TextView tv_ggconten_content;
	String ggId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gonggaocontent_activity);
		setCurrentTitle("公告信息");
		setBackKeyListner(true);

		initView();
	}

	public void initView() {

		tv_ggconten_title = (TextView) findViewById(R.id.tvId_ggconten_biaoti);
		tv_ggconten_date = (TextView) findViewById(R.id.tvId_ggconten_riqi);
		tv_ggconten_content = (TextView) findViewById(R.id.tvId_ggconten_neirong);

		Bundle bundle = this.getIntent().getExtras();
		ggId = bundle.getString("KEY_GGID");
		DoRequest();
	}

	private void DoRequest() {
		new PublicNoticeInfoScene().doScene(callBack, ggId);
		ShowRunning();
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			PublicNoticeInfoResult items = (PublicNoticeInfoResult) data;
			tv_ggconten_title.setText(items.item.title);
			tv_ggconten_date.setText(items.item.date);
			tv_ggconten_content.setText(ToDBC(items.item.content));
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
