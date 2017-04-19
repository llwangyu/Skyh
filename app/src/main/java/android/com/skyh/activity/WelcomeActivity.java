package android.com.skyh.activity;

import android.com.skyh.R;
import android.com.skyh.base.BaseActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;


public class WelcomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startTimer();
    }
    private static Handler handler = new Handler(Looper.myLooper());
    private void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            handler.post(new Runnable() {
                    @Override
                    public void run() {
                        goToNextActivity();

                    }
                });
            }
        }, 3000);
    }
    private void goToNextActivity(){

            JumpToActivity(LoginActivity.class);
            finish();
        }




}
