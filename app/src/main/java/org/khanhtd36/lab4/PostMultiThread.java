package org.khanhtd36.lab4;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PostMultiThread extends AppCompatActivity {

    private ProgressBar pbWaiting;
    private TextView tvTopCaption;
    private EditText etInput;
    private Button btnExecute;
    private int globalValue, accum;
    private long startTime;
    private final String PATIENCE = "Some important data is being collected now.\nPlease be patient...wait...";
    private Handler handler;
    private Runnable fgRunnable, bgRunnable;
    private Thread testThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_multi_thread);

        findViewByIds();
        initVariables();


        //Handle onClickListenner

        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input =etInput.getText().toString();
                Toast.makeText(PostMultiThread.this, "You said: "+input, Toast.LENGTH_SHORT).show();
            }
        });

        //start thread
        testThread.start();
        pbWaiting.incrementProgressBy(0);
    }

    private void findViewByIds(){
        tvTopCaption = findViewById(R.id.tv_top_caption);
        pbWaiting = findViewById(R.id.pb_waiting);
        etInput = findViewById(R.id.et_input);
        btnExecute = findViewById(R.id.btn_execute);

    }

    private void initVariables(){
        globalValue = 0;
        accum = 0;
        startTime = System.currentTimeMillis();
        handler = new Handler();

        fgRunnable = new Runnable() {
            @Override
            public void run() {
                try{
                    //Calculate new value
                    int progressStep = 5;
                    double totalTime = (System.currentTimeMillis()-startTime)/1000;
                    synchronized (this){
                        globalValue+=100;
                    }
                    //update UI
                    tvTopCaption.setText(PATIENCE + totalTime + " - "+globalValue);
                    pbWaiting.incrementProgressBy(progressStep);
                    accum += progressStep;

                    //check to stop
                    if (accum >= pbWaiting.getMax()){
                        tvTopCaption.setText(getString(R.string.bg_work_is_over));
                        pbWaiting.setVisibility(View.GONE);
                    }
                }
                catch (Exception e){
                    Log.e("fgRunnable", e.getMessage());
                }
            }
        };

        bgRunnable = new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i=0; i<20; i++){
                        //sleep 1 second
                        Thread.sleep(1000);

                        //Nov talk to main thread
                        //Optinally change some global variable such as : globalValue

                        synchronized (this){
                            globalValue +=1;
                        }

                        handler.post(fgRunnable);
                    }
                }
                catch (Exception e){
                    Log.e("bgRunnable", e.getMessage());
                }
            }

        };

        testThread = new Thread(bgRunnable);
    }
}
