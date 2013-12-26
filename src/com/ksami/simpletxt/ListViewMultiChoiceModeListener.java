package com.ksami.simpletxt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	private static int numItemsSelected=0;
	
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
			numItemsSelected++;
		}
		else {
			mSelection.removeSelection(position);
			numItemsSelected--;
		}
		mode.setTitle(numItemsSelected + " items selected");
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // Respond to clicks on the actions in the CAB
        switch (item.getItemId()) {
            case R.id.action_delete:
            	deleteSelected(mSelection);
                mSelection.clearSelection();
                
                numItemsSelected=0;
                
                mode.finish(); // Action picked, so close the CAB
                return true;
            case R.id.action_rename:
                // TODO renameItem();
            	
                numItemsSelected=0;
                
                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;
        }
    }
    
    private void deleteSelected(SelectionAdapter mSelection) {
		MainActivity main = (MainActivity) mSelection.getContext();
    	Object[] arr = mSelection.getCurrentCheckedPosition().toArray();
    	
    	// Ensure list of checked items is not empty
    	assert(arr.length != 0);
    	
    	Integer[] integerArray = Arrays.copyOf(arr, arr.length, Integer[].class);
    	
    	for(int i=0; i<integerArray.length; i++) {
    		int itemPosition = integerArray[i];
    		try {
    			String fileName = mSelection.getItem(itemPosition);
	    		File fileToDelete = new File(mSelection.getContext().getFilesDir()+File.separator+mSelection.getItem(itemPosition)+".txt");
	    		if(fileToDelete.delete()) {

	    			List<String> files = main.fileList;
	    			if(files.indexOf(fileName) == -1) {
	    				Toast toast = Toast.makeText(mSelection.getContext(), "Cannot find file", Toast.LENGTH_SHORT);
		    			toast.show();
	    				throw new IOException();
	    			}
	    			Toast toast = Toast.makeText(mSelection.getContext(), "File deleted", Toast.LENGTH_SHORT);
	    			toast.show();
	    		}
	    		else {
	    			Toast toast = Toast.makeText(mSelection.getContext(), "Failed to delete", Toast.LENGTH_SHORT);
	    			toast.show();
	    			throw new IOException();
	    		}
    		}
    		catch(IOException e) {
    			e.printStackTrace();
    		}
    		mSelection.removeSelection(itemPosition);
    	}
		main.updateList(main.getFilesDir());
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
