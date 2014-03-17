package com.btten.hcb.discuss;

import com.btten.hcbvip.R;
import com.btten.tools.InfoQuery;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DiscussListAdapter extends BaseAdapter {
	Activity context = null;
	DiscussListItem[] items;

	public DiscussListAdapter(Activity context, DiscussListItem[] items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder;
		LayoutInflater inflater = null;

		if (convertView == null) {
			vHolder = new ViewHolder();
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.discuss_activity, null);
			vHolder.txtName = (TextView) convertView
					.findViewById(R.id.discuss_list_item_name);
			vHolder.txtDate = (TextView) convertView
					.findViewById(R.id.discuss_list_item_date);
			vHolder.txtContent = (TextView) convertView
					.findViewById(R.id.discuss_list_item_content);
			vHolder.txtCount = (TextView) convertView
					.findViewById(R.id.discuss_list_item_count);

			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtName.setText(items[position].name);
		vHolder.txtDate.setText(items[position].date);
		vHolder.txtContent.setText(InfoQuery.ToDBC(items[position].content));
		vHolder.txtCount.setText("回复（" + items[position].count + ")");
		vHolder.id = items[position].id;
		
		return convertView;
	}

	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public static class ViewHolder {
		TextView txtName, txtContent, txtDate, txtCount;
		String id;
	}
}
