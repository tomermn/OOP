import java.util.Scanner;

/**
 * The Chat class represent a simple conversational program between ChatterBot instances.
 * It initializes bots, provides initial statements, and facilitates an ongoing conversation loop.
 * This class supports conversation with any number of bots. To add a bot to the conversation,
 * it is necessary to construct it using the ChatterBot constructor and add manually to the
 * chatterBots array.
 * @author Tomer Meidan
 * @see ChatterBot
 */
public class Chat {

    private static final String FIRST_STATEMENT = "say what";
    private static final String BOT_1_NAME = "Spongebob";
    private static final String BOT_2_NAME = "Patrick";
    private static final int BOT_COUNT = 2; // Currently set at 2, the class supports any integer number.


    /**
     * Main method that initializes ChatterBots and run the conversation between them.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {

        /* Initial illegals patterns, we will use them in the bots initialization */
        String placeHolderForIllegal = ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST;
        String[] illegalReplies1 = {
            "what", "say I should say",
            "what the hell is " + placeHolderForIllegal + "?!",
            placeHolderForIllegal + "? " + placeHolderForIllegal + "?! " + placeHolderForIllegal + "???!",
            "tell me what is " + placeHolderForIllegal,
            "ok what", "say something else", "I love you"};
        String[] illegalReplies2 = {
            "whaaat", "say say", placeHolderForIllegal + "? oh, thats funny!",
            "i dont know any of " + placeHolderForIllegal,
            "say " + placeHolderForIllegal + "?", "say something else", "bye"};

        /* Initial legals patterns, we will use them in the bots initialization */
        String placeHolderForLegal = ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE;
        String[] legalReplies1 = {
            "You want me to say " + placeHolderForLegal + ", do you? alright: " + placeHolderForLegal,
            placeHolderForLegal, "say " + placeHolderForLegal};
        String[] legalReplies2 = {
            "say " + placeHolderForLegal + "? okay: " + placeHolderForLegal, placeHolderForLegal,
            "at your service hon! " + placeHolderForLegal};

        /* ChatterBots Construction */
        ChatterBot[] chatterBots = new ChatterBot[BOT_COUNT];
        chatterBots[0] = new ChatterBot(BOT_1_NAME, illegalReplies1, legalReplies1);
        chatterBots[1] = new ChatterBot(BOT_2_NAME, illegalReplies2, legalReplies2);
        Chat chatInstance = new Chat();
        chatInstance.runConversation(FIRST_STATEMENT, chatterBots);
    }

    /*
     * Initiates and manages the conversation between ChatterBots.
     * This method simulates a conversation using an endless while loop,
     *  iterating through an array of ChatterBots.
     * At each bot's turn, it receives a statement as input from the last output
     * of the previous bot and generates an output for the next bot.
     * After completing the conversation cycle with all bots in the array,
     * it restarts the iteration from the first bot.
     * @param firstStatement The initial statement to kick-start the conversation.
     * @param chatterBots   An array of ChatterBot instances participating in the conversation.
     */
    private void runConversation(String firstStatement, ChatterBot[] chatterBots) {
        String statement = firstStatement;
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
