/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestClasses;

/**
 *
 * @author Student
 */

public class MessageTest {

    // Test data from spec
    private Message msg1; // Sent:      +27834557896  "Did you get the cake?"
    private Message msg2; // Stored:    +27838884567  "Where are you? You are late! I have asked you to be on time."
    private Message msg3; // Disregard: +27834484567  "Yohoooo, I am at your gate."
    private Message msg4; // Sent:      0838884567    "It is dinner time !"
    private Message msg5; // Stored:    +27838884567  "Ok, I am leaving without you."

    @BeforeEach
    void setUp() {
        Message.resetAll();
        msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        msg4 = new Message(4, "0838884567",   "It is dinner time !");
        msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");

        msg1.sentMessage("send");
        msg2.sentMessage("store");
        msg3.sentMessage("discard");
        msg4.sentMessage("send");
        msg5.sentMessage("store");
    }

    // --- Message ID Tests ---

    @Test
    void testMessageIDGenerated() {
        assertNotNull(msg1.getMessageID());
        System.out.println("Message ID generated: " + msg1.getMessageID());
    }

    @Test
    void testMessageIDLength() {
        assertTrue(msg1.checkMessageID(), "Message ID should be <= 10 characters");
    }

    // --- Message Length Tests ---

    @Test
    void testMessageLengthValid() {
        assertEquals("Message ready to send.",
                msg1.checkMessageLength("Did you get the cake?"));
    }

    @Test
    void testMessageLengthInvalid() {
        String longMsg = "A".repeat(260);
        String result = msg1.checkMessageLength(longMsg);
        assertTrue(result.contains("Message exceeds 250 characters by 10"));
    }

    // --- Recipient Cell Tests ---

    @Test
    void testRecipientCellValid() {
        assertEquals("Cell phone number successfully captured.",
                msg1.checkRecipientCell("+27834557896"));
    }

    @Test
    void testRecipientCellInvalid_noInternationalCode() {
        assertTrue(msg4.checkRecipientCell("0838884567")
                .contains("incorrectly formatted"));
    }

    // --- Message Hash Tests ---

    @Test
    void testMessageHashUpperCase() {
        String hash = msg1.getMessageHash();
        assertEquals(hash.toUpperCase(), hash);
    }

    @Test
    void testMessageHashFormat_msg1() {
        // "Did you get the cake?" -> first=Did, last=cake -> DIDCAKE
        assertTrue(msg1.getMessageHash().endsWith(":1:DIDCAKE"),
                "Hash should end with :1:DIDCAKE but was: " + msg1.getMessageHash());
    }

    // --- sentMessage Tests ---

    @Test
    void testSentMessage_send() {
        Message.resetAll();
        Message m = new Message(1, "+27834557896", "Did you get the cake?");
        assertEquals("Message successfully sent.", m.sentMessage("send"));
    }

    @Test
    void testSentMessage_discard() {
        Message.resetAll();
        Message m = new Message(1, "+27834557896", "Did you get the cake?");
        assertEquals("Press 0 to delete the message.", m.sentMessage("discard"));
    }

    @Test
    void testSentMessage_store() {
        Message.resetAll();
        Message m = new Message(1, "+27834557896", "Did you get the cake?");
        assertEquals("Message successfully stored.", m.sentMessage("store"));
    }

    // --- Part 3: Sent Messages Array ---

    @Test
    void testSentMessagesArrayPopulated() {
        List<String> texts = Message.getSentMessageTexts();
        // msg1 and msg4 were sent
        assertTrue(texts.contains("Did you get the cake?"),
                "Sent array should contain msg1");
        assertTrue(texts.contains("It is dinner time !"),
                "Sent array should contain msg4");
    }

    // --- Part 3: Longest Message ---

    @Test
    void testLongestMessage() {
        String longest = Message.getLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest,
                "Longest message should be msg2");
    }

    // --- Part 3: Search by Message ID ---

    @Test
    void testSearchByMessageID() {
        String result = Message.searchByMessageID(msg4.getMessageID());
        assertTrue(result.contains("It is dinner time !"),
                "Should find msg4 by its ID");
    }

    @Test
    void testSearchByMessageID_notFound() {
        assertEquals("Message ID not found.", Message.searchByMessageID("0000000000"));
    }

    // --- Part 3: Search by Recipient ---

    @Test
    void testSearchByRecipient() {
        List<String> results = Message.searchByRecipient("+27838884567");
        // msg2 (stored) and msg5 (stored) both have this recipient
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."),
                "Should find msg2 for recipient +27838884567");
        assertTrue(results.contains("Ok, I am leaving without you."),
                "Should find msg5 for recipient +27838884567");
    }

    // --- Part 3: Delete by Hash ---

    @Test
    void testDeleteByHash() {
        String hash = msg2.getMessageHash();
        String result = Message.deleteByHash(hash);
        assertTrue(result.contains("successfully deleted"),
                "Should confirm message deleted");
    }

    // --- Part 3: Display Report ---

    @Test
    void testDisplayReport() {
        String report = Message.displayReport();
        assertTrue(report.contains("Message Hash:"), "Report should show hashes");
        assertTrue(report.contains("Recipient:"),    "Report should show recipients");
        assertTrue(report.contains("Message:"),      "Report should show messages");
    }

    // --- Total Messages ---

    @Test
    void testReturnTotalMessages() {
        // msg1 and msg4 were sent
        assertEquals(2, msg1.returnTotalMessages());
    }
}
