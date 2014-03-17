package com.btten.hcb.roadRescue;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class RoadRescueActivity extends BaseActivity {
	private List<RoadRescueMenuItem> groupArray;// 组列表
	private List<List<RoadRescueMenuItem>> childArray;// 子列表
	private ExpandableListView expandableListView;

	class ExpandableListItemHolder { // 定义一个内部类，用于保存listitem的3个子视图引用
		TextView txt_Name;
		TextView txt_oldprice;
		TextView txt_newprice;
		TextView txt_area;
		String itemid;
		String name;
		String phone;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.road_rescue_activity);
		groupArray = new ArrayList<RoadRescueMenuItem>();
		childArray = new ArrayList<List<RoadRescueMenuItem>>();
		initView();
	}

	public void initView() {
		setCurrentTitle("道路救援");
		expandableListView = (ExpandableListView) findViewById(R.id.jmsinfo_explv);
		expandableListView.setGroupIndicator(null);
		expandableListView.setOnChildClickListener(onChildClickListener);
		ShowRunning();
		new RoadRescueMenuScene().doScene(callBack);
	}

	OnChildClickListener onChildClickListener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			Intent intent = new Intent(RoadRescueActivity.this,
					RoadRescuePhoneActivity.class);
			intent.putExtra("KEY_NAME", groupArray.get(groupPosition).name);
			intent.putExtra("KEY_PHONE", groupArray.get(groupPosition).phone);
			startActivity(intent);
			return false;
		}
	};

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			RoadRescueMenuResult items = (RoadRescueMenuResult) data;
			for (RoadRescueMenuItem item : items.items) {
				if (item.upid.equals("0")) {
					groupArray.add(item);
					List<RoadRescueMenuItem> tempchildArray = new ArrayList<RoadRescueMenuItem>();
					for (int i = 0; i < items.items.length; i++) {
						if (items.items[i].upid.equals(item.id)) {
							tempchildArray.add(items.items[i]);
						}
					}
					childArray.add(tempchildArray);
				}
			}
			expandableListView.setAdapter(new MyExpandableListViewAdapter(
					RoadRescueActivity.this));
			HideProgress();
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
		private LayoutInflater mChildInflater; // 用于加载listitem的布局xml
		private LayoutInflater mGroupInflater; // 用于加载group的布局xml

		public MyExpandableListViewAdapter(Activity activity) {
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return childArray.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			ExpandableListItemHolder holder = null;
			if (convertView == null) {

				convertView = mChildInflater.inflate(
						R.layout.road_rescue_childs, null);

				holder = new ExpandableListItemHolder();
				holder.txt_Name = (TextView) convertView
						.findViewById(R.id.road_rescue_childs_name);
				holder.txt_oldprice = (TextView) convertView
						.findViewById(R.id.road_rescue_childs_oldprice);
				holder.txt_newprice = (TextView) convertView
						.findViewById(R.id.road_rescue_childs_newprice);
				holder.txt_area = (TextView) convertView
						.findViewById(R.id.road_rescue_childs_area);
				convertView.setTag(holder);
			} else { // 若行已初始化，直接从tag属性获得子视图的引用
				holder = (ExpandableListItemHolder) convertView.getTag();
			}
			// 获得行数据（模型）
			RoadRescueMenuItem info = childArray.get(groupPosition).get(
					childPosition);

			if (info != null) {
				// 根据模型数据，设置行视图的控件值
				holder.txt_Name.setText(info.name);
				holder.txt_oldprice.setText(info.oldprice);
				holder.txt_newprice.setText(info.newprice);
				holder.txt_oldprice.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG);
				holder.txt_area.setText(info.area);
				holder.itemid = info.id;
				holder.phone = info.phone;
			}
			return convertView;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			ExpandableListItemHolder holder = null; // 清空临时变量holder
			if (convertView == null) { // 判断view（即view是否已构建好）是否为空
				convertView = mGroupInflater.inflate(
						R.layout.road_rescue_groups, null);
				// 下面主要是取得组的各子视图，设置子视图的属性。用tag来保存各子视图的引用
				holder = new ExpandableListItemHolder();
				// 从view中取得textView
				holder.txt_Name = (TextView) convertView
						.findViewById(R.id.road_rescue_groups_txt_name);
				convertView.setTag(holder);
			} else { // 若view不为空，直接从view的tag属性中获得各子视图的引用
				holder = (ExpandableListItemHolder) convertView.getTag();
			}
			// 对应于组索引的组数据（模型）
			RoadRescueMenuItem info = groupArray.get(groupPosition);
			if (info != null) {
				// 根据模型值设置textview的文本
				holder.txt_Name.setText(info.name);
			}
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return childArray.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return getGroup(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return groupArray.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub

	}
}
