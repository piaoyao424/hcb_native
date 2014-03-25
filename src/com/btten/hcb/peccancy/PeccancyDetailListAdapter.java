package com.btten.hcb.peccancy;

import com.btten.hcbvip.R;
import com.btten.tools.InfoQuery;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PeccancyDetailListAdapter extends BaseAdapter {
	Activity context = null;
	PeccancyDetailListItem[] items;

	public PeccancyDetailListAdapter(Activity context,
			PeccancyDetailListItem[] items) {
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
			convertView = inflater.inflate(R.layout.peccancy_detail_item, null);
			vHolder.txtdate = (TextView) convertView
					.findViewById(R.id.peccancy_detail_item_date);
			vHolder.txtAddr = (TextView) convertView
					.findViewById(R.id.peccancy_detail_item_addr);
			vHolder.txtContent = (TextView) convertView
					.findViewById(R.id.peccancy_detail_item_content);
			vHolder.txtMoney = (TextView) convertView
					.findViewById(R.id.peccancy_detail_item_money);
			vHolder.txtPoint = (TextView) convertView
					.findViewById(R.id.peccancy_detail_item_point);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtdate.setText(items[position].date);
		vHolder.txtAddr.setText(items[position].addr);
		vHolder.txtContent.setText(InfoQuery.ToDBC(items[position].content));
		vHolder.txtMoney.setText(items[position].money);
		vHolder.txtPoint.setText(items[position].point);

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

	public class ViewHolder {
		TextView txtdate, txtContent, txtAddr, txtMoney, txtPoint;
	}
}
