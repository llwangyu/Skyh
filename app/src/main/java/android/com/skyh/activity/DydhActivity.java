package android.com.skyh.activity;

import android.com.skyh.R;
import android.com.skyh.base.BaseActivity;
import android.com.skyh.entity.EventSend;
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
import java.util.HashMap;

public class DydhActivity extends BaseActivity {

    private static final int PICK_PHOTO = 1;
    ToolBar toolBar;
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
    private TextView otherText;
    private EditText otherEdit;
    private boolean flag=false;
    private EventSend eventSend;
    private EditText zzmcEdit,zcrEdit,jrlEdit,ydEdit,sdEdit;
    private TextView zkrqText;
    private CheckBox checkBox1,checkBo2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7,checkBox8,checkBox9,checkBox10;

    private String yt1,yt2,yt3,yt4,yt5,yt6,yt7,yt8,yt9,yt10;
    private String jlr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dydh);
        eventSend=new EventSend();
        initToolBar();
        intiView();
    }
    private void initToolBar(){
        toolBar = new ToolBar(this);
        toolBar.setToolbarCentreText("党员大会");
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
        zzmcEdit=(EditText)findViewById(R.id.zzmc_edit);
        zzmcEdit.setText(PreferencesUtils.getString(DydhActivity.this,"HYMC"));
        zcrEdit=(EditText)findViewById(R.id.zcr_edit);
        jrlEdit=(EditText)findViewById(R.id.jlr_edit);
        ydEdit=(EditText)findViewById(R.id.yd_edit);
        sdEdit=(EditText)findViewById(R.id.sdz_edit);
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
        checkBox7=(CheckBox)findViewById(R.id.check7);
        checkBox8=(CheckBox)findViewById(R.id.check8);
        checkBox9=(CheckBox)findViewById(R.id.check9);
        checkBox10=(CheckBox)findViewById(R.id.check10);


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
                    ToastUtil.shortshow(DydhActivity.this,"会议录音中...");
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
                        aduioRecord.start(DydhActivity.this);
                        scBtn.setBackgroundColor(0xFFEDE7E7);
                    }

                }
                else {
                    lyText.setText("会议结束");
                    isRecording = false;
                    aduioRecord.stopRecording();
                    aduioFile=  aduioRecord.getTempFile();
                    aduioPath=aduioFile.getPath();
//                    ftpUpload(ftp.getFtp_url(),ftp.getFtp_port(),ftp.getFtp_user(),ftp.getFtp_pwd(),ftp.getImgPath(),aduioPath, Calendar.getInstance(Locale.getDefault())
//                            .getTimeInMillis() + ".wav");
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


        String url = PrefName.DEFAULT_SERVER_URL+ PrefName.INSERT_DYDY_SERVER_URL+
                "?zkrq="+ zkrqText.getText().toString()
             +"&yt1="+yt1
             +"&yt2="+yt2
             +"&yt3="+yt3
             +"&yt4="+yt4
             +"&yt5="+yt5
             +"&yt6="+yt6
             +"&yt7="+yt7
             +"&yt8="+yt8
             +"&yt9="+yt9
             +"&yt10="+yt10
             +"&yd="+ydEdit.getText().toString()
             +"&sd="+sdEdit.getText().toString()
             +"&qtnr="+otherEdit.getText().toString()
             //+"&zzmc="+zzmcEdit.getText().toString()
             +"&zcr="+zcrEdit.getText().toString()
             +"&jlr="+jrlEdit.getText().toString()
             +"&img="+picturePath
             +"&img1="+aduioPath
            +"&zzdm="+PreferencesUtils.getString(DydhActivity.this,"YHDM")
            +"&zzmc="+PreferencesUtils.getString(DydhActivity.this,"HYMC");

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
            ToastUtil.shortshow(DydhActivity.this,"会议上传成功");
            finish();

        }
