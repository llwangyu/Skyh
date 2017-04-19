package android.com.skyh.activity;

import android.com.skyh.R;
import android.com.skyh.base.BaseActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MeetingActivity extends BaseActivity implements View.OnClickListener{
    private Button takePhoto;
    private ImageView pictureImg;
    private static  final  int TAKE_PHOTO=1;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        takePhoto=(Button)findViewById(R.id.take_photo_btn);
        pictureImg=(ImageView)findViewById(R.id.picture_view);
        takePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_photo_btn:
                takePicture();
                break;
        }
    }
   private void takePicture(){
       File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
       try {
            if(outputImage.exists()){
                outputImage.delete();
            }else {
               outputImage.createNewFile();
            }
       }catch (IOException e){
           e.printStackTrace();
       }
       if(Build.VERSION.SDK_INT>=24){
           imageUri= FileProvider.getUriForFile(MeetingActivity.this,"android.com.threelessons.fileprovider",outputImage);
       }else {
           imageUri=Uri.fromFile(outputImage);
       }
       Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
       intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
       startActivityForResult(intent,TAKE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        pictureImg.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }

                }
                break;
            default:
                break;
        }
    }
}
