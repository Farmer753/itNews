package ru.dpwg.itnews.ui.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import javax.inject.Inject;

import androidx.core.app.NotificationCompat;
import ru.dpwg.itnews.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtil {
    private Context context;
    private static final String CHANNELID = "CHANNELID";
    private NotificationManager notificationManager;

    @Inject
    public NotificationUtil(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    public NotificationCompat.Builder getBuilder() {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNELID,
                    "Channel name",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(context, CHANNELID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        return builder;
    }

    public void showNotification(
            int id,
            String title,
            String text
    ) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = getBuilder();

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(2);

        Notification notification = builder.build();


        notificationManager.notify(id, notification);
    }
}
