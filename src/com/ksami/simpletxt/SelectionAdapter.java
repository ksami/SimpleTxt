package com.ksami.simpletxt;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

// Tracks selections of items in the List<String> objects
public class SelectionAdapter extends ArrayAdapter<String> {
	private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();
	 
    public SelectionAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public void setNewSelection(int position, boolean value) {
        mSelection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = mSelection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition() {
        return mSelection.keySet();
    }

    public void removeSelection(int position) {
        mSelection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        mSelection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);//let the adapter handle setting up the row views
        v.setBackgroundColor(v.getResources().getColor(android.R.color.background_light)); //default color
         
        if (mSelection.get(position) != null) {
            v.setBackgroundColor(v.getResources().getColor(android.R.color.holo_blue_light));// this is a selected position so make it red
        }
        return v;
    }
}
