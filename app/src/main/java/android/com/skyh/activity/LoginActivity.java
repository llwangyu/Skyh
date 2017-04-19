package android.com.skyh.activity;
import android.app.AlertDialog;
import android.com.skyh.R;
import android.com.skyh.base.BaseActivity;
import android.com.skyh.base.ChooseDjdzzActivity;
import android.com.skyh.base.ChooseUserActivity;
import android.com.skyh.entity.LoginData;
import android.com.skyh.entity.LoginInfo;
import android.com.skyh.entity.LoginResult;
import android.com.skyh.entity.UpdataInfo;
import android.com.skyh.service.DownloadService;
import android.com.skyh.service.ParseXmlService;
import android.com.skyh.service.PreferencesUtils;
import android.com.skyh.service.RequestResult;
import android.com.skyh.tool.PrefName;
import android.com.skyh.tool.ProtocolType;
import android.com.skyh.tool.ToastUtil;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends BaseActivity {
    private static final int GJDZZ_CODE = 0; // 请求码
    private static final int DWBM_CODE = 1; // 请求码
    // UI references.
    private EditText userName;
    private EditText mPasswordView;
    private TextView userName1;
    private TextView userName2;
    LoginInfo loginInfo;
    private String userId,password;
    private RadioGroup userTypeRg;
    private int yhlb=1;
    private TextView loginBtn;
    private TextView czText;
    private String yhdm;
    private String yhname;
    private RelativeLayout userLayout1,userLayout2,userLayout3;
    private UpdataInfo info;
    private static String localVersion;
    private final int UPDATA_NONEED = 0;
    private final int UPDATA_CLIENT = 1;
    private final int GET_UNDATAINFO_ERROR = 2;
    private final int DOWN_ERROR = 4;
    private final String TAG = this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    intiView();
        checkVersion();
    }
  private   void checkVersion() {
        try {
            localVersion = getVersionName();
            CheckVersionTask1 cv1 = new CheckVersionTask1();
            new Thread(cv1).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
   获取当前版本号：
    */
    public String getVersionName() throws Exception {
        //获取packagemanager的实例
        // PackageManager packageManager = getPackageManager();
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo("android.com.skyh", 0);
        return packInfo.versionName;
    }
    private class CheckVersionTask1 implements Runnable {
        InputStream is;
        public void run() {
            try {
                String path = getResources().getString(R.string.server_url);
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setConnectTimeout(5000);
                is = conn.getInputStream();

                info = ParseXmlService.getUpdataInfo(is);
                if (Double.parseDouble(info.getVersion())<=(Double.parseDouble(localVersion))) {
                    Log.i(TAG, "版本号相同或小于当前版本");
                    Message msg = new Message();
                    msg.what = UPDATA_NONEED;
                    handler1.sendMessage(msg);
                    // LoginMain();
                } else if( Double.parseDouble(info.getVersion())>(Double.parseDouble(localVersion))){
                    Log.i(TAG, "有新版本");
                    Message msg = new Message();
                    msg.what = UPDATA_CLIENT;
                    handler1.sendMessage(msg);
                }
            } catch (Exception e) {
                Message msg = new Message();
                msg.what = GET_UNDATAINFO_ERROR;
                handler1.sendMessage(msg);
                e.printStackTrace();
            }
        }
    }
    Handler handler1 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATA_NONEED:
//                    Toast.makeText(getApplicationContext(), "不需要更新",
//                               Toast.LENGTH_SHORT).show();
                    break;
                case UPDATA_CLIENT:
                    //对话框通知用户升级程序
                    showUpdataDialog();
                    break;
                case GET_UNDATAINFO_ERROR:
                    //服务器超时
//                    Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_LONG).show();
                    break;
                case DOWN_ERROR:
                    //下载apk失败
//                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    /*
 是否更新对话框
  */
    private void showUpdataDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("版本升级");
        builer.setMessage(info.getDescription());
        //当点确定按钮时从服务器上下载 新的apk 然后安装   װ
        builer.setPositiveButton("确定", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "下载apk,更新");
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), DownloadService.class);
                intent.putExtra("url", info.getUrl());
                startService(intent);

                //  downLoadApk();
            }
        });
        builer.setNegativeButton("以后再说", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

private void intiView(){
    userLayout1=(RelativeLayout)findViewById(R.id.user_layout) ;
    userLayout2=(RelativeLayout)findViewById(R.id.user_layout2) ;
    userLayout3=(RelativeLayout)findViewById(R.id.user_layout3) ;


    czText = (TextView) findViewById(R.id.cz_text);//重置
    czText.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (yhlb){
                case 1:
                    yhdm=null ;
                    userName1.setText("");
                    mPasswordView.setText("");

                    break;
                case 2:
                    yhdm=null ;
                    userName2.setText("");
                    mPasswordView.setText("");
                    break;
                case 3:
                    yhdm=null ;
                    userName.setText("");
                    mPasswordView.setText("");
                    break;
                default:
                    break;

            }
        }
    });

    userName = (EditText) findViewById(R.id.user_name3);//用户名
    userName1=(TextView)findViewById(R.id.user_name1); //各级党组织
    userName1.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this,ChooseDjdzzActivity .class);
            startActivityForResult(intent,DWBM_CODE);
        }
    });

    userName2=(TextView)findViewById(R.id.user_name2);//党委部门
    userName2.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this, ChooseUserActivity.class);
            startActivityForResult(intent,GJDZZ_CODE);
        }
    });
    mPasswordView = (EditText) findViewById(R.id.password);//密码

    loginBtn = (TextView) findViewById(R.id.email_sign_in_button);//登录
    loginBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            attemptLogin();
        }
    });
    userTypeRg=(RadioGroup)findViewById(R.id.user_type);
    userTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.gjdzz_rb:
                    yhlb=1;
                    userLayout1.setVisibility(View.VISIBLE);
                    userLayout2.setVisibility(View.GONE);
                    userLayout3.setVisibility(View.GONE);
                    break;
                case R.id.dwbm_rb:
                    userLayout1.setVisibility(View.GONE);
                    userLayout2.setVisibility(View.VISIBLE);
                    userLayout3.setVisibility(View.GONE);
                    yhlb=2;
                    break;
                case R.id.glyh_rb:
                    userLayout1.setVisibility(View.GONE);
                    userLayout2.setVisibility(View.GONE);
                    userLayout3.setVisibility(View.VISIBLE);
                    yhlb=3;
                    break;


            }
        }
    });

}
    private void attemptLogin() {
        hideKeyboard();


        login();



    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

//    public void doLogin() {
//        Intent intent =new Intent(this,MainActivity.class);
//        startActivity(intent);
//        finish();
//    }

    private void login(){

        showProgBar(getString(R.string.alert_info1));


        String url = PrefName.DEFAULT_SERVER_URL+ PrefName.LOGIN_SERVER_URL
                +"?yhdm="+yhdm+"&yhlb="+yhlb+"&yhmm="+mPasswordView.getText().toString();
        sendRequest(url, null, PrefName.GET, ProtocolType.LOGIN, resultInfo);
    }
    RequestResult resultInfo=new RequestResult() {
        @Override
        public ProtocolType getType() {
            return ProtocolType.LOGIN;
        }

        @Override
        public void onSuccess(Object o) {
            super.onSuccess(o);
            LoginResult loginResult=(LoginResult) o;
            LoginData data=loginResult.getData();
            if (loginResult.isSuccess()){
                PreferencesUtils.putString(LoginActivity.this, "ID", data.getId());
                PreferencesUtils.putString(LoginActivity.this, "YHDM", data.getYhdm());
                PreferencesUtils.putString(LoginActivity.this, "HYID", data.getYhid());
                PreferencesUtils.putString(LoginActivity.this, "HYMC", data.getYhmc());
                JumpToActivity(MainActivity.class);
                finish();
            }else {
                ToastUtil.shortshow(LoginActivity.this,loginResult.getMessage());
            }
            hideProgBar();
        }
//        }

        @Override
        public void onFailure(CharSequence failure) {
            super.onFailure(failure);
            Toast.makeText(LoginActivity.this,failure,Toast.LENGTH_SHORT).show();
            hideProgBar();

        }

        @Override
        public void OnErrorResult(CharSequence error) {
            super.OnErrorResult(error);
            Toast.makeText(LoginActivity.this,error,Toast.LENGTH_SHORT).show();
            hideProgBar();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case GJDZZ_CODE:
                    yhdm = data.getExtras().getString("index");
                     yhname=data.getExtras().getString("name");
                    userName2.setText(yhname);
                    break;
                case DWBM_CODE:
                    yhdm = data.getExtras().getString("index");
                    yhname=data.getExtras().getString("name");
                    userName1.setText(yhname);
                    break;
                default:
                    break;
            }
        }
    }
}








