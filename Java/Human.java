public class Human extends User {

    private boolean human;

    public Human(String subreddit, String[] keyPhrase, String comment, boolean upvote, boolean downvote, boolean human) {
        super(subreddit, keyPhrase, comment, upvote, downvote);
        this.human = human;
    }

    //getter

    public boolean isHuman() {
        return human;
    }

    //setter

    public void setHuman(boolean human) {
        this.human = human;
    }

    @Override
    public String toString() {
        return "Human{" +
                "human=" + human +
                '}';
    }

}
