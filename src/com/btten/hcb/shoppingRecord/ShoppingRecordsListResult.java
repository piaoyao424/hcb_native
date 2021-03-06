package com.btten.hcb.shoppingRecord;

import org.json.JSONArray;
import org.json.JSONObject;

import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class ShoppingRecordsListResult extends BaseJsonItem {
	private static String TAG = "MyConsumeResult";
	private JSONArray jsonArray = null;
	public ShoppingRecordsListItem[] items;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			// 有数据
			if (this.status == 1) {
				this.jsonArray = result.getJSONArray("DATA");
				items = new ShoppingRecordsListItem[this.jsonArray.length()];
				ShoppingRecordsListItem temp;
				for (int i = 0; i < items.length; ++i) {
					temp = new ShoppingRecordsListItem();
					CommonConvert convert = new CommonConvert(
							this.jsonArray.getJSONObject(i));
					temp.date = convert.getString("TIME").substring(0,10);
					temp.money = convert.getString("MONEY");
					temp.count = convert.getString("COUNT");
					temp.id = convert.getString("ID");
					items[i] = temp;
				}
			}
		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			HcbAPP.getInstance();
			HcbAPP.ReportError(TAG + "error:\n" + e.toString() + "\nresult:\n"
					+ result.toString());
			Log.Exception(TAG, e);
			return false;
		}
		return true;
	}

}
