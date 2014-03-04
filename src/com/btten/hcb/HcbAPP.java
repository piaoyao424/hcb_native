package com.btten.hcb;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import com.btten.base.BaseActivity;
import com.btten.msgcenter.MsgCenter;
import com.btten.tools.Log;
import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.analytics.MobclickAgent;

public class HcbAPP extends Application {
	public static HcbAPP mainApp;
	public static ArrayList<Activity> mActivityList = new ArrayList<Activity>();

	public HcbAPP() {
		mainApp = this;
		MsgCenter.getInstance();
		Log.setOutputPath(Log.SDCARD_LOG_PATH, Log.SYS_LOG, 0);
	}

	public static HcbAPP getInstance() {
		return mainApp;
	}

	public int GetVersionCode() {
		String pName = "com.btten.hcb";
		int versionCode = 0;
		try {
			PackageInfo pinfo = this.getPackageManager().getPackageInfo(pName,
					PackageManager.GET_CONFIGURATIONS);
			versionCode = pinfo.versionCode;
		} catch (Exception e) {
		}
		return versionCode;
	}

	public String GetVersion() {
		String pName = "com.btten.hcb";
		String versionName = null;
		try {
			PackageInfo pinfo = this.getPackageManager().getPackageInfo(pName,
					PackageManager.GET_CONFIGURATIONS);
			versionName = pinfo.versionName;
		} catch (Exception e) {
		}
		return versionName;
	}

	public String GetVersionStr() {
		return "" + GetVersionCode();
	}

	public String GetAnonymousName() {
		return "未命名";
	}

	public int GetDpType() {
		Activity top = GetTopActivity();
		if (top == null)
			return 1;

		DisplayMetrics dm = new DisplayMetrics();
		top.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		screenWidth /= 3;
		if (screenWidth > 200)
			return 2;
		if (screenWidth > 150)
			return 1;
		return 0;
	}

	// 上报错误
	public static void ReportError(String error) {
		Activity topactivity = GetTopActivity();
		if (topactivity == null)
			return;
		MobclickAgent.reportError(topactivity, error);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "hcb/Cache");

		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPoolSize(4)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// .memoryCacheSize(20000000) // 20 Mb
				.memoryCache(new FIFOLimitedMemoryCache(5 * 1024 * 1024))
				.discCache(
						new TotalSizeLimitedDiscCache(cacheDir,
								new Md5FileNameGenerator(), 20 * 1024 * 1024))
				// 能缓�?0M文件
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator());

		if (Log.IsDebug()) {
			builder.enableLogging();
		}

		ImageLoaderConfiguration config = builder.build();
		ImageLoader.getInstance().init(config);

	}

	private class RepairInfo {
		Date startTime;
		Date endTime;
		String info;

		public RepairInfo() {

		}

		/*
		 * <repair><starttime>2012-01-09 02:03:04</starttime> endtime>2012-01-09
		 * 02:03:04</endtime> <info>维修信息</info> </repair>
		 */
		public Boolean Parase(String repairInfo) {
			final Map<String, String> values = com.btten.tools.Util.parseXml(
					repairInfo, "repair", null);
			if (values == null)
				return false;
			String strStartTime = values.get(".repair.starttime");
			String strEndTime = values.get(".repair.endtime");
			info = values.get(".repair.info");
			if (info == null || info.length() <= 0)
				return false;

			startTime = getTime(strStartTime);
			endTime = getTime(strEndTime);

			if (startTime == null || endTime == null)
				return false;
			return true;
		}

		// 是否在维�?
		public Boolean IsInTime() {
			Date now = new Date();
			if (startTime == null || endTime == null)
				return false;

			if (now.getTime() >= startTime.getTime()
					&& now.getTime() <= endTime.getTime())
				return true;
			return false;
		}

		@SuppressLint("SimpleDateFormat")
		private Date getTime(String str) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = format.parse(str);
			} catch (Exception e) {
				return null;
			}
			return date;
		}
	}

	public static String GetSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	@Override
	public void onTerminate() {
		System.out.println("程序退出了");
		super.onTerminate();
	}

	// 得到最新的Activity
	public static Activity GetTopActivity() {
		if (HcbAPP.mActivityList.size() <= 0)
			return null;
		return HcbAPP.mActivityList.get(HcbAPP.mActivityList.size() - 1);
	}

	/**
	 * 清空Activity缓存
	 */
	public void ClearAllActivity() {
		if (HcbAPP.mActivityList.size() <= 0)
			return;
		for (int i = 0; i < HcbAPP.mActivityList.size(); ++i) {
			HcbAPP.mActivityList.get(i).finish();
		}
		HcbAPP.mActivityList.clear();
	}

	/**
	 * 清空页面缓存，保留当前activity
	 */
	public void ClearOtherActivity() {
		if (HcbAPP.mActivityList.size() <= 0)
			return;
		int size = HcbAPP.mActivityList.size();
		for (int i = 0; i < size - 1; i++)
			HcbAPP.mActivityList.get(i).finish();
	}

	public void exit() {
		ClearAllActivity();
		System.exit(0);
	}
}
