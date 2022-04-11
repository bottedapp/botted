import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class driver {

    protected ArrayList<String> keyPhrase;

    public static void main(String[] args) {

        ArrayList keyPhrase = new ArrayList();
        keyPhrase.add("This is a good bot!");
        keyPhrase.add("This is a bad bot!");
        keyPhrase.add("This is a human!");
        keyPhrase.add("Undetermined.");

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter username/post/comment: ");
        String in = scan.nextLine();
        Reddit reddit = new Reddit(in);
        Reddit user = new User();
        Reddit human = new Human();
        Reddit submissions = new Submission();

        System.out.println(user);
        System.out.println(submissions);
    }

}
