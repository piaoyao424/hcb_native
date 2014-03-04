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
				item.province = convert.getString("JNAME");
				item.city = convert.getString("JSCOPE");
				item.area = convert.getString("GPS_LO");
				item.car_model = convert.getString("GPS_LA");
				item.car_Num =  convert.getString("COMMENTNUM");
				item.car_date =  convert.getString("STAR");
				item.email = convert.getString("IMAGES1");
				item.username = convert.getString("IMAGES2");
				item.address = convert.getString("ADDRESS");
				item.phone = convert.getString("PHONE");
				item.consignee = convert.getString("PHONE");
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
