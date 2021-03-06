package com.btten.hcb.rescue;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class RescueListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public RescueListItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new RescueListItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					RescueListItem temp = new RescueListItem();
					temp.name = convert.getString("NAME");
					temp.id = convert.getString("ID");
					temp.phone = convert.getString("PHONE");
					items[i] = temp;
				}
			}
		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			Log.d("gwjtag", info);
			return false;
		}
		return true;
	}

}
