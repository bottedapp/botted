import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import javax.security.sasl.AuthenticationException;
import java.time.Instant;
import java.util.Base64;
import java.io.IOException;
import com.google.gson.*;

public class Reddit {

    private final String BASE_URL = "https://www.reddit.com";
    private final String OAUTH_URL = "https://oauth.reddit.com";
    private final String clientId = "GgPNctP2KQdth-iX6aMGUQ";
    private final String clientSecret = "6zov1gDWJ8Ij60yH3L7q6N_LnPUZHA";
    private final String userAgent = "botted 0.0.1";
    private final String token;
    private long expirationDate;

    public Reddit() throws IOException {
        // Get access token
        Connection conn = Jsoup.connect(BASE_URL + "/api/v1/access_token").ignoreContentType(true).ignoreHttpErrors(true).method(Method.POST).userAgent(userAgent);
        conn.data("grant_type", "client_credentials");

        // Generate the Authorization header
        String combination = clientId + ":" + clientSecret;
        combination = Base64.getEncoder().encodeToString(combination.getBytes());
        conn.header("Authorization", "Basic " + combination);

        // Open the connection and get response from server
        Response res = conn.execute();
        JsonObject object = JsonParser.parseString(res.body()).getAsJsonObject();

        // Set access token and expiration time
        this.token = object.get("access_token").getAsString();
        this.expirationDate = object.get("expires_in").getAsInt() + Instant.now().getEpochSecond();
    }

    /**
     * @param endpointPath call to API method ex: "/user/username/about"
     * @return json (Json Object)
     */
    public JsonObject useEndpoint(String endpointPath) throws IOException, InterruptedException {
        ensureConnection();
        Connection connection = Jsoup.connect(OAUTH_URL + endpointPath);
        connection.header("Authorization", "bearer " + token).ignoreContentType(true).userAgent(userAgent);
        return JsonParser.parseString(connection.execute().body()).getAsJsonObject();
    }

    /**
     * Ensure the connection is Authenticated
     * @throws IOException
     * @throws InterruptedException
     * @throws AuthenticationException
     */
    public void ensureConnection() throws IOException, InterruptedException, AuthenticationException {
        // There is no token
        if (token == null) {
            new Reddit();
        }
        // The token is expired
        if (Instant.now().getEpochSecond() > expirationDate) {
            new Reddit();
        }
    }
}