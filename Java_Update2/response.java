import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class response {

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
        response r = new response();
        r.userConnect();
        r.analyze();

    }
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
    public JsonElement useEndpoint(String endpointPath) throws IOException, InterruptedException {
        Connection connection = Jsoup.connect(OAUTH_URL + endpointPath);
        connection.header("Authorization", "bearer " + token).ignoreContentType(true).userAgent(userAgent);
        return JsonParser.parseString(connection.execute().body()).getAsJsonObject();
    }
}
