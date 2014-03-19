package com.btten.hcb.userInfo;

import org.json.JSONObject;
import android.util.Log;
import com.btten.model.BaseJsonItem;

public class UserInfoSubmitResult extends BaseJsonItem {

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
		} catch (Exception ex) {
			this.status = -1;
			this.info = ex.toString();
			Log.d("tag", info);
			return false;
		}
		return true;
	}

}
