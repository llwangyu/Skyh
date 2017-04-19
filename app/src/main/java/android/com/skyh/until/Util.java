package android.com.skyh.until;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * @author weiguo.ren
 */
public class Util {

    // 日志Tag
    private static String TAG = "android.com.skyh";
    // 日志打印开关
    private static boolean print = true;

    public static void print(String msg) {
        if (print) {
            Log.d(TAG, msg);
        }
    }

    public static void print(CharSequence msg) {
        if (msg != null) {
            print(msg.toString());
        }
    }

    public static void print(String tag, String msg) {
        print(msg);
        if (print) {
            Log.i(tag, msg);
        }
    }

    /**
     * 发送Toast
     *
     * @param mContext
     * @param text
     */
    public static void sendToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 发送Toast
     *
     * @param /mContext
     * @param /text
     */
    public static void sendToast(Context context,int StringResId) {
        sendToast(context, context
                .getString(StringResId));
    }

    /**
     * 发送Toast
     *
     * @param mContext
     * @param text
     */
    public static void sendToast(Context mContext, CharSequence text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());

    }
    public static void hiddenInputMethod(Activity activity,
                                         final EditText editView) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editView.setInputType(InputType.TYPE_NULL);
        } else {
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod(
                        "setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editView, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
