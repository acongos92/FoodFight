package com.foodfight;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import static com.foodfight.R.id.restaurant;

public class InputScreen extends AppCompatActivity implements InputScreenRecyclerViewAdapter.InputScreenRecyclerViewAdapaterClickListener{

    public EditText restEdit;

    public ImageButton submitButton;

    private Voting voting;

    protected InputScreenRecyclerViewAdapter recyclerViewAdapter;

    private RecyclerView votingInputView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        setContentView(R.layout.activity_input_screen);
        voting = new Voting();
        /*
         * Recycler view setup
         */

        //set the layout manager
        votingInputView = (RecyclerView) this.findViewById(R.id.content_input_recycler_view);


        recyclerViewAdapter = new InputScreenRecyclerViewAdapter(this,voting,this);

        votingInputView.setAdapter(recyclerViewAdapter);
        votingInputView.setLayoutManager(new LinearLayoutManager(this));
        //finishes adapter setup

        //Defines ID's
        restEdit = (EditText) findViewById(R.id.restaurant);
        submitButton = (ImageButton) findViewById(R.id.submit);

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
}
