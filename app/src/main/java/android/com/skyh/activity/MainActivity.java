package android.com.skyh.activity;

import android.com.skyh.R;
import android.com.skyh.base.BaseActivity;
import android.com.skyh.entity.AdEntity;
import android.com.skyh.entity.FtpResult;
import android.com.skyh.service.PreferencesUtils;
import android.com.skyh.service.RequestResult;
import android.com.skyh.tool.PrefName;
import android.com.skyh.tool.ProtocolType;
import android.com.skyh.until.SlidingMenu;
import android.com.skyh.until.ToolBar;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private LinearLayout dot;
    private ViewPager viewPager;
    private List<AdEntity> list=new ArrayList<AdEntity>();
    private LinearLayout zwhBtn;
    private LinearLayout dydhBtn;
    private LinearLayout dxzhBtn;
    private LinearLayout dkBtn;
    private LinearLayout shykBtn;
    private ToolBar toolBar;
    private SlidingMenu menu;
    private LinearLayout quitDl;
    private LinearLayout qhhyLayout;
    private TextView userName;
   FtpResult ftp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu=(SlidingMenu)findViewById(R.id.activity_main);
        intView();
        initData();
        initToolBar();
        initListener();
        getFTPInfo();
    }
    private void initToolBar(){
        toolBar = new ToolBar(this);
        toolBar.setToolbarCentreText("三会一课");
        toolBar.hideRightButton();
        toolBar.hideRightTextButton();
        toolBar.setimage(ToolBar.left_btn, R.mipmap.qhyhss);
        ToolBar.left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });

    }
    private void intView(){
        quitDl=(LinearLayout)findViewById(R.id.quit_layout);
        qhhyLayout=(LinearLayout)findViewById(R.id.qhyh_layout);
        userName=(TextView)findViewById(R.id.user_type);
        userName.setText(PreferencesUtils.getString(MainActivity.this,"HYMC"));
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        dot = (LinearLayout) findViewById(R.id.dot);

        dkBtn=(LinearLayout) findViewById(R.id.dk_btn);//党课
        dxzhBtn =(LinearLayout)findViewById(R.id.dxzh_btn);//党小组会
        dydhBtn=(LinearLayout)findViewById(R.id.dydh_btn);//党员大会
        zwhBtn=(LinearLayout)findViewById(R.id.zwh_btn);//党课
        shykBtn=(LinearLayout)findViewById(R.id.shyktjb_btn);//党课
        dkBtn.setOnClickListener(listener);
        dxzhBtn.setOnClickListener(listener);
        dydhBtn.setOnClickListener(listener);
        zwhBtn.setOnClickListener(listener);
        shykBtn.setOnClickListener(listener);
        qhhyLayout.setOnClickListener(listener);
        quitDl.setOnClickListener(listener);
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dk_btn:
                    JumpToActivity(DkxxActivity.class);
                    break;
                case R.id.dxzh_btn:
                    JumpToActivity(DxzhActivity.class);
                    break;
                case R.id.dydh_btn:
                    JumpToActivity(DydhActivity.class);
                    break;
                case R.id.zwh_btn:
                    JumpToActivity(ZwhActivity.class);
                    break;
                case R.id.shyktjb_btn:
                    JumpToActivity(TjListActivity.class);
                    break;
                case R.id.qhyh_layout:
                    JumpToActivity(LoginActivity.class);
                    finish();
                    break;
                case R.id.quit_layout:
                    finish();
                    break;

            }


        }
    };


    private void initData() {
        list.add(new AdEntity(R.mipmap.banner1));
        list.add(new AdEntity(R.mipmap.banner2));
        list.add(new AdEntity(R.mipmap.banner3));
        initDots();

        //通过适配器引入图片
        viewPager.setAdapter(new MyViewPager());
        int centerValue=Integer.MAX_VALUE/2;
        int value=centerValue%list.size();
        viewPager.setCurrentItem(centerValue - value);

        //更新文本内容
        updateTextAndDot();

        h.sendEmptyMessageDelayed(0, 5000);
    }
    private Handler h=new Handler(){
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            h.sendEmptyMessageDelayed(0, 5000);
        };
    };

    /**
     * 初始化监听器
     */
    @SuppressWarnings("deprecation")
    private void initListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                updateTextAndDot();

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub

            }
        });
    }
    /**
     * 更新文本信息
     */
    private void updateTextAndDot(){
        int currentPage=viewPager.getCurrentItem()%list.size();
        //改变dot
        for (int i = 0; i < dot.getChildCount(); i++) {
            dot.getChildAt(i).setEnabled(i==currentPage);
        }

    }
    /**
     * 初始化dot
     */
    private void initDots() {
        for (int i = 0; i < list.size(); i++) {
            View view=new View(MainActivity.this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(80,8);
            if(i!=0){
                params.leftMargin=15;
            }

            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selecter_new_dot);
            dot.addView(view);
        }
    }

    class MyViewPager extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        /**
         * true表示无需创建
         * false表示需要创建
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        /**
         * 设置view
         * 最多3个界面
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            AdEntity adImage = list.get(position%list.size());
            View view=View.inflate(MainActivity.this, R.layout.adapter,null);
            ImageView image=(ImageView) view.findViewById(R.id.image);
            image.setImageResource(adImage.getImage());

            container.addView(view);
            return view;
        }
        /**
         * 销毁page
         * position:当前要销毁第几个page
         * object：当前需要销毁的page
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//			super.destroyItem(container, position, object);
            container.removeView((View) object);
        }



    }


    private void getFTPInfo(){


        String url = PrefName.DEFAULT_SERVER_URL+ PrefName.FTP_SERVER_URL;
        sendRequest(url, null, PrefName.GET, ProtocolType.FTPRESULT, resultFtp);
    }
    RequestResult resultFtp=new RequestResult() {
        @Override
        public ProtocolType getType() {
            return ProtocolType.FTPRESULT;
        }

        @Override
        public void onSuccess(Object o) {
            super.onSuccess(o);
            ftp=(FtpResult)o;
            PreferencesUtils.putString(MainActivity.this,"FTPURL",ftp.getFtp_url());
            PreferencesUtils.putString(MainActivity.this,"IMGPATH",ftp.getImgPath());
            PreferencesUtils.putString(MainActivity.this,"FTPPORT",ftp.getFtp_port());
            PreferencesUtils.putString(MainActivity.this,"NULLIMGPATH",ftp.getNullImgpath());
            PreferencesUtils.putString(MainActivity.this,"FTPUSER",ftp.getFtp_user());
            PreferencesUtils.putString(MainActivity.this,"FTPPWD",ftp.getFtp_pwd());


        }
//        }

        @Override
        public void onFailure(CharSequence failure) {
            super.onFailure(failure);
            hideProgBar();

        }

        @Override
        public void OnErrorResult(CharSequence error) {
            super.OnErrorResult(error);
            hideProgBar();
        }
    };
}
