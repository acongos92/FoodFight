package com.BackendCode;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by keegan on 10/21/17.
 */

public class Voting {

    private ArrayList<Data> items;

    public Voting() {
        items = new ArrayList<Data>();
    }

    public Voting(String serialized) {
        items = new ArrayList<Data>();
        while (serialized.length() > 0) {
            String item = removeWord(serialized);
            serialized = updateSerialize(serialized);
            serialized = removeSeparator(serialized);
            int vote = Integer.parseInt(removeWord(serialized));

            if (serialized.length() > 1) {
                serialized = updateSerialize(serialized);
                serialized = removeSeparator(serialized);
            }
            addItem(item);
            setVote(item, vote);
            if (serialized.length() == 1) {
                break;
            }
        }
    }
    private String updateSerialize(String serialized) {
        int pos = 0;
        while(serialized.charAt(pos) != '\n' && pos < serialized.length()) {
            pos++;
        }
        return serialized.substring(pos);
    }

    private String removeSeparator(String serialized) {

        return serialized.substring(1);
    }

    private String removeWord(String serialized) {
            if(serialized.length() > 1) {
                int pos = 0;
                while(serialized.charAt(pos) != '\n' && pos <= serialized.length()) {
                    pos++;
                }
                serialized = serialized.substring(0,pos);
            }

        return serialized;
    }

    public String serialize() {
        String serialized = "";
        for (Data temp : this.items) {
            if(serialized.length() == 0){
                serialized = serialized + temp.getName() + "\n" + temp.getVotes();
            }else {
                serialized = serialized + "\n" + temp.getName() + "\n" + temp.getVotes();
            }
        }

        return serialized;
    }

    public String pickVoteWinner() {
        String winner = "";
        int votes = 0;
        for (Data temp : this.items) {
            if(temp.getVotes() > votes) {
                winner = temp.getName();
                votes = temp.getVotes();
            }
        }

        return winner;
    }

    public String pickRandomWinner() {
        Random r = new Random();
        int winner = r.nextInt(this.size());
        return this.items.get(winner).getName();
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

    public void setVote(String item, int vote) {
        for (Data temp : this.items) {
            if (temp.restaurant.equals(item)) {
                temp.setVotes(vote);
                break;
            }
        }
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
