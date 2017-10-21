package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import Adapters.InputScreenRecyclerViewAdapter;

import static android.support.v7.appcompat.R.styleable.AlertDialog;

/**
 * Created by zigza on 10/21/2017.
 */

public class SwipeToDelete extends ItemTouchHelper.SimpleCallback {
    /*
     * holder is a reference to the inputScreenRecyclerView view holder, exists to get the name of
     * the item want to delete
     *
     * context allows us to display a prompt on the users screen confirming deletion
     *
     * adapter is needed to update the recyclerview adapter if an item is deleted
     */
    private InputScreenRecyclerViewAdapter.InputScreenViewHolder holder;
    private Context mContext;
    private InputScreenRecyclerViewAdapter adapter;
    public SwipeToDelete(int dragDir, int swipeDir){
        super(dragDir, swipeDir);
    }
    @Override
    //have to override this, dont use it though
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        return true;
    }
    //this just calls the confirm delete screen
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        int pos = viewHolder.getAdapterPosition();
        InputScreenRecyclerViewAdapter.InputScreenViewHolder holder = (InputScreenRecyclerViewAdapter.InputScreenViewHolder) viewHolder;
        this.holder = holder;
        String name = (String) holder.userSubmissionView.getText();
        confirmDelete(name);
    }
    //this context has to be set before using the swipe, TODO:implement error checking if have time
    public void setContext(Context context){
        this.mContext = context;
    }
    //this also needs to be done to properly update the adapter TODO:implement error checking if have time
    public void setAdapter(InputScreenRecyclerViewAdapter adapter){
        this.adapter = adapter;
    }
    private void confirmDelete(String name){
        String deletePrompt = "Do you wish to delete " + name + "?";
        String yes = "yes";
        String no  ="no";
        tempListener listener = new tempListener();
        listener.setName(name);
        new AlertDialog.Builder(mContext).setIcon(android.R.drawable.ic_dialog_alert).
                setTitle("Confirm Delete").setMessage(deletePrompt).setPositiveButton(yes , listener).
                setNegativeButton("no", listener).show();
    }




    private class tempListener implements DialogInterface.OnClickListener {
        private String name;
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    dialog.dismiss();
                    holder.removeFromAdapater(name);
                    adapter.notifyDataSetChanged();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }

        public void setName(String name){
            this.name = name;
        }

    }

}
