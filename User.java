public class User extends Reddit {

    private String comment;
    private boolean upvote;
    private boolean downvote;

    public User(String subreddit, String[] keyPhrase, String comment, boolean upvote, boolean downvote) {
        super(subreddit, keyPhrase);
        this.comment = comment;
        this.upvote = upvote;
        this.downvote = downvote;
    }

    //getters

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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public void setDownvote(boolean downvote) {
        this.downvote = downvote;
    }

    @Override
    public String toString() {
        return "User{" +
                "comment='" + comment + '\'' +
                ", upvote=" + upvote +
                ", downvote=" + downvote +
                '}';
    }

}
