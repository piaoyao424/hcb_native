package com.btten.hcb.insurance;

import org.json.JSONObject;
import com.btten.model.BaseJsonItem;

public class InsuranceResult extends BaseJsonItem {
	public String price;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		InsuranceResult items = this;
		try {

			items.status = result.getInt("STATUS");
			items.info = result.getString("INFO");

			// 有数据
			if (items.status == 1) {
				price = result.getString("PRICE");
			}
		} catch (Exception e) {
			items.status = -1;
			items.info = e.toString();
			return false;
		}
		return true;
	}

}
