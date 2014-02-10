package com.btten.hcb.register;

import org.json.JSONObject;

import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.btten.tools.Log;

public class RegistVerifyScene extends NomalJsonSceneBase{
	
	public void doScene(OnSceneCallBack callBack, String phone, String code){
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("Message",
										 "checkMessage",
										 "TeleNum", phone,
										 "indentCode", code);
		ThreadPoolUtils.execute(this);
		Log.i("yexinTAG", targetUrl);
	}
	
	@Override
	protected BaseJsonItem CreateJsonItems() {
		return new VerifyCode();
	}
	
	private class VerifyCode extends BaseJsonItem{
		private static final String TAG = "RegistVerify";
		
		@Override
		public boolean CreateFromJson(JSONObject result) {
			// TODO Auto-generated method stub
			try {
				status = result.getInt("STATUS");
				info = result.getString("INFO");
			}
			catch (Exception e) {
				status=-1;
				info=e.toString();
				HcbAPP.getInstance().ReportError(TAG+"error:\n"+e.toString()+"\nresult:\n"+result.toString());
				Log.Exception(TAG, e);
				return false;
			}
			return true;
		}
	}
	
}
