/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.SQLException;
import java.util.List;
import logic.Section;

import logic.User;

public interface UsersDAO {
    public boolean addUser(User user) throws SQLException;   //добавить 
    public void updateUser(User user) throws SQLException;//обновить 
    public User getUserById(long id) throws SQLException;    //получить по id
    public List getAllUsers() throws SQLException;              //получить всех
    public void deleteUser(User user) throws SQLException;//удалить 
    public User getUserByName (String name) throws SQLException;// поиск по имени юзера 
    public User getUserByMale (String male) throws SQLException;
    
   
}