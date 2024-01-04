import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * @author Dan Nirel
 */
class ChatterBot {
	static final String REQUEST_PREFIX = "say ";
	static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";

	Random rand = new Random();
	String[] repliesToIllegalRequest;
	String[] legalRequestsReplies;

	String name;

	ChatterBot(String name, String[] repliesToIllegalRequest, String[] legalRequestsReplies ) {
		this.name = name;
		this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
		for(int i = 0 ; i < repliesToIllegalRequest.length ; i = i+1) {
			this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
		}

		this.legalRequestsReplies = new String[legalRequestsReplies.length];
		for(int i = 0 ; i < legalRequestsReplies.length ; i = i+1) {
			this.legalRequestsReplies[i] = legalRequestsReplies[i];
		}
	}

	String replyTo(String statement) {
		if(statement.startsWith(REQUEST_PREFIX)) {

			return replyToLegalRequest(statement);
		}
		return replyToIllegalRequest(statement);
	}

	String replyToLegalRequest(String statement) {
		String phrase = statement;
		int randomIndex = rand.nextInt(this.legalRequestsReplies.length);
		String responsePattern = this.legalRequestsReplies[randomIndex];
		String reply = responsePattern.replaceAll(PLACEHOLDER_FOR_REQUESTED_PHRASE, phrase);
		return reply;
	}


	String replyToIllegalRequest(String statement) {
		int randomIndex = rand.nextInt(repliesToIllegalRequest.length);
		String reply = repliesToIllegalRequest[randomIndex];
		if(rand.nextBoolean()) {
			reply = reply + statement;
		}
		return reply;
	}

	String getName(){
		return this.name;
	}
}
