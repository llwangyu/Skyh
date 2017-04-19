package android.com.skyh.activity;

import android.com.skyh.R;
import android.com.skyh.base.BaseActivity;
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
import android.widget.CheckBox;
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

public class ZwhActivity extends BaseActivity {

    private static final int PICK_PHOTO = 1;
    ToolBar toolBar;
    private TextView otherText;
    private EditText otherEdit;
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
    private CheckBox checkBox1,checkBo2,checkBox3,checkBox4,checkBox5,checkBox6;
    private EditText dzzmcEdit,jlrEdit,cjryEdit,zcrEdit;
    private TextView zkrqText;
    private String yt1,yt2,yt3,yt4,yt5,yt6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dydh);

        initToolBar();
        intiView();
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
             dialog();
            }
        });
    }
    private void intiView(){
        dzzmcEdit=(EditText)findViewById(R.id.dzzmc_edit);
        dzzmcEdit.setText(PreferencesUtils.getString(ZwhActivity.this,"HYMC"));
        zcrEdit=(EditText)findViewById(R.id.zcr_edit);
        cjryEdit=(EditText)findViewById(R.id.cjry_edit);
        jlrEdit=(EditText)findViewById(R.id.jlr_edit);
        zkrqText=(TextView)findViewById(R.id.zkrq_text);

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        zkrqText.setText(dateFormat.format( now ));
        checkBox1=(CheckBox)findViewById(R.id.check1);
        checkBo2=(CheckBox)findViewById(R.id.check2);
        checkBox3=(CheckBox)findViewById(R.id.check3);
        checkBox4=(CheckBox)findViewById(R.id.check4);
        checkBox5=(CheckBox)findViewById(R.id.check5);
        checkBox6=(CheckBox)findViewById(R.id.check6);

        otherText=(TextView)findViewById(R.id.other_text);
        otherEdit=(EditText)findViewById(R.id.other_edit);
        otherText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){
                    flag=true;
                    otherEdit.setVisibility(View.VISIBLE);
                    return;
                }
                if (flag){
                    flag=false;
                    otherEdit.setVisibility(View.GONE);
                    return;

                }
            }
        });

        sctp=(LinearLayout)findViewById(R.id.cs_tp);
        takePicture=(ImageView)findViewById(R.id.picture_view);
        luyin=(ImageView)findViewById(R.id.ly_view);
        lyText=(TextView)findViewById(R.id.ly_text);
        scBtn=(Button)findViewById(R.id.schyjl_btn);
        scBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording){
                    ToastUtil.shortshow(ZwhActivity.this,"会议录音中...");
                }else {
                    getRyxx();
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
                        aduioRecord.start(ZwhActivity.this);
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
  //      return Environment.getExternalStorageState() + "/DCIM/";
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

    class imgUploadThread extends Thread {
        @Override
        public void run() {
            FileInputStream in = null ;
                    try {

                        in = new FileInputStream(tempPhotoFile);


                        boolean flag = FileTool.uploadFile(PreferencesUtils.getString(ZwhActivity.this,"FTPURL"),
                                Integer.parseInt(PreferencesUtils.getString(ZwhActivity.this,"FTPPORT"))  ,
                                PreferencesUtils.getString(ZwhActivity.this,"FTPUSER"),
                                PreferencesUtils.getString(ZwhActivity.this,"FTPPWD"),
                                "/zhwimg/",
                                imageFile.getFile().getName()
                             , in);
                        System.out.println("是否上传成功"+flag);
                        picturePath="/zhwimg/"+imageFile.getFile().getName();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }catch (Exception e){

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
                long aa=aduioFile.length();
                boolean flag = FileTool.uploadFile(PreferencesUtils.getString(ZwhActivity.this,"FTPURL"),
                        Integer.parseInt(PreferencesUtils.getString(ZwhActivity.this,"FTPPORT"))  ,
                        PreferencesUtils.getString(ZwhActivity.this,"FTPUSER"),
                        PreferencesUtils.getString(ZwhActivity.this,"FTPPWD"),
                        "/zwhaduio/",
                        aduioFile.getName()
                        , in);
                System.out.println("是否上传成功"+flag);
                aduioPath="/zwh/audio/"+aduioFile.getName();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//                }
//            }
        }
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


        String url = PrefName.DEFAULT_SERVER_URL+ PrefName.INSERT_ZHW_SERVER_URL+
                "?zkrq="+ zkrqText.getText().toString()
                +"&yt1="+yt1
                +"&yt2="+yt2
                +"&yt3="+yt3
                +"&yt4="+yt4
                +"&yt5="+yt5
                +"&yt6="+yt6
                +"&jlr="+jlrEdit.getText().toString()
                +"&cjr="+cjryEdit.getText().toString()
                +"&qtnr="+otherEdit.getText().toString()
                //+"&zzmc="+dzzmcEdit.getText().toString()
                +"&zcr="+zcrEdit.getText().toString()
                +"&img="+picturePath
                +"&img1="+aduioPath
                +"&zzdm="+PreferencesUtils.getString(ZwhActivity.this,"YHDM")
                +"&zzmc="+PreferencesUtils.getString(ZwhActivity.this,"HYMC");

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
            ToastUtil.shortshow(ZwhActivity.this,"会议上传成功");
            finish();

        }
//        }

        @Override
        public void onFailure(CharSequence failure) {
            super.onFailure(failure);
            Toast.makeText(ZwhActivity.this,"会议上传失败",Toast.LENGTH_SHORT).show();
            hideProgBar();

        }

        @Override
        public void OnErrorResult(CharSequence error) {
            super.OnErrorResult(error);
            Toast.makeText(ZwhActivity.this,"会议上传失败",Toast.LENGTH_SHORT).show();
            hideProgBar();
        }
    };

    private void getRyxx(){


        if (checkBox1.isChecked()) {
            yt1 = "是";
        }
        if (checkBo2.isChecked()){
            yt2="是";
        }
        if (checkBox3.isChecked()){
            yt3="是";
        }
        if (checkBox4.isChecked()){
            yt4="是";
        }
        if (checkBox5.isChecked()){
            yt5="是";
        }
        if (checkBox6.isChecked()){
            yt6="是";
        }

        scDydh();
    }

}
