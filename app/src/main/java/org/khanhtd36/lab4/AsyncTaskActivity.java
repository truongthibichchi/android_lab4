package org.khanhtd36.lab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AsyncTaskActivity extends AppCompatActivity {

    private Button btnQuickJob, btnSlowJob;
    private TextView tvStatus;
    private SlowTask slowTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        findViewByIds();

        //Handle onClickListenner
        btnQuickJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                tvStatus.setText(sdf.format(new Date()));
            }
        });


        btnSlowJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Init slowtask
                slowTask = new SlowTask(AsyncTaskActivity.this, tvStatus);
                slowTask.execute();
            }
        });
    }

    private void findViewByIds() {
        btnQuickJob = (Button) findViewById(R.id.btn_quick_job);
        btnSlowJob = (Button) findViewById(R.id.btn_slow_job);
        tvStatus = (TextView) findViewById(R.id.tv_status);

    }

}
