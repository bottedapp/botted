import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.*;

public class Comments extends User {

    private double commentTotalScore;
    private String popularCommentSubreddit = "";
    private int commentSubredditCount = 0;
    private ArrayList commentSubreddits = new ArrayList<>();

    public Comments(String user) throws IOException, InterruptedException {
        super(user);
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
        //find most active subreddit user comments in
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
        //calculate similarities between comments
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

    @Override
    public String toString() {
        return "\nComments\nmost active subreddit for comments: " + popularCommentSubreddit + " " + commentSubredditCount + " / " + commentSubreddits.size() +
                "\ncomment score: " + commentTotalScore;
    }
}
