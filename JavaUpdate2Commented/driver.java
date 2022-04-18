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

        //Creating class objects to unr program
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter username/post/comment: ");
        String in = scan.nextLine();
        Reddit reddit = new Reddit();
        String redditor = reddit.readInput(in);
        Reddit user = new User(redditor);
        Reddit comments = new Comment(redditor);
        Reddit submissions = new Submission(redditor);
        Reddit bot = new Bot();
        Reddit human = new Human();

        //Print results
        System.out.println(user);
        System.out.println(comments);
        System.out.println(submissions);
    }

}
