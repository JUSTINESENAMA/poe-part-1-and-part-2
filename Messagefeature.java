/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.messagefeature;

/**
 *
 * @author Justine
 */
public class MessageFeature {
    private final String MESSAGE_ID;
    private final String MESSAGE_RECIPIENT;
    private final String MESSAGE_PAYLOAD;
    private int MESSAGE_INDEX;
    private String MESSAGE_HASH;
    private String messageStatus;

    private static int messageDispatchCounter = 0;
    private static final int MAX_PAYLOAD_LENGTH = 250;
    private static final Random idGeneratorRandom = new Random();
    private static ArrayList<MessageFeature> allSentMessages = new ArrayList<>();
    private static ArrayList<MessageFeature> allDisregardedMessages = new ArrayList<>();
    private static ArrayList<MessageFeature> allStoredMessages = new ArrayList<>(); 
    private static ArrayList<String> allMessageHashes = new ArrayList<>();
    private static ArrayList<String> allMessageIDs = new ArrayList<>();
    private static String loggedInUsername = "";

    public static void setLoggedInUsername(String username) {
        MessageFeature.loggedInUsername = username;
    }

    public MessageFeature(final String recipient, final String payload) {
    
        this.MESSAGE_ID = String.format("%010d", Math.abs(idGeneratorRandom.nextLong() % 10000000000L));
        this.MESSAGE_RECIPIENT = recipient;
        this.MESSAGE_PAYLOAD = payload;
        this.MESSAGE_INDEX = 0; 
        this.MESSAGE_HASH = "";  
        this.messageStatus = "New";
    }

    public String getMessageID() { return MESSAGE_ID; }
    public String getMessageRecipient() { return MESSAGE_RECIPIENT; }
    public String getMessagePayload() { return MESSAGE_PAYLOAD; }
    public int getMessageIndex() { return MESSAGE_INDEX; }
    public String getMessageHash() { return MESSAGE_HASH; }
    public String getMessageStatus() { return messageStatus; }

    public boolean checkMessageID(final String id) {
        if (id == null) {
            return false;
        }
        return id.matches("\\d{10}");
    }

 
     * @param payload The message content.
     * @return A status string: "Message ready to send." or an error message with details.
     */
    public String validatePayloadLength(final String payload) {
        if (payload == null) {
 
            return "Message exceeds " + MAX_PAYLOAD_LENGTH + " characters by " + (0 - MAX_PAYLOAD_LENGTH) + ", please reduce size.";
        }
        if (payload.length() <= MAX_PAYLOAD_LENGTH) {
            return "Message ready to send.";
        } else {
            int excess = payload.length() - MAX_PAYLOAD_LENGTH;
            return "Message exceeds " + MAX_PAYLOAD_LENGTH + " characters by " + excess + ", please reduce size.";
        }
    }

