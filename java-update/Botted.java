import java.io.IOException;
import java.util.Scanner;

public class Botted {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter username/post/comment: ");
        String in = scan.nextLine();
        Reddit reddit = new Reddit();
        Reddit input = new Input(in);
        Reddit user = new User(input.toString());
        Reddit comments = new Comments(input.toString());
        Reddit submissions = new Submissions(input.toString());

        System.out.println(user);
        System.out.println(comments);
        System.out.println(submissions);
    }
}
