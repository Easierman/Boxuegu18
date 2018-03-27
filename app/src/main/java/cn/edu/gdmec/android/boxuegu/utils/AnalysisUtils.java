package cn.edu.gdmec.android.boxuegu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shinelon on 2018/3/25.
 */

public class AnalysisUtils {
    /*
    * 从SharedPreferences中读取登录用户名
    * */
    public static String readloginUserName(Context context){
        SharedPreferences sp = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String userName = sp.getString("loginUserName","");
        return userName;
    }
}
