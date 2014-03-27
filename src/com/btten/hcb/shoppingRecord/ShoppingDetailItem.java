package com.btten.hcb.shoppingRecord;

import com.btten.base.ListItemBase;
import com.btten.hcbvip.R;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class ShoppingDetailItem extends ListItemBase {

	class ViewChild {
		public TextView tv_name;
		public TextView tv_money;
	}

	ViewChild Child = new ViewChild();

	public String name;
	public String money;

	public ShoppingDetailItem() {
		layoutId = R.layout.shopping_detail_listitem;
	}

	@Override
	public void initView(View view, Context context) {
		Child.tv_name = (TextView) view
				.findViewById(R.id.shopping_detail_listitem_name);
		Child.tv_money = (TextView) view
				.findViewById(R.id.shopping_detail_listitem_money);

		Child.tv_name.setText(name);
		Child.tv_money.setText(money);
	}
}
