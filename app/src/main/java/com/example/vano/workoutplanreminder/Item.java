package com.example.vano.workoutplanreminder;

/**
 * Created by vano on 9/18/14.
 */
public class Item {

	private String title;
	private String text;
	private int img;

	public Item(String title, String text, int img){
		this.title = title;
		this.text = text;
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}
}
