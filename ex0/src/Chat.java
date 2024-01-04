import java.util.Scanner;

public class Chat {

    static final String FIRST_STATEMENT = "Hello Java";


    public static void main(String[] args) {
        ChatterBot[] chatterBots = new ChatterBot[2];
        String[] bot1IllegalReplies = {"what", "say I should say"};
        String[] bot2IllegalReplies = {"whaaat", "say say"};
        String placeHolder = ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE;
        String[] bot1LegalReplies = {"You want me to say " + placeHolder + ", do you? alright: " + placeHolder};
        String[] bot2LegalReplies = {"say " + placeHolder + "? okay: " + placeHolder};
        chatterBots[0] = new ChatterBot("Roni", bot1IllegalReplies, bot1LegalReplies);
        chatterBots[1] = new ChatterBot("Amit", bot2IllegalReplies, bot2LegalReplies);

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
