import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.*;

public class Submissions extends User {

    private double submissionTotalScore;
    private String popularSubmissionSubreddit = "";
    private int submissionSubredditCount, freeKarma = 0;
    private ArrayList commentSubreddits = new ArrayList();

    public Submissions(String user) throws IOException, InterruptedException {
        super(user);
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
        //find most active subreddit user posts in / check if user posts in r/FreeKarma4U
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
        //calculate similarities between submissions
        double postScore = 0;
        int postScoreCount = 0;
        for (Map.Entry<String, String> posts : submissionMap.entrySet()) {
            for (Map.Entry<String, String> post : submissionMap.entrySet()) {
                if (Objects.equals(post.getKey(), posts.getKey())) {
                    // do nothing
                } else {
                    postScore += (findSimilarity(post.getValue(), posts.getValue()));
                    postScoreCount++;
                }
            }
        }
        submissionTotalScore = postScore / postScoreCount;
    }

    @Override
    public String toString() {
            return "\nSubmissions\nmost active subreddit for submissions: " + popularSubmissionSubreddit + " " + submissionSubredditCount + " / " + commentSubreddits.size() +
                    "\nposts in r/FreeKarma4U: " + freeKarma +
                    "\nsubmission score: " + submissionTotalScore;
    }
}
