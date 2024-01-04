import java.util.Scanner;

/**
 * The Chat class represent a simple conversational program between two ChatterBot instances.
 * It initializes bots, provides initial statements, and facilitates an ongoing conversation loop.
 */
public class Chat {

    static final String FIRST_STATEMENT = "Hello Java";
    static final String BOT_1_NAME = "Roni";
    static final String BOT_2_NAME = "Amit";


    public static void main(String[] args) {

        // Illegals patterns
        String placeHolderForIllegal = ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST;
        String[] illegalReplies1 = {"what", "say I should say", "what the hell is " + placeHolderForIllegal + "?!",
        placeHolderForIllegal + "? " + placeHolderForIllegal + "?! " + placeHolderForIllegal + "???!"};
        String[] illegalReplies2 = {"whaaat", "say say", placeHolderForIllegal + " is funny"};

        // Legals patterns
        String placeHolderForLegal = ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE;
        String[] legalReplies1 = {"You want me to say " + placeHolderForLegal + ", do you? alright: " + placeHolderForLegal};
        String[] legalReplies2 = {"say " + placeHolderForLegal + "? okay: " + placeHolderForLegal};


        // ChatterBots Construction
        ChatterBot[] chatterBots = new ChatterBot[2];
        chatterBots[0] = new ChatterBot(BOT_1_NAME, illegalReplies1, legalReplies1);
        chatterBots[1] = new ChatterBot(BOT_2_NAME, illegalReplies2, legalReplies2);
        Chat chatInstance = new Chat();
        chatInstance.startConversation(FIRST_STATEMENT, chatterBots);


    }

    /**
     * Initiates and manages the conversation loop between ChatterBots.
     *
     * @param seedStatement The initial statement to kick-start the conversation.
     * @param chatterBots   An array of ChatterBot instances participating in the conversation.
     */
    void startConversation(String seedStatement, ChatterBot[] chatterBots){
        String statement = seedStatement;
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
