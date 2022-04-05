import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class Reddit {

    protected String subreddit;
    protected String[] keyPhrase = new String[] {"keyPhrase1", "keyPhrase2", "keyPhrase3"};

    public void connect() throws Exception {
        URL redditServer = new URL("https://insertURLhere");
        URLConnection connection1 = redditServer.openConnection();
    }

    public Reddit(String subreddit, String[] keyPhrase) {
        this.subreddit = subreddit;
        this.keyPhrase = keyPhrase;
    }

    //getters

    public String getSubreddit() {
        return subreddit;
    }

    public String[] getKeyPhrase() {
        return keyPhrase;
    }

    //setters

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public void setKeyPhrase(String[] keyPhrase) {
        this.keyPhrase = keyPhrase;
    }

    @Override
    public String toString() {
        return "Reddit{" +
                "subreddit='" + subreddit + '\'' +
                ", keyPhrase=" + Arrays.toString(keyPhrase) +
                '}';
    }

}