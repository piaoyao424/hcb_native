package com.btten.hcb.shoppingRecord;

import com.btten.base.ListItemBase;
import com.btten.hcbvip.R;
import android.view.View;
import android.widget.TextView;

public class ShoppingRecordInfoItem extends ListItemBase {

	class ViewChild {
		public TextView tv_date;
		public TextView tv_gotpoints;
	}

	ViewChild Child = new ViewChild();

	public String gotPoints;
	public String dayStr;

	public ShoppingRecordInfoItem() {
		layoutId = R.layout.recharge_record_listitem;
	}

	@Override
	public void initView(View view) {
		Child.tv_date = (TextView) view
				.findViewById(R.id.recharge_records_listitem_date);
		Child.tv_gotpoints = (TextView) view
				.findViewById(R.id.recharge_records_listitem_gotpoints);

		Child.tv_date.setText(dayStr);
		Child.tv_gotpoints.setText(gotPoints);
	}
}
