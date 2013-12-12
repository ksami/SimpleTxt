package com.ksami.simpletxt;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.Toast;

// Implements contextual action bar for rename and delete
public class ListViewMultiChoiceModeListener implements MultiChoiceModeListener {
	private SelectionAdapter mSelection;
	
	ListViewMultiChoiceModeListener(SelectionAdapter directoryList){
		super();
		mSelection = directoryList;
	}
	
	@Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        // Here you can do something when items are selected/de-selected,
        // such as update the title in the CAB
		if(checked) {
			mSelection.setNewSelection(position, checked);
		}
		else {
			mSelection.removeSelection(position);
		}
		mode.setTitle("pos is " + position + ", id is " + id);
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // Respond to clicks on the actions in the CAB
        switch (item.getItemId()) {
            case R.id.action_delete:
            	deleteSelected(mSelection);
                mSelection.clearSelection();
                mode.finish(); // Action picked, so close the CAB
                return true;
            case R.id.action_rename:
                // TODO renameItem();
                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;
        }
    }
    
    private void deleteSelected(SelectionAdapter mSelection) {
    	Set<Integer> checkedItems = mSelection.getCurrentCheckedPosition();
    	
    	assert(checkedItems.isEmpty() == false);
    	
    	Iterator<Integer> iter =checkedItems.iterator();
    	while(iter.hasNext()) {
    		int itemPosition = iter.next();
    		try {
	    		File fileToDelete = new File(mSelection.getContext().getFilesDir()+File.separator+mSelection.getItem(itemPosition)+".txt");
	    		if(fileToDelete.delete()) {
	    			Toast toast = Toast.makeText(mSelection.getContext(), "File deleted", Toast.LENGTH_SHORT);
	    			toast.show();
	    		}
	    		else {
	    			throw new IOException();
	    		}
	    		mSelection.removeSelection(itemPosition);
	    		mSelection.notifyDataSetChanged();
    		}
    		catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate the menu for the CAB
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // Here you can make any necessary updates to the activity when
        // the CAB is removed. By default, selected items are deselected/unchecked.
    	mSelection.clearSelection();
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Here you can perform updates to the CAB due to
        // an invalidate() request
        return false;
    }
}
