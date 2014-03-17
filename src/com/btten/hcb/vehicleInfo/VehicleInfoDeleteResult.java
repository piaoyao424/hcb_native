package com.btten.hcb.vehicleInfo;

import org.json.JSONObject;
import com.btten.model.BaseJsonItem;

public class VehicleInfoDeleteResult extends BaseJsonItem {
	public String price;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		VehicleInfoDeleteResult items = this;
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
