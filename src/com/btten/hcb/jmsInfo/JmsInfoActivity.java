package com.btten.hcb.jmsInfo;

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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.base.MainActivity;
import com.btten.hcb.search.SearchActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;

public class JmsInfoActivity extends BaseActivity {

	private String jid;
	private List<JmsInfoSaleMenuItem> groupArray;// 组列表
	private List<List<JmsInfoSaleMenuItem>> childArray;// 子列表
	private ExpandableListView expandableListView;
	private TextView txt_jname, txt_scope, txt_commentNum;
	private RatingBar ratingBar;
	private ImageView imageView1, imageView2;

	class ExpandableListItemHolder { // 定义一个内部类，用于保存listitem的3个子视图引用
		TextView txt_Name;
		TextView txt_oldprice;
		TextView txt_newprice;
		TextView txt_sorce;
		String itemid;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jmsinfo_avtivity);

		/*-第一季-*/
		initdate();
		initView();
	}

	private void initView() {
		txt_jname = (TextView) findViewById(R.id.jmsinfo_jname);
		txt_scope = (TextView) findViewById(R.id.jmsinfo_scope);
		txt_commentNum = (TextView) findViewById(R.id.jmsinfo_numstar);
		ratingBar = (RatingBar) findViewById(R.id.jmsinfo_rat_star);
		imageView1 = (ImageView) findViewById(R.id.jmsinfo_image_1);
		imageView2 = (ImageView) findViewById(R.id.jmsinfo_image_2);

		expandableListView = (ExpandableListView) findViewById(R.id.jmsinfo_explv);
		expandableListView.setGroupIndicator(null);
		expandableListView.setAdapter(new MyExpandableListViewAdapter(
				JmsInfoActivity.this));
		expandableListView.setOnChildClickListener(onChildClickListener);
	}

	private void initdate() {
		groupArray = new ArrayList<JmsInfoSaleMenuItem>();
		childArray = new ArrayList<List<JmsInfoSaleMenuItem>>();

		Bundle bundle = this.getIntent().getExtras();
		jid = bundle.getString("KEY_GGID");

		new JmsInfoScene().doScene(callBack, jid);
		ShowRunning();
	}

	OnChildClickListener onChildClickListener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			String itemid = childArray.get(groupPosition).get(childPosition).id;
			Intent intent = new Intent(JmsInfoActivity.this,
					JmsInfoActivity.class);
			intent.putExtra("KEY_ITEMID", itemid);
			startActivity(intent);
			return false;
		}
	};

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			JmsInfoItems items = (JmsInfoItems) data;
			txt_jname.setText(items.item.jname);
			txt_scope.setText(items.item.jscope);
			txt_commentNum.setText(items.item.commentNum + "人评价");
			ratingBar.setRating(items.item.star);
			imageLoader.displayImage(items.item.images1, imageView1);
			imageLoader.displayImage(items.item.images2, imageView2);
			HideProgress();
			new JmsInfoSaleMenuScene().doScene(callBackSaleMenu);
		}

		@Override
		public void OnFailed(int status, String info, NetSceneBase<?> netScene) {
			HideProgress();
			ErrorAlert(status, info);
		}
	};

	OnSceneCallBack callBackSaleMenu = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			JmsInfoSaleMenuItems items = (JmsInfoSaleMenuItems) data;
			for (JmsInfoSaleMenuItem item : items.items) {
				if (item.upid.equals("0")) {
					groupArray.add(item);
					List<JmsInfoSaleMenuItem> tempchildArray = new ArrayList<JmsInfoSaleMenuItem>();
					for (int i = 0; i < items.items.length; i++) {
						if (items.items[i].upid.equals(item.id)) {
							tempchildArray.add(items.items[i]);
						}
					}
					childArray.add(tempchildArray);
				}
			}
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

			ExpandableListItemHolder holder = null; // 清空临时变量
			if (convertView == null) { // 若行未初始化
				// 通过flater初始化行视图
				convertView = mChildInflater.inflate(
						R.layout.jmsinfo_salemenu_childs, null);
				// 并将行视图的3个子视图引用放到tag中
				holder = new ExpandableListItemHolder();
				holder.txt_Name = (TextView) convertView
						.findViewById(R.id.jmsinfo_salemenu_childs_name);
				holder.txt_oldprice = (TextView) convertView
						.findViewById(R.id.jmsinfo_salemenu_childs_oldprice);
				holder.txt_newprice = (TextView) convertView
						.findViewById(R.id.jmsinfo_salemenu_childs_newprice);
				holder.txt_sorce = (TextView) convertView
						.findViewById(R.id.jmsinfo_salemenu_childs_sorce);
				convertView.setTag(holder);
			} else { // 若行已初始化，直接从tag属性获得子视图的引用
				holder = (ExpandableListItemHolder) convertView.getTag();
			}
			// 获得行数据（模型）
			JmsInfoSaleMenuItem info = childArray.get(groupPosition).get(
					childPosition);

			if (info != null) {
				// 根据模型数据，设置行视图的控件值
				holder.txt_Name.setText(info.name);
				holder.txt_oldprice.setText(info.oldprice);
				holder.txt_newprice.setText(info.newprice);
				holder.txt_oldprice.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG);
				holder.txt_sorce.setText("获" + info.newprice + "分");
				holder.itemid = info.id;
			}
			return convertView;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			ExpandableListItemHolder holder = null; // 清空临时变量holder
			if (convertView == null) { // 判断view（即view是否已构建好）是否为空
				// 若组视图为空，构建组视图。注意flate的使用，R.layout.browser_expandable_list_item代表了
				// 已加载到内存的browser_expandable_list_item.xml文件
				convertView = mGroupInflater.inflate(
						R.layout.jmsinfo_salemenu_groups, null);
				// 下面主要是取得组的各子视图，设置子视图的属性。用tag来保存各子视图的引用
				holder = new ExpandableListItemHolder();
				// 从view中取得textView
				holder.txt_Name = (TextView) convertView
						.findViewById(R.id.jmsinfo_salemenu_groups_txt_name);
				;
				convertView.setTag(holder);
			} else { // 若view不为空，直接从view的tag属性中获得各子视图的引用
				holder = (ExpandableListItemHolder) convertView.getTag();
			}
			// 对应于组索引的组数据（模型）
			JmsInfoSaleMenuItem info = groupArray.get(groupPosition);
			if (info != null) {
				// 根据模型值设置textview的文本
				holder.txt_Name.setText(info.name);

				// 根据模型值设置checkbox的checked属性
				/*
				 * holder.chkChecked.setChecked(info.getChecked());
				 * holder.chkChecked.setTag(info);
				 * holder.chkChecked.setOnClickListener(new OnClickListener() {
				 * 
				 * @Override public void onClick(View v) { PhoneGroup group =
				 * (PhoneGroup) v.getTag();
				 * group.setChecked(!group.getChecked());
				 * notifyDataSetChanged(); } });
				 */
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
}
