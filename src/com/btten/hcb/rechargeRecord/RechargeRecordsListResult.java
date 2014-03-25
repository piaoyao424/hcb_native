package com.btten.hcb.rechargeRecord;

import org.json.JSONArray;
import org.json.JSONObject;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class RechargeRecordsListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public float Points = 0;
	public RechargeRecordsListItem[] items;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			// 有数据
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				items = new RechargeRecordsListItem[jsonArray.length()];

				for (int i = 0; i < items.length; ++i) {
					RechargeRecordsListItem temp = new RechargeRecordsListItem();
					CommonConvert convert = new CommonConvert(
							jsonArray.getJSONObject(i));
					temp.dayStr = convert.getString("DATETIME")
							.substring(0, 10);
					temp.gotPoints = convert.getString("VALUE");
					Points += convert.getDouble("VALUE");
					items[i] = temp;
				}
			}
		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			return false;
		}
		return true;
	}

}
