package com.liqaa.client.controllers.services.implementations;

import com.liqaa.client.controllers.services.interfaces.ContactService;
import com.liqaa.client.network.ServerConnection;
import com.liqaa.shared.models.entities.Group;
import com.liqaa.shared.models.entities.User;
import com.liqaa.shared.models.entities.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ContactServiceImpl implements ContactService {
    private static ContactService instance;

    private ContactServiceImpl() {
    }

    public static ContactService getInstance() {
        if (instance == null) {
            instance = new ContactServiceImpl();
        }
        return instance;
    }

    @Override
    public List<User> getUserFriends(int userId) throws RemoteException{
        return ServerConnection.getServer().getUserFriends(userId);
    }

    @Override
    public List<Category> getUserCategories(int userId) throws RemoteException{
        return ServerConnection.getServer().getCategories(userId);
    }

    @Override
    public boolean addContact (int userId, int contactId) throws RemoteException{
        return ServerConnection.getServer().addContact(userId, contactId);
    }

    @Override
    public void removeCategory(int categoryId, int userId) throws RemoteException{
        ServerConnection.getServer().removeCategory(categoryId, userId);
    }

    @Override
    public void createGroup(Group group, ArrayList<Integer> groupMembers) throws RemoteException{
        ServerConnection.getServer().createGroup(group, groupMembers);
    }

    @Override
    public User getUserInfo(String userPhone) throws RemoteException{
        return ServerConnection.getServer().getUserInfo(userPhone);
    }

    @Override
    public List<Integer> addCategories(List<Category> categories) throws RemoteException{
        return ServerConnection.getServer().addCategories(categories);
    }

    @Override
    public List<Category> getCategories(int userId) throws RemoteException{
        return ServerConnection.getServer().getCategories(userId);
    }

    @Override
    public boolean blockContact (int userId , int contactId) throws RemoteException{
        return ServerConnection.getServer().blockContact(userId, contactId);
    }

    @Override
    public boolean unblockContact (int userId , int contactId) throws RemoteException{
        return ServerConnection.getServer().unblockContact(userId, contactId);
    }

    @Override
    public boolean isBlocked(int userId, int contactId) throws RemoteException{
        return ServerConnection.getServer().isBlocked(userId, contactId);
    }

    @Override
    public void removeCategory(String categoryName, int userId) throws RemoteException{
        ServerConnection.getServer().removeCategory(categoryName, userId);
    }
}