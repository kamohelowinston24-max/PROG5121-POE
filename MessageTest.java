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
    
}package TestClasses;

import PROG5121.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private Message msg1;
    private Message msg2;

    @BeforeEach
    void setUp() {
        Message.resetAll();
        // Test Data Task 1
        msg1 = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        // Test Data Task 2
        msg2 = new Message(2, "08575975889", "Hi Keegan, did you receive the payment?");
    }

    // --- Message ID Tests ---

    @Test
    void testMessageIDGenerated() {
        assertNotNull(msg1.getMessageID(), "Message ID should be generated");
        System.out.println("Message ID generated: " + msg1.getMessageID());
    }

    @Test
    void testMessageIDLength() {
        assertTrue(msg1.checkMessageID(),
                "Message ID should be no more than 10 characters");
    }

    // --- Message Length Tests ---

    @Test
    void testMessageLengthValid() {
        String result = msg1.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", result,
                "Short message should be ready to send");
    }

    @Test
    void testMessageLengthInvalid() {
        String longMsg = "A".repeat(260);
        String result = msg1.checkMessageLength(longMsg);
        assertTrue(result.contains("Message exceeds 250 characters by 10"),
                "Should report how many characters over the limit");
    }

    // --- Recipient Cell Tests ---

    @Test
    void testRecipientCellValid() {
        String result = msg1.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result,
                "Valid international number should be captured");
    }

    @Test
    void testRecipientCellInvalid_noInternationalCode() {
        String result = msg2.checkRecipientCell("08575975889");
        assertTrue(result.contains("Cell phone number is incorrectly formatted"),
                "Number without + should be invalid");
    }

    // --- Message Hash Tests ---

    @Test
    void testMessageHashFormat_task1() {
        // msg1: "Hi Mike, can you join us for dinner tonight?"
        // Hash = first2ofID + ":" + msgNum + ":" + first word + last word (no punct), caps
        // Expected pattern: XX:1:HITONIGHT
        String hash = msg1.getMessageHash();
        assertNotNull(hash, "Hash should be generated");
        assertTrue(hash.endsWith(":1:HITONIGHT"),
                "Hash for message 1 should end with :1:HITONIGHT but was: " + hash);
        System.out.println("Message 1 Hash: " + hash);
    }

    @Test
    void testMessageHashFormat_task2() {
        // msg2: "Hi Keegan, did you receive the payment?"
        // Expected pattern: XX:2:HIPAYMENT
        String hash = msg2.getMessageHash();
        assertTrue(hash.endsWith(":2:HIPAYMENT"),
                "Hash for message 2 should end with :2:HIPAYMENT but was: " + hash);
        System.out.println("Message 2 Hash: " + hash);
    }

    @Test
    void testAllMessageHashesUpperCase() {
        String hash = msg1.getMessageHash();
        assertEquals(hash.toUpperCase(), hash,
                "Message hash should be all uppercase");
    }

    // --- sentMessage Tests ---

    @Test
    void testSentMessage_send() {
        String result = msg1.sentMessage("send");
        assertEquals("Message successfully sent.", result,
                "Selecting send should return success message");
    }

    @Test
    void testSentMessage_discard() {
        String result = msg1.sentMessage("discard");
        assertEquals("Press 0 to delete the message.", result,
                "Selecting discard should return discard message");
    }

    @Test
    void testSentMessage_store() {
        String result = msg1.sentMessage("store");
        assertEquals("Message successfully stored.", result,
                "Selecting store should return stored message");
    }

    // --- Total Messages Tests ---

    @Test
    void testReturnTotalMessages() {
        msg1.sentMessage("send");
        msg2.sentMessage("send");
        assertEquals(2, msg1.returnTotalMessages(),
                "Total messages sent should be 2");
    }

    @Test
    void testReturnTotalMessages_discardNotCounted() {
        msg1.sentMessage("send");
        msg2.sentMessage("discard"); // discard should NOT count
        assertEquals(1, msg1.returnTotalMessages(),
                "Discarded messages should not be counted");
    }

    // --- printMessages Test ---

    @Test
    void testPrintMessages() {
        msg1.sentMessage("send");
        String output = msg1.printMessages();
        assertTrue(output.contains("Message ID:"), "Output should contain Message ID");
        assertTrue(output.contains("Recipient:"),  "Output should contain Recipient");
        assertTrue(output.contains("Message:"),    "Output should contain Message");
    }
}
