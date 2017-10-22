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
import android.support.v7.widget.helper.ItemTouchHelper;
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

public class InputScreen extends AppCompatActivity implements InputScreenRecyclerViewAdapter.InputScreenRecyclerViewAdapaterClickListener{
    private final String VOTE_NAME = "voting";
    public EditText restEdit;

    public ImageButton submitButton;

    public Button voteButton;

    private Voting voting;

    protected InputScreenRecyclerViewAdapter recyclerViewAdapter;

    private RecyclerView votingInputView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_input_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            String inString = savedInstanceState.getString(VOTE_NAME);
            voting = new Voting(inString);
        }else {
            voting = new Voting();
        }
        /*
         * Recycler view setup
         */

        //set the layout manager
        votingInputView = (RecyclerView) this.findViewById(R.id.content_input_recycler_view);


        recyclerViewAdapter = new InputScreenRecyclerViewAdapter(this,voting,this);

        votingInputView.setAdapter(recyclerViewAdapter);
        votingInputView.setLayoutManager(new LinearLayoutManager(this));
        //finishes adapter setup
        /*
         * creat and attach swipe listener
         */
        SwipeToDelete swipeListener = new SwipeToDelete(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        swipeListener.setContext(this);
        swipeListener.setAdapter(recyclerViewAdapter);
        ItemTouchHelper swipable = new ItemTouchHelper(swipeListener);
        swipable.attachToRecyclerView(votingInputView);

        //Defines ID's
        restEdit = (EditText) findViewById(R.id.restaurant);
        submitButton = (ImageButton) findViewById(R.id.submit);
        voteButton = (Button) findViewById(R.id.vote);

        /*
            Defines what happens with user input
         */

        //Defines what happens when the user hits enter on the keyboard
        restEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submitButton.performClick();
                    return true;
                }
                return false;
            }
        });

        //Defines what happens when user clicks the checkbox on edittext
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String s = restEdit.getText().toString();
                if (s.length() < 1){
                    makeToast("Enter a Restaurant");
                }else if (s.length() > 20){
                    makeToast("Name Too Long");
                }else{
                    voting.addItem(s);
                    restEdit.setText("");
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

        //Defines what happens when Ready to vote is pressed
        voteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                confirmSwipeDelete();
            }
        });
    }

    @Override
    public void onVoteItemClick(String voteItemName) {
        makeToast(voteItemName + " was Clicked");
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

    //Method that handles the confirmation to intent over
    private boolean confirmSwipeDelete(){
        String deletePrompt = "Are you sure you want to vote now?";
        tempListener listener = new tempListener();
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).
                setTitle("Confirm Voting").setMessage(deletePrompt).setPositiveButton("yes", listener).
                setNegativeButton("no", listener).show();

        return true;
    }

    //Class that implements the backend of the confirmation to intent over
    class tempListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which){
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                        Intent i = new Intent(InputScreen.this, VotingScreen.class);
                        i.putExtra(VOTE_NAME, voting.serialize());
                        startActivity(i);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        String outString = voting.serialize();
        outState.putString(VOTE_NAME, outString);
    }

}
