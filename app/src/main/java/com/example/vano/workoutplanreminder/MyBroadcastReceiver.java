package com.example.vano.workoutplanreminder;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vano on 9/18/14.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("action ======");
		NotificationManager mng = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		mng.cancel(1);
		Intent intent2 = new Intent(context, MyService.class);

		if(isMyServiceRunning(context, MyService.class))
			context.startService(intent2);

	}

	private boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
