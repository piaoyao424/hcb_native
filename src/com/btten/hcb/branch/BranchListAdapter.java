package com.btten.hcb.branch;

import com.btten.hcbvip.R;
import com.btten.vincenttools.CallTelephone;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BranchListAdapter extends BaseAdapter {
	Activity context = null;
	BranchListItem[] items;

	public BranchListAdapter(Activity context, BranchListItem[] items) {
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
			convertView = inflater.inflate(R.layout.branch_activity_item, null);
			vHolder.txtTitle = (TextView) convertView
					.findViewById(R.id.branch_item_title);
			vHolder.txtAddr = (TextView) convertView
					.findViewById(R.id.branch_item_address);
			vHolder.txtPhone = (TextView) convertView
					.findViewById(R.id.branch_item_phone);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtTitle.setText(items[position].title);
		vHolder.txtAddr.setText(items[position].address);
		vHolder.txtPhone.setText(items[position].phone);
		final int i = position;
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new CallTelephone(context, items[i].phone, items[i].title)
						.call();

			}
		});
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
		TextView txtTitle, txtAddr, txtPhone;
	}
}
