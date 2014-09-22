package com.example.vano.workoutplanreminder;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vano on 9/22/14.
 */
public class FileMng {

	private Context context;
	private String fileName;
	private static HashMap<Integer, Integer> imgThumbMap;

	private static FileMng instance = null;


	public static FileMng getInstance(Context context){
		if(instance == null){
			instance = new FileMng(context);
		}
		return instance;
	}

	private FileMng(Context context){
		this.context = context;
		this.fileName = context.getString(R.string.file_name);
		imgThumbMap = new HashMap<Integer, Integer>();
		imgThumbMap.put(R.drawable.ic_abductor, R.drawable.th_ic_abductor);
		imgThumbMap.put(R.drawable.ic_abs, R.drawable.th_ic_abs);
		imgThumbMap.put(R.drawable.ic_adductor, R.drawable.th_ic_adductor);
		imgThumbMap.put(R.drawable.ic_barbell_bench_press, R.drawable.th_ic_barbell_bench_press);
		imgThumbMap.put(R.drawable.ic_barbell_speed_squat, R.drawable.th_ic_barbell_speed_squat);
		imgThumbMap.put(R.drawable.ic_biceps, R.drawable.th_ic_biceps);
		imgThumbMap.put(R.drawable.ic_bodyweight_standing_calf_raise, R.drawable.th_ic_bodyweight_standing_calf_raise);
	}

	public boolean writeInFile(String str){
		FileOutputStream fileOutputStream;
		try{
			fileOutputStream = context.openFileOutput(fileName, context.MODE_PRIVATE);
			fileOutputStream.write(str.getBytes());
			fileOutputStream.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public File createOrGetFile(){
		try{
			File file = new File(context.getFilesDir(), fileName);
			return file;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<String> readFromFile(){
		try{
			BufferedReader br = new BufferedReader(new FileReader(context.getFilesDir().getPath() + "/" + fileName));
			List<String> lines = new ArrayList<String>();

			String line;
			while((line = br.readLine()) != null){
				lines.add(line);
			}
			return lines;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static String buildFileContent(List<Item> items){
		StringBuilder sb = new StringBuilder();

		for(Item item : items){
			sb.append(item.getId())
					.append("|")
					.append(item.getTitle())
					.append("|")
					.append(item.getText())
					.append("|")
					.append(item.getImg())
					.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public static List<Item> buildObject(List<String> lines){
		List<Item> items = new ArrayList<Item>();
		for(String line : lines){
			String[] words = line.split("\\|");
			Item item = new Item(Integer.parseInt(words[0]), words[1], words[2], Integer.parseInt(words[3]) );
			items.add(item);
		}
		return items;
	}

	public static List<HashMap<String, Object>> convertToMap(List<Item> items){
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
		for (Item item : items){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", item.getId());
			map.put("title", item.getTitle());
			map.put("content", item.getText());
			map.put("photo", item.getImg());
			maps.add(map);
		}
		return maps;
	}

	public static Integer getThumb(int imgId){
		return imgThumbMap.get(imgId);
	}
}
