package com.btten.hcb.vehicleInfo;

import org.json.JSONObject;
import com.btten.model.BaseJsonItem;

public class VehicleInfoSubmitResult extends BaseJsonItem {

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			return false;
		}
		return true;
	}

}