    /**
     * Validates the recipient's cellphone number format (+27 followed by 9 digits).
     *
     * @param recipient The cellphone number.
     * @return A status string: "Cell phone number successfully captured." or an error message.
     */
    public String validateRecipientNumber(final String recipient) {
        if (recipient == null || recipient.isBlank()) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        String regexSA = "^\\+27[0-9]{9}$";
        if (Pattern.matches(regexSA, recipient)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    
    public String createMessageHash(final String id, int index, final String payload) {
        if (id == null || id.length() < 2 || payload == null) {
            return ""; s
        }

        String idStart = id.substring(0, 2);
        String content = payload.trim(); 
        if (content.isEmpty()) {
            return (idStart + ":" + index + ":").toUpperCase(); 
        }

        String[] words = content.split("\\s+");
        String firstWord = words[0];
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
        lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");

        return (idStart + ":" + index + ":" + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage() {
        if (this.MESSAGE_PAYLOAD == null || this.MESSAGE_PAYLOAD.trim().isEmpty()) {
            return "Failed to send message: Message content cannot be empty";
        }

        String payloadValMsg = validatePayloadLength(this.MESSAGE_PAYLOAD);
        if (!payloadValMsg.equals("Message ready to send.")) {
            if (this.MESSAGE_PAYLOAD.length() > MAX_PAYLOAD_LENGTH) {
                 return "Failed to send message: Payload too long";
            }
            return payloadValMsg; 
        }

           String recipientValMsg = validateRecipientNumber(this.MESSAGE_RECIPIENT);
        if (!recipientValMsg.equals("Cell phone number successfully captured.")) {
            return "Failed to send message: Invalid recipient";
        }

        if (!checkMessageID(this.MESSAGE_ID)) {
            return "Failed to send message: Invalid message ID (system error)";
        }

        messageDispatchCounter++;
        this.MESSAGE_INDEX = messageDispatchCounter;
        this.MESSAGE_HASH = createMessageHash(this.MESSAGE_ID, this.MESSAGE_INDEX, this.MESSAGE_PAYLOAD);
        this.messageStatus = "Sent";

        allSentMessages.add(this);
        allMessageIDs.add(this.MESSAGE_ID);
        allMessageHashes.add(this.MESSAGE_HASH);

        return "Message successfully sent.";
    }
    
        public void disregardMessage() {
        this.messageStatus = "Disregarded"; /
        allDisregardedMessages.add(this);
    }

        public String storeMessage() {
        JSONObject msgJson = new JSONObject();
        msgJson.put("MESSAGE_ID", this.MESSAGE_ID);
        msgJson.put("MESSAGE_RECIPIENT", this.MESSAGE_RECIPIENT);
        msgJson.put("MESSAGE_PAYLOAD", this.MESSAGE_PAYLOAD);
        msgJson.put("MESSAGE_INDEX", this.MESSAGE_INDEX); // 

        
        if (this.MESSAGE_HASH.isEmpty()) {
            this.MESSAGE_HASH = createMessageHash(this.MESSAGE_ID, this.MESSAGE_INDEX, this.MESSAGE_PAYLOAD);
        }
        msgJson.put("MESSAGE_HASH", this.MESSAGE_HASH);

        String statusToSaveInJson = this.messageStatus;
        if (this.messageStatus.equals("New")) {
                     this.messageStatus = "Stored";
            statusToSaveInJson = "Stored"; 
        }
                msgJson.put("MESSAGE_STATUS", statusToSaveInJson);

        String fileName;
                if (this.MESSAGE_INDEX == 0) {
            fileName = "message_draft_" + this.MESSAGE_ID + ".json";
        } else {
            fileName = "message_" + this.MESSAGE_INDEX + ".json";
        }

        if (!allStoredMessages.contains(this)) {
            allStoredMessages.add(this);
        }

        if (!allMessageIDs.contains(this.MESSAGE_ID)) {
            allMessageIDs.add(this.MESSAGE_ID);
        }
        if (!this.MESSAGE_HASH.isEmpty() && !allMessageHashes.contains(this.MESSAGE_HASH)) {
            allMessageHashes.add(this.MESSAGE_HASH);
        }

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(msgJson.toJSONString());
            return "Message successfully stored.";
        } catch (IOException e) {
            // No console output as per user's request
            return "Failed to store message: IO Exception.";
        }
    }

    
    public String getGeneratedIdNotification() {
        return "Message ID generated: " + this.MESSAGE_ID;
    }

    public static int returnTotalMessages() {
        return messageDispatchCounter;
    }

    public static void resetMessageCounterForTesting() {
        messageDispatchCounter = 0;
        allSentMessages.clear();
        allDisregardedMessages.clear();
        allStoredMessages.clear();
        allMessageHashes.clear();
        allMessageIDs.clear();
        loggedInUsername = ""; 
        File currentDir = new File(".");
        File[] files = currentDir.listFiles((dir, name) -> name.matches("message_.*\\.json|message_draft_.*\\.json"));
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
   
    public static void loadAllMessagesFromJsonFiles() {

        allSentMessages.clear();
        allDisregardedMessages.clear();
        allStoredMessages.clear();
        allMessageHashes.clear();
        allMessageIDs.clear();
        messageDispatchCounter = 0; 

        File currentDir = new File(".");
        File[] files = currentDir.listFiles((dir, name) -> name.matches("message_.*\\.json|message_draft_.*\\.json"));

        if (files == null) {
            return; 
        }

        JSONParser parser = new JSONParser();

        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                Object obj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;

                String id = (String) jsonObject.get("MESSAGE_ID");
                String recipient = (String) jsonObject.get("MESSAGE_RECIPIENT");
                String payload = (String) jsonObject.get("MESSAGE_PAYLOAD");
                Long indexLong = (Long) jsonObject.get("MESSAGE_INDEX");
                int index = (indexLong != null) ? indexLong.intValue() : 0;
                String hash = (String) jsonObject.get("MESSAGE_HASH");
                String status = (String) jsonObject.get("MESSAGE_STATUS");

                MessageFeature loadedMessage = new MessageFeature(recipient, payload);
                loadedMessage.MESSAGE_INDEX = index;
                loadedMessage.MESSAGE_HASH = (hash != null) ? hash : ""; /
                loadedMessage.messageStatus = (status != null) ? status : (index == 0 ? "Stored" : "Sent");
                if (loadedMessage.getMessageStatus().equals("Sent")) {
                    allSentMessages.add(loadedMessage);
                    if (loadedMessage.MESSAGE_INDEX > messageDispatchCounter) {
                        messageDispatchCounter = loadedMessage.MESSAGE_INDEX;
                    }
                } else if (loadedMessage.getMessageStatus().equals("Stored")) {
                    allStoredMessages.add(loadedMessage);
                } else if (loadedMessage.getMessageStatus().equals("Disregarded")) {
                    allDisregardedMessages.add(loadedMessage);
                }

                if (!allMessageIDs.contains(loadedMessage.MESSAGE_ID)) {
                    allMessageIDs.add(loadedMessage.MESSAGE_ID);
                }
                if (!loadedMessage.MESSAGE_HASH.isEmpty() && !allMessageHashes.contains(loadedMessage.MESSAGE_HASH)) {
                    allMessageHashes.add(loadedMessage.MESSAGE_HASH);
                }

            } catch (IOException | ParseException e) {

        }
    }

    public static String displayAllSentMessagesInfo() {
        if (allSentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }

        StringBuilder sb = new StringBuilder("--- All Sent Messages ---\n");
        for (MessageFeature msg : allSentMessages) {
            sb.append("Sender: ").append(loggedInUsername.isEmpty() ? "Unknown" : loggedInUsername)
              .append(", Recipient: ").append(msg.getMessageRecipient()).append("\n");
        }
        return sb.toString();
    }

    public static String findLongestSentMessage() {
        ArrayList<MessageFeature> allRelevantMessages = new ArrayList<>();
        allRelevantMessages.addAll(allSentMessages);

        for (MessageFeature storedMsg : allStoredMessages) {
            boolean isDuplicate = false;
            for (MessageFeature sentMsg : allSentMessages) {
                if (storedMsg.getMessageID().equals(sentMsg.getMessageID())) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                allRelevantMessages.add(storedMsg);
            }
        }

        if (allRelevantMessages.isEmpty()) {
            return "No messages have been sent or stored to determine the longest.";
        }

        String longestMessagePayload = "";
        int maxLength = 0;

        for (MessageFeature msg : allRelevantMessages) {
            if (msg.MESSAGE_PAYLOAD != null && msg.MESSAGE_PAYLOAD.length() > maxLength) {
                maxLength = msg.MESSAGE_PAYLOAD.length();
                longestMessagePayload = msg.MESSAGE_PAYLOAD;
            }
        }
        return longestMessagePayload;
    }
    
 
    public static String searchMessageByID(String searchID) {
        if (searchID == null || searchID.trim().isEmpty()) {
            return "Please provide a message ID to search.";
        }

        for (MessageFeature msg : allSentMessages) {
            if (msg.getMessageID().equals(searchID)) {
                return "Message Found (Sent):\n" +
                       "Recipient: " + msg.getMessageRecipient() + "\n" +
                       "Message: \"" + msg.getMessagePayload() + "\"";
            }
        }

        for (MessageFeature msg : allStoredMessages) {
            if (msg.getMessageID().equals(searchID)) {
                return "Message Found (Stored):\n" +
                       "Recipient: " + msg.getMessageRecipient() + "\n" +
                       "Message: \"" + msg.getMessagePayload() + "\"";
            }
        }

        return "No message found with ID: " + searchID;
    }

    public static String searchMessagesByRecipient(String searchRecipient) {
        if (searchRecipient == null || searchRecipient.trim().isEmpty()) {
            return "Please provide a recipient number to search.";
        }

        StringBuilder sb = new StringBuilder("--- Messages for Recipient: ").append(searchRecipient).append(" ---\n");
        boolean found = false;

        ArrayList<MessageFeature> messagesForRecipient = new ArrayList<>();

        for (MessageFeature msg : allSentMessages) {
            if (msg.getMessageRecipient().equals(searchRecipient)) {
                messagesForRecipient.add(msg);
                found = true;
            }
        }

        for (MessageFeature msg : allStoredMessages) {
            if (msg.getMessageRecipient().equals(searchRecipient)) {
                boolean isDuplicate = false;
                for (MessageFeature existingMsg : messagesForRecipient) {
                    if (existingMsg.getMessageID().equals(msg.getMessageID())) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    messagesForRecipient.add(msg);
                    found = true;
                }
            }
        }
        
        if (!found) {
            return "No messages found for recipient: " + searchRecipient;
        }

        for (MessageFeature msg : messagesForRecipient) {
            sb.append(msg.getMessageStatus()).append(": \"").append(msg.getMessagePayload()).append("\"\n");
        }

        return sb.toString();
    }

    public static String deleteMessageByHash(String hashToDelete) {
        if (hashToDelete == null || hashToDelete.trim().isEmpty()) {
            return "Please provide a message hash to delete.";
        }

        boolean foundAndDeleted = false;
        String deletedMessagePayload = "";
        String deletedMessageID = "";
        File fileToDelete = null;

        Iterator<MessageFeature> sentIt = allSentMessages.iterator();
        while (sentIt.hasNext()) {
            MessageFeature msg = sentIt.next();
            if (msg.getMessageHash().equals(hashToDelete)) {
                deletedMessagePayload = msg.getMessagePayload();
                deletedMessageID = msg.getMessageID();
                sentIt.remove();
                foundAndDeleted = true;
                break; 
            }
        }

        Iterator<MessageFeature> storedIt = allStoredMessages.iterator();
        while (storedIt.hasNext()) {
            MessageFeature msg = storedIt.next();
            if (msg.getMessageHash().equals(hashToDelete)) {
                deletedMessagePayload = msg.getMessagePayload();
                deletedMessageID = msg.getMessageID();
                storedIt.remove();
                foundAndDeleted = true;
                String fileName;
                if (msg.MESSAGE_INDEX == 0) { // Drafts
                    fileName = "message_draft_" + msg.MESSAGE_ID + ".json";
                } else {
                    fileName = "message_" + msg.MESSAGE_INDEX + ".json";
                }
                fileToDelete = new File(fileName);
                break;
            }
        }

        Iterator<MessageFeature> disregardedIt = allDisregardedMessages.iterator();
        while (disregardedIt.hasNext()) {
            MessageFeature msg = disregardedIt.next();
            if (msg.getMessageHash().equals(hashToDelete)) {
                deletedMessagePayload = msg.getMessagePayload();
                deletedMessageID = msg.getMessageID();
                disregardedIt.remove();
                foundAndDeleted = true;
                break;
            }
        }


        if (foundAndDeleted) {
            allMessageIDs.remove(deletedMessageID);
            allMessageHashes.remove(hashToDelete);

            if (fileToDelete != null && fileToDelete.exists()) {
                fileToDelete.delete();
            }
            return "Message \"" + deletedMessagePayload + "\" successfully deleted.";
        } else {
            return "Message with hash " + hashToDelete + " not found.";
        }
    }

    public static String generateSentMessagesReport() {
        if (allSentMessages.isEmpty()) {
            return "No sent messages to report.";
        }

        StringBuilder sb = new StringBuilder("--- QuickChat Sent Messages Report ---\n\n");
        for (int i = 0; i < allSentMessages.size(); i++) {
            MessageFeature msg = allSentMessages.get(i);
            sb.append("Message #").append(i + 1).append(":\n");
            sb.append("  Hash: ").append(msg.getMessageHash()).append("\n");
            sb.append("  Recipient: ").append(msg.getMessageRecipient()).append("\n");
            sb.append("  Message: \"").append(msg.getMessagePayload()).append("\"\n\n");
        }
        return sb.toString();
    }
    
    public static ArrayList<String> getAllMessageIDs() {
        return new ArrayList<>(allMessageIDs); 
    }

    public static ArrayList<String> getAllMessageHashes() {
        return new ArrayList<>(allMessageHashes); 
    }
 
    public static ArrayList<MessageFeature> getSentMessagesForTesting() {
        return allSentMessages;
    }

    public static ArrayList<MessageFeature> getStoredMessagesForTesting() {
        return allStoredMessages;
    }

    public static ArrayList<MessageFeature> getDisregardedMessagesForTesting() {
        return allDisregardedMessages;
    }
}