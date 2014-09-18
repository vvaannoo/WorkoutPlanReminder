package com.example.vano.workoutplanreminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Arrays;
import java.util.List;

public class MyService extends Service {

	private String[] arr = {"text 1", "text 2", "text 3"};
	private List<String> list = Arrays.asList(arr);
	private int currIndex = 0;
    public MyService() {
    }

	@Override
	public void onCreate() {
		System.out.println("service started <-----------------------");

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("************* start again");

		showNotification();
		currIndex++;
		if(currIndex >= list.size()) {
			stopSelf();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public void showNotification(){
		String text = list.get(this.currIndex);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(text)
				.setContentText(text)
				.setTicker(text)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_background)
//				.setStyle(new NotificationCompat.BigPictureStyle()
//								.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.ic_background))
		);
		Intent intent = new Intent(this, MyBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
		NotificationManager mng = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		builder.addAction(R.drawable.ic_tick, "Done", pendingIntent);
		mng.notify(1, builder.build());
	}

    @Override
    public IBinder onBind(Intent intent) {

		return null;
    }

	@Override
	public void onDestroy() {
		System.out.println("service stopped <------------------------");
	}
}
