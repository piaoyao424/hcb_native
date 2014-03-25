package com.btten.hcb.party;

import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;

public class PartyInfoResult extends BaseJsonItem {
	MyPartyListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			if (this.status == 1) {
				item = new MyPartyListItem();
				item.title = result.getString("F2_4230");
				item.id = result.getString("F1_4230");
				item.type = result.getString("F4_4230");
				item.startDate = result.getString("F4_4230");
				item.totleDate = result.getString("F4_4230");
				item.process = result.getString("F4_4230");
				item.other = result.getString("F4_4230");
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
