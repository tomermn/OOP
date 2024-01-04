import java.util.Scanner;

public class Chat {

    static final String FIRST_STATEMENT = "Hello Java";
    static final String BOT_1_NAME = "Roni";
    static final String BOT_2_NAME = "Amit";


    public static void main(String[] args) {
        ChatterBot[] chatterBots = new ChatterBot[2];
        String placeHolderForIllegal = ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST;
        String[] bot1IllegalReplies = {"what", "say I should say", "what the hell is " + placeHolderForIllegal + "?!",
        placeHolderForIllegal + "? " + placeHolderForIllegal + "?! " + placeHolderForIllegal + "???!"};
        String[] bot2IllegalReplies = {"whaaat", "say say", placeHolderForIllegal + " is funny"};
        String placeHolderForLegal = ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE;
        String[] bot1LegalReplies = {"You want me to say " + placeHolderForLegal + ", do you? alright: " + placeHolderForLegal};
        String[] bot2LegalReplies = {"say " + placeHolderForLegal + "? okay: " + placeHolderForLegal};
        chatterBots[0] = new ChatterBot(BOT_1_NAME, bot1IllegalReplies, bot1LegalReplies);
        chatterBots[1] = new ChatterBot(BOT_2_NAME, bot2IllegalReplies, bot2LegalReplies);

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
