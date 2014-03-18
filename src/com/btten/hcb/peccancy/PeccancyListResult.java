package com.btten.hcb.peccancy;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class PeccancyListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public PeccancyListItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new PeccancyListItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					PeccancyListItem temp = new PeccancyListItem();
					temp.carNum = convert.getString("F4_4249");
					temp.id = convert.getString("ID");
					temp.peccancyNum = convert.getString("F3_4250");
					temp.point = convert.getString("F5_4250");
					temp.money = convert.getString("F4_4250");
					temp.checkDate = convert.getString("F6_4250");
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
