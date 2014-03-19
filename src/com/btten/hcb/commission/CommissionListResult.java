package com.btten.hcb.commission;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class CommissionListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public CommissionListItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new CommissionListItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					CommissionListItem temp = new CommissionListItem();
					temp.jmsName = convert.getString("NAME");
					temp.id = convert.getString("ID");
					temp.contacts = convert.getString("CONTACTS");
					temp.addr = convert.getString("ADDR");
					temp.phone = convert.getString("PHONE");
					temp.scope = convert.getString("SCOPE");
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
