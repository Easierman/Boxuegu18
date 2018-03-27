package cn.edu.gdmec.android.boxuegu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.view.MyInfoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;
    private FrameLayout mBodyLayout;
    private LinearLayout mBottomLayout;
    private RelativeLayout mCourseBtn;
    private RelativeLayout mExercisesBtn;
    private RelativeLayout mMyInfoBtn;
    private TextView mTv_course;
    private TextView tv_course;
    private TextView tv_exercises;
    private TextView tv_myInfo;
    private ImageView iv_course;
    private ImageView iv_exercises;
    private ImageView iv_myinfo;
    private MyInfoView mMyInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initBottomBar();
        setListener();
        setInitStatus();
    }
/*
*
*
* */
    private void setInitStatus() {
        clearBottomImageState();
        setSelectedStatus(0);
        createView(0);
    }

    /*
    * 设置底部三个按钮的点击监听事件
    * */
    private void setListener() {
        for (int i=0;i<mBottomLayout.getChildCount();i++){
            mBottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    private void initBottomBar() {
        mBottomLayout = (LinearLayout) findViewById(R.id.main_bottom_bar);
        mCourseBtn = (RelativeLayout) findViewById(R.id.bottom_bar_course_btn);
        mExercisesBtn = (RelativeLayout) findViewById(R.id.bottom_bar_exercises_btn);
        mMyInfoBtn = (RelativeLayout) findViewById(R.id.bottom_bar_myinfo_btn);

        tv_course = (TextView) findViewById(R.id.bottom_bar_text_course);
        tv_exercises = (TextView) findViewById(R.id.bottom_bar_text_exercises);
        tv_myInfo = (TextView) findViewById(R.id.bottom_bar_text_myinfo);

        iv_course = (ImageView) findViewById(R.id.bottom_bar_image_course);
        iv_exercises = (ImageView) findViewById(R.id.bottom_bar_image_exercises);
        iv_myinfo = (ImageView) findViewById(R.id.bottom_bar_image_my);
    }
/*
* 获取界面UI控件
* */
    private void init() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("博学谷课程");
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#3084FF"));
        tv_back.setVisibility(View.GONE);
        initBodyLayout();
    }

    private void initBodyLayout() {
        mBodyLayout = (FrameLayout) findViewById(R.id.main_body);
    }
/*
*控件的点击事件
* */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //课程的点击事件
            case R.id.bottom_bar_course_btn:
            clearBottomImageState();
            selectDisplayView(0);
            break;
            //习题的点击事件
            case R.id.bottom_bar_exercises_btn:
                clearBottomImageState();
                selectDisplayView(1);
                break;
            //我的点击事件
            case R.id.bottom_bar_myinfo_btn:
                clearBottomImageState();
                selectDisplayView(2);
                break;
        }
    }

    private void selectDisplayView(int index) {
        removeAllView();
        createView(index);
        setSelectedStatus(index);
    }
/*
* 选择视图
* */
    private void createView(int viewindex) {
        switch (viewindex){
            case 0:
                //1000:课程界面
                break;
            case 1:
                //习题界面
                break;
            case 2:
                //我的界面
                if (mMyInfoView == null){
                    mMyInfoView = new MyInfoView(this);
                    mBodyLayout.addView(mMyInfoView.getView());
                }else{
                    mMyInfoView.getView();
                }
                mMyInfoView.showView();
                break;
        }
    }
/*
*设置底部按钮的选中状态
* */
    private void setSelectedStatus(int index) {
        switch (index){
            case 0:
                mCourseBtn.setSelected(true);
                iv_course.setImageResource(R.drawable.main_course_icon_selected);
                tv_course.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷课程");
                break;
            case 1:
                mExercisesBtn.setSelected(true);
                iv_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                tv_exercises.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷习题");
                break;
            case 2:
                mMyInfoBtn.setSelected(true);
                iv_myinfo.setImageResource(R.drawable.main_my_icon_selected);
                tv_myInfo.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            //从设置界面或登录界面传递过来的登录状态
            boolean isLogin = data.getBooleanExtra("isLogin",false);
            if (isLogin){
                clearBottomImageState();
                selectDisplayView(0);
            }
            if (mMyInfoView != null){//登录成功或推出登录时根据设置我的界面
                mMyInfoView.setLoginParams(isLogin);
            }
        }
    }

    /*
    * 移除不需要的视图
    * */
    private void removeAllView() {
        for(int i = 0;i<mBodyLayout.getChildCount();i++){
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }

    private void clearBottomImageState() {
        tv_course.setTextColor(Color.parseColor("#666666"));
        tv_exercises.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));

        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercises.setImageResource(R.drawable.main_exercises_icon);
        iv_myinfo.setImageResource(R.drawable.main_my_icon);

        for (int i = 0;i<mBottomLayout.getChildCount();i++){
            mBottomLayout.getChildAt(i).setSelected(false);
        }
    }
    protected  long exitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - exitTime) > 2000){
                Toast.makeText(this,"请再按一次退出博学谷",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else{
                MainActivity.this.finish();
                if (readLoginStatus()){
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
/*
* 清除sharedpreference中的登录状态
* */
    private void clearLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putBoolean("isLogin",false);//清除登录状态
        editor.putString("loginUserName","");//清除登录时的用户名
        editor.commit();//提交修改
    }

    /*
    * 获取sharedpreference中的登录状态
    * */
    private boolean readLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        return isLogin;
    }
}
