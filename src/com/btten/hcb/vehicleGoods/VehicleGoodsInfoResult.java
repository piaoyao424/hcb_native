package com.btten.hcb.vehicleGoods;

import org.json.JSONObject;

import android.util.Log;
import com.btten.model.BaseJsonItem;

public class VehicleGoodsInfoResult extends BaseJsonItem {
	VehicleGoodsListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			if (this.status == 1) {
				item = new VehicleGoodsListItem();
				item.title = result.getString("F2_4230");
				item.id = result.getString("F1_4230");
				item.content = result.getString("F4_4230");
				item.image1 = result.getString("F4_4230");
				item.image2 = result.getString("F4_4230");
				item.image3 = result.getString("F4_4230");
				item.price = result.getString("F4_4230");
				item.discount = result.getString("F4_4230");
				item.type = result.getString("F4_4230");
			}
		} catch (Exception ex) {
			this.status = -1;
			this.info = ex.toString();
			Log.d("gwjtag", info);
			return false;
		}
		return true;
	}

}
