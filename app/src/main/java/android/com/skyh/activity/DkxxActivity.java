package android.com.skyh.activity;

import android.com.skyh.R;
import android.com.skyh.base.BaseActivity;
import android.com.skyh.entity.FtpResult;
import android.com.skyh.service.FileTool;
import android.com.skyh.service.PreferencesUtils;
import android.com.skyh.service.RequestResult;
import android.com.skyh.tool.PrefName;
import android.com.skyh.tool.ProtocolType;
import android.com.skyh.tool.ToastUtil;
import android.com.skyh.until.BigImageFileUtil;
import android.com.skyh.until.ImageFile;
import android.com.skyh.until.MessageAduioRecord;
import android.com.skyh.until.ToolBar;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DkxxActivity extends BaseActivity {

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
    private boolean isRecording = false;
    private String aduioPath;
    private String picturePath;
    private File aduioFile;
    private EditText dzzmcEdit,jlrEdit,cjryEdit,zcrEdit ,xxnr;
    private TextView zkrqText;
    private FtpResult ftp;
    private TextView otherText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dk);

        initToolBar();
        intiView();

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
                dialog();
            }
        });
    }
    private void intiView(){
        dzzmcEdit=(EditText)findViewById(R.id.dzzmc_edit);//组织名称
        dzzmcEdit.setText(PreferencesUtils.getString(DkxxActivity.this,"HYMC"));
        zcrEdit=(EditText)findViewById(R.id.zcr_edit);//主持人
        cjryEdit=(EditText)findViewById(R.id.cjry_edit);
        jlrEdit=(EditText)findViewById(R.id.jlr_edit);
        xxnr=(EditText)findViewById(R.id.other_edit);//学习内容
        zkrqText=(TextView)findViewById(R.id.zkrq_text);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        zkrqText.setText(dateFormat.format( now ));

        sctp=(LinearLayout)findViewById(R.id.cs_tp);
        takePicture=(ImageView)findViewById(R.id.picture_view);
        luyin=(ImageView)findViewById(R.id.ly_view);
        lyText=(TextView)findViewById(R.id.ly_text);
        scBtn=(Button)findViewById(R.id.schyjl_btn);

        otherText=(TextView)findViewById(R.id.other_text);
        otherText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){
                    flag=true;
                    xxnr.setVisibility(View.VISIBLE);
                    return;
                }
                if (flag){
                    flag=false;
                    xxnr.setVisibility(View.GONE);
                    return;

                }
            }
        });

        scBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording){
                    ToastUtil.shortshow(DkxxActivity.this,"会议录音中...");
                }else {
                    scDydh();
                }
            }
        });
        luyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    isRecording = true;
                    if (aduioRecord == null) {
                        lyText.setText("会议录音中");
                        aduioRecord = MessageAduioRecord.getInstance();
//                    aduioRecord.audioInput();
                        aduioRecord.start(DkxxActivity.this);
                        scBtn.setBackgroundColor(0xFFEDE7E7);
                    }

                }
                else {
                    lyText.setText("会议结束");
                    isRecording = false;
                    aduioRecord.stopRecording();
                    aduioFile=  aduioRecord.getTempFile();
                    aduioRecord=null;
                    scBtn.setBackgroundColor(0xFFE92323);
                    new uploadThread().start();
                }
            }
        });
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromAlbum();
            }
        });
    }

    /**
     * 从相机获取照片
     */

    protected void getImageFromAlbum() {
        try {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            mPhotoPath =getPhotoPath() + getPhotoFileName();
            tempPhotoFile = new File(mPhotoPath);
            //    tempPhotoFile = new File(getPhotoPath() + getPhotoFileName());
            if (!tempPhotoFile.exists()) {
                tempPhotoFile.createNewFile();
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(tempPhotoFile));
            startActivityForResult(intent, PICK_PHOTO);
        } catch (Exception e) {
        }
    }
    private String getPhotoPath() {
        return Environment.getExternalStorageDirectory() + "/DCIM/";
    }
    /**
     * 用时间戳生成照片名称
     * @return
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
    @Override protected void onResume() {
        super.onResume();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case PICK_PHOTO:
                    addImageFile(takePicture,tempPhotoFile);
                    sctp.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

    }
    private void addImageFile(ImageView view, File file ) {
        if (!file.exists()) {
            return ;
        }
        File sizedFile = BigImageFileUtil.resizeImageFile(file, 1136,
                1136);
        Bitmap bitmap = BigImageFileUtil.getResizeRotatedBitmap(file,
                400, 400);
         imageFile = new ImageFile();
        imageFile.setFile(sizedFile);
        imageFile.setThumbBitmap(bitmap);
        Drawable drawable = Drawable.createFromPath(sizedFile.toString());
        view.setImageDrawable(drawable);
        new imgUploadThread().start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
        dialog();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void scDydh(){
        showProgBar(getString(R.string.sc_info));
        String url = PrefName.DEFAULT_SERVER_URL+ PrefName.INSERT_DKXX_SERVER_URL+
                "?xxsj="+ zkrqText.getText().toString()
                +"&cjr="+cjryEdit.getText().toString()
                +"&xxrn="+xxnr.getText().toString()
                //+"&zzmc="+dzzmcEdit.getText().toString()
                +"&zcr="+zcrEdit.getText().toString()
                +"&zcr="+zcrEdit.getText().toString()
                +"&jlr="+jlrEdit.getText().toString()
                +"&img="+picturePath
                +"&img1="+aduioPath
                +"&zzdm="+PreferencesUtils.getString(DkxxActivity.this,"YHDM")
                +"&zzmc="+PreferencesUtils.getString(DkxxActivity.this,"HYMC");
        sendRequest(url, null, PrefName.GET, ProtocolType.BASE, resultInfo);
        //  sendRequest(url, eventSend, PrefName.POST, ProtocolType.BASE, resultInfo);
    }
    RequestResult resultInfo=new RequestResult() {
        @Override
        public ProtocolType getType() {
            return ProtocolType.BASE;
        }

        @Override
        public void onSuccess(Object o) {
            super.onSuccess(o);

            hideProgBar();
            ToastUtil.shortshow(DkxxActivity.this,"会议上传成功");
            finish();

        }
//        }

        @Override
        public void onFailure(CharSequence failure) {
            super.onFailure(failure);
            Toast.makeText(DkxxActivity.this,"会议上传失败",Toast.LENGTH_SHORT).show();
            hideProgBar();

        }

        @Override
        public void OnErrorResult(CharSequence error) {
            super.OnErrorResult(error);
            Toast.makeText(DkxxActivity.this,"会议上传失败",Toast.LENGTH_SHORT).show();
            hideProgBar();
        }
    };
    class imgUploadThread extends Thread {
        @Override
        public void run() {//fdsfds
            FileInputStream in = null ;
            try {
                in = new FileInputStream(tempPhotoFile);
                boolean flag = FileTool.uploadFile(PreferencesUtils.getString(DkxxActivity.this,"FTPURL"),
                        Integer.parseInt(PreferencesUtils.getString(DkxxActivity.this,"FTPPORT"))  ,
                        PreferencesUtils.getString(DkxxActivity.this,"FTPUSER"),
                        PreferencesUtils.getString(DkxxActivity.this,"FTPPWD"),
                        "/dkxx/img/",
                        imageFile.getFile().getName()
                        , in);
                System.out.println("是否上传成功"+flag);
                picturePath="/dkxx/img/"+imageFile.getFile().getName();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//                }
//            }
        }
    }
    class uploadThread extends Thread {
        @Override
        public void run() {
            FileInputStream in = null ;

//            if(dir.isDirectory()) {
//                for(int i=0;i<files.length;i++) {
            try {
                in = new FileInputStream(aduioFile);
                boolean flag = FileTool.uploadFile(PreferencesUtils.getString(DkxxActivity.this,"FTPURL"),
                        Integer.parseInt(PreferencesUtils.getString(DkxxActivity.this,"FTPPORT"))  ,
                        PreferencesUtils.getString(DkxxActivity.this,"FTPUSER"),
                        PreferencesUtils.getString(DkxxActivity.this,"FTPPWD"),
                        "/dkxx/audio/",
                        aduioFile.getName()
                        , in);
                System.out.println("是否上传成功"+flag);
                aduioPath="/dkxx/audio/"+aduioFile.getName();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//                }
//            }
        }
    }
}
