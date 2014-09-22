package com.example.vano.workoutplanreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		View listBtn = findViewById(R.id.list_btn);


		startBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), MyService.class);
				intent.putExtra(getString(R.string.flag), 0);
				startService(intent);
			//	me.finish();
			}
		});

		stopBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), MyService.class);
				stopService(intent);
			}
		});

		listBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(), ListActivity.class));
			}
		});

		initFile();
		//fileMng.writeInFile("vano" + System.lineSeparator() + "atabegashvili");



		List list = new ArrayList<Item>();

		list.add(new Item(1, "Barbell Speed Squat", "Sets: 2, Reps: 15 (lite)", R.drawable.ic_barbell_speed_squat));
		list.add(new Item(2, "Abductor", "Sets: 2, Reps: 12", R.drawable.ic_abductor));
		list.add(new Item(3, "Adductor", "Sets: 2, Reps: 12", R.drawable.ic_adductor));
		list.add(new Item(4, "BodyWeight Standing Calf Raise", "Sets: 1, Reps: 15", R.drawable.ic_bodyweight_standing_calf_raise));
		list.add(new Item(5, "Barbell Bench Press", "Sets: 1, Reps: 20 (lite)", R.drawable.ic_barbell_bench_press));
		list.add(new Item(6, "Barbell Bench Press", "Sets: 1, Reps: 12 (lite)", R.drawable.ic_barbell_bench_press));
		list.add(new Item(7, "Barbell Bench Press", "Sets: 2, Reps: 10", R.drawable.ic_barbell_bench_press));
		list.add(new Item(8, "Pectoral Machine", "Sets: 1, Reps: 15 (lite)", R.drawable.ic_pectoral_machine));
		list.add(new Item(9, "Pectoral Machine", "Sets: 2, Reps: 12", R.drawable.ic_pectoral_machine));
		list.add(new Item(10, "Shoulder Press", "Sets: 1, Reps: 12 (lite)", R.drawable.ic_shoulder_press));
		list.add(new Item(11, "Shoulder Press", "Sets: 2, Reps: 10", R.drawable.ic_shoulder_press));
		list.add(new Item(12, "Delts machine", "Sets: 2, Reps: 12", R.drawable.ic_delts_machine));
		list.add(new Item(13, "Vertical Traction", "Sets: 2, Reps: 12", R.drawable.ic_vertical_traction));
		list.add(new Item(14, "Low Row", "Sets: 1, Reps: 12", R.drawable.ic_low_row));
		list.add(new Item(15, "Low Row", "Sets: 1, Reps: 10 (Each Hand)", R.drawable.ic_low_row));
		list.add(new Item(16, "Biceps", "Sets: 2, Reps: 10", R.drawable.ic_biceps));
		list.add(new Item(17, "Triceps", "Sets: 2, Reps: 10", R.drawable.ic_triceps));
		list.add(new Item(18, "Abs", "Sets: 2, Reps: 12", R.drawable.ic_abs));

		fileMng.writeInFile(FileMng.buildFileContent(list));

		List<String> lines = fileMng.readFromFile();

		List<Item> items = FileMng.buildObject(lines);

		for(Item item : items){
			System.out.println( item.getTitle());
		}

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
        if (id == R.id.action_settings) {
            return true;
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
