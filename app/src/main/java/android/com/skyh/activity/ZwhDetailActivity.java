package android.com.skyh.activity;

import android.com.skyh.R;
import android.com.skyh.base.BaseActivity;
import android.com.skyh.base.ImagesDetailActivity;
import android.com.skyh.entity.DwhytResult;
import android.com.skyh.entity.RsEntityResult;
import android.com.skyh.service.FileTool;
import android.com.skyh.service.PreferencesUtils;
import android.com.skyh.service.RequestResult;
import android.com.skyh.tool.PrefName;
import android.com.skyh.tool.ProtocolType;
import android.com.skyh.tool.ToastUtil;
import android.com.skyh.until.BigImageFileUtil;
import android.com.skyh.until.ImageFile;
import android.com.skyh.until.MessageAduioRecord;
import android.com.skyh.until.StringUtils;
import android.com.skyh.until.TimeUtil;
import android.com.skyh.until.ToolBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZwhDetailActivity extends BaseActivity {

    private static final int PICK_PHOTO = 1;
    ToolBar toolBar;
    private TextView otherText;
    private TextView otherEdit;
    private boolean flag=false;
    private ImageView takePicture;
    private ImageView luyin;
    private TextView lyText;
    private Button scBtn;
    private LinearLayout sctp;
    private File tempPhotoFile;
    private String mPhotoPath;
    ImageFile imageFile;
    private static MessageAduioRecord aduioRecord;
    private boolean isRecording = false;
    private String aduioPath;
    private String picturePath;
    private File aduioFile;
    private TextView checkBox1,checkBo2,checkBox3,checkBox4,checkBox5,checkBox6;
    private TextView dzzmcEdit,jlrEdit,cjryEdit,zcrEdit;
    private TextView zkrqText;
    private String yt1,yt2,yt3,yt4,yt5,yt6;
    private String id;
    RsEntityResult result;
    File imgFile;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwh_detail);
        id=getIntent().getStringExtra(PrefName.HYID);
        initToolBar();
        intiView();
        scDydh();
    }
    private void initToolBar(){
        toolBar = new ToolBar(this);
        toolBar.setToolbarCentreText("支委会");
        toolBar.hideRightButton();
        toolBar.hideRightTextButton();
        ToolBar.left_btn.setImageResource(R.mipmap.fh);
        ToolBar.left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
    }
    private void intiView(){
        dzzmcEdit=(TextView)findViewById(R.id.dzzmc_edit);

        zkrqText=(TextView)findViewById(R.id.zkrq_text);

        checkBox1=(TextView)findViewById(R.id.check1);
        checkBo2=(TextView)findViewById(R.id.check2);
        checkBox3=(TextView)findViewById(R.id.check3);
        checkBox4=(TextView)findViewById(R.id.check4);
        checkBox5=(TextView)findViewById(R.id.check5);
        checkBox6=(TextView)findViewById(R.id.check6);

        otherText=(TextView)findViewById(R.id.other_text);
        otherEdit=(TextView)findViewById(R.id.other_edit);


        sctp=(LinearLayout)findViewById(R.id.cs_tp);
        takePicture=(ImageView)findViewById(R.id.picture_view);
        luyin=(ImageView)findViewById(R.id.ly_view);
        lyText=(TextView)findViewById(R.id.ly_text);

        luyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {


                }
                else {

                }
            }
        });
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect frame = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;

                int[] location = new int[2];
                v.getLocationOnScreen(location);
                location[1] += statusBarHeight;

                int width = v.getWidth();
                int height = v.getHeight();

                navigateToImagesDetail(
                        location[0],
                        location[1],
                        width,
                        height);
            }
        });
    }


    private void scDydh(){

        String url = PrefName.DEFAULT_SERVER_URL+ PrefName.ZHW_BYID_SERVER_URL+
                "?id="+ id;


        sendRequest(url, null, PrefName.GET, ProtocolType.DWHYT, resultInfo);

    }
    RequestResult resultInfo=new RequestResult() {
        @Override
        public ProtocolType getType() {
            return ProtocolType.DWHYT;
        }

        @Override
        public void onSuccess(Object o) {
            super.onSuccess(o);
            DwhytResult dwhytResult=(DwhytResult)o;
            result=dwhytResult.getZbwyh();
                zkrqText.setText(TimeUtil.longToDate(result.getZkrq()));

            if(StringUtils.isNotNull(result.getZzmc())){
                dzzmcEdit.setText(result.getZzmc());
            }


            if(!"是".equals(result.getYt1())
                    ){
                checkBox1.setVisibility(View.GONE);
            }
            if(!"是".equals(result.getYt2()
            )){
                checkBo2.setVisibility(View.GONE);
            }
            if(!"是".equals(result.getYt3()
            )){
                checkBox3.setVisibility(View.GONE);
            }
            if(!"是".equals(result.getYt4()
            )){
                checkBox4.setVisibility(View.GONE);
            }
            if(!"是".equals(result.getYt5()
            )){
                checkBox5.setVisibility(View.GONE);
            }
            if(!"是".equals(result.getYt6()
            )){
                checkBox6.setVisibility(View.GONE);
            }
            if(StringUtils.isNotNull(result.getImg())){
                String url = PrefName.IMG_SERVER_URL+"/upload"
                        + result.getImg();
                final ImageView view = takePicture;

                Picasso.with(ZwhDetailActivity.this)
                        .load(url)
                        .placeholder(R.mipmap.tx)
                        .error(R.mipmap.tx)
                        .fit()
                        .into(view);
            }


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


    private String getPhotoPath() {
        return Environment.getExternalStorageDirectory() + "/skyh/zwhImg/";
    }
    private void navigateToImagesDetail( int x, int y, int width, int height) {
        Bundle extras = new Bundle();
        extras.putString(ImagesDetailActivity.INTENT_IMAGE_URL_TAG, PrefName.DEFAULT_SERVER_URL+"/mhgazhdd"+result.getImg());
        extras.putInt(ImagesDetailActivity.INTENT_IMAGE_X_TAG, x);
        extras.putInt(ImagesDetailActivity.INTENT_IMAGE_Y_TAG, y);
        extras.putInt(ImagesDetailActivity.INTENT_IMAGE_W_TAG, width);
        extras.putInt(ImagesDetailActivity.INTENT_IMAGE_H_TAG, height);
        JumpToActivity(ImagesDetailActivity.class, extras);
        overridePendingTransition(0, 0);
    }
}
