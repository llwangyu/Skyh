package android.com.skyh.base;

import android.app.Application;
import android.com.skyh.until.VoiceFileCache;
import android.content.Context;





public class MyApplication extends Application   {

    private static Context mContext;

    private static VoiceFileCache mVoiFileCache;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initImageLoader(getApplicationContext());
        createImageCache();

    }

    public static void initImageLoader(Context context) {

    }

    /*
         * 获取应用的context
         */
    public static Context getContext() {
        return mContext;
    }
    private void createImageCache() {
        mVoiFileCache = new VoiceFileCache();
    }

    public static VoiceFileCache getVoiFileCache() {
        return mVoiFileCache;
    }
}
