package wmp.uksw.pl.pracalicencjacka_prototyp2.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by KMacioszek on 2016-03-22.
 */
public class EventService extends IntentService {

    public EventService() {
        super("Default constructor");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public EventService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Handle event action
    }
}
