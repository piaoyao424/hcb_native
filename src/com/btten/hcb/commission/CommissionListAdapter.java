package com.btten.hcb.commission;

import com.btten.hcb.vehicleInfo.VehicleInfoActivity;
import com.btten.hcbvip.R;
import com.btten.vincenttools.CallTelephone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CommissionListAdapter extends BaseAdapter {
	Activity context = null;
	CommissionListItem[] items;

	public CommissionListAdapter(Activity context, CommissionListItem[] items) {
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
			convertView = inflater.inflate(R.layout.commission_item, null);
			vHolder.txtjmsName = (TextView) convertView
					.findViewById(R.id.commission_item_jmsname);
			vHolder.txtAddr = (TextView) convertView
					.findViewById(R.id.commission_item_addr);
			vHolder.txtcontacts = (TextView) convertView
					.findViewById(R.id.commission_item_contacts);
			vHolder.txtPhone = (TextView) convertView
					.findViewById(R.id.commission_item_phone);
			vHolder.txtscope = (TextView) convertView
					.findViewById(R.id.commission_item_scope);

			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtjmsName.setText(items[position].jmsName);
		vHolder.txtAddr.setText(items[position].addr);
		vHolder.txtcontacts.setText(items[position].contacts);
		vHolder.txtPhone.setText(items[position].phone);
		vHolder.txtscope.setText("服务项目：" + items[position].scope);

		convertView.setOnClickListener(new adapterListner(position));
		return convertView;
	}

	class adapterListner implements OnClickListener {
		int index;

		public adapterListner(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			new CallTelephone(context, items[index].phone, items[index].jmsName).call();
		}
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
		TextView txtjmsName, txtAddr, txtcontacts, txtscope, txtPhone;
	}
}
