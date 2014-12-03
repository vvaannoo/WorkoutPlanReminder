package com.example.vano.workoutplanreminder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;


public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
		ListView listView = (ListView) findViewById(R.id.list);

		FileMng fileMng = FileMng.getInstance(getApplicationContext());
		fileMng.createOrGetFile();
		List<Item> data = FileMng.buildObject(fileMng.readFromFile());


		LazyAdapter adapter = new LazyAdapter(this, data);
		listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
