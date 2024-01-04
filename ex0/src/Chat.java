import java.util.Scanner;

public class Chat {

    static final String FIRST_STATEMENT = "Hello Java";


    public static void main(String[] args) {
        ChatterBot[] chatterBots = new ChatterBot[2];
        String[] bot1IllegalReplies = {"what", "say I should say"};
        String[] bot2IllegalReplies = {"whaaat", "say say"};
        chatterBots[0] = new ChatterBot("Roni", bot1IllegalReplies);
        chatterBots[1] = new ChatterBot("Amit", bot2IllegalReplies);

        String statement = FIRST_STATEMENT;
        Scanner scanner = new Scanner(System.in);

        while (true){
            for (ChatterBot bot : chatterBots){
                statement = bot.replyTo(statement);
                System.out.println(bot.getName() + ": " + statement);
                scanner.nextLine();

            }
        }

    }
}
