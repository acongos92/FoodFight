package com.foodfight;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import static com.foodfight.R.id.done_vote;
import static com.foodfight.R.id.restaurant;

public class VotingScreen extends AppCompatActivity implements InputScreenRecyclerViewAdapter.InputScreenRecyclerViewAdapaterClickListener{

    public TextView tv;

    private int count = 0;

    public Button vote;

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
        vote = (Button)findViewById(done_vote);
        //Defines what happens when done voting button is pressed
        vote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                confirmToVote();
            }
        });


    }

    private void confirmToVote(){
        String deletePrompt = "Are you sure?";
        tempListenerVote listener = new tempListenerVote();
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).
                setTitle("Confirm End Voting?").setMessage(deletePrompt).setPositiveButton("yes" , listener).
                setNegativeButton("no", listener).show();
    }

    private class tempListenerVote implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    endVote();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    makeToast("Voting did not end!");
                    break;
            }
        }
    }

    private void endVote(){
        //Gets voting result
        String winner = "Trashcan Lid";
        String result = winner + " Wins!";
        makeToast("Hey");
        tempListenerEndVote listener = new tempListenerEndVote();
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).
                setTitle("Confirm End Voting?").setMessage(result).setPositiveButton("Return" , listener).show();
    }

    private class tempListenerEndVote implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            Intent i = new Intent(VotingScreen.this, InputScreen.class);
            startActivity(i);
        }
    }

    @Override
    public void onVoteItemClick(String voteItemName) {
        voting.addVote(voteItemName);
    }

    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void confirmAddVote(String name){
        String deletePrompt = "Do you wish to vote for " + name + "?";
        String yes = "yes";
        String no  ="no";
        tempListener listener = new tempListener();
        listener.setName(name);
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).
                setTitle("Confirm Vote").setMessage(deletePrompt).setPositiveButton(yes , listener).
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

        public void setName(String name) {
            this.name = name;
        }
    }
}
