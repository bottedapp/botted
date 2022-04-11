import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;

public class Input extends Reddit{

    String result = "";
    protected String user;

    public Input() throws IOException{
        super();
    }
    public Input(String input) throws IOException, InterruptedException {
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
    }
    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user;
    }
}
