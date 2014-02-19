package com.btten.hcb.jmsInfo;

import org.json.JSONObject;
import android.util.Log;
import com.btten.model.BaseJsonItem;
import com.btten.tools.CommonConvert;

public class JmsInfoItems extends BaseJsonItem {
	public JmsInfoItem item = null;

	@Override
	public boolean CreateFromJson(JSONObject result) {
		try {
			this.status = result.getInt("STATUS");
			this.info = result.getString("INFO");
			if (this.status == 1) {
				item = new JmsInfoItem();
				CommonConvert convert = new CommonConvert(result);

				item.id = convert.getString("ID");
				item.jname = convert.getString("JNAME");
				item.jscope = convert.getString("JSCOPE");
				item.address = convert.getString("ADDRESS");
				item.phone = convert.getString("PHONE");
				item.commentNum = Integer.valueOf(convert
						.getString("COMMENTNUM"));
				item.star = Integer.valueOf(convert.getString("STAR"));
				item.images1 = convert.getString("IMAGES1");
				item.images2 = convert.getString("IMAGES2");
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
