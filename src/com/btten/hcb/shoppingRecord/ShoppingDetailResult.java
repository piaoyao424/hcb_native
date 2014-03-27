package com.btten.hcb.shoppingRecord;

import org.json.JSONArray;
import org.json.JSONObject;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class ShoppingDetailResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public double points = 0;
	public double money = 0;
	public String title, saleNum;
	public ShoppingDetailItem[] items;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			// 有数据
			if (this.status == 1) {
				this.jsonArray = result.getJSONArray("DATA");
				items = new ShoppingDetailItem[this.jsonArray.length()];
				ShoppingDetailItem temp;
				for (int i = 0; i < items.length; ++i) {
					temp = new ShoppingDetailItem();
					CommonConvert convert = new CommonConvert(
							this.jsonArray.getJSONObject(i));

					temp.name = convert.getString("NAME");
					temp.money = convert.getString("MONEY");

					points += convert.getDouble("POINT");
					money += convert.getDouble("MONEY");
					items[i] = temp;
					if (i == 1) {
						title = convert.getString("JNAME");
						saleNum = convert.getString("ID");
					}
				}
			}

		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			Log.Exception("", e);
			return false;
		}
		return true;
	}

}
