package com.infinity.infoway.bmu.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.infinity.infoway.bmu.service.Background_Service;

public class AutoStartNotifyReceiver extends BroadcastReceiver
{
    private final String BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            Intent activityIntent = new Intent(context, Background_Service.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.setAction("com.infinity.infoway.agriculture.service.Background_Service");
            context.startActivity(activityIntent);
        }
    }

}