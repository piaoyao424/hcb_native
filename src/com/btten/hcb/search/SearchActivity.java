package com.btten.hcb.search;

import java.util.ArrayList;
import java.util.List;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.btten.Jms.R;
import com.btten.account.VIPAccountManager;
import com.btten.base.BaseActivity;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class SearchActivity extends BaseActivity {
	// 区域list 目录list 商品lsit 限制条件list
	private ListView lv_area, lv_salesmenu, lv_salesitems, lv_criteria,
			lv_jmsinfo;
	private LinearLayout linear_area, linear_list, linear_criteria;

	private TextView txt_area, txt_list, txt_criteria;

	private String areaID = "261";
	private String menuID = "0";
	
	private 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		initView();
		initData();
	}

	private void initView() {
		lv_area = (ListView) findViewById(R.id.saleslist_lv_area);
		lv_salesmenu = (ListView) findViewById(R.id.saleslist_lv_salesmenu);
		lv_salesitems = (ListView) findViewById(R.id.saleslist_lv_salesitems);
		lv_criteria = (ListView) findViewById(R.id.saleslist_lv_criteria);
		lv_jmsinfo = (ListView) findViewById(R.id.saleslist_lv_jmsinfo);

		linear_area = (LinearLayout) findViewById(R.id.saleslist_linear_area);
		linear_list = (LinearLayout) findViewById(R.id.saleslist_linear_list);
		linear_criteria = (LinearLayout) findViewById(R.id.saleslist_linear_criteria);

		txt_area = (TextView) findViewById(R.id.saleslist_txt_area);
		txt_area.setOnClickListener(listener);
		txt_list = (TextView) findViewById(R.id.saleslist_txt_list);
		txt_list.setOnClickListener(listener);
		txt_criteria = (TextView) findViewById(R.id.saleslist_txt_criteria);
		txt_criteria.setOnClickListener(listener);
	}

	private void initData() {
		Bundle bundle = this.getIntent().getExtras();
		menuID = bundle.getString("KEY_MENUID");
	}

	OnClickListener listener = new OnClickListener() {

		@SuppressLint("ResourceAsColor")
		@Override
		public void onClick(View v) {

			txt_area.setBackgroundColor(R.color.orange);
			txt_list.setBackgroundColor(R.color.orange);
			txt_criteria.setBackgroundColor(R.color.orange);

			switch (v.getId()) {
			case R.id.saleslist_txt_area:
				txt_area.setBackgroundColor(R.color.orange_deep);
				doArea();
				break;
			case R.id.saleslist_txt_list:
				txt_list.setBackgroundColor(R.color.orange_deep);
				doSalesItems();
				break;
			case R.id.saleslist_txt_criteria:
				txt_criteria.setBackgroundColor(R.color.orange_deep);
				doCriteria();
				break;
			}
		}
	};

	private SearchScene searchScene = new SearchScene();

	private void doArea() {
		ShowProgress("加载地区数据", "请稍候……");
		searchScene.doAreaScene(AreacallBack, VIPAccountManager.getInstance()
				.getAreaID());
	}

	private void doSalesItems() {

		ShowProgress("加载销售列表", "请稍候……");

		searchScene.doSalesListScene(SalesListcallBack, VIPAccountManager
				.getInstance().getUserid());
	}

	private void doCriteria() {
		//静态加载限制条件列表
		List<String> data = new ArrayList<String>();
		Resources rs = getResources();
		String[] items = rs.getStringArray(R.array.Criteria);

		for (String item : items) {
			data.add(item);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.saleslist_item, R.id.saleslist_txt_item, data);
		lv_criteria.setAdapter(adapter);
		lv_criteria.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				view.setBackgroundColor(R.color.deep_gray);			
			}
		});
	}

	private void dosearch() {

		ShowProgress("登录中", "请稍候……");
		searchScene.doSearchScene(SearchcallBack, null, null);
	}

	OnSceneCallBack AreacallBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);

		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {

		}
	};

	OnSceneCallBack SalesListcallBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);

		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {

		}
	};

	OnSceneCallBack SearchcallBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);

		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {

		}
	};
}
