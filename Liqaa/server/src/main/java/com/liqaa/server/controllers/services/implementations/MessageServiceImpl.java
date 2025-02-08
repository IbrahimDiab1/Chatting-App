package com.liqaa.server.controllers.services.implementations;

import com.liqaa.server.controllers.reposotories.implementations.AnnouncementRepoImpl;
import com.liqaa.server.controllers.reposotories.interfaces.MessageDAO;
import com.liqaa.server.controllers.reposotories.implementations.MessageDAOImpl;
import com.liqaa.server.controllers.services.interfaces.MessageService;
import com.liqaa.shared.models.entities.Announcement;
import com.liqaa.shared.models.entities.Message;
import com.liqaa.shared.models.enums.MessageType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MessageServiceImpl implements MessageService {
    private static final MessageServiceImpl INSTANCE = new MessageServiceImpl();
    private final MessageDAO messageDAO;

    private MessageServiceImpl() {
        this.messageDAO = new MessageDAOImpl();
    }

    public static MessageServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Message> getMessagesByConversationId(int conversationId) {
        return messageDAO.findByConversationId(conversationId);
    }
    @Override
    public void  createAnnouncement(Announcement announcement)
    {
        AnnouncementRepoImpl AnnouncementRepoImplobj=new AnnouncementRepoImpl();
        try{
            if (AnnouncementRepoImplobj.createAnnouncement(announcement))
            {
                System.out.println("Announcement created");
            }
            else
            {
                System.out.println("Announcement is not created");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<Message> getMessagesByConversationId(int conversationId, int offset, int limit) {
        return messageDAO.findByConversationId(conversationId, offset, limit);
    }

    @Override
    public void sendMessage(Message message) {
        if (message.getContent() == null || message.getContent().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }
        messageDAO.save(message);
    }

    @Override
    public int getUnreadMessageCount(int conversationId, int userId) {
        List<Message> messages = messageDAO.findByConversationId(conversationId);
        return (int) messages.stream()
                .filter(msg -> msg.getSenderId() != userId && msg.getSeenAt() == null)
                .count();
    }

    @Override
    public void markMessagesAsSeen(int conversationId, int userId) {
        List<Message> messages = messageDAO.findByConversationId(conversationId);
        for (Message message : messages) {
            if (message.getSenderId() != userId && message.getSeenAt() == null) {
                message.setSeenAt(LocalDateTime.now());
                messageDAO.update(message);
            }
        }
    }
    @Override
    public Map<String, Integer> getMessagesPerDay()
    {
        Map<String, Integer> MsgNumberPerDay=messageDAO.getMessagesPerDay();
        return MsgNumberPerDay;

    }

    // Main method for testing
    public static void main(String[] args) {
        // Initialize the DAO and Service
        MessageServiceImpl messageService = MessageServiceImpl.getInstance();

        //
        Map<String, Integer> l= messageService.getMessagesPerDay();
        for (Map.Entry<String, Integer> entry : l.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());}
        // Test data
        int conversationId = 1;
        int senderId = 1;
        int recipientId = 2;

        // Test 1: Send a message
        Message message = new Message(
                senderId,
                conversationId,
                "Hello, Hema, this is !!",
                MessageType.TEXT,
                LocalDateTime.now(),
                false,
                null,
                null
        );
        messageService.sendMessage(message);
        System.out.println("Message sent successfully!");

        // Test 2: Retrieve all messages for the conversation (without pagination)
        List<Message> allMessages = messageService.getMessagesByConversationId(conversationId);
        System.out.println("All messages in conversation " + conversationId + ":");
        allMessages.forEach(msg -> System.out.println(msg));

        // Test 3: Retrieve messages with pagination (first 5 messages)
        int offset = 0;
        int limit = 5;
        List<Message> paginatedMessages = messageService.getMessagesByConversationId(conversationId, offset, limit);
        System.out.println("\nFirst " + limit + " messages in conversation " + conversationId + ":");
        paginatedMessages.forEach(msg -> System.out.println(msg));

        // Test 4: Get the number of unread messages for the recipient
        int unreadCount = messageService.getUnreadMessageCount(conversationId, recipientId);
        System.out.println("\nNumber of unread messages for user " + recipientId + ": " + unreadCount);

        // Test 5: Mark messages as seen by the recipient
        messageService.markMessagesAsSeen(conversationId, recipientId);
        System.out.println("\nMessages marked as seen by user " + recipientId + ".");

        // Verify the number of unread messages after marking as seen
        unreadCount = messageService.getUnreadMessageCount(conversationId, recipientId);
        System.out.println("Number of unread messages after marking as seen: " + unreadCount);
    }


}