package com.example.vano.workoutplanreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

	FileMng fileMng;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		final MainActivity me = this;
		View startBtn = findViewById(R.id.start_btn);
		View stopBtn = findViewById(R.id.stop_btn);

		startBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), MyService.class);
				intent.putExtra(getString(R.string.flag), 0);
				startService(intent);

				me.finish();
			}
		});

		stopBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), MyService.class);
				stopService(intent);
				NotificationManagerCompat.from(getApplicationContext()).cancelAll();
			}
		});



		initFile();
		//fileMng.writeInFile("vano" + System.lineSeparator() + "atabegashvili");

		List list = new ArrayList<Item>();
		list.add(new Item(1, "Barbell Speed Squat (lite)", "1x15", R.drawable.ic_barbell_speed_squat));
		list.add(new Item(2, "Barbell Speed Squat", "2x10", R.drawable.ic_barbell_speed_squat));
		list.add(new Item(3, "Abductor", "2x12", R.drawable.ic_abductor));
		list.add(new Item(4, "Adductor", "2x12", R.drawable.ic_adductor));
		list.add(new Item(5, "BodyWeight Standing Calf Raise", "2x15, Each Leg: 1x15", R.drawable.ic_bodyweight_standing_calf_raise));
		list.add(new Item(6, "Barbell Bench Press (lite)", "1x20", R.drawable.ic_barbell_bench_press));
		list.add(new Item(7, "Barbell Bench Press", "3x10/8", R.drawable.ic_barbell_bench_press));
		list.add(new Item(8, "Pectoral Machine (lite)", "1x20", R.drawable.ic_pectoral_machine));
		list.add(new Item(9, "Pectoral Machine", "2x12", R.drawable.ic_pectoral_machine));
		list.add(new Item(10, "Shoulder Press (lite)", "1x15", R.drawable.ic_shoulder_press));
		list.add(new Item(11, "Shoulder Press", "2x10/8", R.drawable.ic_shoulder_press));
		list.add(new Item(12, "Delts machine", "5x10/12", R.drawable.ic_delts_machine));
		list.add(new Item(13, "Barbell Close Grip Bench Press machine (lite)", "1x20", R.drawable.ic_barbell_close_grip_bench_press));
		list.add(new Item(14, "Barbell Close Grip Bench Press machine", "2x10", R.drawable.ic_barbell_close_grip_bench_press));
		list.add(new Item(15, "Triceps", "2x10", R.drawable.ic_triceps));
		list.add(new Item(16, "Abs", "1x30", R.drawable.ic_abs));
		list.add(new Item(17, "Abs Legs", "1x20", R.drawable.ic_abs_legs));
		list.add(new Item(18, "Lower Back", "1x20", R.drawable.ic_back_extension_machine));

		list.add(new Item(-1, "", "", R.drawable.ic_launcher));

		list.add(new Item(1, "Leg Curl (lite)", "1x10", R.drawable.ic_leg_curl));
		list.add(new Item(2, "Leg Curl", "2x10/12", R.drawable.ic_leg_curl));
		list.add(new Item(3, "BodyWeight Standing Calf Raise", "1x15", R.drawable.ic_bodyweight_standing_calf_raise));
		list.add(new Item(4, "Vertical Traction (lite)", "1x15", R.drawable.ic_vertical_traction));
		list.add(new Item(5, "Vertical Traction", "2x10/12", R.drawable.ic_vertical_traction));
		list.add(new Item(6, "Pulley", "2x10", R.drawable.ic_pulley));
		list.add(new Item(7, "Low Row (each hand)", "2x10", R.drawable.ic_low_row));
		list.add(new Item(8, "Pull Down", "2x10/12", R.drawable.ic_pull_down));
		list.add(new Item(9, "Dumbbell Bicep Curl", "3x10/8", R.drawable.ic_dumbbell_bicep_curl));
		list.add(new Item(10, "Barbell Close Grip Preacher Curl", "2x10/8", R.drawable.ic_biceps));
		list.add(new Item(11, "Abs", "1x25", R.drawable.ic_abs));
		list.add(new Item(12, "Abs with Legs", "1x20", R.drawable.ic_abs_legs));
		list.add(new Item(13, "Ab Crunch Machine", "1x20", R.drawable.ic_ab_crunch_machine));
		list.add(new Item(14, "Lower Back Curl", "1x20", R.drawable.ic_lower_back_curl));


		fileMng.writeInFile(FileMng.buildFileContent(list));

//		List<String> lines = fileMng.readFromFile();
//
//		List<Item> items = FileMng.buildObject(lines);
//
//		for(Item item : items){
//			System.out.println( item.getTitle());
//		}

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.list_btn){
			startActivity(new Intent(getApplicationContext(), ListActivity.class));
		}

        return super.onOptionsItemSelected(item);
    }

	private void initFile(){

		if(!FileMng.isExternalStorageWritable()){
			System.out.println("exteranl storage is not available");
		}
		fileMng = FileMng.getInstance(this);

		File file = fileMng.createOrGetFile();
		if(file == null){
			System.out.println("could not create file");
		}
	}
}
