package com.example.vano.workoutplanreminder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vano on 8/11/14.
 */
public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private List<Item> data;
	private static LayoutInflater inflater = null;

	public LazyAdapter(Activity activity, List<Item> data){
		this.activity = activity;
		this.data = data;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return data.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if(vi == null){
			vi = inflater.inflate(R.layout.row_item, null);
		}
		ImageView imageView = (ImageView) vi.findViewById(R.id.photo);
		TextView titleView = (TextView) vi.findViewById(R.id.title);
		TextView contentView = (TextView) vi.findViewById(R.id.content);
		Integer imageId = data.get(position).getImg();
		imageId = FileMng.getThumb(imageId);
		if(imageId == null) imageId = data.get(position).getImg();
		imageView.setImageDrawable(activity.getResources().getDrawable(imageId));
		titleView.setText( data.get(position).getId() + ". " + data.get(position).getTitle());
		contentView.setText( data.get(position).getText());

		return vi;
	}
}
