import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class BottedRequest {

    private final String BASE_URL = "https://www.reddit.com";
    private final String OAUTH_URL = "https://oauth.reddit.com";
    private final String clientId = "_JW4OUQt7_krXZd420ycuw";
    private final String clientSecret = "BJFz2IB-EMvu_ye3EZ66oOcoDzWgwg";
    private final String userAgent = "botted 0.0.1";
    private final String username = "bottedapp";
    private final String password = "mc3.edu!";
    private String token;
    private long expirationDate;
    protected String subreddit;

    public static void main(String[] args) throws IOException, InterruptedException {
        BottedRequest r = new BottedRequest();
        r.userConnect();
        r.analyze();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    r.replyComment();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(task, new Date(), 5 * 60 * 1000);
        r.commentConnect();
    }

    //getters

    public String getToken() {
        return token;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public String getSubreddit() {
        return subreddit;
    }

    //setters

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    /**
     *
     * @throws IOException
     */
    public void userConnect() throws IOException {
        Connection conn = Jsoup.connect(BASE_URL + "/api/v1/access_token").ignoreContentType(true).ignoreHttpErrors(true).method(Connection.Method.POST).userAgent(userAgent);
        conn.data("grant_type", "password");
        conn.data("username", username).data("password", password);
        String combination = clientId + ":" + clientSecret;
        combination = Base64.getEncoder().encodeToString(combination.getBytes());
        conn.header("Authorization", "Basic " + combination);

        Connection.Response res = conn.execute();
        JsonObject object = JsonParser.parseString(res.body()).getAsJsonObject();
        this.token = object.get("access_token").getAsString();
        this.expirationDate = object.get("expires_in").getAsInt() + Instant.now().getEpochSecond();
        System.out.println(res.toString());
    }

    /**
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void analyze() throws IOException, InterruptedException {
        JsonObject posts = (JsonObject) useEndpoint("/r/all/search?q=bottedapp");
        JsonObject data = posts.getAsJsonObject("data");
        JsonArray children = (JsonArray) data.get("children");

        Map<String, String> summonMap = new LinkedHashMap<>();
        for (JsonElement item : children) {
            JsonObject dat = (JsonObject) item.getAsJsonObject().get("data");
            String author = String.valueOf(dat.getAsJsonObject().get("author"));
            String id = String.valueOf(dat.getAsJsonObject().get("id"));
            String selftext = String.valueOf(dat.getAsJsonObject().get("selftext"));
            summonMap.put(id, selftext);
            System.out.println(author + " " + id + " " + selftext);
        }
    }

    /**
     *
     * @param endpointPath
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public JsonElement useEndpoint(String endpointPath) throws IOException, InterruptedException {
        Connection connection = Jsoup.connect(OAUTH_URL + endpointPath);
        connection.header("Authorization", "bearer " + token).ignoreContentType(true).userAgent(userAgent);
        return JsonParser.parseString(connection.execute().body()).getAsJsonObject();
    }

    public void replyComment() throws IOException, InterruptedException {

        List<String> comments = new ArrayList<>();
        comments.add(userComments(String.valueOf(comments)));

        for (String comment: comments)  {
            if (userComments(String.valueOf(comment)).contains("u/botted"))  {
                driver run = new driver();
                run.main(null);
                HumanAccount human = new HumanAccount();
                BotAccount bot = new BotAccount();
                    if (human.isHuman() == true) {
                        BottedRequest.reply(HumanAccount.responses());
                    }
                    else if (bot.isBot() == true) {
                        BottedRequest.reply(BotAccount.responses());
                    }
                    else {
                        BottedRequest.reply(UserAccount.responses());
                    }
            }
            else {
                //do nothing
            }
        }
    }

    public String userComments(String comments) throws IOException {
        boolean error = false;
        String read = null;
        URL getReqURL = new URL(BASE_URL + "/r/" + subreddit + "/comments/");
        HttpURLConnection connection = (HttpURLConnection) getReqURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty(username, password);
        int response = connection.getResponseCode();
        if (response == HttpURLConnection.HTTP_OK) {
            InputStreamReader inputObject = new InputStreamReader(connection.getInputStream());
            BufferedReader buff = new BufferedReader(inputObject);
            StringBuffer commentLog = new StringBuffer();
            while ((read = buff.readLine()) != null) {
                commentLog.append(read);
                comments = String.valueOf(commentLog);
                return comments;
            }
            buff.close();
            connection.disconnect();
        }
        else {
            error = true;
        }
        return comments;
    }

    public static void reply(Object responses) {
        //send reply to reddit backend
    }

    public void commentConnect() throws IOException {
        Connection connect = Jsoup.connect(OAUTH_URL + "/api/comment").ignoreContentType(true).ignoreHttpErrors(true).postDataCharset("UTF-8")
                .data("api_type", "json")
                .data("text", "test")
                .data("thing_id", "t1_i5kycps");
        connect.header("Authorization", "bearer " + token).userAgent(userAgent).post();
    }
    
    @Override
    public String toString() {
        return "BottedRequest{" +
                "BASE_URL='" + BASE_URL + '\'' +
                ", OAUTH_URL='" + OAUTH_URL + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", expirationDate=" + expirationDate +
                ", subreddit='" + subreddit + '\'' +
                '}';
    }

}
