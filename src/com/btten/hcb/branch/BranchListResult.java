package com.btten.hcb.branch;

import org.json.JSONArray;
import org.json.JSONObject;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class BranchListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public BranchListItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new BranchListItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					BranchListItem temp = new BranchListItem();
					temp.title = convert.getString("F1_4007");
					temp.address = convert.getString("F11_4007");
					temp.phone = convert.getString("F13_4007");
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
