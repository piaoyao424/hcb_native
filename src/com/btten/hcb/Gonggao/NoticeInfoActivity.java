package com.btten.hcb.Gonggao;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.btten.hcbvip.R;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.tools.Util;

public class NoticeInfoActivity extends BaseActivity {

	private TextView tv_ggconten_biaoti;
	private TextView tv_ggconten_riqi;
	private TextView tv_ggconten_neirong;
	private TextView tv_ggconten_name;
	NoticeInfoScene ggScene = null;
	String ggId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gonggaocontent_activity);
		setCurrentTitle("公告信息");

		init();
	}

	private void init() {

		tv_ggconten_biaoti = (TextView) findViewById(R.id.tvId_ggconten_biaoti);
		tv_ggconten_riqi = (TextView) findViewById(R.id.tvId_ggconten_riqi);
		tv_ggconten_neirong = (TextView) findViewById(R.id.tvId_ggconten_neirong);
		tv_ggconten_name = (TextView) findViewById(R.id.tvId_ggconten_name);
		
		// 取得当前登录的用户的userid
		Bundle bundle = this.getIntent().getExtras();
		ggId = bundle.getString("KEY_GGID");
		tv_ggconten_biaoti.setText(bundle.getString("KEY_TITLE"));
		tv_ggconten_riqi.setText(bundle.getString("KEY_DATE"));
		DoRequest();
	}

	private void DoRequest() {
		ggScene = new NoticeInfoScene();
		ggScene.doscene(callBack, ggId);
	}

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			NoticeInfoItems items = (NoticeInfoItems) data;
			
			tv_ggconten_neirong.setText(items.item.content);
			tv_ggconten_name.setText("惠车宝");

			return;
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			ErrorAlert(status, info);
		}
	};

	/***
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
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
}
