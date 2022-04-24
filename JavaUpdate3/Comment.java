import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.*;

public class Comment extends RedditComponent {

    /**
     * Private variables
     */
    private double commentTotalScore;
    private String popularCommentSubreddit = "";
    private int commentSubredditCount = 0;
    private ArrayList commentSubreddits = new ArrayList<>();

    /**
     * Default constructor
     * @throws IOException
     * @throws InterruptedException
     */
    public Comment() throws IOException, InterruptedException {
        super();
    }

    /**
     * Constructor with parameters
     * @param user The username
     * @throws IOException
     * @throws InterruptedException
     */
    public Comment(String user) throws IOException, InterruptedException {
        super(user);
        analyzeComment();
    }

    /**
     * Constructor
     * @param subreddit The specific subreddit
     * @param commentTotalScore Total karma from comments
     * @param popularCommentSubreddit Most popular comment in subreddit
     * @param commentSubredditCount How many comments in particular subreddit
     * @param commentSubreddits Subreddits commented in
     * @throws IOException
     * @throws InterruptedException
     */
    public Comment(String subreddit, double commentTotalScore, String popularCommentSubreddit, int commentSubredditCount, ArrayList commentSubreddits) throws IOException, InterruptedException {
        super(subreddit);
        this.commentTotalScore = commentTotalScore;
        this.popularCommentSubreddit = popularCommentSubreddit;
        this.commentSubredditCount = commentSubredditCount;
        this.commentSubreddits = commentSubreddits;
    }

    //getters

    public double getCommentTotalScore() {
        return commentTotalScore;
    }

    public String getPopularCommentSubreddit() {
        return popularCommentSubreddit;
    }

    public int getCommentSubredditCount() {
        return commentSubredditCount;
    }

    public ArrayList getCommentSubreddits() {
        return commentSubreddits;
    }

    //setters

    public void setCommentTotalScore(double commentTotalScore) {
        this.commentTotalScore = commentTotalScore;
    }

    public void setPopularCommentSubreddit(String popularCommentSubreddit) {
        this.popularCommentSubreddit = popularCommentSubreddit;
    }

    public void setCommentSubredditCount(int commentSubredditCount) {
        this.commentSubredditCount = commentSubredditCount;
    }

    public void setCommentSubreddits(ArrayList commentSubreddits) {
        this.commentSubreddits = commentSubreddits;
    }

    /**
     * Finds most active subreddit user comments in
     * Calculates similarities between comments
     * @throws IOException
     * @throws InterruptedException
     */
    public void analyzeComment() throws IOException, InterruptedException {
        JsonObject comments = useEndpoint("/user/" + user + "/comments");
        JsonObject data = comments.getAsJsonObject("data");
        JsonArray children = data.getAsJsonArray("children");

        Map<String, String> commentMap = new LinkedHashMap<>();
        for (JsonElement item : children) {
            JsonObject dat = (JsonObject) item.getAsJsonObject().get("data");
            String id = String.valueOf(dat.getAsJsonObject().get("id"));
            String body = String.valueOf(dat.getAsJsonObject().get("body"));
            commentMap.put(id, body);
        }

        if (commentMap.size() <= 1) {
            //do nothing
        } else {
            for (JsonElement item : children) {
                JsonObject dat = (JsonObject) item.getAsJsonObject().get("data");
                String subreddit = String.valueOf(dat.getAsJsonObject().get("subreddit_name_prefixed"));
                commentSubreddits.add(subreddit);
            }

            for (Object commentSubreddit : commentSubreddits) {
                if (Collections.frequency(commentSubreddits, commentSubreddit) > commentSubredditCount) {
                    commentSubredditCount = Collections.frequency(commentSubreddits, commentSubreddit);
                    popularCommentSubreddit = (String) commentSubreddit;
                }
            }
        }

        if (commentMap.size() <= 1) {
            //do nothing
        } else {
            double commentScore = 0;
            int commentScoreCount = 0;
            for (Map.Entry<String, String> comm : commentMap.entrySet()) {
                for (Map.Entry<String, String> comment : commentMap.entrySet()) {
                    if (Objects.equals(comment.getKey(), comm.getKey())) {
                        // do nothing
                    } else {
                        commentScore += (findSimilarity(comment.getValue(), comm.getValue()));
                        commentScoreCount++;
                    }
                }
            }
            commentTotalScore = commentScore / commentScoreCount;
        }
    }

    /**
     * Send results to string
     * @return commentTotalScore, popularCommentSubreddit, commentSubredditCount, and commentSubreddits
     */
    @Override
    public String toString() {
        return "Comment{" +
                "commentTotalScore=" + commentTotalScore +
                ", popularCommentSubreddit='" + popularCommentSubreddit + '\'' +
                ", commentSubredditCount=" + commentSubredditCount +
                ", commentSubreddits=" + commentSubreddits +
                '}';
    }
}