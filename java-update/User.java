import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User extends Input {
    protected String name, id;
    protected Boolean verified, has_verified_email, is_gold, is_mod, is_employee;
    protected int awardee_karma, awarder_karma, link_karma, comment_karma, total_karma;
    protected Date created;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");

    public User(String user) throws IOException, InterruptedException {
        JsonObject about = useEndpoint("/user/" + user +"/about");
        JsonObject data = (JsonObject) about.get("data");
        //User Info
        name = String.valueOf(data.get("name"));
        id = String.valueOf(data.get("id"));
        long utc = Long.parseLong(String.valueOf(data.get("created_utc").getAsInt()));
        created = new Date(utc * 1000);
        verified = Boolean.valueOf(String.valueOf(data.get("verified")));
        has_verified_email = Boolean.valueOf(String.valueOf(data.get("has_verified_email")));
        is_gold = Boolean.valueOf(String.valueOf(data.get("is_gold")));
        is_mod = Boolean.valueOf(String.valueOf(data.get("is_mod")));
        is_employee = Boolean.valueOf(String.valueOf(data.get("is_employee")));
        //User Karma
        awardee_karma = Integer.parseInt(String.valueOf(data.get("awardee_karma")));
        awarder_karma = Integer.parseInt(String.valueOf(data.get("awarder_karma")));
        link_karma = Integer.parseInt(String.valueOf(data.get("link_karma")));
        comment_karma = Integer.parseInt(String.valueOf(data.get("comment_karma")));
        total_karma = Integer.parseInt(String.valueOf(data.get("total_karma")));
    }

    public static double findSimilarity(String x, String y) {
        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0)
            return (maxLength - StringUtils.getLevenshteinDistance(x, y)) / maxLength;
        return 0.0;
    }
    @Override
    public String getUser() {
        return super.user;
    }

    @Override
    public String toString() {
        return "User\nname: " + name + ", id: " + id + ", created: " + sdf.format(created) + ", verified: " + verified + ", verified email: " + has_verified_email + ", premium: " + is_gold + ", mod: " + is_mod + ", employee: " + is_employee +
        "\nkarma: " + "awardee: " + awardee_karma + ", awarder: " + awarder_karma + ", link: " + link_karma + ", comment: " + comment_karma + ", total: " + total_karma;
    }
}
