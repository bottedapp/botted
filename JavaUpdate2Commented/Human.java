import java.io.IOException;
import java.util.ArrayList;

public class Human extends User {

    /**
     * Private variable
     */
    private boolean human;

    /**
     * Default constructor
     * @throws IOException
     * @throws InterruptedException
     */
    public Human() throws IOException, InterruptedException {
        super();
    }

    /**
     * Constructor
     * @param subreddit The specific subreddit
     * @param comment A comment on a post
     * @param upvote An upvote on a post or comment
     * @param downvote A downvote on a post or comment
     * @param human Whether it is a human account
     * @throws IOException
     * @throws InterruptedException
     */
    public Human(String subreddit, String comment, boolean upvote, boolean downvote, boolean human) throws IOException, InterruptedException {
        super(subreddit, comment, upvote, downvote);
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

    /**
     * Return correct response according to results
     * @param keyPhrase Phrase to signal to our bot for an action
     * @return
     */
    public String[] responses(ArrayList<String> keyPhrase) {
        return new String[]{keyPhrase.get(2)};
    }

    /**
     * Send results to string
     * @return human
     */
    @Override
    public String toString() {
        return "Human{" +
                "human=" + human +
                '}';
    }

}
