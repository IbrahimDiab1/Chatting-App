package com.liqaa.client.controllers.services.interfaces;

import com.liqaa.shared.models.entities.Notification;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface TryService {
    List<Notification> getNotifications(int recipientId) throws SQLException, RemoteException;
}
