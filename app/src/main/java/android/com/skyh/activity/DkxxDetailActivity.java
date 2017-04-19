package android.com.skyh.activity;

import android.com.skyh.R;
import android.com.skyh.base.BaseActivity;
import android.com.skyh.base.ImagesDetailActivity;
import android.com.skyh.entity.DwhytResult;
import android.com.skyh.entity.FtpResult;
import android.com.skyh.entity.RsEntityResult;
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
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DkxxDetailActivity extends BaseActivity {

    private static final int PICK_PHOTO = 1;
    ToolBar toolBar;
    private ImageView takePicture;
    private ImageView luyin;
    private TextView lyText;
    private Button scBtn;
    private boolean flag=false;
    private LinearLayout sctp;
    private File tempPhotoFile;
    private String mPhotoPath;
    ImageFile imageFile;
    private static MessageAduioRecord aduioRecord;

    private String aduioPath;
    private String picturePath;
    private TextView dzzmcEdit,cjryEdit ,xxnr;
    private TextView zkrqText;
    private String id;
    RsEntityResult result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dk_detail);
        id=getIntent().getStringExtra(PrefName.HYID);
        initToolBar();
        intiView();
        getDydh();
    }
    private void initToolBar(){
        toolBar = new ToolBar(this);
        toolBar.setToolbarCentreText("党课");
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
        dzzmcEdit=(TextView)findViewById(R.id.dzzmc_edit);//组织名称

        cjryEdit=(TextView)findViewById(R.id.cjry_edit);
        xxnr=(TextView)findViewById(R.id.other_edit);//学习内容
        zkrqText=(TextView)findViewById(R.id.zkrq_text);

        takePicture=(ImageView)findViewById(R.id.picture_view);
        luyin=(ImageView)findViewById(R.id.ly_view);
        lyText=(TextView)findViewById(R.id.ly_text);

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
        luyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isNull(result.getImg1())) {
                ToastUtil.shortshow(DkxxDetailActivity.this,"无录频" +
                        "信息");
                }
                else {

                }
            }
        });
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isNull(result.getImg())) {

                return;
                }
                else {

                }
            }
        });
    }



    private void getDydh(){
        String url = PrefName.DEFAULT_SERVER_URL+ PrefName.DKXX_BYID_SERVER_URL+
                "?id="+id;


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
            hideProgBar();
            DwhytResult dwhytResult=(DwhytResult)o;
             result=dwhytResult.getDkxxjl();

                zkrqText.setText(TimeUtil.longToDate(result.getXxsj()));


            if(StringUtils.isNotNull(result.getZzmc())){
                dzzmcEdit.setText(result.getZzmc());
            }
            if(StringUtils.isNotNull(result.getCjr()
            )){
                cjryEdit.setText(result.getCjr());
            }

            if(StringUtils.isNotNull(result.getXxnr())){
                xxnr.setText(result.getXxnr());
            }
            if(StringUtils.isNotNull(result.getImg())){
                String url = PrefName.IMG_SERVER_URL+"/upload"
                        + result.getImg();
                final ImageView view = takePicture;

                Picasso.with(DkxxDetailActivity.this)
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
