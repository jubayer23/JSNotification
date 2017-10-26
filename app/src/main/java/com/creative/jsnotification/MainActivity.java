package com.creative.jsnotification;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText ed_notification_time;
    private int mJobId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_notification_time = (EditText) findViewById(R.id.ed_notification_time);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void fireNotification(View view) {

        String ed_time = ed_notification_time.getText().toString();

        if (ed_time.isEmpty()) return;

        try {
            int time_to_fire_notification = Integer.parseInt(ed_time);

            ComponentName serviceComponent = new ComponentName(this, TestJobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(mJobId++, serviceComponent);
            builder.setMinimumLatency(time_to_fire_notification * 1000); // wait at least
            builder.setOverrideDeadline((time_to_fire_notification + 3) * 1000); // maximum delay
            //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
            //builder.setRequiresDeviceIdle(true); // device should be idle
            //builder.setRequiresCharging(false); // we don't care if the device is charging or not
            //JobScheduler jobScheduler = getSystemService(JobScheduler.class);
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());

            Toast.makeText(MainActivity.this, "Notification scheduled", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

            Toast.makeText(MainActivity.this, "Please give integer value", Toast.LENGTH_SHORT).show();

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pendingNotification(View view) {

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();

        Toast.makeText(
                MainActivity.this, "Number of notification pending : " + allPendingJobs.size(),
                Toast.LENGTH_SHORT).show();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelAllNotification(View view) {

        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancelAll();
        Toast.makeText(MainActivity.this, "All job cancelled", Toast.LENGTH_SHORT).show();

    }
}
