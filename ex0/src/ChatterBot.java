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

//	Const fields
	static final String REQUEST_PREFIX = "say ";
	static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
	static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";
	Random rand = new Random();
	String[] repliesToIllegalRequest;
	String[] legalRequestsReplies;
	String name;


	/**
	 * Constructor for ChatterBot class
	* */
	ChatterBot(String name, String[] repliesToIllegalRequest, String[] legalRequestsReplies) {

		this.name = name;

		this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
		for (int i = 0 ; i < repliesToIllegalRequest.length ; i++) {
			this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
		}

		this.legalRequestsReplies = new String[legalRequestsReplies.length];
		for (int i = 0 ; i < legalRequestsReplies.length ; i++) {
			this.legalRequestsReplies[i] = legalRequestsReplies[i];
		}
	}

	public String replyTo(String statement) {
		if(statement.startsWith(REQUEST_PREFIX)) {
			return replyToLegalRequest(statement.replaceFirst(REQUEST_PREFIX, ""));
		}
		return replyToIllegalRequest(statement);
	}

	public String replyToLegalRequest(String statement) {
		String reply = replacePlaceholderInARandomPattern(this.legalRequestsReplies,
				this.PLACEHOLDER_FOR_REQUESTED_PHRASE, statement);

		return reply;
		}


	String replyToIllegalRequest(String statement) {
		String reply = replacePlaceholderInARandomPattern(this.repliesToIllegalRequest,
				this.PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement);

		return reply;
	}

	public String replacePlaceholderInARandomPattern(String[] patterns, String placeholder, String replacement){
		int randomIndex = rand.nextInt(patterns.length);
		String responsePattern = patterns[randomIndex];
		return responsePattern.replaceAll(placeholder, replacement);
	}



	public String getName() {
		return this.name;
	}
}
