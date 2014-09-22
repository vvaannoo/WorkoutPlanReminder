package com.example.vano.workoutplanreminder;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {


	private List<Item> list;
	private SharedPreferences preferences;

	private static Integer currIndex;
    public MyService() {
    }

	@Override
	public void onCreate() {

		preferences = this.getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE);

		currIndex = preferences.getInt(getString(R.string.index), 0);

		Toast.makeText(this, "ind: " + currIndex, Toast.LENGTH_SHORT).show();
//		list = new ArrayList<Item>();
//		System.out.println("service started <-----------------------");
//
//		list.add(new Item(1, "Barbell Speed Squat", "Sets: 2, Reps: 15 (lite)", R.drawable.ic_barbell_speed_squat));
//		list.add(new Item(2, "Abductor", "Sets: 2, Reps: 12", R.drawable.ic_abductor));
//		list.add(new Item(3, "Adductor", "Sets: 2, Reps: 12", R.drawable.ic_adductor));
//		list.add(new Item(4, "BodyWeight Standing Calf Raise", "Sets: 1, Reps: 15", R.drawable.ic_bodyweight_standing_calf_raise));
//		list.add(new Item(5, "Barbell Bench Press", "Sets: 1, Reps: 20 (lite)", R.drawable.ic_barbell_bench_press));
//		list.add(new Item(6, "Barbell Bench Press", "Sets: 1, Reps: 12 (lite)", R.drawable.ic_barbell_bench_press));
//		list.add(new Item(7, "Barbell Bench Press", "Sets: 2, Reps: 10", R.drawable.ic_barbell_bench_press));
//		list.add(new Item(8, "Pectoral Machine", "Sets: 1, Reps: 15 (lite)", R.drawable.ic_pectoral_machine));
//		list.add(new Item(9, "Pectoral Machine", "Sets: 2, Reps: 12", R.drawable.ic_pectoral_machine));
//		list.add(new Item(10, "Shoulder Press", "Sets: 1, Reps: 12 (lite)", R.drawable.ic_shoulder_press));
//		list.add(new Item(11, "Shoulder Press", "Sets: 2, Reps: 10", R.drawable.ic_shoulder_press));
//		list.add(new Item(12, "Delts machine", "Sets: 2, Reps: 12", R.drawable.ic_delts_machine));
//		list.add(new Item(13, "Vertical Traction", "Sets: 2, Reps: 12", R.drawable.ic_vertical_traction));
//		list.add(new Item(14, "Low Row", "Sets: 1, Reps: 12", R.drawable.ic_low_row));
//		list.add(new Item(15, "Low Row", "Sets: 1, Reps: 10 (Each Hand)", R.drawable.ic_low_row));
//		list.add(new Item(16, "Biceps", "Sets: 2, Reps: 10", R.drawable.ic_biceps));
//		list.add(new Item(17, "Triceps", "Sets: 2, Reps: 10", R.drawable.ic_triceps));
//		list.add(new Item(18, "Abs", "Sets: 2, Reps: 12", R.drawable.ic_abs));


		FileMng fileMng = FileMng.getInstance(this);
		list = FileMng.buildObject(fileMng.readFromFile());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("************* start again");
		int flag = -100;
		if(intent != null){
			flag = intent.getIntExtra(getString(R.string.flag), -100);
		}
		Toast.makeText(this, "flag: " + flag, Toast.LENGTH_SHORT).show();

		if(flag == -100){
			return super.onStartCommand(intent, flags, startId);
		} else {
			currIndex = preferences.getInt(getString(R.string.index), 0);
			currIndex += flag;
			SharedPreferences.Editor editor = preferences.edit();
			if(currIndex >= list.size()){
				editor.remove(getString(R.string.index));
				editor.commit();
				exit();
				return super.onStartCommand(intent, flags, startId);
			}
			showNotification(flag);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public void showNotification(int dir){

		currIndex = preferences.getInt(getString(R.string.index), 0);
		SharedPreferences.Editor editor = preferences.edit();
		currIndex += dir;

		if(currIndex < 0){
			currIndex = 0;
		}

		editor.putInt(getString(R.string.index), currIndex);
		editor.commit();

		System.out.println("notify " + currIndex);
		Item item = list.get(currIndex);
		String title = (currIndex + 1) + ". " + item.getTitle();

		Intent intentNext = new Intent(this, MyNextReceiver.class);
		Intent intentPrevious = new Intent(this, MyPreviousReceiver.class);
		intentNext.putExtra(getString(R.string.flag), 1);
		intentPrevious.putExtra(getString(R.string.flag), -1);
		PendingIntent pendingIntentNext = PendingIntent.getBroadcast(this, 1, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);

		PendingIntent pendingIntentPrevious = PendingIntent.getBroadcast(this, 1, intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender()
				//.setContentIcon(R.drawable.ic_launcher)
				.setBackground(BitmapFactory.decodeResource(getResources(), item.getImg()))
				.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_next, getString(R.string.next), pendingIntentNext).build())
				.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_previous, getString(R.string.previous), pendingIntentPrevious).build())
		;


		Notification notif = new NotificationCompat.Builder(this)
				.setContentTitle(title)
				.setNumber(currIndex + 1)
				.setContentText(item.getText())
				.setTicker(title + ": " + item.getText())
				.setSmallIcon(R.drawable.ic_launcher)
				.extend(extender)
				.setStyle(new NotificationCompat.BigPictureStyle()
						.bigPicture(BitmapFactory.decodeResource(getResources(), item.getImg()))
						.setSummaryText(item.getText()))
				.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_previous, getString(R.string.previous), pendingIntentPrevious).build())
				.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_next, getString(R.string.next), pendingIntentNext).build())
				.build();
		NotificationManagerCompat.from(this).notify(1, notif);
	}

    @Override
    public IBinder onBind(Intent intent) {

		return null;
    }

	@Override
	public void onDestroy() {
		System.out.println("service stopped <------------------------");
	}

	public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	private void exit(){
		NotificationManagerCompat.from(this).cancelAll();
		stopSelf();
	}
}
