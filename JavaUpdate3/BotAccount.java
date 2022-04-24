import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class BotAccount extends UserAccount {

    /**
     * Private variables
     */
    private boolean bot;
    private boolean goodBot;
    private boolean badBot;

    /**
     * Default constructor
     * @throws IOException
     * @throws InterruptedException
     */
    public BotAccount() throws IOException, InterruptedException {
        super();
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
     * @param bot Whether this is a bot
     * @param goodBot Whether the bot is good
     * @param badBot Whether the bot is bad
     * @throws IOException
     * @throws InterruptedException
     */
    public BotAccount(String subreddit, String name, String id, String user, Boolean verified, Boolean has_verified_email, Boolean is_gold, Boolean is_mod, Boolean is_employee, int awardee_karma, int awarder_karma, int link_karma, int comment_karma, int total_karma, Date created, String comment, boolean upvote, boolean downvote, boolean bot, boolean goodBot, boolean badBot) throws IOException, InterruptedException {
        super(subreddit, name, id, user, verified, has_verified_email, is_gold, is_mod, is_employee, awardee_karma, awarder_karma, link_karma, comment_karma, total_karma, created, comment, upvote, downvote);
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

    /**
     * Send results to string
     * @return bot, goodBot, and badBot
     */
    @Override
    public String toString() {
        return "Bot{" +
                "bot=" + bot +
                ", goodBot=" + goodBot +
                ", badBot=" + badBot +
                '}';
    }

    /**
     * Return correct response according to results
     * @param goodBot Whether the bot is good
     * @param badBot Whether the bot is bad
     * @param keyPhrase Phrase to signal to our bot for an action
     * @return
     */
    public String[] responses(boolean goodBot, boolean badBot, ArrayList<String> keyPhrase) {
        if (goodBot = true) {
            return new String[]{keyPhrase.get(0)};
        }
        else if (badBot = true) {
            return new String[]{keyPhrase.get(1)};
        }
        else {
            return new String[]{keyPhrase.get(3)};
        }
    }

}
