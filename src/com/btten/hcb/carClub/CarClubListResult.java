package com.btten.hcb.carClub;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.network.UrlFactory;
import com.btten.tools.CommonConvert;

public class CarClubListResult extends BaseJsonItem {
	private JSONArray jsonArray = null;
	public CarClubListItem[] items = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				jsonArray = result.getJSONArray("DATA");
				int length = jsonArray.length();
				items = new CarClubListItem[length];
				for (int i = 0; i < length; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					CommonConvert convert = new CommonConvert(obj);
					CarClubListItem temp = new CarClubListItem();

					temp.title = convert.getString("wname");
					temp.id = convert.getString("wid");
					temp.image = UrlFactory.rootUrl_short + "/"
							+ convert.getString("attachment") + ".thumb.jpg";
					temp.imageType = convert.getInt("wmarrow");
					temp.initiator = convert.getString("uname");
					temp.addr = convert.getString("wplace");
					temp.startDate = convert.getString("wstarttime");
					temp.totleDate = convert.getString("sj");
					temp.partyType = convert.getInt("cid");
					temp.totleNum = convert.getString("wpnum");
					temp.participantNum = convert.getString("wapplypnum");
					temp.processType = convert.getInt("wzt");

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
