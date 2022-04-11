import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.*;

public class Submission extends User {

    protected String input, result;
    private double submissionTotalScore;
    private String popularSubmissionSubreddit = "";
    private int submissionSubredditCount, freeKarma = 0;
    private ArrayList commentSubreddits = new ArrayList();

    public Submission(String subreddit, String name, String id, String user, Boolean verified, Boolean has_verified_email, Boolean is_gold, Boolean is_mod, Boolean is_employee, int awardee_karma, int awarder_karma, int link_karma, int comment_karma, int total_karma, Date created, String comment, boolean upvote, boolean downvote, double submissionTotalScore, String popularSubmissionSubreddit, int submissionSubredditCount, int freeKarma, ArrayList commentSubreddits) {
        super(subreddit, name, id, user, verified, has_verified_email, is_gold, is_mod, is_employee, awardee_karma, awarder_karma, link_karma, comment_karma, total_karma, created, comment, upvote, downvote);
        this.submissionTotalScore = submissionTotalScore;
        this.popularSubmissionSubreddit = popularSubmissionSubreddit;
        this.submissionSubredditCount = submissionSubredditCount;
        this.freeKarma = freeKarma;
        this.commentSubreddits = commentSubreddits;
        this.input = input;
        this.result = result;
    }

    //getters

    public double getSubmissionTotalScore() {
        return submissionTotalScore;
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
        this.submissionTotalScore = submissionTotalScore;
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

    public String readInput(String user, String input) throws IOException, InterruptedException {
        if (!input.contains("/")) {
            user = input;
        }
        if (input.startsWith("t2_")) {
            //fullname
        }
        if (input.startsWith("u/") || input.contains("/u/") || input.contains("/user/")) {
            String[] e = input.split("u/");
            String[] f = e[1].split("/");
            user = f[0];
        }

        if (input.contains("/comments/") && !input.contains("/comment/")) {
            String[] c = input.split(".com/");
            String[] d = c[1].split("/");
            String endpoint;
            if (d.length <= 5)
                result = "submission";
            endpoint = "/r/" + d[1] + "/comments/" + d[3];
            JsonArray submissionInfo = useEndpointSubmission(endpoint);
            JsonObject array0 = submissionInfo.get(0).getAsJsonObject();
            JsonObject submissionData = (JsonObject) array0.getAsJsonObject().get("data");
            JsonArray submissionChildren = submissionData.getAsJsonArray("children");
            for (JsonElement items : submissionChildren) {
                JsonObject data3 = (JsonObject) items.getAsJsonObject().get("data");
                String author = String.valueOf(data3.getAsJsonObject().get("author"));
                user = author.replace("\"", "");
            }

            if (d.length >= 6) {
                result = "comment";
                endpoint = "/r/" + d[1] + "/api/info?id=t1_" + d[5];
                JsonObject info = useEndpoint(endpoint);
                JsonObject data = info.getAsJsonObject("data");
                JsonArray children = data.getAsJsonArray("children");
                for (JsonElement item : children) {
                    JsonObject data11 = (JsonObject) item.getAsJsonObject().get("data");
                    String author = String.valueOf(data11.getAsJsonObject().get("author"));
                    user = author.replace("\"", "");
                }
            }
        }

        if (input.contains("/comments/") && input.contains("/comment/")) {
            result = "comment";
            String[] a = input.split("/r/");
            String[] subreddit = a[1].split("/");
            String[] b = input.split("/comment/");
            String[] commentId = b[1].split("/");
            String endpoint = "/r/" + subreddit[0] + "/api/info?id=t1_" + commentId[0];
            JsonObject info = useEndpoint(endpoint);
            JsonObject data = info.getAsJsonObject("data");
            JsonArray children = data.getAsJsonArray("children");
            for (JsonElement item : children) {
                JsonObject data1 = (JsonObject) item.getAsJsonObject().get("data");
                String author = String.valueOf(data1.getAsJsonObject().get("author"));
                user = author.replace("\"", "");
            }
        }
        this.user = user;
        return result;
    }

    public void analyzeSubmission() {
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

    public static double findSimilarity(String x, String y) {
        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0)
            return (maxLength - StringUtils.getLevenshteinDistance(x, y)) / maxLength;
        return 0.0;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "submissionTotalScore=" + submissionTotalScore +
                ", popularSubmissionSubreddit='" + popularSubmissionSubreddit + '\'' +
                ", submissionSubredditCount=" + submissionSubredditCount +
                ", freeKarma=" + freeKarma +
                ", commentSubreddits=" + commentSubreddits +
                ", input='" + input + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public String getResponse(String[] keyPhrase) {
        return "Submission class - Insert response here.";
    }

}
