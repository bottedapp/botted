import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class driver {

    /**
     * Protected variable
     */
    protected ArrayList<String> keyPhrase;

    public static void main(String[] args) throws IOException, InterruptedException {
        //Array list of responses
        ArrayList keyPhrase = new ArrayList();
        keyPhrase.add("This is a good bot!");
        keyPhrase.add("This is a bad bot!");
        keyPhrase.add("This is a human!");
        keyPhrase.add("Undetermined.");

        //Creating class objects to run program
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter username/post/comment: ");
        String in = scan.nextLine();
        RedditComponent reddit = new RedditComponent();
        String redditor = reddit.readInput(in);
        RedditComponent user = new UserAccount(redditor);
        RedditComponent comments = new Comment(redditor);
        RedditComponent subreddits = new Subreddit(redditor);
        RedditComponent bot = new BotAccount();
        RedditComponent human = new HumanAccount();

        //Print results
        System.out.println(user);
        System.out.println(comments);
        System.out.println(subreddits);
    }

}
