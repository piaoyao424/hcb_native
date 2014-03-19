package com.btten.hcb.tools.areaInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class ProvinceListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public ProvinceListItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new ProvinceListItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					ProvinceListItem temp = new ProvinceListItem();
					temp.name = convert.getString("NAME");
					temp.id = convert.getString("ID");
					// 过滤掉直辖市
					if (temp.name.equals("北京")) {
					} else if (temp.name.equals("上海")) {
					} else if (temp.name.equals("重庆")) {
					} else if (temp.name.equals("天津")) {
					} else {
						items[i] = temp;
					}

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
