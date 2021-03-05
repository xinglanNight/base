package com.xinlan.android.basesupport.net;

public class ApiConstants {

    public static boolean isTest = true;

    public static String TEST_URL = "";

    public static String FORMAL_URL = "";

    public static String BASE_URL = isTest ? TEST_URL :FORMAL_URL;

    public static String BASE_FILE_URL = "" ;//文件获取

    public static int TIME_OUT = 30;

}
