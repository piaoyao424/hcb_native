package com.btten.hcb.userInfo;

import org.json.JSONObject;
import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class UserInfoResult extends BaseJsonItem {
	public UserInfoItem item = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				item = new UserInfoItem();
				CommonConvert convert = new CommonConvert(result);

				item.id = convert.getString("ID");
				item.province = convert.getString("F1_4015_1");
				item.city = convert.getString("F2_4015_1");
				item.area = convert.getString("F3_4015_1");
				item.provinceid = convert.getString("F1_4015");
				item.cityid = convert.getString("F2_4015");
				item.areaid = convert.getString("F3_4015");
				item.email = convert.getString("EMAIL");
				item.username = convert.getString("NAME");
				item.address = convert.getString("F4_4015");
				item.phone = convert.getString("F5_4015");
				item.consignee = convert.getString("F6_4015");
				item.gerder = convert.getInt("GERDER");
			}
		} catch (Exception ex) {
			this.status = -1;
			this.info = ex.toString();
			Log.d("tag", info);
			return false;
		}
		return true;
	}

}
