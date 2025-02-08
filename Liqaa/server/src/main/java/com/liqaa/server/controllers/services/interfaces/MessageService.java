package com.liqaa.server.controllers.services.interfaces;

import com.liqaa.shared.models.entities.Announcement;
import com.liqaa.shared.models.entities.Message;
import java.util.List;
import java.util.Map;

public interface MessageService {
    List<Message> getMessagesByConversationId(int conversationId); // Without pagination
    List<Message> getMessagesByConversationId(int conversationId, int offset, int limit); // With pagination
    void sendMessage(Message message); // Send a message
    int getUnreadMessageCount(int conversationId, int userId); // Get unread message count
    void markMessagesAsSeen(int conversationId, int userId); // Mark messages as seen
    public Map<String, Integer> getMessagesPerDay();
     public void  createAnnouncement(Announcement announcement);
}