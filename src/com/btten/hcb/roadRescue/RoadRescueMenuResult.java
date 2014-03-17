package com.btten.hcb.roadRescue;

import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class RoadRescueMenuResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public RoadRescueMenuItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new RoadRescueMenuItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					RoadRescueMenuItem temp = new RoadRescueMenuItem();

					temp.id = convert.getString("ID");
					temp.upid = convert.getString("UPID");
					temp.name = convert.getString("NAME");
					temp.oldprice = convert.getString("OLDPRICE");
					temp.newprice = convert.getString("NEWPRICE");
					temp.area = convert.getString("AREA");
					temp.phone = convert.getString("PHONE");
					
					items[i] = temp;
				}
			}
		} catch (Exception ex) {
			this.status = -1;
			this.info = ex.toString();
			Log.d("tag", info);
			return false;
		}
		return true;
	}

}
