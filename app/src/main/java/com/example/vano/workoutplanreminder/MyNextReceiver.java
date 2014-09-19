package com.example.vano.workoutplanreminder;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vano on 9/18/14.
 */
public class MyNextReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("next");

		int flag = intent.getIntExtra(context.getString(R.string.flag), 1);
		Intent intent2 = new Intent(context, MyService.class);
		intent2.putExtra(context.getString(R.string.flag), flag);

		if(MyService.isMyServiceRunning(context, MyService.class))
			context.startService(intent2);

	}


}