//        }

        @Override
        public void onFailure(CharSequence failure) {
            super.onFailure(failure);
            Toast.makeText(DydhActivity.this,"会议上传失败",Toast.LENGTH_SHORT).show();
            hideProgBar();

        }

        @Override
        public void OnErrorResult(CharSequence error) {
            super.OnErrorResult(error);
            Toast.makeText(DydhActivity.this,"会议上传失败",Toast.LENGTH_SHORT).show();
            hideProgBar();
        }
    };

    private void getRyxx(){
        HashMap<String, String> stringMap = new HashMap<String, String>();
        if (StringUtils.isNotNull(zkrqText.getText().toString())) {
            stringMap.put("zkrq", zkrqText.getText().toString());
        }

        if (StringUtils.isNotNull(zzmcEdit.getText().toString())) {
                stringMap.put("zzmc", zzmcEdit.getText().toString());
            }

        if (checkBox1.isChecked()){
            stringMap.put("yt1", checkBox1.getText().toString());
            yt1="是";
        }else {

        }
        if (checkBo2.isChecked()){
            stringMap.put("yt2", checkBo2.getText().toString());
            yt2="是";
        }
        if (checkBox3.isChecked()){
            stringMap.put("yt3", checkBox3.getText().toString());
            yt3="是";
        }
        if (checkBox4.isChecked()){
            stringMap.put("yt4", checkBox4.getText().toString());
            yt4="是";
        }
        if (checkBox5.isChecked()){
            stringMap.put("yt5", checkBox5.getText().toString());
            yt5="是";
        }
        if (checkBox6.isChecked()){
            stringMap.put("yt6", checkBox6.getText().toString());
            yt6="是";
        }
        if (checkBox7.isChecked()){
            stringMap.put("yt7", checkBox7.getText().toString());
            yt7="是";
        }
        if (checkBox8.isChecked()){
            stringMap.put("yt8", checkBox8.getText().toString());
            yt8="是";
        }
        if (checkBox9.isChecked()){
            stringMap.put("yt9", checkBox9.getText().toString());
            yt9="是";
        }
        if (checkBox10.isChecked()){
            stringMap.put("yt10", checkBox10.getText().toString());
            yt10="是";
        }
        if (StringUtils.isNotNull(otherEdit.getText().toString())) {
            stringMap.put("qtnr", otherEdit.getText().toString());
        }
        if (StringUtils.isNotNull(zcrEdit.getText().toString())) {
            stringMap.put("zcr", zcrEdit.getText().toString());
        }
        if (StringUtils.isNotNull(jrlEdit.getText().toString())) {
            stringMap.put("jlr", jrlEdit.getText().toString());

        }
//        else {
//            Toast.makeText(SjySjysActivity.this,"运单号码不能为空", Toast.LENGTH_LONG).show();
//            return false;
//        }
        if (StringUtils.isNotNull(ydEdit.getText().toString())) {
            stringMap.put("yd", ydEdit.getText().toString());
        }
        if (StringUtils.isNotNull(sdEdit.getText().toString())) {
            stringMap.put("sd", sdEdit.getText().toString());
        }

//        if (imageFile!=null){
//            eventSend.setFile(imageFile.getFile().getPath());
//        }
//        if (aduioFile!=null){
//            eventSend.setVidiofile(aduioFile.getPath());
//        }
        eventSend.setStringParamMap(stringMap);
        scDydh();
    }
    class imgUploadThread extends Thread {
        @Override
        public void run() {
            FileInputStream in = null ;
            try {
                in = new FileInputStream(tempPhotoFile);
                boolean flag = FileTool.uploadFile(PreferencesUtils.getString(DydhActivity.this,"FTPURL"),
                        Integer.parseInt(PreferencesUtils.getString(DydhActivity.this,"FTPPORT"))  ,
                        PreferencesUtils.getString(DydhActivity.this,"FTPUSER"),
                        PreferencesUtils.getString(DydhActivity.this,"FTPPWD"),
                        "/dydh/img/",
                        imageFile.getFile().getName()
                        , in);
                System.out.println("是否上传成功"+flag);
                picturePath="/dydh/img/"+imageFile.getFile().getName();
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
                boolean flag = FileTool.uploadFile(PreferencesUtils.getString(DydhActivity.this,"FTPURL"),
                        Integer.parseInt(PreferencesUtils.getString(DydhActivity.this,"FTPPORT"))  ,
                        PreferencesUtils.getString(DydhActivity.this,"FTPUSER"),
                        PreferencesUtils.getString(DydhActivity.this,"FTPPWD"),
                        "/dydh/audio/",
                        aduioFile.getName()
                        , in);
                System.out.println("是否上传成功"+flag);
                aduioPath="/dydh/audio/"+aduioFile.getName();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//                }
//            }
        }
    }

}
