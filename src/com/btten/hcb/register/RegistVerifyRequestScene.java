package com.btten.hcb.register;

import org.json.JSONObject;

import com.btten.hcb.HcbAPP;
import com.btten.model.BaseJsonItem;
import com.btten.network.NomalJsonSceneBase;
import com.btten.network.OnSceneCallBack;
import com.btten.network.ThreadPoolUtils;
import com.btten.network.UrlFactory;
import com.btten.tools.Log;

public class RegistVerifyRequestScene extends NomalJsonSceneBase{
	
	public void doScene(OnSceneCallBack callBack, String phone){
		SetCallBack(callBack);
		targetUrl = UrlFactory.GetUrlNew("Message",
										 "sendMessage",
										 "TeleNum", phone);
		ThreadPoolUtils.execute(this);
		Log.i("yexinTAG", targetUrl);
	}
	
	@Override
	protected BaseJsonItem CreateJsonItems() {
		// TODO Auto-generated method stub
		return new VerifyCode();
	}
	
	private class VerifyCode extends BaseJsonItem{
		private static final String TAG = "RegistVerifyCodeRequest";
		
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
