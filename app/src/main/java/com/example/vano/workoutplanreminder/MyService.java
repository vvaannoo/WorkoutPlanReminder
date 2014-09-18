package com.example.vano.workoutplanreminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {


	private List<Item> list;


	private int currIndex = 0;
    public MyService() {
    }

	@Override
	public void onCreate() {
		list = new ArrayList<Item>();
		System.out.println("service started <-----------------------");
		list.add(new Item("Barbell Speed Squat", "Sets: 2, Reps: 15 (lite)", R.drawable.ic_barbell_speed_squat));
		list.add(new Item("Abductor", "Sets: 2, Reps: 12", R.drawable.ic_abductor));
		list.add(new Item("Adductor", "Sets: 2, Reps: 12", R.drawable.ic_adductor));
		list.add(new Item("BodyWeight Standing Calf Raise", "Sets: 1, Reps: 15", R.drawable.ic_bodyweight_standing_calf_raise));
		list.add(new Item("Barbell Bench Press", "Sets: 1, Reps: 20 (lite)", R.drawable.ic_barbell_bench_press));
		list.add(new Item("Barbell Bench Press", "Sets: 1, Reps: 12 (lite)", R.drawable.ic_barbell_bench_press));
		list.add(new Item("Barbell Bench Press", "Sets: 2, Reps: 10", R.drawable.ic_barbell_bench_press));
		list.add(new Item("Pectoral Machine", "Sets: 1, Reps: 15 (lite)", R.drawable.ic_pectoral_machine));
		list.add(new Item("Pectoral Machine", "Sets: 2, Reps: 12", R.drawable.ic_pectoral_machine));
		list.add(new Item("Shoulder Press", "Sets: 1, Reps: 12 (lite)", R.drawable.ic_shoulder_press));
		list.add(new Item("Shoulder Press", "Sets: 2, Reps: 10", R.drawable.ic_shoulder_press));
		list.add(new Item("Delts machine", "Sets: 2, Reps: 12", R.drawable.ic_delts_machine));
		list.add(new Item("Vertical Traction", "Sets: 2, Reps: 12", R.drawable.ic_vertical_traction));
		list.add(new Item("Low Row", "Sets: 1, Reps: 12", R.drawable.ic_low_row));
		list.add(new Item("Low Row", "Sets: 1, Reps: 10 (Each Hand)", R.drawable.ic_low_row));
		list.add(new Item("Biceps", "Sets: 2, Reps: 10", R.drawable.ic_biceps));
		list.add(new Item("Triceps", "Sets: 2, Reps: 10", R.drawable.ic_triceps));
		list.add(new Item("Abs", "Sets: 2, Reps: 12", R.drawable.ic_abs));
		list.add(new Item("Upper Leg", "Sets: 1, Reps: 12 (lite)", R.drawable.ic_upper_leg));
		list.add(new Item("Upper Leg", "Sets: 2, Reps: 10", R.drawable.ic_upper_leg));


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
		Item item = list.get(this.currIndex);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(item.getTitle())
				.setContentText(item.getText())
				.setTicker(item.getTitle() + ": " + item.getText())
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), item.getImg())
//				.setStyle(new NotificationCompat.BigPictureStyle()
//								.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.ic_background))
		);
		Intent intent = new Intent(this, MyBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
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
