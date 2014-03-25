package com.btten.hcb.vehicleGoods;

import org.json.JSONObject;

import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.network.UrlFactory;

public class VehicleGoodsInfoResult extends BaseJsonItem {
	VehicleGoodsListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			if (this.status == 1) {
				item = new VehicleGoodsListItem();
				item.title = result.getString("gname");
				item.id = result.getString("gid");
				item.content = result.getString("content");
				item.image1 = UrlFactory.rootUrl_short
						+ result.getString("img");
				item.price = result.getString("price");
				item.discount = result.getString("zk");
				item.oldprice = result.getString("oprice");
				item.type = result.getString("appliance");
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
