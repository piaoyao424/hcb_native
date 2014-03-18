package com.btten.hcb.peccancy;

import com.btten.hcb.vehicleInfo.VehicleInfoActivity;
import com.btten.hcbvip.R;
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

public class PeccancyListAdapter extends BaseAdapter {
	Activity context = null;
	PeccancyListItem[] items;

	public PeccancyListAdapter(Activity context, PeccancyListItem[] items) {
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
			convertView = inflater.inflate(R.layout.peccancy_list_item, null);
			vHolder.txtCarNum = (TextView) convertView
					.findViewById(R.id.peccancy_item_carnum);
			vHolder.txtCheckDate = (TextView) convertView
					.findViewById(R.id.peccancy_item_date);
			vHolder.txtContent = (TextView) convertView
					.findViewById(R.id.peccancy_item_content);
			vHolder.btnCarInfo = (Button) convertView
					.findViewById(R.id.peccancy_item_car_info);
			vHolder.btnPeccancy = (Button) convertView
					.findViewById(R.id.peccancy_item_search);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtCarNum.setText(items[position].carNum);
		vHolder.txtCheckDate.setText(items[position].checkDate);
		vHolder.txtContent.setText("违章" + items[position].peccancyNum
				+ " 次   罚款 " + items[position].money + " 元   扣"
				+ items[position].point + "分");

		vHolder.btnCarInfo.setOnClickListener(new adapterListner(position));
		vHolder.btnPeccancy.setOnClickListener(new adapterListner(position));
		return convertView;
	}

	class adapterListner implements OnClickListener {
		int index;

		public adapterListner(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			Intent intent = null;
			switch (v.getId()) {
			case R.id.peccancy_item_car_info:
				intent = new Intent(context, VehicleInfoActivity.class);
				intent.putExtra("KEY_ID", items[index].id);
				break;
			case R.id.peccancy_item_search:
				intent = new Intent(context, PeccancyDetailActivity.class);
				intent.putExtra("KEY_ID", items[index].id);
				intent.putExtra("KEY_DATE", items[index].checkDate);
				break;

			default:
				break;
			}
			if (intent == null) {

			} else {
				context.startActivity(intent);
			}

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
		TextView txtCarNum, txtContent, txtCheckDate;
		Button btnCarInfo, btnPeccancy;
	}
}
