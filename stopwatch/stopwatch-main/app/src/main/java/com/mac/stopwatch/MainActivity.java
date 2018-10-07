package com.mac.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvTimeDisplay)
    TextView tvTimeDisplay;
    @BindView(R.id.btnStart)
    Button btnStart;
    @BindView(R.id.btnStop)
    Button btnStop;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private int seconds;
    private int minutes;
    private int hours;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hours = mins / 60;
            secs = secs % 60;
            tvTimeDisplay.setText("" + String.format("%02d", hours)
                    + ":" + String.format("%02d", mins)
                    + ":" + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setButtonEnables(true);
                Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
                setButtonEnables(false);
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });

    }

    private void setButtonEnables(boolean b) {
        btnStop.setEnabled(b);
        btnStart.setEnabled(!b);
    }
}
