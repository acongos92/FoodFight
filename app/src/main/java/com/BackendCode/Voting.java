package com.BackendCode;
import java.util.ArrayList;

/**
 * Created by keegan on 10/21/17.
 */

public class Voting {

    private ArrayList<Data> items;

    public Voting() {
        items = new ArrayList<Data>();
    }

    public void addItem(String item) {
        Data temp = new Data(item, 0);
        items.add(temp);
    }

    public void removeItem(String item) {
        for (Data temp : this.items) {
            if (temp.getName().equals(item)) {
                this.items.remove(temp);
                break;
            }
        }
    }

    public int getVote(String item) {
        int vote = 0;
        for (Data temp : this.items) {
            if (temp.restaurant.equals(item)) {
                vote = temp.getVotes();
                break;
            }
        }

        return vote;
    }

    public String getName(int pos) {
        return items.get(pos).getName();
    }

    public void addVote(String item) {
        for (Data temp : this.items) {
            if (temp.restaurant.equals(item)) {
                temp.setVotes(temp.getVotes() + 1);
                break;
            }
        }
    }

    public int size() {
        return items.size();
    }

    private class Data {
        private String restaurant;
        private int votes;

        public Data(String restaurant, int votes){
            this.restaurant = restaurant;
            this.votes = votes;
        }

        public void setName(String restaurant) {
            this.restaurant = restaurant;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }

        public String getName(){
            return this.restaurant;
        }

        public int getVotes() {
            return this.votes;
        }

    }

}
