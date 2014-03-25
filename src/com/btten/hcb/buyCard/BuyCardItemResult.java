package com.btten.hcb.buyCard;

import org.json.JSONArray;
import org.json.JSONObject;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class BuyCardItemResult extends BaseJsonItem {
	public BuyCardItem[] item;
	private JSONArray jsonArray;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				item = new BuyCardItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					BuyCardItem temp = new BuyCardItem();
					temp.name = convert.getString("NAME");
					temp.id = convert.getString("ID");
					temp.price = convert.getDouble("PRICE");
					temp.value = convert.getDouble("VALUE1");
					item[i] = temp;
				}
			}
		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			return false;
		}
		return true;
	}

}
