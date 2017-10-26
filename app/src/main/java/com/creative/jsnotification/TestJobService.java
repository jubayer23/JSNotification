package com.creative.jsnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

/**
 * Created by jubayer on 10/26/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TestJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);

//Create the intent that’ll fire when the user taps the notification//

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.androidauthority.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("My notification");
        mBuilder.setContentText("Hello World!");

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random rand = new Random();
        int  random_number = rand.nextInt(50) + 1;

        mNotificationManager.notify(random_number, mBuilder.build());

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
