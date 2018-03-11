package cn.edu.gdmec.android.boxuegu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Shinelon on 2018/3/11.
 */

public class MD5Utils {
/*
* md5加密的算法
* */
    public static String md5(String text){
        try {
            MessageDigest digest = MessageDigest.getInstance("ms5");
            byte[] resullt = digest.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b :resullt){
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1){
                    sb.append("0" + hex);
                }else{
                    sb.append(hex);
                }
            }
            return  sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
