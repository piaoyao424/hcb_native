package com.btten.hcb.shoppingRecord;

import com.btten.base.ListItemAdapter;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

public class RechargeRecordsListAdapter extends ListItemAdapter {

	public RechargeRecordsListAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(items[position].getItemLayout(),
					null);
		items[position].initView(convertView);
		return convertView;
	}
}
