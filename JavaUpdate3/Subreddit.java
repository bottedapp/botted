import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

public class Subreddit extends RedditComponent {

    /**
     * Protected and private variables
     */
    protected String input, result;
    private double subredditTotalScore;
    private String popularSubmissionSubreddit = "";
    private int submissionSubredditCount, freeKarma = 0;
    private ArrayList commentSubreddits = new ArrayList();

    /**
     * Constructor with parameters
     * @param user The username
     * @throws IOException
     * @throws InterruptedException
     */
    public Subreddit(String user) throws IOException, InterruptedException {
        super(user);
        analyzeSubmission();
    }

    /**
     * Constructor
     * @param subreddit The specific subreddit
     * @param subredditTotalScore Total karma gained from particular subreddit
     * @param popularSubmissionSubreddit Most popular submission in particular subreddit
     * @param submissionSubredditCount How many submissions in particular subreddit
     * @param freeKarma Subreddit called FreeKarma4U
     * @param commentSubreddits Subreddits commented in
     * @throws IOException
     * @throws InterruptedException
     */
    public Subreddit(String subreddit, double subredditTotalScore, String popularSubmissionSubreddit, int submissionSubredditCount, int freeKarma, ArrayList commentSubreddits) throws IOException, InterruptedException {
        super(subreddit);
        this.subredditTotalScore = subredditTotalScore;
        this.popularSubmissionSubreddit = popularSubmissionSubreddit;
        this.submissionSubredditCount = submissionSubredditCount;
        this.freeKarma = freeKarma;
        this.commentSubreddits = commentSubreddits;
        this.input = input;
        this.result = result;
    }

    //getters

    public double getSubredditTotalScore() {
        return subredditTotalScore;
    }

    public String getPopularSubmissionSubreddit() {
        return popularSubmissionSubreddit;
    }

    public int getSubmissionSubredditCount() {
        return submissionSubredditCount;
    }

    public int getFreeKarma() {
        return freeKarma;
    }

    public ArrayList getCommentSubreddits() {
        return commentSubreddits;
    }

    public String getInput() {
        return input;
    }

    public String getResult() {
        return result;
    }

    //setters

    public void setSubmissionTotalScore(double submissionTotalScore) {
        this.subredditTotalScore = subredditTotalScore;
    }

    public void setPopularSubmissionSubreddit(String popularSubmissionSubreddit) {
        this.popularSubmissionSubreddit = popularSubmissionSubreddit;
    }

    public void setSubmissionSubredditCount(int submissionSubredditCount) {
        this.submissionSubredditCount = submissionSubredditCount;
    }

    public void setFreeKarma(int freeKarma) {
        this.freeKarma = freeKarma;
    }

    public void setCommentSubreddits(ArrayList commentSubreddits) {
        this.commentSubreddits = commentSubreddits;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Finds the most active subreddit a user posts in
     * Checks if posted in r/FreeKarma4U
     * Calculates similarities between submissions
     * @throws IOException
     * @throws InterruptedException
     */
    public void analyzeSubmission() throws IOException, InterruptedException {
        JsonObject submitted = useEndpoint("/user/" + user + "/submitted");
        JsonObject data = (JsonObject) submitted.get("data");
        JsonArray children = (JsonArray) data.get("children");

        Map<String, String> submissionMap = new LinkedHashMap<>();
        for (JsonElement item : children) {
            JsonObject dat = (JsonObject) item.getAsJsonObject().get("data");
            String id = String.valueOf(dat.getAsJsonObject().get("id"));
            String body = String.valueOf(dat.getAsJsonObject().get("selftext"));
            submissionMap.put(id, body);
        }

        if (submissionMap.size() <= 1) {
            //do nothing
        } else {
            for (JsonElement item : children) {
                JsonObject dat = (JsonObject) item.getAsJsonObject().get("data");
                String subreddit = String.valueOf(dat.getAsJsonObject().get("subreddit_name_prefixed"));
                commentSubreddits.add(subreddit);
            }
            for (Object a : commentSubreddits) {
                if ( a.toString().contains("r/FreeKarma4U"))
                    freeKarma = Collections.frequency(commentSubreddits, a);
                if (Collections.frequency(commentSubreddits, a) > submissionSubredditCount) {
                    submissionSubredditCount = Collections.frequency(commentSubreddits, a);
                    popularSubmissionSubreddit = (String) a;
                }
            }
        }

        double postScore = 0;
        int postScoreCount = 0;
        for (Map.Entry<String, String> posts : submissionMap.entrySet()) {
            for (Map.Entry<String, String> post : submissionMap.entrySet()) {
                if (Objects.equals(post.getKey(), posts.getKey())) {
                    //do nothing
                } else {
                    postScore += (findSimilarity(post.getValue(), posts.getValue()));
                    postScoreCount++;
                }
            }
        }
        subredditTotalScore = postScore / postScoreCount;
    }

    /**
     * Send results to string
     * @return submissionTotalScore, popularSubmissionSubreddit, submissionSubredditCount, freeKarma, commentSubreddits, input, and result
     */
    @Override
    public String toString() {
        return "Submission{" +
                "submissionTotalScore=" + subredditTotalScore +
                ", popularSubmissionSubreddit='" + popularSubmissionSubreddit + '\'' +
                ", submissionSubredditCount=" + submissionSubredditCount +
                ", freeKarma=" + freeKarma +
                ", commentSubreddits=" + commentSubreddits +
                ", input='" + input + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

}
