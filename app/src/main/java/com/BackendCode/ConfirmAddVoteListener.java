package com.BackendCode;

import android.content.DialogInterface;

import Adapters.InputScreenRecyclerViewAdapter;

/**
 * Created by zigza on 10/21/2017.
 */

public class ConfirmAddVoteListener implements DialogInterface.OnClickListener {
    private String name;
    private InputScreenRecyclerViewAdapter.InputScreenViewHolder holder;
    private InputScreenRecyclerViewAdapter adapter;
    private Voting voting;
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                dialog.dismiss();
                voting.addVote(this.name);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
        }
    }

    public void setName(String name){
        this.name = name;
    }
    public void setHolder(InputScreenRecyclerViewAdapter.InputScreenViewHolder holder){
        this.holder = holder;
    }
    public void setAdapter(InputScreenRecyclerViewAdapter adapter){
        this.adapter = adapter;
    }
    public void setVoting(Voting voting){
        this.voting = voting;
    }
}
