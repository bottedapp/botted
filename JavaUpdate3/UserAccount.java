import com.google.gson.JsonObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserAccount extends RedditComponent {

    /**
     * Protected and private variables
     */
    protected String name, id, user;
    protected Boolean verified, has_verified_email, is_gold, is_mod, is_employee;
    protected int awardee_karma, awarder_karma, link_karma, comment_karma, total_karma;
    protected Date created;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
    private String comment;
    private boolean upvote;
    private boolean downvote;

    /**
     * Default constructor
     * @throws IOException
     * @throws InterruptedException
     */
    public UserAccount() throws IOException, InterruptedException {
        super();
        this.user = "spez";
    }

    /**
     * Constructor with parameters
     * @param user The username
     * @throws IOException
     * @throws InterruptedException
     */
    public UserAccount(String user) throws IOException, InterruptedException {
        this.user = user;
        value(user);
    }

    /**
     * Constructor for human user
     * @param subreddit The specific subreddit
     * @param comment A comment on a post
     * @param upvote An upvote on a post or comment
     * @param downvote A downvote on a post or comment
     * @throws IOException
     * @throws InterruptedException
     */
    public UserAccount(String subreddit, String comment, boolean upvote, boolean downvote) throws IOException, InterruptedException { // human
        super();
        this.subreddit = subreddit;
        this.comment = comment;
        this.upvote = upvote;
        this.downvote = downvote;
    }

    /**
     * Constructor
     * @param subreddit The specific subreddit
     * @param name The name associated with the account
     * @param id The account ID
     * @param user The username
     * @param verified If account is verified
     * @param has_verified_email If E-mail has been verified on account
     * @param is_gold If account has gold status
     * @param is_mod Is a moderator
     * @param is_employee Is an employee
     * @param awardee_karma Karma gained from awardee
     * @param awarder_karma Karma gained from awarder
     * @param link_karma Karma gained from link
     * @param comment_karma Karma gained from comment
     * @param total_karma Total karma on account
     * @param created When the account was created
     * @param comment A comment on a post
     * @param upvote An upvote on a post or comment
     * @param downvote A downvote on a post or comment
     * @throws IOException
     * @throws InterruptedException
     */
    public UserAccount(String subreddit, String name, String id, String user, Boolean verified, Boolean has_verified_email, Boolean is_gold, Boolean is_mod, Boolean is_employee, int awardee_karma, int awarder_karma, int link_karma, int comment_karma, int total_karma, Date created, String comment, boolean upvote, boolean downvote) throws IOException, InterruptedException {
        super(subreddit);
        this.name = name;
        this.id = id;
        this.user = user;
        this.verified = verified;
        this.has_verified_email = has_verified_email;
        this.is_gold = is_gold;
        this.is_mod = is_mod;
        this.is_employee = is_employee;
        this.awardee_karma = awardee_karma;
        this.awarder_karma = awarder_karma;
        this.link_karma = link_karma;
        this.comment_karma = comment_karma;
        this.total_karma = total_karma;
        this.created = created;
        this.comment = comment;
        this.upvote = upvote;
        this.downvote = downvote;
    }

    //getters

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public Boolean getVerified() {
        return verified;
    }

    public Boolean getHas_verified_email() {
        return has_verified_email;
    }

    public Boolean getIs_gold() {
        return is_gold;
    }

    public Boolean getIs_mod() {
        return is_mod;
    }

    public Boolean getIs_employee() {
        return is_employee;
    }

    public int getAwardee_karma() {
        return awardee_karma;
    }

    public int getAwarder_karma() {
        return awarder_karma;
    }

    public int getLink_karma() {
        return link_karma;
    }

    public int getComment_karma() {
        return comment_karma;
    }

    public int getTotal_karma() {
        return total_karma;
    }

    public Date getCreated() {
        return created;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public String getComment() {
        return comment;
    }

    public boolean isUpvote() {
        return upvote;
    }

    public boolean isDownvote() {
        return downvote;
    }

    //setters

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setHas_verified_email(Boolean has_verified_email) {
        this.has_verified_email = has_verified_email;
    }

    public void setIs_gold(Boolean is_gold) {
        this.is_gold = is_gold;
    }

    public void setIs_mod(Boolean is_mod) {
        this.is_mod = is_mod;
    }

    public void setIs_employee(Boolean is_employee) {
        this.is_employee = is_employee;
    }

    public void setAwardee_karma(int awardee_karma) {
        this.awardee_karma = awardee_karma;
    }

    public void setAwarder_karma(int awarder_karma) {
        this.awarder_karma = awarder_karma;
    }

    public void setLink_karma(int link_karma) {
        this.link_karma = link_karma;
    }

    public void setComment_karma(int comment_karma) {
        this.comment_karma = comment_karma;
    }

    public void setTotal_karma(int total_karma) {
        this.total_karma = total_karma;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public void setDownvote(boolean downvote) {
        this.downvote = downvote;
    }

    /**
     * Collects information about user and user account
     * @param user Username
     * @throws IOException
     * @throws InterruptedException
     */
    public void value(String user) throws IOException, InterruptedException {
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

    /**
     * Send results to string
     * @return name, id, user, verified, has_verified_email, is_gold, is_mod, is_employee, awardee_karma, awarder_karma, link_karma, created, sdf, comment, upvote, and downvote
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", verified=" + verified +
                ", has_verified_email=" + has_verified_email +
                ", is_gold=" + is_gold +
                ", is_mod=" + is_mod +
                ", is_employee=" + is_employee +
                ", awardee_karma=" + awardee_karma +
                ", awarder_karma=" + awarder_karma +
                ", link_karma=" + link_karma +
                ", comment_karma=" + comment_karma +
                ", total_karma=" + total_karma +
                ", created=" + created +
                ", sdf=" + sdf +
                ", comment='" + comment + '\'' +
                ", upvote=" + upvote +
                ", downvote=" + downvote +
                '}';
    }
}
