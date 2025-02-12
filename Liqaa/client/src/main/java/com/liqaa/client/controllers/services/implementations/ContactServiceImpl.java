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
    public ObservableList<User> getContacts(int userId) {
        ObservableList<User> contacts = FXCollections.observableArrayList();
        // التواصل مع السيرفر للحصول على قائمة الاتصالات
        try {
            contacts.addAll(ServerConnection.getServer().getUserFriends(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public ObservableList<Category> getCategoriesForContact(int userId, int contactId) {
        ObservableList<Category> categories = FXCollections.observableArrayList();
        // التواصل مع السيرفر للحصول على الفئات الخاصة بالاتصال
        try {
            categories.addAll(ServerConnection.getServer().getCategoriesForContact(userId, contactId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public boolean deleteContact(int userId, int contactId) {
        // التواصل مع السيرفر لحذف الاتصال
        try {
            ServerConnection.getServer().deleteContact(userId, contactId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean renameCategory(int categoryId, String newName) {
        try {
            ServerConnection.getServer().renameCategory(categoryId, newName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
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
}