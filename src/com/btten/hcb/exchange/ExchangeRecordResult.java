package com.btten.hcb.exchange;

import org.json.JSONArray;
import org.json.JSONObject;

import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;
import com.btten.tools.Log;

public class ExchangeRecordResult extends BaseJsonItem {
	private static String TAG = "MyConsumeResult";
	private JSONArray jsonArray = null;
	public ExchangeRecordListItem[] items;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			// 有数据
			if (this.status == 1) {
				this.jsonArray = result.getJSONArray("DATA");
				items = new ExchangeRecordListItem[this.jsonArray.length()];
				for (int i = 0; i < items.length; ++i) {
					ExchangeRecordListItem temp = new ExchangeRecordListItem();
					CommonConvert convert = new CommonConvert(
							this.jsonArray.getJSONObject(i));

					temp.date = convert.getString("DAYTIME");
					temp.title = convert.getString("GOTPOINTS");
					temp.name = convert.getString("USEDPOINTS");
					temp.jmsname = convert.getString("TYPE");

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
