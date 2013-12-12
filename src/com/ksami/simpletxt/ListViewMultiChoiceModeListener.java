package com.ksami.simpletxt;

import java.util.HashMap;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;

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
            	// TODO deleteSelected();
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
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Here you can perform updates to the CAB due to
        // an invalidate() request
        return false;
    }
}
