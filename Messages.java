/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PROG5121;

/**
 *
 * @author Student
 */

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String flag; // "sent", "stored", "disregarded"

    // Arrays as required by Part 3
    private static List<Message> sentMessages      = new ArrayList<>();
    private static List<Message> disregardedMessages = new ArrayList<>();
    private static List<Message> storedMessages    = new ArrayList<>();
    private static List<String>  messageHashes     = new ArrayList<>();
    private static List<String>  messageIDs        = new ArrayList<>();
    private static int totalMessagesSent = 0;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.messageID     = generateMessageID();
        this.messageHash   = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        long id = (long)(rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell(String cell) {
        if (cell == null || !cell.startsWith("+"))
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        String afterPlus = cell.substring(1);
        if (afterPlus.length() > 10)
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        for (char c : afterPlus.toCharArray())
            if (!Character.isDigit(c))
                return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        return "Cell phone number successfully captured.";
    }

    public String createMessageHash() {
        String idPrefix = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        String hash = (idPrefix + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        this.messageHash = hash;
        return hash;
    }

    public String checkMessageLength(String message) {
        if (message.length() <= 250) return "Message ready to send.";
        int excess = message.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    public String sentMessage(String action) {
        messageIDs.add(this.messageID);
        messageHashes.add(this.messageHash);
        switch (action.toLowerCase()) {
            case "send":
                this.flag = "sent";
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case "discard":
                this.flag = "disregarded";
                disregardedMessages.add(this);
                return "Press 0 to delete the message.";
            case "store":
                this.flag = "stored";
                storedMessages.add(this);
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // --- Part 3 methods ---

    /** Returns all sent messages text as a List */
    public static List<String> getSentMessageTexts() {
        List<String> texts = new ArrayList<>();
        for (Message m : sentMessages) texts.add(m.messageText);
        return texts;
    }

    /** Returns the longest stored message */
    public static String getLongestMessage() {
        List<Message> all = new ArrayList<>(sentMessages);
        all.addAll(storedMessages);
        if (all.isEmpty()) return "No messages found.";
        Message longest = all.get(0);
        for (Message m : all)
            if (m.messageText.length() > longest.messageText.length())
                longest = m;
        return longest.messageText;
    }

    /** Search for message by ID, return recipient and message */
    public static String searchByMessageID(String id) {
        List<Message> all = new ArrayList<>(sentMessages);
        all.addAll(storedMessages);
        all.addAll(disregardedMessages);
        for (Message m : all)
            if (m.messageID.equals(id))
                return "Recipient: " + m.recipient + "\nMessage: " + m.messageText;
        return "Message ID not found.";
    }

    /** Search all messages for a particular recipient */
    public static List<String> searchByRecipient(String recipient) {
        List<String> results = new ArrayList<>();
        List<Message> all = new ArrayList<>(sentMessages);
        all.addAll(storedMessages);
        for (Message m : all)
            if (m.recipient.equals(recipient))
                results.add(m.messageText);
        return results;
    }

    /** Delete a message using its hash */
    public static String deleteByHash(String hash) {
        for (Message m : storedMessages) {
            if (m.messageHash.equals(hash)) {
                storedMessages.remove(m);
                messageHashes.remove(hash);
                return "Message: \"" + m.messageText + "\" successfully deleted.";
            }
        }
        for (Message m : sentMessages) {
            if (m.messageHash.equals(hash)) {
                sentMessages.remove(m);
                messageHashes.remove(hash);
                return "Message: \"" + m.messageText + "\" successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    /** Display full report of all sent messages */
    public static String displayReport() {
        if (sentMessages.isEmpty()) return "No messages sent.";
        StringBuilder sb = new StringBuilder("=== Message Report ===\n");
        for (Message m : sentMessages) {
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n");
            sb.append("------------------------------\n");
        }
        return sb.toString();
    }

    public String printMessages() {
        if (sentMessages.isEmpty()) return "No messages sent.";
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n");
            sb.append("----------------------------\n");
        }
        return sb.toString();
    }

    public int returnTotalMessages() { return totalMessagesSent; }

    public String storeMessage() {
        StringBuilder json = new StringBuilder("[\n");
        List<Message> all = new ArrayList<>(sentMessages);
        all.addAll(storedMessages);
        for (int i = 0; i < all.size(); i++) {
            Message m = all.get(i);
            json.append("  {\"messageID\":\"").append(m.messageID)
                .append("\",\"messageNumber\":").append(m.messageNumber)
                .append(",\"recipient\":\"").append(m.recipient)
                .append("\",\"message\":\"").append(m.messageText)
                .append("\",\"messageHash\":\"").append(m.messageHash).append("\"}");
            if (i < all.size() - 1) json.append(",");
            json.append("\n");
        }
        return json.append("]").toString();
    }

    public static void resetAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        totalMessagesSent = 0;
    }

    public String getMessageID()     { return messageID;     }
    public String getMessageHash()   { return messageHash;   }
    public String getRecipient()     { return recipient;     }
    public String getMessageText()   { return messageText;   }
    public String getFlag()          { return flag;          }
    public int    getMessageNumber() { return messageNumber; }

    public static List<Message> getSentMessages()        { return sentMessages;        }
    public static List<Message> getStoredMessages()      { return storedMessages;      }
    public static List<Message> getDisregardedMessages() { return disregardedMessages; }
    public static List<String>  getMessageHashes()       { return messageHashes;       }
    public static List<String>  getMessageIDs()          { return messageIDs;          }
}
