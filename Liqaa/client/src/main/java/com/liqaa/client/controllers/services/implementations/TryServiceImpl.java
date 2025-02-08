package com.liqaa.client.controllers.services.implementations;

import com.liqaa.client.controllers.services.interfaces.TryService;
import com.liqaa.client.network.ServerConnection;
import com.liqaa.shared.models.entities.Notification;
import com.liqaa.shared.models.entities.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class TryServiceImpl implements TryService {
    private static TryServiceImpl instance;

    public static synchronized TryServiceImpl getInstance() {
        if (instance == null) {
            instance = new TryServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Notification> getNotifications(int recipientId) throws SQLException, RemoteException {
        return ServerConnection.getServer().getUserNotifications(recipientId);
    }

    public boolean delNot(User user) throws RemoteException {
        return ServerConnection.getServer().updateUserInfo(user);
    }
}
