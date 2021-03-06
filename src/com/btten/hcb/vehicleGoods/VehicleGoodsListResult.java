package com.btten.hcb.vehicleGoods;

import org.json.JSONArray;
import org.json.JSONObject;
import com.btten.model.BaseJsonItem;
import com.btten.network.UrlFactory;
import com.btten.tools.CommonConvert;

public class VehicleGoodsListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public VehicleGoodsListItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new VehicleGoodsListItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					VehicleGoodsListItem temp = new VehicleGoodsListItem();
					temp.title = convert.getString("gname");
					temp.id = convert.getString("gid");
					temp.content = convert.getString("promotion");
					temp.image1 = UrlFactory.rootUrl_short
							+ convert.getString("img");
					items[i] = temp;
				}
			}
		} catch (Exception ex) {
			this.status = -1;
			this.info = ex.toString();
			return false;
		}
		return true;
	}

}
