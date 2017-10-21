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
import android.widget.TextView;
import android.widget.Toast;
import com.BackendCode.Voting;

import Adapters.InputScreenRecyclerViewAdapter;

import static com.foodfight.R.id.restaurant;

public class InputScreen extends AppCompatActivity implements InputScreenRecyclerViewAdapter.InputScreenRecyclerViewAdapaterClickListener{

    public EditText restEdit;

    public Button submitRestButt;

    private Voting voting;

    private InputScreenRecyclerViewAdapter recyclerViewAdapter;

    private RecyclerView votingInputView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //GABE DONT DELETE THIS
        voting = new Voting();
        voting.addItem("first");
        voting.addItem("second");
        setContentView(R.layout.activity_input_screen);
        /*
         * Recycler view setup
         */


        //set the layout manager
        votingInputView = (RecyclerView) this.findViewById(R.id.content_input_recycler_view);


        recyclerViewAdapter = new InputScreenRecyclerViewAdapter(this,voting,this);

        votingInputView.setAdapter(recyclerViewAdapter);
        votingInputView.setLayoutManager(new LinearLayoutManager(this));
        //finishes adapter setup

        restEdit = (EditText) findViewById(R.id.restaurant);


        //Defines what happens when the user hits enter on the keyboard
        submitRestButt = (Button) findViewById(R.id.enterButt);

        restEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submitRestButt.performClick();
                    //Here is the string
                    String s = restEdit.getText().toString();
                    restEdit.setText("");
                    makeToast(s);
                    return true;
                }
                return false;
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
