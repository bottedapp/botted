public class Bot extends User {

    private boolean bot;
    private boolean goodBot;
    private boolean badBot;

    public Bot(String subreddit, String[] keyPhrase, String comment, boolean upvote, boolean downvote, boolean bot, boolean goodBot, boolean badBot) {
        super(subreddit, keyPhrase, comment, upvote, downvote);
        this.bot = bot;
        this.goodBot = goodBot;
        this.badBot = badBot;
    }

    //getters

    public boolean isBot() {
        return bot;
    }

    public boolean isGoodBot() {
        return goodBot;
    }

    public boolean isBadBot() {
        return badBot;
    }

    //setters

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public void setGoodBot(boolean goodBot) {
        this.goodBot = goodBot;
    }

    public void setBadBot(boolean badBot) {
        this.badBot = badBot;
    }

    @Override
    public String toString() {
        return "Bot{" +
                "bot=" + bot +
                ", goodBot=" + goodBot +
                ", badBot=" + badBot +
                '}';
    }

    public String getResponse(boolean goodBot, boolean badBot) {

        String result;

        if (goodBot = true) {
            result = "Good bot";
        }
        else if (badBot = true) {
            result = "Bad bot";
        }
        else {
            result = "Undetermined";
        }

        return result;
    }
}
