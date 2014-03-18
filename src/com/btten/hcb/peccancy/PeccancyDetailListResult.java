package com.btten.hcb.peccancy;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class PeccancyDetailListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public PeccancyDetailListItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new PeccancyDetailListItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					PeccancyDetailListItem temp = new PeccancyDetailListItem();
					temp.date = convert.getString("F3_4251");
					temp.id = convert.getString("ID");
					temp.content = convert.getString("F5_4251");
					temp.addr = convert.getString("F4_4251");
					temp.point = convert.getString("F6_4251");
					temp.money = convert.getString("F7_4251");
					items[i] = temp;
				}
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
