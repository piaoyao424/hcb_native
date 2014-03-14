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

	public void setItems(CarClubListItem[] items) {
		this.items = items;
		this.notifyAll();
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
					.findViewById(R.id.book_list_item_title);
			vHolder.txtAddr = (TextView) convertView
					.findViewById(R.id.book_list_item_title);
			vHolder.txtInitiator = (TextView) convertView
					.findViewById(R.id.book_list_item_title);
			vHolder.txtDate = (TextView) convertView
					.findViewById(R.id.book_list_item_title);
			vHolder.txtParticipantNum = (TextView) convertView
					.findViewById(R.id.book_list_item_title);
			vHolder.txtProcessType = (TextView) convertView
					.findViewById(R.id.book_list_item_title);

			vHolder.imageView = (ImageView) convertView
					.findViewById(R.id.book_list_item_image);
			vHolder.imageViewType = (ImageView) convertView
					.findViewById(R.id.book_list_item_image);

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
		case 0:
			vHolder.txtProcessType.setText("进行中...");
			break;
		case 1:
			vHolder.txtProcessType.setText("已结束...");
			break;
		default:
			break;
		}

		ImageLoader.getInstance().displayImage(items[position].image,
				vHolder.imageView);
		vHolder.imageViewType.setVisibility(View.GONE);
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
