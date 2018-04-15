package cn.edu.gdmec.android.boxuegu.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shinelon on 2018/4/7.
 */

public class SQLiteHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION=1;
    public static String DB_NAME="bxg.db";
    public static final String U_USERINFO="userinfo";//个人资料

    public SQLiteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
/*
* 创建个人资料
* */
    db.execSQL("CREATE TABLE IF NOT EXISTS " + U_USERINFO + "("
    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
    + "userName VARCHAR,"
    + "nickName VARCHAR,"
    + "sex VARCHER,"
    + "signature VARCHAR"
    + ")");
    }
/*
* 当数据库版本号增加时才会调用此方法
* */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + U_USERINFO);
        onCreate(db);
    }
}
