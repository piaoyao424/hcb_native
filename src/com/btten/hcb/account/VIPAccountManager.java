package com.btten.hcb.account;

import com.btten.hcb.HcbAPP;
import com.btten.tools.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class VIPAccountManager {

	private static final String PREFS_NAME = "user_info";
	private static final String UID_KEY = "userid";
	private static final String NICKNAME_KEY = "nickname";
	private static final String USERNAME_KEY = "username";
	private static final String USERIMAGE_KEY = "userimage";
	private static final String USERPHONE_KEY = "userphone";
	private static final String USERCARNUM_KEY = "usercarnum";
	private static final String USERTYPE_KEY = "usertype";
	// 加盟商-end
	private String userid;
	private String username;
	private String userimage;
	private String usercarnum;
	private String userphone; // 用户手机号
	private String areaID = "261";
	private int usertype;
	private Double gps_la = 30.711399;
	private Double gps_lo = 111.329899;

	private String nickname;
	// user
	SharedPreferences settings = HcbAPP.getInstance().getSharedPreferences(
			PREFS_NAME, Context.MODE_PRIVATE);
	SharedPreferences.Editor editor = settings.edit();

	/**
	 * 设置当前用户车牌号
	 */
	public void setCarnum(String carnum) {
		this.usercarnum = carnum;
		editor.putString(USERCARNUM_KEY, usercarnum).commit();
	}

	public void setGps(Double la, Double lo) {
		this.gps_la = la;
		this.gps_lo = lo;
	}

	public Double getGpsla() {
		return this.gps_la;
	}

	public Double getGpslo() {
		return this.gps_lo;
	}

	/**
	 * 获取当前用户车牌号
	 */
	public String getCarnum() {
		return usercarnum;
	}

	/**
	 * 设置用户手机号
	 */
	public void setUserPhone(String phone) {
		this.userphone = phone;
		editor.putString(USERPHONE_KEY, userphone).commit();
	}

	/**
	 * 设置用户类型
	 * 
	 * @param type
	 */
	public void setUserType(int type) {
		this.usertype = type;
		editor.putInt(USERTYPE_KEY, type).commit();
	}

	public int getUserType() {
		return usertype;
	}

	/**
	 * 获取缓存的用户手机号
	 */
	public String getUserPhone() {

		return userphone;
	}

	public void setUsername(String username) {
		this.username = username;
		editor.putString(USERNAME_KEY, username).commit();
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
		editor.putString(NICKNAME_KEY, nickname).commit();
	}

	public String getUserImage() {
		return userimage;
	}

	public String getUserid() {
		return userid;
	}

	public String getUsername() {
		return username;
	}

	public String getNickname() {
		if (Util.IsEmpty(nickname))
			return HcbAPP.getInstance().GetAnonymousName();
		return nickname;
	}

	public void SetInfo(String username, String userid, String nickname,
			String userimage) {
		this.username = username;
		this.nickname = nickname;
		this.userid = userid;
		this.userimage = userimage;

		editor.putString(UID_KEY, userid);
		editor.putString(NICKNAME_KEY, nickname);
		editor.putString(USERNAME_KEY, username);
		editor.putString(USERIMAGE_KEY, userimage);
		editor.commit();

	}

	public void setPhoto(String userimage) {
		this.userimage = userimage;
		editor.putString(USERIMAGE_KEY, userimage);
		editor.commit();
	}

	private VIPAccountManager() {
		SharedPreferences jmsUserInfo = HcbAPP.getInstance()
				.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		userid = jmsUserInfo.getString(UID_KEY, "");
		username = jmsUserInfo.getString(USERNAME_KEY, "");
		nickname = jmsUserInfo.getString(NICKNAME_KEY, "");
		userimage = jmsUserInfo.getString(USERIMAGE_KEY, "");
		userphone = jmsUserInfo.getString(USERPHONE_KEY, "");
		usercarnum = jmsUserInfo.getString(USERCARNUM_KEY, "");
		usertype = jmsUserInfo.getInt(USERTYPE_KEY, 5);
	}

	private static VIPAccountManager instance;

	public static VIPAccountManager getInstance() {
		if (instance == null) {
			instance = new VIPAccountManager();
		}
		return instance;
	}

	// 是否登录成功
	public boolean IsLogin() {
		if (userid == null || userid.length() <= 0)
			return false;
		return true;
	}

	public void Logout() {
		SetInfo("", "", "", "");
	}

	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}

	public String getAreaID() {
		return areaID;
	}
}
