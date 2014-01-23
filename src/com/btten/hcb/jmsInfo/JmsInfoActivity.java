package com.btten.hcb.jmsInfo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.btten.base.BaseActivity;
import com.btten.hcbvip.R;
import com.umeng.update.UmengUpdateAgent;

public class JmsInfoActivity extends BaseActivity {

	private String jid;
	private List<String> groupArray;// 组列表
	private List<List<String>> childArray;// 子列表
	private ExpandableListView expandableListView;
	private TextView txt_jname, txt_scope, txt_commentNum;
	private RatingBar ratingBar;
	private ImageView imageView1, imageView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jmsinfo_avtivity);

		/*-第一季-*/
		initdate();
		initView();

		/*-第二季-*/
		// groupArray.add("移动开发");
		// List<String> arrayList = new ArrayList<String>();
		// arrayList.add("Android");
		// arrayList.add("IOS");
		// arrayList.add("Windows Phone");
		// //组循环
		// for(int index=0;index<groupArray.size();++index)
		// {
		// childArray.add(arrayList);
		// }
		// expandableListView_one.setAdapter(new
		// ExpandableListViewaAdapter(ExpandableListViewDemo.this));

	}

	private void initView() {
		txt_jname = (TextView) findViewById(R.id.jmsinfo_jname);
		txt_scope = (TextView) findViewById(R.id.jmsinfo_scope);
		txt_commentNum = (TextView) findViewById(R.id.jmsinfo_numstar);
		ratingBar = (RatingBar) findViewById(R.id.jmsinfo_rat_star);
		imageView1 = (ImageView) findViewById(R.id.jmsinfo_image_1);
		imageView2 = (ImageView) findViewById(R.id.jmsinfo_image_2);

		expandableListView = (ExpandableListView) findViewById(R.id.jmsinfo_explv);
		expandableListView.setAdapter(new MyExpandableListViewAdapter(
				JmsInfoActivity.this));
	}

	private void initdate() {
		groupArray = new ArrayList<String>();
		childArray = new ArrayList<List<String>>();
		
		
		
		addInfo("语言", new String[] { "Oracle", "Java", "Linux", "Jquery" });
		addInfo("男人的需求",
				new String[] { "金钱", "事业", "权力", "女人", "房子", "车", "球" });
	}

	class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
		Activity activity;

		public MyExpandableListViewAdapter(Activity a) {
			activity = a;
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

			String string = childArray.get(groupPosition).get(childPosition);

			return getGenericView(string);
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
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			String string = groupArray.get(groupPosition);
			return getGenericView(string);
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		private TextView getGenericView(String string) {
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			TextView textView = new TextView(activity);
			textView.setLayoutParams(layoutParams);

			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

			textView.setPadding(40, 0, 0, 0);
			textView.setText(string);
			return textView;
		}
	}

	private void addInfo(String group, String[] child) {

		groupArray.add(group);

		List<String> childItem = new ArrayList<String>();

		for (int index = 0; index < child.length; index++) {
			childItem.add(child[index]);
		}
		childArray.add(childItem);
	}
}
