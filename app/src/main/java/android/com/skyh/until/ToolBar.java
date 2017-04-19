package android.com.skyh.until;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.com.skyh.R;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Administrator on 2015/11/14.
 */
public class ToolBar {
    Activity mActivity;
    public static ImageView left_btn;
    public ImageView left_btn2;
    public static ImageView right_btn;
    public static Button right_text_btn;
    TextView title;

    public ToolBar(Activity activity){
        this.mActivity=activity;
        init();
    }

    @SuppressLint("CutPasteId")
    public void init(){
        left_btn=(ImageView)mActivity.findViewById(R.id.toolbar_left_back_btn);
        left_btn2=(ImageView)mActivity.findViewById(R.id.toolbar_left_back_btn);
        title=(TextView)mActivity.findViewById(R.id.toolbar_title_text);
        right_btn=(ImageView)mActivity.findViewById(R.id.toolbar_right_btn);
        right_text_btn=(Button)mActivity.findViewById(R.id.toolbar_right_button);
    }

    /**
     * 隐藏左边按钮
     */
    public void hideLeftButton() {
        left_btn.setVisibility(View.GONE);
    }

    /**
     * 隐藏中间文字
     */
    public void hideCentreTextView() {
        title.setVisibility(View.GONE);
    }

    /**
     * 隐藏右边按钮
     */
    public static void hideRightButton() {
        right_btn.setVisibility(View.GONE);
    }
    /**
     * 隐藏右边文字按钮
     */
    public static void hideRightTextButton() {
        right_text_btn.setVisibility(View.GONE);
    }

    /**
     * 显示左边按钮
     */
    public void showLeftButton() {
        left_btn.setVisibility(View.VISIBLE);
    }

    /**
     * 显示中间文字
     */
    public void showCentreTextView() {
        title.setVisibility(View.VISIBLE);
    }

    /**
     * 显示右边按钮
     */
    public void showRightButton() {
        right_btn.setVisibility(View.VISIBLE);
    }
    /**
     * 显示右边文字按钮
     */
    public void showRightTextButton() {
        right_text_btn.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右边文字按钮的文字
     *
     * @param text
     */
    public void setToolbarRightText(String text) {
        right_text_btn.setText(text);
    }

    /**
     * 设置中间TextView的文字
     *
     * @param text
     */
    public void setToolbarCentreText(String text) {
        title.setText(text);
    }

    /**
     * 设置按钮图片
     * @param imgBtn
     * @param drawable
     */
    public void setimage(ImageView imgBtn, int drawable) {
        imgBtn.setImageResource(drawable);
    }
    /**
     * 设置右边文字按钮图片
     * @param imgBtn
     * @param drawable
     */
    public void setBackground(Button imgBtn, int drawable) {
        imgBtn.setBackgroundResource(drawable);
    }

}

