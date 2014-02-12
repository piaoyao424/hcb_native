package com.btten.hcb.pointRecords;

import com.btten.base.ListItemBase;
import com.btten.hcbvip.R;
import android.view.View;
import android.widget.TextView;

public class PointRecordsListItem extends ListItemBase {
	
	class ViewChild {
		public TextView tv_date;
		public TextView tv_type;
		public TextView tv_gotpoints;
		public TextView tv_usedpoints;
	}

	ViewChild Child = new ViewChild();

	public String gotPoints;
	public String usedPoints;
	public String dayStr;
	public String type;

	public PointRecordsListItem() {
		layoutId = R.layout.point_records_listitem;
	}

	@Override
	public void initView(View view) {
		Child.tv_date = (TextView) view
				.findViewById(R.id.myjiesuan_item_numdate);
		Child.tv_type = (TextView) view
				.findViewById(R.id.myjiesuan_item_huibipay);
		Child.tv_gotpoints = (TextView) view
				.findViewById(R.id.myjiesuan_item_cardpay);
		Child.tv_usedpoints = (TextView) view
				.findViewById(R.id.myjiesuan_item_totalmoney);

		Child.tv_date.setText(dayStr);
		Child.tv_type.setText(type);
		Child.tv_gotpoints.setText(gotPoints);
		Child.tv_usedpoints.setText(usedPoints);
	}
}
