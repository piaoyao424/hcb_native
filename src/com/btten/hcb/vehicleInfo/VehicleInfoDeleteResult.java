package com.btten.hcb.vehicleInfo;

import org.json.JSONObject;
import com.btten.model.BaseJsonItem;

public class VehicleInfoDeleteResult extends BaseJsonItem {

	@Override
	public boolean CreateFromJson(JSONObject result) {
		VehicleInfoDeleteResult items = this;
		try {

			items.status = result.getInt("STATUS");
			items.info = result.getString("INFO");
 
		} catch (Exception e) {
			items.status = -1;
			items.info = e.toString();
			return false;
		}
		return true;
	}

}
