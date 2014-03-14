package com.btten.hcb.carClub;

import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;

public class CarClubPartyInfoResult extends BaseJsonItem {
	CarClubListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			if (this.status == 1) {
				item = new CarClubListItem();
				
				item.title = result.getString("F2_4230");
				item.id = result.getString("F1_4230");
				
				item.participantNum = result.getString("F4_4230");
				item.totleNum = result.getString("F4_4230");
				
				item.startDate = result.getString("F4_4230");
				item.totleDate = result.getString("F4_4230");
				
				item.content = result.getString("F4_4230");
				item.other = result.getString("F4_4230");
				item.processType = result.getInt("F4_4230");
				item.partyType = result.getInt("F4_4230");

				item.image = result.getString("F4_4230");
				item.imageType = result.getInt("F4_4230");
				
				item.addr = result.getString("F4_4230");
				item.initiator = result.getString("F4_4230");
				
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
