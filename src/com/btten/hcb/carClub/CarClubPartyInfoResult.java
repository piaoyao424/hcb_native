package com.btten.hcb.carClub;

import org.json.JSONObject;

import android.util.Log;

import com.btten.model.BaseJsonItem;
import com.btten.network.UrlFactory;

public class CarClubPartyInfoResult extends BaseJsonItem {
	CarClubListItem item;

	@Override
	public boolean CreateFromJson(JSONObject result) {

		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");

			if (this.status == 1) {
				item = new CarClubListItem();

				item.title = result.getString("wname");
				item.id = result.getString("wid");
				item.participantNum = result.getString("wapplypnum");
				item.totleNum = result.getString("wpnum");
				item.evalu = result.getString("wcomment");
				item.startDate = result.getString("wstarttime");
				item.totleDate = result.getString("sj");
				item.content = result.getString("wdetail")
						+ result.getString("witinerary");
				item.other = result.getString("wprecautions");
				item.partyType = result.getInt("cid");
				item.image = UrlFactory.rootUrl_short + "/"
						+ result.getString("attachment") + ".default.jpg";
				item.imageType = result.getInt("wmarrow");
				item.addr = result.getString("wplace");
				item.initiator = result.getString("uname");
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
