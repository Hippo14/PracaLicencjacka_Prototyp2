package wmp.uksw.pl.pracalicencjacka_prototyp2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import wmp.uksw.pl.pracalicencjacka_prototyp2.service.EventService;

/**
 * Created by KMacioszek on 2016-03-22.
 */
public class EventAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 1;
    public static final String ACTION = "";


    @Override
    public void onReceive(Context context, Intent intent) {
        // On receive
        Intent i = new Intent(context, EventService.class);

        context.startService(i);
    }
}
