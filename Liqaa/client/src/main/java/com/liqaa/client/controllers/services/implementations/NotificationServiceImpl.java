package com.liqaa.client.controllers.services.implementations;

import com.liqaa.client.controllers.services.interfaces.NotificationService;
import com.liqaa.client.network.ServerConnection;
import com.liqaa.shared.models.entities.Announcement;
import com.liqaa.shared.models.entities.AnnouncementNotification;
import com.liqaa.shared.models.entities.Notification;
import com.liqaa.shared.models.entities.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    private static NotificationServiceImpl instance;

    public static synchronized NotificationServiceImpl getInstance() {
        if (instance == null) {
            instance = new NotificationServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Notification> getAllNotifications(int userId) throws SQLException, RemoteException {
        return ServerConnection.getServer().getUserNotifications(userId);
    }

    @Override
    public User getNotificationSenderData(int senderId) throws RemoteException{
        return ServerConnection.getServer().getUserInfoById(senderId);
    }

    @Override
    public AnnouncementNotification getAnnouncementById(int notificationId) throws RemoteException{
        //return ServerConnection.getServer().
        return null;
    }
}
