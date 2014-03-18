package com.btten.hcb.vehicleInfo;

import org.json.JSONObject;
import com.btten.model.BaseJsonItem;

public class VehicleInfoResult extends BaseJsonItem {
	VehicleInfoItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {

			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				item = new VehicleInfoItem();

				item.id = result.getString("ID");
				item.type = result.getString("F2_4249");
				item.area = result.getString("F3_4249");
				item.frame = result.getString("F5_4249");
				item.date = result.getString("F6_4249");
				item.carNo = result.getString("F4_4249");
				item.drivingLicence = result.getString("F8_4249");
				item.name = result.getString("F7_4249");
				item.fileNo = result.getString("F9_4249");
			}

		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			return false;
		}
		return true;
	}

}
