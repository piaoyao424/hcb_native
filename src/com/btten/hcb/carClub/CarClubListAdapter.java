package com.btten.hcb.carClub;

import com.btten.hcbvip.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CarClubListAdapter extends BaseAdapter {
	Activity context = null;
	CarClubListItem[] items;

	public CarClubListAdapter(Activity context, CarClubListItem[] items) {
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
			convertView = inflater.inflate(R.layout.car_club_list_item, null);
			vHolder.txtTitle = (TextView) convertView
					.findViewById(R.id.car_club_list_item_title);
			vHolder.txtAddr = (TextView) convertView
					.findViewById(R.id.car_club_list_item_addr);
			vHolder.txtInitiator = (TextView) convertView
					.findViewById(R.id.car_club_list_item_initiator);
			vHolder.txtDate = (TextView) convertView
					.findViewById(R.id.car_club_list_item_date);
			vHolder.txtParticipantNum = (TextView) convertView
					.findViewById(R.id.car_club_list_item_participant);
			vHolder.txtProcessType = (TextView) convertView
					.findViewById(R.id.car_club_list_item_processType);

			vHolder.imageView = (ImageView) convertView
					.findViewById(R.id.car_club_list_item_image);
			vHolder.imageViewType = (ImageView) convertView
					.findViewById(R.id.car_club_list_item_imageType);

			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}

		vHolder.txtTitle.setText(items[position].title);
		vHolder.txtAddr.setText(items[position].addr);
		vHolder.txtInitiator.setText(items[position].initiator);
		vHolder.txtDate.setText(items[position].startDate + "出发，共"
				+ items[position].totleDate + "天");
		vHolder.txtParticipantNum.setText("参与活动("
				+ items[position].participantNum + "/"
				+ items[position].totleNum + "人)");

		switch (items[position].processType) {
		case 1:
			vHolder.txtProcessType.setText("进行中...");
			break;
		case 0:
			vHolder.txtProcessType.setText("已结束...");
			break;
		default:
			break;
		}

		ImageLoader.getInstance().displayImage(items[position].image,
				vHolder.imageView);
		if (items[position].imageType == 1) {
			vHolder.imageViewType.setVisibility(View.VISIBLE);
		} else {
			vHolder.imageViewType.setVisibility(View.GONE);
		}

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
			Intent intent = new Intent(context, CarClubPartyInfoActivity.class);
			intent.putExtra("KEY_ID", items[index].id);
			context.startActivity(intent);
		}
	}

	@Override
	public int getCount() {
		if (items == null || items.length < 1) {
			return 0;
		}
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
		TextView txtTitle, txtInitiator, txtAddr, txtDate, txtParticipantNum,
				txtProcessType;
		ImageView imageView, imageViewType;
	}
}
