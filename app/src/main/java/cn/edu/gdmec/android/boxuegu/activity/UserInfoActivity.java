package cn.edu.gdmec.android.boxuegu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.UserBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private ImageView iv_head_icon;
    private RelativeLayout rl_head;
    private TextView tv_user_name;
    private RelativeLayout rl_account;
    private TextView tv_nickName;
    private RelativeLayout rl_nickName;
    private TextView tv_sex;
    private RelativeLayout rl_sex;
    private TextView tv_signature;
    private RelativeLayout rl_signature;
    private String spUserName;
    private static final int CHANGE_NICKNAME = 1;
    private static final int CHANGE_SIGNATURE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        spUserName = AnalysisUtils.readloginUserName(this);
        initDate();
        setListener();
    }
//获取数据
    private void initDate(){
        UserBean bean = null;
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        //首先判断一下数据是否有数据
        if (bean == null){
            bean = new UserBean();
            bean.userName=spUserName;
            bean.nickName="问答精灵";
            bean.sex="男";
            bean.signature="问答精灵";
            //保存用户信息到数据库
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValue(bean);
    }
/*
* 为界面控件设置值
* */
    private void setValue(UserBean bean) {
        tv_nickName.setText(bean.nickName);
        tv_user_name.setText(bean.userName);
        tv_sex.setText(bean.sex);
        tv_signature.setText(bean.signature);
    }
/*
* 设置控件的点击监听事件
* */
    private void setListener() {
        tv_back.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        iv_head_icon = (ImageView) findViewById(R.id.iv_head_icon);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        rl_account = (RelativeLayout) findViewById(R.id.rl_account);
        tv_nickName = (TextView) findViewById(R.id.tv_nickName);
        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        tv_signature = (TextView) findViewById(R.id.tv_signature);
        rl_signature = (RelativeLayout) findViewById(R.id.rl_signature);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back://返回键的点击事件
                this.finish();
                break;
            case R.id.rl_nickName://昵称的点击事件
                String name = tv_nickName.getText().toString();//获取昵称控件上的数据
            Bundle bdName = new Bundle();
            bdName.putString("content",name);//传递界面上的昵称数据
            bdName.putString("title","昵称");
            bdName.putInt("flag",1);//flag传递1时表示修改昵称
            enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_NICKNAME,bdName);//跳转到个人资料修改页面
                break;
            case R.id.rl_sex://性别的点击事件
                String sex = tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.rl_signature://签名的点击事件
                String signature = tv_signature.getText().toString();//获取签名控件上的数据
                Bundle bdSignature = new Bundle();
                bdSignature.putString("content",signature);//传递界面上的签名数据
                bdSignature.putString("title","签名");
                bdSignature.putInt("flag",2);//flag传递2时表示修改签名
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_SIGNATURE,bdSignature);//跳转到个人资料修改界面
                break;
                default:
                    break;
        }
    }
    /*
        * 设置性别的弹出框
        * */
    private void sexDialog(String sex) {
        int sexFlag=0;
        if ("男".equals(sex)){
            sexFlag=0;
        }else if ("女".equals(sex)){
            sexFlag=1;
        }
        final String items[]={"男","女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别");//设置标题
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,items[which],Toast.LENGTH_SHORT).show();
                setSex(items[which]);
            }
        });
        builder.create().show();
    }
    /*
            * 更新界面上的性别数据
        * */
    private void setSex(String sex) {
    tv_sex.setText(sex);
    //更新数据库中的性别字段
        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("sex",sex,spUserName);
    }
    /*
    * 获取回传数据时需使用的跳转方法，第一个参数to表示需要跳转到的界面，
    * 第二个参数requestCode表示一个请求码，第三个参数b表示跳转时传递的数据
    * */
    public void enterActivityForResult(Class<?> to,int requesCode,Bundle b){
        Intent i = new Intent(this,to);
        i.putExtras(b);
        startActivityForResult(i,requesCode);
    }
    /*
    * 回传数据
    * */
    private String new_info;//最新数据

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHANGE_NICKNAME://个人资料修改界面回传过来的昵称数据
                if (data!=null){
                new_info = data.getStringExtra("nickName");
                if (TextUtils.isEmpty(new_info)){
                    return;
                }
                tv_nickName.setText(new_info);
                //更新数据库中的昵称字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("nickName",new_info,spUserName);
                }
                break;
            case CHANGE_SIGNATURE://个人资料修改界面回传过来的签名数据
                if (data!=null){
                new_info = data.getStringExtra("signature");
                if (TextUtils.isEmpty(new_info)){
                    return;
                }
                tv_signature.setText(new_info);
                //更新数据库中的签名字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("signature",new_info,spUserName);
                }
                break;
        }
    }
}
