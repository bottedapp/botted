import java.io.IOException;

public class Botted {
    public static void main(String[] args) throws IOException, InterruptedException {
        Reddit reddit = new Reddit();
        Reddit user = new User();
        Reddit comments = new Comments();
        Reddit submissions = new Submissions();

        System.out.println(user);
        System.out.println(comments);
        System.out.println(submissions);
    }
}
