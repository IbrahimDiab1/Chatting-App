package com.liqaa.server.controllers.reposotories.implementations;

import com.liqaa.server.controllers.reposotories.interfaces.MessageDAO;
import com.liqaa.server.util.DatabaseManager;
import com.liqaa.shared.models.entities.Message;
import com.liqaa.shared.models.enums.MessageType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageDAOImpl implements MessageDAO {

    private MessageType getMessageTypeFromString(String value) {
        try {
            return MessageType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid MessageType value: " + value);
            return MessageType.TEXT; // Default to TEXT if invalid
        }
    }

    @Override
    public Optional<Message> findById(int id) {
        String sql = "SELECT * FROM messages WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Message message = new Message(
                        rs.getInt("sender_id"),
                        rs.getInt("conversation_id"),
                        rs.getString("content"),
                        getMessageTypeFromString(rs.getString("type")),
                        rs.getTimestamp("sent_at") != null ? rs.getTimestamp("sent_at").toLocalDateTime() : null,
                        rs.getBoolean("is_sent"),
                        rs.getTimestamp("received_at") != null ? rs.getTimestamp("received_at").toLocalDateTime() : null,
                        rs.getTimestamp("seen_at") != null ? rs.getTimestamp("seen_at").toLocalDateTime() : null
                );
                message.setId(rs.getInt("id"));
                return Optional.of(message);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving message by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages";
        try (Connection connection = DatabaseManager.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("sender_id"),
                        rs.getInt("conversation_id"),
                        rs.getString("content"),
                        getMessageTypeFromString(rs.getString("type")),
                        rs.getTimestamp("sent_at") != null ? rs.getTimestamp("sent_at").toLocalDateTime() : null,
                        rs.getBoolean("is_sent"),
                        rs.getTimestamp("received_at") != null ? rs.getTimestamp("received_at").toLocalDateTime() : null,
                        rs.getTimestamp("seen_at") != null ? rs.getTimestamp("seen_at").toLocalDateTime() : null
                );
                message.setId(rs.getInt("id"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all messages: " + e.getMessage());
        }
        return messages;
    }

    @Override
    public List<Message> findByConversationId(int conversationId, int offset, int limit) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE conversation_id = ? ORDER BY sent_at ASC, id ASC LIMIT ? OFFSET ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, conversationId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("sender_id"),
                        rs.getInt("conversation_id"),
                        rs.getString("content"),
                        getMessageTypeFromString(rs.getString("type")),
                        rs.getTimestamp("sent_at") != null ? rs.getTimestamp("sent_at").toLocalDateTime() : null,
                        rs.getBoolean("is_sent"),
                        rs.getTimestamp("received_at") != null ? rs.getTimestamp("received_at").toLocalDateTime() : null,
                        rs.getTimestamp("seen_at") != null ? rs.getTimestamp("seen_at").toLocalDateTime() : null
                );
                message.setId(rs.getInt("id"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving messages by conversation ID: " + e.getMessage());
        }
        return messages;
    }

    @Override
    public List<Message> findByConversationId(int conversationId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE conversation_id = ? ORDER BY sent_at ASC, id ASC";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, conversationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("sender_id"),
                        rs.getInt("conversation_id"),
                        rs.getString("content"),
                        getMessageTypeFromString(rs.getString("type")),
                        rs.getTimestamp("sent_at") != null ? rs.getTimestamp("sent_at").toLocalDateTime() : null,
                        rs.getBoolean("is_sent"),
                        rs.getTimestamp("received_at") != null ? rs.getTimestamp("received_at").toLocalDateTime() : null,
                        rs.getTimestamp("seen_at") != null ? rs.getTimestamp("seen_at").toLocalDateTime() : null
                );
                message.setId(rs.getInt("id"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving messages by conversation ID: " + e.getMessage());
        }
        return messages;
    }

    @Override
    public int save(Message message)
    {
        String sql = "INSERT INTO messages (sender_id, conversation_id, content, type, sent_at, is_sent, received_at, seen_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getConversationId());
            stmt.setString(3, message.getContent());
            stmt.setString(4, message.getType() != null ? message.getType().name() : MessageType.TEXT.name());
            stmt.setTimestamp(5, message.getSentAt() != null ? Timestamp.valueOf(message.getSentAt()) : null);
            stmt.setBoolean(6, message.isSent());
            stmt.setTimestamp(7, message.getReceivedAt() != null ? Timestamp.valueOf(message.getReceivedAt()) : null);
            stmt.setTimestamp(8, message.getSeenAt() != null ? Timestamp.valueOf(message.getSeenAt()) : null);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error saving message: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public void update(Message message) {
        String sql = "UPDATE messages SET sender_id = ?, conversation_id = ?, content = ?, type = ?, sent_at = ?, is_sent = ?, received_at = ?, seen_at = ? WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getConversationId());
            stmt.setString(3, message.getContent());
            stmt.setString(4, message.getType() != null ? message.getType().name() : MessageType.TEXT.name());
            stmt.setTimestamp(5, message.getSentAt() != null ? Timestamp.valueOf(message.getSentAt()) : null);
            stmt.setBoolean(6, message.isSent());
            stmt.setTimestamp(7, message.getReceivedAt() != null ? Timestamp.valueOf(message.getReceivedAt()) : null);
            stmt.setTimestamp(8, message.getSeenAt() != null ? Timestamp.valueOf(message.getSeenAt()) : null);
            stmt.setInt(9, message.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating message: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM messages WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting message: " + e.getMessage());
        }
    }
}