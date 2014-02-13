package com.btten.hcb.pointRecord;

import org.json.JSONArray;
import org.json.JSONObject;

import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class PointRecordsListResult extends BaseJsonItem {
	private static String TAG = "MyConsumeResult";
	private JSONArray jsonArray = null;
	public float remainPoints = 0;
	public PointRecordsListItem[] items;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			// 有数据
			if (this.status == 1 && !result.isNull("DATA")) {
				this.jsonArray = result.getJSONArray("DATA");
				this.remainPoints = result.getLong("REMAINPOINTS");
				
				items = new PointRecordsListItem[this.jsonArray.length()];
				PointRecordsListItem temp;
				for (int i = 0; i < items.length; ++i) {
					temp = new PointRecordsListItem();
					CommonConvert convert = new CommonConvert(
							this.jsonArray.getJSONObject(i));

					temp.dayStr = convert.getString("DAYTIME");
					temp.gotPoints = convert.getString("GOTPOINTS");
					temp.usedPoints = convert.getString("USEDPOINTS");
					temp.type = convert.getString("TYPE");

					items[i] = temp;
				}

			}
		} catch (Exception e) {
			this.status = -1;
			this.info = e.toString();
			HcbAPP.getInstance();
			HcbAPP.ReportError(TAG + "error:\n" + e.toString() + "\nresult:\n"
					+ result.toString());
			Log.Exception(TAG, e);
			return false;
		}
		return true;
	}

}
