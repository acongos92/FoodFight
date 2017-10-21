package com.foodfight;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.BackendCode.Voting;

import Adapters.InputScreenRecyclerViewAdapter;
import Adapters.SwipeToDelete;

import static com.foodfight.R.id.restaurant;

public class VotingScreen extends AppCompatActivity implements InputScreenRecyclerViewAdapter.InputScreenRecyclerViewAdapaterClickListener{

    public TextView tv;

    private int count = 0;

    private Voting voting;

    protected InputScreenRecyclerViewAdapter recyclerViewAdapter;

    private RecyclerView votingInputView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        setContentView(R.layout.voting_screen);
        voting = new Voting();
        /*
         * Recycler view setup
         */

        //set the layout manager
        votingInputView = (RecyclerView) this.findViewById(R.id.content_voting_recycler_view);


        recyclerViewAdapter = new InputScreenRecyclerViewAdapter(this,voting,this);

        votingInputView.setAdapter(recyclerViewAdapter);
        votingInputView.setLayoutManager(new LinearLayoutManager(this));
        //finishes adapter setup

        //Defines ID's
        tv = (TextView)findViewById(R.id.num_votes);

        /*
            Defines what happens with user input
         */


    }


    @Override
    public void onVoteItemClick(String voteItemName) {
        voting.addVote(voteItemName);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void confirmAddVote(String name){
        String deletePrompt = "Do you wish to delete " + name + "?";
        String yes = "yes";
        String no  ="no";
        tempListener listener = new tempListener();
        listener.setName(name);
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).
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
                    voting.addVote(name);
                    makeToast("Vote was added");
                    count++;
                    tv.setText("Number of votes: " + count);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    makeToast("Vote not counted");
                    break;
            }
        }

        public void setName(String name){
            this.name = name;
        }

    }
}
