package com.liqaa.client.controllers.services.interfaces;

import com.liqaa.shared.models.entities.Group;
import com.liqaa.shared.models.entities.User;
import com.liqaa.shared.models.entities.Category;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ContactService {
    ObservableList<User> getContacts(int userId); // الحصول على قائمة الاتصالات
    ObservableList<Category> getCategoriesForContact(int userId, int contactId); // الحصول على الفئات الخاصة بالاتصال
    boolean deleteContact(int userId, int contactId); // حذف الاتصال
    boolean renameCategory(int categoryId, String newName); // تغيير اسم الفئة

    List<User> getUserFriends(int userId) throws RemoteException;
    List<Category> getUserCategories(int userId) throws RemoteException;
    boolean addContact (int userId, int contactId) throws RemoteException;
    void removeCategory(int categoryId, int userId) throws RemoteException;
    void createGroup(Group group, ArrayList<Integer> groupMembers) throws RemoteException;
    User getUserInfo(String userPhone) throws RemoteException;
    List<Integer> addCategories(List<Category> categories) throws RemoteException;
    List<Category> getCategories(int userId) throws RemoteException;
    boolean blockContact (int userId , int contactId) throws RemoteException;
    boolean unblockContact (int userId , int contactId) throws RemoteException;
}