import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatterBotTest {

    @Test
    void replyToLegalRequest() {
        String[] repliesToIllegalRequest = {"Illegal request response 1", "Illegal request response 2"};
        String[] legalRequestsReplies = {"Legal request response 1: <phrase>", "Legal request response 2: <phrase>"};
        ChatterBot bot = new ChatterBot("TestBot", repliesToIllegalRequest, legalRequestsReplies);

        String legalRequest = "say Hello";
        String reply = bot.replyToLegalRequest(legalRequest);

        assertTrue(reply.startsWith("Legal request response"));
        assertFalse(reply.contains("<phrase>"));

    }

    @Test
    void replyToIllegalRequest() {
        String[] repliesToIllegalRequest = {"Illegal request response 1", "Illegal request response 2"};
        String[] legalRequestsReplies = {"Legal request response 1: <phrase>", "Legal request response 2: <phrase>"};
        ChatterBot bot = new ChatterBot("TestBot", repliesToIllegalRequest, legalRequestsReplies);

        String illegalRequest = "This is an illegal request";
        String reply = bot.replyToIllegalRequest(illegalRequest);

        assertTrue(reply.startsWith("Illegal request response"));
    }

    @Test
    void replacePlaceholderInARandomPattern() {
        String[] patterns = {"Pattern 1: <placeholder>", "Pattern 2: <placeholder>"};
        ChatterBot bot = new ChatterBot("TestBot", new String[]{}, new String[]{});

        String replacement = "Replaced";
        String result = bot.replacePlaceholderInARandomPattern(patterns, "<placeholder>", replacement);

        assertTrue(result.equals("Pattern 1: Replaced") || result.equals("Pattern 2: Replaced"));
    }

    @Test
    void getName() {
        String[] repliesToIllegalRequest = {"Illegal request response 1", "Illegal request response 2"};
        String[] legalRequestsReplies = {"Legal request response 1: <phrase>", "Legal request response 2: <phrase>"};
        ChatterBot bot = new ChatterBot("TestBot", repliesToIllegalRequest, legalRequestsReplies);

        assertEquals("TestBot", bot.getName());
    }
}