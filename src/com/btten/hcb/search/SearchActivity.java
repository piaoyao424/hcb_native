package com.btten.hcb.search;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
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
import com.btten.hcbvip.R;
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
	private String itemID = null;
	//缓存查询数据,避免更改第三选项时多次查询
	private List<SearchResultItem> searchResultItems = new ArrayList<SearchResultItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saleslist_activity);

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
		areaID = VIPAccountManager.getInstance().getAreaID();

		doArea();
		doCriteria();
	}

	// 隐藏其他listview
	private void clearOtherListView(LinearLayout linearLayout) {
		linear_area.setVisibility(View.GONE);
		linear_list.setVisibility(View.GONE);
		linear_criteria.setVisibility(View.GONE);

		linearLayout.setVisibility(View.VISIBLE);
	}

	// txt监听
	OnClickListener listener = new OnClickListener() {

		@SuppressLint("ResourceAsColor")
		@Override
		public void onClick(View v) {

			txt_area.setBackgroundResource(R.color.orange);
			txt_list.setBackgroundResource(R.color.orange);
			txt_criteria.setBackgroundResource(R.color.orange);

			switch (v.getId()) {
			case R.id.saleslist_txt_area:
				txt_area.setBackgroundResource(R.color.orange_deep);
				clearOtherListView(linear_area);
				break;
			case R.id.saleslist_txt_list:
				txt_list.setBackgroundResource(R.color.orange_deep);
				clearOtherListView(linear_list);
				break;
			case R.id.saleslist_txt_criteria:
				txt_criteria.setBackgroundResource(R.color.orange_deep);
				clearOtherListView(linear_criteria);
				break;
			}
		}
	};

	private void doArea() {
		ShowProgress("加载地区数据", "请稍候……");
		new SearchScene().doAreaScene(AreacallBack, VIPAccountManager
				.getInstance().getAreaID());
	}

	private void doSalesItems() {

		ShowProgress("加载销售列表", "请稍候……");

		new SearchScene().doSalesListScene(SalesListcallBack, VIPAccountManager
				.getInstance().getUserid());
	}

	// 静态加载限制条件列表
	private void doCriteria() {
		Resources rs = getResources();
		final String[] items = rs.getStringArray(R.array.Criteria);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				SearchActivity.this, R.layout.saleslist_item,
				R.id.saleslist_txt_item, items);
		lv_criteria.setAdapter(adapter);
		txt_criteria.setText(items[0]);

		lv_criteria.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				txt_criteria.setText(items[position]);
				linear_criteria.setVisibility(View.GONE);
			}
		});
	}

	private void dosearch() {

		ShowProgress("查询中", "请稍候……");
		new SearchScene().doSearchScene(SearchcallBack, areaID, itemID);
	}

	// 区域回调
	OnSceneCallBack AreacallBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			List<String> data1 = new ArrayList<String>();

			final SearchResultItem_area[] items = ((SearchResultItems) data).areaItems;

			for (SearchResultItem_area item : items) {
				data1.add(item.areaName);
			}

			txt_area.setText(data1.get(0));

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					SearchActivity.this, R.layout.saleslist_item,
					R.id.saleslist_txt_item, data1);

			lv_area.setAdapter(adapter);

			lv_area.setOnItemClickListener(new OnItemClickListener() {

				@SuppressLint("ResourceAsColor")
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					txt_area.setText(items[position].areaName);
					areaID = items[position].areaID;
					linear_area.setVisibility(View.GONE);
					dosearch();
				}
			});
			HideProgress();
			doSalesItems();
		}
	};

	// 商品列表回调
	OnSceneCallBack SalesListcallBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {

			final List<String> menu = new ArrayList<String>();
			final SearchResultItem_saleslist[] items = ((SearchResultItems) data).saleslist;

			for (SearchResultItem_saleslist item : items) {
				if (item.itemUpID.equals("0")) {
					menu.add(item.itemName);
				}
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					SearchActivity.this, R.layout.saleslist_item,
					R.id.saleslist_txt_item, menu);

			lv_salesmenu.setAdapter(adapter);

			lv_salesmenu.setOnItemClickListener(new OnItemClickListener() {

				@SuppressLint("ResourceAsColor")
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					showItems(items, menu.get(position));
				}
			});

			showItems(items, menu.get(0));
			HideProgress();
		}
	};

	// 点击目录,显示商品
	private void showItems(final SearchResultItem_saleslist[] items, String name) {
		String upid = null;

		for (SearchResultItem_saleslist searchResultItem_saleslist : items) {
			if (searchResultItem_saleslist.itemName.equals(name)) {
				upid = searchResultItem_saleslist.itemID;
				break;
			}
		}

		final List<String> itemlist = new ArrayList<String>();

		for (SearchResultItem_saleslist item : items) {
			if (item.itemUpID.equals(upid)) {
				itemlist.add(item.itemName);
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				SearchActivity.this, R.layout.saleslist_item,
				R.id.saleslist_txt_item, itemlist);
		lv_salesitems.setAdapter(adapter);
		txt_list.setText(itemlist.get(0));

		lv_salesitems.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				view.setBackgroundColor(R.color.deep_gray);

				int i = 0;
				String itemName = itemlist.get(position);
				while (i < items.length) {
					if (itemName.equals(items[i].itemName)) {
						itemID = items[i].itemID;
						txt_list.setText(items[i].itemName);
						linear_list.setVisibility(View.GONE);
						break;
					}
					i++;
				}
				dosearch();
			}
		});
	}

	// 查询回调
	OnSceneCallBack SearchcallBack = new OnSceneCallBack() {
		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			HideProgress();
		}
	};
}
