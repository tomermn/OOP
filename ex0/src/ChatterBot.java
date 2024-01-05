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
	static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";
	private Random rand = new Random();
	private String[] repliesToIllegalRequest;
	private String[] legalRequestsReplies;
	private String name;


	/**
	 * Constructor for ChatterBot class.
	 * @param name                    The name of the ChatterBot.
	 * @param repliesToIllegalRequest An array of possible replies for illegal requests.
	 * @param legalRequestsReplies    An array of possible replies for legal requests.
	 */
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

	/**
	 * Generates ChatterBot's reply based on the provided statement.
	 *
	 * If the statement starts with the constant REQUEST_PREFIX, it generates a legal request reply.
	 * Otherwise, it generates a reply for an illegal request.
	 *
	 * @param statement The input statement for which the ChatterBot generates a reply.
	 * @return The generated reply.
	 */
	public String replyTo(String statement) {
		if(statement.startsWith(REQUEST_PREFIX)) {
			return replyToLegalRequest(statement);
		}

		return replyToIllegalRequest(statement);
	}

	/**
	 * Generates a reply for a legal request.
	 *
	 * @param statement The request statement.
	 * @return The generated reply.
	 */
	public String replyToLegalRequest(String statement) {
		String reply = replacePlaceholderInARandomPattern(this.legalRequestsReplies,
				ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE, statement.replaceFirst(REQUEST_PREFIX, ""));

		return reply;
	}


	/**
	 * Generates a reply for an illegal request.
	 *
	 * @param statement The request statement.
	 * @return The generated reply.
	 */
	public String replyToIllegalRequest(String statement) {

		String reply = replacePlaceholderInARandomPattern(this.repliesToIllegalRequest,
				ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement);

		return reply;
	}

	/**
	 * Replaces a placeholder in a random pattern with the provided replacement.
	 *
	 * @param patterns    An array of possible patterns.
	 * @param placeholder The placeholder to be replaced in the pattern.
	 * @param replacement The replacement for the placeholder.
	 * @return The pattern with the placeholder replaced by the provided replacement.
	 */
	public String replacePlaceholderInARandomPattern(String[] patterns, String placeholder,
													 String replacement) {

		int randomIndex = rand.nextInt(patterns.length);
		String responsePattern = patterns[randomIndex];
		return responsePattern.replaceAll(placeholder, replacement);
	}

	/**
	 * Retrieves the name of the ChatterBot.
	 *
	 * @return The name of the ChatterBot.
	 */
	public String getName() {
		return this.name;
	}
}
