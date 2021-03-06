package com.btten.hcb.jmsInfo;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.hcb.map.BMapActivity;
import com.btten.hcb.map.JmsGps;
import com.btten.hcb.serviceEvaluation.ServiceEvaluationActivity;
import com.btten.hcbvip.R;
import com.btten.network.NetSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.vincenttools.CallTelephone;

public class JmsInfoActivity extends BaseActivity {

	private JmsInfoItem jmsInfo;
	private List<JmsInfoSaleMenuItem> groupArray;// 组列表
	private List<List<JmsInfoSaleMenuItem>> childArray;// 子列表
	private ExpandableListView expandableListView;
	private TextView txt_jname, txt_scope, txt_commentNum, txt_address,
			txt_phone;
	private RatingBar ratingBar;
	private ImageView imageView1, imageView2;
	private RelativeLayout address_layout, phone_layout;
	private LinearLayout starLinearLayout;

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
		setContentView(R.layout.jmsinfo_activity);

		initdate();
		initView();
	}

	public void initView() {
		setCurrentTitle("网点详情");
		txt_jname = (TextView) findViewById(R.id.jmsinfo_jname);
		txt_scope = (TextView) findViewById(R.id.jmsinfo_scope);
		txt_commentNum = (TextView) findViewById(R.id.jmsinfo_numstar);
		txt_address = (TextView) findViewById(R.id.jmsinfo_address);
		txt_phone = (TextView) findViewById(R.id.jmsinfo_phone);

		ratingBar = (RatingBar) findViewById(R.id.jmsinfo_rat_star);
		imageView1 = (ImageView) findViewById(R.id.jmsinfo_image_1);
		imageView2 = (ImageView) findViewById(R.id.jmsinfo_image_2);
		address_layout = (RelativeLayout) findViewById(R.id.jmsinfo_relative_address);
		address_layout.setOnClickListener(clickListener);
		phone_layout = (RelativeLayout) findViewById(R.id.jmsinfo_relative_phone);
		phone_layout.setOnClickListener(clickListener);
		starLinearLayout = (LinearLayout) findViewById(R.id.jmsinfo_activity_star_layout);
		starLinearLayout.setOnClickListener(clickListener);

		expandableListView = (ExpandableListView) findViewById(R.id.jmsinfo_explv);
		expandableListView.setGroupIndicator(null);

		setBackKeyListner(true);
	}

	private void initdate() {
		jmsInfo = new JmsInfoItem();
		groupArray = new ArrayList<JmsInfoSaleMenuItem>();
		childArray = new ArrayList<List<JmsInfoSaleMenuItem>>();

		Bundle bundle = this.getIntent().getExtras();
		jmsInfo.id = bundle.getString("KEY_GGID");

		new JmsInfoScene().doScene(callBack, jmsInfo.id);
		ShowRunning();
	}

	OnClickListener clickListener = new OnClickListener() {
		Intent intent;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.jmsinfo_relative_address:
				intent = new Intent(JmsInfoActivity.this, BMapActivity.class);

				// 初始化加盟商GPS信息
				JmsGps jmsGps = new JmsGps();

				jmsGps.id = jmsInfo.id;
				jmsGps.name = jmsInfo.jname;
				jmsGps.la = jmsInfo.gps_la;
				jmsGps.lo = jmsInfo.gps_lo;

				ArrayList<JmsGps> list_jmsgps = new ArrayList<JmsGps>();
				list_jmsgps.add(jmsGps);

				intent.putExtra("KEY_JMSGPS", list_jmsgps);
				startActivity(intent);
				break;
			case R.id.jmsinfo_relative_phone:
				new CallTelephone(JmsInfoActivity.this, jmsInfo.phone,
						jmsInfo.jname).call();
				break;
			case R.id.jmsinfo_activity_star_layout:
				intent = new Intent(JmsInfoActivity.this,
						ServiceEvaluationActivity.class);
				intent.putExtra("KEY_JID", jmsInfo.id);
				intent.putExtra("KEY_JNAME", jmsInfo.jname);
				intent.putExtra("KEY_JSTAR", ratingBar.getRating());
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	};

	OnSceneCallBack callBack = new OnSceneCallBack() {

		@Override
		public void OnSuccess(Object data, NetSceneBase<?> netScene) {
			JmsInfoItems items = (JmsInfoItems) data;
			txt_jname.setText(items.item.jname);
			txt_scope.setText(items.item.jscope);
			txt_commentNum.setText(items.item.commentNum + "人评价");
			txt_address.setText(items.item.address);
			txt_phone.setText(items.item.phone);
			jmsInfo = items.item;

			ratingBar.setRating(items.item.star);
			imageLoader.displayImage(items.item.images1, imageView1);
			imageLoader.displayImage(items.item.images2, imageView2);

			HideProgress();
			new JmsInfoSaleMenuScene().doScene(callBackSaleMenu, jmsInfo.id);
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
				String upid = item.id;
				if (item.upid.equals("0")) {
					groupArray.add(item);

					List<JmsInfoSaleMenuItem> tempchildArray = new ArrayList<JmsInfoSaleMenuItem>();
					// 找到child数据
					for (JmsInfoSaleMenuItem item1 : items.items) {
						if (item1.upid.equals(upid)) {
							tempchildArray.add(item1);
						}
					}

					childArray.add(tempchildArray);
				}
			}
			expandableListView.setAdapter(new MyExpandableListViewAdapter(
					JmsInfoActivity.this));
			expandableListView.expandGroup(0);
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

		public MyExpandableListViewAdapter(Context context) {
			mChildInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mGroupInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
				// 通过inflater初始化行视图
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
				holder.txt_oldprice.setText("￥" + info.oldprice);
				holder.txt_newprice.setText("￥" + info.newprice);
				holder.txt_oldprice.getPaint().setFlags(
						Paint.STRIKE_THRU_TEXT_FLAG);
				holder.txt_sorce.setText(info.newprice + "分");
				holder.itemid = info.id;
			}
			return convertView;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			ExpandableListItemHolder holder = null; // 清空临时变量holder
			if (convertView == null) { // 判断view是否已构建好
				// 若组视图为空，构建组视图。注意inflate的使用
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
			return false;
		}
	}

	@Override
	public void initDate() {
		// TODO Auto-generated method stub
		
	}
}
