package com.xinlan.android.basesupport.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据resume刷新工具类
 * @author Wuyson
 */
public class ResumeRefreshUtils {
	private static List<String> mRefreshNameList;

	/**
	 * 设置XX页面刷新
	 */
	public static void setPageRefresh(Class<?> pClass) {
		if (pClass == null) {
			return;
		}
		synchronized (ResumeRefreshUtils.class) {
			String className = pClass.getName();
			if (mRefreshNameList == null) {
				mRefreshNameList = new ArrayList<String>();
			}
			if (mRefreshNameList.contains(className)) {
				return;
			}
			mRefreshNameList.add(className);
		}
	}

	/**
	 * 获取某某页面是否刷新
	 */
	public static boolean getIsPageRefresh(Class<?> pClass) {
		if (pClass == null) {
			return false;
		}
		synchronized (ResumeRefreshUtils.class) {
			if (mRefreshNameList == null) {
				return false;
			}
			String className = pClass.getName();
			if (mRefreshNameList.contains(className)) {
				mRefreshNameList.remove(className);
				return true;
			}
			return false;
		}
	}

	/**
	 * 清除页面刷新
	 */
	public static void clearPageRefresh(Class<?> pClass) {
		if (pClass == null) {
			return;
		}
		synchronized (ResumeRefreshUtils.class) {
			if (mRefreshNameList == null) {
				return;
			}
			String className = pClass.getName();
			mRefreshNameList.remove(className);
		}
	}
}

