package com.kanhan.had.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class ArrayListAdapter<U> extends BaseAdapter {
	 
	protected ArrayList<U>itemList = new ArrayList<U>();
	
	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public U getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return  position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}