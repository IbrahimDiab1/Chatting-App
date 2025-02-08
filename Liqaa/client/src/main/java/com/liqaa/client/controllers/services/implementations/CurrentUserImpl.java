package com.liqaa.client.controllers.services.implementations;

import com.liqaa.client.controllers.services.interfaces.CurrentUser;
import com.liqaa.client.network.ServerConnection;
import com.liqaa.shared.models.entities.User;

import java.rmi.RemoteException;

public class CurrentUserImpl implements CurrentUser
{
        private static User currentUser;
        // return currentUser;
        public User getCurrentUser()
        {
            try {
                return ServerConnection.getServer().getUserInfo("+201065320309"); // id=7
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public void setCurrentUser(User user)
        {
            currentUser = user;
        }
}



