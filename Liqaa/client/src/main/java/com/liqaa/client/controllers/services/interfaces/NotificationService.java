package com.liqaa.client.controllers.services.interfaces;

import com.liqaa.shared.models.entities.Announcement;
import com.liqaa.shared.models.entities.AnnouncementNotification;
import com.liqaa.shared.models.entities.Notification;
import com.liqaa.shared.models.entities.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications(int userId) throws SQLException, RemoteException;
    User getNotificationSenderData(int senderId) throws RemoteException;
    AnnouncementNotification getAnnouncementById(int notificationId) throws RemoteException;
}
