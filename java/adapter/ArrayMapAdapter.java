package com.kanhan.had.adapter;

import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ArrayMapAdapter<U> extends BaseAdapter {
	 
	protected ArrayMap<Integer,U>itemMap = new ArrayMap<Integer,U>();
	
	@Override
	public int getCount() {
		return itemMap.size();
	}

	@Override
	public U getItem(int position) {
		return itemMap.valueAt(position);
	}

	@Override
	public long getItemId(int position) {
		return  itemMap.keyAt(position);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
